package test;

import impl.ModelGeneratorImp;
import interfaces.ModelGenerator;
import model.TCModel;
import util.Utilities;
import weka.core.Instances;

import java.util.Map;

public class TestClass {
    public static void main(String[] args) {
        //dataTest();
        ModelGenerator modelGenerator = new ModelGeneratorImp();
        TCModel model = modelGenerator.generateModel("");
    }

    private static void dataTest() {
        //System.out.println("Hello word...");
        String dataSetFolderPath = "C:\\Users\\khalid\\Documents\\safar projects\\data set\\text classification\\test";
        String dataSetFolderPathOut = "C:\\Users\\khalid\\Documents\\safar projects\\data set\\text classification\\out";
        Map<String, String[]> data = Utilities.getData(dataSetFolderPath);
        for (Map.Entry<String, String[]> entry : data.entrySet()) {
            System.out.println(entry.getKey());
        }
        //Utilities.saveData(dataSetFolderPathOut,data);
        Instances instances = Utilities.getInstances(dataSetFolderPath);
        System.out.println(instances.toString());
        System.out.println("************************************");
        instances = Utilities.getData(data);
        System.out.println(instances.toString());
    }
}
