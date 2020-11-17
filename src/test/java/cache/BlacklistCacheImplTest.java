package cache;


import org.junit.Test;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BlacklistCacheImplTest {

    @Test
    public void getRelatedNames_ReturnsSet_IfInputSetHasExactSameName() {
        // Given
        Set<String> blacklistedNames = new HashSet<>();
        blacklistedNames.add("Nils Emil Lille");
        blacklistedNames.add("Aurelius Augustus");
        blacklistedNames.add("Atticus Atlas");
        blacklistedNames.add("Aurelius Atlas");
        Set<String> ignoredWords = Collections.emptySet();
        BlacklistCache blacklistCache = new BlacklistCacheImpl(blacklistedNames, ignoredWords);

        // When
        Set<String> relatedNames = blacklistCache.getRelatedNames("Nils");

        // Then
        assertTrue(relatedNames.contains("nils"));
        assertTrue(relatedNames.contains("emil"));
        assertTrue(relatedNames.contains("lille"));
        assertEquals(3, relatedNames.size());
    }

    @Test
    public void getRelatedNames_ReturnsSet_IfCacheHasSimilarName() {
        // Given
        Set<String> blacklistedNames = new HashSet<>();
        blacklistedNames.add("Nils Emil Lille");
        blacklistedNames.add("Aurelius Augustus");
        blacklistedNames.add("Atticus Atlas");
        blacklistedNames.add("Aurelius Atlas");
        Set<String> ignoredWords = Collections.emptySet();
        BlacklistCache blacklistCache = new BlacklistCacheImpl(blacklistedNames, ignoredWords);

        // When
        Set<String> relatedNames = blacklistCache.getRelatedNames("Matlas");

        // Then
        assertTrue(relatedNames.contains("atticus"));
        assertTrue(relatedNames.contains("aurelius"));
        assertTrue(relatedNames.contains("atlas"));
        assertEquals(3, relatedNames.size());
    }

    @Test
    public void getRelatedNames_ReturnsSet_IfTwoNamesShouldMatchButTwoAreNoiseWord() {
        // Given
        Set<String> blacklistedNames = new HashSet<>();
        blacklistedNames.add("Nils and the Emil");
        blacklistedNames.add("Aurelius Augustus");
        blacklistedNames.add("Atticus Atlas");
        blacklistedNames.add("Aurelius Atlas");
        Set<String> ignoredWords = new HashSet<>();
        ignoredWords.add("and");
        ignoredWords.add("the");

        BlacklistCache blacklistCache = new BlacklistCacheImpl(blacklistedNames, ignoredWords);

        // When
        Set<String> relatedNames = blacklistCache.getRelatedNames("Nils");

        // Then
        assertTrue(relatedNames.contains("nils"));
        assertTrue(relatedNames.contains("emil"));
        assertFalse(relatedNames.contains("and"));
        assertFalse(relatedNames.contains("the"));
        assertEquals(2, relatedNames.size());
    }

    @Test
    public void getRelatedNames_ReturnsEmptySet_IfNoSimilarNameInCache() {
        // Given
        Set<String> blacklistedNames = new HashSet<>();
        blacklistedNames.add("Nils Emil Lille");
        blacklistedNames.add("Aurelius Augustus");
        blacklistedNames.add("Atticus Atlas");
        blacklistedNames.add("Aurelius Atlas");
        Set<String> ignoredWords = Collections.emptySet();
        BlacklistCache blacklistCache = new BlacklistCacheImpl(blacklistedNames, ignoredWords);

        // When
        Set<String> relatedNames = blacklistCache.getRelatedNames("Antonius");

        // Then
        assertTrue(relatedNames.isEmpty());
    }

}