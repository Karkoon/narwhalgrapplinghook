package com.karkoon.grapplinghook.Ranking;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Created by @Karkoon on 2016-08-12.
 */
public class SpecializedScrollPane extends ScrollPane {

    public SpecializedScrollPane(Skin skin) {
        super(new EntryListTable(skin), skin);
        pack();
        setOverscroll(false, false);
    }

    public void fit(Stage stage) {
        if (getHeight() > stage.getHeight() * 0.9f) setSize(stage.getWidth() * 0.66f, stage.getHeight() * 0.9f);
        else setSize(stage.getWidth() * 0.75f, getPrefHeight());
        setPosition(stage.getWidth() / 2f - getWidth() / 2f, stage.getHeight() / 2f - getHeight() / 2f);
    }
}
