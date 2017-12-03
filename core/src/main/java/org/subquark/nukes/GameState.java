package org.subquark.nukes;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class GameState {
    public List<Entity> entities = new ArrayList<Entity>();
    private List<Entity> addedEntities = new ArrayList<>();
    private List<Entity> removedEntities = new ArrayList<>();

    public int heat = 0;
    public int score = 0;
    public List<Particle> particles = new ArrayList<>();

    public static final int GAME = 0;
    public static final int LOST = 1;

    public int gameState = GAME;

    public void addEntity(Entity e) {
        addedEntities.add(e);
    }

    public Stream<Entity> entitiesNear(float x, float y, int maxDistance) {
        return entities.stream().filter(e -> (e.x - x) * (e.x - x) + (e.y - y) * (e.y - y) < maxDistance * maxDistance);
    }

    public void resolveEntityChanges() {
        entities.addAll(addedEntities);
        entities.removeAll(removedEntities);
        addedEntities.clear();
        removedEntities.clear();
    }

    public void removeEntity(Entity entity) {
        removedEntities.add(entity);
    }
}
