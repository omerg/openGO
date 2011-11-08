package com.obss.go.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Line2D;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import com.obss.go.api.Constants;
import com.obss.go.exception.GoException;
import com.obss.go.util.GamePlayUtils;
import com.obss.go.util.Services;

public class Board extends JPanel {
	
	//logger
	private static Logger logger = Logger.getLogger("Board");

	public Board() {

		//listen to player actions
		this.addMouseListener(new MouseAdapter() {

			public void mousePressed(MouseEvent e) { // get coordinates
				
				//get highlighted cell
				Cell highlighedCell = GamePlayUtils.getCellByPoint(e.getPoint());

				Iterator<Entry<String, Cell>> it = Game.getCells().entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry<String, Cell> cellMapElement = (Map.Entry<String, Cell>) it.next();
					if (cellMapElement.getValue().equals(highlighedCell)) {
						
						try {
							
							//play turn
							Services.getGamePlayService().playTurn(highlighedCell.getCellX(), highlighedCell.getCellY());
							
						} catch (GoException e1) {
							logger.warn(e1.getMessage());
							return;
						}
					}
				}
				repaint();
			}

		});

		this.addMouseMotionListener(new MouseMotionAdapter() {

			@Override
			public void mouseMoved(MouseEvent e) {

				// get cell
				Cell highlighedCell = GamePlayUtils.getCellByPoint(e.getPoint());

				// repaint highlighted cell
				Iterator<Entry<String, Cell>> it = Game.getCells().entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry<String, Cell> cell = (Map.Entry<String, Cell>) it.next();
					if (cell.getValue().equals(highlighedCell)) {
						
						//if cell is already highlighted, skip redraw() method
						if (cell.getValue().getHighlighted()) {
							return;
						}
						Game.getCells().get(cell.getKey()).setHighlighted(true);
						logger.debug("Cell Highlighted: " + Game.getCells().get(cell.getKey()).toString());
						logger.debug("Group ID: " + Game.getCells().get(cell.getKey()).getGroupId());
					} else {
						Game.getCells().get(cell.getKey()).setHighlighted(false);
					}
				}
				repaint();
			}
		});

	}

	public void paint(Graphics g) {
		
		Graphics2D g2 = (Graphics2D) g;
		
		//draw table
		drawTable(g2);
		
		// set anti-aliasing
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

		// draw cells
		Iterator<Entry<String, Cell>> it = Game.getCells().entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Cell> cell = (Map.Entry<String, Cell>) it.next();
			Game.getCells().get(cell.getKey()).paint(g2);
		}
	}
	
	private void drawTable(Graphics g) {
		
		// get image
		ImageIcon ii = new ImageIcon(this.getClass().getResource(
				"../../../../woodenBoard.jpg"));
		
		Graphics2D g2 = (Graphics2D) g;

		// set background
		g2.drawImage(ii.getImage(), 0, 0, null);

		// set anti-aliasing
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

		// set paint color
		g2.setPaint(Color.DARK_GRAY);

		// draw lines
		drawLines(g2);
		
	}

	private static void drawLines(Graphics2D g2) {

		// draw horizontal and vertical lines
		final int totalGameArea = 13 * Constants.CELL_SIDE_WIDTH;
		final int rightBorder = Constants.TABLE_MARGIN + totalGameArea;
		final int bigDotcenteringDiff = Constants.BIG_DOT_RADIUS / 2;
		for (int y = Constants.TABLE_MARGIN; y < rightBorder; y += Constants.CELL_SIDE_WIDTH) {

			// horizontal
			g2.draw(new Line2D.Double(Constants.TABLE_MARGIN, y,
					totalGameArea - 4, y));

			// vertical
			g2.draw(new Line2D.Double(y, Constants.TABLE_MARGIN, y,
					totalGameArea - 4));

			if ((y - Constants.TABLE_MARGIN)
					% ((Constants.CELL_SIDE_WIDTH * 3)) == 0
					&& y >= (Constants.CELL_SIDE_WIDTH * 3)
					&& y <= (Constants.CELL_SIDE_WIDTH * 12)) {
				// draw dots
				g2.fillOval(y - bigDotcenteringDiff,
						(Constants.CELL_SIDE_WIDTH * 3)
								+ Constants.TABLE_MARGIN - bigDotcenteringDiff,
						10, 10);
				g2.fillOval(y - bigDotcenteringDiff,
						(Constants.CELL_SIDE_WIDTH * 6)
								+ Constants.TABLE_MARGIN - bigDotcenteringDiff,
						10, 10);
				g2.fillOval(y - bigDotcenteringDiff,
						(Constants.CELL_SIDE_WIDTH * 9)
								+ Constants.TABLE_MARGIN - bigDotcenteringDiff,
						10, 10);
			}
		}
	}
}
