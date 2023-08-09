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
package org.eclipse.xsmp.profile.simsat.generator.cpp.type

import org.eclipse.xsmp.generator.cpp.IncludeAcceptor
import org.eclipse.xsmp.generator.cpp.type.ComponentGenerator
import org.eclipse.xsmp.xcatalogue.Component
import org.eclipse.xsmp.xcatalogue.NamedElement
import org.eclipse.xsmp.xcatalogue.NamedElementWithMembers
import org.eclipse.xsmp.xcatalogue.Operation
import org.eclipse.xsmp.xcatalogue.Parameter
import java.util.List
import org.eclipse.xsmp.xcatalogue.Container
import org.eclipse.xsmp.xcatalogue.Reference
import org.eclipse.xsmp.xcatalogue.EventSource
import org.eclipse.xsmp.xcatalogue.EventSink
import org.eclipse.xsmp.xcatalogue.EntryPoint
import org.eclipse.xsmp.xcatalogue.VisibilityKind
import org.eclipse.xsmp.util.QualifiedNames
import org.eclipse.xsmp.xcatalogue.SimpleType
import org.eclipse.xsmp.xcatalogue.String

class SimSatComponentGenerator extends ComponentGenerator {

    override collectIncludes(Component type, IncludeAcceptor acceptor) {
        super.collectIncludes(type, acceptor)
        acceptor.userHeader("esa/ecss/smp/cdk/" + type.eClass.name + ".h")
        if (type.useDynamicInvocation) {
            acceptor.systemHeader("map")
            acceptor.userSource("Smp/IPublication.h")
            acceptor.userSource("esa/ecss/smp/cdk/Request.h")
        }

        if (type.member.exists[it instanceof Container])
            acceptor.userHeader("esa/ecss/smp/cdk/Composite.h")
        if (type.member.exists[it instanceof Reference])
            acceptor.userHeader("esa/ecss/smp/cdk/Aggregate.h")
        if (type.member.exists[it instanceof EventSource])
            acceptor.userHeader("esa/ecss/smp/cdk/EventProvider.h")
        if (type.member.exists[it instanceof EventSink])
            acceptor.userHeader("esa/ecss/smp/cdk/EventConsumer.h")
        if (type.member.exists[it instanceof EntryPoint])
            acceptor.userHeader("esa/ecss/smp/cdk/EntryPointPublisher.h")
    }

    override protected base(Component e) {
        if (e.base !== null)
            '''«e.base.id»'''
        else
            '''::esa::ecss::smp::cdk::«e.eClass.name»'''

    }

    protected override List<CharSequence> bases(Component e) {
        val bases = super.bases(e)

        if (e.member.exists[it instanceof Container])
            bases += '''public virtual ::esa::ecss::smp::cdk::Composite'''
        if (e.member.exists[it instanceof Reference])
            bases += '''public virtual ::esa::ecss::smp::cdk::Aggregate'''
        if (e.member.exists[it instanceof EventSource])
            bases += '''public virtual ::esa::ecss::smp::cdk::EventProvider'''
        if (e.member.exists[it instanceof EventSink])
            bases += '''public virtual ::esa::ecss::smp::cdk::EventConsumer'''
        if (e.member.exists[it instanceof EntryPoint])
            bases += '''public virtual ::esa::ecss::smp::cdk::EntryPointPublisher'''
        return bases;
    }

    override CharSequence constructorDeclaration(Component t, boolean useGenPattern) {
        val name = t.name(useGenPattern)
        '''
            // ------------------------------------------------------------------------------------
            // -------------------------- Constructors/Destructor --------------------------
            // ------------------------------------------------------------------------------------
            
            
            /// Constructor setting name, description and parent.
            /// @param name Name of new model instance.
            /// @param description Description of new model instance.
            /// @param parent Parent of new model instance.
            /// @param simulator The simulator.
            «name»(
                    ::Smp::String8 name,
                    ::Smp::String8 description,
                    ::Smp::IComposite *parent,
                    ::Smp::ISimulator *simulator);
            
            /// Virtual destructor to release memory.
            ~«name»() override;
        '''
    }

    override protected generateSourceBody(Component type, boolean useGenPattern) {
        if (useGenPattern)
            '''
                «type.name»::«type.name»(
                        ::Smp::String8 name,
                        ::Smp::String8 description,
                        ::Smp::IComposite* parent,
                        ::Smp::ISimulator* simulator)
                        : «type.nameGen»::«type.nameGen»(name, description, parent, simulator) {
                }
                
                «type.name»::~«type.name»() {
                }
                
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

    override protected generateSourceGenBody(Component t, boolean useGenPattern) {
        val base = t.base()
        '''
            // ------ Static fields ------
            ::esa::ecss::smp::cdk::RequestContainer<«t.name(useGenPattern)»>::Map «t.name(useGenPattern)»::requestHandlers;
            
            //--------------------------- Constructor -------------------------
            «t.name(useGenPattern)»::«t.name(useGenPattern)»(
                ::Smp::String8 name,
                ::Smp::String8 description,
                ::Smp::IComposite* parent,
                ::Smp::ISimulator* simulator) «FOR i : t.initializerList(useGenPattern) BEFORE ": \n" SEPARATOR ", "»«i»«ENDFOR»
            {
                «FOR f : t.member»
                    «t.construct(f, useGenPattern)»
                «ENDFOR»
            }
            
            /// Virtual destructor that is called by inherited classes as well.
            «t.name(useGenPattern)»::~«t.name(useGenPattern)»() {
                «FOR f : t.member»
                    «f.finalize»
                «ENDFOR»
            }
            
            void «t.name(useGenPattern)»::Publish(::Smp::IPublication* receiver) {
                «IF base !== null»
                    // Call parent class implementation first
                    «base»::Publish(receiver);
                «ENDIF»
                
                if (requestHandlers.empty()) {
                    PopulateRequestHandlers<«t.name(useGenPattern)»>(this, requestHandlers);
                }
                
                «FOR m : t.member»«m.Publish»«ENDFOR»
                
                dynamic_cast<«t.id»*>(this)->DoPublish(receiver);
            }
            
            
            void «t.name(useGenPattern)»::Configure(::Smp::Services::ILogger* logger, ::Smp::Services::ILinkRegistry* linkRegistry) {
                «IF base !== null»
                    // Call parent implementation first
                    «base»::Configure(logger, linkRegistry);
                    
                «ENDIF»
                dynamic_cast<«t.id»*>(this)->DoConfigure(logger, linkRegistry);
            }
            
            
            void «t.name(useGenPattern)»::Connect(::Smp::ISimulator* simulator) {
                «IF base !== null»
                    // Call CDK implementation first
                    «base»::Connect(simulator);
                    
                «ENDIF»
                dynamic_cast<«t.id»*>(this)->DoConnect(simulator);
            }
            
            void «t.name(useGenPattern)»::Disconnect() {
                dynamic_cast<«t.id»*>(this)->DoDisconnect();
                «IF base !== null»
                    
                    // Call parent implementation last, to remove references to the Simulator and its services
                    «base»::Disconnect();
                «ENDIF»
            }
            
            void «t.name(useGenPattern)»::DoPublish(::Smp::IPublication*) {
            }
            
            void «t.name(useGenPattern)»::DoConfigure( ::Smp::Services::ILogger*, ::Smp::Services::ILinkRegistry*){
            }
            
            void «t.name(useGenPattern)»::DoConnect( ::Smp::ISimulator*){
            }
            
            void «t.name(useGenPattern)»::DoDisconnect(){
            }
            
            «IF t.useDynamicInvocation»
                void «t.name(useGenPattern)»::Invoke(::Smp::IRequest* request) {
                    if (request == nullptr) {
                        return;
                    }
                    auto handler = requestHandlers.find(request->GetOperationName());
                    if (handler != requestHandlers.end()) {
                        handler->second->Execute(*this, request);
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
            «t.defineMembersGen(useGenPattern)»
                                
            const Smp::Uuid& «t.name(useGenPattern)»::GetUuid() const {
                return UuidProvider_«t.name»::UUID;
            }
            «t.uuidDefinition»
        '''
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
                «IF r !== null»req->SetReturnValue({«r.type.generatePrimitiveKind», p_«r.name»});«ENDIF»
                };
            }
        '''
    }

    override CharSequence initParameter(Parameter p, NamedElementWithMembers parent) {
        switch (p.direction) {
            case IN,
            case INOUT: {
                // declare and initialize the parameter
                '''
                    auto p_«p.name» = static_cast<«p.type.id»>(req->GetParameterValue(req->GetParameterIndex("«p.name»")));
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
                    req->SetParameterValue(req->GetParameterIndex("«p.name»"), p_«p.name»);
                '''
            }
            default: {
                // do nothing
            }
        }
    }

    override protected generateHeaderGenBody(Component t, boolean useGenPattern) {
        '''
            «IF useGenPattern»
                class «t.name»;
            «ENDIF»
            «t.uuidDeclaration»
            
            «t.comment»
            class «t.name(useGenPattern)»«FOR base : t.bases BEFORE ": " SEPARATOR ", "»«base»«ENDFOR»{
            
            «IF useGenPattern»
                friend class «t.id»;
            «ENDIF»
            
            public:
            «t.constructorDeclaration(useGenPattern)»
            
            // ----------------------------------------------------------------------------------
            // -------------------------------- IComponent ---------------------------------
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
            /// @param simulator Simulation Environment that hosts the model.
            ///
            virtual void Connect( ::Smp::ISimulator* simulator) override;
            
            /// Disconnect model to simulator.
            /// @throws Smp::InvalidComponentState
            virtual void Disconnect() override;
            
            /// Return the Universally Unique Identifier of the Model.
            /// @return Universally Unique Identifier of the Model.
            virtual const Smp::Uuid& GetUuid() const override;
            
            «IF t.useDynamicInvocation»
                // ----------------------------------------------------------------------------------
                // --------------------------- IDynamicInvocation ---------------------------
                // ----------------------------------------------------------------------------------
                //using RequestHandlers = std::map<std::string, std::function<void(«t.name(useGenPattern)»*, ::Smp::IRequest*)>>;
                //static RequestHandlers requestHandlers;
                //static RequestHandlers InitRequestHandlers();
                
                /// Invoke the operation for the given request.
                /// @param request Request object to invoke.
                virtual void Invoke(::Smp::IRequest* request) override;
                
            «ENDIF»
            private:
                void DoPublish(::Smp::IPublication* receiver);
                void DoConfigure( ::Smp::Services::ILogger* logger, ::Smp::Services::ILinkRegistry* linkRegistry);
                void DoConnect( ::Smp::ISimulator* simulator);
                void DoDisconnect();
                
                «t.declareMembersGen(useGenPattern, VisibilityKind.PRIVATE)»
            
            protected:
                template <typename _Type> static void PopulateRequestHandlers(_Type* bluePrint, typename ::esa::ecss::smp::cdk::RequestContainer<_Type>::Map& handlers);
                
            private:
                static ::esa::ecss::smp::cdk::RequestContainer<«t.name(useGenPattern)»>::Map requestHandlers;
            };
            
            template <typename _Type>
            void «t.name(useGenPattern)»::PopulateRequestHandlers(_Type* bluePrint, typename ::esa::ecss::smp::cdk::RequestContainer<_Type>::Map& handlers) 
            {
                typedef ::esa::ecss::smp::cdk::RequestContainer<_Type> Help;
                
                // ---- Operations ----
                «FOR op : t.member.filter(Operation).filter[it.isInvokable && it.attributeValue(QualifiedNames.Attributes_View) !== null && !it.parameter.exists[!(it instanceof SimpleType) || (it instanceof String)]]»
                    «t.generatePopulateRqHandler(op, useGenPattern)»
                «ENDFOR»
                
                ::esa::ecss::smp::cdk::«t.eClass.name»::PopulateRequestHandlers<_Type>(bluePrint, handlers);
            }
        '''
    }

    def protected CharSequence generatePopulateRqHandler(NamedElementWithMembers container, Operation o,
        boolean useGenPattern) {
        '''
            Help::template AddIfMissing<«IF o.returnParameter !== null»«o.returnParameter.type.id»«ELSE»void«ENDIF»«FOR param : o.parameter BEFORE ', ' SEPARATOR ', '»«param.type.id»«ENDFOR»>(
                handlers,
                "«o.name»",
                «IF o.returnParameter !== null»«o.returnParameter.type.generatePrimitiveKind»«ELSE»::Smp::PrimitiveTypeKind::PTK_None«ENDIF»,
                &«container.name(useGenPattern)»::«o.name»);
        '''
    }
}
