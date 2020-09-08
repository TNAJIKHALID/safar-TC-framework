package impl;

import interfaces.Preprocessing;
import interfaces.EvaluationModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import model.Preprocessors;
import model.TCModel;
import util.Utilities;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;

import java.util.Random;

@Data @AllArgsConstructor
public class EvaluationImpl implements EvaluationModel {
    private Preprocessing preprocessing;
    public EvaluationImpl(){
        preprocessing = new PreprocessingImpl();
    }

    private String runTrainTestSplitOnInstances(TCModel model, Instances instances) {
        String trainResults = null, testResults = null;
        try {
            instances.setClassIndex(0);
            instances.randomize(new java.util.Random());
            StringToWordVector filter = (StringToWordVector) model.getFilter();
            Classifier classifier = model.getClassifier();
            filter.setInputFormat(instances);
            instances = Filter.useFilter(instances, filter);
            Instances trainData = instances.trainCV(2, 0);
            Instances testData = instances.testCV(2, 0);
            classifier.buildClassifier(trainData);
            Evaluation eval = new Evaluation(trainData);
            eval.evaluateModel(classifier, trainData);
            trainResults = "1-NN accuracy on training data:\n" + eval.pctCorrect() / 100;
            eval.evaluateModel(classifier, testData);
            testResults = "1-NN accuracy on separate test data:\n" + eval.pctCorrect() / 100;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return trainResults + "\n" + testResults;
    }
    private String evaluateInstances(TCModel model, Instances instances) {
        String evalResults = null;
        try {
            instances.setClassIndex(0);
            instances.randomize(new Random());
            StringToWordVector filter = (StringToWordVector) model.getFilter();
            Classifier classifier = model.getClassifier();
            filter.setInputFormat(instances);
            instances = Filter.useFilter(instances, filter);
            Evaluation eval = new Evaluation(instances);
            eval.evaluateModel(classifier, instances);
            evalResults = eval.toSummaryString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String runCrossValidationOnInstances(TCModel model, Instances instances) {
        Evaluation eval = null;
        StringToWordVector filter = (StringToWordVector) model.getFilter();
        try {
            filter.setInputFormat(instances);
            instances = Filter.useFilter(instances,filter);
            eval = new Evaluation(instances);
            eval.crossValidateModel(model.getClassifier(),instances,5,new Random());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return eval.toSummaryString();
    }

    @Override
    public String evaluateModel(TCModel model, Preprocessors preprocessors, String dataSetFolderPath) {
        Instances instances = preprocessing.preprocess(dataSetFolderPath, preprocessors);
        return evaluateInstances(model,instances);
    }

    @Override
    public String evaluateModel(TCModel model, String dataSetFolderPath, boolean preprocess) {
        if(preprocess) return evaluateModel(model, new Preprocessors(),dataSetFolderPath);
        else return evaluateInstances(model, Utilities.getInstances(dataSetFolderPath));
    }

    @Override
    public String runCrossValidation(TCModel model, Preprocessors preprocessors, String dataSetFolderPath) {
        Instances instances = preprocessing.preprocess(dataSetFolderPath, preprocessors);
        return runCrossValidationOnInstances(model,instances);
    }

    @Override
    public String runCrossValidation(TCModel model, String dataSetFolderPath, boolean preprocess) {
        if(preprocess) return runCrossValidation(model, new Preprocessors(),dataSetFolderPath);
        else return runCrossValidationOnInstances(model, Utilities.getInstances(dataSetFolderPath));
    }

    @Override
    public String runTrainTestSplit(TCModel model, Preprocessors preprocessors, String dataSetFolderPath) {
        Instances instances = preprocessing.preprocess(dataSetFolderPath, preprocessors);
        return runCrossValidationOnInstances(model,instances);
    }

    @Override
    public String runTrainTestSplit(TCModel model, String dataSetFolderPath, boolean preprocess) {
        if(preprocess) return runTrainTestSplit(model, new Preprocessors(),dataSetFolderPath);
        else return runTrainTestSplitOnInstances(model, Utilities.getInstances(dataSetFolderPath));
    }
}
