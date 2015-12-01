package com.kaliwe.neercgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.kaliwe.neercgame.stages.GameStage;
import com.kaliwe.neercgame.stages.Level1;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by anton on 18.11.15.
 */
public class GameScreen implements Screen {
    private Iterator<Class<? extends GameStage>> iter;
    private GameStage stage;

    public GameScreen() {
        iter = new ArrayList<Class<? extends GameStage>>() {{
            //add(Level0.class);
            add(Level1.class);
        }}.iterator();
        iter.next();
        stage = new Level1();
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (stage.isNext()) {
            try {
                stage = iter.next().newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
            Gdx.input.setInputProcessor(stage);
        } else if (stage.isFailed()) {
            stage.dispose();
            try {
                stage = stage.getClass().newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
            Gdx.input.setInputProcessor(stage);
        }
        stage.draw();
        stage.act(delta);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
