package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import safar.modern_standard_arabic.basic.morphology.lemmatizer.factory.LemmatizerFactory;
import safar.modern_standard_arabic.basic.morphology.lemmatizer.interfaces.ILemmatizer;
import safar.modern_standard_arabic.basic.morphology.stemmer.factory.StemmerFactory;
import safar.modern_standard_arabic.basic.morphology.stemmer.interfaces.IStemmer;
import safar.modern_standard_arabic.util.normalization.factory.NormalizerFactory;
import safar.modern_standard_arabic.util.normalization.interfaces.INormalizer;
import safar.modern_standard_arabic.util.splitting.factory.SentenceSplitterFactory;
import safar.modern_standard_arabic.util.splitting.interfaces.ISentenceSplitter;
import safar.modern_standard_arabic.util.tokenization.factory.TokenizerFactory;
import safar.modern_standard_arabic.util.tokenization.interfaces.ITokenizer;
@Data @AllArgsConstructor
public class Preprocessors {
    private IStemmer stemmer;
    private ILemmatizer lemmatizer;
    private ISentenceSplitter sentenceSplitter;
    private INormalizer normalizer;
    private ITokenizer tokenizer;
    private boolean stem = true;
    private boolean lemmatize = true;
    private boolean normalize = true;
    private boolean splitSentences = true;
    public Preprocessors(){
        stemmer = StemmerFactory.getSAFARImplementation();
        lemmatizer = LemmatizerFactory.getFARASAImplementation();
        sentenceSplitter = SentenceSplitterFactory.getSAFARSentenceSplitterImplementation();
        normalizer = NormalizerFactory.getSAFARNormalizerImplementation();
        tokenizer = TokenizerFactory.getSAFARTokenizerImplementation();
    }

    Preprocessors stemmer(IStemmer stemmer){
        this.stemmer = stemmer;
        return this;
    }
    Preprocessors lemmatizer(ILemmatizer lemmatizer){
        this.lemmatizer = lemmatizer;
        return this;
    }
    Preprocessors normalizer(INormalizer normalizer){
        this.normalizer = normalizer;
        return this;
    }
    Preprocessors tokenizer(ITokenizer tokenizer){
        this.tokenizer = tokenizer;
        return this;
    }
    Preprocessors sentenceSplitter(ISentenceSplitter sentenceSplitter){
        this.sentenceSplitter = sentenceSplitter;
        return this;
    }
    Preprocessors disableStemming(){this.stem = false;return this;}
    Preprocessors disableLemmatization(){this.lemmatize = false;return this;}
    Preprocessors disableNormalization(){this.normalize = false;return this;}
    Preprocessors disableSentenceSpliting(){this.splitSentences = false;return this;}

}
