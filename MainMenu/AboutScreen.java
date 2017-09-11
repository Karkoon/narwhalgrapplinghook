package com.karkoon.grapplinghook.MainMenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.karkoon.grapplinghook.FirstGame;
import com.karkoon.grapplinghook.GameScene2D.Game.Background;

/**
 * Created by Pc on 2016-07-05.
 * Twitter: Karkoon_
 */
class AboutScreen extends ScreenAdapter {

    private Stage stage;
    private SpriteBatch batch;
    private AssetManager manager;
    private FirstGame game;
    private Skin skin;

    AboutScreen(SpriteBatch batch, AssetManager manager, Skin skin, FirstGame game) {
        stage = new Stage(new ScreenViewport(), batch);
        this.batch = batch;
        this.manager = manager;
        this.game = game;
        this.skin = skin;
        BackgroundTable table = new BackgroundTable(skin);
        table.add(createURLButton("Author's Twitter: @karkoon_", "https://twitter.com/Karkoon_"));
        table.row();
        table.add(createURLButton("Music's source:\n \"Brain Forest Werx \n» 8bit blix (aka Lost Moons)\" by Micah Young,\n compressed", "http://www.freesound.org/people/Questiion/sounds/166392/"));
        table.row();
        table.add(createURLButton("Music's license", "https://creativecommons.org/licenses/by/3.0/"));
        table.row();
        table.add(createURLButton("Font's Source: \"MR Pixel\" by Christophe Badani", "https://fontlibrary.org/en/font/mr-pixel"));
        table.row();
        table.add(createURLButton("Font's License: OFL 1.1", "http://scripts.sil.org/OFL"));
        table.row();
        table.add(createURLButton("Game Made using libGDX", "https://libgdx.badlogicgames.com/"));
        table.pad(10);
        table.pack();
        stage.addActor(new Background(manager));
        stage.addActor(table);
        stage.addActor(createReturnButton());
        table.setPosition(stage.getWidth() / 2f - table.getWidth() / 2f, stage.getHeight() / 2f - table.getHeight() / 2f);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        super.show();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1 / 5f, 2 / 5f, 4 / 5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
        super.render(delta);
    }

    private Actor createReturnButton() {
        TextButton button = new TextButton("Return", skin, "default");
        button.setPosition(stage.getWidth() - stage.getWidth() / 5f, stage.getHeight() / 6f);
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

    private Actor createURLButton(String text, final String URL) {
        TextButton button = new TextButton(text, skin, "default");
        button.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.net.openURI(URL);
                return true;
            }
        });
        button.pad(10);
        return button;
    }

    @Override
    public void dispose() {
        stage.dispose();
        super.dispose();
    }
}
