package communication;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

import org.apache.log4j.Logger;

import lejos.pc.comm.NXTComm;
import lejos.pc.comm.NXTCommException;
import lejos.pc.comm.NXTCommFactory;
import lejos.pc.comm.NXTInfo;
import controller.*;
import main.java.JobSelection.*;
import main.java.PathFinding.*;

public class Connection implements Runnable {

	private static final Logger logger = Logger.getLogger(Run.class);
	
	public static ArrayList<Order> allOrders;
	private static String[] robotNames = {"OptimusPrime", "BumbleBee", "IronHide"};
    private DataInputStream m_dis;
    private DataOutputStream m_dos;
    private final NXTInfo m_nxt;
    private static Map<String, Boolean> robotPathFinished = new LinkedHashMap<String, Boolean>();

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
	        	readReply();
	            while (isConnected()) {
	            	
	            	for (Order ord : allOrders) {
	            		/*if(Integer.toString(ord.getID()).equals())){
	            			continue;
	            		}*/
	            		
	            		for (int i = 0; i < ord.getDetail().size(); i++) {
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
	            			

                            switch(rank) {
                                case 0:
                                    checkRobot(steps, robotNames[0]);
                                    System.out.println(rank + " " + m_nxt.name);
                                    break;
                                case 1:
                                    checkRobot(steps, robotNames[1]);
                                    System.out.println(rank + " " + m_nxt.name);
                                    break;
                                case 2:
                                    checkRobot(steps, robotNames[2]);
                                    System.out.println(rank + " " + m_nxt.name);
                                    break;
                            }
                            if(robotPathFinished.get(robotNames[0])&&robotPathFinished.get(robotNames[1])&&robotPathFinished.get(robotNames[2])){
                                m_dos.writeInt(50);
                                m_dos.flush();
                                robotPathFinished.put(m_nxt.name, false);
                            }
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
			//System.out.println(step);
			System.out.println(step + " " + m_nxt.name);
			m_dos.writeInt(step);
			m_dos.flush();
		}
		readReply();
	}

	private void readReply() throws IOException {
        System.out.println("Waiting for reply from " + m_nxt.name);
        int answer = m_dis.readInt();
		System.out.println(m_nxt.name + " returned " + answer);
        robotPathFinished.put(m_nxt.name, true);
	}


    /**
     * @param orders
     */
    public static void main(ArrayList<Order> orders) {
    	allOrders = orders;
        for(int i = 0; i <3; i++){
            robotPathFinished.put(robotNames[i], false);
        }
        try {
        	
            NXTInfo[] nxts = {

                    new NXTInfo(NXTCommFactory.BLUETOOTH, robotNames[0], "00:16:53:0A:97:1B"),

                   	new NXTInfo(NXTCommFactory.BLUETOOTH, robotNames[1], "00:16:53:15:5F:A3"),
                   	
                   	new NXTInfo(NXTCommFactory.BLUETOOTH, robotNames[2], "00:16:53:0A:9A:AB"),
                   	
                   	/*new NXTInfo(NXTCommFactory.BLUETOOTH, "Megatron", "00:16:53:08:9B:0D"),*/};
            

            ArrayList<Connection> connections = new ArrayList<Connection>(
                    nxts.length);

            for (NXTInfo nxt : nxts) {
                connections.add(new Connection(nxt));
            }

            for (Connection connection : connections) {
                NXTComm nxtComm = NXTCommFactory
                        .createNXTComm(NXTCommFactory.BLUETOOTH);
                connection.connect(nxtComm);
                logger.debug("NXT connected to a robot");
            }

            ArrayList<Thread> threads = new ArrayList<Thread>(nxts.length);

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
