package com.example.lib.MultiplayerPaint;
import javax.swing.JFrame;

public class GameFrameMultiplayerPaint extends JFrame{
    GameFrameMultiplayerPaint(){
        this.add(new GamePanelMultiplayerPaint());
        this.pack();
        this.setTitle("Multiplayer Paint");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
