package com.karkoon.grapplinghook.GameScene2D.Game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Created by Pc on 2016-05-14.
 * Twitter: Karkoon_
 */
public class PowerUp extends Box2DActor {
    private static final Vector2 SIZE = new Vector2(0.125f * 2f, 0.25f * 2);

    private static Sound sound;

    PowerUp(World world, AssetManager manager) {
        super(world, SIZE.x, SIZE.y, manager.get("game/coin.png", Texture.class), BodyDef.BodyType.StaticBody);
        getBody().getFixtureList().get(0).setSensor(true);
        sound = manager.get("game/coin.wav");
    }

    void playSound() {
        float volume = 1f;
        sound.play(volume);
    }

}
