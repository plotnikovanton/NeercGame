package com.kaliwe.neercgame.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kaliwe.neercgame.utils.ResourceUtils;

/**
 * Created by anton on 04.12.15.
 */
public class IntroScreen extends InputAdapter implements Screen {
    private BitmapFont font = ResourceUtils.getFont("visitor");
    private SpriteBatch sb;
    private Color color;
    private float timer;

    protected final Game game;

    public IntroScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        sb = new SpriteBatch();
        color = new Color(Color.WHITE);
        timer = 0;

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        timer += delta;

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        sb.begin();

        color = new Color(1, 1, 1, ((float) Math.sin(timer * 8) + 1) / 2);

        GlyphLayout layout = new GlyphLayout();
        layout.setText(font, "press any key to start");
        font.setColor(color);
        font.draw(sb, "press any key to start",
                Gdx.graphics.getWidth()/2 - layout.width / 2,
                Gdx.graphics.getHeight() / 2 - layout.height / 2);
        sb.end();

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
        sb.dispose();

    }

    @Override
    public boolean keyDown(int keyCode) {
        game.setScreen(new GameScreen());
        return true;
    }
}
