package com.karkoon.grapplinghook.GameScene2D.Game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Pc on 2016-05-04.
 * Twitter: Karkoon_
 */
class Platform extends Box2DActor {

    private static final Vector2 SIZE = new Vector2(2f, 0.5f);

    Platform(World world, AssetManager manager) {
        super(world, SIZE.x, SIZE.y, manager.get("game/badlogic.png", Texture.class), BodyDef.BodyType.StaticBody);
        for (Fixture fixture : getBody().getFixtureList()) {
            fixture.setRestitution(0.25f);
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }
}