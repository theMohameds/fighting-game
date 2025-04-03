package systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import components.Player;

/**
 * A system that applies keyboard input to any entity with PlayerComponent,
 * causing it to move around via Box2D forces.
 */
public class Movement2 extends IteratingSystem {

    // Strength of force applied to the player each frame
    private static final float MOVE_FORCE = 50f;

    public Movement2() {
        // Only process entities with PlayerComponent
        super(Family.all(Player.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        // Retrieve the player's Box2D body
        Player pc = entity.getComponent(Player.class);
        Body body = pc.body;

        Vector2 force = new Vector2();

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            force.x = -MOVE_FORCE;
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            force.x = MOVE_FORCE;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            force.y = MOVE_FORCE;
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            force.y = -MOVE_FORCE;
        }

        // Apply the force to the center of the body if we're pressing keys
        if (!force.isZero()) {
            body.applyForceToCenter(force, true);
        }
    }
}


