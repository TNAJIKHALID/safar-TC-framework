package safar.machine_learning.text_features_extraction.interfaces;

import safar.machine_learning.text_features_extraction.model.Feature;
import safar.machine_learning.text_features_extraction.model.FeaturesExtractor;

import java.util.Map;

public interface IFeaturesExtraction {
    Map<Feature, String> useFeaturesExtractor(String text);

    FeaturesExtractor buildFeaturesExtractor(String texts[]);
}
