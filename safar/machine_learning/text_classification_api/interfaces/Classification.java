package safar.machine_learning.text_classification_api.interfaces;

import safar.machine_learning.text_classification_api.model.Model;


public interface Classification {
    String classify(String text, Model model, Preprocessing preprocessingImplementation);
}
