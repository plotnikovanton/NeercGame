package com.kaliwe.neercgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Created by anton on 18.11.15.
 */
public class GameScreen implements Screen {
    private Stage stage;
    private static double totalScore = 0;
    private static float totalTime = 0;
    public static boolean lock;

    public GameScreen() {
        Gdx.gl.glClearColor(135/255f,206/255f,235/255f,1);
    }

    public static double getTotalScore() {
        return totalScore;
    }

    public static float getTotalTime() {
        return totalTime;
    }

    @Override
    public void show() {
        //stage = new SaveScore();
        //Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (GameStateManager.next) {
            try {
                stage = GameStateManager.getNext();
            } catch (IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        }
        GameStateManager.act(delta);

        stage.act(delta);
        stage.draw();
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
