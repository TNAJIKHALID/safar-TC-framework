package test;

import meka.classifiers.MultiXClassifier;
import meka.classifiers.multilabel.BR;
import weka.core.Instances;

public class MekaTest {
    public static void main(String[] args) throws Exception {

        // TCModel model = new TCModel().classifier(new BR());
        Instances train = null;
        MultiXClassifier classifier = new BR();
        //classifier.buildClassifier(train);
    }
}
