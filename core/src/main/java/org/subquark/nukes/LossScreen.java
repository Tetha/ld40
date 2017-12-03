package org.subquark.nukes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;

public class LossScreen {
    private SpriteBatch spriteBatch;
    private ShapeRenderer shapeRenderer;
    private BitmapFont font;

    private GameState gameState;

    public LossScreen() {
        spriteBatch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        font = new BitmapFont();
    }

    public void setGameState(GameState gs) {
        this.gameState = gs;
    }

    public void render() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.DARK_GRAY);
        shapeRenderer.rect(0, 0, 500, 500);
        shapeRenderer.end();

        spriteBatch.begin();
        font.draw(spriteBatch,
                "Oh no! You attracted too much international heat!\n"
                + "Now a number of big nations are bombing your cute little operation\n" +
                        "(sadly that wasn't implemented in-game\n"
                + "You managed to get " + gameState.score + " points",
                125, 400, 250, Align.center, true);
        spriteBatch.end();
    }
}
