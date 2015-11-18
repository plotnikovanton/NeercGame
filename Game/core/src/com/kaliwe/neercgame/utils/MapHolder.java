package com.kaliwe.neercgame.utils;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.Body;
import com.kaliwe.neercgame.actors.Ground;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by anton on 18.11.15.
 */
public class MapHolder {
    public final List<Ground> ground;
    public final TiledMap map;

    public MapHolder(List<Body> grounds, TiledMap map) {
        this.ground = grounds.stream().map(Ground::new).collect(Collectors.toList());
        this.map = map;
    }
}
