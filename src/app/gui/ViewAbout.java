/**
 * Copyright (c) 2014 Saúl Piña <sauljabin@gmail.com>, Jorge Parra <thejorgemylio@gmail.com>.
 * 
 * This file is part of AriaJava P3DX.
 * 
 * AriaJava P3DX is licensed under The MIT License.
 * For full copyright and license information please see the LICENSE file.
 */

package app.gui;

import java.io.File;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import app.Config;
import app.Translate;
import app.util.JOpenFileLabel;
import app.util.JUrlLinkLabel;
import net.miginfocom.swing.MigLayout;

public class ViewAbout extends JDialog {

	private static final long serialVersionUID = -3296703600845483586L;

	public ViewAbout() {
		setSize(380, 200);
		setTitle(Config.get("APP_NAME"));

		JPanel panel = new JPanel(new MigLayout());
		add(panel);

		panel.add(new JLabel(Config.get("APP_NAME")), "span 2, wrap");
		panel.add(new JLabel(Translate.get("GUI_APPDESCRIP")), "span 2, grow, wrap 20");
		panel.add(new JLabel(Translate.get("GUI_LICENSE") + ": "), "grow");
		panel.add(new JLabel(Config.get("APP_LICENSE")), "span 2, grow, wrap");
		panel.add(new JLabel(Translate.get("GUI_COPYRIGHT") + ": "), "grow");
		panel.add(new JLabel(Config.get("APP_AUTHOR")), "span 2, grow, wrap");
		panel.add(new JLabel(Translate.get("GUI_URL") + ": "), "grow");
		panel.add(new JUrlLinkLabel(Config.get("APP_URL")), "grow, wrap");
		panel.add(new JLabel(Translate.get("GUI_DOCUMENTATION") + ": "), "grow");
		panel.add(new JOpenFileLabel(new File(Config.get("APP_DOCUMENTACION"))), "grow, wrap");

		setLocationRelativeTo(this);
		setModal(true);
		setVisible(true);
	}

}
