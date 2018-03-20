package warehouseInterface;

import communication.Connection;
import main.java.JobSelection.Order;

import javax.swing.*;

import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;

public class WarehouseInterface extends JFrame implements ActionListener {

    private static List<Integer> jobArray = new LinkedList<>();
    private JList<String> jobList;
    private static List<String> cancelArray = new LinkedList<>();
    private int index;
    private int outputIndex;
    private DefaultListModel<String> listModel;
    private static List<String> outputArray = new ArrayList<>();
    private DefaultListModel<String> outputModel;
    private JList<String> outputList;
    private int tempIndex;
    private String value;
    private String tempValue;
    private String outputValue;
    private int evenMoreTempIndex;

    public static List<String> getOutputList() {
        return outputArray;
    }

    public static void main(ArrayList<Order> orders) {
        jobReader(orders); // need to adjust this for repo
        SwingUtilities.invokeLater(WarehouseInterface::new);
        Connection.main(orders);
        for (String anOutputArray : outputArray) {
            System.out.println(anOutputArray);
        }
    }

    private static void jobReader(ArrayList<Order> orders) {
        for(Order order : orders){
            jobArray.add(order.getJobID()); // adds jobs from file to array
        }
        for (Integer num: jobArray) {
            outputArray.add(Integer.toString(num));
        }
    }

    private WarehouseInterface() {
        this.getContentPane().setLayout(new FlowLayout());

        listModel = new DefaultListModel<>(); // the list that updates the JFrame
        for (int i = 0; i < jobArray.size(); i++) {
            listModel.add(i, Integer.toString(jobArray.get(i))); // adding job ID to the JFrame
        }

        outputModel = new DefaultListModel<>();
        for (int i = 0; i < outputArray.size(); i++) {
            outputModel.add(i, outputArray.get(i));
        }

        jobList = new JList<>(listModel);
        jobList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                index = jobList.getSelectedIndex();
                value = jobList.getSelectedValue();
                System.out.println("Selected index: " + index);
                outputIndex = jobList.getSelectedIndex();
                outputValue = jobList.getSelectedValue();
            }
        });

        outputList = new JList<>(outputModel);

        add(new JScrollPane(jobList));

        add(new JScrollPane(outputList));

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setTitle("Warehouse Interface");
        this.setSize(275, 225);
        this.setLocationRelativeTo(null);
        this.setResizable(true);
        this.setVisible(true);
        jobList.setFixedCellWidth(100);

        JButton b1 = new JButton("Cancel Job");
        add(b1);
        b1.addActionListener(this);
        b1.setActionCommand("cancel");
    }

    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        if (action.equals("cancel")) {
            try {
                tempIndex = index;
                tempValue = value;
                listModel.remove(index); // The script that runs when the cancel button is pressed
            } catch (IndexOutOfBoundsException e1) {
                System.err.println("No job selected.");
            }
            try {
                for (int i = 0; i < outputModel.size(); i++) {
                    if (tempValue.equals(outputModel.get(i))) {
                        System.out.println("THIS IS I: " + i);
                        outputModel.set(i, "removed");
                        evenMoreTempIndex = i;
                    }
                }

            } catch (IndexOutOfBoundsException e1) {
                System.err.println("Output Model idx out of bounds.");
                System.err.println("index to be removed " + index);
                System.err.println(outputModel.size());
            }
            try {
                outputArray.set(evenMoreTempIndex, "removed");
            } catch (IndexOutOfBoundsException e1) {
                System.err.println("Output array idx out of bounds.");
                System.err.println("index to be removed " + index);
                System.err.println(outputArray.size());

            }
            try {
                System.out.println(tempIndex);
                System.out.println(outputArray.get(tempIndex));
            } catch (IndexOutOfBoundsException e1) {
                System.err.println("Struggling to print output array.");
            }
        }
    }

}
