package com.karkoon.grapplinghook.GameScene2D.Interface;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;

/**
 * Created by Pc on 2016-07-10.
 * Twitter: Karkoon_
 */
abstract class SpecializedDialog extends Dialog {

    SpecializedDialog(String title, Skin skin) {
        super(title, skin);
        setMovable(false);
        setResizable(false);
        pad(50);
        getTitleLabel().setAlignment(Align.center);
    }

    Cell<Button> addButton(Button button) {
        button.pad(10, 20, 10, 20);
        return getButtonTable().add(button);
    }


}
