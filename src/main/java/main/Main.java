package main;

import cache.BlacklistCache;
import cache.BlacklistCacheImpl;
import classifier.BlacklistClassifier;
import utils.FileUtils;

import java.io.FileNotFoundException;
import java.text.MessageFormat;
import java.util.Set;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        String blacklistFileName = "names.csv";
        String noiseWordsFileName = "noise_words.csv";

        Set<String> noiseWords = FileUtils.getCsvLinesAsUniqueNames(noiseWordsFileName);
        Set<String> blacklistedNames = FileUtils.getCsvLinesAsUniqueNames(blacklistFileName);

        BlacklistCache blacklistCache = new BlacklistCacheImpl(blacklistedNames, noiseWords);
        BlacklistClassifier classifier = new BlacklistClassifier(blacklistCache);

        runClassification(classifier, "Osama bin");                 // True
        runClassification(classifier, "Osama Bin Laden");           // True
        runClassification(classifier, "Bin Laden, Osama");          // True
        runClassification(classifier, "Laden Osama Bin");           // True
        runClassification(classifier, "to the osama bin laden");    // True
        runClassification(classifier, "osama and bin laden");       // True
        runClassification(classifier, "osama and nils emil");       // False
        runClassification(classifier, "Vladimir");                  // True
    }


    private static void runClassification(BlacklistClassifier classifier, String name) {
        String result = classifier.isBlacklisted(name) ? "blacklisted" : "not blacklisted";
        String messageTemplate = "The name `{0}` has been classified as {1}";
        System.out.println(MessageFormat.format(messageTemplate, name, result));
    }
}
