package communication;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import org.apache.log4j.Logger;

import lejos.pc.comm.NXTComm;
import lejos.pc.comm.NXTCommException;
import lejos.pc.comm.NXTCommFactory;
import lejos.pc.comm.NXTInfo;
import main.java.JobSelection.*;
import warehouseInterface.WarehouseInterface;

public class Connection extends Thread {

	private static final Logger logger = Logger.getLogger(Run.class);
	
	public static ArrayList<Order> m_allOrders;
	private static String[] robotNames = {"OptimusPrime", "BumbleBee", "Megatron"};
    private DataInputStream m_dis;
    private DataOutputStream m_dos;
    private final NXTInfo m_nxt;
    private boolean m_receivedPath;
    private static boolean[] pathReceived = new boolean[3];
    private static Map<String, Boolean> robotPathReceived = new LinkedHashMap<String, Boolean>();

    public Connection(NXTInfo _nxt, ArrayList<Order> orders) {
        m_nxt = _nxt;
        m_allOrders = orders;
    }

    public boolean connect(NXTComm _comm) throws NXTCommException {
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
        try {
            if (isConnected()) {
                logger.debug("PC connected to a robot" + m_nxt.name);
                System.out.println("connected to " + m_nxt.name);

            }
            System.out.println("First check " + m_nxt.name);
            readReply();
            while (isConnected()) {

                for (Order ord : m_allOrders) {
                    for (int i = 0; i < ord.getDetail().size(); i++) {
                            /*if(!Integer.toString(ord.getID()).equals(WarehouseInterface.getOutputList().get(i))){
                                continue;
                            }*/
                        ArrayList<Integer> steps = ord.getPath(i);
                        if (ord.getPath(i).size() == 0) {
                            continue;
                        }

                        if (steps.size() > 0 && steps.get(steps.size() - 1) != 4 && steps.get(steps.size() - 1) != 5) {
                            if (steps.get(steps.size() - 1) != 5) {
                                steps.add(4);
                            }
                        }
                        checkRobot(steps, m_nxt.name);

                        if (robotPathReceived.get(robotNames[0]) && robotPathReceived.get(robotNames[1]) && robotPathReceived.get(robotNames[2])) {
                            System.out.println("Sending execute command to " + m_nxt.name);
                            m_dos.writeInt(50);
                            m_dos.flush();
                        }
                        System.out.println("Checking if the robot has finished the route  " + m_nxt.name);
                        readReply();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

     private void checkRobot(ArrayList<Integer> steps, String name) throws IOException {
		if(m_nxt.name.equals(name)) {
			sendSteps(steps);
        }

	}

	private void sendSteps(ArrayList<Integer> steps) throws IOException {
		for(int step : steps) {			
			System.out.println(step + " " + m_nxt.name);
			m_dos.writeInt(step);
			m_dos.flush();
		}
		System.out.println("Checking if robot has finished receiving the route  " + m_nxt.name );
		readReply();

	}

	private void readReply() throws IOException {
        System.out.println("Waiting for reply from " + m_nxt.name);
        int answer = m_dis.readInt();
		System.out.println(m_nxt.name + " returned " + answer);
		
		if(answer == 4 || answer == 5) {
			System.out.println(m_nxt.name + " finished receiving the path");
			robotPathReceived.put(m_nxt.name, true);
			
		} else if (answer == 51) {
			System.out.println(m_nxt.name + " finished the path");
            robotPathReceived.put(m_nxt.name, false);
            
		} else if(answer == -1) {
			System.out.println(m_nxt.name + " is ready to receive the path");
			
		} else {
			System.out.println("Unknown message " + answer + " from: " + m_nxt.name);
		}
        
	}
}
