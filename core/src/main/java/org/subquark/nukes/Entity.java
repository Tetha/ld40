package org.subquark.nukes;

import com.badlogic.gdx.graphics.Texture;

import java.util.Arrays;

public class Entity {
    public static final int ROCKET = 1;
    public static final int REFINERY = 2;
    public static final int MINE = 3;
    public static final int RAW_URANIUM = 4;
    public static final int REFINED_URANIUM = 5;
    public static final int DICTATOR = 6;
    public static final int INSPECTOR = 7;

    public float x;
    public float y;
    public int type;
    public float progress;

    public TextureProvider textureProvider;
    public Entity inventory;
    public boolean running;

    public float targetX;
    public float targetY;

    public int aiState;

    private Entity() {
        // factory methods below
    }

    public String toString() {
        return String.format("Entity[x=%f,y=%f,type=%d,progress=%f]", x, y, type, progress);
    }

    private static StaticTextureProvider rocket;
    private static StaticTextureProvider refinery;
    private static StaticTextureProvider mine;
    private static StaticTextureProvider rawUranium;
    private static StaticTextureProvider refinedUranium;

    public static Entity makeDictator() {
        Texture dictatorStanding = new Texture("assets/dictator_standing.png");
        Texture walkCycleTwo = new Texture("assets/walk_cycle_2.png");
        Texture walkCycleThree = new Texture("assets/walk_cycle_three.png");
        Texture walkCycleFour = new Texture("assets/walk_cycle_4.png");

        Entity result = new Entity();
        result.type = DICTATOR;
        result.textureProvider = new AnimatedWalkProvider(dictatorStanding,
                Arrays.asList(dictatorStanding, walkCycleTwo, walkCycleThree, walkCycleFour),
                DictatorSystem.MS_PER_FRAME);
        return result;
    }

    public static Entity makeRocket() {
        if (rocket == null) {
            rocket = new StaticTextureProvider(new Texture("assets/rocket.png"));
        }

        Entity result = new Entity();
        result.type = ROCKET;
        result.textureProvider = rocket;
        return result;
    }

    public static Entity makeRefinery() {
        if (refinery == null) {
            refinery = new StaticTextureProvider(new Texture("assets/refinery.png"));
        }

        Entity result = new Entity();
        result.type = REFINERY;
        result.textureProvider = refinery;
        return result;
    }

    public static Entity makeMine() {
        if (mine == null) {
            mine = new StaticTextureProvider(new Texture("assets/mine.png"));
        }

        Entity result = new Entity();
        result.type = MINE;
        result.textureProvider = mine;
        return result;
    }

    public static Entity makeRawUranium() {
        if (rawUranium == null) {
            rawUranium = new StaticTextureProvider(new Texture("assets/raw_uranium.png"));
        }

        Entity result = new Entity();
        result.type = RAW_URANIUM;
        result.textureProvider = rawUranium;
        return result;
    }


    public static Entity makeRefinedUranium() {
        if (refinedUranium == null) {
            refinedUranium = new StaticTextureProvider(new Texture("assets/refined_uranium.png"));
        }

        Entity result = new Entity();
        result.type = REFINED_URANIUM;
        result.textureProvider = refinedUranium;
        return result;
    }

    private static Texture inspectorStanding;
    private static Texture inspectorWalkCycle1;
    private static Texture inspectorWalkCycle2;
    private static Texture inspectorWalkCycle3;
    private static Texture inspectorWalkCycle4;
    public static Entity makeInspector() {
        if (inspectorStanding == null) {
            inspectorStanding = new Texture("assets/inspector_standing.png");
            inspectorWalkCycle1 = new Texture("assets/inspector_walk_cycle_1.png");
            inspectorWalkCycle2 = new Texture("assets/inspector_walk_cycle_2.png");
            inspectorWalkCycle3 = new Texture("assets/inspector_walk_cycle_3.png");
            inspectorWalkCycle4 = new Texture("assets/inspector_walk_cycle_4.png");
        }
        Entity result = new Entity();
        result.type = INSPECTOR;
        result.textureProvider = new AnimatedWalkProvider(inspectorStanding,
                Arrays.asList(inspectorWalkCycle1, inspectorWalkCycle2, inspectorWalkCycle3, inspectorWalkCycle4),
                DictatorSystem.MS_PER_FRAME);
        return result;
    }
}
