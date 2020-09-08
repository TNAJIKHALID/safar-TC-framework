package interfaces;

import model.Preprocessors;
import model.TCModel;
import weka.core.Instances;

public interface ModelGenerator {
    default TCModel generateModel(String dataSetFolderPath){
        return generateModel(dataSetFolderPath,new Preprocessors());
    }

    TCModel generateModel(String dataSetFolderPath, boolean preprocess);

    TCModel generateModel(String dataSetFolderPath, Preprocessors preprocessors);

    void setModel(TCModel model, Preprocessors preprocessors, String dataSetFolderPath);

    void setModel(TCModel model, String dataSetFolderPath, boolean preprocess);

    TCModel getModelFromInstances(Instances instances, TCModel model);
}
