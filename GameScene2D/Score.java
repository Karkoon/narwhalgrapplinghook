package com.karkoon.grapplinghook.GameScene2D;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.StringBuilder;

/**
 * Created by Pc on 2016-05-19.
 * Twitter: Karkoon_
 */
public class Score {

    public void setDistanceScore(float playerXPosition) {
        this.distanceScore = playerXPosition / 10;
    }

    public void incrementCoinScore() {
        this.coinScore++;
    }

    void reset() {
        distanceScore = 0;
        coinScore = 0;
        timeElapsed = 0;
    }

    public void updateTimeElapsed() {
        timeElapsed += Gdx.graphics.getDeltaTime();
    }

    public String getDistanceScore() {
        return Integer.toString((int) Math.floor(distanceScore * 10f - 6)); // 6 is the position of player so we are compensating
    }

    public String getCoinScore() {
        return Integer.toString((int) coinScore * 10);
    }
// TODO: 2016-08-12 cache strings instead of constructing them every frame
    public String getTimeElapsed() {
        StringBuilder builder = new StringBuilder(6);
        builder.append((int) timeElapsed / 60);
        builder.append(":");
        builder.append(String.format("%02d", (int) timeElapsed % 60));
        return builder.toString();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(getDistanceScore());
        builder.append(" ");
        builder.append(getCoinScore());
        builder.append(" ");
        builder.append(getTimeElapsed());
        return builder.toString();
    }

    private float distanceScore;
    private float coinScore;
    private float timeElapsed;
}
