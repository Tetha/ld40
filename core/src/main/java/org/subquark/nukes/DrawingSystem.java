package org.subquark.nukes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Comparator;
import java.util.stream.Stream;

public class DrawingSystem {
    private SpriteBatch spriteBatch;
    private GameState gameState;


    public DrawingSystem() {
        this.spriteBatch = new SpriteBatch();
    }

    public void setGameState(GameState gs) {
        this.gameState = gs;
    }

    public void update() {
        spriteBatch.begin();
        float ds = Gdx.graphics.getDeltaTime();
        gameState.entities.stream().forEach(e -> e.textureProvider.update(ds * 1000));
        Stream<Entity> entities = gameState.entities.stream().sorted(Comparator.comparingDouble((Entity r) -> r.y).reversed());
        entities.forEach(e -> {
            Texture t = e.textureProvider.getCurrentTexture();
            spriteBatch.draw(t, e.x, e.y, t.getWidth(), t.getHeight(), 0, 0, t.getWidth(), t.getHeight(), e.textureProvider.doFlip(), false);
        });
        spriteBatch.end();
    }
}
