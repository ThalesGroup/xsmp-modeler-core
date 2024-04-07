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
                <p>It contains an optional profile, source folder(s) in which are located modeling files, activated tool(s) and project dependencies.</p>
            '''
            case ga.profileReferenceAccess.profileKeyword_0: '''
                <p><code>«kw("profile")» <em>name</em></code></p>
                <br/>
                A <b>Profile</b> defines a hole configuration context applied to the project.
            '''
            case ga.toolReferenceAccess.toolKeyword_0: '''
                <p><code>«kw("tool")» <em>name</em></code></p>
                <br/>
                A <b>Tool</b> defines an additional code generator or validator applied to the project.
            '''
            case ga.sourcePathAccess.sourceKeyword_0: '''
                <p><code>«kw("source")» <em>name</em></code></p>
                <br/>
                The <b>Source</b> directive defines the location of modeling files. 
                <br />It must be relative to project root.
                <br />It can be a folder or a file name.
            '''
            case ga.sourcePathAccess.sourceKeyword_0: '''
                <p><code>«kw("include")» <em>name</em></code></p>
                <br/>
                The <b>Include</b> directive defines the location of nested project(s). 
                <br />It must be relative to project root.
            '''
            case ga.projectReferenceAccess.dependencyKeyword_0: '''
                <p><code>«kw("dependency")» <em>name</em></code></p>
                <br/>
                A <b>Dependency</b> defines a relation with an other <b>Project</b>. The current <b>Project</b> has the visibility on dependent project's modeling files.</p>
            '''
            default: ''''''
        }.toString
    }

}
