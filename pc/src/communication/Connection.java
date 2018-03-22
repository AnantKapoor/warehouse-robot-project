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
	
	public static ArrayList<Order> allOrders;
	private static String[] robotNames = {"OptimusPrime", "BumbleBee", "Megatron"};
    private DataInputStream m_dis;
    private DataOutputStream m_dos;
    private final NXTInfo m_nxt;
    private boolean m_receivedPath;
    private int m_finishedRoute;
    private static boolean[] pathReceived = new boolean[3];
    private static Map<String, Boolean> robotPathReceived = new LinkedHashMap<String, Boolean>();

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

    	int rank;
	        try {
	        	if(isConnected()) {
	        		logger.debug("PC connected to a robot" + m_nxt.name);
	        		System.out.println("connected to " + m_nxt.name);
	        		
	        	}
	        	System.out.println("First check " + m_nxt.name);
	        	readReply();
	            while (isConnected()) {
	            	
	            	for (Order ord : allOrders) {	            		
	            		for (int i = 0; i < ord.getDetail().size(); i++) {
	            			System.out.print(i);
                            /*if(!Integer.toString(ord.getID()).equals(WarehouseInterface.getOutputList().get(i))){
                                continue;
                            }*/
	            			ArrayList<Integer> steps = ord.getPath(i);
	            			if(ord.getPath(i).size()==0){
	            				continue;
	            			}
	            			
	            			if(steps.size()>0&&steps.get(steps.size()-1)!=4&&steps.get(steps.size()-1)!=5){
	            				if(steps.get(steps.size()-1)!=5){
	            					steps.add(4);
	            				}
	            			}
	            			
	            			rank = i%3;
	            			/*System.out.println("Rank 1: Optimus, Rank 2: Bumble, Rank 3: Megatron");
	            			System.out.println("rank " + rank + " " + m_nxt.name);*/

                            switch(rank) {
                                case 0:
                                    m_receivedPath = checkRobot(steps, robotNames[0]);
                                    break;
                                case 1:
                                    m_receivedPath = checkRobot(steps, robotNames[1]);
                                    break;
                                case 2:
                                    m_receivedPath = checkRobot(steps, robotNames[2]);
                                    break;
                            }
                            //System.out.println(m_nxt.name + " received path " + m_receivedPath);
                            if(m_receivedPath) {

                            	if(robotPathReceived.get(robotNames[0])&& robotPathReceived.get(robotNames[1])&& robotPathReceived.get(robotNames[2])){
                                    System.out.println("Sending execute command to " + m_nxt.name);
                                    m_dos.writeInt(50);
                                    m_dos.flush();                      
                                }
                            	System.out.println("Checking if the robot has finished the route  "  + m_nxt.name);
                            	readReply();
                            }

                            
	            		}
	            	}
	            }

	        } catch (IOException e) {
	            e.printStackTrace();
	        }

    }

     private boolean checkRobot(ArrayList<Integer> steps, String name) throws IOException {
		if(m_nxt.name.equals(name)) {
			sendSteps(steps);
            return true;
		}
		return false;
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


    /**
     * @param orders
     */
    public static void main(ArrayList<Order> orders) {
    	allOrders = orders;
        for(int i = 0; i <3; i++){
            robotPathReceived.put(robotNames[i], false);
        }
        try {
        	
            NXTInfo[] nxts = {

                    new NXTInfo(NXTCommFactory.BLUETOOTH, robotNames[0], "00:16:53:0A:97:1B"),

                   	new NXTInfo(NXTCommFactory.BLUETOOTH, robotNames[1], "00:16:53:15:5F:A3"),
                   	
                   	//new NXTInfo(NXTCommFactory.BLUETOOTH, robotNames[2], "00:16:53:0A:9A:AB"),
                   	
                   	new NXTInfo(NXTCommFactory.BLUETOOTH, robotNames[2], "00:16:53:08:9B:0D"),};
            

            ArrayList<Connection> connections = new ArrayList<Connection>(
                    nxts.length);

            /*for (NXTInfo nxt : nxts) {
                connections.add(new Connection(nxt));
            }*/
            Connection Optimus = new Connection(nxts[0]);
            Connection Bumble = new Connection(nxts[1]);
            Connection Megatron = new Connection(nxts[2]);

            connections.add(Optimus);
            connections.add(Bumble);
            connections.add(Megatron);

            for (Connection connection : connections) {
                NXTComm nxtComm = NXTCommFactory
                        .createNXTComm(NXTCommFactory.BLUETOOTH);
                connection.connect(nxtComm);
                logger.debug("NXT connected to a robot");
            }

            Optimus.start();
            Bumble.start();
            Megatron.start();

            try {
                Optimus.join();
                Bumble.join();
                Megatron.join();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            /*ArrayList<Thread> threads = new ArrayList<Thread>(nxts.length);

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
            }*/

        } catch (NXTCommException e) {
            e.printStackTrace();
        }

    }
}
