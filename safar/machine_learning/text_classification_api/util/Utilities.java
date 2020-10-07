package safar.machine_learning.text_classification_api.util;

import safar.machine_learning.text_classification_api.conf.LoggerConf;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

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
        LoggerConf.logger.info("Start Loading data...");
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
        LoggerConf.logger.info("End Loading data...");
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
        LoggerConf.logger.info("Start Saving data in " + dir.getAbsolutePath());
        LoggerConf.logger.warn("Any existing source with the same name will be replaced");
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
        LoggerConf.logger.info("Saving data is completed");
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
        LoggerConf.logger.info("Start Loading instances...");
        Instances instances;
        FastVector textClasses = new FastVector(data.size());
        for (Map.Entry<String, String[]> entry : data.entrySet()) {
            textClasses.addElement(entry.getKey());
        }
        Attribute attributeClass = new Attribute("class", textClasses);
        Attribute attributeText = new Attribute("text", (FastVector) null);
        FastVector fastVector = new FastVector(2);
        fastVector.addElement(attributeText);
        fastVector.addElement(attributeClass);
        instances = new Instances("relation", fastVector, 1);
        instances.setClassIndex(1);
        for (Map.Entry<String,String[]> entry : data.entrySet()) {
            for (String text : entry.getValue()) {
                double[] instanceValue1 = new double[2];
                instanceValue1[0] = instances.attribute(0).addStringValue(text);
                instanceValue1[1] = textClasses.indexOf(entry.getKey());
                instances.add(new Instance(1.0, instanceValue1));
            }
        }
        LoggerConf.logger.info("End Loading instances...");
        return instances;

    }

    /**
     * Done
     *
     * @param dataSetFolderPath
     * @return
     */
    public static Instances getInstances(String dataSetFolderPath) {
        LoggerConf.logger.info("Start Loading instances...");
        Instances instances = null;
        /*TextDirectoryLoader loader = new TextDirectoryLoader();
        try {
            loader.setDirectory(new File(dataSetFolderPath));
            instances = loader.getDataSet();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        instances = getData(getData(dataSetFolderPath));
        instances.setClassIndex(1);
        LoggerConf.logger.info("End Loading instances...");
        return instances;
    }

    /**
     * Donne
     *
     * @param classes
     * @param text
     * @param classIndex
     * @return
     */
    public static Instances getInstancesForSingleText(List<String> classes, String text, int classIndex) {
        FastVector textClasses = new FastVector(classes.size());
        for (String classValue : classes) {
            textClasses.addElement(classValue);
        }
        Attribute attributeClass = new Attribute("class", textClasses);
        Attribute attributeText = new Attribute("text", (FastVector) null);
        FastVector fastVector = new FastVector(2);
        fastVector.addElement(attributeText);
        fastVector.addElement(attributeClass);
        Instances instances = new Instances("relation", fastVector, 1);
        instances.setClassIndex(1);
        double[] instanceValue1 = new double[2];
        instanceValue1[0] = instances.attribute(0).addStringValue(text);
        instanceValue1[1] = (double) textClasses.elementAt(classIndex);
        instances.add(new Instance(1.0, instanceValue1));
        return instances;
    }
}
