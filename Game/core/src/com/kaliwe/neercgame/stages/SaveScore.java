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
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

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
    protected Label label1;
    private boolean flag = true;

    public SaveScore() {
        Gdx.gl.glClearColor(0,0,0,1);
        this.defaultFont = ResourceUtils.getFont("visitor");
        setupCamera();
        setupLabel();
        setupTextField();
    }

    private void sendToServer(String name) throws NoSuchAlgorithmException {
        if (!flag) return;
        flag = false;
        System.out.println("gotit");
        // generate data
        String score = HUDUtils.totalScoreFormatter.format(GameStateManager.totalScore);
        String time = Integer.toString(Math.round(GameStateManager.totalTime));
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


        //System.out.println("In: " + data);
        //System.out.println("Out: " + stringBuffer.toString());

        // Send post request
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://kaliwe.ru:1488");
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("score", score));
        nvps.add(new BasicNameValuePair("time", time));
        nvps.add(new BasicNameValuePair("name", name));
        nvps.add(new BasicNameValuePair("md5", stringBuffer.toString()));
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
            CloseableHttpResponse response2 = httpClient.execute(httpPost);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                try {
                    sendToServer(textField1.getText().toLowerCase());
                    textField1.setDisabled(true);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
            }
        });

        addActor(textField);
        setKeyboardFocus(textField);
    }

    protected void setupLabel() {
        labelStyle = new Label.LabelStyle(defaultFont, Color.LIGHT_GRAY);
        label = new Label("Set your name", labelStyle);
        label.setPosition(VIEWPORT_WIDTH, VIEWPORT_HEIGHT + 100, 1);
        addActor(label);

        label1 = new Label("Total score: "+ HUDUtils.totalScoreFormatter.format(GameStateManager.totalScore)
                + "\nTotal time: " + HUDUtils.simpleDateFormat.format(GameStateManager.totalTime * 1000)
                , labelStyle);
        label1.setPosition(VIEWPORT_WIDTH, VIEWPORT_HEIGHT-50, 1);
        addActor(label1);
    }

    protected void setupCamera() {
        getCamera().viewportHeight = VIEWPORT_HEIGHT;
        getCamera().viewportWidth = VIEWPORT_WIDTH;
        getCamera().update();
    }

}
