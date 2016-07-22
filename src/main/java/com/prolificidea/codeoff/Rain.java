package com.prolificidea.codeoff;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.List;
import java.util.ArrayList;

public class Rain extends JPanel {

    public static List<String> TWITTER_RAIN = new ArrayList();
    public static List<String> THE_MATRIX = new ArrayList();

    private Drop[] drops;
    private double drawSizeHeight;
    private double drawSizeWidth;

    public Rain(double height, double width) {

        THE_MATRIX.add(new StringBuilder("BLONDE").reverse().toString());
        THE_MATRIX.add(new StringBuilder("REDHEAD").reverse().toString());
        THE_MATRIX.add(new StringBuilder("BRUNETTE").reverse().toString());
        //THE_MATRIX.add("DRESS RED THE IN LADY THE");
        THE_MATRIX.add(new StringBuilder("THE LADY IN THE RED DRESS").reverse().toString());

        setDrawSizeHeight(height);
        setDrawSizeWidth(width);

        drops = new Drop[(int) (drawSizeWidth / Config.FONT_SIZE)];
        for (int i = 0; i < drops.length; i++) {
            drops[i] = new Drop(i * Config.FONT_SIZE, (int) drawSizeHeight, TWITTER_RAIN);
        }

        try {
            URL soundFile = getClass().getClassLoader().getResource("matrix03.wav");
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.loop(100000);
            clip.start();

        } catch (Exception e){
            System.out.printf("Oh no audio");
        }
    }

    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.black);

        ((Graphics2D) g).setBackground(Color.black);

        g.fillRect(0, 0, (int) drawSizeHeight, (int) drawSizeWidth);

        g.setColor(Color.black);

        Font font = new Font("Monospaced", Font.PLAIN, Config.FONT_SIZE);

        g2.setFont(font);

        g2.setBackground(Color.black);

        for (int i = 0; i < drops.length; i++) {
            if (drops[i].isOffScreen()) {
                drops[i] = new Drop(i * Config.FONT_SIZE, (int) drawSizeHeight, TWITTER_RAIN);
            }
            drops[i].draw(g2);
        }

        try {
            Thread.sleep(10);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        repaint();
    }

    public void setDrawSizeHeight(double drawSize) {
        this.drawSizeHeight = drawSize;
    }

    public void setDrawSizeWidth(double drawSizeWidth) {
        this.drawSizeWidth = drawSizeWidth;
    }
}