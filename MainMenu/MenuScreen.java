package com.karkoon.grapplinghook.MainMenu;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.karkoon.grapplinghook.FirstGame;
import com.karkoon.grapplinghook.GameScene2D.Game.Background;
import com.karkoon.grapplinghook.GameScene2D.GameScreen;
import com.karkoon.grapplinghook.Ranking.RankingScreen;

/**
 * Created by Pc on 2016-05-15.
 * Twitter: Karkoon_
 */
public class MenuScreen extends ScreenAdapter {

    private Stage stage;

    public MenuScreen(final SpriteBatch batch, final AssetManager manager, final Skin skin, final FirstGame game) {
        stage = new Stage(new ScreenViewport(), batch);
        TextButton start = new TextButton("Play", skin, "default-big");
        start.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new GameScreen(manager, skin, game));
                return true;
            }
        });

        TextButton exit = new TextButton("Exit", skin, "default-big");
        exit.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.exit();
                return true;
            }
        });
        TextButton about = new TextButton("About", skin, "default-big");
        about.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new AboutScreen(batch, manager, skin, game));
                dispose();
                return true;
            }
        });
        TextButton scores = new TextButton("Scores", skin, "default-big");
        scores.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new RankingScreen(batch, manager, skin, game));
                dispose();
                return true;
            }
        });
        TextButton tutorial = new TextButton("Tutorial", skin, "default-big");
        tutorial.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new TutorialScreen(batch, manager, skin, game));
                dispose();
                return true;
            }
        });
        Table window = new Table(skin);
        window.center();
        window.setBackground(new NinePatchDrawable(new NinePatch(skin.getPatch("field"))));
        float buttonWidth = stage.getWidth() / 2.5f;
        window.add(start).width(buttonWidth).pad(10);
        window.row();
        window.add(tutorial).width(buttonWidth).pad(10);
        window.row();
        window.add(scores).width(buttonWidth).pad(10);
        window.row();
        window.add(about).width(buttonWidth).pad(10);
        window.row();
        window.add(exit).width(buttonWidth).pad(10);
        window.pad(30, 50, 30, 50);
        window.setSize(window.getPrefWidth(), window.getPrefHeight());
        window.setPosition(stage.getWidth() / 2 - window.getWidth() / 2f, stage.getHeight() / 2 - window.getHeight() / 2f);
        stage.addActor(window);
        Background background = new Background(manager);
        stage.addActor(background);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1 / 5f, 2 / 5f, 4 / 5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

}
