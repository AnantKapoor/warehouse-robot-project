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
        System.out.println("Connected");
        boolean run = true;
        while (run) {

            try {
                int input = inputStream.readInt();
                System.out.println("Received " + input + " from pc");
                switch (input) {
                    case 1:
                        outputStream.writeInt(1);
                        outputStream.flush();
                        break;
                    case -1:
                        outputStream.writeInt(-1);
                        outputStream.flush();
                        break;
                    case 0:
                        outputStream.writeInt(0);
                        outputStream.flush();
                        break;
                    default:
                    	outputStream.writeInt(100);
                    	outputStream.flush();
                    	break;
                }
            } catch (IOException e) {
                System.out.println("Exception: " + e.getClass());
                run = false;
            }
        }
    }

}
