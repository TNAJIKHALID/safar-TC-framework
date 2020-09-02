package interfaces;

import model.Preprocessors;
import model.TCModel;

public interface ModelGenerator {
    TCModel generateModel(String dataSetFolderPath);

    TCModel generateModel(Preprocessors preprocessors, String dataSetFolderPath);

    void setModel(TCModel model, Preprocessors preprocessors, String dataSetFolderPath);
}
