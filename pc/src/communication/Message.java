package communication;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public abstract class Message {
    DataInputStream iStream;
    DataOutputStream oStream;

    public Message(DataInputStream inputStream, DataOutputStream outputStream){
        this.iStream = inputStream;
        this.oStream = outputStream;

    }

    public void sendMessage(String message) {
        try {
            oStream.writeChars(message);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
