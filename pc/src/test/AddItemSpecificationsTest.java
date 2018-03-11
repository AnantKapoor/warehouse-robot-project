package test;
import static org.junit.Assert.*;
import main.java.JobSelection.ItemSpecifications;

import org.junit.Test;

public class AddItemSpecificationsTest {
	@Test
	public void addItemSpecificationSingle(){
		ItemSpecifications itemSpecs = new ItemSpecifications();
		itemSpecs.addSpecifications('a', 1234, 56);
		String requiredResult = "a 1234.0 56.0";
		assertEquals(itemSpecs.toString('a'), requiredResult);
	}
	
	@Test
	public void addItemSpecificationMultiple(){
		ItemSpecifications itemSpecs = new ItemSpecifications();
		itemSpecs.addSpecifications('a', 1234, 56);
		itemSpecs.addSpecifications('b', 567, 32);
		String requiredResult = "a 1234.0 56.0";
		assertEquals(itemSpecs.toString('a'), requiredResult);
	}
}
