package test;

import safar.modern_standard_arabic.basic.morphology.stemmer.factory.StemmerFactory;
import safar.modern_standard_arabic.basic.morphology.stemmer.interfaces.IStemmer;
import safar.modern_standard_arabic.basic.morphology.stemmer.model.StemmerAnalysis;
import safar.modern_standard_arabic.basic.morphology.stemmer.model.WordStemmerAnalysis;
import safar.modern_standard_arabic.util.stop_words.factory.StopWordFactory;
import safar.modern_standard_arabic.util.stop_words.interfaces.ISWsService;

import java.util.List;

public class PreprocessingTest {
    public static void main(String[] args) {
        IStemmer stemmer = StemmerFactory.getKhojaImplementation();
        String text = "تَداعى طوق الصمت الذي كان مطبقا" +
                " على جرائم اغتصاب الأطفال في المغرب، وإن لم يتهدَّم كلّيا. تحرّر كثير من " +
                "الأمهات والآباء من أغلال ثقافة \"حشومة\" وتصدّعت أركان مبدأ \"ستْر الفضيحة\"،" +
                " وصارت تفاصيل الجرائم الشنيعة التي تطال الأطفال الأبرياء تُسرَد على ألسن" +
                " ذويهم أمام كاميرات وسائل الإعلام وبوجوه مكشوفة، وشجّع فضحُ هذه الجرائم" +
                " القضاءَ على التشدد مع الجناة رغم بروز حالات تساهل بين الفينة والأخرى.";
        List<WordStemmerAnalysis> wordStemmerAnalyses = stemmer.stem(text);
        for (int i = 0; i < wordStemmerAnalyses.size(); i++) {
            WordStemmerAnalysis wordStemmerAnalysis = wordStemmerAnalyses.get(i);
            List<StemmerAnalysis> listStemmerAnalysis = wordStemmerAnalysis.getListStemmerAnalysis();
            System.out.println("********************************");
            for (int i1 = 0; i1 < listStemmerAnalysis.size(); i1++) {
                StemmerAnalysis stemmerAnalysis = listStemmerAnalysis.get(i1);
                String morpheme = stemmerAnalysis.getMorpheme();
                System.out.println(morpheme);
            }
        }
        System.out.println("***************************************************");
        String words[] = text.split(" ");
        text = "";
        ISWsService wsService = StopWordFactory.getSWsImplementation();
        ;
        for (int i = 0; i < words.length; i++) {
            boolean stopWord = wsService.isStopWord(words[i]);
            text = !stopWord ? text + " " + words[i] : text;
            System.out.println(stopWord);
        }
        System.out.println("done...");
    }
}
