package main.java.JobSelection;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ItemSpecifications {
    private Map<Character, Specifications> itemSpecification = new HashMap<Character, Specifications>();

    public void addSpecifications (char item, double reward, double weight) {
        itemSpecification.put(item, new Specifications(reward, weight));
    }

    public Map<Character, Specifications> getItemSpecification() {
        return itemSpecification;
    }

    public String toString (char item){
        Specifications itemData = itemSpecification.get(item);
        String text = item + " " + itemData.getReward() + " " + itemData.getWeight();
        return text;
    }
}
