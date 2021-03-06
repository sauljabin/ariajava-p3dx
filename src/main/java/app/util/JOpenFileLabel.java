/**
 * Copyright (c) 2014 Saúl Piña <sauljabin@gmail.com>, Jorge Parra <thejorgemylio@gmail.com>.
 * <p>
 * This file is part of AriaJava P3DX.
 * <p>
 * AriaJava P3DX is licensed under The MIT License.
 * For full copyright and license information please see the LICENSE file.
 */

package app.util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.Serializable;

/**
 *
 *
 * @author Saul Piña <sauljp07@gmail.com>
 * @version 1.0
 */
public class JOpenFileLabel extends JLabel implements Serializable {

    public static final String VERSION = "1.0";
    private static final Color color = Color.BLACK;
    private static final Color colorEntered = Color.DARK_GRAY;

    private File file;

    /**
     * Set text and file
     *
     * @param text
     *            Text label
     * @param file
     *            File
     */
    public JOpenFileLabel(String text, File file) {
        setText(text);
        setFile(file);
        setForeground(color);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                openFile();
            }

            @Override
            public void mouseEntered(MouseEvent arg0) {
                setForeground(colorEntered);
            }

            @Override
            public void mouseExited(MouseEvent arg0) {
                setForeground(color);
            }
        });

    }

    /**
     * Set the file
     *
     * @param file
     *            File
     */
    public JOpenFileLabel(File file) {
        this(file.getName(), file);
    }

    public JOpenFileLabel() {
        this("", null);
    }

    /**
     * Get file
     *
     * @return file
     */
    public File getFile() {
        return file;
    }

    /**
     * Set File
     *
     * @param file
     *            File
     */
    public void setFile(File file) {
        this.file = file;
    }

    /**
     * Set text and file
     *
     * @param text
     *            Text label
     * @param file
     *            File
     */
    public void setText(String text, File file) {
        setFile(file);
        setText(text);
    }

    /**
     * Open file
     */
    public void openFile() {
        try {
            if (file != null)
                Desktop.getDesktop().open(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
