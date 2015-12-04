package com.kaliwe.neercgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.kaliwe.neercgame.stages.*;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by anton on 04.12.15.
 */
public class GameStateManager {
    private static Iterator<Class<? extends GameStage>> iter = new ArrayList<Class<? extends GameStage>>() {{
        add(Level0.class);
            add(Level1.class);
            add(Level2.class);
            add(Level3.class);
            add(Level6.class);
    }}.iterator();
    private static Class<? extends GameStage> last;
    private static Stage lastInstance;

    public static boolean timeLock = false;

    public static boolean next = true;
    public static boolean failed = false;
    public static boolean sureComplete = true;

    public static short score = 0;
    public static short maxScore = 1;

    public static double totalScore = 0;
    public static float totalTime = 0;

    public static Stage getNext() throws IllegalAccessException, InstantiationException {
        if (lastInstance != null) lastInstance.dispose();
        if (!failed) {
            if (sureComplete) {
                if (iter.hasNext()) {
                    last = iter.next();
                    lastInstance = last.newInstance();
                } else {
                    lastInstance = new SaveScore();
                }
            } else {
                lastInstance = new BetweenStagesStage(score, maxScore, ((GameStage)lastInstance).text);
            }
        } else {
            timeLock = false;
            lastInstance = last.newInstance();
        }
        failed = false;
        next = false;
        //totalTime += lastInstance.getTime();
        Gdx.input.setInputProcessor(lastInstance);
        return lastInstance;
    }

    public static void failed(short score1, short maxScore1) {
        score = score1;
        maxScore = maxScore1;
        failed = true;
        next = true;
    }

    public static void complete(short score1, short maxScore1) {
        next = true;
        sureComplete = false;
        score = score1;
        maxScore = maxScore1;
        timeLock = true;
    }

    public static void sureComplete(short score1, short maxScore1) {
        next = true;
        sureComplete = true;
        score = score1;
        maxScore = maxScore1;
        timeLock = false;
        totalScore += (double) score / (double) maxScore;
    }

    public static void act(float delta) {
        if (!timeLock) {
            totalTime += delta;
        }
    }
}
