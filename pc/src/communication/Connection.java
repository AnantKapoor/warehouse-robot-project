package communication;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
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
    	int rank;
	        try {
	        	if(isConnected()) {
	        		logger.debug("PC connected to a robot" + m_nxt.name);
	        		System.out.println("connected to " + m_nxt.name);
	        	}
	            while (isConnected()) {
	            	
	            	for (Order ord : allOrders) {
	            		/*if(Integer.toString(ord.getID()).equals())){
	            			continue;
	            		}*/
	            		
	            		for (int i = 1; i <= ord.getDetail().size(); i++) {
	            			ArrayList<Integer> steps = ord.getPath(i-1);
	            			if(ord.getPath(i-1).size()==0){

	            				continue;
	            			}

	            			
	            			if(steps.size()>0&&steps.get(steps.size()-1)!=4&&steps.get(steps.size()-1)!=5){
	            				if(steps.get(steps.size()-1)!=5){
	            					steps.add(4);
	            				}
	            			}
	            			
	            			rank = i%3;
	            			System.out.print("rank " + rank + " ");
	            			
	            			switch(rank) {
	            			case 0:
	            				checkRobot(steps, "IronHide");
	            				break;
	            			case 1:
	            				checkRobot(steps, "BumbleBee");
	            				break;
	            			case 2:
	            				checkRobot(steps, "OptimusPrime");
	            				break;
	            		
	            			}
	         
	        				

	            		}
	            	}
	                readReply();
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
		int answer = m_dis.readInt();
		System.out.println(m_nxt.name + " returned " + answer);
	}


    /**
     * @param args
     */
    public static void main(ArrayList<Order> orders) {
    	allOrders = orders;
        try {
        	
            NXTInfo[] nxts = {

                    new NXTInfo(NXTCommFactory.BLUETOOTH, "OptimusPrime", "00:16:53:0A:97:1B"),

                   	new NXTInfo(NXTCommFactory.BLUETOOTH, "BumbleBee", "00:16:53:15:5F:A3"),
                   	
                   	new NXTInfo(NXTCommFactory.BLUETOOTH, "IronHide", "00:16:53:0A:9A:AB"),
                   	
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
