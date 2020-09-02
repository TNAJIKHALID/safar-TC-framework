package interfaces;

import model.Preprocessors;
import model.TCModel;

public interface Evaluation {
    void evaluateModel(TCModel model, Preprocessors preprocessors, String dataSetFolderPath);

    void runCrossValidation(TCModel model, Preprocessors preprocessors, String dataSetFolderPath);
}
