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
    /**
     * Done
     *
     * @param dataSetFolderPath
     * @return
     */
    public static Map<String, String[]> getData(String dataSetFolderPath) {
        Map<String, String[]> dataSet = new LinkedHashMap<>();
        File dir = new File(dataSetFolderPath);
        if (dir.isDirectory()) {
            for (File classDirectory : dir.listFiles()) {
                if (classDirectory.isDirectory()) {
                    List<String> texts = new ArrayList<>();
                    for (File file : classDirectory.listFiles()) {
                        texts.add(readFile(file));
                    }
                    dataSet.put(classDirectory.getName(), texts.toArray(new String[texts.size()]));
                }
            }
        } else {
            return null;
        }
        return dataSet;
    }

    /**
     * Done
     *
     * @param dataSetFolderPath
     * @param dataSet
     */
    public static void saveData(String dataSetFolderPath, Map<String, String[]> dataSet) {
        File dir = new File(dataSetFolderPath);
        if (!dir.isDirectory()) {
            dir.mkdir();
        }
        for (Map.Entry<String, String[]> entry : dataSet.entrySet()) {
            int k = 0;
            File classDir = new File(dataSetFolderPath + "\\" + entry.getKey());
            if (!classDir.isDirectory()) {
                classDir.mkdir();
            }
            for (String text : entry.getValue()) {
                saveFile(dataSetFolderPath + "\\" + entry.getKey() + "\\" + k + ".txt", text);
                k++;
            }
        }
    }

    /**
     * Done
     *
     * @param path
     * @param text
     */
    private static void saveFile(String path, String text) {
        try {
            File file = new File(path);
            file.createNewFile();
            FileWriter myWriter = new FileWriter(path);
            myWriter.write(text);
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Done
     */
    private static String readFile(File file) {
        String data = "";
        try {
            //File myObj = new File("filename.txt");
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                data += "\n" + myReader.nextLine();
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * Done
     *
     * @param data
     * @return
     */
    public static Instances getData(Map<String, String[]> data) {
        Instances instances;
        FastVector textClasses = new FastVector(data.size());
        for (Map.Entry<String, String[]> entry : data.entrySet()) {
            textClasses.addElement(entry.getKey());
        }
        Attribute attributeClass = new Attribute("class", textClasses);
        Attribute attributeText = new Attribute("text", (FastVector) null);
        // Create list of instances with one element
        FastVector fastVector = new FastVector(2);
        fastVector.addElement(attributeText);
        fastVector.addElement(attributeClass);
        instances = new Instances("relation", fastVector, 1);
        instances.setClassIndex(0);
        for (Map.Entry<String,String[]> entry : data.entrySet()) {
            for (String text : entry.getValue()) {
                double[] instanceValue1 = new double[2];
                instanceValue1[0] = instances.attribute(0).addStringValue(text);
                instanceValue1[1] = textClasses.indexOf(entry.getKey());
                instances.add(new Instance(1.0, instanceValue1));
            }
        }
        return instances;
    }

    /**
     * Done
     *
     * @param dataSetFolderPath
     * @return
     */
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

    /**
     * Done
     *
     * @param instances
     * @param text
     * @param textClass
     * @return
     */
    public static Instance getInstance(Instances instances, String text, String textClass) {
        double[] instanceValue1 = new double[2];
        instanceValue1[0] = instances.attribute(0).addStringValue(text);
        instanceValue1[1] = instances.attribute(1).indexOfValue(textClass);
        return new Instance(1.0,instanceValue1);
    }
}
