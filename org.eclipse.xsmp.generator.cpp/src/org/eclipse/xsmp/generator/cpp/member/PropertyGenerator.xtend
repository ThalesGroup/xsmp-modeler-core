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
import org.eclipse.xsmp.xcatalogue.NamedElementWithMembers
import org.eclipse.xsmp.xcatalogue.Property
import org.eclipse.xsmp.xcatalogue.Interface

class PropertyGenerator extends AbstractMemberGenerator<Property> {

    override declare(NamedElementWithMembers type, Property element) {
        if (!(type instanceof Interface)) {
            var static = element.static
            if (element.attachedField === null && !static) {

                var const = element.const

                val getter = '''
                    «IF const»const «ENDIF»::«element.type.fqn.toString("::")» get_«element.name»() override;
                '''

                val setter = '''
                    «IF const»const «ENDIF»void set_«element.name»(::«element.type.fqn.toString("::")» «element.name») override;
                '''

                switch (element.access) {
                    case READ_ONLY: {
                        return getter
                    }
                    case READ_WRITE: {
                        '''
                            «getter»
                            «setter»
                        '''
                    }
                    case WRITE_ONLY: {
                        return setter
                    }
                }
            }
        }
    }

    override define(NamedElementWithMembers type, Property element, boolean useGenPattern) {
        val gen = element.static && useGenPattern
        if (element.attachedField === null && !(type instanceof Interface)) {
            val getter = '''
                ::«element.type.fqn.toString("::")» «type.name(gen)»::get_«element.name»()
                {
                    ::«element.type.fqn.toString("::")» ret {};
                    return ret;
                   
                }
            '''

            val setter = '''
                void «type.name(gen)»::set_«element.name»(::«element.type.fqn.toString("::")» «element.name»)
                {
                     // TODO
                }
            '''

            switch (element.access) {
                case READ_ONLY: {
                    return getter
                }
                case READ_WRITE: {
                    '''
                        «getter»
                        «setter»
                    '''
                }
                case WRITE_ONLY: {
                    return setter
                }
            }
        }
    }

    override declareGen(NamedElementWithMembers type, Property element, boolean useGenPattern) {
        var static = element.static
        var const = element.const
        var abstract = !static && (element.attachedField === null)
        var virtual = !static && (element.virtual || abstract)

        val getter = '''
            /// Get «element.name».
            «element.comment»
            /// @return Current value of property «element.name».
            «IF virtual»virtual «ENDIF»«IF static»static «ENDIF»«IF const»const «ENDIF»::«element.type.fqn.toString("::")» get_«element.name»() «IF abstract»=0«ENDIF»;
        '''

        val setter = '''
            /// Set «element.name».
            «element.comment»
            /// @param value New value of property «element.name» to set.
            «IF virtual»virtual «ENDIF»«IF static»static «ENDIF»«IF const»const «ENDIF»void set_«element.name»(::«element.type.fqn.toString("::")» value) «IF abstract»=0«ENDIF»;
        '''

        switch (element.access) {
            case READ_ONLY: {
                return getter
            }
            case READ_WRITE: {
                '''
                    «getter»
                    «setter»
                '''
            }
            case WRITE_ONLY: {
                return setter
            }
        }
    }

    override defineGen(NamedElementWithMembers type, Property element, boolean useGenPattern) {
        if (element.attachedField !== null /*&& !element.static*/ ) {
            val getter = '''
                ::«element.type.fqn.toString("::")» «type.name(useGenPattern)»::get_«element.name»()
                {
                    return «element.attachedField.name»;
                }
            '''

            val setter = '''
                void «type.name(useGenPattern)»::set_«element.name»(::«element.type.fqn.toString("::")» «element.name»)
                {
                    «element.attachedField.name» = «element.name»;
                }
            '''

            switch (element.access) {
                case READ_ONLY: {
                    return getter
                }
                case READ_WRITE: {
                    '''
                        «getter»
                        «setter»
                    '''
                }
                case WRITE_ONLY: {
                    return setter
                }
            }
        }
    }

    override collectIncludes(Property element, IncludeAcceptor acceptor) {
        super.collectIncludes(element, acceptor)
        acceptor.include(element.type);
    }

    override initialize(NamedElementWithMembers container, Property member, boolean useGenPattern) {
    }

    override finalize(Property element) {
    }

    override Publish(Property element) {
        '''
            // Publish Property «element.name»
            receiver->PublishProperty("«element.name»", «element.description()», «element.type.uuidQfn», «element.accessKind», «element.viewKind»);
        '''
    }

    protected def CharSequence accessKind(Property p) {
        switch (p.access) {
            case READ_ONLY: '''::Smp::AccessKind::AK_ReadOnly'''
            case READ_WRITE: '''::Smp::AccessKind::AK_ReadWrite'''
            case WRITE_ONLY: '''::Smp::AccessKind::AK_WriteOnly'''
        }
    }
}
