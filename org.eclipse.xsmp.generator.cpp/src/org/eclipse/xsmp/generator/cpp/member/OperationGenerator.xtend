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
package org.eclipse.xsmp.generator.cpp.member

import org.eclipse.xsmp.generator.cpp.IncludeAcceptor
import org.eclipse.xsmp.model.xsmp.NamedElementWithMembers
import org.eclipse.xsmp.model.xsmp.Operation
import org.eclipse.xsmp.model.xsmp.Parameter
import org.eclipse.xsmp.model.xsmp.ParameterDirectionKind
import org.eclipse.xsmp.model.xsmp.ReferenceType
import org.eclipse.xsmp.model.xsmp.Structure
import org.eclipse.xsmp.model.xsmp.Type

class OperationGenerator extends AbstractMemberGenerator<Operation> {

    def String name(Operation o) {

        switch (o.operatorKind) {
            case NONE: {
                o.name
            }
            case ADD: {
                "operator+="
            }
            case ASSIGN: {
                "operator="
            }
            case DIFFERENCE,
            case NEGATIVE: {
                "operator-"
            }
            case DIVIDE: {
                "operator/="
            }
            case EQUAL: {
                "operator=="
            }
            case GREATER: {
                "operator>"
            }
            case INDEXER: {
                "operator[]"
            }
            case LESS: {
                "operator<"
            }
            case MODULE: {
                "operator%"
            }
            case MULTIPLY: {
                "operator*="
            }
            case NOT_EQUAL: {
                "operator!="
            }
            case NOT_GREATER: {
                "operator<="
            }
            case NOT_LESS: {
                "operator>="
            }
            case POSITIVE,
            case SUM: {
                "operator+"
            }
            case PRODUCT: {
                "operator*"
            }
            case QUOTIENT: {
                "operator/"
            }
            case REMAINDER: {
                "operator%="
            }
            case SUBTRACT: {
                "operator-="
            }
        }
    }

    def returnType(Operation it) {
        if (returnParameter === null)
            '''void '''
        else
            '''«returnParameter.type()» '''
    }

    override declare(NamedElementWithMembers parent, Operation it) {
        if (isVirtual && !isAbstract) // override virtual methods
            '''
                «comment»
                «returnType()»«name()» («FOR p : parameter SEPARATOR '\n, '»«p.generateParameter(true)»«ENDFOR»)«IF isConst» const«ENDIF» override;
            '''

    }

    override define(NamedElementWithMembers parent, Operation it, boolean useGenPattern) {

        if (isConstructor) // constructor declared in gen folder
        // TODO call base class constructor if any ?
            '''
                «parent.name(useGenPattern)»::«parent.name(useGenPattern)» («FOR p : parameter SEPARATOR '\n, '»«p.generateParameter(false)»«ENDFOR»)
                {
                }
            '''
        else if (isStatic || !isVirtual) // static or not virtual method declared in gen folder
            '''
                «returnType()»«parent.name(useGenPattern)»::«name()» («FOR p : parameter SEPARATOR '\n, '»«p.generateParameter(false)»«ENDFOR»)«IF isConst» const«ENDIF»
                {
                    «IF returnParameter!==null»
                        «IF returnParameter.type === parent»
                            return «IF !returnParameter.isByPointer»*«ENDIF»this;
                        «ELSE»
                            «returnType()» ret {};
                            return ret;
                        «ENDIF» 
                    «ENDIF» 
                }
            '''
        else if (!isAbstract) // override virtual method
            '''
                «returnType()» «parent.name»::«name()» («FOR p : parameter SEPARATOR '\n, '»«p.generateParameter(false)»«ENDFOR»)«IF isConst» const«ENDIF»
                {
                    «IF returnParameter!==null»
                        «IF returnParameter.type === parent»
                            return «IF !returnParameter.isByPointer»*«ENDIF»this;
                        «ELSE»
                            «returnType()» ret {};
                            return ret;
                        «ENDIF» 
                    «ENDIF» 
                }
            '''

    }

    override declareGen(NamedElementWithMembers parent, Operation it, boolean useGenPattern) {

        if (isConstructor) // a constructor
            '''
                «comment»
                «parent.name(useGenPattern)»(«FOR p : parameter SEPARATOR ','»«p.generateParameter(true)»«ENDFOR»);
            '''
        else if (isStatic) // a static method
            '''
                «comment»
                static «returnType()» «name()»(«FOR p : parameter SEPARATOR ','»«p.generateParameter(true)»«ENDFOR»);
            '''
        else if (isVirtual) // a virtual method
            '''
                «comment»
                virtual «returnType()» «name()»(«FOR p : parameter SEPARATOR ','»«p.generateParameter(true)»«ENDFOR»)«IF isConst» const«ENDIF»«IF isAbstract || useGenPattern»=0«ENDIF»;
            '''
        else
            '''
                «comment»
                «returnType()» «name()»(«FOR p : parameter SEPARATOR ','»«p.generateParameter(true)»«ENDFOR»)«IF isConst» const«ENDIF»;
            '''

    }

    override collectIncludes(Operation it, IncludeAcceptor acceptor) {
        super.collectIncludes(it, acceptor)
        // if a parameter is passed by value, add it to includes
        // In case of pointer or reference, use a forward declaration
        for (m : parameter) {
            if ( /*!m.isByPointer&& !m.isByReference &&*/ !m.type.isForwardable)
                acceptor.include(m.type)
            else
                acceptor.forward(m.type)
            m.^default?.include(acceptor)
        }
        if (returnParameter !== null) {
            if (!returnParameter.type.isForwardable)
                acceptor.include(returnParameter.type)
            else
                acceptor.forward(returnParameter.type)
        }
    }

    override Publish(Operation it) {
        if (isInvokable) {
            val r = returnParameter
            '''
                {
                    // Publish operation «name»
                    «IF !parameter.empty || r !== null »
                        ::Smp::Publication::IPublishOperation* operation = 
                    «ENDIF»
                    receiver->PublishOperation("«name»", «description()», «viewKindCpp»);
                    «FOR p : parameter»
                        operation->PublishParameter("«p.name»", «p.description()», «p.type.uuid()», «p.direction.generatePDK»);
                    «ENDFOR»
                    «IF r !== null »
                        operation->PublishParameter("«r.name»", «r.description()», «r.type.uuid()», «r.direction.generatePDK»);
                    «ENDIF»
                }
            '''
        }
    }

    protected def CharSequence type(Parameter it) {

        '''«IF isConst»const «ENDIF»«type.id» «IF isByPointer»*«ENDIF»«IF isByReference»&«ENDIF»'''

    }

    protected def CharSequence generateParameter(Parameter it, boolean withDefault) {
        '''«type()»«name»«IF ^default !==null && withDefault» = «^default.doGenerateExpression()»«ENDIF»'''
    }

    /**
     * By default types are not forwardable
     */
    private def dispatch boolean isForwardable(Type t) {
        false
    }

    /**
     * Struct, class and exception are forwardable
     */
    private def dispatch boolean isForwardable(Structure t) {
        true
    }

    /**
     * Interface, model and services are forwardable
     */
    private def dispatch boolean isForwardable(ReferenceType t) {
        true
    }

    def CharSequence generatePDK(ParameterDirectionKind t) {
        switch (t) {
            case ParameterDirectionKind.IN: '''Smp::Publication::ParameterDirectionKind::PDK_In'''
            case ParameterDirectionKind.OUT: '''Smp::Publication::ParameterDirectionKind::PDK_Out'''
            case ParameterDirectionKind.INOUT: '''Smp::Publication::ParameterDirectionKind::PDK_InOut'''
            case ParameterDirectionKind.RETURN: '''Smp::Publication::ParameterDirectionKind::PDK_Return'''
        }
    }



}
