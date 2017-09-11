package com.karkoon.grapplinghook.GameScene2D.Interface;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;

/**
 * Created by @Karkoon on 2016-08-07.
 */
class SaveScoreDialog extends SpecializedDialog {

    SaveScoreDialog(Skin skin, final Scoreboard scoreboard) {
        super("Add a Score!", skin);
        final TextField field = new TextField("Me", skin);
        field.setAlignment(Align.center);
        field.setMaxLength(12);
        getContentTable().add(field).pad(50);
        TextButton button = new TextButton("Save", skin);
        button.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                scoreboard.saveScore(field.getText());
                hide();
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        TextButton button1 = new TextButton("Return", skin);
        button1.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                hide();
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        addButton(button);
        addButton(button1);
        pack();
    }
}
