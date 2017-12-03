package org.subquark.nukes;

public class InspectorSpawnSystem {
    private GameState gameState;

    public void setGameState(GameState gs) {
        this.gameState = gs;
    }

    public void update() {
        int expectedInspectors = 0;
        while (expectedInspectors < Constants.INSPECTOR_SPAWNS.length && Constants.INSPECTOR_SPAWNS[expectedInspectors] < gameState.heat) {
            expectedInspectors++;
        }

        long currentInspectors = gameState.entities.stream().filter(e -> e.type == Entity.INSPECTOR).count();
        while (currentInspectors < expectedInspectors) {
            Entity newInspector = Entity.makeInspector();
            newInspector.x = (float)(Math.random() * 500);
            newInspector.y = (float)(Math.random() * 500);
            gameState.addEntity(newInspector);
            currentInspectors++;
        }
    }
}
