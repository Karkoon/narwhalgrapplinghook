package com.karkoon.grapplinghook.GameScene2D.Interface;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Align;
import com.karkoon.grapplinghook.GameScene2D.Score;

/**
 * Created by Roksana on 13.07.2016.
 */
public class Scoreboard extends Table {

    private Label coinScore;
    private Label distanceScore;
    private Label timeScore;

    private Score score;

    Scoreboard(Skin skin, Score score, float stageWidth) {
        this.score = score;
        Label.LabelStyle scoreStyle = new Label.LabelStyle(skin.get("default", Label.LabelStyle.class));
        Label.LabelStyle annotationStyle = skin.get("default-small", Label.LabelStyle.class);
        scoreStyle.background = new NinePatchDrawable(new NinePatch(skin.getPatch("field")));
        coinScore = createScoreLabel(score.getCoinScore(), scoreStyle, stageWidth);
        distanceScore = createScoreLabel(score.getDistanceScore(), scoreStyle, stageWidth);
        timeScore = createScoreLabel(score.getTimeElapsed(), scoreStyle, stageWidth);
        row();
        createScoreLabel("COINS", annotationStyle, stageWidth);
        createScoreLabel("DISTANCE", annotationStyle, stageWidth);
        createScoreLabel("TIME", annotationStyle, stageWidth);
        pack();
    }

    private Label createScoreLabel(String content, Label.LabelStyle style, float stageWidth) {
        Label label = new Label(content, style);
        label.setAlignment(Align.center);
        add(label).pad(10).width(stageWidth / 10f);
        return label;
    }

    private void updateScoreText() {
        coinScore.setText(score.getCoinScore());
        distanceScore.setText(score.getDistanceScore());
        timeScore.setText(score.getTimeElapsed());
    }

    @Override
    public void act(float delta) {
        updateScoreText();
        super.act(delta);
    }

    void saveScore(String id) {
        Preferences preferences = Gdx.app.getPreferences("com.karkoon.grapplinghooknarwhal.scores");
        preferences.putString(id, score.toString());
        preferences.flush();
        Gdx.app.log("Info", "Score saved under: '"+ id + "' with scores " + score.toString());
    }


}
