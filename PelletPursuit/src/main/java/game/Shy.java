package game;

import javafx.scene.paint.Color;

// Shy — runs away unless cornered (few open neighbors), then attacks.
public class Shy extends Ghost {

    public Shy(GameMap map) {
        super(map, GameMap.Tile.SPAWN_G2, 1.5);
    }

    @Override
    public String getName() { return "Shy"; } // give your ghost a name

    @Override
    protected int[] chooseTarget(Player player, GameMap map) {
        // TODO (Base): Implement Shy's personality.
        //
        // Shy has two modes:
        //   1. FLEE   — target the maze corner FARTHEST from the player
        //   2. ATTACK — target the player's exact tile (like Shadow)
        //
        // Shy switches from FLEE to ATTACK when it is "cornered":
        //   Count how many of Shy's four neighbors (up/down/left/right) are open
        //   (not walls). If only ONE neighbor is open, Shy is cornered.
        //   Use col(map) and row(map) for Shy's position, and map.isWall() to test each neighbor.
        //
        // To find the farthest corner from the player:
        //   Check each of the four near-corner tiles and pick the one with the
        //   greatest Math.hypot distance from the player's tile.
        //
        // When frightened, CHASE the player instead of fleeing.
        // Note: this is intentionally the OPPOSITE of every other ghost's frightened
        // behavior — Shy is bold when cornered and bold when scared.
        //
        // How to verify: run the game and walk toward Shy — it should move away
        // from you. Trap it in a dead-end corridor and it should turn and chase.

        int neighbors = 4;
        if (map.isWall(col(map), row(map) - 1))//up
        {
            neighbors--;
        }
        if (map.isWall(col(map), row(map) + 1))//down
        {
            neighbors--;
        }
        if (map.isWall(col(map) - 1, row(map)))//left
        {
            neighbors--;
        }
        if (map.isWall(col(map) + 1, row(map)))//right
        {
            neighbors--;

        }
        if (frightened || neighbors == 1) {
            // Run away: target the corner farthest from the player
            return new int[]{ player.col(map), player.row(map) };
        }
        double deltaCol1 = player.col(map) - 2;
        double deltaRow1 = player.row(map) - 2;
        double corner1 = Math.hypot(deltaCol1, deltaRow1);

        double deltaCol2 = player.col(map) - map.cols-2;
        double deltaRow2 = player.row(map) - 2;
        double corner2 = Math.hypot(deltaCol2, deltaRow2);

        double deltaCol3 = player.col(map) - 2;
        double deltaRow3 = player.row(map) - map.rows-2;
        double corner3 = Math.hypot(deltaCol3, deltaRow3);

        double deltaCol4 = player.col(map) - map.cols-2;
        double deltaRow4 = player.row(map) - map.rows-2;
        double corner4 = Math.hypot(deltaCol4, deltaRow4);

        if (corner1 >= corner2 && corner1 >= corner3 && corner1 >= corner4){
            return new int[]{ 2, 2 };// col, row (corners)
        }

        else if (corner2 >= corner1 && corner2 >= corner3 && corner2 >= corner4){
            return new int[]{ map.cols-2, 2 };//row, col (corners)
        }

        else if (corner3 >= corner1 && corner3 >= corner2 && corner3 >= corner4){
            return new int[]{ 2, map.rows-2 };//row, col (corners)
        }
        else //(corner4 >= corner1 && corner4 >= corner2 && corner4 >= corner3){
            return new int[]{ map.cols-2, map.rows-2 };//row, col (corners)
        }

        //return new int[]{ player.col(map), player.row(map) }; // placeholder — replace this


    // When chooseTarget() is working, add this ghost to the list in GameApp.java:
    //   new Shy(map)

    @Override
    protected Color getBodyColor() { return Color.web("#00ffff"); }
}
