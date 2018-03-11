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

  private static String[] jobArray;
  private JList<String> jobList;

  public static void main(String[] args) {
    jobReader();
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        new WarehouseInterface();
      }
    });
  }

  public static void jobReader() {
    String filePath = "../resources/jobs.csv";
    BufferedReader reader = null;
    String line = "";
    String csvSplitBy = "\\r?\\n";

    try {
      reader = new BufferedReader(new FileReader(filePath));
      while ((line = reader.readLine()) != null) {
        jobArray = line.split(csvSplitBy);

        for(int i = 0; i < jobArray.length; i++) {
          System.out.println(jobArray[i]);
        }
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
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

  public WarehouseInterface() {
    this.getContentPane().setLayout(new FlowLayout());

    DefaultListModel<String> listModel = new DefaultListModel<>();
    for (int i = 0; i < jobArray.length; i++) {
      listModel.addElement(jobArray[i]);
    }

    jobList = new JList<>(listModel);
    jobList.addListSelectionListener(new ListSelectionListener() {
      @Override
      public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
          final List<String> selectedValuesList = jobList.getSelectedValuesList();
          System.out.println(selectedValuesList);
        }
      }
    });

    add(new JScrollPane(jobList));

    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setTitle("Warehouse Interface");
    this.setSize(200, 200);
    this.setLocationRelativeTo(null);
    this.setVisible(true);

    JButton b1 = new JButton("Cancel");
    add(b1);
    b1.addActionListener(this);
    b1.setActionCommand("cancel");
  }

  public void actionPerformed(ActionEvent e) {
    String action = e.getActionCommand();
    if (action.equals("cancel")) {
      System.out.println("cancel");
    }
  }

}
