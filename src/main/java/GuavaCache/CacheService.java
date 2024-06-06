package GuavaCache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class CacheService{
    private LoadingCache<String, String> cacheElement;
    String spec = "maximumSize=10000,expireAfterWrite=10m";
    CacheLoader<String, String> loader;

    public CacheService(int maxSize){
        cacheElement = CacheBuilder
            .newBuilder()
            .maximumSize(maxSize)
            .weakKeys()
            .expireAfterWrite(Duration.ofSeconds(5))
            .removalListener(new RemovalListener<String, String>() {

                @Override
                public void onRemoval(RemovalNotification<String, String> notification) {
                    System.out.println(notification.getCause().name());
                }
            })
            .recordStats()
            .build(
            new CacheLoader<String, String>() {
                @Override
                public String load(String key) throws Exception {
                    return key.toUpperCase();
                }
            }
        );
    }

    public LoadingCache<String, String> getCache(){
        return  cacheElement;
    }

    public Map<String, String> getCacheAsMap(){
        return  cacheElement.asMap();
    }
    public long getTotalLoadTime(){
       return cacheElement.stats().totalLoadTime();
    }
    public long getEvictionCount(){
        return cacheElement.stats().evictionCount();
    }
    public double getHitRate(){
        return cacheElement.stats().hitRate();
    }
}
