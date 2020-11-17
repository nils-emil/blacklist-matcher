package cache;


import java.util.Set;

public class BlacklistCacheTestImpl implements BlacklistCache {

    Set<String> nameVariations;

    public BlacklistCacheTestImpl(Set<String> nameVariations) {
        this.nameVariations = nameVariations;
    }

    public Set<String> getRelatedNames(String name) {
        return this.nameVariations;
    }

}