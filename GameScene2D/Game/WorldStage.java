package com.karkoon.grapplinghook.GameScene2D.Game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.karkoon.grapplinghook.GameScene2D.GameScreen;
import com.karkoon.grapplinghook.GameScene2D.Score;
import com.karkoon.grapplinghook.GameScene2D.States;


public class WorldStage extends Stage implements States {

    private static final Vector2 GRAVITY = new Vector2(0, -5.0f);

    private final World world = new World(GRAVITY, true);
    private final Score score;
    private Player player;
    private Array<Actor> actors = new Array<Actor>();
    private float lastPowerUp = 6.5f;
    private float lastPlatform = 6.5f;
    private AssetManager assetManager;

    private boolean isPaused = false;
    private com.karkoon.grapplinghook.GameScene2D.States states;

    public WorldStage(SpriteBatch batch, Score score, AssetManager manager, States gameStates) {
        super(new ExtendViewport(GameScreen.VIRTUAL_WIDTH, GameScreen.VIRTUAL_HEIGHT, 0, GameScreen.VIRTUAL_HEIGHT), batch);
        this.assetManager = manager;
        createBackground();
        createNewPlayer();
        createBox2DActors();
        this.score = score;
        world.setContactListener(new Collisions(this.score));
        this.states = gameStates;
    }

    @Override
    public void act(float delta) {
        if (!isPaused) {
            score.setDistanceScore(player.getX());
            score.updateTimeElapsed();
            updateCameraPosition();
            removeAndCreateActors();
            checkLoseCondition();
            world.step(1 / 60f, 6, 2);
            super.act(delta);
        }
    }

    @Override
    public void pauseState() {
        isPaused = true;
    }

    @Override
    public void running() {
        isPaused = false;
    }

    @Override
    public void gameOver() {
        pauseState();
        player.remove();
        player = null;
        states.gameOver();
    }

    @Override
    public void restart() {
        quit();
    }

    @Override
    public void quit() {
        dispose();
    }

    private void checkLoseCondition() {
        if (isActorNotVisible(player)) {
            gameOver();
        }
    }

    private void updateCameraPosition() {
        float moveRate = 0.06f;
        Vector3 cameraPosition = getCamera().position;
        if (cameraPosition.x < player.getX()) {
            cameraPosition.set(player.getX() + moveRate, cameraPosition.y, 0);
        } else {
            cameraPosition.add(moveRate, 0, 0);
        }
    }

    private void placeActorsBatch(float startingPosition, int lowerBound, int upperBound, Box2DActor[] box2DActor) {
        float offset = 4 + 2; //the '4' is the width of a platform, less than that they overlap, + 2 is the real offset
        float xPosition = startingPosition;
        for (Box2DActor actor : box2DActor) {
            xPosition += offset;
            float yPosition = (float) (lowerBound + (Math.random() * ((upperBound - lowerBound) + 1)));
            actor.setPosition(xPosition, yPosition);
        }
    }

    private void removeAndCreateActors() {
        for (Actor actor : actors) {
            if (isActorNotVisible(actor)) {
                actor.remove();
                actors.removeValue(actor, true);
            }
        }
        if (lastPlatform < getCamera().position.x + getCamera().viewportWidth) {
            createBox2DActors();
        }
    }

    private void createBackground() {
        Background background = new Background(assetManager);
        background.setZIndex(0);
        addActor(background);
    }

    private void createBox2DActors() {
        int numberOfColumns = 5; // a column is a pair of a powerUp and a Platform, they are aligned
        Box2DActor[] powerUps = new Box2DActor[numberOfColumns];
        Box2DActor[] platforms = new Box2DActor[numberOfColumns];
        for (int i = 0; i < numberOfColumns; i++) {
            Platform platform = new Platform(world, assetManager);
            platform.setZIndex(1);
            addActor(platform);
            platforms[i] = platform;
            PowerUp powerUp = new PowerUp(world, assetManager);
            powerUp.setZIndex(2);
            addActor(powerUp);
            powerUps[i] = powerUp;
        }
        placeActorsBatch(lastPlatform, (int) (getHeight() - getHeight() / 3f), (int) (getHeight() - getHeight() / 6f), platforms);
        placeActorsBatch(lastPowerUp, (int) (getHeight() - getHeight() / 1.5f), (int) (getHeight() - getHeight() / 1.9f), powerUps);
        this.lastPlatform = platforms[platforms.length - 1].getX();
        this.lastPowerUp = powerUps[powerUps.length - 1].getX();
        actors.addAll(platforms);
        actors.addAll(powerUps);
    }

    private boolean isActorNotVisible(Actor actor) {
        boolean left = actor.getX() + actor.getWidth() < getCamera().position.x - getWidth() / 2f;
        boolean down = actor.getY() + actor.getHeight() < getCamera().position.y - getHeight() / 2f;
        boolean offCamera = left || down;
        return !actor.isVisible() || offCamera;
    }

    private void createNewPlayer() {
        player = new Player(world, assetManager);
        player.setPosition(6.5f, 4f);
        addListener(new GrapplingListener(this, player));
        addActor(player);
        setKeyboardFocus(player);
    }
}
