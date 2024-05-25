package main;

public enum Direction {
    NORTH(0, 1),
    EAST(1, 0),
    SOUTH(0,-1),
    WEST(-1, 0),
    NORTH_EAST(1, -1),
    SOUTH_EAST(1, 1),
    SOUTH_WEST(-1, 1),
    NORTH_WEST(-1, -1);

    public final int colValue;
    public final int rowValue;

    Direction(int colValue, int rowValue){
        this.colValue = colValue;
        this.rowValue = rowValue;
    }
}
