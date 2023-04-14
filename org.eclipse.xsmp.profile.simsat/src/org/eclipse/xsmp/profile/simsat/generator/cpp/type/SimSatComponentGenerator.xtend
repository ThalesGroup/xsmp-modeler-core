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
import org.eclipse.xsmp.xcatalogue.SimpleType
import java.util.List
import org.eclipse.xsmp.xcatalogue.Container
import org.eclipse.xsmp.xcatalogue.Reference
import org.eclipse.xsmp.xcatalogue.EventSource
import org.eclipse.xsmp.xcatalogue.EventSink
import org.eclipse.xsmp.xcatalogue.EntryPoint

class SimSatComponentGenerator extends ComponentGenerator {

    override collectIncludes(Component type, IncludeAcceptor acceptor) {
        super.collectIncludes(type, acceptor)
        acceptor.mdkHeader("esa/ecss/smp/cdk/" + type.eClass.name + ".h")
        if (type.useDynamicInvocation) {
            acceptor.systemHeader("map")
            acceptor.mdkSource("Smp/IPublication.h")
            acceptor.mdkSource("esa/ecss/smp/cdk/Request.h")
        }

        if (type.member.exists[it instanceof Container])
            acceptor.mdkHeader("esa/ecss/smp/cdk/Composite.h")
        if (type.member.exists[it instanceof Reference])
            acceptor.mdkHeader("esa/ecss/smp/cdk/Aggregate.h")
        // if (type.useDynamicInvocation)
        // acceptor.mdkSource("esa/ecss/smp/cdk/Composite.h")
        if (type.member.exists[it instanceof EventSource])
            acceptor.mdkHeader("esa/ecss/smp/cdk/EventProvider.h")
        if (type.member.exists[it instanceof EventSink])
            acceptor.mdkHeader("esa/ecss/smp/cdk/EventConsumer.h")
        if (type.member.exists[it instanceof EntryPoint])
            acceptor.mdkHeader("esa/ecss/smp/cdk/EntryPointPublisher.h")
    }

    override protected base(Component e) {
        if (e.base !== null)
            '''::«e.base.fqn.toString("::")»'''
        else
            '''::esa::ecss::smp::cdk::«e.eClass.name»'''

    }

    protected override CharSequence initializeBase(Component c) {
        val base = c.base()
        if (base !== null)
            '''
                // Base class initialization
                «base»(name, description, parent, simulator)
            '''
    }

    protected override List<CharSequence> bases(Component e) {
        val bases = super.bases(e)

        if (e.member.exists[it instanceof Container])
            bases += '''public virtual ::esa::ecss::smp::cdk::Composite'''
        if (e.member.exists[it instanceof Reference])
            bases += '''public virtual ::esa::ecss::smp::cdk::Aggregate'''
        // if (e.useDynamicInvocation)
        // bases += '''::WithOperations'''
        if (e.member.exists[it instanceof EventSource])
            bases += '''public virtual ::esa::ecss::smp::cdk::EventProvider'''
        if (e.member.exists[it instanceof EventSink])
            bases += '''public virtual ::esa::ecss::smp::cdk::EventConsumer'''
        if (e.member.exists[it instanceof EntryPoint])
            bases += '''public virtual ::esa::ecss::smp::cdk::EntryPointPublisher'''
        // if (e instanceof Model && e.member.filter(Field).exists[it.isFailureField])
        // bases += '''::WithFailures'''
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
        '''
            «type.name»::«type.name»(
                    ::Smp::String8 name,
                    ::Smp::String8 description,
                    ::Smp::IComposite* parent,
                    ::Smp::ISimulator* simulator)
                    : «type.genName»::«type.genName»(name, description, parent, simulator) {
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
    }

    override protected generateSourceGenBody(Component t, boolean useGenPattern) {
        val base = t.base()
        '''
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
            
                        «FOR m : t.member»«m.Publish»«ENDFOR»
                        
                        dynamic_cast<«t.fqn(false).toString("::")»*>(this)->DoPublish(receiver);
                    }
                    
            
            
                    void «t.name(useGenPattern)»::Configure(::Smp::Services::ILogger* logger, ::Smp::Services::ILinkRegistry* linkRegistry) {
                        «IF base !== null»
                            // Call parent implementation first
                            «base»::Configure(logger, linkRegistry);
                            
                        «ENDIF»
                        dynamic_cast<«t.fqn(false).toString("::")»*>(this)->DoConfigure(logger, linkRegistry);
                    }
                    
                    
                    void «t.name(useGenPattern)»::Connect(::Smp::ISimulator* simulator) {
                        «IF base !== null»
                            // Call CDK implementation first
                            «base»::Connect(simulator);
                            
                        «ENDIF»
                        dynamic_cast<«t.fqn(false).toString("::")»*>(this)->DoConnect(simulator);
                    }
                    
                    void «t.name(useGenPattern)»::Disconnect() {
                        dynamic_cast<«t.fqn(false).toString("::")»*>(this)->DoDisconnect();
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
                        «t.name(useGenPattern)»::RequestHandlers «t.name(useGenPattern)»::requestHandlers = InitRequestHandlers();
                        
                        «t.name(useGenPattern)»::RequestHandlers «t.name(useGenPattern)»::InitRequestHandlers()
                        {
                            RequestHandlers handlers;
                            «FOR op : t.member.filter(Operation).filter[it.isInvokable]»
                                «t.generateRqHandlerParam(op, useGenPattern)»
                            «ENDFOR»
                            return handlers;
                        }
                        
                        void «t.name(useGenPattern)»::Invoke(::Smp::IRequest* request) {
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
                //«IF r !== null»auto p_«r.name» = «ENDIF»cmp->«o.name»(«FOR p : o.parameter SEPARATOR ', '»«IF p.isByPointer»&«ENDIF»p_«p.name»«ENDFOR»);
                
                «FOR p : o.parameter»
                «p.setParameter(container)»
                «ENDFOR»
                «IF r !== null»//::esa::ecss::smp::cdk::Request::setReturnValue(req, «r.type.generatePrimitiveKind», p_«r.name»);«ENDIF»
                };
            }
        '''
    }

    override CharSequence initParameter(Parameter p, NamedElement context) {
        switch (p.direction) {
            case IN,
            case INOUT: {
                // declare and initialize the parameter
                if (p.type instanceof SimpleType)
                    '''
                        //auto p_«p.name» = ::esa::ecss::smp::cdk::Request::get<::«p.type.fqn.toString("::")»>(cmp, req, "«p.name»", «p.type.generatePrimitiveKind»«IF p.^default !== null», «p.^default.generateExpression(p.type, context)»«ENDIF»);
                    '''
                else
                    '''
                        //auto p_«p.name» = ::esa::ecss::smp::cdk::Request::get<::«p.type.fqn.toString("::")»>(cmp, req, "«p.name»", «p.type.uuidQfn»«IF p.^default !== null», «p.^default.generateExpression(p.type, context)»«ENDIF»);
                    '''
            }
            default: {
                // only declare the parameter
                '''
                    //::«p.type.fqn.toString("::")» p_«p.name» «p.^default?.generateExpression(p.type, context)»;
                '''
            }
        }
    }

    override CharSequence setParameter(Parameter p, NamedElement context) {

        switch (p.direction) {
            case OUT,
            case INOUT: {
                '''
                    //::esa::ecss::smp::cdk::Request::set(cmp, req, "«p.name»", «p.type.generatePrimitiveKind», p_«p.name»);
                '''
            }
            default: {
                // do nothing
            }
        }
    }

}
