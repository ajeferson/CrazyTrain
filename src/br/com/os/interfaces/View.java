package br.com.os.interfaces;

import java.awt.Component;
import java.awt.Point;

public interface View {
	
	public Component asView();
	public void moveTo(Point point);

}
