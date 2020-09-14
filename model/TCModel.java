package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayesMultinomial;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;
@Data @AllArgsConstructor
public class TCModel {
   Classifier classifier;
   public final Filter filter = new StringToWordVector();

   public TCModel(){
       classifier = new NaiveBayesMultinomial();
   }
   TCModel classifier(Classifier classifier){
       this.classifier = classifier;
       return this;
   }
}
