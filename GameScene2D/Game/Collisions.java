package com.karkoon.grapplinghook.GameScene2D.Game;


import com.badlogic.gdx.physics.box2d.*;
import com.karkoon.grapplinghook.GameScene2D.Score;

class Collisions implements ContactListener {

    private Score score;

    Collisions(Score score) {
        this.score = score;
    }

    @Override
    public void beginContact(Contact contact) {
        Body bodyA = contact.getFixtureA().getBody();
        Body bodyB = contact.getFixtureB().getBody();
        if (bodyB.getUserData() instanceof PowerUp && bodyA.getUserData() instanceof Player) { // 'instance of' because thats how libgdx/scene2d works
            float forceToApply = 50f;
            bodyA.applyForceToCenter(forceToApply, 0f, false);
            PowerUp userData = (PowerUp) bodyB.getUserData();
            userData.playSound();
            userData.setVisible(false); // == marking the powerUp to be removed
            score.incrementCoinScore();
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
