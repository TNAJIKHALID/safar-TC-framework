package safar.machine_learning.text_classification_api.impl;

import safar.machine_learning.text_classification_api.conf.LoggerConf;
import safar.machine_learning.text_classification_api.interfaces.Preprocessing;
import safar.machine_learning.text_classification_api.model.Preprocessors;
import safar.modern_standard_arabic.basic.morphology.lemmatizer.interfaces.ILemmatizer;
import safar.modern_standard_arabic.basic.morphology.lemmatizer.model.LemmatizerAnalysis;
import safar.modern_standard_arabic.basic.morphology.lemmatizer.model.WordLemmatizerAnalysis;
import safar.modern_standard_arabic.basic.morphology.stemmer.interfaces.IStemmer;
import safar.modern_standard_arabic.basic.morphology.stemmer.model.WordStemmerAnalysis;
import safar.modern_standard_arabic.util.normalization.interfaces.INormalizer;
import safar.modern_standard_arabic.util.splitting.interfaces.ISentenceSplitter;
import safar.modern_standard_arabic.util.stop_words.interfaces.ISWsService;
import safar.modern_standard_arabic.util.tokenization.interfaces.ITokenizer;

import java.util.ArrayList;
import java.util.List;

public class PreprocessingImpl implements Preprocessing {
    public static List<String> stopWords = new ArrayList<>();

    public PreprocessingImpl() {
        LoggerConf.logger.warn("Removing Strop words may slow down the speed");
        LoggerConf.logger.warn("Lemmtization Strop words may slow down the speed");
    }

    @Override
    public String preprocessText(String text, Preprocessors preprocessors) {
        text = preprocessors.isNormalize() ? this.normalizeText(text, preprocessors.getNormalizer()) : text;
        text = preprocessors.isStem() ? this.stemText(text, preprocessors.getStemmer()) : text;
        text = preprocessors.isTokenize() ? this.tokenizeText(text, preprocessors.getTokenizer()) : text;
        text = preprocessors.isRemoveStopWords() ? this.removeStopWords(text, preprocessors.getWsService()) : text;
        return text;
    }

    public String removeStopWords(String text, ISWsService wsService) {
        String words[] = text.split(" ");
        text = "";
        for (int i = 0; i < words.length; i++) {
            boolean stopWord;
            if (!stopWords.contains(words[i])) {
                stopWord = wsService.isStopWord(words[i]);
                if (!stopWord) {
                    text = text + " " + words[i];
                    stopWords.add(words[i]);
                }
            }
        }
        return text;
    }

    public String stemText(String text, IStemmer stemmer) {
        String textOut = "";
        List<WordStemmerAnalysis> wordStemmerAnalyses = stemmer.stem(text);
        //System.out.println(wordStemmerAnalyses.toString());
        for (WordStemmerAnalysis wordStemmerAnalysis : wordStemmerAnalyses) {
            textOut += " " + wordStemmerAnalysis.getListStemmerAnalysis().get(0).getMorpheme();
        }
        //System.out.println("**********************************************");
        //System.out.println(textOut);
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
