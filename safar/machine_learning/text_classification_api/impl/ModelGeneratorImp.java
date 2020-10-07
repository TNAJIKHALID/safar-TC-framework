package safar.machine_learning.text_classification_api.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import safar.machine_learning.text_classification_api.conf.LoggerConf;
import safar.machine_learning.text_classification_api.interfaces.ModelGenerator;
import safar.machine_learning.text_classification_api.interfaces.Preprocessing;
import safar.machine_learning.text_classification_api.model.Preprocessors;
import safar.machine_learning.text_classification_api.model.TCModel;
import safar.machine_learning.text_classification_api.util.Utilities;
import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;

import java.util.Enumeration;
import java.util.Random;


@Data
@AllArgsConstructor
public class ModelGeneratorImp implements ModelGenerator {
    private Preprocessing preprocessing;

    public ModelGeneratorImp() {
        preprocessing = new PreprocessingImpl();
    }

    /*
    Methode to define
     */
    @Override
    public TCModel getModelFromInstances(Instances instances, TCModel model) {
        LoggerConf.logger.info("Start generating model...");
        Attribute attribute = instances.attribute(1);
        System.out.println("*****************************************");
        System.out.println("Extract Classes names....");
        Enumeration enumeration = attribute.enumerateValues();
        while (enumeration.hasMoreElements()) {
            String className = (String) enumeration.nextElement();
            model.classes.add(className);
            System.out.println(className);
        }
        try {
            instances.setClassIndex(1);
            instances.randomize(new Random());
            StringToWordVector filter = (StringToWordVector) model.getFilter();
            Classifier classifier = model.getClassifier();
            filter.setInputFormat(instances);
            Instances dataFiltered = Filter.useFilter(instances, filter);
            classifier.buildClassifier(dataFiltered);
        } catch (Exception e) {
            e.printStackTrace();
        }
        LoggerConf.logger.info("Start generating model...");
        return model;
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
    public void buildModel(TCModel model, Preprocessors preprocessors, String dataSetFolderPath) {
        Instances instances = preprocessing.preprocess(dataSetFolderPath, preprocessors);
        model = getModelFromInstances(instances, model);
    }

    @Override
    public void buildModel(TCModel model, String dataSetFolderPath, boolean preprocess) {
        if (preprocess) {
            buildModel(model, new Preprocessors(), dataSetFolderPath);
        } else {
            model = getModelWithoutPreprocessing(model, dataSetFolderPath);
        }
    }
}
