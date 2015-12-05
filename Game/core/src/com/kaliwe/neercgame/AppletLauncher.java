package com.kaliwe.neercgame;

import com.badlogic.gdx.backends.lwjgl.LwjglApplet;

/**
 * Created by anton on 05.12.15.
 */
public class AppletLauncher extends LwjglApplet {
    private static final long serialVersionUID = 1L;
    public AppletLauncher()
    {
        super(new NeercGame());
    }

}
