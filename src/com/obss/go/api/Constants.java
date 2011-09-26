package com.obss.go.api;

public class Constants {

	public enum MESSAGE_TYPE {
		ERROR("Error!"), WARNING("Warning!"), INFO("Info");

		private final String text;

		private MESSAGE_TYPE(String text) {
			this.text = text;
		}

		public String getValue() {
			return text;
		}
	}

	// board size
	public static final Integer BOARD_WIDTH = 480;
	public static final Integer BOARD_HEIGHT = 500;

	public static final Integer CELLS_PER_ROW = 13;
	public static final Integer TABLE_MARGIN = 30;
	public static final Integer CELL_SIDE_WIDTH = 34;
	public static final Integer BIG_DOT_RADIUS = 10;

	public enum CELL_X {
		X0(0), X1(1), X2(2), X3(3), X4(4), X5(5), X6(6), X7(7), X8(8), X9(9), X10(
				10), X11(11), X12(12);

		private final Integer x;

		private CELL_X(Integer x) {
			this.x = x;
		}

		public Integer getValue() {
			return x;
		}

		public Integer getCoordinate() {
			return Constants.TABLE_MARGIN - (Constants.CELL_SIDE_WIDTH/2) + (x * Constants.CELL_SIDE_WIDTH);
		}
	}

	public enum CELL_Y {
		Y0(0), Y1(1), Y2(2), Y3(3), Y4(4), Y5(5), Y6(6), Y7(7), Y8(8), Y9(9), Y10(
				10), Y11(11), Y12(12);

		private final Integer y;

		private CELL_Y(Integer Y) {
			this.y = Y;
		}

		public Integer getValue() {
			return y;
		}

		public Integer getCoordinate() {
			return Constants.TABLE_MARGIN - (Constants.CELL_SIDE_WIDTH/2) + (y * Constants.CELL_SIDE_WIDTH);
		}
	}

	public enum CELL_STATUS {
		BLACK, WHITE, EMPTY;
	}

}
