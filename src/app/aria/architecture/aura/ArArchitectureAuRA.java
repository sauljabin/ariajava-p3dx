/**
 * 
 * Copyright (c) 2014 Saul Piña <sauljp07@gmail.com>, Jorge Parra <thejorgemylio@gmail.com>
 * 
 * This file is part of AriaJavaP3DX.
 *
 * AriaJavaP3DX is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * AriaJavaP3DX is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with AriaJavaP3DX.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */

package app.aria.architecture.aura;

import app.aria.ArArchitecture;

public class ArArchitectureAuRA extends ArArchitecture {

	private ArMisionPlanner arMisionPlanner;
	private ArPlanSequencer arPlanSequencer;
	private ArSpatialReasoner arSpatialReasoner;

	public ArMisionPlanner getArMisionPlanner() {
		return arMisionPlanner;
	}

	public void setArMisionPlanner(ArMisionPlanner arMisionPlanner) {
		this.arMisionPlanner = arMisionPlanner;
	}

	public ArPlanSequencer getArPlanSequencer() {
		return arPlanSequencer;
	}

	public void setArPlanSequencer(ArPlanSequencer arPlanSequencer) {
		this.arPlanSequencer = arPlanSequencer;
	}

	public ArSpatialReasoner getArSpatialReasoner() {
		return arSpatialReasoner;
	}

	public void setArSpatialReasoner(ArSpatialReasoner arSpatialReasoner) {
		this.arSpatialReasoner = arSpatialReasoner;
	}

	public ArArchitectureAuRA(String host, int tcpPort) {
		super("AuRA", host, tcpPort);
	}

	@Override
	public void behavior() {

	}

}
