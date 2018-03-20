package warehouseInterface;

import javax.swing.*;
import java.util.ArrayList;

public class CancelJob {

    public ArrayList<Integer> orders;

    CancelJob(DefaultListModel listModel, int index) {
        listModel.remove(index);
    }

    public void setCurrentJobs(ArrayList<Integer> orders) {
        this.orders.addAll(WarehouseInterface.getJobList());
    }

    

}
