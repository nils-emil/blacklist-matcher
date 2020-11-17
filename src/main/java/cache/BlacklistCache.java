package cache;

import java.util.Set;

public interface BlacklistCache {

    Set<String> getRelatedNames(String name);

}
