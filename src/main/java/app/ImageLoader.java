/**
 * Copyright (c) 2014 Saúl Piña <sauljabin@gmail.com>, Jorge Parra <thejorgemylio@gmail.com>.
 * <p>
 * This file is part of AriaJava P3DX.
 * <p>
 * AriaJava P3DX is licensed under The MIT License.
 * For full copyright and license information please see the LICENSE file.
 */

package app;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class ImageLoader {

    public static String configPath = "ICONS.properties";
    private static Properties properties = new Properties();

    public static void load() throws IOException {
        properties.load(new InputStreamReader(ClassLoader.getSystemResourceAsStream(configPath), "UTF-8"));
    }

    public static ImageIcon get(String key) {
        return new ImageIcon(ClassLoader.getSystemResource("images/" + properties.getProperty(key)));
    }

}
