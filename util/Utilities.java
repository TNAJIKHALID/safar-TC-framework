package util;

import weka.core.Instances;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class Utilities {
    public static Map<String,String[]> getData(String dataSetFolderPath){
        Map<String,String[]> dataSet = new LinkedHashMap<>();
        
        return  dataSet;
    }

    public static void saveData(String dataSetFolderPath,Map<String,String[]> dataSet){

    }

    public static Instances getData(Map<String,String[]> data){

        return null;
    }
}
