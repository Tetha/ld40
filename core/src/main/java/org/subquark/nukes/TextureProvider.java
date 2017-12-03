package org.subquark.nukes;

import com.badlogic.gdx.graphics.Texture;

public interface TextureProvider {
    Texture getCurrentTexture();
    boolean doFlip();

    void setFlip(boolean flip);
    void update(float dx);
    void startWalking();
    void stopWalking();
}
