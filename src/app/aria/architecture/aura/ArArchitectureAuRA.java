/**
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 * 
 *		SAUL PIÑA - SAULJP07@GMAIL.COM
 *		JORGE PARRA - THEJORGEMYLIO@GMAIL.COM
 *		2014
 */

package app.aria.architecture.aura;

import app.aria.architecture.ArArchitecture;

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
