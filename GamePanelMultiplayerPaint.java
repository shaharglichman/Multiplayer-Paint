package com.example.lib.MultiplayerPaint;

import static com.example.lib.MultiplayerPaint.DrawPagePanel.image;
import static com.example.lib.MultiplayerPaint.DrawPagePanel.imageCls;
import static com.example.lib.MultiplayerPaint.DrawPagePanel.imageClsArrayList;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanelMultiplayerPaint extends JPanel implements ActionListener {
    static final int SCREEN_WIDTH = 800;
    static final int SCREEN_HEIGHT = 800;
    static final int DELAY = 175;
    public static int margin = 50;
    public static DrawPageFrame myWindow;
    public static ArrayList<Cube> cubes;
    public static int mouseX;
    public static int mouseY;
    public static int mousex;
    public static int mousey;
    public static ArrayList<Image> images;
    public static HashMap<Integer, Integer> coordinates = new HashMap<>();
    private static Color Cubecolor;
    Timer timer;
    boolean running = false;
    private Cube c;

    GamePanelMultiplayerPaint() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.white);
        this.setFocusable(true);
        this.addMouseListener(new GamePanelMultiplayerPaint.Mouse());
        this.addMouseMotionListener(new GamePanelMultiplayerPaint.Mouse());
        startGame();
        cubes = new ArrayList<>();
        Cubecolor = Color.white;

        images = new ArrayList<>();
    }

    public void createGrid(Graphics g) {
        g.setColor(Color.black);
        for (int x = 0; x < SCREEN_HEIGHT / margin; x++) {
            g.drawLine(0, x * margin, SCREEN_WIDTH, x * margin);
            for (int y = 0; y < SCREEN_WIDTH / margin; y++) {
                g.drawLine(y * margin, 0, y * margin, SCREEN_HEIGHT);
                c = new Cube(x * margin, y * margin, Cubecolor, image);
                cubes.add(c);
                Rectangle cR = new Rectangle(x * margin, y * margin, 50, 50);

                Rectangle mouse = new Rectangle();
                mouse.x = mouseX;
                mouse.y = mouseY;
                mouse.width = 1;
                mouse.height = 1;

                if (mouse.intersects(cR)) {
                    c.color = Color.gray;
                    repaint();
                }

                for (int i = 0; i < imageClsArrayList.size(); i++) {
                    g.drawImage(imageClsArrayList.get(i).image, imageClsArrayList.get(i).x, imageClsArrayList.get(i).y, 49, 49, null);
                    repaint();
                }

            }
        }
    }

    private void startGame() {
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Cube cube : cubes) {
            cube.draw(g);
        }
        createGrid(g);
    }


    public static class Mouse implements MouseListener, MouseMotionListener {

        @Override
        public void mouseClicked(MouseEvent mouseEvent) {
            myWindow = new DrawPageFrame();

            mousex = mouseEvent.getX();
            mousey = mouseEvent.getY();
            int x = mouseX;
            int onex = (x) % 10;
            int tensx = (x / 10) % 10 * 10;
            int finalx = mouseX - tensx - onex;
            if (tensx > 50)
                finalx += 50;

            int y = mousey;
            int oney = (y) % 10;
            int tensy = (y / 10) % 10 * 10;
            int finaly = mousey - tensy - oney;
            if (tensy > 50)
                finaly += 50;

            mousex = finalx + 1;
            mousey = finaly + 1;
            System.out.println(finalx);
            System.out.println(finaly);
            coordinates.put(mousex, mousey);
            imageClsArrayList.add(imageCls);

        }

        @Override
        public void mousePressed(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseReleased(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseEntered(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseExited(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseDragged(MouseEvent mouseEvent) {


        }

        @Override
        public void mouseMoved(MouseEvent mouseEvent) {
            GamePanelMultiplayerPaint.mouseX = mouseEvent.getX();
            GamePanelMultiplayerPaint.mouseY = mouseEvent.getY();
        }
    }

    public static class Cube {
        int x;
        int y;
        Color color;
        Image image;

        Cube(int x, int y, Color color, Image image) {
            this.x = x;
            this.y = y;
            this.color = color;
            this.image = image;
        }

        private void draw(Graphics g) {
            g.setColor(color);
            g.fillRect(x, y, margin, margin);
        }
    }
}