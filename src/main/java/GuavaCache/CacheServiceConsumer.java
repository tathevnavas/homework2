package GuavaCache;

import java.util.Map;

public class CacheServiceConsumer {
    public static void main(String[] args) {
        int maxSize = 100000;
        var cache = new CacheService(maxSize).getCache();
        cache.getUnchecked("first");
        cache.getUnchecked("second");
        cache.getUnchecked("third");
        cache.getUnchecked("forth");


    }

}

