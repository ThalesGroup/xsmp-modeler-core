package org.eclipse.xsmp.generator.cpp

import java.util.Collections
import java.util.List
import org.eclipse.xsmp.util.XsmpUtil
import org.eclipse.xsmp.xcatalogue.Array
import org.eclipse.xsmp.xcatalogue.Catalogue
import org.eclipse.xsmp.xcatalogue.Component
import org.eclipse.xsmp.xcatalogue.LanguageType
import org.eclipse.xsmp.xcatalogue.Model
import org.eclipse.xsmp.xcatalogue.NamedElement
import org.eclipse.xsmp.xcatalogue.PrimitiveType
import org.eclipse.xsmp.xcatalogue.Service
import org.eclipse.xsmp.xcatalogue.Structure
import org.eclipse.xsmp.xcatalogue.Type
import org.eclipse.xsmp.xcatalogue.ValueReference
import org.eclipse.xsmp.xcatalogue.ValueType

class CatalogueGenerator extends AbstractFileGenerator<Catalogue> {

    override void collectIncludes(Catalogue type, IncludeAcceptor acceptor) {

        acceptor.mdkHeader("Smp/ISimulator.h")

        type.eAllContents.filter(LanguageType).filter[!(it instanceof ValueReference)].forEach[acceptor.source(it)]
        XsmpUtil.dependentPackages(type).forEach[acceptor.mdkSource(it.name() + ".h")]
    }

    protected def boolean requireFactory(Catalogue t) {
        t.eAllContents.filter(Component).exists[!it.isAbstract]
    }

    protected def dispatch CharSequence registerComponent(Model model) {
    }

    protected def dispatch CharSequence registerComponent(Service service) {
        '''
            // Register Service «service.name»
            simulator->AddService( new ::«service.fqn.toString("::")»(
                                "«service.name»", // name
                                «service.description()», // description
                                «globalNamespaceName»::simulator // parent
                                ));
        '''
    }

    protected def dispatch CharSequence unregisterComponent(Model model) {
        '''
            // Delete Factory for the Model «model.name»
            delete «globalNamespaceName»::simulator->GetFactory(«model.uuidQfn»);
        '''

    }

    protected def dispatch CharSequence unregisterComponent(Service service) {
        ''''''
    }

    protected def dispatch CharSequence register(ValueType t) {
        '''
            // register «t.eClass.name» «t.name»
            ::«(t.eContainer as NamedElement).fqn.toString("::")»::_Register_«t.name»(typeRegistry);
        '''
    }

    protected def dispatch CharSequence register(PrimitiveType t) {
    }

    protected def dispatch CharSequence register(Structure t) {
        '''
            // register «t.eClass.name» «t.name»
            ::«t.fqn.toString("::")»::_Register(typeRegistry);
        '''
    }

    def CharSequence generatePkgFile(Catalogue t, boolean useGenPattern, IncludeAcceptor acceptor, Catalogue cat) {

        '''
            «copyright.getSourceText(t, useGenPattern,cat)»
            
            // -------------------------------------------------------------------------------
            // --------------------------------- Includes ----------------------------------
            // -------------------------------------------------------------------------------
            #include "«t.name()».h"
            #include "Smp/Publication/ITypeRegistry.h"
                            
            #ifdef  WIN32
            #define DLL_EXPORT __declspec(dllexport) // %RELAX<mconst> Visual Studio requires a define
            #else
            #define DLL_EXPORT
            #endif
            
            // -----------------------------------------------------------------------------
            // -------------------------- Initialise Function ------------------------------
            // -----------------------------------------------------------------------------
            
            extern "C" {
            
                        /// Global Initialise function of Package «t.name».
                        /// @param simulator Simulator for registration of factories.
                        /// @param typeRegistry Type Registry for registration of types.
                        /// @return True if initialisation was successful, false otherwise.
                        DLL_EXPORT bool Initialise(
                                ::Smp::ISimulator* simulator,
                                ::Smp::Publication::ITypeRegistry* typeRegistry) {
                            bool initialisationSucceeded = true;
                            initialisationSucceeded &= Initialise_«t.name»(simulator, typeRegistry);
                            return initialisationSucceeded;
                        }
            }
            
            // -----------------------------------------------------------------------------
            // ---------------------------- Finalise Function ------------------------------
            // -----------------------------------------------------------------------------
            
            extern "C" {                        
                        /// Global Finalise function of Package «t.name».
                        /// @return True if finalisation was successful, false otherwise.
                        DLL_EXPORT bool Finalise() {
                            bool finalisationSucceeded = true;
                            finalisationSucceeded &= Finalise_«t.name»();
                            return finalisationSucceeded;
                        }
            }
        '''
    }

    override protected generateHeaderGenBody(Catalogue t, boolean useGenPattern) {
        '''
            // Entry points for static library
            extern "C" {
                /// Initialise Package «t.name».
                /// @param simulator Simulator for registration of factories.
                /// @param typeRegistry Type Registry for registration of types.
                /// @return True if initialisation was successful, false otherwise.
                bool Initialise_«t.name»(
                    ::Smp::ISimulator* simulator,
                    ::Smp::Publication::ITypeRegistry* typeRegistry);
            
                /// Finalise Package «t.name».
                /// @return True if finalisation was successful, false otherwise.
                bool Finalise_«t.name»();
            }
        '''
    }

    override protected generateHeaderBody(Catalogue t) {
        '''
            // Entry points for static library
            extern "C" {
                /// Initialise Package «t.name».
                /// @param simulator Simulator for registration of factories.
                /// @param typeRegistry Type Registry for registration of types.
                /// @return True if initialisation was successful, false otherwise.
                bool Initialise_«t.name»(
                    ::Smp::ISimulator* simulator,
                    ::Smp::Publication::ITypeRegistry* typeRegistry);
            
                /// Finalise Package «t.name».
                /// @return True if finalisation was successful, false otherwise.
                bool Finalise_«t.name»();
            }
        '''
    }

    /**
     * Return the List of ValueType in the Catalogue
     * Keep order as defined in the Catalogue as much as possible
     * Types with dependencies are put at the end
     */
    protected def sortedTypes(Catalogue c) {
        // collect all types
        val types = c.eAllContents.filter(ValueType).toList

        val deps = <ValueType, List<Type>>newLinkedHashMap
        // compute the list of dependencies for each Type
        types.forEach[type|deps.put(type, type.computeDeps.filter[types.contains(it)].toList)]

        var result = <ValueType>newArrayList

        while (!deps.empty) {
            var iterator = deps.entrySet.iterator
            while (iterator.hasNext) {
                var dep = iterator.next
                // check that all deps are already contained
                if (result.containsAll(dep.value)) {
                    result.add(dep.key)
                    // remove the current entry
                    iterator.remove
                }
            }
        }
        return result
    }

    private def dispatch Iterable<Type> computeDeps(Type t) {
        Collections.emptyList
    }

    private def dispatch Iterable<Type> computeDeps(Array t) {
        Collections.singleton(t.itemType)
    }

    private def dispatch Iterable<Type> computeDeps(ValueReference t) {
        Collections.singleton(t.type)
    }

    private def dispatch Iterable<Type> computeDeps(Structure t) {
        t.getAssignableFields().map[type]
    }



    def CharSequence globalNamespaceName() {
        ''''''
    }

    override protected generateSourceGenBody(Catalogue t, boolean useGenPattern) {

        // list of dependent packages
        val deps = XsmpUtil.dependentPackages(t)

        // list of Component to un/register
        val cmps = t.eAllContents.filter(Component).filter[!it.abstract].toList

        '''
            // ----------------------------------------------------------------------------------
            // ----------------------------- Global variables ------------------------------
            // ----------------------------------------------------------------------------------
            
            namespace «globalNamespaceName»{
                /// Simulator.
                ::Smp::ISimulator* simulator = nullptr;
            }
            
            // --------------------------------------------------------------------------------
            // --------------------------- Initialise Function -----------------------------
            // --------------------------------------------------------------------------------
            
            extern "C"
            {
                /// Initialise Package «t.name(useGenPattern)».
                /// @param simulator Simulator for registration of factories.
                /// @param typeRegistry Type Registry for registration of types.
                /// @return True if initialisation was successful, false otherwise.
                bool Initialise_«t.name(useGenPattern)»(
                        ::Smp::ISimulator* simulator,
                        ::Smp::Publication::ITypeRegistry* typeRegistry) {
                    // Avoid double initialisation
                    if («globalNamespaceName»::simulator) {
                        return true;
                    }
                    else if (simulator) {
                        «globalNamespaceName»::simulator = simulator;
                    }
                    else     {
                        return false;
                    }
                    
                    «FOR c : deps BEFORE '''    // Initialisation of dependent Packages
                        bool initialised_«t.name» = true;''' AFTER '''if (!initialised_«t.name») {
                                return false;
                            }
                        '''»
                        initialised_«t.name» &= Initialise_«c.name»(simulator, typeRegistry);
                    «ENDFOR»
            
            
                    «FOR v : t.sortedTypes»
                        «v.register»
                    «ENDFOR»
                    
                    «FOR cmp : cmps»
                        «cmp.registerComponent»
                    «ENDFOR»
                    
                    return true;
                }
                
                }
                
                // ---------------------------------------------------------------------------------
                // ---------------------------- Finalise Function ------------------------------
                // ---------------------------------------------------------------------------------
                
                extern "C"
                {
                    /// Finalise Package «t.name(useGenPattern)».
                    /// @return True if finalisation was successful, false otherwise.
                    bool Finalise_«t.name(useGenPattern)»() {
                        
                        if («globalNamespaceName»::simulator) {
                            «FOR cmp : cmps AFTER '''
                            
                            '''»
                                «cmp.unregisterComponent»
                            «ENDFOR»
                            «globalNamespaceName»::simulator = nullptr;
                        }
                        «IF deps.empty»
                            return true;
                        «ELSEIF deps.size == 1»
                            // Finalisation of dependent Package
                            return Finalise_«deps.get(0).name»();
                        «ELSE»
                            bool result = true;
                            // Finalisation of dependent Packages
                            «FOR c : deps»
                                result &= Finalise_«c.name»();
                            «ENDFOR»
                            
                            return result;
                        «ENDIF»
                    }
                }
        '''
    }

    override protected generateSourceBody(Catalogue t, boolean useGenPattern) {
        '''
            
            // --------------------------------------------------------------------------------
            // --------------------------- Initialise Function -----------------------------
            // --------------------------------------------------------------------------------
            
            extern "C"
            {
                /// Initialise Package «t.name».
                /// @param simulator Simulator for registration of factories.
                /// @param typeRegistry Type Registry for registration of types.
                /// @return True if initialisation was successful, false otherwise.
                bool Initialise_«t.name»(
                        ::Smp::ISimulator* simulator,
                        ::Smp::Publication::ITypeRegistry* typeRegistry) {
            
                    return Initialise_«t.name(true)»(simulator, typeRegistry) ;
                }
                
            }
                
                // ---------------------------------------------------------------------------------
                // ---------------------------- Finalise Function ------------------------------
                // ---------------------------------------------------------------------------------
                
            extern "C"
            {
                /// Finalise Package «t.name».
                /// @return True if finalisation was successful, false otherwise.
                bool Finalise_«t.name»() {
                    
                    return Finalise_«t.name(true)»() ;
                }
            }
        '''
    }

}
