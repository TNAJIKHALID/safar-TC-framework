package safar.machine_learning.text_classification_api.impl;

import safar.machine_learning.text_classification_api.interfaces.Classification;
import safar.machine_learning.text_classification_api.interfaces.Preprocessing;
import safar.machine_learning.text_classification_api.model.Model;
import safar.machine_learning.text_classification_api.util.Utilities;
import weka.core.Instances;

public class ClassificationImpl implements Classification {

    @Override
    public String classify(String text, Model model, Preprocessing preprocessingImpl) {
        String preprocessedText = preprocessingImpl.preprocessText(text, model.getPreprocessors());
        Instances instances = Utilities.getInstancesForSingleText(model.getTcModel().classes, text, 0);
        return "";
    }
}
