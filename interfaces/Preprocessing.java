package interfaces;

import model.Preprocessors;
import model.TCModel;
import util.Utilities;
import weka.core.Instances;

import java.util.AbstractMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public interface Preprocessing {
    /**
     * Methode to implement
     * @param text
     * @param preprocessors
     * @return
     */
    String preprocessText(String text,Preprocessors preprocessors);

    default String preprocessText(String text){
     return preprocessText(text, new Preprocessors());
    }

    default void preprocess(String dataSetFolderPathIn,String dataSetFolderPathOut,Preprocessors preprocessors){
        Map<String,String[]> data = Utilities.getData(dataSetFolderPathIn);
        data = preprocessDataSet(data);
        Utilities.saveData(dataSetFolderPathOut,data);
    }

    default void preprocess(String dataSetFolderPathIn,String dataSetFolderPathOut){
        preprocess(dataSetFolderPathIn,dataSetFolderPathOut,new Preprocessors());
    }

    default Map<String,String[]> preprocessDataSet(String dataSetFolderPathIn, Preprocessors preprocessors){
        Map<String,String[]> data = Utilities.getData(dataSetFolderPathIn);
        data = preprocessDataSet(data,preprocessors);
        return data;
    }

    default Map<String,String[]> preprocessDataSet(String dataSetFolderPathIn){
        return preprocessDataSet(dataSetFolderPathIn,new Preprocessors());
    }
    /**
     * Methode to define...
     */
    default Map<String,String[]> preprocessDataSet(Map<String,String[]> dataSet, Preprocessors preprocessors){

        return null;
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
