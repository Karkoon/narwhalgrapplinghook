package com.karkoon.grapplinghook.GameScene2D;

/**
 * Created by Pc on 2016-07-05.
 * Twitter: Karkoon_
 */
public interface States {

    void pauseState();

    void running();

    void gameOver();

    void restart();

    void quit();
}
