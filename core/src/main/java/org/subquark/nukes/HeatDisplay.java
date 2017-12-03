package org.subquark.nukes;

        import com.badlogic.gdx.graphics.Color;
        import com.badlogic.gdx.graphics.g2d.BitmapFont;
        import com.badlogic.gdx.graphics.g2d.SpriteBatch;
        import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class HeatDisplay {
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private BitmapFont font;

    private GameState gameState;

    public int x;
    public int y;

    private static final int MAX_RED_HEAT = 1000;
    private static final int MAX_GREEN_HEAT = 1000;
    private static final int MAX_YELLOW_HEAT = 1000;

    private static final int GREEN_WIDTH = 60;
    private static final int YELLOW_WIDTH = 60;
    private static final int RED_WIDTH = 60;

    private static final int TEXT_OFFSET = 50;

    private static int HEIGHT = 50;
    public HeatDisplay() {
        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
        font = new BitmapFont();
    }

    public void render() {


        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.DARK_GRAY);
        shapeRenderer.rect(x, y, x + 250, y + 50);
        int greenHeat = gameState.heat;
        if (greenHeat > MAX_GREEN_HEAT) {
            greenHeat = MAX_GREEN_HEAT;
        }
        if (greenHeat >= 0) {
            shapeRenderer.setColor(Color.GREEN);
            shapeRenderer.rect(x + TEXT_OFFSET, y, ((float)greenHeat / MAX_GREEN_HEAT) * GREEN_WIDTH, HEIGHT);
        }

        int yellowHeat = gameState.heat - MAX_GREEN_HEAT;
        if (yellowHeat > MAX_YELLOW_HEAT) {
            yellowHeat = MAX_YELLOW_HEAT;
        }
        if (yellowHeat >= 0) {
            shapeRenderer.setColor(Color.ORANGE);
            shapeRenderer.rect(x + TEXT_OFFSET + GREEN_WIDTH, y, ((float)yellowHeat / MAX_YELLOW_HEAT) * YELLOW_WIDTH, HEIGHT);
        }

        int redHeat = gameState.heat - MAX_YELLOW_HEAT - MAX_GREEN_HEAT;
        if (redHeat > MAX_RED_HEAT) {
            redHeat = MAX_RED_HEAT;
        }
        if (redHeat >= 0) {
            shapeRenderer.setColor(Color.RED);
            shapeRenderer.rect(x + TEXT_OFFSET + GREEN_WIDTH + YELLOW_WIDTH, y, ((float)redHeat / MAX_RED_HEAT) * RED_WIDTH, HEIGHT);
        }
        shapeRenderer.end();

        batch.begin();
        font.draw(batch, "Heat", x, y + 25);
        batch.end();
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }
}
