package com.kaliwe.neercgame.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.kaliwe.neercgame.screens.GameStateManager;
import com.kaliwe.neercgame.utils.HUDUtils;
import com.kaliwe.neercgame.utils.ResourceUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by anton on 04.12.15.
 */
public class SaveScore extends Stage {
    protected TextField textField;
    protected static final float VIEWPORT_WIDTH = 20 * 20;
    protected static final float VIEWPORT_HEIGHT = 13 * 20;
    protected BitmapFont defaultFont;
    protected TextField.TextFieldStyle tfStyle;
    protected Label.LabelStyle labelStyle;
    protected Label label;

    public SaveScore() {
        Gdx.gl.glClearColor(0,0,0,1);
        this.defaultFont = ResourceUtils.getFont("visitor");
        setupCamera();
        setupLabel();
        setupTextField();
    }

    private void sendToServer(String name) throws NoSuchAlgorithmException {
        // generate data
        String score = HUDUtils.totalScoreFormatter.format(GameStateManager.totalScore);
        String time = HUDUtils.simpleDateFormat.format((long)GameStateManager.totalTime * 1000);
        StringBuilder toEncode = new StringBuilder("kaliweproductioninaction");
        toEncode.append(score);
        toEncode.append(time);
        toEncode.append(name);

        String data = toEncode.toString();
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(data.getBytes());

        byte[] messageDigestMD5 = md.digest();
        StringBuffer stringBuffer = new StringBuffer();
        for (byte bytes : messageDigestMD5) {
            stringBuffer.append(String.format("%02x", bytes & 0xff));
        }


        System.out.println("In: " + data);
        System.out.println("Out: " + stringBuffer.toString());
    }

    protected void setupTextField() {
        tfStyle = new TextField.TextFieldStyle();
        tfStyle.font = defaultFont;
        tfStyle.fontColor = Color.WHITE;
        textField = new TextField("", tfStyle);

        float height = defaultFont.getCapHeight();

        textField.setPosition(VIEWPORT_WIDTH - 250/2, VIEWPORT_HEIGHT);
        textField.setAlignment(1);
        textField.setSize(250, height);
        textField.setFocusTraversal(true);
        textField.setBlinkTime(0.2f);

        textField.setTextFieldListener((textField1, c) -> {
            if (c == 13) {
                System.out.println(123);
                try {
                    sendToServer(textField1.getText());
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
            }
        });

        addActor(textField.debug());
        setKeyboardFocus(textField);
    }

    protected void setupLabel() {
        labelStyle = new Label.LabelStyle(defaultFont, Color.LIGHT_GRAY);
        label = new Label("Set your name", labelStyle);
        label.setPosition(VIEWPORT_WIDTH, VIEWPORT_HEIGHT + 100, 1);
        addActor(label);
    }

    protected void setupCamera() {
        getCamera().viewportHeight = VIEWPORT_HEIGHT;
        getCamera().viewportWidth = VIEWPORT_WIDTH;
        getCamera().update();
    }

}
