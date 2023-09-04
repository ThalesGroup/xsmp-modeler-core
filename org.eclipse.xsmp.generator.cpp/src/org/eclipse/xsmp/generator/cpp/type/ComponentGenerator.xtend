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
package org.eclipse.xsmp.generator.cpp.type

import java.util.List
import org.eclipse.xsmp.generator.cpp.IncludeAcceptor
import org.eclipse.xsmp.xcatalogue.Component
import org.eclipse.xsmp.xcatalogue.NamedElement
import org.eclipse.xsmp.xcatalogue.NamedElementWithMembers
import org.eclipse.xsmp.xcatalogue.Operation
import org.eclipse.xsmp.xcatalogue.Parameter
import org.eclipse.xsmp.xcatalogue.VisibilityKind

abstract class ComponentGenerator extends AbstractTypeWithMembersGenerator<Component> {

    override protected generateHeaderBody(Component it) {
        '''
            «comment»
            class «name»: public «nameGen» {
            public:
                /// Re-use parent constructors
                using «nameGen»::«nameGen»;
                
                /// Virtual destructor to release memory.
                ~«name»() noexcept override = default;
                
            private:
                // «nameGen» call DoPublish/DoConfigure/DoConnect/DoDisconnect
                friend class «idGen»;
                
                /// Publish fields, operations and properties of the model.
                /// @param receiver Publication receiver.
                void DoPublish(::Smp::IPublication* receiver);
                
                /// Request for configuration.
                /// @param logger Logger to use for log messages during Configure().
                /// @param linkRegistry Link Registry to use for registration of
                ///         links created during Configure() or later.
                void DoConfigure( ::Smp::Services::ILogger* logger, ::Smp::Services::ILinkRegistry* linkRegistry);
                
                /// Connect model to simulator.
                /// @param simulator Simulation Environment that hosts the model.
                ///
                void DoConnect( ::Smp::ISimulator* simulator);
                
                /// Disconnect model to simulator.
                /// @throws Smp::InvalidComponentState
                void DoDisconnect();
            
                «declareMembers(VisibilityKind.PRIVATE)»
            };
        '''
    }

    def CharSequence constructorDeclaration(Component it, boolean useGenPattern) {
        val name = name(useGenPattern)
        '''
            // ------------------------------------------------------------------------------------
            // -------------------------- Constructors/Destructor --------------------------
            // ------------------------------------------------------------------------------------
            
            
            /// Constructor setting name, description, parent and simulator.
            /// @param name Name of new model instance.
            /// @param description Description of new model instance.
            /// @param parent Parent of new model instance.
            /// @param simulator The simulator instance.
            «name»(
                    ::Smp::String8 name,
                    ::Smp::String8 description,
                    ::Smp::IObject* parent,
                    ::Smp::ISimulator* simulator);
            /// deleted copy constructor
            «name»(const «name»&) = delete;
            /// deleted move constructor
            «name»(«name»&&) = delete;
            /// deleted copy assignment
            «name»& operator=(const «name»&) = delete;
            /// deleted move assignment
            «name»& operator=(«name»&&) = delete;
            
            /// Virtual destructor to release memory.
            ~«name»() override;
        '''
    }

    override protected generateSourceBody(Component type, boolean useGenPattern) {
        if (useGenPattern)
            '''
                void «type.name»::DoPublish( ::Smp::IPublication* receiver) {
                }
                
                void «type.name»::DoConfigure(::Smp::Services::ILogger* logger, ::Smp::Services::ILinkRegistry* linkRegistry) {
                }
                
                
                void «type.name»::DoConnect(::Smp::ISimulator* simulator) {
                }
                
                void «type.name»::DoDisconnect() {
                }
                
                «type.defineMembers(useGenPattern)»
            '''
        else
            type.defineMembers(useGenPattern)
    }

    override protected generateHeaderGenBody(Component it, boolean useGenPattern) {
        '''
            «IF useGenPattern»
                // forward declaration of user class
                class «name»;
            «ENDIF»
            «uuidDeclaration»
            
            «comment»
            class «name(useGenPattern)»«FOR base : bases BEFORE ": " SEPARATOR ", "»«base»«ENDFOR»{
            
            «IF useGenPattern»
                friend class «id»;
            «ENDIF»
            
            public:
            «constructorDeclaration(useGenPattern)»
            
            // ----------------------------------------------------------------------------------
            // -------------------------------- IComponent ---------------------------------
            // ----------------------------------------------------------------------------------
            
            /// Publish fields, operations and properties of the model.
            /// @param receiver Publication receiver.
            void Publish(::Smp::IPublication* receiver) override;
            
            /// Request for configuration.
            /// @param logger Logger to use for log messages during Configure().
            /// @param linkRegistry Link Registry to use for registration of
            ///         links created during Configure() or later.
            void Configure( ::Smp::Services::ILogger* logger, ::Smp::Services::ILinkRegistry* linkRegistry) override;
            
            /// Connect model to simulator.
            /// @param simulator Simulation Environment that hosts the model.
            ///
            void Connect( ::Smp::ISimulator* simulator) override;
            
            /// Disconnect model to simulator.
            /// @throws Smp::InvalidComponentState
            void Disconnect() override;
            
            /// Return the Universally Unique Identifier of the Model.
            /// @return Universally Unique Identifier of the Model.
            const Smp::Uuid& GetUuid() const override;
            
            «IF useDynamicInvocation»
                // ----------------------------------------------------------------------------------
                // --------------------------- IDynamicInvocation ---------------------------
                // ----------------------------------------------------------------------------------
                using RequestHandlers = std::map<std::string, std::function<void(«name(useGenPattern)»*, ::Smp::IRequest*)>>;
                static RequestHandlers requestHandlers;
                static RequestHandlers InitRequestHandlers();
                
                /// Invoke the operation for the given request.
                /// @param request Request object to invoke.
                void Invoke(::Smp::IRequest* request) override;
                
            «ENDIF»
            private:
                void DoPublish(::Smp::IPublication* receiver);
                void DoConfigure( ::Smp::Services::ILogger* logger, ::Smp::Services::ILinkRegistry* linkRegistry);
                void DoConnect( ::Smp::ISimulator* simulator);
                void DoDisconnect();
                
                «declareMembersGen(useGenPattern, VisibilityKind.PRIVATE)»
            };
        '''
    }

    protected def CharSequence initializeBase(Component c) {
        val base = c.base()
        if (base !== null)
            '''
                // Base class initialization
                «base»(name, description, parent, simulator)
            '''
    }

    protected override List<CharSequence> initializerList(Component c, boolean useGenPattern) {
        var List<CharSequence> list = newArrayList

        val base = c.initializeBase
        if (base !== null)
            list += base

        list += super.initializerList(c, useGenPattern)

        return list
    }

    override protected generateSourceGenBody(Component it, boolean useGenPattern) {
        val base = base()
        '''
            //--------------------------- Constructor -------------------------
            «name(useGenPattern)»::«name(useGenPattern)»(
                ::Smp::String8 name,
                ::Smp::String8 description,
                ::Smp::IObject* parent,
                ::Smp::ISimulator* simulator) «FOR i : initializerList(useGenPattern) BEFORE ": \n" SEPARATOR ", "»«i»«ENDFOR»
            {
                «FOR f : member»
                    «construct(f, useGenPattern)»
                «ENDFOR»
            }
            
            /// Virtual destructor that is called by inherited classes as well.
            «name(useGenPattern)»::~«name(useGenPattern)»() {
                «FOR f : member»
                    «f.finalize»
                «ENDFOR»
            }
            
            void «name(useGenPattern)»::Publish(::Smp::IPublication* receiver) {
                «IF base !== null»
                    // Call parent class implementation first
                    «base»::Publish(receiver);
                «ENDIF»
                
                «FOR m : member»«m.Publish»«ENDFOR»
                
                dynamic_cast<«id»*>(this)->DoPublish(receiver);
            }
            
            
            
            void «name(useGenPattern)»::Configure(::Smp::Services::ILogger* logger, ::Smp::Services::ILinkRegistry* linkRegistry) {
                «IF base !== null»
                    // Call parent implementation first
                    «base»::Configure(logger, linkRegistry);
                    
                «ENDIF»
                dynamic_cast<«id»*>(this)->DoConfigure(logger, linkRegistry);
            }
            
            
            void «name(useGenPattern)»::Connect(::Smp::ISimulator* simulator) {
                «IF base !== null»
                    // Call parent implementation first
                    «base»::Connect(simulator);
                    
                «ENDIF»
                dynamic_cast<«id»*>(this)->DoConnect(simulator);
            }
            
            void «name(useGenPattern)»::Disconnect() {
                dynamic_cast<«id»*>(this)->DoDisconnect();
                «IF base !== null»
                    
                    // Call parent implementation last, to remove references to the Simulator and its services
                    «base»::Disconnect();
                «ENDIF»
            }
            
            void «name(useGenPattern)»::DoPublish(::Smp::IPublication*) {
            }
            
            void «name(useGenPattern)»::DoConfigure( ::Smp::Services::ILogger*, ::Smp::Services::ILinkRegistry*){
            }
            
            void «name(useGenPattern)»::DoConnect( ::Smp::ISimulator*){
            }
            
            void «name(useGenPattern)»::DoDisconnect(){
            }
            
            «IF useDynamicInvocation»
                «name(useGenPattern)»::RequestHandlers «name(useGenPattern)»::requestHandlers = InitRequestHandlers();
                
                «name(useGenPattern)»::RequestHandlers «name(useGenPattern)»::InitRequestHandlers()
                {
                    RequestHandlers handlers;
                    «FOR op : member.filter(Operation).filter[isInvokable]»
                        «generateRqHandlerParam(op, useGenPattern)»
                    «ENDFOR»
                    return handlers;
                }
                
                void «name(useGenPattern)»::Invoke(::Smp::IRequest* request) {
                    if (request == nullptr) {
                        return;
                    }
                    auto handler = requestHandlers.find(request->GetOperationName());
                    if (handler != requestHandlers.end()) {
                        handler->second(this, request);
                    }
                    else {
                        «IF base !== null»
                            // pass the request down to the base model
                            «base»::Invoke(request);
                        «ELSE»
                            «InvokeFallback»
                        «ENDIF»
                    }
                }
                
            «ENDIF»
            const Smp::Uuid& «name(useGenPattern)»::GetUuid() const {
                return Uuid_«name»;
            }
            «uuidDefinition»
            «defineMembersGen(useGenPattern)»
        '''
    }

    protected def InvokeFallback() {
        '''
            // request does not match any operation provided by this model
            //TODO throw InvalidOperationName(request->GetOperationName());
        '''
    }

    protected def useDynamicInvocation(Component e) {
        return e.member.exists[it instanceof Operation && (it as Operation).isInvokable]
    }

    override collectIncludes(Component type, IncludeAcceptor acceptor) {
        super.collectIncludes(type, acceptor)

        if (type.base !== null)
            acceptor.include(type.base)

        for (i : type.interface)
            acceptor.include(i)

        if (type.useDynamicInvocation) {
            acceptor.systemHeader("functional")
            acceptor.userHeader("Smp/Publication/IPublishOperation.h")
            acceptor.userHeader("Smp/IRequest.h")
        }
    }

    override protected collectIncludes(IncludeAcceptor acceptor) {
        super.collectIncludes(acceptor)
        acceptor.userHeader("Smp/ISimulator.h")
        acceptor.userSource("Smp/IPublication.h")
        acceptor.systemHeader("type_traits")
    }

    protected def CharSequence base(Component it) {
        if (base !== null)
            return '''«base.id»'''

    }

    protected def List<CharSequence> bases(Component it) {
        val List<CharSequence> bases = newArrayList
        val base = base()

        if (base !== null)
            bases += '''public «base»'''
        bases.addAll(interface.map['''public virtual «id»'''])

        return bases;
    }

    def CharSequence initParameter(Parameter p, NamedElementWithMembers parent) {
        '''
            «p.type.id» p_«p.name»«p.^default?.generateExpression()»;
        '''
    }

    def CharSequence setParameter(Parameter p, NamedElement context) {
    }

    protected def CharSequence generateRqHandlerParam(NamedElementWithMembers container, Operation o,
        boolean useGenPattern) {

        val r = o.returnParameter

        '''
            if (handlers.find("«o.name»") == handlers.end()) {
                handlers["«o.name»"] = [](«container.name(useGenPattern)»* component, ::Smp::IRequest* request) {
                «FOR p : o.parameter»
                    «p.initParameter(container)»
                «ENDFOR»
                
                /// Invoke «o.name»
                «IF r !== null»«r.type.id» p_«r.name» = «ENDIF»component->«o.name»(«FOR p : o.parameter SEPARATOR ', '»«IF p.isByPointer»&«ENDIF»p_«p.name»«ENDFOR»);
                
                «FOR p : o.parameter»
                «p.setParameter(container)»
                «ENDFOR»
                «IF r !== null»request->SetReturnValue({«r.type.generatePrimitiveKind», p_«r.name»});«ENDIF»
                };
            }
        '''
    }
}
