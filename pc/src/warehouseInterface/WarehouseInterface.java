import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.FlowLayout;

public class WarehouseInterface extends JFrame implements ActionListener {

    private static List<String> jobArray = new LinkedList<>();
    private JList<String> jobList;
    private static List<String> cancelArray = new LinkedList<>();
    private int index;
    private DefaultListModel<String> listModel;

    public static void main(String[] args) {
        jobReader("C:/Users/Will/warehouse-assignment/pc/resources/jobs.csv"); // need to adjust this for repo
        SwingUtilities.invokeLater(WarehouseInterface::new);
    }

    private static void jobReader(String filePath) {
        BufferedReader reader = null;
        String line;
        String csvSplitBy = "\\r?\\n";

        try {
            reader = new BufferedReader(new FileReader(filePath));
            while ((line = reader.readLine()) != null) {
                jobArray.add(Arrays.toString(line.split(csvSplitBy))); // adds jobs from file to array

//                for (String aJobArray : jobArray) {
//                    System.out.println(aJobArray);
//                }
                System.out.println(jobArray.size()); // debug - get rid of in final version
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private WarehouseInterface() {
        this.getContentPane().setLayout(new FlowLayout());

        listModel = new DefaultListModel<>(); // the list that updates the JFrame
        for (int i = 0; i < jobArray.size(); i++) {
            listModel.add(i, jobArray.get(i).substring(1, 6)); // adding job ID to the JFrame
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
                listModel.remove(index); // The script that runs when the cancel button is pressed
            } catch (IndexOutOfBoundsException e1) {
                System.err.println("No job selected.");
            }
        }
    }

}
