package safar.machine_learning.text_features_extraction.model;

import java.util.List;

public class FeaturesExtractor {
    private List<Feature> features;

    public FeaturesExtractor(List<Feature> features) {
        this.features = features;
    }

    public FeaturesExtractor() {

    }

    public List<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(List<Feature> features) {
        this.features = features;
    }
}
