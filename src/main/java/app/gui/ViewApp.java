/**
 * Copyright (c) 2014 Saúl Piña <sauljabin@gmail.com>, Jorge Parra <thejorgemylio@gmail.com>.
 * <p>
 * This file is part of AriaJava P3DX.
 * <p>
 * AriaJava P3DX is licensed under The MIT License.
 * For full copyright and license information please see the LICENSE file.
 */

package app.gui;

import app.Config;
import app.ImageLoader;
import app.Translate;
import app.util.ClassW;
import app.util.JIntegerField;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

public class ViewApp extends JFrame {

    private ControllerViewApp controller;
    private Vector<JMenuItem> menuItems;
    private Vector<JButton> buttons;
    private Vector<JSpinner> spinners;
    private JMenuBar menuBar;
    private JMenu menuOptions;
    private JMenuItem menuItemShowConfig;
    private JMenuItem menuItemClose;
    private JMenu menuHelp;
    private JMenuItem menuItemAbout;
    private JPanel pnlConnection;
    private JLabel lblHost;
    private JLabel lblPort;
    private JTextField txtHost;
    private JIntegerField txtPort;
    private JPanel pnlTab2;
    private JPanel pnlSouth;
    private JTextArea tarConsole;
    private JPanel pnlCenter;
    private JLabel lblArch;
    private JComboBox<ClassW> cmbArch;
    private JButton btnConnect;
    private JButton btnDisconnect;
    private JMenuItem menuItemLoadMap;
    private Canvas canvasAnimation;
    private JLabel lblFPS;
    private JSpinner spnFPS;
    private JPanel pnlAnimation;
    private JPanel pnlAnimationControl;
    private JButton btnZoomIn;
    private JButton btnZoomOut;
    private JButton btnSaveImage;
    private JButton btnArrowUp;
    private JButton btnArrowDown;
    private JButton btnArrowLeft;
    private JButton btnArrowRight;
    private JButton btnShowHidePath;
    private JButton btnCenterMap;
    private JButton btnAntialiasingOnOff;
    private JPanel pnlRobotControl;
    private JLabel lblProportion;
    private JSpinner spnProportion;
    private JLabel lblStrokeSize;
    private JSpinner spnStrokeSize;
    private JTabbedPane pnlTabWest;
    private JPanel pnlWest;
    private JPanel pnlTab1;
    private JPanel pnlInitPoint;
    private JLabel lblInitX;
    private JSpinner spnInitX;
    private JLabel lblInitY;
    private JSpinner spnInitY;
    private JLabel lblInitAngle;
    private JSpinner spnInitAngle;
    private JLabel lblEndX;
    private JSpinner spnEndX;
    private JLabel lblEndY;
    private JSpinner spnEndY;
    private JLabel lblEndAngle;
    private JSpinner spnEndAngle;
    private JPanel pnlEndPoint;
    private JPanel pnlRobot;
    private JLabel lblMaxSpeed;
    private JSpinner spnMaxSpeed;
    private JLabel lblPositionUpdate;
    private JSpinner spnPositionUpdate;
    private JPanel pnlCanvas;
    private JMenuItem menuItemDoc;
    private JLabel lblSleepTime;
    private JSpinner spnSleepTime;
    private JLabel lblErrorDistance;
    private JSpinner spnErrorDistance;
    private JLabel lblErrorAngle;
    private JSpinner spnErrorAngle;
    private JLabel lblStopDistance;
    private JSpinner spnStopDistance;
    private JLabel lblSonarAngle;
    private JSpinner spnSonarAngle;

    public ViewApp() {
        menuItems = new Vector<JMenuItem>();
        buttons = new Vector<JButton>();
        spinners = new Vector<JSpinner>();
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        init();
        setLocationRelativeTo(this);
    }

    public ControllerViewApp getController() {
        return controller;
    }

    public void setController(ControllerViewApp controller) {
        this.controller = controller;
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                close();
            }
        });
        for (int i = 0; i < menuItems.size(); i++) {
            menuItems.get(i).addActionListener(controller);
        }
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).addActionListener(controller);
        }
        for (int i = 0; i < spinners.size(); i++) {
            spinners.get(i).addChangeListener(controller);
        }
    }

    public void close() {
        if (controller != null)
            controller.close();
    }

    private void init() {
        setLayout(new BorderLayout());
        setSize(800, 700);
        setTitle(Config.get("APP_NAME"));

        menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        menuOptions = new JMenu(Translate.get("GUI_OPTIONS"));
        menuBar.add(menuOptions);

        menuItemLoadMap = new JMenuItem(Translate.get("GUI_LOADMAP"));
        menuOptions.add(menuItemLoadMap);

        menuItemShowConfig = new JMenuItem(Translate.get("GUI_SHOWCONFIG"));
        menuOptions.add(menuItemShowConfig);

        menuOptions.add(new JSeparator());

        menuItemClose = new JMenuItem(Translate.get("GUI_CLOSE"));
        menuOptions.add(menuItemClose);

        menuHelp = new JMenu(Translate.get("GUI_HELP"));
        menuBar.add(menuHelp);

        menuItemAbout = new JMenuItem(Translate.get("GUI_ABOUT") + " " + Config.get("APP_NAME"));
        menuHelp.add(menuItemAbout);

        menuItemDoc = new JMenuItem(Translate.get("GUI_DOCUMENTATION"));
        menuHelp.add(menuItemDoc);

        pnlConnection = new JPanel();
        pnlConnection.setLayout(new MigLayout("insets 3"));
        pnlConnection.setBorder(BorderFactory.createTitledBorder(Translate.get("GUI_CONNECTION")));

        lblHost = new JLabel(Translate.get("GUI_HOST"));
        lblPort = new JLabel(Translate.get("GUI_PORT"));

        txtHost = new JTextField();
        txtPort = new JIntegerField();

        btnConnect = new JButton(Translate.get("GUI_CONNECT"));
        btnDisconnect = new JButton(Translate.get("GUI_DISCONNECT"));

        lblArch = new JLabel(Translate.get("GUI_ARCH"));
        cmbArch = new JComboBox<ClassW>();

        pnlConnection.add(lblHost, "width 100, height 20");
        pnlConnection.add(txtHost, "width 100, height 20, wrap");
        pnlConnection.add(lblPort, "grow, height 20");
        pnlConnection.add(txtPort, "grow, height 20, wrap");
        pnlConnection.add(btnConnect, "grow, height 20");
        pnlConnection.add(btnDisconnect, "grow, height 20, wrap");

        lblFPS = new JLabel(Translate.get("GUI_FPS"));
        spnFPS = new JSpinner();

        lblProportion = new JLabel(Translate.get("GUI_PROPORTION"));
        spnProportion = new JSpinner();

        lblStrokeSize = new JLabel(Translate.get("GUI_STROKESIZE"));
        spnStrokeSize = new JSpinner();

        lblPositionUpdate = new JLabel(Translate.get("GUI_POSITIONUPDATE"));
        spnPositionUpdate = new JSpinner();

        pnlAnimation = new JPanel();
        pnlAnimation.setLayout(new MigLayout("insets 3"));
        pnlAnimation.setBorder(BorderFactory.createTitledBorder(Translate.get("GUI_ANIMATION")));

        pnlAnimation.add(lblFPS, "width 100, height 20");
        pnlAnimation.add(spnFPS, "width 100, height 20, wrap");
        pnlAnimation.add(lblProportion, "grow, height 20");
        pnlAnimation.add(spnProportion, "grow, height 20, wrap");
        pnlAnimation.add(lblStrokeSize, "grow, height 20");
        pnlAnimation.add(spnStrokeSize, "grow, height 20, wrap");
        pnlAnimation.add(lblPositionUpdate, "grow, height 20");
        pnlAnimation.add(spnPositionUpdate, "grow, height 20, wrap");

        pnlRobotControl = new JPanel();
        pnlRobotControl.setLayout(new MigLayout("insets 3"));
        pnlRobotControl.setBorder(BorderFactory.createTitledBorder(Translate.get("GUI_CONTROL")));

        pnlRobotControl.add(lblArch, "width 100, height 20");
        pnlRobotControl.add(cmbArch, "width 100, height 20, wrap");

        lblInitX = new JLabel(Translate.get("GUI_X"));
        spnInitX = new JSpinner();

        lblInitY = new JLabel(Translate.get("GUI_Y"));
        spnInitY = new JSpinner();

        lblInitAngle = new JLabel(Translate.get("GUI_ANGLE"));
        spnInitAngle = new JSpinner();

        pnlInitPoint = new JPanel();
        pnlInitPoint.setLayout(new MigLayout("insets 3"));
        TitledBorder borderStarPoint = BorderFactory.createTitledBorder(Translate.get("GUI_STARTPOINT"));
        borderStarPoint.setTitleColor(Color.BLUE);
        pnlInitPoint.setBorder(borderStarPoint);

        pnlInitPoint.add(lblInitX, "width 100, height 20");
        pnlInitPoint.add(spnInitX, "width 100, height 20, wrap");
        pnlInitPoint.add(lblInitY, "grow, height 20");
        pnlInitPoint.add(spnInitY, "grow, height 20, wrap");
        pnlInitPoint.add(lblInitAngle, "grow, height 20");
        pnlInitPoint.add(spnInitAngle, "grow, height 20, wrap");

        lblEndX = new JLabel(Translate.get("GUI_X"));
        spnEndX = new JSpinner();

        lblEndY = new JLabel(Translate.get("GUI_Y"));
        spnEndY = new JSpinner();

        lblEndAngle = new JLabel(Translate.get("GUI_ANGLE"));
        spnEndAngle = new JSpinner();

        pnlEndPoint = new JPanel();
        pnlEndPoint.setLayout(new MigLayout("insets 3"));
        TitledBorder borderEndPoint = BorderFactory.createTitledBorder(Translate.get("GUI_ENDPOINT"));
        borderEndPoint.setTitleColor(Color.RED);
        pnlEndPoint.setBorder(borderEndPoint);

        pnlEndPoint.add(lblEndX, "width 100, height 20");
        pnlEndPoint.add(spnEndX, "width 100, height 20, wrap");
        pnlEndPoint.add(lblEndY, "grow, height 20");
        pnlEndPoint.add(spnEndY, "grow, height 20, wrap");
        pnlEndPoint.add(lblEndAngle, "grow, height 20");
        pnlEndPoint.add(spnEndAngle, "grow, height 20, wrap");

        pnlRobot = new JPanel();
        pnlRobot.setLayout(new MigLayout("insets 3"));
        pnlRobot.setBorder(BorderFactory.createTitledBorder(Translate.get("GUI_ROBOT")));

        lblMaxSpeed = new JLabel(Translate.get("GUI_MAXSPEED"));
        spnMaxSpeed = new JSpinner();

        lblSleepTime = new JLabel(Translate.get("GUI_SLEEPTIME"));
        spnSleepTime = new JSpinner();

        lblErrorDistance = new JLabel(Translate.get("GUI_ERRORDISTANCE"));
        spnErrorDistance = new JSpinner();

        lblErrorAngle = new JLabel(Translate.get("GUI_ERRORANGLE"));
        spnErrorAngle = new JSpinner();

        lblStopDistance = new JLabel(Translate.get("GUI_STOPDISTANCE"));
        spnStopDistance = new JSpinner();

        lblSonarAngle = new JLabel(Translate.get("GUI_SONARANGLE"));
        spnSonarAngle = new JSpinner();

        pnlRobot.add(lblMaxSpeed, "width 100, height 20");
        pnlRobot.add(spnMaxSpeed, "width 100, height 20, wrap");
        pnlRobot.add(lblSleepTime, "grow, height 20");
        pnlRobot.add(spnSleepTime, "grow, height 20, wrap");
        pnlRobot.add(lblErrorDistance, "grow, height 20");
        pnlRobot.add(spnErrorDistance, "grow, height 20, wrap");
        pnlRobot.add(lblErrorAngle, "grow, height 20");
        pnlRobot.add(spnErrorAngle, "grow, height 20, wrap");
        pnlRobot.add(lblStopDistance, "grow, height 20");
        pnlRobot.add(spnStopDistance, "grow, height 20, wrap");
        pnlRobot.add(lblSonarAngle, "grow, height 20");
        pnlRobot.add(spnSonarAngle, "grow, height 20, wrap");

        pnlTab2 = new JPanel();
        pnlTab2.setLayout(new MigLayout("insets 3"));
        pnlTab1 = new JPanel();
        pnlTab1.setLayout(new MigLayout("insets 3"));
        pnlWest = new JPanel();
        pnlWest.setLayout(new MigLayout());
        pnlTabWest = new JTabbedPane();

        pnlTab1.add(pnlAnimation, "wrap");
        pnlTab1.add(pnlConnection, "wrap");
        pnlTab2.add(pnlRobotControl, "wrap");
        pnlTab2.add(pnlRobot, "wrap");
        pnlTab2.add(pnlInitPoint, "wrap");
        pnlTab2.add(pnlEndPoint, "wrap");

        pnlTabWest.addTab(Translate.get("GUI_APPLICATION"), pnlTab1);
        pnlTabWest.addTab(Translate.get("GUI_ROBOT"), pnlTab2);

        pnlWest.add(pnlTabWest);

        add(pnlWest, BorderLayout.WEST);

        pnlCenter = new JPanel(new BorderLayout());
        pnlCenter.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 7));
        add(pnlCenter, BorderLayout.CENTER);

        canvasAnimation = new Canvas();
        canvasAnimation.setBackground(Color.WHITE);
        pnlCanvas = new JPanel(new BorderLayout());
        pnlCanvas.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 100)));
        pnlCanvas.add(canvasAnimation);
        pnlCenter.add(pnlCanvas, BorderLayout.CENTER);

        pnlAnimationControl = new JPanel(new MigLayout("insets 0 7 0 0"));

        btnZoomIn = new JButton(ImageLoader.get("ZOOM_IN"));
        btnZoomIn.setToolTipText(Translate.get("GUI_ZOOMIN"));
        btnZoomOut = new JButton(ImageLoader.get("ZOOM_OUT"));
        btnZoomOut.setToolTipText(Translate.get("GUI_ZOOMOUT"));

        btnArrowUp = new JButton(ImageLoader.get("ARROW_UP"));
        btnArrowUp.setToolTipText(Translate.get("GUI_ARROWUP"));
        btnArrowDown = new JButton(ImageLoader.get("ARROW_DOWN"));
        btnArrowDown.setToolTipText(Translate.get("GUI_ARROWDOWN"));
        btnArrowLeft = new JButton(ImageLoader.get("ARROW_LEFT"));
        btnArrowLeft.setToolTipText(Translate.get("GUI_ARROWLEFT"));
        btnArrowRight = new JButton(ImageLoader.get("ARROW_RIGHT"));
        btnArrowRight.setToolTipText(Translate.get("GUI_ARROWRIGHT"));

        btnSaveImage = new JButton(ImageLoader.get("SAVE_IMAGE"));
        btnSaveImage.setToolTipText(Translate.get("GUI_SAVEIMAGE"));

        btnShowHidePath = new JButton(ImageLoader.get("HIDE_PATH"));
        btnShowHidePath.setToolTipText(Translate.get("GUI_HIDEPATH"));

        btnCenterMap = new JButton(ImageLoader.get("CENTER_MAP"));
        btnCenterMap.setToolTipText(Translate.get("GUI_CENTERMAP"));

        btnAntialiasingOnOff = new JButton(ImageLoader.get("ANTIALIASING_ON"));
        btnAntialiasingOnOff.setToolTipText(Translate.get("GUI_ANTIALIASINGON"));

        pnlAnimationControl.add(btnZoomIn, "wrap");
        pnlAnimationControl.add(btnZoomOut, "wrap 15");
        pnlAnimationControl.add(btnArrowDown, "wrap");
        pnlAnimationControl.add(btnArrowUp, "wrap");
        pnlAnimationControl.add(btnArrowLeft, "wrap");
        pnlAnimationControl.add(btnArrowRight, "wrap 15");
        pnlAnimationControl.add(btnCenterMap, "wrap");
        pnlAnimationControl.add(btnSaveImage, "wrap 15");
        pnlAnimationControl.add(btnShowHidePath, "wrap");
        pnlAnimationControl.add(btnAntialiasingOnOff, "wrap");

        pnlCenter.add(pnlAnimationControl, BorderLayout.EAST);

        pnlSouth = new JPanel();
        pnlSouth.setLayout(new MigLayout());
        add(pnlSouth, BorderLayout.SOUTH);

        JScrollPane scrollPanelConsole = new JScrollPane();
        pnlSouth.add(scrollPanelConsole, "width 100%, height 100");
        tarConsole = new JTextArea();
        scrollPanelConsole.setViewportView(tarConsole);

        menuItems.add(menuItemShowConfig);
        menuItems.add(menuItemClose);
        menuItems.add(menuItemAbout);
        menuItems.add(menuItemLoadMap);
        menuItems.add(menuItemDoc);

        spinners.add(spnStrokeSize);
        spinners.add(spnProportion);
        spinners.add(spnFPS);
        spinners.add(spnEndAngle);
        spinners.add(spnEndX);
        spinners.add(spnEndY);
        spinners.add(spnInitAngle);
        spinners.add(spnInitX);
        spinners.add(spnInitY);
        spinners.add(spnMaxSpeed);
        spinners.add(spnPositionUpdate);
        spinners.add(spnSleepTime);
        spinners.add(spnErrorAngle);
        spinners.add(spnErrorDistance);
        spinners.add(spnStopDistance);
        spinners.add(spnSonarAngle);

        buttons.add(btnConnect);
        buttons.add(btnDisconnect);
        buttons.add(btnAntialiasingOnOff);
        buttons.add(btnArrowDown);
        buttons.add(btnArrowLeft);
        buttons.add(btnArrowRight);
        buttons.add(btnArrowUp);
        buttons.add(btnCenterMap);
        buttons.add(btnSaveImage);
        buttons.add(btnShowHidePath);
        buttons.add(btnZoomIn);
        buttons.add(btnZoomOut);
    }

    public JMenuItem getMenuItemClose() {
        return menuItemClose;
    }

    public JMenuItem getMenuItemAbout() {
        return menuItemAbout;
    }

    public JMenuItem getMenuItemShowConfig() {
        return menuItemShowConfig;
    }

    public JTextField getTxtHost() {
        return txtHost;
    }

    public JIntegerField getTxtPort() {
        return txtPort;
    }

    public JTextArea getTarConsole() {
        return tarConsole;
    }

    public JComboBox<ClassW> getCmbArch() {
        return cmbArch;
    }

    public JButton getBtnConnect() {
        return btnConnect;
    }

    public JButton getBtnDisconnect() {
        return btnDisconnect;
    }

    public JMenuItem getMenuItemLoadMap() {
        return menuItemLoadMap;
    }

    public Canvas getCanvasAnimation() {
        return canvasAnimation;
    }

    public JSpinner getSpnFPS() {
        return spnFPS;
    }

    public JButton getBtnZoomIn() {
        return btnZoomIn;
    }

    public JButton getBtnZoomOut() {
        return btnZoomOut;
    }

    public JButton getBtnSaveImage() {
        return btnSaveImage;
    }

    public JButton getBtnArrowUp() {
        return btnArrowUp;
    }

    public JButton getBtnArrowDown() {
        return btnArrowDown;
    }

    public JButton getBtnArrowLeft() {
        return btnArrowLeft;
    }

    public JButton getBtnArrowRight() {
        return btnArrowRight;
    }

    public JButton getBtnShowHidePath() {
        return btnShowHidePath;
    }

    public JButton getBtnCenterMap() {
        return btnCenterMap;
    }

    public JButton getBtnAntialiasingOnOff() {
        return btnAntialiasingOnOff;
    }

    public JSpinner getSpnProportion() {
        return spnProportion;
    }

    public JSpinner getSpnStrokeSize() {
        return spnStrokeSize;
    }

    public JSpinner getSpnInitX() {
        return spnInitX;
    }

    public JSpinner getSpnInitY() {
        return spnInitY;
    }

    public JSpinner getSpnInitAngle() {
        return spnInitAngle;
    }

    public JSpinner getSpnEndX() {
        return spnEndX;
    }

    public JSpinner getSpnEndY() {
        return spnEndY;
    }

    public JSpinner getSpnEndAngle() {
        return spnEndAngle;
    }

    public JSpinner getSpnMaxSpeed() {
        return spnMaxSpeed;
    }

    public JSpinner getSpnPositionUpdate() {
        return spnPositionUpdate;
    }

    public JMenuItem getMenuItemDoc() {
        return menuItemDoc;
    }

    public JSpinner getSpnSleepTime() {
        return spnSleepTime;
    }

    public JSpinner getSpnErrorDistance() {
        return spnErrorDistance;
    }

    public JSpinner getSpnErrorAngle() {
        return spnErrorAngle;
    }

    public JSpinner getSpnStopDistance() {
        return spnStopDistance;
    }

    public JSpinner getSpnSonarAngle() {
        return spnSonarAngle;
    }

    public void setSpnSonarAngle(JSpinner spnSonarAngle) {
        this.spnSonarAngle = spnSonarAngle;
    }

}
