package safar.machine_learning.text_classification_api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayes;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class TCModel {
    public static List<String> classes = new ArrayList<>();
    Classifier classifier;
    public final Filter filter = new StringToWordVector();

    public TCModel() {
        classifier = new NaiveBayes();
    }

    public TCModel classifier(Classifier classifier) {
        this.classifier = classifier;
        return this;
    }
}
