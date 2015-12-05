package com.kaliwe.neercgame;

import com.badlogic.gdx.Game;
import com.kaliwe.neercgame.screens.IntroScreen;

public class NeercGame extends Game {

    public NeercGame() {
    }

    @Override
    public void create () {
        setScreen(new IntroScreen(this));
    }

    @Override
    public void render () {
        super.render();
    }
}
