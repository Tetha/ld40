package org.subquark.nukes;

import java.util.Optional;

import static org.subquark.nukes.Constants.INSPECTOR_VIEW_RADIUS;

public class RocketSystem {
    private GameState gameState;

    private static final int URANIUM_DISTANCE = 75;

    public void setGameState(GameState gs) {
        this.gameState = gs;
    }

    private final void act(Entity rocket) {
        assert rocket.type == Entity.ROCKET;

        Optional<Entity> possibleUranium = gameState.entitiesNear(rocket.x, rocket.y, URANIUM_DISTANCE).filter(e -> e.type == Entity.REFINED_URANIUM).findAny();
        possibleUranium.ifPresent(e -> {
            gameState.removeEntity(e);
            gameState.score += Constants.ROCKET_SCORE;
            for (int i = 0; i < 10; i++) {
                gameState.particles.add(Particle.makeHeat(rocket.x, rocket.y));
                gameState.particles.add(Particle.makeScore(rocket.x, rocket.y));
            }
            gameState.heat += Constants.HEAT_PER_ROCKET;
            Optional<Entity> possibleInspector = gameState.entitiesNear(rocket.x, rocket.y, INSPECTOR_VIEW_RADIUS).filter(ee -> ee.type == Entity.INSPECTOR).findAny();
            possibleInspector.ifPresent(inspector -> {
                for (int i = 0; i < 5; i++) {
                    gameState.particles.add(Particle.makeHeat(inspector.x, inspector.y));
                }
                gameState.heat += Constants.INSPECTOR_ROCKET_HEAT;
            });
        });
    }
    public void update() {
        for (Entity e : gameState.entities) {
            if (e.type != Entity.ROCKET) continue;
            act(e);
        }
    }
}
