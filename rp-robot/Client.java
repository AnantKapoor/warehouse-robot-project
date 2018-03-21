package warehouse;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

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
		Robot robot = new Robot();
		ArrayList<Integer> path = new ArrayList<>();
		outputStream.writeInt(-1);
		outputStream.flush();
		while (true) {
			try {
				int input = inputStream.readInt();
				path.add(input);
				if (input == 4 || input == 5) {
					robot.executeRoute(path);
					path.clear();
					outputStream.writeInt(input);
					outputStream.flush();
				}
			} catch (IOException e) {
				System.out.println("Exception: " + e.getClass());
				break;
			}
		}
	}

}
