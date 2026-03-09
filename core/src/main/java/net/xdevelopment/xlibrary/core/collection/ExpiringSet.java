package net.xdevelopment.xlibrary.core.collection;

import java.io.Serializable;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import org.jetbrains.annotations.NotNull;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;

public final class ExpiringSet<E> extends AbstractSet<E> implements Set<E>, Serializable {

    private static final Object PRESENT = new Object();

    private final ConcurrentMap<E, Object> map;
    private final Cache<E, Object> cache;

    public ExpiringSet(long expireMillis) {
        this.cache = CacheBuilder.newBuilder()
                .expireAfterWrite(expireMillis, TimeUnit.MILLISECONDS)
                .build(new CacheLoader<>() {
                    @NotNull
                    @Override
                    public Object load(@NotNull Object o) {
                        return PRESENT;
                    }
                });

        this.map = this.cache.asMap();
    }

    @NotNull
    @Override
    public Iterator<E> iterator() {
        return this.map.keySet().iterator();
    }

    @Override
    public int size() {
        this.cache.cleanUp();
        return this.map.size();
    }

    @Override
    public boolean add(@NotNull E e) {
        return this.map.put(e, ExpiringSet.PRESENT) == null;
    }
}
