package org.subquark.nukes;

import com.badlogic.gdx.graphics.Texture;

public class StaticTextureProvider implements TextureProvider {
    private final Texture t;

    public StaticTextureProvider(Texture t) {
        this.t = t;
    }

    @Override
    public Texture getCurrentTexture() {
        return t;
    }

    @Override
    public boolean doFlip() {
        return false;
    }

    @Override
    public void setFlip(boolean flip) {
        // nop
    }

    @Override
    public void update(float dx) {
        // nop
    }

    @Override
    public void startWalking() {
        // nop
    }

    @Override
    public void stopWalking() {
        // nop
    }
}
