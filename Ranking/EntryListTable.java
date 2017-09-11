package com.karkoon.grapplinghook.Ranking;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.karkoon.grapplinghook.MainMenu.BackgroundTable;

import java.util.Comparator;
import java.util.Map;


/**
 * Created by @Karkoon on 2016-08-12.
 */
public class EntryListTable extends BackgroundTable {

    private Preferences preferences = Gdx.app.getPreferences("com.karkoon.grapplinghooknarwhal.scores");
    private Array<ScoreEntry> entries;
    private Skin skin;

    public EntryListTable(Skin skin) {
        super(skin);
        this.skin = skin;
        entries = new Array<ScoreEntry>();
        loadSavedEntries();
        createTableHead();
        addEntriesToTable();
        pack();
    }

    private void addEntriesToTable() {
        for (ScoreEntry entry : entries) {
            addEntry(entry);
        }
    }

    private void loadSavedEntries() {
        Map<String, String> map = (Map<String, String>) preferences.get();
        entries.clear();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            entries.add(new ScoreEntry(entry));
        }
    }

    private void addEntry(ScoreEntry entry) {
        Label nameLabel = createLabel(entry.id);
        Label distanceLabel = createLabel(Integer.toString(entry.distance));
        Label coinScoreLabel = createLabel(Integer.toString(entry.coins));
        Label timeElapsedLabel = createLabel(entry.time);
        TextButton deleteButton = createDeleteButton(entry.id);
        int padding = 15;
        add(nameLabel).pad(padding, 0, padding, 0);
        add(distanceLabel).pad(padding, 0, padding, 0);
        add(coinScoreLabel).pad(padding, 0, padding, 0);
        add(timeElapsedLabel).pad(padding, 0, padding, 0);
        add(deleteButton).pad(padding, 0, padding, 0);
        row();
    }

    private TextButton createDeleteButton(final String key) {
        TextButton button = new TextButton("Delete", skin);
        button.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                preferences.remove(key);
                preferences.flush();
                loadSavedEntries();
                update();
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        return button;
    }

    private boolean nameFromHighestToLowest = true;
    private boolean distanceFromHighestToLowest = true;
    private boolean coinFromHighestToLowest = true;
    private boolean timeFromHighestToLowest = true;

    private void createTableHead(){
        TextButton name = new TextButton("Name", skin);
        name.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                entries.sort(new Comparator<ScoreEntry>() {
                    @Override
                    public int compare(ScoreEntry o1, ScoreEntry o2) {
                        if (nameFromHighestToLowest) return o1.id.compareToIgnoreCase(o2.id);
                        else return o2.id.compareToIgnoreCase(o1.id);
                    }
                });
                update();
                nameFromHighestToLowest = !nameFromHighestToLowest;
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        TextButton distance = new TextButton("Distance", skin);
        distance.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                entries.sort(new Comparator<ScoreEntry>() {
                    @Override
                    public int compare(ScoreEntry o1, ScoreEntry o2) {
                        if (distanceFromHighestToLowest)
                            return o1.distance > o2.distance ? 1 : o1.distance == o2.distance ? 0 : -1;
                        else
                            return o1.distance < o2.distance ? 1 : o1.distance == o2.distance ? 0 : -1;
                    }
                });
                update();
                distanceFromHighestToLowest = !distanceFromHighestToLowest;
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        TextButton coins = new TextButton("Coins", skin);
        coins.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                entries.sort(new Comparator<ScoreEntry>() {
                    @Override
                    public int compare(ScoreEntry o1, ScoreEntry o2) {
                        if (coinFromHighestToLowest)
                            return o1.coins > o2.coins ? 1 : o1.coins == o2.coins ? 0 : -1;
                        else
                            return o1.coins < o2.coins ? 1 : o1.coins == o2.coins ? 0 : -1;
                    }
                });
                update();
                coinFromHighestToLowest = !coinFromHighestToLowest;
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        TextButton time = new TextButton("Time", skin);
        time.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                entries.sort(new Comparator<ScoreEntry>() {
                    @Override
                    public int compare(ScoreEntry o1, ScoreEntry o2) {
                        if (timeFromHighestToLowest) return -o1.time.compareTo(o2.time);
                        else return o1.time.compareTo(o2.time);
                    }
                });
                update();
                timeFromHighestToLowest = !timeFromHighestToLowest;
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        add(name).pad(10);
        add(distance).pad(10);
        add(coins).pad(10);
        add(time).pad(10);
        row();
    }

    private void update() {
        clearChildren();
        createTableHead();
        addEntriesToTable();
    }

    private Label createLabel(String content) {
        Label label = new Label(content, skin.get("default", Label.LabelStyle.class));
        label.setAlignment(Align.center);
        return label;
    }

    private class ScoreEntry {

        String id;
        int coins;
        int distance;
        String time;

        ScoreEntry(Map.Entry<String, String> entry) {
            this.id = entry.getKey();
            String[] values = entry.getValue().split(" ");
            distance = Integer.parseInt(values[0]);
            coins = Integer.parseInt(values[1]);
            time = values[2];
        }

        @Override
        public boolean equals(Object obj) {
            ScoreEntry entry = (ScoreEntry) obj;
            return this.id.equals(entry.id);
        }

    }

}
