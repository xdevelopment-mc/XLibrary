package net.xdevelopment.xlibrary.core.pipeline;

import net.xdevelopment.xlibrary.core.pipeline.list.UnLoadablePipelineList;

import lombok.SneakyThrows;

import org.jetbrains.annotations.NotNull;

/**
 * @author Anyachkaa
 */
public final class Pipeline extends UnLoadablePipelineList {

    public void addLoadable(LoadablePipeline loadable) {
        this.loadableList.add(loadable);
    }

    public void addUnLoadable(UnLoadablePipeline unLoadable) {
        this.unLoadableList.add(unLoadable);
    }

    public void addLoader(Object object) {
        if (object instanceof LoadablePipeline loadable) {
            addLoadable(loadable);
        }
        if (object instanceof UnLoadablePipeline unLoadable) {
            addUnLoadable(unLoadable);
        }
    }

    @SneakyThrows
    public void load(@NotNull LoadablePipeline loadable) {
        loadable.load();
    }

    @SneakyThrows
    public void unload(@NotNull UnLoadablePipeline unLoadable) {
        unLoadable.unload();
    }

    public void loadAll() {
        loadableList.forEach(this::load);
    }

    public void unLoadAll() {
        unLoadableList.forEach(this::unload);
    }
}
