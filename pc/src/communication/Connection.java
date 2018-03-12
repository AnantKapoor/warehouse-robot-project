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
import controller.*;
import main.java.JobSelection.*;
import main.java.PathFinding.*;

public class Connection implements Runnable {

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
    	
	    Scanner scan = new Scanner(System.in);
	        try {
	        	if(isConnected()) {
	        		System.out.println("connected to" + m_nxt.name);
	        	}
	            while (isConnected()) {
	            	
	            	for (Order ord : allOrders) {
	            	//Order ord=allOrders.get(0);
	            		
	            		int n = ord.getDetail().size();
	            		int counter=ord.getDetail().size()-2;
	            		for (int i = 0; i < ord.getDetail().size(); i++) {
	            			ArrayList<Integer> steps = ord.getPath(i);
	            			if(ord.getPath(i).size()==0){
	            				//counter--;
	            				continue;
	            			}
	            			//if(steps.size()==0){
	            				//steps.add(4);
	            			//}
	            			
	            			if(steps.size()>0&&steps.get(steps.size()-1)!=4&&steps.get(steps.size()-1)!=5){
	            				if(steps.get(steps.size()-1)!=5){
	            					steps.add(4);
	            				}
	            			}
	            			for(int step : steps) {
	            				//System.out.println(step);
	            				System.out.println(step);
	            				m_dos.writeInt(step);
	            				m_dos.flush();
	            			}
	            			/*if(steps.size()==0){
	            			System.out.println(4);
	            			m_dos.writeInt(4);
	            			}*/
            				//m_dos.flush();
	            			//System.out.println("I am here");
	            			//System.out.println(5);
	            			//m_dos.writeInt(5); // telling the robot it needs to wait for a button press
	        				//m_dos.flush();
	        				//System.out.println("I am here");
	        				readReply();
	        				//System.out.println("I am here");
	            		}
	            	}
	            	
	            	int message = scan.nextInt();
	            	System.out.println(message);
	                m_dos.writeInt(message);
	                m_dos.flush();
	
	                readReply();
	            }
	            scan.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

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

                    //new NXTInfo(NXTCommFactory.BLUETOOTH, "OptimusPrime", "00:16:53:0A:97:1B"),};

                   	new NXTInfo(NXTCommFactory.BLUETOOTH, "Megatron", "00:16:53:08:9B:0D"), };

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
                thread.run();
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
