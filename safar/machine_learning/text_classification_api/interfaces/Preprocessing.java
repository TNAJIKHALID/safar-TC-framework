package safar.machine_learning.text_classification_api.interfaces;

import safar.machine_learning.text_classification_api.conf.LoggerConf;
import safar.machine_learning.text_classification_api.model.Preprocessors;
import safar.machine_learning.text_classification_api.util.Utilities;
import weka.core.Instances;

import java.util.LinkedHashMap;
import java.util.Map;

public interface Preprocessing {
    /**
     * Methode to implement
     *
     * @param text
     * @param preprocessors
     * @return
     */
    String preprocessText(String text, Preprocessors preprocessors);

    default String preprocessText(String text) {
        return preprocessText(text, new Preprocessors());
    }

    /**
     * Methode to define...
     */
    default Map<String, String[]> preprocessDataSet(Map<String, String[]> dataSet, Preprocessors preprocessors) {
        LoggerConf.logger.info("Start preprocessing...");
        Map<String, String[]> data = new LinkedHashMap<>();
        for (Map.Entry<String, String[]> entry : dataSet.entrySet()) {
            String[] entryValue = entry.getValue();
            LoggerConf.logger.info("Start preprocessing for class " + entry.getKey());
            String[] preprocessedTexts = new String[entryValue.length];
            for (int i = 0; i < preprocessedTexts.length; i++) {
                preprocessedTexts[i] = preprocessText(entryValue[i], preprocessors);
            }
            data.put(entry.getKey(), preprocessedTexts);
        }
        LoggerConf.logger.info("End of preprocessing");
        return data;
    }

    default void preprocess(String dataSetFolderPathIn, String dataSetFolderPathOut, Preprocessors preprocessors) {
        Map<String, String[]> data = Utilities.getData(dataSetFolderPathIn);
        data = preprocessDataSet(data, preprocessors);
        Utilities.saveData(dataSetFolderPathOut, data);
    }

    default void preprocess(String dataSetFolderPathIn, String dataSetFolderPathOut) {
        preprocess(dataSetFolderPathIn, dataSetFolderPathOut, new Preprocessors());
    }

    default Map<String,String[]> preprocessDataSet(String dataSetFolderPathIn, Preprocessors preprocessors){
        Map<String,String[]> data = Utilities.getData(dataSetFolderPathIn);
        data = preprocessDataSet(data,preprocessors);
        return data;
    }

    default Map<String,String[]> preprocessDataSet(String dataSetFolderPathIn){
        return preprocessDataSet(dataSetFolderPathIn,new Preprocessors());
    }

    default Map<String,String[]> preprocessDataSet(Map<String,String[]> dataSet){
        return preprocessDataSet(dataSet, new Preprocessors());
    }

    default Instances preprocess(String dataSetFolderPathIn, Preprocessors preprocessors){
        Map<String,String[]> data = preprocessDataSet(dataSetFolderPathIn,preprocessors);
        return Utilities.getData(data);
    }

    default Instances preprocess(String dataSetFolderPathIn){
        return preprocess(dataSetFolderPathIn, new Preprocessors());
    }

}
