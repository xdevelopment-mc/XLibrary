package net.xdevelopment.xlibrary.core.utility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.jetbrains.annotations.NotNull;

import com.google.common.collect.Lists;

import lombok.Getter;

@Getter
public class Paginator<T> {

    private final int pageSize;
    private final List<List<T>> pages;

    public Paginator(@NotNull Collection<T> objects, int pageSize) {
        if (pageSize <= 0) {
            throw new IllegalArgumentException("Page size must be greater than 0");
        }
        this.pageSize = pageSize;
        
        List<T> list = (objects instanceof List) ? (List<T>) objects : new ArrayList<>(objects);
        this.pages = Lists.partition(list, pageSize);
    }

    @NotNull
    public List<T> getPage(int pageNumber) {
        int index = pageNumber - 1;

        if (index < 0 || index >= pages.size()) {
            return Collections.emptyList();
        }

        return pages.get(index);
    }

    public int getTotalPages() {
        return pages.size();
    }

    public int getStartIndex(int pageNumber) {
        return (pageNumber - 1) * pageSize;
    }
}
