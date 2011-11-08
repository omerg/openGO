package com.obss.go.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.obss.go.api.Constants.CELL_STATUS;

public class StoneGroup {

	private static Integer nextGroupId = 0;

	// logger
	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger("StoneGroup");

	private Integer groupId;

	private List<Cell> cellList;
	private CELL_STATUS color;

	public StoneGroup(CELL_STATUS color) {
		super();
		this.setGroupId();
		this.setCellList(new ArrayList<Cell>());
		this.setColor(color);
	}

	public void setCellList(List<Cell> cellList) {
		this.cellList = cellList;
	}

	public List<Cell> getCellList() {
		return cellList;
	}


	public void setColor(CELL_STATUS color) {
		this.color = color;
	}

	public CELL_STATUS getColor() {
		return color;
	}

	private void setGroupId() {
		if (this.getGroupId() == null) {
			this.groupId = nextGroupId;
			nextGroupId++;
		}
	}
	
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
		
		//set groupIDs of member cells as well
		for (Cell c : this.getCellList()) {
			c.setGroupId(groupId);
		}
	}

	public Integer getGroupId() {
		return groupId;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((groupId == null) ? 0 : groupId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StoneGroup other = (StoneGroup) obj;
		if (groupId == null) {
			if (other.groupId != null)
				return false;
		} else if (!groupId.equals(other.groupId))
			return false;
		return true;
	}

}
