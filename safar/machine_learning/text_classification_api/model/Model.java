package safar.machine_learning.text_classification_api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Model {
    private Preprocessors preprocessors;
    private TCModel tcModel;
}


