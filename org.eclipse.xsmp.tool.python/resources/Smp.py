from simulator_exception import SimulatorException
import json
import numpy
import XsmpUtils

# SMP types
Char8 = str
Bool = numpy.bool
Int8 = numpy.int8
Int16 = numpy.int16
Int32 = numpy.int32
Int64 = numpy.int64

UInt8 = numpy.uint8
UInt16 = numpy.uint16
UInt32 = numpy.uint32
UInt64 = numpy.uint64

Float32 = numpy.float32
Float64 = numpy.float64

Duration = numpy.int64
DateTime = numpy.int64

String8 = str


class ForceSlots(type):

    @classmethod
    def __prepare__(metaclass, name, bases, **kwds):
        # calling super is not strictly necessary because
        #  type.__prepare() simply returns an empty dict.
        # But if you plan to use metaclass-mixins then this is essential!
        super_prepared = super().__prepare__(metaclass, name, bases, **kwds)
        super_prepared['__slots__'] = ()
        return super_prepared


class Object:
    __slots__ = ('_parent', '_name')

    def __init__(self, parent: object, name: str):
        self._parent = parent
        self._name = name


class Constant:

    def __init__(self, value):
        self.value = value

    def __get__(self, obj, objtype=None):
        return self.value
    
    def __set__(self, obj, value):
        raise SimulatorException("The value of a constant cannot be changed")


class ContainerObject(list):

    def __init__(self, parent, name):
        self._parent = parent
        self._name = name
        list.__init__(self)
        if parent != None:
            parent._containers.append(self)

            
class Container:

    def __init__(self, dtype=None):
        self.dtype = dtype
        
    def __set_name__(self, owner, name):
        self.name = name
        self.private_name = "_" + name
                
    def __get__(self, obj, objtype=None) -> ContainerObject:
        if obj is None:
            return self
        if hasattr(obj, self.private_name):
            return getattr(obj, self.private_name)
        else:
            value = ContainerObject(obj, self.name)
            setattr(obj, self.private_name, value)
            return value

    def __set__(self, obj, value):
        self.__get__(obj).value = value

    def __delete__(self, obj):
        raise AttributeError(f"can't delete field {self.name}")


class Simulator:
    Models: Container = Container() 
    Services: Container = Container()
    
    def __init__(self):
        self.simu = None
        self.connected = False
        
        self.configurations = dict()
        
        self.io_connections = list()
        self.event_connections = list()
        self.if_link_connections = list()
        
    def connect_simulator(self):
        self.connected = True
        
        for model in self.Models:
            for neasted_instance in XsmpUtils.get_neasted_instances(model):
                # Initialisations
                self.configurations.update(neasted_instance._configurations)
                
                # Connections
                self.io_connections += neasted_instance._io_connections
                self.event_connections += neasted_instance._event_connections
                self.if_link_connections += neasted_instance._if_link_connections
    
    def get_value(self, obj: Object, additional_field_path: str=""):
        """
            Query the current field value on the simulator.
            Return an object whose type depends on the SMP field type
        """
        if not self.connected:
            return self.configurations[self.get_path(obj)]
        else:
            return self.simu.get_field_value(obj, additional_field_path)

    def set_value(self, obj: Object, value, additional_field_path: str="") -> None:
        """
            Overload the current value of a field. The given value is an object
            whose type should match the SMP field type.
        """
        print("Blabla", obj._name)
        if not self.connected:
            self.configurations[self.get_path(obj)] = value
        else:
            self.simu.set_field_value(obj, value, additional_field_path)
    
    def call_entrypoint(self, obj: Object) -> None:
        self.simu.call_entrypoint(obj)
        
    def call_event(self, obj: Object, arg) -> None:
        self.simu.call_event(obj, arg)

    def call_operation(self, obj: Object, parameters: dict, operation_name: str="") -> dict:
        """
            Call the operation of a model
        """
        response = self.simu.call_operation(obj, parameters, operation_name)
        return json.loads(response)
    
    def run(self, time: int, is_time_delta: bool=False) -> None:
        """
            Instruct the simulator to reach the given simulation time. (if is_time_delta == False)
            Or instruct the simulator to advance time in delta compared to the current time. (if is_time_delta == True)
        """
        self.simu.run(time, is_time_delta)
    
    def run_delta(self, delta_time: int) -> None:
        """
            Instruct the simulator to advance time in delta compared to the current time.
        """
        self.simu.run(delta_time, True)
        
    def connect(self, src, dest) -> None:
        """
            Connect an object to another object
            
            - Output field to input field
            - Event source to event sink
            - Reference to component
        """
        if self.connected:
            raise SimulatorException("Unable to make a connection after building the simulator")
        
        if isinstance(src, FieldObject) and isinstance(dest, FieldObject):
            self.io_connections.append((src, dest))
            
        elif isinstance(src, EventSourceObject) and isinstance(dest, EventSinkObject):
            self.event_connections.append((src, dest))
        
        elif isinstance(src, ReferenceObject) and isinstance(dest, Component):
            self.if_link_connections.append((src._parent, src._field.name, dest))

    def get_all_connections(self):
        global_io_connections = []
        global_event_connections = []
        global_if_link_connections = []
        
        # Get connections stored in the Simulator object
        global_io_connections += self.io_connections
        global_event_connections += self.event_connections
        global_if_link_connections += self.if_link_connections
        
        # Get connections stored in Component objects
        for model in self.Models:
            for neasted_instance in self.get_neasted_instances(model):
                global_io_connections += neasted_instance._io_connections
                global_event_connections += neasted_instance._event_connections
                global_if_link_connections += neasted_instance._if_link_connections
                
        return global_io_connections, global_event_connections, global_if_link_connections

    @staticmethod
    def get_simulator(obj: Object) -> '__class__':
        if isinstance(obj, Simulator):
            return obj
        elif hasattr(obj, "_parent"):
            return Simulator.get_simulator(obj._parent)
        else:
            return None

    def get_path(self, obj: Object) -> str:
        path = obj._name
        current_obj = obj
        while isinstance(current_obj._parent, Object):
            separator = "/" if isinstance(current_obj, Component) else "."
            current_obj = current_obj._parent
            path = current_obj._name + separator + path
        return "/" + path
    
    # FIXME duplication dans SimulatorJsonCreator
    def get_neasted_instances(self, instance):
        """Get the list of all instances nested in all containers."""
        instances = []
        stack = [(instance, 0)]
        
        while stack:
            instance, counter = stack.pop()
            instances.insert(counter, instance)
            counter += 1
            
            containers = instance._containers
            for container in containers:
                for neasted_instance in container:
                    stack.append((neasted_instance, counter))
    
        return instances
    
    
class Component(Object):

    def __init__(self, container: Container, name: str, description: str=""):
        super().__init__(container._parent if container != None else container, name)
        self._description = description
        self._containers = list()
        self._container_name = container._name
        self._references = list()
        self._entry_points = list()
        self._event_sinks = list()
        self._event_sources = list()
        
        self._io_connections = list()
        self._event_connections = list()
        self._if_link_connections = list()
        
        self._configurations = dict()
        
        if container != None:
            container.append(self)
            
    def __setitem__(self, field_name, value):
        '''Operator overloading to provide a more dynamic setter syntax for the user.
        model.['field_name'] = new_value
        
        Attribute:
            field_name -- The name of the model field
            value -- The new field value
        '''
        is_connected = Simulator.get_simulator(self).connected
        
        if not is_connected:
            parent_component = XsmpUtils.get_parent_component(self)
            parent_component._configurations[XsmpUtils.get_path(self) + "." + field_name] = value
        else:
            Simulator.get_simulator(self).set_value(self, value, field_name)
        
    def __getitem__(self, field_name):
        '''Operator overloading to provide a more dynamic getter syntax for the user.
        var = model.['field_name']
        
        Attribute:
            field_name -- The name of the model field
        '''
        is_connected = Simulator.get_simulator(self).connected
        
        if not is_connected:
            parent_component = XsmpUtils.get_parent_component(self)
            return parent_component._configurations[XsmpUtils.get_path(self) + "." + field_name]
        else:
            return Simulator.get_simulator(self).get_value(self, field_name)
    
    def call_operation(self, operation_name: str, **kwargs) -> dict:
        '''Call an operation'''
        return Simulator.get_simulator(self).call_operation(self, kwargs, operation_name)


class EntryPointObject(Object):

    def __init__(self, parent, name):
        super().__init__(parent, name)
        if parent != None:
            parent._entry_points.append(self)
    
    def __call__(self) -> None:
        Simulator.get_simulator(self).call_entrypoint(self)


class EntryPoint(object):

    def __init__(self, event_type=None):
        self.event_type = event_type

    def __set_name__(self, owner, name):
        self.name = name
        self.private_name = "_" + name

    def __get__(self, obj, objtype=None) -> EntryPointObject:
        if obj is None:
            return self
        if hasattr(obj, self.private_name):
            return getattr(obj, self.private_name)
        else:
            value = EntryPointObject(obj, self.name)
            setattr(obj, self.private_name , value)
            return value

    def __delete__(self, obj):
        raise AttributeError(f"can't delete field {self.name}")


class EventSink:

    def __init__(self, event_type=None):
        self.event_type = event_type
        
    def __set_name__(self, owner, name):
        self.name = name
        self.private_name = "_" + name
                
    def __get__(self, obj, objtype=None):
        if obj is None:
            return self
        if hasattr(obj, self.private_name):
            return getattr(obj, self.private_name)
        else:
            value = EventSinkObject(obj, self)
            setattr(obj, self.private_name, value)
            return value

    def __set__(self, obj, value):
        self.__get__(obj).value = value

    def __delete__(self, obj):
        raise AttributeError(f"can't delete field {self.name}")


class EventSinkObject(Object):
    
    def __init__(self, parent, field):
        super().__init__(parent, field.name)
        self._field = field
        self.event_type = field.event_type
        
        parent._event_sinks.append(self)
        
    def call(self, arg=None):
        Simulator.get_simulator(self).call_event(self, arg)


class EventSource:

    def __init__(self, dtype=None):
        self.dtype = dtype
        
    def __set_name__(self, owner, name):
        self.name = name
        self.private_name = "_" + name
                
    def __get__(self, obj, objtype=None):
        if obj is None:
            return self
        if hasattr(obj, self.private_name):
            return getattr(obj, self.private_name)
        else:
            value = EventSourceObject(obj, self)
            setattr(obj, self.private_name, value)
            return value

    def __set__(self, obj, value):
        self.__get__(obj).value = value

    def __delete__(self, obj):
        raise AttributeError(f"can't delete field {self.name}")


class EventSourceObject(Object):

    def __init__(self, parent, eventsource):
        super().__init__(parent, eventsource.name)
        self._parent = parent
        self._eventsource = eventsource
        
        parent._event_sources.append(self)

    def call(self, arg=None) -> None:
        Simulator.get_simulator(self).call_event(self, arg)

    def connect(self, esi: EventSinkObject) -> None:
        self._parent._event_connections.append((self, esi))


class Field:

    def __init__(self, dtype, default_value=None):
        self.dtype = dtype
        self.default_value = default_value

    def __get__(self, obj, objtype=None):
        if obj is None:
            return self
    
        if issubclass(self.dtype, Object):
            return self.dtype(obj, self.name, self.default_value)
        else:
            return FieldObject(obj, self)

    def __set__(self, obj, value):
        self.__get__(obj).value = value

    def __delete__(self, obj):
        raise AttributeError(f"can't delete field {self.name}")

    def __set_name__(self, owner, name):
        self.name = name
        self.private_name = "_" + name


class FieldObject(Object, metaclass=ForceSlots):
    __slots__ = ('_parent', '_field')

    def __init__(self, parent, field, index=None):
        super().__init__(parent, field.name + ('[' + str(index) + ']' if index is not None else ''))
        self._parent = parent
        self._field = field
    
    @property
    def value(self):
        is_connected = Simulator.get_simulator(self).connected
        
        if not is_connected:
            parent_component = XsmpUtils.get_parent_component(self)
            return parent_component._configurations[XsmpUtils.get_path(self)]
        else:
            if self._field.dtype != None:
                return self._field.dtype(Simulator.get_simulator(self).get_value(self))
            else:
                return Simulator.get_simulator(self).get_value(self)

    @value.setter
    def value(self, value):
        is_connected = Simulator.get_simulator(self).connected
        
        if not is_connected:
            parent_component = XsmpUtils.get_parent_component(self)
            parent_component._configurations[XsmpUtils.get_path(self)] = value
        else:
            if self._field.dtype != None:
                Simulator.get_simulator(self).set_value(self, self._field.dtype(value))
            else:
                Simulator.get_simulator(self).set_value(self, value)
            
    def connect(self, input_field: Field) -> None:
        XsmpUtils.get_parent_component(self)._io_connections.append((self, input_field))

    def __rpow__(self, mod):
        return self.value.__rpow__(mod)

    def __and__(self, value):
        return self.value.__and__(value)

    def __rfloordiv__(self, value):
        return self.value.__rfloordiv__(value)

    def __mul__(self, value):
        return self.value.__mul__(value)

    def __round__(self, *args, **kwargs):
        return self.value.__round__(*args, **kwargs)

    def __rdivmod__(self, value):
        return self.value.__rdivmod__(value)

    def conjugate(self, *args, **kwargs):
        return self.value.conjugate(*args, **kwargs)

    def __ror__(self, value):
        return self.value.__ror__(value)

    def __floordiv__(self, value):
        return self.value.__floordiv__(value)

    def __float__(self):
        return self.value.__float__()

    def __ge__(self, value):
        return self.value.__ge__(value)

    def __invert__(self):
        return self.value.__invert__()

    def __index__(self):
        return self.value.__index__()

    def __rtruediv__(self, value):
        return self.value.__rtruediv__(value)

    def __format__(self, *args, **kwargs):
        return self.value.__format__(*args, **kwargs)

    def __bool__(self):
        return self.value.__bool__()

    def __reduce__(self, *args, **kwargs):
        return self.value.__reduce__(*args, **kwargs)

    def __int__(self):
        return self.value.__int__()

    def __rmod__(self, value):
        return self.value.__rmod__(value)

    def __truediv__(self, value):
        return self.value.__truediv__(value)

    def __pow__(self, mod):
        return self.value.__pow__(mod)

    def __ceil__(self, *args, **kwargs):
        return self.value.__ceil__(*args, **kwargs)

    def __rand__(self, value):
        return self.value.__rand__(value)

    def __repr__(self):
        return self.value.__repr__()

    def __ne__(self, value):
        return self.value.__ne__(value)

    def __pos__(self):
        return self.value.__pos__()

    def __floor__(self, *args, **kwargs):
        return self.value.__floor__(*args, **kwargs)

    def __xor__(self, value):
        return self.value.__xor__(value)

    def __lt__(self, value):
        return self.value.__lt__(value)

    def __mod__(self, value):
        return self.value.__mod__(value)

    def __divmod__(self, value):
        return self.value.__divmod__(value)

    def __lshift__(self, value):
        return self.value.__lshift__(value)

    def __sub__(self, value):
        return self.value.__sub__(value)

    def __rshift__(self, value):
        return self.value.__rshift__(value)

    def __trunc__(self, *args, **kwargs):
        return self.value.__trunc__(*args, **kwargs)

    def __neg__(self):
        return self.value.__neg__()

    def __rsub__(self, value):
        return self.value.__rsub__(value)

    def __radd__(self, value):
        return self.value.__radd__(value)

    def __reduce_ex__(self, *args, **kwargs):
        return self.value.__reduce_ex__(*args, **kwargs)

    def __abs__(self):
        return self.value.__abs__()

    def __str__(self):
        return self.value.__str__()

    def __or__(self, value):
        return self.value.__or__(value)

    def __eq__(self, value):
        return self.value.__eq__(value)

    def __gt__(self, value):
        return self.value.__gt__(value)

    def __rmul__(self, value):
        return self.value.__rmul__(value)

    def bit_length(self, *args, **kwargs):
        return self.value.bit_length(*args, **kwargs)

    def __le__(self, value):
        return self.value.__le__(value)

    def __add__(self, value):
        return self.value.__add__(value)

    def __sizeof__(self, *args, **kwargs):
        return self.value.__sizeof__(*args, **kwargs)

    def __rrshift__(self, value):
        return self.value.__rrshift__(value)

    def __rlshift__(self, value):
        return self.value.__rlshift__(value)

    def __rxor__(self, value):
        return self.value.__rxor__(value)


class Operation(Object):

    def __init__(self, parent, name):
        super().__init__(parent, name)
        parent._entry_points.append(self)
    
    def __call__(self, args) -> dict:
        return Simulator.get_simulator(self).call_operation(self, args)


class ReferenceObject(list):

    def __init__(self, parent, field):
        list.__init__(self)
        
        self._parent = parent
        self._field = field
        
        if parent != None:
            parent._references.append(self)
            
    def __iadd__(self, value):
        '''Redefining the += operator to add an item to the list with a simple syntax'''
        
        # If the value is a list, add separately in the list
        if isinstance(value, list):
            self.extend(value)
        else:
            self.append(value)
        
        return self
    
    def connect(self, instance: Component) -> None:
        self._parent._if_link_connections.append((self._parent, self._field.name, instance))
        # Simulator.get_simulator(self).if_link_connections.append((self._parent, self._field.name, instance))


class Reference:

    def __init__(self, dtype=None):
        self.dtype = dtype

    def __set_name__(self, owner, name):
        self.name = name
        self.private_name = "_" + name
                
    def __get__(self, obj, objtype=None) -> ReferenceObject:
        if obj is None:
            return self
        if hasattr(obj, self.private_name):
            return getattr(obj, self.private_name)
        else:
            value = ReferenceObject(obj, self)
            setattr(obj, self.private_name, value)
            return value

    def __set__(self, obj, value):
        self.__get__(obj).value = value

    def __delete__(self, obj):
        raise AttributeError(f"can't delete field {self.name}")

  
class Type(Object):

    def __init__(self, parent, name):
        super().__init__(parent, name)

        
class Structure(Object, metaclass=ForceSlots):
    __slots__ = ('_parent', '_name')

    def __init__(self, parent: object, name: str):
        super().__init__(parent, name)
        
    def __setitem__(self, field_name, value):
        is_connected = Simulator.get_simulator(self).connected
        
        if not is_connected:
            parent_component = XsmpUtils.get_parent_component(self)
            parent_component._configurations[XsmpUtils.get_path(self) + "." + field_name] = value
        else:
            Simulator.get_simulator(self).set_value(self, value, field_name)
        
    def __getitem__(self, field_name):
        is_connected = Simulator.get_simulator(self).connected
        
        if not is_connected:
            parent_component = XsmpUtils.get_parent_component(self)
            return parent_component._configurations[XsmpUtils.get_path(self) + "." + field_name]
        else:
            return Simulator.get_simulator(self).get_value(self, field_name)


class Array(Object, metaclass=ForceSlots):
    __slots__ = ('_parent', 'name', 'dtype', '_size')

    def __init__(self, parent: Object, name: str, clazz, size: int, default_value=None) -> None:
        '''
        Constructor
        '''
        super().__init__(parent, name)
        self.name = name
        self.dtype = clazz
        self._size = size

    def get(self, index:int):
        '@rtype ItemType'
        i = index
        if i < 0:
            i = self._size + i
        if i >= self._size or i < 0:
            raise IndexError(f'Index {index} is not in range({self._size})')
        
        # return self._clazz(self._parent, f'{self._name}[{i}]')
        if issubclass(self.dtype, Object):
            return  self.dtype(self._parent, f'{self._name}[{i}]')
        else:
            return FieldObject(self._parent, self, index)

    def __getitem__(self, index: int):
        return self.get(index)

    def __setitem__(self, index: int, value) -> None:
        self.get(index).value = value
        
    def __len__(self) -> int:
        return self._size

