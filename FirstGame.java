package com.karkoon.grapplinghook;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.karkoon.grapplinghook.Loading.LoadingScreen;

public class FirstGame extends Game {

	public AssetManager assetManager = new AssetManager();

	@Override
	public void create () {
		setScreen(new LoadingScreen(this));
	}

}
