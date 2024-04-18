/*******************************************************************************
 * Copyright (C) 2020-2022 THALES ALENIA SPACE FRANCE.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.xsmp.profile.tas_mdk.generator.cpp.type

import com.google.inject.Inject
import org.eclipse.xsmp.generator.cpp.type.ComponentGenerator
import org.eclipse.xsmp.profile.tas_mdk.generator.cpp.FieldHelper
import org.eclipse.xsmp.model.xsmp.Component
import org.eclipse.xsmp.model.xsmp.Field
import org.eclipse.xsmp.model.xsmp.Structure
import org.eclipse.xsmp.model.xsmp.Operation
import org.eclipse.xsmp.model.xsmp.Parameter
import org.eclipse.xsmp.model.xsmp.NamedElement
import org.eclipse.xsmp.model.xsmp.VisibilityKind
import org.eclipse.xsmp.model.xsmp.String
import org.eclipse.xsmp.model.xsmp.Array
import org.eclipse.xsmp.model.xsmp.NamedElementWithMembers
import org.eclipse.xsmp.model.xsmp.Property
import org.eclipse.xsmp.model.xsmp.AccessKind

class TasMdkComponentGenerator extends ComponentGenerator {

	@Inject extension FieldHelper
	
	protected override CharSequence initializeBase(Component c) {
		val base = c.base()
		if (base !== null)
			'''
				// Base class initialization
				«base»(name, description, parent, type_registry)
			'''
	}

	override protected generateHeaderBody(Component t) {
		'''
			«t.comment»
			class «t.name»: public «t.nameGen» {
			public:
				«t.constructorDeclaration(false)»
				
				«t.declareMembers(VisibilityKind.PUBLIC)»
			};
		'''
	}

	override protected declareGen(Component type, Field f, boolean useGenPattern) {

		if (f.isTasMdkField) {
			var CharSequence fieldType

			if (f.type instanceof Structure)
				fieldType = '''StructureField<«f.type.field_fqn»>'''
			else if (f.type instanceof Array)
				fieldType = '''ArrayField<«f.type.field_fqn»>'''
			else
				fieldType = '''SimpleField<«f.type.id»>'''

			'''
				«f.comment»
				«IF f.isMutable»mutable «ENDIF»typename ::TasMdk::«fieldType»«IF f.eContainer instanceof Structure»::in<Opts...>«ENDIF»«IF f.transient»::transient«ENDIF»«IF f.input»::input«ENDIF»«IF f.output»::output«ENDIF»«IF f.failure»::failure«ENDIF»«IF f.forcible»::forcible«ENDIF»«IF f.ofInput»::of_input«ENDIF»«IF f.ofOutput»::of_output«ENDIF»«IF f.ofFailure»::of_failure«ENDIF»::type «f.name»;
			'''
		} else
			super.declareGen(type, f, useGenPattern)
	}
	
    override protected generateSourceBody(Component type, boolean useGenPattern) {
        '''
            «type.name(false)»::«type.name(false)»(
                    ::Smp::String8 name,
                    ::Smp::String8 description,
                    ::Smp::IObject* parent,
                    ::Smp::Publication::ITypeRegistry* type_registry)
                    : «type.name(true)»::«type.name(true)»(name, description, parent, type_registry) {
                // Publish_Callback = std::bind(&«type.name(false)»::_publishHook, this, std::placeholders::_1);
                // Configure_Callback = std::bind(&«type.name(false)»::_configureHook, this, std::placeholders::_1, std::placeholders::_2);
                // Connect_Callback = std::bind(&«type.name(false)»::_connectHook, this, std::placeholders::_1);
                // Disconnect_Callback = std::bind(&«type.name(false)»::_disconnectHook, this);
            }
            
            /// Virtual destructor that is called by inherited classes as well.
            «type.name(false)»::~«type.name(false)»() {
            }
            
            «type.defineMembers(useGenPattern)»
        '''
    }
    
	override protected generateHeaderGenBody(Component t, boolean useGenPattern) {
		'''
			«IF useGenPattern»class «t.name(false)»;«ENDIF»
			«t.uuidDeclaration»
			
			«t.comment»
			class «t.name(useGenPattern)»«FOR base : t.bases BEFORE ": " SEPARATOR ", "»«base»«ENDFOR» {
			
			«IF useGenPattern»friend class «t.name(false)»;«ENDIF»
			
			«t.declareMembersGen(useGenPattern, VisibilityKind.PRIVATE)»
			
			// ------------------------------------------------------------------------------------
			// ------------------------- Constructors/Destructor -------------------------
			// ------------------------------------------------------------------------------------
			public:
			    «constructorDeclaration(t, useGenPattern)»
			
			    static ::TasMdk::Request::Handler<«t.name(useGenPattern)»>::CollectionType requestHandlers;
			    
			«addCommonModelDefinitions»
			};
			template <typename _Type>
			void «t.name(useGenPattern)»::PopulateRequestHandlers(
			    _Type* bluePrint,
			    typename ::TasMdk::Request::Handler<_Type>::CollectionType& handlers)
			{
			    «FOR op : t.member.filter(Operation)»
			    	«op.generateRqHandlerParam(t)»
			    «ENDFOR»
			    «FOR property : t.member.filter(Property)»
			    	«property.generateRqHandlerParam(t)»
			    «ENDFOR»
			}
		'''
	}

	override protected generateSourceGenBody(Component t, boolean useGenPattern) {
		'''
			::TasMdk::Request::Handler<«t.name(useGenPattern)»>::CollectionType «t.name(useGenPattern)»::requestHandlers;
			
			void «t.name(useGenPattern)»::Configure(
			    ::Smp::Services::ILogger* logger,
			    ::Smp::Services::ILinkRegistry* linkRegistry) {
			    // Call base implementation first
			    «IF t.base !== null»«t.base.id»::Configure(logger, linkRegistry);«ENDIF»
			    if (Configure_Callback) {
			    	Configure_Callback(logger, linkRegistry);
			    }
			}
			
			void «t.name(useGenPattern)»::Connect(::Smp::ISimulator* simulator) {
			    «IF t.base !== null»
			    	// Call mdk implementation first
			    	«t.base.id»::Connect(simulator);
			    «ENDIF»
			    if (Connect_Callback) {
			    	Connect_Callback(simulator);
			    }
			}
			
			void «t.name(useGenPattern)»::Disconnect() {
				if (Disconnect_Callback) {
					Disconnect_Callback();
				}
				   «IF t.base !== null»
				   	// Call parent implementation last, to remove references to the Simulator and its services
				   	«t.base.id»::Disconnect();
				   «ENDIF»
			}
			
			«t.name(useGenPattern)»::«t.name(useGenPattern)»(
			    ::Smp::String8 name,
			    ::Smp::String8 description,
			    ::Smp::IObject* parent,
			    ::Smp::Publication::ITypeRegistry* type_registry)«FOR i : t.initializerList(useGenPattern) BEFORE ": \n" SEPARATOR ", "»«i»«ENDFOR»
			{
			}
			
			«t.name(useGenPattern)»::~«t.name(useGenPattern)»() {
			    «FOR f : t.member»
			    	«f.finalize»
			    «ENDFOR»
			}
			
			void «t.name(useGenPattern)»::Publish(
			    ::Smp::IPublication* receiver) {
			    «IF t.base !== null»
			    	// Call base class implementation first
			    	«t.base.id»::Publish(receiver);
			    «ENDIF»
			    
			    if (Publish_Callback) {
			    	Publish_Callback(receiver);
			    }
			    
			    // Populate the request handlers (only once)
			    if (requestHandlers.empty()) {
			        PopulateRequestHandlers<«t.name(useGenPattern)»>(this, requestHandlers);
			    }
			     
			     «FOR m : t.member»«m.Publish»«ENDFOR»
			}
			
			void «t.name(useGenPattern)»::Invoke(
			    ::Smp::IRequest* request) {
			    if (request == nullptr) {
			        return;
			    }
			    auto handler = requestHandlers.find(request->GetOperationName());
			    if (handler != requestHandlers.end()) {
			        handler->second(*this, request);
			    }
			    else {
			        «IF t.base !== null»
			        	// pass the request down to the base model
			        	«t.base.id»::Invoke(request);
			        «ELSE»
			        	// request does not match any operation provided by this model
			        	throw ::TasMdk::InvalidOperationName(request->GetOperationName());
			    «ENDIF»
			    }
			}
			const Smp::Uuid& «t.name(useGenPattern)»::GetUuid() const {
			    return Uuid_«t.name(false)»;
			}
			«t.defineMembersGen(useGenPattern)»
		'''
	}

	override CharSequence constructorDeclaration(Component t, boolean useGenPattern) {
		'''
			/// Constructor setting name, description and parent.
			/// @param name Name of new model instance.
			/// @param description Description of new model instance.
			/// @param parent Parent of new model instance.
			/// @param type_registry Reference to global type registry.
			«t.name(useGenPattern)»(
			        ::Smp::String8 name,
			        ::Smp::String8 description,
			        ::Smp::IObject* parent,
			        ::Smp::Publication::ITypeRegistry* type_registry);
			
			/// Virtual destructor to release memory.
			virtual ~«t.name(useGenPattern)»();
		'''
	}

	def CharSequence addCommonModelDefinitions() {
		'''
			// ----------------------------------------------------------------------------------
			// ------------------------------- IComponent --------------------------------
			// ----------------------------------------------------------------------------------
			
			    /// Publish fields, operations and properties of the model.
			    /// @param receiver Publication receiver.
			    virtual void Publish(::Smp::IPublication* receiver) override;
			
			    /// Request for configuration.
			    /// @param logger Logger to use for log messages during Configure().
			    /// @param linkRegistry Link Registry to use for registration of
			    ///         links created during Configure() or later.
			    virtual void Configure( ::Smp::Services::ILogger* logger, ::Smp::Services::ILinkRegistry* linkRegistry) override;
			
			    /// Connect model to simulator.
			    /// @param simulator Simulation  Environment  that  hosts the model.
			    ///
			    virtual void Connect(
			        ::Smp::ISimulator* simulator) override;
			
			    /// Disconnect model to simulator.
			    /// @throws Smp::InvalidComponentState
			    virtual void Disconnect() override;
			
			    /// Return the Universally Unique Identifier of the Model.
			    /// @return  Universally Unique Identifier of the Model.
			    virtual const Smp::Uuid& GetUuid() const override;
			
			    /// Invoke the operation for the given request.
			    /// @param request Request object to invoke.
			    virtual void Invoke(::Smp::IRequest* request) override;
			
			protected:
			    /// It populates the static map of request handler to implement the invoke function.
			    /// @param bluePrint an object to use as blue print if needed
			    /// @param handlers the map to be populated.
			    template <typename _Type>
			    static void PopulateRequestHandlers(_Type* bluePrint, typename ::TasMdk::Request::Handler<_Type>::CollectionType& handlers);
			        
			private:
			    /// Callback for custom publication
			    std::function<void(::Smp::IPublication*)> Publish_Callback;
			    /// Callback for custom configuration
			    std::function<void(::Smp::Services::ILogger *, ::Smp::Services::ILinkRegistry*)> Configure_Callback;
			    /// Callback for custom connection
			    std::function<void(::Smp::ISimulator*)> Connect_Callback;
			    /// Callback for custom disconnection
			    std::function<void()> Disconnect_Callback;
			
		'''
	}

	override CharSequence initParameter(Parameter p, NamedElementWithMembers context) {
		switch (p.direction) {
			case IN,
			case INOUT: {
				// declare and initialize the parameter
				'''
					«super.initParameter(p, context)»
					::TasMdk::Request::initParameter(p_«p.name», request, "«p.name»", «p.type.generatePrimitiveKind»«IF p.^default!==null», true«ENDIF»);
				'''
			}
			default: {
				super.initParameter(p, context)
			}
		}
	}


	override CharSequence setParameter(Parameter p, NamedElement context) {

		switch (p.direction) {
			case OUT,
			case INOUT: {
				if (p.type instanceof String)
					'''
						::TasMdk::Request::setParameter(request, "«p.name»", {«p.type.generatePrimitiveKind», p_«p.name».data()});
					'''
				else
					'''
						::TasMdk::Request::setParameter(request, "«p.name»", {«p.type.generatePrimitiveKind», p_«p.name»});
					'''
			}
			default: {
				// do nothing
			}
		}
	}

	def CharSequence generateRqHandlerParam(Operation o, NamedElementWithMembers context) {

		val r = o.returnParameter

		'''
			if (handlers.find("«o.name»") == handlers.end()) {
			    handlers["«o.name»"] = [](_Type & component, ::Smp::IRequest* request) {
			    «FOR p : o.parameter»
			    	«p.initParameter(context)»
			    «ENDFOR»
			    /// Invoke «o.name»
			    «IF r !== null»«r.type.id» p_«r.name» = «ENDIF»
			    component.«o.name»(«FOR p : o.parameter SEPARATOR ', '»«IF p.isByPointer»&«ENDIF»p_«p.name»«ENDFOR»);
			    «FOR p : o.parameter»
				«p.setParameter(context)»
				«ENDFOR»
				«IF r !== null»request->SetReturnValue({«r.type.generatePrimitiveKind», p_«r.name»});«ENDIF»
				};
			}
		'''
	}
    def CharSequence generateRqHandlerParam(Property p, NamedElementWithMembers container) {

        '''
            «IF p.access !== AccessKind.WRITE_ONLY»
                if (handlers.find("get_«p.name»") == handlers.end()) {
                    handlers["get_«p.name»"] = [](_Type & component, ::Smp::IRequest* request) {
                        /// Invoke get_«p.name»
                        request->SetReturnValue({«p.type.generatePrimitiveKind», component.get_«p.name»()});
                    };
                }
            «ENDIF»
            «IF p.access !== AccessKind.READ_ONLY»
                if (handlers.find("set_«p.name»") == handlers.end()) {
                    handlers["set_«p.name»"] = [](_Type & component, ::Smp::IRequest* request) {
                        /// Invoke set_«p.name»
                        «p.type.id» «p.name»;
                        ::TasMdk::Request::initParameter(«p.name», request, "«p.name»", «p.type.generatePrimitiveKind»);
                        component.set_«p.name»(«p.name»);
                    };
                }
            «ENDIF»
        '''
    }
}
