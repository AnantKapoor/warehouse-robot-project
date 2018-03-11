package communication;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import lejos.pc.comm.NXTComm;
import lejos.pc.comm.NXTCommException;
import lejos.pc.comm.NXTCommFactory;
import lejos.pc.comm.NXTInfo;

public class Connection implements Runnable {

    private DataInputStream m_dis;
    private DataOutputStream m_dos;
    private final NXTInfo m_nxt;

    public Connection(NXTInfo _nxt) {
        m_nxt = _nxt;
    }

    private boolean connect(NXTComm _comm) throws NXTCommException {
        if (_comm.open(m_nxt)) {

            m_dis = new DataInputStream(_comm.getInputStream());
            m_dos = new DataOutputStream(_comm.getOutputStream());
        }
        return isConnected();
    }

    private boolean isConnected() {
        return m_dos != null;
    }

    @Override
    public void run() {
    Scanner scan = new Scanner(System.in);
    //int message = 1;
        try {
        	if(isConnected()) {
        		System.out.println("connected to");
        	}
            while (isConnected()) {
            	
            	
            	int message = scan.nextInt();
                m_dos.writeInt(message);
                m_dos.flush();

                int answer = m_dis.readInt();
                System.out.println(m_nxt.name + " returned " + answer);
            }
            scan.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * @param args
     */
    public static void main(String[] args) {
        try {

            NXTInfo[] nxts = {

                    new NXTInfo(NXTCommFactory.BLUETOOTH, "OptimusPrime", "00:16:53:0A:97:1B"),};

                   	//new NXTInfo(NXTCommFactory.BLUETOOTH, "Megatron", "0016530A9ABC"), };

            ArrayList<Connection> connections = new ArrayList<>(
                    nxts.length);

            for (NXTInfo nxt : nxts) {
                connections.add(new Connection(nxt));
            }

            for (Connection connection : connections) {
                NXTComm nxtComm = NXTCommFactory
                        .createNXTComm(NXTCommFactory.BLUETOOTH);
                connection.connect(nxtComm);
            }

            ArrayList<Thread> threads = new ArrayList<>(nxts.length);

            for (Connection connection : connections) {
                threads.add(new Thread(connection));
            }

            for (Thread thread : threads) {
                thread.start();
            }

            for (Thread thread : threads) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        } catch (NXTCommException e) {
            e.printStackTrace();
        }

    }
}
