package org.common.utils;

import com.badlogic.gdx.physics.box2d.World;

public class CoreResources {
    public static World getWorld() {
        return world;
    }


    public static void setWorld(World world) {
        CoreResources.world = world;
    }

    public static World world;

}
