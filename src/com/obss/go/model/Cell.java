package com.obss.go.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.List;

import org.apache.log4j.Logger;

import com.obss.go.api.Constants;
import com.obss.go.api.Constants.CELL_STATUS;
import com.obss.go.api.Constants.CELL_X;
import com.obss.go.api.Constants.CELL_Y;
import com.obss.go.util.GamePlayUtils;

/**
 * @author omerg
 * 
 * Cell object both visually and logically represents the cell
 * on the game board.
 * 
 * It keeps game logic data such as the coordinates within the 
 * board, stone existence and cellGroup Id as well as 
 * mouseHighlighted state of the visual representation.
 *
 */
public class Cell extends Rectangle {
	
	//logger
	private static final Logger logger = Logger.getLogger("Cell");

	//coordinates
	private CELL_X x;
	private CELL_Y y;
	
	//cell status
	private CELL_STATUS status = CELL_STATUS.EMPTY;
	
	
	/**
	 * Given a player, if placing a stone in a cell puts the 
	 * game in atari
	 * 
	 * The <i>CELL_STATUS</i> can either be <i>BLACK</i> or 
	 * <i>WHITE</i>
	 */
	private CELL_STATUS atariOf = null;
	private Boolean highlighted = false;
	private Integer groupId;

	public Cell(CELL_X x, CELL_Y y) {
		super();

		// set coordinates
		this.setCellX(x);
		this.setCellY(y);

	}

	public void setGroupId() {

		// get neighboring cells
		List<Cell> neighboringCells = GamePlayUtils.getNeighboringCells(this);

		// check if there are other stones in neighboring cells
		for (Cell c : neighboringCells) {

			if (c.getGroupId() != null  && c.getStatus().equals(this.getStatus())) {
				
				// neighbor found. 
				
				//if this cell does not belong to any group,
				// adopt group of neighboring cell
				if (this.getGroupId() == null) {
					this.setGroupId(c.getGroupId());
					Game.getGroupMap().get(c.getGroupId()).getCellList().add(this);
				}
				
				//else if this cell has a different group already, merge two groups
				else {
					StoneGroup existingGroup = Game.getGroupMap().get(c.getGroupId());
					if (existingGroup.getGroupId().equals(this.getGroupId()) == false) {
						existingGroup.merge(this.getGroupId());
					}
				}
			}
		}
				
		//at the end of the iteration above, if the current cell still doesn't
		//belong to any group, assign it a new group.
		if (this.getGroupId() == null) {
			
			//create a new group
			StoneGroup newStoneGroup = new StoneGroup(this.getStatus());
			Game.getGroupMap().put(newStoneGroup.getGroupId(), newStoneGroup);
			
			//set the group id of this cell to the id of newly formed stone group
			this.setGroupId(newStoneGroup.getGroupId());
			
			//add this cell to newly formed group
			Game.getGroupMap().get(newStoneGroup.getGroupId()).getCellList().add(this);
		}
	}

	public void paint(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;

		// draw sides if highlighted
		if (getHighlighted()) {
			g2d.setColor(Color.red);
			g2d.drawRoundRect(x.getCoordinate(), y.getCoordinate(),
					Constants.CELL_SIDE_WIDTH, Constants.CELL_SIDE_WIDTH, 5, 5);
		}

		if (CELL_STATUS.BLACK.equals(getStatus())) {
			new StoneShape(GamePlayUtils.getCellPoint(x, y), Color.BLACK, g2d);
		} else if (CELL_STATUS.WHITE.equals(getStatus())) {
			new StoneShape(GamePlayUtils.getCellPoint(x, y), Color.WHITE, g2d);
		}
	}

	public String toString() {
		return x.toString() + y.toString();
	}

	public Boolean neigbourTo(Cell anotherCell) {
		Integer xDiff = Math.abs(this.getCellX().getValue()
				- anotherCell.getCellX().getValue());
		Integer yDiff = Math.abs(this.getCellY().getValue()
				- anotherCell.getCellY().getValue());
		return (Math.abs(xDiff - yDiff) == 1);
	}


	public CELL_X getCellX() {
		return x;
	}

	public void setCellX(CELL_X x) {
		this.x = x;
	}

	public CELL_Y getCellY() {
		return y;
	}

	public void setCellY(CELL_Y y) {
		this.y = y;
	}

	public CELL_STATUS getStatus() {
		return status;
	}

	public void setStatus(CELL_STATUS status) {
		this.status = status;
	}

	public Boolean getHighlighted() {
		return highlighted;
	}

	public void setHighlighted(Boolean highlighted) {
		this.highlighted = highlighted;
	}

	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((x == null) ? 0 : x.hashCode());
		result = prime * result + ((y == null) ? 0 : y.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cell other = (Cell) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	public void setGroupId(Integer groupId) {
		Integer oldGroupId = this.getGroupId();
		this.groupId = groupId;
		logger.info("GroupId of Cell: " + this.toString() + " has been changed from " 
				+ oldGroupId + " into " + this.getGroupId() + ".");
	}

	public Integer getGroupId() {
		return groupId;
	}
	
	public void setAtariOf(CELL_STATUS atariOf) {
		this.atariOf = atariOf;
	}

	public CELL_STATUS getAtariOf() {
		return atariOf;
	}

	public boolean isBreathing() {
		
		List<Cell> neighboringCellsList = GamePlayUtils.getNeighboringCells(this);
		
		//iterate neighboring cells
		for (Cell neighboringCell : neighboringCellsList) {
			
			//return true if any of the neighboring cells is empty
			if (Constants.CELL_STATUS.EMPTY.equals(neighboringCell.getStatus())) {
				logger.debug("Cell: " + this.toString() + " breathes from position: " + neighboringCell.toString());
				return true;
			} 
		}
		return false;
	}

}
