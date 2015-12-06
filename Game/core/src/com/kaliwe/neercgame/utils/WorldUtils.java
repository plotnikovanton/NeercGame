package com.kaliwe.neercgame.utils;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.*;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.kaliwe.neercgame.actors.RainCloud;
import com.kaliwe.neercgame.box2d.*;
import com.kaliwe.neercgame.stages.GameStage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anton on 18.11.15.
 */
public class WorldUtils {
    public static World createWorld() {
        return new World(Constants.WORLD_GRAVITY, true);
    }

    public static Body createPlayer(World world, TiledMap tiledMap, MapHolder mapHolder) {
        BodyDef bodyDef = new BodyDef();

        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.fixedRotation = true;
        bodyDef.position.set(new Vector2(Constants.PLAYER_X, Constants.PLAYER_Y));
        bodyDef.gravityScale = Constants.PLAYER_GRAVITY_SCALE;

        // Spawn point
        Ellipse circle = ((EllipseMapObject) tiledMap.getLayers().get("spawn").getObjects().get("spawn")).getEllipse();
        bodyDef.position.set(circle.x / Constants.PPM, circle.y / Constants.PPM);
        mapHolder.spawn = new Vector2(bodyDef.position);

        // Main shape
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(Constants.PLAYER_WIDTH / 2, Constants.PLAYER_HEIGHT / 2);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.friction = 0;
        fixtureDef.density = Constants.PLAYER_DENSITY;
        fixtureDef.filter.categoryBits = Mask.PLAYER;


        Body body = world.createBody(bodyDef);
        body.setUserData(new PlayerUserData());
        body.createFixture(fixtureDef).setUserData(body.getUserData());
        //body.resetMassData();

        // Foot
        fixtureDef = new FixtureDef();
        shape.setAsBox(Constants.PLAYER_WIDTH / 2 - 0.03f , Constants.FOOT_HEIGHT,
                       new Vector2(0f, -Constants.PLAYER_HEIGHT / 2 - Constants.FOOT_HEIGHT), 0f);
        //fixtureDef.isSensor = true;
        fixtureDef.shape = shape;
        body.createFixture(fixtureDef).setUserData(new PlayerUserData());

        // Foot Sensor
        fixtureDef = new FixtureDef();
        shape.setAsBox(Constants.PLAYER_WIDTH / 2 - 0.05f , 0.05f,
                new Vector2(0f, -Constants.PLAYER_HEIGHT / 2 - Constants.FOOT_HEIGHT * 2 -0.05f), 0f);
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;
        fixtureDef.filter.categoryBits = -1;
        body.createFixture(fixtureDef).setUserData(new FootUserData());



        // Build invisible wall
        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        fixtureDef = new FixtureDef();
        fixtureDef.shape = new PolygonShape();
        ((PolygonShape) fixtureDef.shape).setAsBox(0, 10);
        bodyDef.position.set(mapHolder.spawn.x - GameStage.VIEWPORT_WIDTH / 4, body.getPosition().y);
        Body wallBody = world.createBody(bodyDef);
        wallBody.createFixture(fixtureDef);

        shape.dispose();
        return body;
    }

    public static MapHolder createMap(World world, String mapName) {
        java.util.List<Body> bodies = new ArrayList<>();
        TiledMap tiledMap = ResourceUtils.getMap(mapName);

        // Create ground
        MapObjects ground = tiledMap.getLayers().get("ground").getObjects();

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

    public static Body createFinish(World world, TiledMap tiledMap) {
        MapLayer layer = tiledMap.getLayers().get("finish");
        if (layer == null) return null;
        Shape shape = getRectangle((RectangleMapObject) layer.getObjects().get(0));

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;

        Body body = world.createBody(bodyDef);
        body.createFixture(fixtureDef).setUserData(new FinishUserData());

        return body;
    }

    public static List<Body> createBugs(World world, TiledMap tiledMap) {
        List<Body> res = new ArrayList<>();

        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        Shape shape = new CircleShape();
        shape.setRadius(0.2f);
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;
        bodyDef.type = BodyDef.BodyType.StaticBody;

        for (MapObject o : tiledMap.getLayers().get("bugs").getObjects()) {
            BugUserData  userData = new BugUserData();

            Ellipse e = ((EllipseMapObject)o).getEllipse();
            bodyDef.position.set(e.x / Constants.PPM, e.y / Constants.PPM);

            Body body = world.createBody(bodyDef);
            body.createFixture(fixtureDef).setUserData(userData);
            body.setUserData(userData);

            res.add(body);
        }

        shape.dispose();
        return res;
    }

    public static List<Body> createEgors(World world, TiledMap tiledMap) {
        MapLayer layer = tiledMap.getLayers().get("egors");
        List<Body> res = new ArrayList<>();
        if (layer == null) return res;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.fixedRotation = true;
        bodyDef.gravityScale = 100f;

        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(23f/25f/2f, 46f/25f/2f);
        fixtureDef.shape = shape;
        fixtureDef.friction = 0;
        fixtureDef.filter.categoryBits = Mask.EGOR;
        fixtureDef.filter.maskBits = Mask.PLAYER;

        for (MapObject o : layer.getObjects()) {
            System.out.println("test");
            float[] pts = getXLine((PolylineMapObject) o);

            EgorUserData userData = new EgorUserData(pts[0], pts[1]);

            bodyDef.position.set(pts[0], pts[2]);
            Body body = world.createBody(bodyDef);
            body.setUserData(userData);
            body.createFixture(fixtureDef).setUserData(userData);
            res.add(body);
        }

        return res;
    }
    public static List<Body> createCats(World world, TiledMap tiledMap) {
        MapLayer layer = tiledMap.getLayers().get("cats");
        List<Body> res = new ArrayList<>();
        if (layer == null) return res;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.fixedRotation = true;
        bodyDef.gravityScale = 100f;

        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(26f/58f, 22f/58f);
        fixtureDef.shape = shape;
        fixtureDef.friction = 0;

        for (MapObject o : layer.getObjects()) {
            float[] pts = getXLine((PolylineMapObject) o);

            CatUserData userData = new CatUserData(pts[0], pts[1]);

            bodyDef.position.set(pts[0], pts[2]);
            Body body = world.createBody(bodyDef);
            body.setUserData(userData);
            body.createFixture(fixtureDef).setUserData(userData);
            res.add(body);
        }

        return res;
    }

    public static void createPlatforms(World world, TiledMap tiledMap) {
        MapLayer layer = tiledMap.getLayers().get("platforms");
        if (layer == null) return;

        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();

        bodyDef.type = BodyDef.BodyType.StaticBody;
        fixtureDef.friction = Constants.WORLD_FRICTION;
        fixtureDef.filter.maskBits = ~Mask.PLAYER;

        for (MapObject o : tiledMap.getLayers().get("platforms").getObjects()) {
            Body body = world.createBody(bodyDef);
            fixtureDef.shape = getRectangle((RectangleMapObject) o);

            body.createFixture(fixtureDef).setUserData(new PlatformUserData());
        }
    }

    public static List<RainCloud> createRain(World world, TiledMap tiledMap) {
        List<RainCloud> res = new ArrayList<>();
        for (MapObject o : tiledMap.getLayers().get("rain").getObjects()) {
            Ellipse e = ((EllipseMapObject) o).getEllipse();
            res.add(new RainCloud(world, new Vector2(e.x / Constants.PPM, e.y / Constants.PPM)));
        }
        return res;
    }

    public static List<Body> createSimpleEnemy(World world, TiledMap tiledMap) {
        List<Body> res = new ArrayList<>();

        if (tiledMap.getLayers().get("Simple") == null) return res;
        for (MapObject o : tiledMap.getLayers().get("Simple").getObjects()) {
            float[] track = getXLine((PolylineMapObject) o);

            BodyDef bodyDef = new BodyDef();
            FixtureDef fixtureDef = new FixtureDef();

            //bodyDef.fixedRotation = true;
            bodyDef.gravityScale = 5f;
            bodyDef.type = BodyDef.BodyType.DynamicBody;
            bodyDef.position.set(track[0], track[2]);
            PolygonShape shape = new PolygonShape();
            // TODO: replace with constants
            shape.setAsBox(0.3f, 0.3f, new Vector2(Constants.PPM / 2, 0.5f), 0);
            fixtureDef.shape = shape;
            fixtureDef.friction = 1;

            Body body = world.createBody(bodyDef);
            body.createFixture(fixtureDef);
            body.setUserData(new SimpleEnemyUserData(track));

            res.add(body);
        }

        return res;
    }

    public static List<Body> createDisappearObjects(World world, TiledMap tiledMap) {
        List<Body> res = new ArrayList<>();
        if (tiledMap.getLayers().get("logo") == null) return  res;
        for (MapObject o : tiledMap.getLayers().get("logo").getObjects()) {
            Ellipse ellipse = ((EllipseMapObject) o).getEllipse();
            BodyDef bodyDef = new BodyDef();
            bodyDef.position.set(ellipse.x / Constants.PPM, ellipse.y / Constants.PPM);
            FixtureDef fixtureDef = new FixtureDef();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            PolygonShape shape = new PolygonShape();
            shape.setAsBox(Constants.DISAPPEAR_OBJECT_WIDTH / 2, Constants.DISAPPEAR_OBJECT_HEIGHT / 2);
            fixtureDef.shape = shape;
            fixtureDef.friction = Constants.WORLD_FRICTION;

            Body body = world.createBody(bodyDef);
            body.createFixture(fixtureDef);
            body.setUserData(new DisappearUserData(o.getName()));

            res.add(body);
        }
        return res;
    }

    // 0 - A.x, 1 - B.x, 2 - Ay
    public static float[] getXLine(PolylineMapObject polylineMapObject) {
        float[] raw = polylineMapObject.getPolyline().getTransformedVertices();
        float[] wordVertices = new float[3];

        wordVertices[0] = raw[0] / Constants.PPM;
        wordVertices[1] = raw[2] / Constants.PPM;
        wordVertices[2] = raw[1] / Constants.PPM;

        return wordVertices;
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
            worldVertices[i] = vertices[i] / Constants.PPM;
        }

        //worldVertices[worldVertices.length - 2] = worldVertices[0];
        //worldVertices[worldVertices.length - 1] = worldVertices[1];

        chain.createLoop(worldVertices);
        //chain.setPrevVertex(worldVertices[0], worldVertices[1]);
        return chain;
    }

    public static PolygonShape getPolygon(PolygonMapObject polygonObject) {
        PolygonShape polygon = new PolygonShape();
        float[] vertices = polygonObject.getPolygon().getTransformedVertices();

        float[] worldVertices = new float[vertices.length];

        for (int i = 0; i < vertices.length; ++i) {
            worldVertices[i] = vertices[i] / Constants.PPM;
        }

        polygon.set(worldVertices);
        return polygon;
    }

}
