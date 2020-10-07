package test;

import safar.machine_learning.text_classification_api.conf.LoggerConf;
import safar.machine_learning.text_classification_api.impl.EvaluationImpl;
import safar.machine_learning.text_classification_api.impl.ModelGeneratorImp;
import safar.machine_learning.text_classification_api.interfaces.EvaluationModel;
import safar.machine_learning.text_classification_api.interfaces.ModelGenerator;
import safar.machine_learning.text_classification_api.model.Preprocessors;
import safar.machine_learning.text_classification_api.model.TCModel;

/**
 * Done XX
 */
public class TestClass {
    public static void main(String[] args) throws Exception {
        LoggerConf loggerConf = new LoggerConf();
        Preprocessors preprocessors = new Preprocessors()
                .disableLemmatization()
                .disableRemovingStopWords()
                //.disableStemming()
                ;
        ModelGenerator modelGenerator = new ModelGeneratorImp();
        String dataSetFolderPath = "C:\\Users\\khalid\\Documents\\safar projects\\data set\\text classification\\test_arabic";
        //classifier = new BR();
        TCModel model = modelGenerator.generateModel(dataSetFolderPath, preprocessors);
        EvaluationModel evaluationModel = new EvaluationImpl();
        String results = evaluationModel.runCrossValidation(model, preprocessors, dataSetFolderPath);
        System.out.println(results);
        System.out.println("That's it...");
    }
}
