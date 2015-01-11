/**
 * 
 * UtilDate.java
 * 
 * Copyright (c) 2014, Saul Piña <sauljp07@gmail.com>, Jorge Parra <thejorgemylio@gmail.com>.
 * 
 * This file is part of AriaJavaP3DX.
 * 
 * AriaJavaP3DX is licensed under The MIT License.
 * For full copyright and license information please see the LICENSE.txt file.
 *
 */

package app.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class UtilDate {
	public static Date now() {
		return Calendar.getInstance().getTime();
	}

	public static String nowFormat(String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(Calendar.getInstance().getTime());
	}

	public static String nowString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(Calendar.getInstance().getTime());
	}
}
