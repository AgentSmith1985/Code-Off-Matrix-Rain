package com.prolificidea.codeoff;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.util.ArrayList;

/**
 * Created by christoff.smith on 2016/07/22.
 */
public class FetchTwitterFeed {
    public static void makeItRainTwitter(final Rain rain) {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        initTwitterConfigBuilder(cb);
        Twitter twitter = new TwitterFactory(cb.build()).getInstance();
        Query query = new Query("#ICJ16");
        query.count(5);

        int numberOfTweets = 180;
        long lastID = Long.MAX_VALUE;
        ArrayList<Status> tweets = new ArrayList<Status>();

        while (tweets.size() < numberOfTweets) {

            rain.TWITTER_RAIN = new ArrayList();
            rain.TWITTER_RAIN.addAll(rain.THE_MATRIX);

            lastID = getLatestTweets(twitter, query, lastID, tweets);

            readInTweets(rain, tweets);

            try {
                Thread.sleep(5000);
            } catch (InterruptedException ie) {
                //Ah heck!
            }
        }
    }

    private static void readInTweets(Rain rain, ArrayList<Status> tweets) {
        for (int i = 0; i < tweets.size(); i++) {

            Status t = (Status) tweets.get(i);

            String user = t.getUser().getScreenName();
            String msg = t.getText();
            System.out.println(i + " USER: " + user + " wrote: " + msg + "\n");

            StringBuilder newMessage = new StringBuilder();

            if (msg != null)
            {
                if (user != null) {
                    msg = user + " wrote: " + msg;
                }

                msg = cleanTweet(msg, newMessage);

                rain.TWITTER_RAIN.add(msg);

                System.out.println(i + " " + msg + "\n");
            }
        }
    }

    private static String cleanTweet(String msg, StringBuilder newMessage) {
        msg = msg.replaceAll("\uD83D\uDE09", "");

        if (msg.contains("https")) {
            int posToDelete = msg.indexOf("https");
            msg = msg.substring(0 , posToDelete);
        }

        if (msg.length() > Drop.MAX_LEN) {
            msg = msg.substring(0, Drop.MAX_LEN);
        }

        msg = msg.trim();

        String[] message = msg.split(" ");
        for (int j = message.length - 1; j > -1; j--) {

            newMessage.append(new StringBuilder(message[j]).reverse().toString());
            newMessage.append(" ");
        }

        msg = newMessage.toString().trim();
        return msg;
    }

    private static long getLatestTweets(Twitter twitter, Query query, long lastID, ArrayList<Status> tweets) {
        try {
            QueryResult result = twitter.search(query);
            tweets.addAll(result.getTweets());
            System.out.println("Gathered " + tweets.size() + " tweets" + "\n");
            for (Status t : tweets)
                if (t.getId() < lastID)
                    lastID = t.getId();

        } catch (TwitterException te) {
            System.out.println("Couldn't connect: " + te);
        }
        query.setMaxId(lastID - 1);
        return lastID;
    }

    private static void initTwitterConfigBuilder(ConfigurationBuilder cb) {
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("VyiUoBfUD3mny9gTFZE5BJWh1")
                .setOAuthConsumerSecret("tLdd2Pgtrvd5D4yYilmXOii87rBlonbksX7oINF0hgfDc3FEKy")
                .setOAuthAccessToken("932350375-5PUXcZyV9ruDjToHncZT44PQb8Ew8Yqmp30tWnXD")
                .setOAuthAccessTokenSecret("5WxlpZs8aG87i75ZjPPFtniwrQIm7mmQ8JZyrIICA2Vcb");
    }
}
