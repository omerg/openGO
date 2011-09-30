package com.obss.go.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;

import com.obss.go.api.Constants;

public class StoneShape {

	private static final Integer MARGIN = 2;
	private Point point;
	private Color color;

	public StoneShape(Point point, Color color, Graphics g) {
		super();
		this.setPoint(point);
		this.setColor(color);
		paint(g);

	}

	public void paint(Graphics g) {

		Graphics2D g2 = (Graphics2D) g;

		// set paint color
		g2.setPaint(color);

		// draw circle
		g2.fillOval((int) point.getX() + MARGIN, (int) point.getY() + MARGIN, Constants.CELL_SIDE_WIDTH - (2 * MARGIN), Constants.CELL_SIDE_WIDTH - (2 * MARGIN));

	}

	public void mouseOver(MouseEvent e) {

	}

	public void mousePressed(MouseEvent e) {

	}

	public void mouseDragged(MouseEvent e) {

	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Color getColor() {
		return color;
	}

	public void setPoint(Point point) {
		this.point = point;
	}

	public Point getPoint() {
		return point;
	}

}
