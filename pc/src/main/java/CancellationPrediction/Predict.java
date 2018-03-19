package main.java.CancellationPrediction;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;

import main.java.JobSelection.Run;

import org.apache.log4j.Logger;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.RandomForest;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.converters.ArffLoader;
import weka.core.converters.CSVLoader;
import weka.core.converters.CSVSaver;
import weka.core.converters.Loader;

/**
 * The Predict class uses the trained Classifier and the test data to create a
 * prediction CSV file. As noted inthe README.md, we modified the original
 * test.csv file to contain the 'survived' column. We do not actually use the
 * values of this column, weka simply requires the train and test data to match.
 * 
 * @author jbirchfield
 * 
 */
public class Predict {

	private static final Logger logger = Logger.getLogger(Run.class);
	
	public static void main() throws Exception {

		//loading data from .arff file
		ArffLoader testLoader = new ArffLoader();
		testLoader.setSource(new File("resources/predictionData.arff"));
		testLoader.setRetrieval(Loader.BATCH);
		Instances testDataSet = testLoader.getDataSet();

		//tells the data set which attribute we want to classify (first column - cancellation)

		Attribute testAttribute = testDataSet.attribute(0);
		testDataSet.setClass(testAttribute);
		testDataSet.deleteStringAttributes();

		//reads in the serialised model from disk
		
		Classifier classifier = (Classifier) SerializationHelper
				.read("resources/jobsTraining.model");

		ArffLoader test1Loader = new ArffLoader();
		test1Loader.setSource(new File("resources/predictionData.arff"));
		Instances test1DataSet = test1Loader.getDataSet();
		Attribute test1Attribute = test1DataSet.attribute(0);
		test1DataSet.setClass(test1Attribute);

		Enumeration testInstances = testDataSet.enumerateInstances();
		Enumeration test1Instances = test1DataSet.enumerateInstances();
		while (testInstances.hasMoreElements()) {
			Instance instance = (Instance) testInstances.nextElement();
			Instance instance1 = (Instance) test1Instances.nextElement();
			double classification = classifier.classifyInstance(instance);
			instance1.setClassValue(classification);
		}

		CSVSaver predictedCsvSaver = new CSVSaver();
		predictedCsvSaver.setFile(new File("resources/predict.csv"));
		predictedCsvSaver.setInstances(test1DataSet);
		predictedCsvSaver.writeBatch();

		logger.debug("Prediciton saved to resources/predict.csv");
	}
}
