/**
 * Copyright (c) 2014 Saúl Piña <sauljabin@gmail.com>, Jorge Parra <thejorgemylio@gmail.com>.
 * <p>
 * This file is part of AriaJava P3DX.
 * <p>
 * AriaJava P3DX is licensed under The MIT License.
 * For full copyright and license information please see the LICENSE file.
 */

package app;

import app.gui.ControllerViewApp;
import com.mobilerobots.Aria.Aria;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        initGUI();
    }

    private static void loadFeaturesGUI() {
        try {
            Config.load();
            Log.setLevel(LogLevel.valueOf(Config.get("LOG_LEVEL")));
            Translate.load();
            ImageLoader.load();
            Library.load();
            Aria.init();
        } catch (Exception e) {
            Log.error(Main.class, "loadFeaturesGUI()", e);
            JOptionPane.showMessageDialog(null, e.getClass().getName() + "\n" + e.getMessage(), "Error loadFeaturesGUI()", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }

    private static void initGUI() {
        loadFeaturesGUI();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ControllerViewApp();
            }
        });
    }

}
