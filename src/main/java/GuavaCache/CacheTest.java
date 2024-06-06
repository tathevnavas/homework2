package GuavaCache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import org.junit.Assert;
import org.junit.Test;

import java.time.Duration;
import java.util.concurrent.ExecutionException;

public class CacheTest {
    int maxSize = 2;
    LoadingCache<String, String> cache;

    @Test
    public void sizeBasedEviction() throws ExecutionException {
        cache = new CacheService(maxSize).getCache();
        cache.put("1st", "first");
        cache.put("2nd", "second");
        cache.put("3rd", "third");
        Assert.assertEquals(2, cache.size());
        Assert.assertTrue(cache.get("2nd") == "second");
        Assert.assertTrue(cache.get("3rd") == "third");
    }

    @Test
    public void timeBasedEviction() throws InterruptedException {
        var cache = new CacheService(maxSize).getCache();
        cache.put("1st", "first");
        cache.put("2nd", "second");
        cache.put("3rd", "third");
        Thread.sleep(5000);
        Assert.assertNull(cache.getIfPresent("1st"));
        Assert.assertNull(cache.getIfPresent("2nd"));
        Assert.assertNull(cache.getIfPresent("3rd"));
    }

    @Test
    public void CachesAsMapCheck() throws InterruptedException {
        var cache = new CacheService(maxSize).getCacheAsMap();
        cache.put("1st", "first");
        cache.put("2nd", "second");
        cache.put("3rd", "third");
        cache.put("4th", "fourth");
        Assert.assertEquals(cache.size(), 2);
    }
}
