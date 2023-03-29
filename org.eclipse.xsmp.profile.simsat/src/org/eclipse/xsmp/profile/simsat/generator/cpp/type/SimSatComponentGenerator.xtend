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

class SimSatComponentGenerator extends ComponentGenerator {

    override collectIncludes(Component type, IncludeAcceptor acceptor) {
        super.collectIncludes(type, acceptor)
        acceptor.mdkHeader("esa/ecss/smp/cdk/" + type.eClass.name + ".h")
        if (type.useDynamicInvocation) {
            acceptor.systemHeader("map")
            acceptor.mdkSource("Smp/IPublication.h")
            acceptor.mdkSource("esa/ecss/smp/cdk/Request.h")
        }
    }

    override protected base(Component e) {
        if (e.base !== null)
            '''::«e.base.fqn.toString("::")»'''
        else
            '''::esa::ecss::smp::cdk::«e.eClass.name»'''

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
            	«IF r !== null»::esa::ecss::smp::cdk::Request::setReturnValue(req, «r.type.generatePrimitiveKind», p_«r.name»);«ENDIF»
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
                        auto p_«p.name» = ::esa::ecss::smp::cdk::Request::get<::«p.type.fqn.toString("::")»>(cmp, req, "«p.name»", «p.type.generatePrimitiveKind»«IF p.^default !== null», «p.^default.generateExpression(p.type, context)»«ENDIF»);
                    '''
                else
                    '''
                        auto p_«p.name» = ::esa::ecss::smp::cdk::Request::get<::«p.type.fqn.toString("::")»>(cmp, req, "«p.name»", «p.type.uuidQfn»«IF p.^default !== null», «p.^default.generateExpression(p.type, context)»«ENDIF»);
                    '''
            }
            default: {
                // only declare the parameter
                '''
                    ::«p.type.fqn.toString("::")» p_«p.name» «p.^default?.generateExpression(p.type, context)»;
                '''
            }
        }
    }

    override CharSequence setParameter(Parameter p, NamedElement context) {

        switch (p.direction) {
            case OUT,
            case INOUT: {
                '''
                    ::esa::ecss::smp::cdk::Request::set(cmp, req, "«p.name»", «p.type.generatePrimitiveKind», p_«p.name»);
                '''
            }
            default: {
                // do nothing
            }
        }
    }

}
