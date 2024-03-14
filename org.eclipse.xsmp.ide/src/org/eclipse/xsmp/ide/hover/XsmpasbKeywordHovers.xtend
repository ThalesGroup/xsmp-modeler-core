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
import org.eclipse.xsmp.services.XsmpasbGrammarAccess
import org.eclipse.xtext.Keyword

@Singleton
class XsmpasbKeywordHovers implements IKeywordHovers {

    @Inject XsmpasbGrammarAccess ga;

    private def typeReference() {
        '''<em>Catalogue::Component|Factory|"00000000-0000-0000-0000-000000000000"</em>'''
    }

    private def kw(String kw) {
        '''<strong><span style="color: #7f0055;">«kw»</span></strong>'''
    }

    override hoverText(Keyword k) {

        return switch (k) {
            case ga.assemblyAccess.assemblyKeyword_1: '''
                <p><code>«kw("assembly")» <em>name</em></code></p>
                <br/>
                An <b>Assembly</b> is a document that defines factories and simulators.
                <p>It contains namespaces as a primary ordering mechanism.
                <br />The names of these namespaces need to be unique within the assembly.</p>
            '''
            case ga.namespaceAccess.namespaceKeyword_4,
            case ga.namespaceMemberAccess.namespaceKeyword_3_0_1: '''
                <p><code>«kw("namespace")» <em>name</em> { } </code></p>
                <br/>
                A <b>Namespace</b> is a primary ordering mechanism. <br />
                <p>A <b>namespace</b> may contain other namespaces (nested namespaces), and does typically contain factories and simulators.
                <br />In SMDL, namespaces are contained within an <b>Assembly</b> (either directly, or within another namespace in an assembly).
                <br />All sub-elements of a namespace (namespaces, factories and simulators) must have unique names.</p>
            '''
            case ga.namespaceMemberAccess.simulatorKeyword_3_1_1: '''
                <p><code>«kw("simulator")» <em>name</em> [«kw("extends")» <em>sim1, ..., simN</em>] [«kw("epoch")» <em>epochTime</em>] [«kw("mission")» <em>missionStartTime</em>] { } </code></p>
                <br/>
                A <b>Simulator</b> is the root element of the simulation. <br />
                <p>A <b>simulator</b> may contain Model or Service instances.
                <br />All sub-elements of a simulator (models and services) must be unique.</p>
            '''
            case ga.namespaceMemberAccess.epochKeyword_3_1_4_0_0: '''
                The Epoch Time of the <b>Simulator</b>.
            '''
            case ga.namespaceMemberAccess.missionKeyword_3_1_4_1_0: '''
                The Mission Start Time of the <b>Simulator</b>.
            '''
            case ga.namespaceMemberAccess.extendsKeyword_3_1_3_0: '''
                The list of <b>Simulator</b> to inherit from.
            '''
            case ga.namespaceMemberAccess.factoryKeyword_3_2_1: '''
                <p><code>«kw("factory")» <em>name</em> «kw("extends")» «typeReference» { } </code></p>
                <br/>
                A <b>Factory</b> create a specialized Instance from a Component or an other Factory. <br />
                <p>A <b>factory</b> may contain nested-instances, configuration, connections, ...
                <br />All sub-elements of a factory (instances and resolvable elements) must have unique names.</p>
            '''
            case ga.instanceAccess.instanceKeyword_0: '''
                <p><code>«kw("instance")» <em>name</em> «kw("extends")» «typeReference» { } </code></p>
                <br/>
                Create an <b>Instance</b> of the given type.
            '''
            case ga.instanceAccess.extendsKeyword_2: '''
                The <b>Instance</b> type or factory.
            '''
            case ga.initEntryPointAccess.initKeyword_0: '''
                <p><code>«kw("init")» <em>entryPoint</em></code></p>
                <br/>
                Add an <b>EntryPoint</b> or a <b>Task</b> that will be executed by the <b>Simulator</b> during the Initialising state.
            '''
            case ga.loadDirectiveAccess.loadKeyword_0: '''
                <p><code>«kw("load")» <em>Catalogue</em></code></p>
                <br/>
                Load the library of a package in the <b>Simulator</b>.
            '''
            case ga.scheduleAccess.scheduleKeyword_0: '''
                <p><code>«kw("schedule")» <em>entryPoint</em> [«kw("immediate")» | («kw("simulation")» | «kw("epoch")» | «kw("zulu")» <em>time</em> [«kw("cycle")» <em>cycle</em>«kw("repeat")» <em>repeat</em> ]  «»]</code></p>
                <br/>
                Add an event on the <b>Scheduler</b>.<br/>
                If <b>immediate</b> the event will be executed the next time the <b>Scheduler</b> process the simulation time.<br/>
            '''
            case ga.scheduleAccess.immediateKeyword_2_0: '''
                The event will be executed the next time the <b>Scheduler</b> process the simulation time.<br/>
            '''
            case ga.scheduleAccess.cycleKeyword_2_1_2_0: '''
                Duration between two triggers of the event.
            '''
            case ga.scheduleAccess.repeatKeyword_2_1_2_2_0: '''
                Number of times the event shall be repeated, or 0 for a single event, or -1 for no limit.
            '''
            case ga.timeKindAccess.simulationSimulationKeyword_0_0: '''
                Event based on simulation time.
            '''
            case ga.timeKindAccess.epochEpochKeyword_1_0: '''
                Event based on epoch time.
            '''
            case ga.timeKindAccess.missionMissionKeyword_2_0: '''
                Event based on mission time.
            '''
            case ga.timeKindAccess.zuluZuluKeyword_3_0: '''
                Event based on zulu time.
            '''
            case ga.configurationAccess.setKeyword_0: '''
                <p><code>«kw("set")» <em>field</em> <em>value</em></code></p>
                <br/>
                Set the value of a <b>Field</b> or writable <b>Property</b>.
            '''
            case ga.connectionAccess.connectKeyword_0: '''
                <p><code>«kw("connect")» <em>source</em> <em>destination</em></code></p>
                <br/>
                Create a <b>Connection</b> between a <em>source</em> and a <em>destination</em>.<br/>
                Following <b>Connection</b> are supported:<br/>
                 - output <b>Field</b> to input <b>Field</b><br/>
                 - <b>EventSource</b> to <b>EventSink</b><br/>
                 - <b>Reference</b> to <b>Instance</b><br/>
            '''
            default: ''''''
        }.toString
    }

}
