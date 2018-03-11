package communication;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import lejos.nxt.Button;
import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;
import java.util.ArrayList;

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
		Robot robot = new Robot();
		ArrayList<Integer> path = new ArrayList<>();
        boolean run = true;
        while (run) {

            try {
                int input = inputStream.readInt();
                path.add(input);
				if (input == 5) {
					break;
				}
            } catch (IOException e) {
                System.out.println("Exception: " + e.getClass());
                run = false;
            }
        }
		robot.executeRoute(path);
    }

}
