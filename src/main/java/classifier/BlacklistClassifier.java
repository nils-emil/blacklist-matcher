package classifier;

import cache.BlacklistCache;
import utils.Constants;
import utils.StringUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class BlacklistClassifier {

    private final BlacklistCache blacklistCache;

    public BlacklistClassifier(BlacklistCache blacklistCache) {
        this.blacklistCache = blacklistCache;
    }

    public boolean isBlacklisted(String fullName) {
        if (fullName == null) {
            throw new IllegalArgumentException("Name can not be null");
        }
        int numberOfNamesThatMatched = 0;
        Set<String> examinedNamesInLowerCase = getSinglePersonNames(fullName);
        for (String name : examinedNamesInLowerCase) {
            Set<String> possibleRelatedNames = this.blacklistCache.getRelatedNames(name);
            Set<String> namesWithCloseDistance = getNamesWithCloseDistance(
                    examinedNamesInLowerCase,
                    possibleRelatedNames);
            if (examinedNamesInLowerCase.size() == 1 && namesWithCloseDistance.size() == 1) {
                return true;
            }
            numberOfNamesThatMatched = namesWithCloseDistance.size();
        }
        return numberOfNamesThatMatched >= Constants.MINIMAL_AMOUNT_OF_NAMES_TO_MATCH_BLACKLIST;

    }

    private Set<String> getNamesWithCloseDistance(Set<String> personNames,
                                                  Set<String> relatedBlackListedNames) {
        Set<String> matchingNames = new HashSet<>();
        for (String blacklistedName : relatedBlackListedNames) {
            for (String name : personNames) {
                int distance = StringUtils.getEditDistanceBetweenWords(blacklistedName, name,
                        blacklistedName.length(), name.length());
                if (distance <= Constants.MAXIMUM_ALLOWED_DIFFERENCE_BETWEEN_NAMES) {
                    matchingNames.add(name);
                }
            }
        }
        return matchingNames;
    }

    private Set<String> getSinglePersonNames(String name) {
        String nameWithoutSpecialCharacters = name
                .toLowerCase()
                .replaceAll(Constants.NON_ALPHANUMERIC_CHARACTERS_EXCLUDING_SPACE, Constants.EMPTY_STRING);
        String[] names = nameWithoutSpecialCharacters.split(Constants.SPACE);
        return new HashSet<>(Arrays.asList(names));
    }
}
