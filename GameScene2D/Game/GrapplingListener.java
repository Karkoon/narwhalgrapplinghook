package com.karkoon.grapplinghook.GameScene2D.Game;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Created by Pc on 2016-06-08.
 * Twitter: Karkoon_
 */
class GrapplingListener extends InputListener {

    private final Player player;
    private final Stage stage;

    private GrapplingHook grapplingHook;

    GrapplingListener(Stage stage, Player player) {
        this.stage = stage;
        this.player = player;
        grapplingHook = new GrapplingHook();
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        switch (pointer) {
            case 0:
                stage.addActor(grapplingHook);
                grapplingHook.setPosition(x, y);
                for (Actor target : stage.getActors()) {
                    if (target instanceof Platform && areIntersecting(grapplingHook, target)) {
                        grapplingHook.createLineTo(player, (Platform) target);
                    }
                }
                return true;
        }
        return false;
    }

    @Override
    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        grapplingHook.remove();
    }

    private boolean areIntersecting(Actor actor, Actor target) {
        float xmin = actor.getX();
        float xmax = xmin + actor.getWidth();
        float ymin = actor.getY();
        float ymax = ymin + actor.getHeight();
        return ((xmax > target.getX() && xmin < target.getX() + target.getWidth()))
                && ((ymax > target.getY() && ymin < target.getY() + target.getHeight())); // the important part
    }
}



