package com.karkoon.grapplinghook.GameScene2D.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Pc on 2016-05-04.
 * Twitter: Karkoon_
 */
class Player extends Box2DActor {

    private final static float MAX_SPEED = 10f;
    private final static Vector2 SIZE = new Vector2(0.5f, 0.5f);

    private ParticleEffect particleEffect = new ParticleEffect();

    Player(World world, AssetManager manager) {
        super(world, SIZE.x, SIZE.y, manager.get("game/narwhal.png", Texture.class), BodyDef.BodyType.DynamicBody);
        initializeParticleEffect(manager);
        float initialXForce = 600f;
        getBody().applyForceToCenter(initialXForce, 0, true);
        particleEffect.start();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        toFront();
        particleEffect.setPosition(getBody().getPosition().x, getBody().getPosition().y);
        particleEffect.update(Gdx.graphics.getDeltaTime());
        if (particleEffect.isComplete()) particleEffect.reset();
        limitVelocity();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        particleEffect.draw(batch);
        super.draw(batch, parentAlpha);
    }


    private void initializeParticleEffect(AssetManager manager) {
        particleEffect.load(Gdx.files.internal("game/greenParticle.txt"), Gdx.files.internal("game/"));
    }

    private void limitVelocity() {
        Vector2 velocity = getBody().getLinearVelocity();
        if (velocity.x > MAX_SPEED) getBody().setLinearVelocity(MAX_SPEED, velocity.y);
    }
}
