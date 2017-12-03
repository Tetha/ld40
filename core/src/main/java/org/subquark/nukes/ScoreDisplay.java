package org.subquark.nukes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class ScoreDisplay {
    private SpriteBatch spriteBatch;
    private ShapeRenderer shapeRenderer;
    private BitmapFont font;

    private GameState gameState;

    public int x;
    public int y;

    public ScoreDisplay() {
        this.spriteBatch = new SpriteBatch();
        this.shapeRenderer = new ShapeRenderer();
        this.font = new BitmapFont();
    }

    public void setGameState(GameState gs) {
        this.gameState = gs;
    }

    public void render() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.DARK_GRAY);
        shapeRenderer.rect(x, y, x + 250, y + 50);
        shapeRenderer.end();

        spriteBatch.begin();
        font.draw(spriteBatch, "Score: ", x, y + 25);
        font.draw(spriteBatch, Integer.toString(gameState.score), x +50, y + 25);
        spriteBatch.end();
    }
}
