package br.com.os.other;

/** Just holds a few constants. */
public class Constants {
	
	public static final int WINDOW_WIDTH = 1000;
	public static final int WINDOW_HEIGHT = 700;

	// Sprite
	public static final int SPRITE_SPEED_MULTIPLIER = 200;
	
	// Passenger
	public static final int PASSENGER_WIDTH = 50;
	public static final int PASSENGER_HEIGHT = 50;
	public static final int PASSENGER_IDLE_SPRITE_INDEX = 1;
	public static final int PASSENGER_DEFAULT_MOVE_TIME = 5000;
	public static final boolean PASSENGER_DEFAULT_TIMES = false;
	
	// Roller Coaster
	public static final int ROLLER_COASTER_WIDTH = 100;
	public static final int ROLLER_COASTER_HEIGHT = 71;
	public static final int ALLOWED_ROLLER_COASTERS = 1;
	public static final int ROLLER_COASTER_DEFAULT_TIME = 20000;
	public static final boolean ROLLER_COASTER_DEFAULT_TIMES = false;
	
	// Tiles
	public static final int TILE_SIZE = 50;
	public static final int LADDER_ENTRANCE_X_POSITION = WINDOW_WIDTH - 5 * TILE_SIZE;
	public static final int LADDER_HEIGHT = 3 * TILE_SIZE;
	
}
