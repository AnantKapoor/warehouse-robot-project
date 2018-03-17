import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.SwingUtilities;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.AbstractButton;
import javax.swing.JButton;

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
        jobReader();
        SwingUtilities.invokeLater(WarehouseInterface::new);
    }

    private static void jobReader() {
        String filePath = "C:/Users/Will/warehouse-assignment/pc/resources/jobs.csv";
        BufferedReader reader = null;
        String line;
        String csvSplitBy = "\\r?\\n";

        try {
            reader = new BufferedReader(new FileReader(filePath));
            while ((line = reader.readLine()) != null) {
                jobArray.add(Arrays.toString(line.split(csvSplitBy)));

//                for (String aJobArray : jobArray) {
//                    System.out.println(aJobArray);
//                }
                System.out.println(jobArray.size());
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

        listModel = new DefaultListModel<>();
        for (int i = 0; i < jobArray.size(); i++) {
            listModel.add(i, jobArray.get(i).substring(1, 6));
        }

        jobList = new JList<>(listModel);
        jobList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                final List<String> selectedValuesList = jobList.getSelectedValuesList();
                index = jobList.getSelectedIndex();
                cancelArray.add(0, jobList.getSelectedValue());
                // System.out.println(selectedValuesList);
                System.out.println(cancelArray.get(0));
            }
        });

        add(new JScrollPane(jobList));

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Warehouse Interface");
        this.setSize(200, 225);
        this.setLocationRelativeTo(null);
        this.setResizable(true);
        this.setVisible(true);
        jobList.setFixedCellWidth(100);

        JButton b1 = new JButton("Cancel");
        add(b1);
        b1.addActionListener(this);
        b1.setActionCommand("cancel");
    }

    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        if (action.equals("cancel")) {
            listModel.remove(index);
            // System.out.println(cancelArray.get(0));
        }
    }

}
