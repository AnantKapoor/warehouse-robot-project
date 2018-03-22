package communication;

import lejos.pc.comm.NXTComm;
import lejos.pc.comm.NXTCommException;
import lejos.pc.comm.NXTCommFactory;
import lejos.pc.comm.NXTInfo;
import main.java.JobSelection.Order;
import org.apache.log4j.Logger;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class ConnectionController {
    private static final Logger logger = Logger.getLogger(ConnectionController.class);
    public static ArrayList<Order> allOrders;
    private static String[] robotNames = {"OptimusPrime", "BumbleBee", "Megatron"};
    private static Map<String, Boolean> robotPathReceived = new LinkedHashMap<String, Boolean>();
    private static ArrayList<Order> firstJobs = new ArrayList<>();
    private static ArrayList<Order> secondJobs = new ArrayList<>();
    private static ArrayList<Order> thirdJobs = new ArrayList<>();
    private static int rank;

    public static void main(ArrayList<Order> orders) {
        allOrders = orders;
        for (int i = 0; i < allOrders.size(); i++) {
            rank = i % 3;

            switch (rank) {
                case 0:
                    firstJobs.add(allOrders.get(i));
                    break;
                case 1:
                    secondJobs.add(allOrders.get(i));
                    break;
                case 2:
                    secondJobs.add(allOrders.get(i));
                    break;
            }
        }
        try {
            NXTInfo[] nxts = {
                    new NXTInfo(NXTCommFactory.BLUETOOTH, robotNames[0], "00:16:53:0A:97:1B"),

                    new NXTInfo(NXTCommFactory.BLUETOOTH, robotNames[1], "00:16:53:15:5F:A3"),

                    //new NXTInfo(NXTCommFactory.BLUETOOTH, robotNames[2], "00:16:53:0A:9A:AB"),

                    new NXTInfo(NXTCommFactory.BLUETOOTH, robotNames[2], "00:16:53:08:9B:0D"),};


            ArrayList<Connection> connections = new ArrayList<Connection>(nxts.length);

            Connection Optimus = new Connection(nxts[0], firstJobs);
            Connection Bumble = new Connection(nxts[1], secondJobs);
            Connection Megatron = new Connection(nxts[2], thirdJobs);
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


        } catch (NXTCommException e) {
            e.printStackTrace();
        }
    }

}
