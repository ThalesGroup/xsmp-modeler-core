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
package org.eclipse.xsmp.profile.xsmp_sdk.generator.cpp.type

import com.google.inject.Inject
import java.util.List
import org.eclipse.xsmp.generator.cpp.IncludeAcceptor
import org.eclipse.xsmp.generator.cpp.type.ComponentGenerator
import org.eclipse.xsmp.profile.xsmp_sdk.generator.cpp.FieldHelper
import org.eclipse.xsmp.xcatalogue.Component
import org.eclipse.xsmp.xcatalogue.Container
import org.eclipse.xsmp.xcatalogue.EntryPoint
import org.eclipse.xsmp.xcatalogue.EventSink
import org.eclipse.xsmp.xcatalogue.EventSource
import org.eclipse.xsmp.xcatalogue.Field
import org.eclipse.xsmp.xcatalogue.Model
import org.eclipse.xsmp.xcatalogue.NamedElement
import org.eclipse.xsmp.xcatalogue.NamedElementWithMembers
import org.eclipse.xsmp.xcatalogue.Operation
import org.eclipse.xsmp.xcatalogue.Parameter
import org.eclipse.xsmp.xcatalogue.Reference
import org.eclipse.xsmp.xcatalogue.SimpleType
import org.eclipse.xsmp.xcatalogue.VisibilityKind
import org.eclipse.xsmp.xcatalogue.Property
import org.eclipse.xsmp.xcatalogue.AccessKind

class XsmpSdkComponentGenerator extends ComponentGenerator {

    @Inject extension FieldHelper

    override collectIncludes(Component it, IncludeAcceptor acceptor) {
        super.collectIncludes(it, acceptor)

        acceptor.userSource("Xsmp/ComponentHelper.h")
        if (base === null)
            acceptor.userHeader("Xsmp/" + eClass.name + ".h")
        if (useDynamicInvocation) {
            acceptor.systemHeader("map")
            acceptor.userSource("Smp/IPublication.h")
            acceptor.userSource("Xsmp/Request.h")
        }
        if (member.exists[it instanceof Container])
            acceptor.userHeader("Xsmp/Composite.h")
        if (member.exists[it instanceof Reference])
            acceptor.userHeader("Xsmp/Aggregate.h")
        if (member.exists[it instanceof EventSource])
            acceptor.userHeader("Xsmp/EventProvider.h")
        if (member.exists[it instanceof EventSink])
            acceptor.userHeader("Xsmp/EventConsumer.h")
        if (member.exists[it instanceof EntryPoint])
            acceptor.userHeader("Xsmp/EntryPointPublisher.h")
        if (it instanceof Model && member.exists[it instanceof Field && (it as Field).isFailure])
            acceptor.userHeader("Xsmp/FallibleModel.h")
    }

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
                // visibility to call DoPublish/DoConfigure/DoConnect/DoDisconnect
                friend class ::Xsmp::Component::Helper;
                
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
            
                «declareMembersGen(useGenPattern, VisibilityKind.PUBLIC)»
            };
        '''
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
                // Call user DoPublish if any
                ::Xsmp::Component::Helper::Publish<«id»>(this, receiver);
            }
            
            
            
            void «name(useGenPattern)»::Configure(::Smp::Services::ILogger* logger, ::Smp::Services::ILinkRegistry* linkRegistry) {
                «IF base !== null»
                    // Call parent implementation first
                    «base»::Configure(logger, linkRegistry);
                    
                «ENDIF»
                // Call user DoConfigure if any
                ::Xsmp::Component::Helper::Configure<«id»>(this, logger, linkRegistry);
            }
            
            
            void «name(useGenPattern)»::Connect(::Smp::ISimulator* simulator) {
                «IF base !== null»
                    // Call parent implementation first
                    «base»::Connect(simulator);
                    
                «ENDIF»
                // Call user DoConnect if any
                ::Xsmp::Component::Helper::Connect<«id»>(this, simulator);
            }
            
            void «name(useGenPattern)»::Disconnect() {
                // Call user DoDisconnect if any
                ::Xsmp::Component::Helper::Disconnect<«id»>(this);
                «IF base !== null»
                    
                    // Call parent implementation last, to remove references to the Simulator and its services
                    «base»::Disconnect();
                «ENDIF»
            }
            
            
            «IF useDynamicInvocation»
                «name(useGenPattern)»::RequestHandlers «name(useGenPattern)»::requestHandlers = InitRequestHandlers();
                
                «name(useGenPattern)»::RequestHandlers «name(useGenPattern)»::InitRequestHandlers()
                {
                    RequestHandlers handlers;
                    «FOR op : member.filter(Operation).filter[isInvokable]»
                        «generateRqHandlerParam(op, useGenPattern)»
                    «ENDFOR»
                    «FOR property : member.filter(Property).filter[isInvokable]»
                        «generateRqHandlerParam(property, useGenPattern)»
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

    override protected declareGen(Component type, Field it, boolean useGenPattern) {

        if (isCdkField)
            '''
                «comment»
                «IF isMutable»mutable «ENDIF»::Xsmp::Field<«it.type.id»>«IF transient»::transient«ENDIF»«IF input»::input«ENDIF»«IF output»::output«ENDIF»«IF forcible»::forcible«ENDIF»«IF failure»::failure«ENDIF» «name»;
            '''
        else
            super.declareGen(type, it, useGenPattern)
    }

    override protected base(Component e) {
        if (e.base !== null)
            '''«e.base.id»'''
        else
            '''::Xsmp::«e.eClass.name»'''
    }

    protected override List<CharSequence> bases(Component e) {
        val bases = super.bases(e)
        if (e.member.exists[it instanceof Container])
            bases += '''public virtual ::Xsmp::Composite'''
        if (e.member.exists[it instanceof Reference])
            bases += '''public virtual ::Xsmp::Aggregate'''
        if (e.member.exists[it instanceof EventSource])
            bases += '''public virtual ::Xsmp::EventProvider'''
        if (e.member.exists[it instanceof EventSink])
            bases += '''public virtual ::Xsmp::EventConsumer'''
        if (e.member.exists[it instanceof EntryPoint])
            bases += '''public virtual ::Xsmp::EntryPointPublisher'''
        if (e.member.exists[it instanceof Field && (it as Field).isFailure])
            bases += '''public virtual ::Xsmp::FallibleModel'''
        return bases;
    }

    protected override CharSequence generateRqHandlerParam(NamedElementWithMembers container, Operation o,
        boolean useGenPattern) {

        val r = o.returnParameter

        '''
            if (handlers.find("«o.name»") == handlers.end()) {
                handlers["«o.name»"] = [](«container.name(useGenPattern)»* cmp, ::Smp::IRequest* req) {
                «FOR p : o.parameter»
                    «p.initParameter(container)»
                «ENDFOR»
                
                /// Invoke «o.name»
                «IF r !== null»auto p_«r.name» = «ENDIF»cmp->«o.name»(«FOR p : o.parameter SEPARATOR ', '»«IF p.isByPointer»&«ENDIF»p_«p.name»«ENDFOR»);
                
                «FOR p : o.parameter»
                «p.setParameter(container)»
                «ENDFOR»
                «IF r !== null»::Xsmp::Request::setReturnValue(req, «r.type.generatePrimitiveKind», p_«r.name»);«ENDIF»
                };
            }
        '''
    }

    override CharSequence initParameter(Parameter p, NamedElementWithMembers parent) {
        switch (p.direction) {
            case IN,
            case INOUT: {
                // declare and initialize the parameter
                if (p.type instanceof SimpleType)
                    '''
                        auto p_«p.name» = ::Xsmp::Request::get<«p.type.id»>(cmp, req, "«p.name»", «p.type.generatePrimitiveKind»«IF p.^default !== null», «p.^default.generateExpression()»«ENDIF»);
                    '''
                else
                    '''
                        auto p_«p.name» = ::Xsmp::Request::get<«p.type.id»>(cmp, req, "«p.name»", «p.type.uuid()»«IF p.^default !== null», «p.^default.generateExpression()»«ENDIF»);
                    '''
            }
            default: {
                // only declare the parameter
                '''
                    «p.type.id» p_«p.name» «p.^default?.generateExpression()»;
                '''
            }
        }
    }

    override CharSequence setParameter(Parameter p, NamedElement context) {

        switch (p.direction) {
            case OUT,
            case INOUT: {
                '''
                    ::Xsmp::Request::set(cmp, req, "«p.name»", «p.type.generatePrimitiveKind», p_«p.name»);
                '''
            }
            default: {
                // do nothing
            }
        }
    }

    protected override CharSequence generateRqHandlerParam(NamedElementWithMembers container, Property o,
        boolean useGenPattern) {

        '''
            «IF o.access !== AccessKind.WRITE_ONLY»
                if (handlers.find("get_«o.name»") == handlers.end()) {
                    handlers["get_«o.name»"] = [](«container.name(useGenPattern)»* component, ::Smp::IRequest* request) {
                        /// Invoke get_«o.name»
                        ::Xsmp::Request::setReturnValue(request, «o.type.generatePrimitiveKind», component->get_«o.name»());
                    };
                }
            «ENDIF»
            «IF o.access !== AccessKind.READ_ONLY»
                if (handlers.find("set_«o.name»") == handlers.end()) {
                    handlers["set_«o.name»"] = [](«container.name(useGenPattern)»* component, ::Smp::IRequest* request) {
                        /// Invoke set_«o.name»
                        component->set_«o.name»(::Xsmp::Request::get<«o.type.id»>(component, request, "«o.name»", «o.type.generatePrimitiveKind»));
                    };
                }
            «ENDIF»
        '''
    }
}
