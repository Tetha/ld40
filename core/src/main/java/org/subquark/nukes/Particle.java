package org.subquark.nukes;

import com.badlogic.gdx.graphics.Texture;

public class Particle {
    public double lifetimeSeconds;
    public float x;
    public float y;
    public Texture texture;

    public float velocityX;
    public float velocityY;

    private static Texture heatParticle = new Texture("assets/heat_particle.png");
    private static Texture scoreParticle = new Texture("assets/score_particle.png");
    private static Texture smokeParticle = new Texture("assets/smoke_particle.png");

    public static Particle makeHeat(float x, float y) {
        Particle result = new Particle();
        result.velocityX = 0;
        result.velocityY = (float) (50 + Math.random() * 10);
        result.texture = heatParticle;
        result.lifetimeSeconds = 0.8;
        result.x = x + (int)(Math.random() * 10 - 5);
        result.y = y + 30;
        return result;
    }

    public static Particle makeScore(float x, float y) {
        Particle result = new Particle();
        result.velocityX = (float)(Math.random() * 40 - 20);
        result.velocityY = (float)(10 + Math.random() * 10);
        result.texture = scoreParticle;
        result.lifetimeSeconds = 0.8;
        result.x = x;
        result.y = y + 40;
        return result;
    }

    public static Particle makeSmoke(float x, float y) {
        Particle result = new Particle();
        result.velocityX = (float)(Math.random() * 60);
        result.velocityY = (float)(20 + Math.random() * 10);
        result.texture = smokeParticle;
        result.lifetimeSeconds = 1.2;
        result.x = x;
        result.y = y + 40;
        return result;
    }

}
