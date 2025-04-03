package plugins;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.*;
import io.group9.CoreResources;
import org.common.services.ECSPlugin;
import components.Player;
import systems.Movement2;

/**
 * A plugin that creates a "player" circle body in Box2D,
 * registers the control system, and spawns the player entity in the ECS.
 */
public class PlayerPlugin implements ECSPlugin {
    // We'll inject the Box2D world via constructor

    public PlayerPlugin() {
    }

    @Override
    public void registerSystems(Engine engine) {
        // Add the system that handles player movement via input
        engine.addSystem(new Movement2());
    }

    @Override
    public void createEntities(Engine engine) {
        World world = CoreResources.getWorld();
        // Create a new ECS Entity
        Entity playerEntity = new Entity();

        // Create a dynamic body for the player (circle)
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(10, 10); // Example spawn position

        Body body = world.createBody(bodyDef);

        // Define a circle shape
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(1f);

        // Define a fixture
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circleShape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0.6f;
        fixtureDef.restitution = 0.1f;
        body.createFixture(fixtureDef);

        // Dispose the shape (we're done using it)
        circleShape.dispose();

        // Create a PlayerComponent to store our new body
        Player playerComp = new Player();
        playerComp.body = body;

        // Attach the component to the entity
        playerEntity.add(playerComp);

        // Add the entity to the Ashley engine
        engine.addEntity(playerEntity);
    }
}
