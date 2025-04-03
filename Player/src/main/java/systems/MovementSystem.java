package systems;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import components.Player;
import io.group9.CoreResources;

public class MovementSystem extends EntitySystem {
    private Player playerComponent = new Player();
    private ShapeRenderer shapeRenderer = new ShapeRenderer();
    private boolean bodyCreated = false;

    @Override
    public void update(float deltaTime) {
        World world = CoreResources.getWorld();

        // Create the player's body only once
        if (!bodyCreated) {
            // Configure player's body definition
            playerComponent.bodyDef.type = BodyDef.BodyType.DynamicBody;
            playerComponent.bodyDef.position.set(5, 10);

            // Create the body and assign it to the player component
            playerComponent.body = world.createBody(playerComponent.bodyDef);

            // Create a circle shape with a radius of 6
            CircleShape circle = new CircleShape();
            circle.setRadius(6f);

            // Create a fixture definition and apply the shape
            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = circle;
            fixtureDef.density = 0.5f;
            fixtureDef.friction = 0.4f;
            fixtureDef.restitution = 0.6f; // Make it bounce a little

            // Create the fixture on the player's body
            playerComponent.body.createFixture(fixtureDef);
            // Dispose of the shape to free resources
            circle.dispose();

            bodyCreated = true;
        }

        // Retrieve current velocity and position
        Vector2 vel = playerComponent.body.getLinearVelocity();
        Vector2 pos = playerComponent.body.getPosition();

        // Apply a leftward impulse if the 'A' key is pressed and max velocity is not exceeded
        if (Gdx.input.isKeyPressed(Keys.A) && vel.x > -100) {
            playerComponent.body.applyLinearImpulse(new Vector2(-0.80f, 0), pos, true);
        }

        // Apply a rightward impulse if the 'D' key is pressed and max velocity is not exceeded
        if (Gdx.input.isKeyPressed(Keys.D) && vel.x < 100) {
            playerComponent.body.applyLinearImpulse(new Vector2(0.80f, 0), pos, true);
        }

        // Render the circle using the ShapeRenderer
        // It's common to clear the screen here, but in a real application, that might be done elsewhere
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        // Begin drawing with the filled shape type
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        // Optionally set the projection matrix if you have a camera, e.g.:
        // shapeRenderer.setProjectionMatrix(CoreResources.getCamera().combined);

        // Draw the circle at the body's position. The radius here should match the physics shape.
        shapeRenderer.circle(pos.x, pos.y, 6f);
        shapeRenderer.end();
    }

    // Call this method when disposing of your game to release the ShapeRenderer resources
    public void dispose() {
        shapeRenderer.dispose();
    }
}
