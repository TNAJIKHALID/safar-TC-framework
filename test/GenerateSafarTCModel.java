package test;

import safar.machine_learning.text_classification_api.conf.LoggerConf;
import safar.machine_learning.text_classification_api.impl.EvaluationImpl;
import safar.machine_learning.text_classification_api.impl.ModelGeneratorImp;
import safar.machine_learning.text_classification_api.impl.PreprocessingImpl;
import safar.machine_learning.text_classification_api.interfaces.EvaluationModel;
import safar.machine_learning.text_classification_api.interfaces.ModelGenerator;
import safar.machine_learning.text_classification_api.interfaces.Preprocessing;
import safar.machine_learning.text_classification_api.model.Preprocessors;
import safar.machine_learning.text_classification_api.model.TCModel;
import weka.classifiers.bayes.NaiveBayes;

/**
 * Ready !!
 */
public class GenerateSafarTCModel {
    public static void main(String[] args) throws Exception {
        String dataSetPah = "C:\\Users\\khalid\\Documents\\safar projects\\data set\\text classification\\test_arabic",
                preprocessedDataSetPath = "C:\\Users\\khalid\\Documents\\safar projects\\data set\\text classification\\preprocessed_text_arabic";
        LoggerConf loggerConf = new LoggerConf();

        /*Preprocessing dataSet*/
        Preprocessors preprocessors = new Preprocessors()
                .disableLemmatization()
                .disableRemovingStopWords();
        Preprocessing preprocessing = new PreprocessingImpl();
        preprocessing.preprocess(dataSetPah, preprocessedDataSetPath, preprocessors);

        /*Model Generation*/
        TCModel textClassificationModel = new TCModel()
                .classifier(new NaiveBayes());
        ModelGenerator modelGenerator = new ModelGeneratorImp();
        modelGenerator.buildModel(textClassificationModel,
                preprocessedDataSetPath, false);

        /*Testing Model*/
        EvaluationModel evaluationModel = new EvaluationImpl();
        String summaryString = evaluationModel.runCrossValidation(textClassificationModel,
                preprocessedDataSetPath, false);
        System.out.println(summaryString);
        System.out.println("that's it...");
        /*Saving Results*/

    }
}
