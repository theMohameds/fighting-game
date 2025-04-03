package io.group9;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

// 1) Import the GameMap class from the GameMap package
import GameMap.GameMap;

/** First screen of the application. Displayed after the application is created. */
public class FirstScreen implements Screen {
    private Box2DDebugRenderer debugRenderer;
    private World world;
    private Engine engine;
    private OrthographicCamera camera;

    // 2) We'll hold a reference to our tile-based map
    private GameMap gameMap;

    @Override
    public void show() {
        // Create the Box2D world
        world = new World(new Vector2(0, -10), true);

        // Create a debug renderer to visualize physics bodies
        debugRenderer = new Box2DDebugRenderer();

        // Set up the camera
        camera = new OrthographicCamera(1280, 720);
        camera.position.set(25, 15, 0);
        camera.update();

        // If you're using the Ashley ECS
        engine = new Engine();

        // 3) Instantiate your GameMap, passing in the Box2D world, camera,
        //    path to .tmx, and collision layer index
        gameMap = new GameMap(world, camera, "map/New4.tmx", 2);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        // Step the physics simulation
        // (Your original code used 1/60f * delta; you can adjust as needed)
        world.step(1/60f * delta, 6, 2);

        // Update ECS engine if you have systems or entities
        engine.update(delta);

        // Update the camera (if you need to move or zoom it)
        camera.update();

        // 4) Render your tiled map
        gameMap.render();

        // Debug-draw the Box2D bodies in the world
        debugRenderer.render(world, camera.combined);
    }

    @Override
    public void resize(int width, int height) {
        // Adjust your camera's viewport if needed
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        // Clean up resources
        if (world != null) {
            world.dispose();
        }
        if (debugRenderer != null) {
            debugRenderer.dispose();
        }
        // 5) Dispose the GameMap resources (does NOT dispose the World)
        if (gameMap != null) {
            gameMap.dispose();
        }
    }
}
