package com.karkoon.grapplinghook.GameScene2D;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.karkoon.grapplinghook.FirstGame;
import com.karkoon.grapplinghook.MainMenu.MenuScreen;
import com.karkoon.grapplinghook.GameScene2D.Game.WorldStage;
import com.karkoon.grapplinghook.GameScene2D.Interface.InterfaceStage;


/**
 * Created by Pc on 2016-05-04.
 * Twitter: Karkoon_
 */
public class GameScreen extends ScreenAdapter implements States {

    public static final float VIRTUAL_HEIGHT = 10;
    public static final float VIRTUAL_WIDTH = VIRTUAL_HEIGHT * Gdx.graphics.getWidth() / Gdx.graphics.getHeight();
    private final InputMultiplexer multiplexer;
    private WorldStage worldStage;
    private InterfaceStage userInterface;
    private Score score;
    private SpriteBatch batch;
    private AssetManager manager;
    private Skin skin;
    private Music music;
    private FirstGame game;

    public GameScreen(AssetManager manager, Skin skin, FirstGame game) {
        this.manager = manager;
        this.skin = skin;
        this.game = game;
        score = new Score();
        batch = new SpriteBatch();
        worldStage = new WorldStage(batch, score, manager, this);
        userInterface = new InterfaceStage(batch, score, skin, this);
        multiplexer = new InputMultiplexer(userInterface, worldStage);
        setUpMusic();
    }

    private void setUpMusic() {
        music = Gdx.audio.newMusic(Gdx.files.internal("music.ogg"));
        music.setVolume(0.8f);
        music.setLooping(true);
        music.play();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(multiplexer);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1 / 5f, 2 / 5f, 4 / 5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        worldStage.act(delta);
        userInterface.act(delta);
        worldStage.draw();
        userInterface.draw();
        worldStage.getBatch().setColor(Color.WHITE);
    }

    @Override
    public void resize(int width, int height) {
        worldStage.getViewport().update(width, height, true);
        userInterface.getViewport().update(width, height, true);

    }

    @Override
    public void dispose() {
        worldStage.dispose();
        userInterface.dispose();
        music.dispose();
    }

    @Override
    public void pause() {
        super.pause();
        userInterface.pauseState();
    }

    @Override
    public void pauseState() {
        worldStage.pauseState();
    }

    @Override
    public void running() {
        worldStage.running();
    }

    @Override
    public void gameOver() {
        userInterface.gameOver();
    }

    @Override
    public void restart() {
        score.reset();
        multiplexer.removeProcessor(userInterface);
        userInterface = new InterfaceStage(batch, score, skin, this);
        multiplexer.addProcessor(userInterface);
        multiplexer.removeProcessor(worldStage);
        worldStage.restart();
        worldStage = new WorldStage(batch, score, manager, this);
        multiplexer.addProcessor(worldStage);
    }

    @Override
    public void quit() {
        dispose();
        worldStage.quit();
        game.setScreen(new MenuScreen(batch, manager, skin, game));
    }
}
