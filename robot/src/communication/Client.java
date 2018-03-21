package communication;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;

public class Client {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		System.out.println("Waiting for Bluetooth connection...");
		BTConnection connection = Bluetooth.waitForConnection();
		System.out.println("OK!");

		DataInputStream inputStream = connection.openDataInputStream();
		DataOutputStream outputStream = connection.openDataOutputStream();
		System.out.println("Connected");
		//Robot robot = new Robot();
		ArrayList<Integer> path = new ArrayList<>();
		while (true) {

			try {
				int input = inputStream.readInt();
				path.add(input);
				System.out.println("added " + input);
				if (input == 5) {
					break;
				}
			} catch (IOException e) {
				System.out.println("Exception: " + e.getClass());
				break;
			}
		}
		System.out.println("executing path : " + path);
		//robot.executeRoute(path);
		try {
			outputStream.writeInt(5);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
