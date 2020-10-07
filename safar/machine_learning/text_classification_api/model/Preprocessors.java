package safar.machine_learning.text_classification_api.model;

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
import safar.modern_standard_arabic.util.stop_words.factory.StopWordFactory;
import safar.modern_standard_arabic.util.stop_words.interfaces.ISWsService;
import safar.modern_standard_arabic.util.tokenization.factory.TokenizerFactory;
import safar.modern_standard_arabic.util.tokenization.interfaces.ITokenizer;

/**
 * Done
 */
@Data
@AllArgsConstructor
public class Preprocessors {
    private IStemmer stemmer;
    private ILemmatizer lemmatizer;
    private ISentenceSplitter sentenceSplitter;
    private INormalizer normalizer;
    private ITokenizer tokenizer;
    private ISWsService wsService;
    private boolean stem = true;
    private boolean lemmatize = true;
    private boolean normalize = true;
    private boolean splitSentences = true;
    private boolean removeStopWords = true;
    private boolean tokenize = true;

    public Preprocessors() {
        stemmer = StemmerFactory.getKhojaImplementation();
        lemmatizer = LemmatizerFactory.getFARASAImplementation();
        sentenceSplitter = SentenceSplitterFactory.getSAFARSentenceSplitterImplementation();
        normalizer = NormalizerFactory.getSAFARNormalizerImplementation();
        tokenizer = TokenizerFactory.getSAFARTokenizerImplementation();
        wsService = StopWordFactory.getSWsImplementation();
    }

    public Preprocessors stemmer(IStemmer stemmer) {
        this.stemmer = stemmer;
        return this;
    }

    public Preprocessors lemmatizer(ILemmatizer lemmatizer) {
        this.lemmatizer = lemmatizer;
        return this;
    }

    public Preprocessors normalizer(INormalizer normalizer) {
        this.normalizer = normalizer;
        return this;
    }

    public Preprocessors swService(ISWsService wsService) {
        this.wsService = wsService;
        return this;
    }

    public Preprocessors tokenizer(ITokenizer tokenizer) {
        this.tokenizer = tokenizer;
        return this;
    }

    public Preprocessors sentenceSplitter(ISentenceSplitter sentenceSplitter) {
        this.sentenceSplitter = sentenceSplitter;
        return this;
    }

    public Preprocessors disableStemming() {
        this.stem = false;
        return this;
    }

    public Preprocessors disableLemmatization() {
        this.lemmatize = false;
        return this;
    }

    public Preprocessors disableNormalization() {
        this.normalize = false;
        return this;
    }

    public Preprocessors disableSentenceSplitting() {
        this.splitSentences = false;
        return this;
    }

    public Preprocessors disableRemovingStopWords() {
        this.removeStopWords = false;
        return this;
    }

    public Preprocessors disableTokenization() {
        this.tokenize = false;
        return this;
    }

}
