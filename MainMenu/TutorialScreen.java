package com.karkoon.grapplinghook.MainMenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.karkoon.grapplinghook.FirstGame;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Created by karkoon on 30.01.17.
 */
public class TutorialScreen extends ScreenAdapter {

    private Deque<Texture> panels = new ArrayDeque<>();

    private Texture currentPanel;
    private SpriteBatch batch;

    Stage stage;

    public TutorialScreen(final SpriteBatch batch, final AssetManager manager, final Skin skin, final FirstGame game) {

        this.batch = batch;
        stage = new Stage(new ScreenViewport(), batch);

        panels.push(manager.<Texture>get("tutorial/tutorialPanel4.jpg"));
        panels.push(manager.<Texture>get("tutorial/tutorialPanel3.jpg"));
        panels.push(manager.<Texture>get("tutorial/tutorialPanel2.jpg"));
        panels.push(manager.<Texture>get("tutorial/tutorialPanel1.jpg"));

        currentPanel = panels.pop();

        TextButton next = new TextButton("Next", skin);
        stage.addActor(next);
        next.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (!panels.isEmpty()) {
                    currentPanel = panels.pop();
                } else {
                    game.setScreen(new MenuScreen(batch, manager, skin, game));
                }
                return true;
            }
        });
        next.setPosition(stage.getWidth() - stage.getWidth() / 7f - 20, stage.getHeight() / 10f - 10);
        next.setSize(stage.getWidth() / 10, stage.getHeight() / 10);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        batch.draw(currentPanel, 0, 0, stage.getWidth(), stage.getHeight());
        batch.end();
        stage.draw();
        stage.act(delta);
    }

    @Override
    public void dispose() {
        super.dispose();
        stage.dispose();
    }
}
