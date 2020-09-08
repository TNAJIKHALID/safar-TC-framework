package interfaces;

import model.Preprocessors;
import model.TCModel;

public interface EvaluationModel {
    String evaluateModel(TCModel model, Preprocessors preprocessors, String dataSetFolderPath);

    String evaluateModel(TCModel model, String dataSetFolderPath, boolean preprocess);

    default String evaluateModel(TCModel model, String dataSetFolderPath){
       return evaluateModel(model,new Preprocessors(),dataSetFolderPath);
    }

    String runCrossValidation(TCModel model, Preprocessors preprocessors, String dataSetFolderPath);

    String runCrossValidation(TCModel model, String dataSetFolderPath, boolean preprocess);

    default String runCrossValidation(TCModel model, String dataSetFolderPath){
        return runCrossValidation(model,new Preprocessors(),dataSetFolderPath);
    }

    String runTrainTestSplit(TCModel model, Preprocessors preprocessors, String dataSetFolderPath);

    String runTrainTestSplit(TCModel model, String dataSetFolderPath, boolean preprocess);

    default String runTrainTestSplit(TCModel model, String dataSetFolderPath){
        return runTrainTestSplit(model,new Preprocessors(),dataSetFolderPath);
    }
}
