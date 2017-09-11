package com.karkoon.grapplinghook.Ranking;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.karkoon.grapplinghook.FirstGame;
import com.karkoon.grapplinghook.MainMenu.MenuScreen;
import com.karkoon.grapplinghook.GameScene2D.Game.Background;

/**
 * Created by @Karkoon on 2016-08-07.
 */
public class RankingScreen implements Screen {

    private SpriteBatch batch;
    private AssetManager manager;
    private Skin skin;
    private FirstGame game;
    private Stage stage;
    private SpecializedScrollPane pane;

    private Button button;
    public RankingScreen(SpriteBatch batch, AssetManager manager, Skin skin, FirstGame game) {
        this.batch = batch;
        this.manager = manager;
        this.skin = skin;
        this.game = game;
        stage = new Stage(new ScreenViewport(), batch);
        stage.addActor(new Background(manager));
        this.pane = new SpecializedScrollPane(skin);
        stage.addActor(pane);
        button = createReturnButton();
        stage.addActor(button);
    }

    private Button createReturnButton() {
        TextButton button = new TextButton("Return", skin, "default");
        button.setPosition(stage.getWidth() - stage.getWidth() / 6f, stage.getHeight() / 6f);
        button.pad(10);
        button.pack();
        button.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                dispose();
                game.setScreen(new MenuScreen(batch, manager, skin, game));
                return true;
            }
        });
        return button;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        pane.fit(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1 / 5f, 2 / 5f, 4 / 5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        button.toFront();
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
