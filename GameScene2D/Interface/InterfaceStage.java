package com.karkoon.grapplinghook.GameScene2D.Interface;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.karkoon.grapplinghook.GameScene2D.Score;
import com.karkoon.grapplinghook.GameScene2D.States;


/**
 * Created by Pc on 2016-05-17.
 * Twitter: Karkoon_
 */
public class InterfaceStage extends Stage implements States {

    private Skin skin;
    private GameOverDialog gameOverDialog;
    private Dialog pauseDialog;
    private Score score;
    private Scoreboard scoreboard;

    private States states;

    private boolean gameOver = false;
    private boolean firstFrameElapsed = true;

    public InterfaceStage(SpriteBatch batch, Score score, Skin skin, States states) {
        super(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()), batch);
        this.score = score;
        this.states = states;
        this.skin = skin;
        createGameHud();
    }

    @Override
    public void draw() {
        super.draw();
        if (firstFrameElapsed) {
            gameOver = true;
            pauseState();
            gameOver = false;
            Countdown countdown = new Countdown(skin.get("default-big", Label.LabelStyle.class), this);
            addActor(countdown);
            countdown.setPosition(getWidth() / 2f - (countdown.getWidth() / 2f), getHeight() / 2f - (countdown.getHeight() / 2f));
            firstFrameElapsed = false;
        }
    }

    private void createGameHud() {
        scoreboard = new Scoreboard(skin, score, getWidth());
        scoreboard.setPosition(getWidth() / 20f, getHeight() / 20f);
        addActor(scoreboard);
        addActor(createPauseButton());
    }

    private Actor createPauseButton() {
        TextButton pause = new TextButton("Pause", skin);
        pause.setPosition(getWidth() - getWidth() / 7f, getHeight() / 10f);
        pause.pad(10);
        pause.pack();
        pause.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                pauseState();
                return true;
            }
        });
        return pause;
    }

    @Override
    public void pauseState() {
        if (!gameOver && pauseDialog == null) {
            pauseDialog = new PauseDialog(skin, this);
            pauseDialog.show(this);
        }
        states.pauseState();
    }

    @Override
    public void running() {
        if (pauseDialog != null) {
            pauseDialog.hide();
            pauseDialog = null;
        }
        states.running();
    }

    @Override
    public void gameOver() {
        gameOverDialog = new GameOverDialog(skin, this, scoreboard);
        gameOverDialog.show(this);
        gameOver = true;
    }

    @Override
    public void restart() {
        gameOverDialog.hide();
        states.restart();
        gameOver = false;
    }

    @Override
    public void quit() {
        dispose();
        states.quit();
    }
}
