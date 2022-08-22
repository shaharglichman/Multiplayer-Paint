package com.example.lib.MultiplayerPaint;

import static com.example.lib.MultiplayerPaint.GamePanelMultiplayerPaint.images;
import static com.example.lib.MultiplayerPaint.GamePanelMultiplayerPaint.margin;
import static com.example.lib.MultiplayerPaint.GamePanelMultiplayerPaint.mousex;
import static com.example.lib.MultiplayerPaint.GamePanelMultiplayerPaint.mousey;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class DrawPagePanel extends JPanel {
    public static BufferedImage image;
    public static ColorChooserButton colorChooser = new ColorChooserButton(Color.white);
    public static ImageCls imageCls;
    public static ArrayList<ImageCls> imageClsArrayList = new ArrayList<>();
    private static Color newC;
    private static JFileChooser jFileChooser;
    private static JFileChooser saveFileChooser;
    private static JButton openFileBtn;
    private final JButton savebtn;
    private int currentX, currentY, oldX, oldY;
    private Graphics2D g2;
    private JLabel pickColorLbl;

    DrawPagePanel() {
        this.setPreferredSize(new Dimension(500, 500));
        this.setDoubleBuffered(false);
        this.setBackground(Color.white);
        this.setFocusable(true);
        this.addMouseListener(new DrawPagePanel.Mouse());
        this.addMouseMotionListener(new DrawPagePanel.Mouse());

        pickColorLbl = new JLabel("Pick color:");
        this.add(pickColorLbl);

        this.add(colorChooser);
        savebtn = new JButton("Save");
        this.add(savebtn);
        setSavebtn();

        openFileBtn = new JButton("Open file");
        this.add(openFileBtn);

        jFileChooser = new JFileChooser();
        jFileChooser.setCurrentDirectory(new File("C:"));
        jFileChooser.setFileFilter(new FileNameExtensionFilter("PNG images", "png", "jpg", "jpeg"));

        saveFileChooser = new JFileChooser();
        saveFileChooser.setCurrentDirectory(new File("C:"));
        saveFileChooser.setFileFilter(new FileNameExtensionFilter("PNG images", "png", "jpg", "jpeg"));
        imageCls = new ImageCls();
        imageCls.setSaveOpenbtn();
    }

    private void createGrid(Graphics g) {
        g.setColor(Color.black);
        for (int x = 0; x < 500 / margin; x++) {
            g.drawLine(0, x * margin, 500, x * margin);
            for (int y = 0; y < 500 / margin; y++) {
                g.drawLine(y * margin, 0, y * margin, 500);
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        createGrid(g);

        for (int i = 0; i < imageClsArrayList.size(); i++) {
            imageCls.x = mousex;
            imageCls.y = mousey;
            imageCls.draw(g);
        }
    }

    private void setSavebtn() {
        savebtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ev) {
                imageCls.save();
                GamePanelMultiplayerPaint.myWindow.dispose();
            }
        });
    }

    public class ImageCls {
        BufferedImage image;
        int x;
        int y;

        ImageCls() {
            this.x = mousex;
            this.y = mousey;
        }

        private void draw(Graphics g) {
            if (image == null) {
                image = (BufferedImage) createImage(getSize().width, getSize().height);
                g2 = (Graphics2D) image.getGraphics();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                clear();
            }

            g.drawImage(image, 0, 0, null);
            images.add(image);

            colorChooser.addColorChangedListener(new ColorChooserButton.ColorChangedListener() {
                @Override
                public void colorChanged(Color newColor) {
                    newC = newColor;

                    if (newC == null) {
                        g2.setPaint(Color.black);
                    } else {
                        g2.setPaint(newC);
                        //System.out.println(newC);
                    }
                }
            });
        }

        public void clear() {
            g2.setPaint(Color.white);
            g2.fillRect(0, 0, getSize().width, getSize().height);
            repaint();
        }


        private void save() {
            int rv = saveFileChooser.showSaveDialog(null);
            if (rv == JFileChooser.APPROVE_OPTION) {
                try {
                    ImageIO.write((RenderedImage) image, "png", saveFileChooser.getSelectedFile());
                    System.out.println("s");
                    repaint();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("f");
            }
        }

        private void setSaveOpenbtn() {
            openFileBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ev) {
                    int rv = jFileChooser.showOpenDialog(null);
                    if (rv == JFileChooser.APPROVE_OPTION) {
                        try {
                            image = ImageIO.read(jFileChooser.getSelectedFile());
                            System.out.println("s");
                            repaint();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println("f");
                    }
                }
            });
        }
    }

    public class Mouse implements MouseListener, MouseMotionListener {

        @Override
        public void mouseClicked(MouseEvent mouseEvent) {

        }

        @Override
        public void mousePressed(MouseEvent mouseEvent) {
            oldX = mouseEvent.getX();
            oldY = mouseEvent.getY();
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
            currentX = mouseEvent.getX();
            currentY = mouseEvent.getY();

            if (g2 != null) {
                g2.setStroke(new BasicStroke(10));
                g2.drawLine(oldX, oldY, currentX, currentY);
                repaint();
                oldX = currentX;
                oldY = currentY;
            }
        }

        @Override
        public void mouseMoved(MouseEvent mouseEvent) {
        }
    }
}
