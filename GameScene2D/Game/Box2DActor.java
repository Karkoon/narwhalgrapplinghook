package com.karkoon.grapplinghook.GameScene2D.Game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.karkoon.grapplinghook.GameScene2D.Box2DUtils.BodyBuilder;
import com.karkoon.grapplinghook.GameScene2D.Box2DUtils.FixtureDefBuilder;

/**
 * Created by Pc on 2016-05-04.
 * Twitter: Karkoon_
 */

abstract class Box2DActor extends Actor {

    private Body body;
    private SpriteDrawable sprite;

    Box2DActor(World world, float width, float height, Texture texture, BodyDef.BodyType type) {
        body = new BodyBuilder(world).
                fixedRotation()
                .fixture(new FixtureDefBuilder()
                        .boxShape(width, height)
                        .build())
                .type(type)
                .userData(this)
                .build();
        setSize(width * 2f, height * 2f); //box2d "grows" boxes from the middle so 1f width becomes 2 irl, scene2d doesn't so I need to normalize
        sprite =  new SpriteDrawable(new Sprite(texture));
        setOriginX(getOriginX() + getWidth() / 2f);
        setOriginY(getOriginY() + getHeight() / 2f);
        setUserObject(body);
    }

    @Override
    public void setPosition(float x, float y) {
        body.setTransform(x, y, 0);
        super.setPosition(x, y);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(Color.WHITE);
        flipSpriteAccordingToVelocity();
        sprite.draw(batch, getX(), getY(), getOriginX(), getOriginY(),
                getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        setX(body.getPosition().x - getWidth() / 2f);
        setY(body.getPosition().y - getHeight() / 2f);
    }

    Body getBody() {
        return body;
    }

    private void flipSpriteAccordingToVelocity() {
        Vector2 linearVelocity = body.getLinearVelocity();
        Sprite sprite = this.sprite.getSprite();
        if (linearVelocity.x < 0) sprite.setFlip(true, false);
        else if (linearVelocity.x != 0) sprite.setFlip(false, false);
    }

    @Override
    public boolean remove() {
        body.getWorld().destroyBody(body);
        body = null;
        return super.remove();
    }

}
