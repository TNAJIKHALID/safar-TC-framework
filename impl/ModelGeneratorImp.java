package impl;

import interfaces.ModelGenerator;
import interfaces.Preprocessing;
import lombok.AllArgsConstructor;
import lombok.Data;
import model.Preprocessors;
import model.TCModel;
import util.Utilities;
import weka.classifiers.Classifier;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;

@Data @AllArgsConstructor
public class ModelGeneratorImp implements ModelGenerator {
    private Preprocessing preprocessing;
    public ModelGeneratorImp(){
        preprocessing = new PreprocessingImpl();
    }

    /*
    Methode to define
     */
    @Override
    public TCModel getModelFromInstances(Instances instances, TCModel model) {
        try {
            StringToWordVector filter = (StringToWordVector) model.getFilter();
            Classifier classifier = model.getClassifier();
            filter.setInputFormat(instances);
            Instances dataFiltered = Filter.useFilter(instances, filter);
            classifier.buildClassifier(dataFiltered);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private TCModel getModelWithoutPreprocessing(TCModel model, String dataSetFolderPath) {
        Instances instances = Utilities.getData(Utilities.getData(dataSetFolderPath));
        return getModelFromInstances(instances, new TCModel());
    }

    @Override
    public TCModel generateModel(String dataSetFolderPath, boolean preprocess) {
        if (preprocess) {
            return generateModel(dataSetFolderPath, new Preprocessors());
        } else  return getModelWithoutPreprocessing(new TCModel(), dataSetFolderPath);
    }


    @Override
    public TCModel generateModel(String dataSetFolderPath, Preprocessors preprocessors) {
        Instances instances = preprocessing.preprocess(dataSetFolderPath, preprocessors);
        return getModelFromInstances(instances,new TCModel());
    }

    @Override
    public void setModel(TCModel model, Preprocessors preprocessors, String dataSetFolderPath) {
        Instances instances = preprocessing.preprocess(dataSetFolderPath,preprocessors);
        model = getModelFromInstances(instances,model);
    }

    @Override
    public void setModel(TCModel model, String dataSetFolderPath, boolean preprocess) {
        if (preprocess) {
            setModel(model,new Preprocessors(),dataSetFolderPath);
        } else{
            model = getModelWithoutPreprocessing(model,dataSetFolderPath);
        }
    }
}
