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
    private DefaultListModel<String> listModel;

    public int getCurrentJob() {
        return jobArray.get(0);
    }

    public void completedJob() {
        listModel.remove(0);
    }

    public static void main(ArrayList<Order> orders) {
        jobReader(orders); // need to adjust this for repo
        SwingUtilities.invokeLater(WarehouseInterface::new);
        Connection.main(orders);
    }

    private static void jobReader(ArrayList<Order> orders) {
        for(Order order : orders){
            jobArray.add(order.getJobID()); // adds jobs from file to array

        }
    }

    private WarehouseInterface() {
        this.getContentPane().setLayout(new FlowLayout());

        listModel = new DefaultListModel<>(); // the list that updates the JFrame
        for (int i = 0; i < jobArray.size(); i++) {
            listModel.add(i, Integer.toString(jobArray.get(i))); // adding job ID to the JFrame
        }

        jobList = new JList<>(listModel);
        jobList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                final List<String> selectedValuesList = jobList.getSelectedValuesList();
                index = jobList.getSelectedIndex();
                cancelArray.add(0, jobList.getSelectedValue());
                // System.out.println(selectedValuesList);
                System.out.println(cancelArray.get(0)); // checks what to do with selected items i.e delete them
            }
        });

        add(new JScrollPane(jobList));

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
                new CancelJob(listModel, index); // The script that runs when the cancel button is pressed
            } catch (IndexOutOfBoundsException e1) {
                System.err.println("No job selected.");
            }
        }
    }

}
