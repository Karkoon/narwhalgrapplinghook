package com.karkoon.grapplinghook.GameScene2D.Game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.ListIterator;

/**
 * Created by Roksana on 11.07.2016.
 */
public class Background extends Group {

    private float lastStripe = 0;
    private ArrayList<Color> stripeColors = new ArrayList<Color>(Arrays.asList(Color.RED, Color.ORANGE, Color.YELLOW,
            Color.GREEN, Color.BLUE, Color.PURPLE, Color.PINK));
    private ListIterator<Color> colorIterator = stripeColors.listIterator();
    private AssetManager manager;

    public Background(AssetManager manager) {
        this.manager = manager;
    }

    private void createStripe(Color color, AssetManager manager) {
        Stripe stripe = new Stripe(lastStripe, getStage().getHeight(), getStage().getWidth() / 12, color, manager);
        addActor(stripe);
        this.lastStripe += getStage().getWidth() / 12f;
        setZIndex(0);
    }

    @Override
    public void act(float delta) {
        while (lastStripe <= getStage().getCamera().position.x + (getStage().getCamera().viewportWidth / 2f)) {
            if (colorIterator.hasNext()) createStripe(colorIterator.next(), manager);
                    else colorIterator = stripeColors.listIterator();
        }
        for (Actor actor : getChildren()) {
            if (getStage().getCamera().position.x - getStage().getWidth() > actor.getX()) {
                actor.remove();
            }
        }
        super.act(delta);
    }


    private class Stripe extends Actor {
        private SpriteDrawable sprite = new SpriteDrawable();
        private Color color;

        Stripe(float xPosition, float height, float width, Color color, AssetManager manager) {
            this.color = color;
            this.sprite.setSprite(new Sprite(manager.get("game/star.png", Texture.class)));
            setSize(width, height);
            setX(xPosition);
            float duration = 2;
            addAction(Actions.repeat(RepeatAction.FOREVER, Actions.sequence(
                    Actions.moveTo(getX(), getY() - height, duration),
                    Actions.moveTo(getX(), getY(), duration))));
        }

        @Override
        public void draw(Batch batch, float parentAlpha) {
            batch.setColor(color);
            sprite.draw(batch, getX(), getY(), getOriginX(), getOriginY(),
                    getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
        }
    }
}
