package com.karkoon.grapplinghook.GameScene2D.Interface;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.karkoon.grapplinghook.GameScene2D.States;

/**
 * Created by Pc on 2016-07-05.
 * Twitter: Karkoon_
 */
class GameOverDialog extends SpecializedDialog {

    private final static String TITLE = "Game Over";

    GameOverDialog(final Skin skin, final States states, final Scoreboard scoreboard) {
        super(TITLE, skin);
        TextButton restart = new TextButton("Restart", skin, "default");
        restart.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                states.restart();
                return true;
            }
        });
        TextButton quit = new TextButton("Quit", skin, "default");
        quit.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                states.quit();
                return true;
            }
        });
        final TextButton saveScore = new TextButton("Save Score", skin, "default");
        saveScore.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                new SaveScoreDialog(skin, scoreboard).show(getStage());
                saveScore.setTouchable(Touchable.disabled);
                saveScore.setDisabled(true);
                return true;
            }
        });
        addButton(restart);
        addButton(quit);
        addButton(saveScore);
        addScoreboard(scoreboard);
        pack();
    }

    private void addScoreboard(Scoreboard scoreboard) {
        getContentTable().add(scoreboard).pad(10);
    }

}
