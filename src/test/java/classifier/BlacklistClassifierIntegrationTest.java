package classifier;

import cache.BlacklistCache;
import cache.BlacklistCacheImpl;
import org.junit.Test;
import utils.FileUtils;

import java.io.FileNotFoundException;
import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BlacklistClassifierIntegrationTest {

    @Test
    public void isBlacklisted_ReturnsTrue_IfNamesShouldMatch() throws FileNotFoundException {
        // Given
        String blacklistFileName = "names.csv";
        String noiseWordsFileName = "noise_words.csv";

        Set<String> noiseWords = FileUtils.getCsvLinesAsUniqueNames(noiseWordsFileName);
        Set<String> blacklistedNames = FileUtils.getCsvLinesAsUniqueNames(blacklistFileName);

        BlacklistCache blacklistCache = new BlacklistCacheImpl(blacklistedNames, noiseWords);
        BlacklistClassifier classifier = new BlacklistClassifier(blacklistCache);

        // Then
        assertTrue(classifier.isBlacklisted("Franklin The Delano Roosevelt"));
        assertTrue(classifier.isBlacklisted("Franklin Delano Roosevelt"));
        assertTrue(classifier.isBlacklisted("Franklin The Delanoo Rooseveltt"));
        assertTrue(classifier.isBlacklisted("Franklinn The Delanon Rooseveltn"));
        assertTrue(classifier.isBlacklisted("franklin the delanoo rooosevelt"));
        assertTrue(classifier.isBlacklisted("rooosevelt franklin  delanoo the  "));
        assertTrue(classifier.isBlacklisted("Roosevelt"));
    }

    @Test
    public void isBlacklisted_ReturnsFalse_IfNamesShouldNotMatch() throws FileNotFoundException {
        // Given
        String blacklistFileName = "names.csv";
        String noiseWordsFileName = "noise_words.csv";

        Set<String> noiseWords = FileUtils.getCsvLinesAsUniqueNames(noiseWordsFileName);
        Set<String> blacklistedNames = FileUtils.getCsvLinesAsUniqueNames(blacklistFileName);

        BlacklistCache blacklistCache = new BlacklistCacheImpl(blacklistedNames, noiseWords);
        BlacklistClassifier classifier = new BlacklistClassifier(blacklistCache);

        // Then
        assertFalse(classifier.isBlacklisted("Osama Bin Laden"));
        assertFalse(classifier.isBlacklisted("FranklinMan The Delanonino Roosevelterino"));
    }

}