package classifier;

import cache.BlacklistCache;
import cache.BlacklistCacheTestImpl;
import org.junit.Test;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BlacklistClassifierTest {

    private Set<String> getTestData() {
        Set<String> set = new HashSet<>();
        set.add("nils");
        set.add("emil");
        set.add("lille");
        return set;
    }

    @Test
    public void isBlacklisted_ReturnsTrue_IfNameHasMultipleDirectMatches() {
        // Given
        Set<String> set = getTestData();
        BlacklistCache cache = new BlacklistCacheTestImpl(set);
        BlacklistClassifier blacklistClassifier = new BlacklistClassifier(cache);

        // When
        boolean result = blacklistClassifier.isBlacklisted("nils emil");

        // Then
        assertTrue(result);
    }

    @Test
    public void isBlacklisted_ReturnsTrue_IfNameHasOneNameWithDirectMatchAndOneWithCloseMatch() {
        // Given
        Set<String> set = getTestData();
        BlacklistCache cache = new BlacklistCacheTestImpl(set);
        BlacklistClassifier blacklistClassifier = new BlacklistClassifier(cache);

        // When
        boolean result = blacklistClassifier.isBlacklisted("nils emiil");

        // Then
        assertTrue(result);
    }

    @Test
    public void isBlacklisted_ReturnsTrue_IfNameTwoNamesWithCloseMatch() {
        // Given
        Set<String> set = getTestData();
        BlacklistCache cache = new BlacklistCacheTestImpl(set);
        BlacklistClassifier blacklistClassifier = new BlacklistClassifier(cache);

        // When
        boolean result = blacklistClassifier.isBlacklisted("pils Remil");

        // Then
        assertTrue(result);
    }

    @Test
    public void isBlacklisted_ReturnsTrue_IfPersonHasOnlyOneNameAndItMatches() {
        // Given
        Set<String> set = getTestData();
        BlacklistCache cache = new BlacklistCacheTestImpl(set);
        BlacklistClassifier blacklistClassifier = new BlacklistClassifier(cache);

        // When
        boolean result = blacklistClassifier.isBlacklisted("nils");

        // Then
        assertTrue(result);
    }

    @Test
    public void isBlacklisted_ReturnsTrue_IfInputCapitalLettersAndMultipleCloseMatches() {
        // Given
        Set<String> set = getTestData();
        BlacklistCache cache = new BlacklistCacheTestImpl(set);
        BlacklistClassifier blacklistClassifier = new BlacklistClassifier(cache);

        // When
        boolean result = blacklistClassifier.isBlacklisted("RILS REMIL");

        // Then
        assertTrue(result);
    }

    @Test
    public void isBlacklisted_ReturnsFalse_IfInputCapitalAndNoMatches() {
        // Given
        Set<String> set = getTestData();
        BlacklistCache cache = new BlacklistCacheTestImpl(set);
        BlacklistClassifier blacklistClassifier = new BlacklistClassifier(cache);

        // When
        boolean result = blacklistClassifier.isBlacklisted("OSAMA BIN LADEN");

        // Then
        assertFalse(result);
    }

    @Test
    public void isBlacklisted_ReturnsFalse_IfNoNameHasAMatch() {
        // Given
        Set<String> set = getTestData();
        BlacklistCache cache = new BlacklistCacheTestImpl(set);
        BlacklistClassifier blacklistClassifier = new BlacklistClassifier(cache);

        // When
        boolean result = blacklistClassifier.isBlacklisted("osama bin laden");

        // Then
        assertFalse(result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void isBlacklisted_ThrowsIllegalArgumentException_IfInputNull() {
        // Given
        Set<String> set = Collections.emptySet();
        BlacklistCache cache = new BlacklistCacheTestImpl(set);
        BlacklistClassifier blacklistClassifier = new BlacklistClassifier(cache);

        // Then
        blacklistClassifier.isBlacklisted(null);
    }
}