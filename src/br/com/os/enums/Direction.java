package br.com.os.enums;

/** The directions the animatables can move. */
public enum Direction {

	LEFTWARDS("Leftwards"),
	RIGHTWARDS("Rightwards"),
	UPWARDS("Upwards"),
	DOWNWARDS("Downwards"),
	NONE("None");
	
	private String description;
	
	Direction(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return this.description;
	}
	
}
