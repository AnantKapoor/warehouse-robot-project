package main.java.JobSelection;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ItemSpecifications {
    private Map<String, Specifications> itemSpecification = new HashMap<String, Specifications>();

    public void addSpecifications (String item, double reward, double weight) {
        itemSpecification.put(item, new Specifications(reward, weight));
    }

    public Map<String, Specifications> getItemSpecification() {
        return itemSpecification;
    }

    public String toString (String item){
        Specifications itemData = itemSpecification.get(item);
        String text = item + " " + itemData.getReward() + " " + itemData.getWeight();
        return text;
    }
}
