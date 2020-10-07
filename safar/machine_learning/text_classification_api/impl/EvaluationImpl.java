package safar.machine_learning.text_classification_api.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import safar.machine_learning.text_classification_api.conf.LoggerConf;
import safar.machine_learning.text_classification_api.interfaces.EvaluationModel;
import safar.machine_learning.text_classification_api.interfaces.Preprocessing;
import safar.machine_learning.text_classification_api.model.Preprocessors;
import safar.machine_learning.text_classification_api.model.TCModel;
import safar.machine_learning.text_classification_api.util.Utilities;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
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
        LoggerConf.logger.info("Start train test split on instances...");
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
        LoggerConf.logger.info("Start evaluating instances...");
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
        return evalResults;
    }
    private String runCrossValidationOnInstances(TCModel model, Instances instances) {
        Evaluation eval = null;
        LoggerConf.logger.info("Start cross-validation on instances...");
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
        //System.out.println(instances);
        return runCrossValidationOnInstances(model,instances);
    }

    @Override
    public String runCrossValidation(TCModel model, String dataSetFolderPath, boolean preprocess) {
        if (preprocess) return runCrossValidation(model, new Preprocessors(), dataSetFolderPath);
        else {
            Instances instances = Utilities.getInstances(dataSetFolderPath);
            //System.out.println(instances);
            return runCrossValidationOnInstances(model, instances);
        }
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
