package org.subquark.nukes;

import com.badlogic.gdx.Gdx;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class InspectorSystem {
    private GameState gameState;

    private static final int TARGET_DISTANCE = 20;
    private static final int RUNSPEED_PX_SECOND = 125;

    private static final double MIN_IDLE_SECONDS = 1;
    private static final double MAX_IDLE_SECONDS = 5;

    private static final int AI_STATE_IDLE = 0;
    private static final int AI_STATE_GOING_TO_TARGET = 1;
    private static final int AI_STATE_FOLLOWING_DICTATOR = 2;


    public void setGameState(GameState gs) {
        this.gameState = gs;
    }

    private void updateSingle(Entity e) {
        assert e.type == Entity.INSPECTOR;
        final float deltaTime = Gdx.graphics.getDeltaTime();
        switch (e.aiState) {
            case AI_STATE_IDLE:
                e.progress -= deltaTime;
                if (e.progress <= 0) {
                    // stop being idle
                    e.progress = 0;
                    // randomly walk to a random mine, random refinery, random rocket or the dictator
                    double choice2 = Math.random();
                    int targetType;
                    if (choice2 < 0.25) {
                        targetType = Entity.MINE;
                    } else if (choice2 < 0.5) {
                        targetType = Entity.REFINERY;
                    } else if (choice2 < 0.75) {
                        targetType = Entity.ROCKET;
                    } else {
                        e.aiState = AI_STATE_FOLLOWING_DICTATOR;
                        e.progress = (float) (Math.random() * (MAX_IDLE_SECONDS - MIN_IDLE_SECONDS) + MIN_IDLE_SECONDS);
                        targetType = -1;
                    }

                    if (targetType != -1) {
                        List<Entity> targetEntities = gameState.entities.stream().filter(ee -> ee.type == targetType).collect(Collectors.toList());
                        if (targetEntities.size() > 0) {
                            Collections.shuffle(targetEntities);
                            Entity target = targetEntities.get(0);
                            e.targetX = (float) (target.x + Math.random() * 10 + 5);
                            e.targetY = (float) (target.y + Math.random() * 10 + 5);
                        }
                        e.aiState = AI_STATE_GOING_TO_TARGET;
                    }
                }
                break;

            case AI_STATE_FOLLOWING_DICTATOR:
                e.progress -= deltaTime;
                if (!e.running) {
                    // start running
                    e.running = true;
                    e.textureProvider.startWalking();
                    e.textureProvider.setFlip(e.x < e.targetY);
                }
                if (e.progress <= 0) {
                    e.progress = (float) (Math.random() * (MAX_IDLE_SECONDS - MIN_IDLE_SECONDS) + MIN_IDLE_SECONDS);
                    e.running = false;
                    e.textureProvider.stopWalking();
                    e.aiState = AI_STATE_IDLE;
                } else {
                    Entity dictator = gameState.entities.stream().filter(ee -> ee.type == Entity.DICTATOR).findFirst().get();
                    boolean nearDictator = (e.x - dictator.x) * (e.x - dictator.x) + (e.y - dictator.y) * (e.y - dictator.y) < TARGET_DISTANCE * TARGET_DISTANCE;
                    e.textureProvider.setFlip(e.x < dictator.x);
                    if (!nearDictator) {
                        if (e.x < dictator.x) {
                            e.x += RUNSPEED_PX_SECOND * deltaTime;
                            if (e.x > dictator.x) {
                                e.x = dictator.x;
                            }
                        } else {
                            e.x -= RUNSPEED_PX_SECOND * deltaTime;
                            if (e.x < dictator.x) {
                                e.x = dictator.x;
                            }
                        }

                        if (e.y < dictator.y) {
                            e.y += RUNSPEED_PX_SECOND * deltaTime;
                            if (e.y > dictator.y) {
                                e.y = dictator.y;
                            }
                        } else {
                            e.y -= RUNSPEED_PX_SECOND * deltaTime;
                            if (e.y < dictator.y) {
                                e.y = dictator.y;
                            }
                        }
                    }

                    boolean canSeeDictator = (e.x - dictator.x) * (e.x - dictator.x) + (e.y - dictator.y) * (e.y - dictator.y) < Constants.INSPECTOR_VIEW_RADIUS * Constants.INSPECTOR_VIEW_RADIUS;
                    if (canSeeDictator) {
                        if (dictator.inventory != null && dictator.inventory.type == Entity.REFINED_URANIUM) {
                            gameState.heat += 1;
                            if (Math.random() > 0.4) {
                                gameState.particles.add(Particle.makeHeat(e.x, e.y));
                            }
                        }
                    }
                }
                break;

            case AI_STATE_GOING_TO_TARGET:
                boolean nearTarget = (e.x - e.targetX) * (e.x - e.targetX) + (e.y - e.targetY) * (e.y - e.targetY) < TARGET_DISTANCE * TARGET_DISTANCE;
                if (nearTarget) {
                    e.progress = (float) (Math.random() * (MAX_IDLE_SECONDS - MIN_IDLE_SECONDS) + MIN_IDLE_SECONDS);
                    e.running = false;
                    e.textureProvider.stopWalking();
                    e.aiState = AI_STATE_IDLE;
                } else {
                    if (!e.running) {
                        // start running
                        e.running = true;
                        e.textureProvider.startWalking();
                        e.textureProvider.setFlip(e.x < e.targetY);
                    }


                    if (e.x < e.targetX) {
                        e.x += RUNSPEED_PX_SECOND * deltaTime;
                        if (e.x > e.targetX) {
                            e.x = e.targetX;
                        }
                    } else {
                        e.x -= RUNSPEED_PX_SECOND * deltaTime;
                        if (e.x < e.targetX) {
                            e.x = e.targetX;
                        }
                    }

                    if (e.y < e.targetY) {
                        e.y += RUNSPEED_PX_SECOND * deltaTime;
                        if (e.y > e.targetY) {
                            e.y = e.targetY;
                        }
                    } else {
                        e.y -= RUNSPEED_PX_SECOND * deltaTime;
                        if (e.y < e.targetY) {
                            e.y = e.targetY;
                        }
                    }
                }
                break;
        }
    }

    public void update() {
        gameState.entities.stream().filter(e -> e.type == Entity.INSPECTOR).forEach(e -> updateSingle(e));
    }
}
