package com.experiment;

/**
 * Created by yongduan on 2017/1/14.
 */
public class Rating {
    private int userId;
    private int itemId;
    private double rating;
    private double avgRating;
    private double guessRating;
    private long timeStamp;
    private long timeRelease;
    private long timeActive;
    private double timeWeight;
    private double avgTimeWeight;

    public Rating() {
    }

    public Rating(int userId, int itemId, double rating, long timeStamp, long timeRelease, long timeActive) {
        this.userId = userId;
        this.itemId = itemId;
        this.rating = rating;
        this.timeStamp = timeStamp;
        this.timeRelease = timeRelease;
        this.timeActive = timeActive;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public long getTimeRelease() {
        return timeRelease;
    }

    public void setTimeRelease(long timeRelease) {
        this.timeRelease = timeRelease;
    }

    public double getGuessRating() {
        return guessRating;
    }

    public void setGuessRating(double guessRating) {
        this.guessRating = guessRating;
    }

    public double getTimeWeight() {
        return timeWeight;
    }

    public void setTimeWeight(double timeWeight) {
        this.timeWeight = timeWeight;
    }

    public double getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(double avgRating) {
        this.avgRating = avgRating;
    }

    public double getAvgTimeWeight() {
        return avgTimeWeight;
    }

    public void setAvgTimeWeight(double avgTimeWeight) {
        this.avgTimeWeight = avgTimeWeight;
    }

    public long getTimeActive() {
        return timeActive;
    }

    public void setTimeActive(long timeActive) {
        this.timeActive = timeActive;
    }
}
