package org.subquark.nukes;

import com.badlogic.gdx.Gdx;

import java.util.Optional;

import static org.subquark.nukes.Constants.INSPECTOR_VIEW_RADIUS;

public class RefinerySystem {
    private GameState gameState;

    private static final int MS_PER_PRODUCTION = 5000;
    private static final int MIN_DX = 20;
    private static final int MIN_DY = 20;
    private static final int MAX_DX = 50;
    private static final int MAX_DY = 50;
    private static final int URANIUM_DISTANCE = 75;
    private static final int MAX_URANIUM = 5;

    public void setGameState(GameState gs) {
        this.gameState = gs;
    }

    private void act(Entity refinery, float dTime) {
        assert refinery.type == Entity.REFINERY;
        if (refinery.progress == 0) { // idle
            long numUraniumNearby = gameState.entitiesNear(refinery.x, refinery.y, URANIUM_DISTANCE).filter(e -> e.type == Entity.REFINED_URANIUM).count();
            if (numUraniumNearby >= MAX_URANIUM) return;

            Optional<Entity> possibleUranium = gameState.entitiesNear(refinery.x, refinery.y, URANIUM_DISTANCE).filter(e -> e.type == Entity.RAW_URANIUM).findAny();
            possibleUranium.ifPresent(e -> {
                gameState.removeEntity(e);
                refinery.progress = MS_PER_PRODUCTION / 1000f;
            });
        } else {
            refinery.progress -= dTime;
            gameState.particles.add(Particle.makeSmoke(refinery.x, refinery.y));
            if (refinery.progress <= 0) {
                refinery.progress = 0;

                int dx = (int)(Math.random() * 2*MAX_DX - MAX_DX) + MIN_DX;
                int dy = (int)(Math.random() * 2*MAX_DY - MAX_DY) + MIN_DY;

                gameState.heat += Constants.HEAT_PER_REFINERY;
                gameState.score += Constants.ROCKET_SCORE;
                for (int i = 0; i < 2; i++) {
                    gameState.particles.add(Particle.makeHeat(refinery.x, refinery.y));
                    gameState.particles.add(Particle.makeScore(refinery.x, refinery.y));
                }
                Optional<Entity> possibleInspector = gameState.entitiesNear(refinery.x, refinery.y, INSPECTOR_VIEW_RADIUS).filter(ee -> ee.type == Entity.INSPECTOR).findAny();
                possibleInspector.ifPresent(inspector -> {
                    for (int i = 0; i < 1; i++) {
                        gameState.particles.add(Particle.makeHeat(inspector.x, inspector.y));
                    }
                    gameState.heat += Constants.INSPECTOR_REFINERY_HEAT;
                });
                Entity uranium = Entity.makeRefinedUranium();
                uranium.x = refinery.x + dx;
                uranium.y = refinery.y + dy;
                gameState.addEntity(uranium);
            }
        }
    }
    public void update() {
        float dx = Gdx.graphics.getDeltaTime();
        for (Entity e : gameState.entities) {
            if (e.type != Entity.REFINERY) continue;
            act(e, dx);
        }
    }
}
