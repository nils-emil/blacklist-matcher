package cache;

import utils.Constants;
import utils.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class BlacklistCacheImpl implements BlacklistCache {

    Map<String, Set<String>> blacklistNameRelations;

    public BlacklistCacheImpl(Set<String> blacklistedNames, Set<String> noiseWords) {
        this.blacklistNameRelations = getRelatedNames(blacklistedNames, noiseWords);
    }

    public Set<String> getRelatedNames(String name) {
        Set<String> relatedNames = new HashSet<>();
        for (Map.Entry<String, Set<String>> key : this.blacklistNameRelations.entrySet()) {
            if (isNameDistanceClose(name, key.getKey())) {
                relatedNames.addAll(key.getValue());
            }
        }
        return relatedNames;
    }

    private Map<String, Set<String>> getRelatedNames(Set<String> blacklistedNames,
                                                     Set<String> noiseWords) {
        Map<String, Set<String>> relatedNames = new HashMap<>();
        blacklistedNames.stream()
                .map(e -> e.toLowerCase().replace(Constants.COMMA, Constants.EMPTY_STRING))
                .map(lowerCaseName -> getUniqueNamesFromFullNameWithNoiseRemoved(noiseWords, lowerCaseName))
                .forEach(allNames -> allNames
                        .forEach(j -> relatedNames.merge(j, allNames, (a, b) -> {
                            a.addAll(b);
                            return a;
                        })));
        return Collections.unmodifiableMap(relatedNames);
    }

    private boolean isNameDistanceClose(String name, String key) {
        int distance = StringUtils.getEditDistanceBetweenWords(key, name, key.length(), name.length());
        return distance <= Constants.MAXIMUM_ALLOWED_DIFFERENCE_BETWEEN_NAMES;
    }

    private Set<String> getUniqueNamesFromFullNameWithNoiseRemoved(Set<String> noiseWords,
                                                                   String lowerCaseName) {
        return new HashSet<>(Arrays.asList(lowerCaseName.split(Constants.SPACE)))
                .stream()
                .filter(g -> !noiseWords.contains(g))
                .collect(Collectors.toSet());
    }
}
