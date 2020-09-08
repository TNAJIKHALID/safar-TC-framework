package util;

import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.TextDirectoryLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public abstract class Utilities {
    public static Map<String,String[]> getData(String dataSetFolderPath){
        Map<String,String[]> dataSet = new LinkedHashMap<>();
        File dir = new File(dataSetFolderPath);
        if(dir.isDirectory()) {
            for(File classDirectory : dir.listFiles()) {
                if(classDirectory.isDirectory()) {
                    List<String> texts = new ArrayList<>();
                    for(File file : classDirectory.listFiles()){
                        texts.add(readFile(file));
                    }
                    dataSet.put(dir.getName(), texts.toArray(new String[texts.size()]));
                }
            }
        } else {
            return null;
        }
        return  dataSet;
    }

    public static void saveData(String dataSetFolderPath,Map<String,String[]> dataSet){
        File dir = new File(dataSetFolderPath);
        if(!dir.isDirectory()) {
            dir.mkdir();
        }
        for (Map.Entry<String,String[]> entry: dataSet.entrySet()){
            int k = 0;
            for (String text: entry.getValue()){
                saveFile(dataSetFolderPath+"\\"+entry.getKey()+"\\"+k+".txt",text);
            }
        }
    }

    private static void saveFile(String path, String text) {
        try {
            FileWriter myWriter = new FileWriter(path);
            myWriter.write(text);
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }    }

    private static String readFile(File file) {
        String data = "";
        try {
            File myObj = new File("filename.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                data += "\n" + myReader.nextLine();
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static Instances getData(Map<String,String[]> data){
        Instances instances;
        FastVector textClasses = new FastVector(2);
        for (Map.Entry<String,String[]> entry: data.entrySet()){
            textClasses.addElement(entry.getKey());
        }
        Attribute attributeClass = new Attribute("class", textClasses);
        Attribute attributeText = new Attribute("text",(FastVector) null);
        // Create list of instances with one element
        FastVector fastVector = new FastVector(2);
        fastVector.addElement(attributeClass);
        fastVector.addElement(attributeText);
        instances = new Instances("relation", fastVector, 1);
        instances.setClassIndex(0);
        for (Map.Entry<String,String[]> entry: data.entrySet()){
            for (String text: entry.getValue()){
                instances.add(getInstance(text, entry.getKey()));
            }
        }
        return null;
    }

    public static Instances getInstances(String dataSetFolderPath) {
        Instances instances = null;
        TextDirectoryLoader loader = new TextDirectoryLoader();
        try {
            loader.setDirectory(new File(dataSetFolderPath));
            instances = loader.getDataSet();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return instances;
    }

    public static Instance getInstance(String text,String textClass){
        Instance instance = new Instance(2);
        instance.setValue(1,text);
        instance.setValue(0,textClass);
        return instance;
    }
}
