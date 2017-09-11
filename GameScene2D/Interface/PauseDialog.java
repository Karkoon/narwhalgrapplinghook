package com.karkoon.grapplinghook.GameScene2D.Interface;


import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.karkoon.grapplinghook.GameScene2D.States;

/**
 * Created by Pc on 2016-07-05.
 * Twitter: Karkoon_
 */
public class PauseDialog extends SpecializedDialog {

    private final static String TITLE = "Paused";

    PauseDialog(Skin skin, final States states) {
        super(TITLE, skin);
        TextButton unpause = new TextButton("Unpause", skin);
        unpause.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                states.running();
                return true;
            }
        });
        TextButton quit = new TextButton("Quit", skin);
        quit.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                states.quit();
                return true;
            }
        });
        addButton(unpause);
        addButton(quit);
        pack();
    }
}
