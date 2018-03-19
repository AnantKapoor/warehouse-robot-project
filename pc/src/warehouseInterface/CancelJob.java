package warehouseInterface;

import javax.swing.*;

public class CancelJob {

    CancelJob(DefaultListModel listModel, int index) {
        listModel.remove(index);
    }



}
