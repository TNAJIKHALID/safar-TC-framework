package impl;

import interfaces.Preprocessing;
import model.Preprocessors;
import safar.modern_standard_arabic.basic.morphology.lemmatizer.interfaces.ILemmatizer;
import safar.modern_standard_arabic.basic.morphology.lemmatizer.model.LemmatizerAnalysis;
import safar.modern_standard_arabic.basic.morphology.lemmatizer.model.WordLemmatizerAnalysis;
import safar.modern_standard_arabic.basic.morphology.stemmer.interfaces.IStemmer;
import safar.modern_standard_arabic.basic.morphology.stemmer.model.WordStemmerAnalysis;
import safar.modern_standard_arabic.util.normalization.interfaces.INormalizer;
import safar.modern_standard_arabic.util.splitting.interfaces.ISentenceSplitter;
import safar.modern_standard_arabic.util.tokenization.interfaces.ITokenizer;

import java.util.Arrays;
import java.util.List;

public class PreprocessingImpl implements Preprocessing {

    @Override
    public String preprocessText(String text, Preprocessors preprocessors) {
        text = preprocessors.isNormalize()? this.normalizeText(text,preprocessors.getNormalizer()) : text;
        boolean splitSentences = preprocessors.isSplitSentences();
        if(splitSentences){

        }else {

        }
        return text;
    }

    public String stemText(String text, IStemmer stemmer){
        String textOut = "";
        List<WordStemmerAnalysis> wordStemmerAnalyses = stemmer.stem(text);
        for (WordStemmerAnalysis wordStemmerAnalysis : wordStemmerAnalyses) {
            textOut += " " + wordStemmerAnalyses.get(0).getListStemmerAnalysis().get(0).getMorpheme();
        }
        return textOut;
    }

    public String lemmatizeText(String text, ILemmatizer lemmatizer){
        String textOut = "";
        List<WordLemmatizerAnalysis> wordLemmatizerAnalyses = lemmatizer.lemmatize(text);
        for (WordLemmatizerAnalysis wordLemmatizerAnalysis : wordLemmatizerAnalyses) {
            for (LemmatizerAnalysis lemmatizerAnalysis : wordLemmatizerAnalysis.getStandardAnalysisList()) {
                textOut += " " + lemmatizerAnalysis.getLemma();
            }
        }
        return textOut;
    }

    public String normalizeText(String text, INormalizer normalizer){
        return  normalizer.normalize(text);
    }

    public String[] splitText(String text, ISentenceSplitter sentenceSplitter){
        return sentenceSplitter.split(text);
    }

    public String tokenizeText(String text, ITokenizer tokenizer){
        String textOut = "";
        String[] tokens = tokenizer.tokenize(text);
        for (int i = 0; i < tokens.length; i++) {
            textOut += " " + tokens[i];
        }
        return textOut;
    }
}
