/**
 * Copyright (c) 2014 Saúl Piña <sauljabin@gmail.com>, Jorge Parra <thejorgemylio@gmail.com>.
 * <p>
 * This file is part of AriaJava P3DX.
 * <p>
 * AriaJava P3DX is licensed under The MIT License.
 * For full copyright and license information please see the LICENSE file.
 */

package app.animation;

import app.aria.architecture.aura.ArMisionPlanner;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Vector;

public class Animator implements Runnable {

    public static final int ZOOM_PROPORTION = 20;

    private Vector<Animated> animateds;
    private Vector<Animated> animatedsMouseListener;
    private int FPS;
    private boolean pause;
    private Thread thread;
    private boolean stop;
    private BufferedImage image;
    private Graphics2D graphics;
    private boolean antialiasing;
    private Canvas canvas;
    private int height;
    private int width;
    private int translateX;
    private int translateY;
    private int zoomW;
    private int zoomH;
    private int mouseX;
    private int mouseY;
    private BufferedImage backImage;
    private Graphics2D backGraphics;
    private ArMisionPlanner arMisionPlanner;
    private int strokeSize;
    private int yMouseAnimated;
    private int xMouseAnimated;
    private boolean enableMouseListenerAnimated;
    private InfoPanel infoPanel;

    public Animator(Canvas canvas) {
        this.canvas = canvas;
        FPS = 24;
        strokeSize = 1;
        animateds = new Vector<Animated>();
        animatedsMouseListener = new Vector<Animated>();
        initState();
        thread = new Thread(this);
        addMouseAdapter();
    }

    public int getStrokeSize() {
        return strokeSize;
    }

    public void setStrokeSize(int strokeSize) {
        this.strokeSize = strokeSize;
        if (graphics == null)
            return;
        BasicStroke stroke = new BasicStroke(strokeSize);
        graphics.setStroke(stroke);
    }

    public void antialiasing() {
        if (graphics != null) {
            if (antialiasing) {
                graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            } else {
                graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
                graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
            }
        }
    }

    public void setSize(int width, int height) {
        setHeight(height);
        setWidth(width);
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setZoom(double zoom) {
        zoomW += zoom;
        zoomH += zoom;
        if (zoomWidth() <= 0 || zoomHeight() <= 0) {
            zoomW -= zoom;
            zoomH -= zoom;
        }
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    private void addMouseAdapter() {
        canvas.addMouseWheelListener(new MouseAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                setZoom(-e.getPreciseWheelRotation());
            }
        });
        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    mouseX = e.getX();
                    mouseY = e.getY();
                }
            }
        });
        canvas.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    setTranslate(e.getX() - mouseX, e.getY() - mouseY);
                    mouseX = e.getX();
                    mouseY = e.getY();
                }
            }
        });
        canvas.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                refresh();
            }
        });
        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                animatorMouseListener(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                animatorMouseListener(e);
            }

        });
        canvas.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                animatorMouseListener(e);
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                animatorMouseListener(e);
            }
        });
    }

    public void initState() {
        width = 0;
        height = 0;
        translateX = offsetWidth();
        translateY = offsetHeight();
    }

    public int offsetWidth() {
        return zoomWidth() / 2;
    }

    public int offsetHeight() {
        return zoomHeight() / 2;
    }

    public int zoomWidth() {
        return (width + width * zoomW / ZOOM_PROPORTION);
    }

    public int zoomWidth(int width) {
        return (width + width * zoomW / ZOOM_PROPORTION);
    }

    public int zoomHeight() {
        return (height + height * zoomH / ZOOM_PROPORTION);
    }

    public int zoomHeight(int height) {
        return (height + height * zoomH / ZOOM_PROPORTION);
    }

    public boolean isAntialiasing() {
        return antialiasing;
    }

    public void setAntialiasing(boolean antialiasing) {
        this.antialiasing = antialiasing;
        antialiasing();
    }

    public Vector<Animated> getAnimateds() {
        return animateds;
    }

    public boolean isPause() {
        return pause;
    }

    public boolean isStop() {
        return stop;
    }

    public synchronized void addAnimated(Animated animated) {
        animateds.add(animated);
        animated.initAnimated();
        sortAnimateds();
        if (animated.getAnimatedMouseListener() != null)
            animatedsMouseListener.add(animated);
    }

    public synchronized void removeAnimated(Animated animated) {
        animateds.remove(animated);
        animatedsMouseListener.remove(animated);
    }

    public int getFPS() {
        return FPS;
    }

    public void setFPS(int FPS) {
        this.FPS = FPS;
    }

    public synchronized void refresh() {
        if (width > 0 && height > 0) {
            image = new BufferedImage(width, height, 1);
            graphics = (Graphics2D) image.getGraphics();
            graphics.setBackground(Color.WHITE);
            BasicStroke stroke = new BasicStroke(strokeSize);
            graphics.setStroke(stroke);
        }
        backImage = new BufferedImage(canvas.getWidth(), canvas.getHeight(), 1);
        backGraphics = (Graphics2D) backImage.getGraphics();
        backGraphics.setBackground(Color.GRAY);

        antialiasing();
    }

    public synchronized void start() {
        if (!thread.isAlive()) {
            refresh();
            thread.start();
        }
    }

    public synchronized void stop() {
        stop = true;
        pause = true;
        try {
            thread.join(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized void pause() {
        pause = true;
    }

    public synchronized void resume() {
        pause = false;
    }

    @Override
    public void run() {
        stop = false;
        pause = false;
        while (!stop) {
            rendering();
            if (!pause) {
                animate();
            }
            try {
                Thread.sleep(1000 / FPS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void animate() {
        for (int i = 0; i < animateds.size(); i++) {
            animateds.get(i).animate();
        }
    }

    public synchronized void rendering() {
        backGraphics.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        if (width > 0 && height > 0) {
            graphics.clearRect(0, 0, width, height);
            for (int i = 0; i < animateds.size(); i++) {
                Animated animate = animateds.get(i);
                if (!animate.isVisible())
                    continue;
                animate.paint(graphics);
            }
            backGraphics.drawImage(image, translateX - offsetWidth(), translateY - offsetHeight(), zoomWidth(), zoomHeight(), null);
        }
        if (infoPanel != null)
            infoPanel.paint(backGraphics);
        canvas.getGraphics().drawImage(backImage, 0, 0, null);
    }

    public synchronized void restart() {
        refresh();
        initAnimateds();
    }

    public synchronized void initAnimateds() {
        for (int i = 0; i < animateds.size(); i++) {
            animateds.get(i).initAnimated();
        }
    }

    public void setTranslate(int x, int y) {
        translateX = translateX + x;
        translateY = translateY + y;
    }

    public void removeAnimateds() {
        animateds.removeAllElements();
        animatedsMouseListener.removeAllElements();
    }

    public void setSizeAndRefresh(int width, int height) {
        setSize(width, height);
        refresh();
    }

    public void centerMap() {
        zoomH = 0;
        zoomW = 0;
        translateX = canvas.getWidth() / 2;
        translateY = canvas.getHeight() / 2;
    }

    public void showMap(ArMisionPlanner arMisionPlanner) {
        this.arMisionPlanner = arMisionPlanner;
        removeAnimateds();
        addAnimated(arMisionPlanner.getMap());
        if (arMisionPlanner.getStart() != null)
            addAnimated(arMisionPlanner.getStart());
        if (arMisionPlanner.getGoal() != null)
            addAnimated(arMisionPlanner.getGoal());
        setSize(arMisionPlanner.getMap().getCanvasWidth(), arMisionPlanner.getMap().getCanvasHeight());
        refresh();
    }

    public void updateMapProportion(int proportion) {
        if (arMisionPlanner == null)
            return;
        arMisionPlanner.getMap().setProportion(proportion);
        setSizeAndRefresh(arMisionPlanner.getMap().getCanvasWidth(), arMisionPlanner.getMap().getCanvasHeight());
        centerMap();
    }

    public synchronized BufferedImage getImage() {
        if (image == null)
            return null;
        BufferedImage bi = new BufferedImage(width, height, 1);
        bi.getGraphics().drawImage(image, 0, 0, null);
        return bi;
    }

    public synchronized void animatorMouseListener(MouseEvent e) {
        if (!enableMouseListenerAnimated)
            return;
        for (int i = 0; i < animatedsMouseListener.size(); i++) {
            Animated animated = animatedsMouseListener.get(i);
            if (animated.getShape() != null && animated.getAnimatedMouseListener() != null) {
                Shape tempShape = animated.getShape();

                int tempX = tempShape.getBounds().x;
                int tempY = tempShape.getBounds().y;
                int tempW = tempShape.getBounds().width;
                int tempH = tempShape.getBounds().height;

                if (e.getID() == MouseEvent.MOUSE_DRAGGED) {
                    tempW *= 5;
                    tempH *= 5;
                    tempX -= tempW / 2;
                    tempY -= tempH / 2;
                }

                double width = zoomWidth(tempW);
                double height = zoomHeight(tempH);

                double x = zoomWidth(tempX) + translateX - offsetWidth();
                double y = zoomHeight(tempY) + translateY - offsetHeight();

                Shape shape = new Rectangle2D.Double(x, y, width, height);

                if (shape.contains(e.getPoint())) {
                    if (e.getID() == MouseEvent.MOUSE_MOVED)
                        animated.getAnimatedMouseListener().mouseEntered();
                    else if (e.getID() == MouseEvent.MOUSE_DRAGGED && SwingUtilities.isLeftMouseButton(e)) {
                        xMouseAnimated = (int) ((e.getPoint().x - xMouseAnimated) * (double) e.getPoint().x / (double) zoomHeight(e.getPoint().x));
                        yMouseAnimated = (int) ((e.getPoint().y - yMouseAnimated) * (double) e.getPoint().x / (double) zoomHeight(e.getPoint().x));
                        animated.getAnimatedMouseListener().mouseDragged(xMouseAnimated, yMouseAnimated);
                        xMouseAnimated = e.getPoint().x;
                        yMouseAnimated = e.getPoint().y;
                    } else if (e.getID() == MouseEvent.MOUSE_PRESSED && SwingUtilities.isLeftMouseButton(e)) {
                        xMouseAnimated = e.getPoint().x;
                        yMouseAnimated = e.getPoint().y;
                        animated.getAnimatedMouseListener().mousePressed();
                    }
                } else {
                    animated.getAnimatedMouseListener().mouseExited();
                }
            }
        }
    }

    public synchronized void sortAnimateds() {
        for (int i = 1; i < animateds.size(); i++) {
            Animated aux = animateds.get(i);
            int j;
            for (j = i - 1; j >= 0 && animateds.get(j).getZ() > aux.getZ(); j--) {
                animateds.set(j + 1, animateds.get(j));
            }
            animateds.set(j + 1, aux);
        }
    }

    public void enableMouseListenerAnimated(boolean b) {
        enableMouseListenerAnimated = b;
    }

    public InfoPanel getInfoPanel() {
        return infoPanel;
    }

    public void setInfoPanel(InfoPanel infoPanel) {
        this.infoPanel = infoPanel;
    }

}
