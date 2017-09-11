package com.karkoon.grapplinghook.MainMenu;

import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

/**
 * Created by @Karkoon on 09.08.2016.
 */
public class BackgroundTable extends Table {
    public BackgroundTable(Skin skin) {
        super(skin);
        setBackground(new NinePatchDrawable(new NinePatch(skin.getPatch("field"))));
    }

    @Override
    public Cell row() {
        Cell cell = super.row();
        cell.pad(10);
        return cell;
    }
}

