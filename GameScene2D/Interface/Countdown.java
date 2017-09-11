package com.karkoon.grapplinghook.GameScene2D.Interface;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.karkoon.grapplinghook.GameScene2D.States;

/**
 * Created by @Karkoon on 2016-08-06.
 */
class Countdown extends Label {

    private States states;

    Countdown(LabelStyle style, States states) {
        super("3", style);
        this.states = states;
        addAction(Actions.sequence(Actions.repeat(3, Actions.sequence(Actions.fadeIn(0.5f),
                Actions.fadeOut(0.5f), new SetTextToCountdownNumberSequenceAction(this))),
                Actions.removeActor()));
    }

    @Override
    public boolean remove() {
        states.running();
        return super.remove();
    }

    private class SetTextToCountdownNumberSequenceAction extends Action {

        private Label label;
        private int countDown = 2;

        SetTextToCountdownNumberSequenceAction(Label label) {
            this.label = label;
        }

        @Override
        public boolean act(float delta) {
            label.setText(Integer.toString(countDown));
            countDown--;
            return true;
        }
    }
}
