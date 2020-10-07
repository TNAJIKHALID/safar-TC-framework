package safar.machine_learning.text_classification_api.interfaces;

import safar.machine_learning.text_classification_api.model.Preprocessors;
import safar.machine_learning.text_classification_api.model.TCModel;
import weka.core.Instances;

public interface ModelGenerator {
    default TCModel generateModel(String dataSetFolderPath){
        return generateModel(dataSetFolderPath,new Preprocessors());
    }

    TCModel generateModel(String dataSetFolderPath, boolean preprocess);

    TCModel generateModel(String dataSetFolderPath, Preprocessors preprocessors);

    void buildModel(TCModel model, Preprocessors preprocessors, String dataSetFolderPath);

    void buildModel(TCModel model, String dataSetFolderPath, boolean preprocess);

    TCModel getModelFromInstances(Instances instances, TCModel model);
}
