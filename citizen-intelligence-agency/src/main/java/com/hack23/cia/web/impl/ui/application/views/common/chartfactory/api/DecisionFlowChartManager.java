/*
 * Copyright 2014 James Pether Sörling
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *	$Id$
 *  $HeadURL$
*/
package com.hack23.cia.web.impl.ui.application.views.common.chartfactory.api;

import java.util.List;
import java.util.Map;

import com.hack23.cia.model.internal.application.data.committee.impl.ViewRiksdagenCommittee;
import com.hack23.cia.web.widgets.charts.SankeyChart;

/**
 * The Interface DecisionFlowChartManager.
 */
public interface DecisionFlowChartManager {

	/**
	 * Creates the all decision flow.
	 *
	 * @param committeeMap
	 *            the committee map
	 * @return the sankey chart
	 */
	SankeyChart createAllDecisionFlow(Map<String, List<ViewRiksdagenCommittee>> committeeMap);

	/**
	 * Creates the committee decision flow.
	 *
	 * @param viewRiksdagenCommittee
	 *            the view riksdagen committee
	 * @param committeeMap
	 *            the committee map
	 * @return the sankey chart
	 */
	SankeyChart createCommitteeDecisionFlow(ViewRiksdagenCommittee viewRiksdagenCommittee,
			Map<String, List<ViewRiksdagenCommittee>> committeeMap);

}