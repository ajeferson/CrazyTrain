package br.com.os.interfaces;

import java.awt.Point;

import br.com.os.enums.Direction;
import br.com.os.other.Sprite;

public interface SpriteDelegate {

	/** Called every time a sprite updates its position */
	public void spriteDidUpdatePositionToPoint(Sprite sprite, Point point);
	
	/** Called every time a sprite changes its direction */
	public void spriteDidChangeDirectionTo(Direction direction);
	
}
