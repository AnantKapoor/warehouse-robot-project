package main.java.CancellationPrediction;

import java.io.File;

import main.java.JobSelection.Run;

import org.apache.log4j.Logger;

import weka.classifiers.trees.*;
import weka.core.Attribute;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.converters.ArffLoader;
import weka.core.converters.Loader;

/**
 * The Train class is responsible for loading the training data, instantiating a
 * Classifier, then building the classifier instance with the training data. It
 * then serializes the Classifier to disk for other operations to use.
 * 
 * As seen in the README.md file, we have converted the given CSV formatted
 * training and teat data into ARFF formatted files. This allows us to specify
 * the types of each column (nominal, numeric, string).
 * 
 * @author jbirchfield
 * 
 */
public class Train {

	private static final Logger logger = Logger.getLogger(Run.class);

	public static void main() throws Exception {

		// loading data from .arff file
		ArffLoader trainLoader = new ArffLoader();
		trainLoader.setSource(new File("resources/trainingData.arff"));
		trainLoader.setRetrieval(Loader.BATCH);
		Instances trainDataSet = trainLoader.getDataSet();

		// tells the data set which attribute we want to classify (first column
		// - cancellation)
		Attribute trainAttribute = trainDataSet.attribute(0);
		trainDataSet.setClass(trainAttribute);

		trainDataSet.deleteStringAttributes();

		// creates a new classifier
		RandomForest classifier = new RandomForest();
		classifier.setNumTrees(100);
		classifier.setDebug(true);

		// trains classifier
		classifier.buildClassifier(trainDataSet);

		
		logger.debug("Started saving training model");

		//serialises classifier to .model file
		SerializationHelper.write("resources/jobsTraining.model", classifier);

		logger.debug("Saved trained model to jobsTraining.model");
	}
}
