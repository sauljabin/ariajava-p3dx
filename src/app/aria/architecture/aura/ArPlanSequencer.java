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

import java.util.ArrayList;

import app.map.Map;
import app.path.geometry.Point;

public class ArPlanSequencer {

	private ArrayList<Point> way;
	private ArrayList<Point> path;
	private Point start;
	private Point finish;

	public ArPlanSequencer(Map map) {
		way = new ArrayList<Point>();
	}

	public boolean continuePath() {
		if (start == null || finish == null) {
			if(path != null){
				
			}
		} else if (targetObtained()) {
			way.add(start);
			start = finish;
			finish = path.remove(0);
		} else if (brokenPath()) {
			return true;
		} else {
			toFinish();
		}
		return false;
	}

	private void toFinish() {

	}

	private boolean brokenPath() {
		return false;
	}

	private boolean targetObtained() {
		
		return false;
	}

	public void newPath(ArrayList<Point> path) {
		this.path = path;
		
	}

}
