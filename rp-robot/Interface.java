package warehouse;

import lejos.nxt.Button;
import lejos.nxt.LCD;

public class Interface {
	public static int[] getCoordinates() {
		int x = 0;
		int y = 0;
		int coordinate = 1;
		LCD.drawString(x + ", " + y, 6, 4);
		while (true) {
			while (coordinate == 1) {
				int button = Button.waitForAnyPress();
				LCD.clear();
				if (button == Button.ID_ENTER) {
					coordinate = 2;
				} else if (button == Button.ID_LEFT) {
					x--;
				} else if (button == Button.ID_RIGHT) {
					x++;
				}
				if (x <= 0) {
					x = 0;
				} else if (x >= 12) {
					x = 12;
				}
				LCD.drawString(x + ", " + y, 6, 4);
			}
			while (coordinate == 2) {
				int button = Button.waitForAnyPress();
				LCD.clear();
				if (button == Button.ID_ESCAPE) {
					coordinate = 1;
				} else if (button == Button.ID_ENTER) {
					coordinate = 3;
				} else if (button == Button.ID_LEFT) {
					y--;
				} else if (button == Button.ID_RIGHT) {
					y++;
				}
				if (y <= 0) {
					y = 0;
				} else if (y >= 8) {
					y = 8;
				}
				LCD.drawString(x + ", " + y, 6, 4);
			}
			if (coordinate == 3) {
				break;
			} else {
				continue;
			}
		}
		LCD.clear();
		return new int[] { x, y };
	}

	public static void printPose(int[] coordinates, int direction) {
		LCD.clear(1);
		LCD.drawChar(',', 5, 1);
		LCD.drawChar(',', 10, 1);
		LCD.drawInt(coordinates[0], 2, 1);
		LCD.drawInt(coordinates[1], 7, 1);
		LCD.drawInt(direction, 13, 1);
	}

	private static void confirmation() {
		LCD.drawString("Press the ENTER", 0, 5);
		LCD.drawString("button to", 3, 6);
		LCD.drawString("confirm!", 4, 7);
		while (true) {
			int buttonPress = Button.waitForAnyPress();
			if (buttonPress == Button.ID_ENTER) {
				LCD.clear(3);
				LCD.clear(5);
				
				LCD.clear(6);
				LCD.clear(7);
				break;
			}
		}
	}

	public static void print(int message) {
		LCD.clear(3);
		switch (message) {
		case 3:
			LCD.drawString("Dropping item!", 1, 3);
			break;
		case 4:
			LCD.drawString("Picking up item!", 0, 3);
			break;
		}
		confirmation();
	}

}