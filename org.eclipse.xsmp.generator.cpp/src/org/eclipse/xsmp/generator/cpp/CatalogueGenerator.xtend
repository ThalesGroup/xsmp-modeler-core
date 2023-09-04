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
 package org.eclipse.xsmp.generator.cpp

import java.util.Collections
import java.util.List
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

        acceptor.userHeader("Smp/ISimulator.h")
        acceptor.systemSource("unordered_set")

        type.eAllContents.filter(LanguageType).filter[!(it instanceof ValueReference)].forEach[acceptor.source(it)]
        type.dependentPackages().forEach[acceptor.userSource(it.name() + ".h")]
    }

    protected def boolean requireFactory(Catalogue t) {
        t.eAllContents.filter(Component).exists[!it.isAbstract]
    }

    protected def dispatch CharSequence registerComponent(Model it) {
    }

    protected def dispatch CharSequence registerComponent(Service it) {
        '''
            // Register Service «name»
            simulator->AddService( new «id»(
                                "«name»", // name
                                «description()», // description
                                simulator, // parent
                                simulator // simulator
                                ));
        '''
    }

    protected def dispatch CharSequence register(ValueType t) {
        '''
            // register «t.eClass.name» «t.name»
            «(t.eContainer as NamedElement).id»::_Register_«t.name»(typeRegistry);
        '''
    }

    protected def dispatch CharSequence register(PrimitiveType t) {
    }

    protected def dispatch CharSequence register(Structure t) {
        '''
            // register «t.eClass.name» «t.name»
            «t.id»::_Register(typeRegistry);
        '''
    }

    def CharSequence generatePkgFile(Catalogue it, boolean useGenPattern, IncludeAcceptor acceptor, Catalogue cat) {

        '''
            «copyright.getSourceText(it, useGenPattern,cat)»
            
            // -------------------------------------------------------------------------------
            // --------------------------------- Includes ----------------------------------
            // -------------------------------------------------------------------------------
            #include "«name()».h"
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
            
                        /// Global Initialise function of Package «name».
                        /// @param simulator Simulator for registration of factories.
                        /// @param typeRegistry Type Registry for registration of types.
                        /// @return True if initialisation was successful, false otherwise.
                        DLL_EXPORT bool Initialise(
                                ::Smp::ISimulator* simulator,
                                ::Smp::Publication::ITypeRegistry* typeRegistry) {
                            return Initialise_«name»(simulator, typeRegistry);
                        }
            }
            
            // -----------------------------------------------------------------------------
            // ---------------------------- Finalise Function ------------------------------
            // -----------------------------------------------------------------------------
            
            extern "C" {                        
                        /// Global Finalise function of Package «name».
                        /// @return True if finalisation was successful, false otherwise.
                        DLL_EXPORT bool Finalise(::Smp::ISimulator* simulator) {
                            return Finalise_«name»(simulator);
                        }
            }
        '''
    }

    override protected generateHeaderGenBody(Catalogue it, boolean useGenPattern) {
        '''
            // Entry points for static library
            extern "C" {
                /// Initialise Package «name».
                /// @param simulator Simulator for registration of factories.
                /// @param typeRegistry Type Registry for registration of types.
                /// @return True if initialisation was successful, false otherwise.
                bool Initialise_«name»(
                    ::Smp::ISimulator* simulator,
                    ::Smp::Publication::ITypeRegistry* typeRegistry);
            
                /// Finalise Package «name».
                /// @param simulator Optional Simulator.
                /// @return True if finalisation was successful, false otherwise.
                bool Finalise_«name»(::Smp::ISimulator* simulator = nullptr);
            }
        '''
    }

    override protected generateHeaderBody(Catalogue it) {
        '''
            // Entry points for static library
            extern "C" {
                /// Initialise Package «name».
                /// @param simulator Simulator for registration of factories.
                /// @param typeRegistry Type Registry for registration of types.
                /// @return True if initialisation was successful, false otherwise.
                bool Initialise_«name»(
                    ::Smp::ISimulator* simulator,
                    ::Smp::Publication::ITypeRegistry* typeRegistry);
            
                /// Finalise Package «name».
                /// @param simulator Optional Simulator.
                /// @return True if finalisation was successful, false otherwise.
                bool Finalise_«name»(::Smp::ISimulator* simulator = nullptr);
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

    override protected generateSourceGenBody(Catalogue it, boolean useGenPattern) {

        // list of dependent packages
        val deps = it.dependentPackages()

        // list of Component to un/register
        val cmps = eAllContents.filter(Component).filter[!isAbstract].toList

        '''
            // ----------------------------------------------------------------------------------
            // ----------------------------- Global variables ------------------------------
            // ----------------------------------------------------------------------------------
            
            namespace {
            /// Simulators set.
            std::unordered_set<::Smp::ISimulator*> simulators { };
            «IF !deps.empty»
            /// Helper function to call Finalise function
            bool CallFinalise(bool (*f)(::Smp::ISimulator*), ::Smp::ISimulator *simulator) {
                return f(simulator);
            }
            bool CallFinalise(bool (*f)(), ::Smp::ISimulator*) {
                return f();
            }
            «ENDIF»
            } // namespace
            
            #if __cplusplus >= 201703L
                #define MAYBE_UNUSED [[maybe_unused]]
            #else
                #define MAYBE_UNUSED
            #endif
            
            // --------------------------------------------------------------------------------
            // --------------------------- Initialise Function -----------------------------
            // --------------------------------------------------------------------------------
            
            extern "C"
            {
                /// Initialise Package «name(useGenPattern)».
                /// @param simulator Simulator for registration of factories.
                /// @param typeRegistry Type Registry for registration of types.
                /// @return True if initialisation was successful, false otherwise.
                bool Initialise_«name(useGenPattern)»(
                        ::Smp::ISimulator* simulator,
                        MAYBE_UNUSED ::Smp::Publication::ITypeRegistry* typeRegistry) {
                    // check simulator validity
                    if (!simulator) {
                        return false;
                    }
                    // avoid double initialisation
                    else if (!::simulators.emplace(simulator).second) {
                        return true;
                    }
                    
                    «FOR c : deps BEFORE '''    // Initialisation of dependent Package(s)
                        bool initialised_«name» = true;''' AFTER '''if (!initialised_«name») {
                                return false;
                            }
                        '''»
                        initialised_«name» &= Initialise_«c.name»(simulator, typeRegistry);
                    «ENDFOR»
            
                    «FOR v : sortedTypes»
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
                    /// Finalise Package «name(useGenPattern)».
                    /// @param simulator Simulator.
                    /// @return True if finalisation was successful, false otherwise.
                    bool Finalise_«name(useGenPattern)»(::Smp::ISimulator* simulator) {
                        // backward compatibility
                        if (!simulator) {
                            ::simulators.clear();
                        }
                        // avoid double finalisation
                        else if (!::simulators.erase(simulator)) {
                            return true;
                        }
                        
                        «IF deps.empty»
                            return true;
                        «ELSEIF deps.size == 1»
                            // Finalisation of dependent Package
                            return CallFinalise(Finalise_«deps.get(0).name», simulator);
                        «ELSE»
                            bool result = true;
                            // Finalisation of dependent Packages
                            «FOR c : deps»
                                result &= CallFinalise(Finalise_«c.name», simulator);
                            «ENDFOR»
                            
                            return result;
                        «ENDIF»
                    }
                }
        '''
    }

    override protected generateSourceBody(Catalogue it, boolean useGenPattern) {
        '''
            
            // --------------------------------------------------------------------------------
            // --------------------------- Initialise Function -----------------------------
            // --------------------------------------------------------------------------------
            
            extern "C"
            {
                /// Initialise Package «name».
                /// @param simulator Simulator for registration of factories.
                /// @param typeRegistry Type Registry for registration of types.
                /// @return True if initialisation was successful, false otherwise.
                bool Initialise_«name»(
                        ::Smp::ISimulator* simulator,
                        ::Smp::Publication::ITypeRegistry* typeRegistry) {
            
                    return Initialise_«nameGen»(simulator, typeRegistry) ;
                }
                
            }
                
                // ---------------------------------------------------------------------------------
                // ---------------------------- Finalise Function ------------------------------
                // ---------------------------------------------------------------------------------
                
            extern "C"
            {
                /// Finalise Package «name».
                /// @param simulator Simulator.
                /// @return True if finalisation was successful, false otherwise.
                bool Finalise_«name»(::Smp::ISimulator* simulator) {
            
                    return Finalise_«nameGen»(simulator);
                }
            }
        '''
    }

}
