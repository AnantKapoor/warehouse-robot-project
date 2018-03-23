package warehouse;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;
import lejos.util.Delay;

public class Client {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		System.out.println("Waiting for Bluetooth connection...");
		BTConnection connection = Bluetooth.waitForConnection();
		System.out.println("OK!");

		DataInputStream inputStream = connection.openDataInputStream();
		DataOutputStream outputStream = connection.openDataOutputStream();
		System.out.println("Connected");

		Delay.msDelay(500);
		LCD.clear();
		outputStream.writeInt(-1);
		outputStream.flush();
		Robot robot = new Robot();
		ArrayList<Integer> path = new ArrayList<>();
		LCD.drawString("Waiting for pc..", 0, 3);
		while (true) {
			try {
				int input = inputStream.readInt();
				path.add(input);
				if (input == 4 || input == 5) {
					LCD.drawString("Path received!", 1, 4);
					outputStream.writeInt(input);
					outputStream.flush();
					int message = inputStream.readInt();
					if (message == 50) {
						LCD.drawString("Press to execute", 0, 5);
						Button.waitForAnyPress();
						LCD.drawString("Executing route", 0, 6);
						robot.executeRoute(path);
						outputStream.writeInt(51);
						outputStream.flush();
						path.clear();
					}
				}
			} catch (IOException e) {
				System.out.println("Exception: " + e.getClass());
				break;
			}
		}
	}

}
