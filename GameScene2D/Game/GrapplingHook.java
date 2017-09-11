package com.karkoon.grapplinghook.GameScene2D.Game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.joints.RopeJointDef;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

class GrapplingHook extends Actor {

    private static final Vector2 SIZE = new Vector2(0.25f * 2, 0.25f * 2); //to maintain consistency with Box2D bodies and avoid confusion SIZE is multiplied by 2
    private static final float ROTATION_SPEED = 5f;

    private SpriteDrawable sprite = new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("game/hook.png"))));
    private ShapeRenderer shapeRenderer = new ShapeRenderer();
    private Joint joint;
    private Color lineColor = new Color(Color.BROWN).add(-10 / 255f, -10 / 255f, -10 / 255f, 0);

    GrapplingHook() {
        setPosition(0, 0);
        setOrigin(SIZE.x / 2f, SIZE.y / 2f);
        setSize(SIZE.x, SIZE.y);
    }

    void createLineTo(Player player, Platform target) {
        RopeJointDef jointDef = new RopeJointDef();
        jointDef.collideConnected = true;
        jointDef.bodyA = player.getBody();
        jointDef.bodyB = target.getBody();
        jointDef.localAnchorA.set(0, 0); // box2d puts the anchor on -1, 0, dunno why
        jointDef.localAnchorB.set(evalSecondAnchor(target));
        jointDef.maxLength = determineLengthOfLine(player);
        joint = player.getBody().getWorld().createJoint(jointDef);
    }


    @Override
    public boolean remove() {
        if (isConnected()) {
            joint.getBodyA().getWorld().destroyJoint(joint);
        }
        setRotation(0);
        joint = null;
        return super.remove();
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x - getWidth() / 2f, y - getHeight() / 2f);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (isConnected()) {
            renderJoint();
        }
        sprite.draw(batch, getX(), getY(), getOriginX(), getOriginY(),
                getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        addAction(Actions.rotateBy(ROTATION_SPEED));
    }

    private boolean isConnected() {
        return joint != null && joint.getBodyB() != null && joint.getBodyA() != null;
    }

    private float determineLengthOfLine(Actor player) {
        Vector2 differenceInPosition = new Vector2(player.getX() - getX() + getWidth() / 2f, player.getY() - getY() + getHeight() / 2f);
        return (float) Math.hypot(differenceInPosition.x, differenceInPosition.y);
    }

    private Vector2 evalSecondAnchor(Platform target) {
        Vector2 position = new Vector2(getOriginX() - target.getWidth() / 2f, getOriginY() - target.getHeight() / 2f);
        position = localToStageCoordinates(position);
        position = target.stageToLocalCoordinates(position);
        return position;
    }

    private void renderJoint() {
        getStage().getBatch().end();
        shapeRenderer.setProjectionMatrix(getStage().getCamera().combined);
        shapeRenderer.setAutoShapeType(true);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(lineColor);
        shapeRenderer.rectLine(new Vector2(getX() + getWidth() / 2f, getY() + getHeight() / 2f), joint.getBodyA().getPosition(), 0.035f);
        shapeRenderer.end();
        getStage().getBatch().begin();
    }
}
