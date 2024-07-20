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
import org.eclipse.xsmp.model.xsmp.Property

class PropertyGenerator extends AbstractMemberGenerator<Property> {

    protected def type(Property it) {
        '''«type.id» «IF isByPointer»*«ENDIF»«IF isByReference»&«ENDIF»'''
    }

    protected def declareGetter(NamedElementWithMembers parent, Property it) {
        '''
            «type()»get_«name»()«IF isConstGetter» const«ENDIF» override;
        '''
    }

    protected def declareSetter(NamedElementWithMembers parent, Property it) {
        '''
            void set_«name»(«type()»«name»)«IF isConst» const«ENDIF» override;
        '''
    }

    override declare(NamedElementWithMembers parent, Property it) {
        if (attachedField === null && isVirtual && !isAbstract) {

            switch (access) {
                case READ_ONLY: {
                    declareGetter(parent, it)
                }
                case READ_WRITE: {
                    '''
                        «declareGetter(parent, it)»
                        «declareSetter(parent, it)»
                    '''
                }
                case WRITE_ONLY: {
                    declareSetter(parent, it)
                }
            }

        }
    }

    protected def defineGetter(NamedElementWithMembers parent, Property it, boolean useGenPattern) {
        '''
            «type()»«parent.name(useGenPattern)»::get_«name»()«IF isConstGetter» const«ENDIF»
            {
                «type()»ret {};
                return ret;
               
            }
        '''
    }

    protected def defineSetter(NamedElementWithMembers parent, Property it, boolean useGenPattern) {
        '''
            void «parent.name(useGenPattern)»::set_«name»(«type()»value)«IF isConst» const«ENDIF»
            {
                 // TODO
            }
        '''
    }

    override define(NamedElementWithMembers parent, Property it, boolean useGenPattern) {

        if (attachedField === null && !isAbstract) {
            val gen = useGenPattern && !isVirtual

            switch (access) {
                case READ_ONLY: {
                    defineGetter(parent, it, gen)
                }
                case READ_WRITE: {
                    '''
                        «defineGetter(parent, it, gen)»
                        «defineSetter(parent, it, gen)»
                    '''
                }
                case WRITE_ONLY: {
                    defineSetter(parent, it, gen)
                }
            }
        }
    }

    protected def declareGetterGen(NamedElementWithMembers parent, Property it, boolean useGenPattern) {
        '''
            /// Get «name».
            «comment»
            /// @return Current value of property «name».
            «IF isVirtual»virtual «ENDIF»«IF isStatic»static «ENDIF»«type()»get_«name»()«IF isConstGetter» const«ENDIF»«IF isAbstract || (isVirtual && useGenPattern && attachedField === null)»=0«ENDIF»;
        '''
    }

    protected def declareSetterGen(NamedElementWithMembers parent, Property it, boolean useGenPattern) {
        '''
            /// Set «name».
            «comment»
            /// @param value New value of property «name» to set.
            «IF isVirtual»virtual «ENDIF»«IF isStatic»static «ENDIF»void set_«name»(«type()»value)«IF isConst» const«ENDIF»«IF isAbstract || (isVirtual && useGenPattern && attachedField === null)»=0«ENDIF»;
        '''
    }

    override declareGen(NamedElementWithMembers parent, Property it, boolean useGenPattern) {

        switch (access) {
            case READ_ONLY: {
                declareGetterGen(parent, it, useGenPattern)
            }
            case READ_WRITE: {
                '''
                    «declareGetterGen(parent, it, useGenPattern)»
                    «declareSetterGen(parent, it, useGenPattern)»
                '''
            }
            case WRITE_ONLY: {
                declareSetterGen(parent, it, useGenPattern)
            }
        }
    }

    protected def defineGetterGen(NamedElementWithMembers parent, Property it, boolean useGenPattern) {
        '''
            «type()»«parent.name(useGenPattern)»::get_«name»()«IF isConstGetter» const«ENDIF»
            {
                return «IF it.static»«parent.name(useGenPattern)»::«ELSE»this->«ENDIF»«attachedField.name»;
            }
        '''
    }

    protected def defineSetterGen(NamedElementWithMembers parent, Property it, boolean useGenPattern) {
        '''
            void «parent.name(useGenPattern)»::set_«name»(«type()»value)«IF isConst» const«ENDIF»
            {
                «IF it.static»«parent.name(useGenPattern)»::«ELSE»this->«ENDIF»«attachedField.name» = value;
            }
        '''
    }

    override defineGen(NamedElementWithMembers parent, Property it, boolean useGenPattern) {
        if (attachedField !== null) {

            switch (access) {
                case READ_ONLY: {
                    defineGetterGen(parent, it, useGenPattern)
                }
                case READ_WRITE: {
                    '''
                        «defineGetterGen(parent, it, useGenPattern)»
                        «defineSetterGen(parent, it, useGenPattern)»
                    '''
                }
                case WRITE_ONLY: {
                    defineSetterGen(parent, it, useGenPattern)
                }
            }
        }
    }

    override collectIncludes(Property element, IncludeAcceptor acceptor) {
        super.collectIncludes(element, acceptor)
        acceptor.include(element.type);
    }

    override Publish(Property it) {
        if (it.isInvokable)
            '''
                // Publish Property «name»
                receiver->PublishProperty("«name»", «description()», «type.uuid()», «accessKind», «viewKindCpp»);
            '''
    }

    protected def CharSequence accessKind(Property it) {
        switch (access) {
            case READ_ONLY: '''::Smp::AccessKind::AK_ReadOnly'''
            case READ_WRITE: '''::Smp::AccessKind::AK_ReadWrite'''
            case WRITE_ONLY: '''::Smp::AccessKind::AK_WriteOnly'''
        }
    }
}
