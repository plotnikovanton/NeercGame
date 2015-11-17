package com.kaliwe.neercgame;

import com.badlogic.gdx.Game;
import com.kaliwe.neercgame.screens.GameScreen;

public class NeercGame extends Game {

    @Override
    public void create () {
        setScreen(new GameScreen());
    }

    @Override
    public void render () {
        super.render();
    }
}
