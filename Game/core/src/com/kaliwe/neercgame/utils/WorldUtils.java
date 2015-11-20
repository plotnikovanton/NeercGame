package com.kaliwe.neercgame.utils;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.*;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.kaliwe.neercgame.box2d.FootUserData;
import com.kaliwe.neercgame.box2d.GroundUserData;
import com.kaliwe.neercgame.box2d.PlayerUserData;
import com.kaliwe.neercgame.box2d.SimpleEnemyUserData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anton on 18.11.15.
 */
public class WorldUtils {
    public static World createWorld() {
        return new World(Constants.WORLD_GRAVITY, true);
    }

    public static Body createPlayer(World world) {
        BodyDef bodyDef = new BodyDef();

        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.fixedRotation = true;
        bodyDef.position.set(new Vector2(Constants.PLAYER_X, Constants.PLAYER_Y));
        bodyDef.gravityScale = Constants.PLAYER_GRAVITY_SCALE;

        // Main shape
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(Constants.PLAYER_WIDTH / 2, Constants.PLAYER_HEIGHT / 2);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.friction = 0;
        fixtureDef.density = Constants.PLAYER_DENSITY;

        Body body = world.createBody(bodyDef);
        body.setUserData(new PlayerUserData());
        body.createFixture(fixtureDef);
        //body.resetMassData();

        // Foot sensor
        fixtureDef = new FixtureDef();
        shape.setAsBox(Constants.PLAYER_WIDTH / 2 - 0.04f , Constants.FOOT_HEIGHT,
                       new Vector2(0f, -Constants.PLAYER_HEIGHT / 2 - Constants.FOOT_HEIGHT), 0f);
        //fixtureDef.isSensor = true;
        fixtureDef.shape = shape;
        body.createFixture(fixtureDef).setUserData(new FootUserData());

        shape.dispose();
        return body;
    }

    public static MapHolder createMap(World world, String mapName) {
        java.util.List<Body> bodies = new ArrayList<>();
        TiledMap tiledMap = new TmxMapLoader().load(mapName);

        // Create ground
        MapObjects ground = tiledMap.getLayers().get("GrndObj").getObjects();

        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        Shape shape = null;
        for (MapObject obj : ground) {
            if (obj instanceof TextureMapObject) continue;
            if (obj instanceof RectangleMapObject) {
                shape = getRectangle((RectangleMapObject)obj);
            } else if (obj instanceof PolygonMapObject) {
                shape = getChain((PolygonMapObject)obj);
            }

            bodyDef.type = BodyDef.BodyType.StaticBody;
            fixtureDef.friction = Constants.WORLD_FRICTION;
            fixtureDef.shape = shape;
            fixtureDef.isSensor = false;
            Body body = world.createBody(bodyDef);
            body.setUserData(new GroundUserData());
            body.createFixture(fixtureDef);
            bodies.add(body);

            shape.dispose();
        }

        return new MapHolder(bodies, tiledMap);
    }

    public static List<Body> createSimpleEnemy(World world, TiledMap tiledMap) {
        List<Body> res = new ArrayList<>();

        for (MapObject o : tiledMap.getLayers().get("Simple").getObjects()) {
            ChainShape chain = getPolyline((PolylineMapObject) o);

            BodyDef bodyDef = new BodyDef();
            FixtureDef fixtureDef = new FixtureDef();

            //bodyDef.fixedRotation = true;
            bodyDef.type = BodyDef.BodyType.DynamicBody;
            Vector2 position = new Vector2();
            chain.getVertex(0, bodyDef.position);

            PolygonShape shape = new PolygonShape();
            // TODO: replace with constants
            shape.setAsBox(1f, 1f, new Vector2(Constants.PPM / 2, 0.5f), 0);
            fixtureDef.shape = shape;

            Body body = world.createBody(bodyDef);
            body.createFixture(fixtureDef);
            body.setUserData(new SimpleEnemyUserData(chain));

            res.add(body);
        }

        return res;
    }

    public static ChainShape getPolyline(PolylineMapObject polylineMapObject) {
        float[] raw = polylineMapObject.getPolyline().getTransformedVertices();
        float[] wordVertices = new float[raw.length];

        for (int i = 0; i < raw.length; i++) {
            wordVertices[i] = raw[i] / Constants.PPM;
        }

        ChainShape shape = new ChainShape();
        shape.createChain(wordVertices);

        return shape;
    }

    public static PolygonShape getRectangle(RectangleMapObject rectangleObject) {
        Rectangle rectangle = rectangleObject.getRectangle();
        PolygonShape polygon = new PolygonShape();
        Vector2 size = new Vector2((rectangle.x + rectangle.width * 0.5f) / Constants.PPM,
                (rectangle.y + rectangle.height * 0.5f ) / Constants.PPM);
        polygon.setAsBox(rectangle.width * 0.5f / Constants.PPM,
                rectangle.height * 0.5f / Constants.PPM,
                size,
                0.0f);
        return polygon;
    }

    public static CircleShape getCircle(CircleMapObject circleObject) {
        Circle circle = circleObject.getCircle();
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(circle.radius / Constants.PPM);
        circleShape.setPosition(new Vector2(circle.x / Constants.PPM, circle.y / Constants.PPM));
        return circleShape;
    }


    public static ChainShape getChain(PolygonMapObject polygonObject) {
        ChainShape chain = new ChainShape();
        float[] vertices = polygonObject.getPolygon().getTransformedVertices();

        float[] worldVertices = new float[vertices.length];

        for (int i = 0; i < vertices.length; ++i) {
            System.out.println(vertices[i]);
            worldVertices[i] = vertices[i] / Constants.PPM;
        }

        chain.createChain(worldVertices);
        return chain;
    }


    public static PolygonShape getPolygon(PolygonMapObject polygonObject) {
        PolygonShape polygon = new PolygonShape();
        float[] vertices = polygonObject.getPolygon().getTransformedVertices();

        float[] worldVertices = new float[vertices.length];

        for (int i = 0; i < vertices.length; ++i) {
            System.out.println(vertices[i]);
            worldVertices[i] = vertices[i] / Constants.PPM;
        }

        polygon.set(worldVertices);
        return polygon;
    }

}
