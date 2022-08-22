package com.example.lib.MultiplayerPaint;
import javax.swing.JFrame;

public class DrawPageFrame extends JFrame {
    DrawPageFrame(){
        this.add(new DrawPagePanel());
        this.pack();
        this.setTitle("Draw paint");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
