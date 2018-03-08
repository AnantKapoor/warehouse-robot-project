package communication;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import lejos.nxt.Button;
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

        boolean run = true;
        while (run) {

            try {
                String input = inputStream.readLine();

                switch (input) {
                    case "test":
                        Button.waitForAnyPress();
                        outputStream.writeChars("test");
                        outputStream.flush();
                        break;
                    case "stop":
                        run = false;
                        break;
                }
            } catch (IOException e) {
                System.out.println("Exception: " + e.getClass());
                run = false;
            }
        }
    }

}
