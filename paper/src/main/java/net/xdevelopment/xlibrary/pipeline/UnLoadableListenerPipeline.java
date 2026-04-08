package net.xdevelopment.xlibrary.pipeline;

import net.xdevelopment.xlibrary.core.pipeline.UnLoadablePipeline;

import org.bukkit.event.HandlerList;

/**
 * @author Anyachkaa
 */
public interface UnLoadableListenerPipeline extends LoadableListenerPipeline, UnLoadablePipeline {

    @Override
    default void unload() {
        HandlerList.unregisterAll(this);
    }
}
