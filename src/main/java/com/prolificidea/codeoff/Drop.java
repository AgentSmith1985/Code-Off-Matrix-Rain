package com.prolificidea.codeoff;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Drop {

    public static final int MAX_LEN = 130;
    private Random random = new Random();
    private int velocity, height, length, x, y;
    private char[][] text;
    private List<String> tweets;
    private boolean isTweet = false;

    Drop(int x, int y, List<String> tweets) {

        this.x = x;
        height = y;

        int randomNumber = getRandomInteger(0, 100000);

        if (randomNumber < 30001) {
            if (tweets != null && tweets.size() > 0) {
                int randomTweet = getRandomInteger(0, tweets.size() - 1);
                try {
                    this.tweets = tweets;
                    length = tweets.get(randomTweet).length();
                    text = createTweetContent(randomTweet);
                    velocity = 2;
                } catch (Exception e) {
                    length = getRandomInteger(3, MAX_LEN);
                    text = createContent(length);
                    velocity = getRandomInteger(0, 6);
                }
            } else {
                length = getRandomInteger(3, MAX_LEN);
                text = createContent(length);
                velocity = getRandomInteger(0, 6);
            }
        } else {
            length = getRandomInteger(3, MAX_LEN);
            text = createContent(length);
            velocity = getRandomInteger(0, 6);
        }

        this.y = (-1) * (length + getRandomInteger(1,10)) * Config.FONT_SIZE;
        this.tweets = tweets;
    }

    protected char[][] createContent(int length) {

        char[][] result = new char[length][1];

        for (int i = 0; i < result.length; i++) {
            result[i][0] = getRandomCharacter();
        }

        return result;
    }

    protected char[][] createTweetContent(int randomTweet) {

        char[] text = new StringBuilder(tweets.get(randomTweet)).toString().toCharArray();
        char[][] result = new char[length][1];

        for (int i = 0; i < length; i++) {
            if (i < tweets.get(randomTweet).length()) {
                if (length == tweets.get(randomTweet).length()) {
                    result[i][0] = text[i];
                    isTweet = true;
                }
            } else
                result[i][0] = getRandomCharacter();
        }

        return result;
    }

    public void draw(Graphics2D g2) {
        int fontSize = g2.getFont().getSize();

        for (int i = 0; i < length; i++) {
            if (!isTweet) {
                if (getRandomInteger(0, length) == i) {
                    text[i][0] = getRandomCharacter();
                }

                if (i == length - 1) {
                    g2.setColor(Color.GREEN);
                } else {
                    try {
                        g2.setColor(new Color(0, 255, 0, (i * 2)));
                    } catch (IllegalArgumentException iae) {
                        g2.setColor(new Color(0, 255, 0));
                    }
                }
            } else {
                g2.setColor(Color.RED);
            }
            g2.drawChars(text[i], 0, 1, x, y + (i * fontSize));
        }

        y += velocity;
    }

    public char getRandomCharacter() {
        return (char) ('だ' + (int) (Math.random() * 100));
    }

    public int getRandomInteger(int min, int max) {
        return random.nextInt((max - min) + 1) + min;
    }

    public boolean isOffScreen() {
        return (y > this.height);
    }
}