/*******************************************************************************
 * Copyright (C) 2024 THALES ALENIA SPACE FRANCE.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.xsmp.ide.hover

import com.google.inject.Inject
import com.google.inject.Singleton
import org.eclipse.xsmp.services.XsmpprojectGrammarAccess
import org.eclipse.xtext.Keyword

@Singleton
class XsmpprojectKeywordHovers implements IKeywordHovers {

    @Inject XsmpprojectGrammarAccess ga;

    private def kw(String kw) {
        '''<strong><span style="color: #7f0055;">«kw»</span></strong>'''
    }

    override hoverText(Keyword k) {

        return switch (k) {
            case ga.projectAccess.projectKeyword_1: '''
                <p><code>«kw("project")» <em>name</em></code></p>
                <br/>
                A <b>Project</b> is a document that defines the project configuration.
                <p>It contains an optional profile, source folder(s) where modeling files are located, activated tool(s) and project dependencies.</p>
            '''
            case ga.profileReferenceAccess.profileKeyword_0: '''
                <p><code>«kw("profile")» <em>name</em></code></p>
                <br/>
                A <b>Profile</b> defines a whole configuration context applied to the project.
            '''
            case ga.toolReferenceAccess.toolKeyword_0: '''
                <p><code>«kw("tool")» <em>name</em></code></p>
                <br/>
                A <b>Tool</b> specifies an additional code generator or validator used for the project.
            '''
            case ga.sourcePathAccess.sourceKeyword_0: '''
                <p><code>«kw("source")» <em>name</em></code></p>
                <br/>
                The <b>Source</b> directive defines the location of modeling files.
                <br />The path can be either a folder or a filename relative to the project root.
            '''
            case ga.includePathAccess.includeKeyword_0: '''
                <p><code>«kw("include")» <em>name</em></code></p> <strong>Deprecated.</strong>
                <br/>
                The <b>Include</b> directive defines the location of nested project(s).
            '''
            case ga.projectReferenceAccess.dependencyKeyword_0: '''
                <p><code>«kw("dependency")» <em>name</em></code></p>
                <br/>
                A <b>Dependency</b> establishes a relationship with another <b>Project</b>,
                granting the current <b>Project</b> visibility over the dependent project's modeling files.</p>
            '''
            default: ''''''
        }.toString
    }

}
