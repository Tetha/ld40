package org.subquark.nukes;

import com.badlogic.gdx.graphics.Texture;

import java.util.List;

public class AnimatedWalkProvider implements TextureProvider {
    private final Texture idleTexture;
    private final List<Texture> walkTextures;
    private final float msPerFrame;

    private Texture currentTexture;
    private boolean walking;
    private float msInFrame;
    private int animationIdx;
    private boolean doFlip;

    public AnimatedWalkProvider(Texture idleTexture, List<Texture> walkTextures, float msPerFrame) {
        this.idleTexture = idleTexture;
        this.walkTextures = walkTextures;
        this.currentTexture = idleTexture;
        this.msPerFrame = msPerFrame;
    }

    public void startWalking() {
        msInFrame = 0;
        animationIdx = 0;
        walking = true;
    }

    public void stopWalking() {
        walking = false;
        currentTexture = idleTexture;
    }

    @Override
    public void setFlip(boolean flip) {
        this.doFlip = flip;
    }

    @Override
    public Texture getCurrentTexture() {
        return currentTexture;
    }

    @Override
    public boolean doFlip() {
        return doFlip;
    }

    @Override
    public void update(float dsMS) {
        if (!walking) return;
        msInFrame += dsMS;
        if (msInFrame > msPerFrame) {
            msInFrame = 0;
            animationIdx++;
            if (animationIdx >= this.walkTextures.size()) {
                animationIdx = 0;
            }
            currentTexture = walkTextures.get(animationIdx);
        }
    }
}
