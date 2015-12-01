package com.kaliwe.neercgame;

import com.badlogic.gdx.Game;
import com.kaliwe.neercgame.screens.GameScreen;

public class NeercGame extends Game {

    @Override
    public void create () {
        //Gdx.gl.glClearColor(135/255f, 206/255f, 235/255f, 1);
        //Gdx.gl.glClearColor(1f, 1f, 1f, 1);
        setScreen(new GameScreen());
    }

    @Override
    public void render () {
        super.render();
    }
}
