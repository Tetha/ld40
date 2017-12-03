package org.subquark.nukes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Disposable;

import java.util.Arrays;
import java.util.Optional;

public class DictatorSystem {
    private GameState gameState;

    private static final int PICKUP_RANGE = 20;
    private static final int RUNSPEED_PX_SECOND = 150;
    public static final int MS_PER_FRAME = 200;

    public void setGameState(GameState gs) {
        this.gameState = gs;
    }

    public void update() {
        final float deltaMs = Gdx.graphics.getDeltaTime();
        Entity dictator = gameState.entities.stream().filter(e -> e.type == Entity.DICTATOR).findFirst().get();

        final boolean runningBeforeInputHandling = dictator.running;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            dictator.x -= deltaMs * RUNSPEED_PX_SECOND;
            dictator.running = true;
            dictator.textureProvider.setFlip(false);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            dictator.x += deltaMs * RUNSPEED_PX_SECOND;
            dictator.running = true;
            dictator.textureProvider.setFlip(true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            dictator.y += deltaMs * RUNSPEED_PX_SECOND;
            dictator.running = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            dictator.y -= deltaMs * RUNSPEED_PX_SECOND;
            dictator.running = true;
        }

        if (dictator.x < 0) dictator.x = 0;
        if (dictator.x + 25 > 500) dictator.x = 500 - 25;
        if (dictator.y < 0) dictator.y = 0;
        if (dictator.y + 30> 500) dictator.y = 500 - 30;

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            if (dictator.inventory == null) {
                System.out.println("Pickup");
                // picking up stuff
                Optional<Entity> possibleEntityNearby = gameState.entitiesNear(dictator.x, dictator.y, PICKUP_RANGE).filter(e -> e.type == Entity.RAW_URANIUM || e.type == Entity.REFINED_URANIUM).findFirst();
                if (possibleEntityNearby.isPresent()) {
                    Entity pickedUp = possibleEntityNearby.get();
                    System.out.println("Picked up a " + pickedUp.type);
                    dictator.inventory = possibleEntityNearby.get();
                    gameState.removeEntity(pickedUp);
                }
            } else {

                // dropping stuff
                gameState.addEntity(dictator.inventory);
                dictator.inventory.x = dictator.x;
                dictator.inventory.y = dictator.y;
                System.out.println("Dropping " + dictator.inventory);
                dictator.inventory = null;
            }
        }

        if (dictator.running
                && !Gdx.input.isKeyPressed(Input.Keys.DOWN)
                && !Gdx.input.isKeyPressed(Input.Keys.UP)
                && !Gdx.input.isKeyPressed(Input.Keys.RIGHT)
                && !Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            System.out.println("stopped running");
            dictator.textureProvider.stopWalking();
            dictator.running = false;
        }

        if (!runningBeforeInputHandling && dictator.running) { // started running
            System.out.println("started running");
            dictator.textureProvider.startWalking();
        }
    }
}
