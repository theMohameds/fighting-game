package services;

import com.badlogic.ashley.core.Engine;

public interface ECSPlugin {

    void registerSystems(Engine engine);

    void createEntities(Engine engine);
}
