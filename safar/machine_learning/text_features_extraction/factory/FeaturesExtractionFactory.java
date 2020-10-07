package safar.machine_learning.text_features_extraction.factory;

import safar.machine_learning.text_features_extraction.impl.WekaStringToWordVectorImpl;
import safar.machine_learning.text_features_extraction.interfaces.IFeaturesExtraction;

public class FeaturesExtractionFactory {
    private IFeaturesExtraction wekaStringToWordVectorImpl;

    public IFeaturesExtraction getWekaStringToWordVectorImpl() {

        if (wekaStringToWordVectorImpl == null) {
            wekaStringToWordVectorImpl = new WekaStringToWordVectorImpl();
        }
        return wekaStringToWordVectorImpl;
    }

}
