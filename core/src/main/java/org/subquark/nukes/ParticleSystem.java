package org.subquark.nukes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ParticleSystem {
    private SpriteBatch spriteBatch;
    private GameState gameState;


    public ParticleSystem() {
        this.spriteBatch = new SpriteBatch();
    }

    public void setGameState(GameState gs) {
        this.gameState = gs;
    }

    public void update() {
        spriteBatch.begin();
        float ds = Gdx.graphics.getDeltaTime();
        gameState.particles.stream().forEach(p -> {
            p.x += p.velocityX * ds;
            p.y += p.velocityY * ds;

            p.lifetimeSeconds -= ds;
        });

        List<Particle> deadParticles = gameState.particles.stream().filter(p -> p.lifetimeSeconds <= 0).collect(Collectors.toList());
        gameState.particles.removeAll(deadParticles);

        gameState.particles.stream().forEach(p -> {
            spriteBatch.draw(p.texture, p.x, p.y);
        });
        spriteBatch.end();
    }
}
