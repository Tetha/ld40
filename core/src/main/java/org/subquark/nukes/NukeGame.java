package org.subquark.nukes;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Arrays;

public class NukeGame extends ApplicationAdapter {

	private SpriteBatch batch;

	private GameState gameState;

	private DrawingSystem drawingSystem;
	private MineSystem mineSystem;
	private RefinerySystem refinerySystem;
	private RocketSystem rocketSystem;
	private DictatorSystem dictatorSystem;
	private InspectorSystem inspectorSystem;
	private InspectorSpawnSystem inspectorSpawnSystem;
    private ParticleSystem particleSystem;

	private HeatDisplay heatDisplay;
	private ScoreDisplay scoreDisplay;
	private LossScreen lossScreen;


	@Override
	public void create () {
		batch = new SpriteBatch();
		gameState = new GameState();

		drawingSystem = new DrawingSystem();
		drawingSystem.setGameState(gameState);

		mineSystem = new MineSystem();
		mineSystem.setGameState(gameState);

		refinerySystem = new RefinerySystem();
		refinerySystem.setGameState(gameState);

		rocketSystem = new RocketSystem();
        rocketSystem.setGameState(gameState);

		dictatorSystem = new DictatorSystem();
		dictatorSystem.setGameState(gameState);

		inspectorSystem = new InspectorSystem();
		inspectorSystem.setGameState(gameState);

		inspectorSpawnSystem = new InspectorSpawnSystem();
		inspectorSpawnSystem.setGameState(gameState);

		particleSystem = new ParticleSystem();
		particleSystem.setGameState(gameState);

		lossScreen = new LossScreen();
		lossScreen.setGameState(gameState);

		heatDisplay = new HeatDisplay();
		heatDisplay.y = 550;
		heatDisplay.setGameState(gameState);

		scoreDisplay = new ScoreDisplay();
		scoreDisplay.y = 550;
		scoreDisplay.x = 250;
		scoreDisplay.setGameState(gameState);

		Entity d = Entity.makeDictator();
		d.x = 100;
		d.y = 100;
		gameState.entities.add(d);

		Entity rocket = Entity.makeRocket();
		rocket.x = 100;
		rocket.y = 200;
        gameState.entities.add(rocket);

		Entity mine = Entity.makeMine();
		mine.x = 200;
		mine.y = 300;
        gameState.entities.add(mine);

        Entity refinery = Entity.makeRefinery();
        refinery.x = 400;
        refinery.y = 400;
        gameState.entities.add(refinery);

        Entity rawUranium = Entity.makeRawUranium();
        rawUranium.x = 250;
        rawUranium.y = 300;
        gameState.entities.add(rawUranium);

        Entity refinedUranium = Entity.makeRefinedUranium();
        refinedUranium.x = 450;
        refinedUranium.y = 400;
        gameState.entities.add(refinedUranium);

        Entity inspector = Entity.makeInspector();
        inspector.x = 400;
        inspector.y = 400;

        inspector.targetX = 200;
        inspector.targetY = 200;
		gameState.entities.add(inspector);
	}

	@Override
	public void render () {
	    if (gameState.gameState == GameState.GAME) {
            Gdx.gl.glClearColor(216f / 256, 220f / 256, 96 / 256f, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            dictatorSystem.update();
            mineSystem.update();
            refinerySystem.update();
            rocketSystem.update();
            inspectorSystem.update();
            inspectorSpawnSystem.update();

            particleSystem.update();

            gameState.resolveEntityChanges();
            drawingSystem.update();
            heatDisplay.render();
            scoreDisplay.render();

            if (gameState.heat >= Constants.MAX_HEAT) {
                gameState.gameState = GameState.LOST;
            }
        } else if (gameState.gameState == GameState.LOST) {
	        lossScreen.render();
        }
	}
	
	@Override
	public void dispose () {
        batch.dispose();
	}
}
