package org.subquark.nukes;

import com.badlogic.gdx.Gdx;

public class MineSystem {
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

    private void produce(Entity mine) {
        assert mine.type == Entity.MINE;
        mine.progress = 0;
        long numUraniumNearby = gameState.entitiesNear(mine.x, mine.y, URANIUM_DISTANCE).filter(e -> e.type == Entity.RAW_URANIUM).count();
        System.out.println(numUraniumNearby);
        if (numUraniumNearby >= MAX_URANIUM) return;

        int dx = (int)(Math.random() * 2*MAX_DX - MAX_DX) + MIN_DX;
        int dy = (int)(Math.random() * 2*MAX_DY - MAX_DY) + MIN_DY;

        Entity uranium = Entity.makeRawUranium();
        uranium.x = mine.x + dx;
        uranium.y = mine.y + dy;
        gameState.addEntity(uranium);
    }

    public void update() {
        float dx = Gdx.graphics.getDeltaTime();
        for (Entity e : gameState.entities) {
            if (e.type != Entity.MINE) continue;
            e.progress += dx;
            if (e.progress >= MS_PER_PRODUCTION/1000) {
                produce(e);
            }
        }
    }
}
