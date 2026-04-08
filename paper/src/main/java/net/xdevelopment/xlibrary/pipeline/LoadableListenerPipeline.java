package net.xdevelopment.xlibrary.pipeline;

import net.xdevelopment.xlibrary.core.pipeline.LoadablePipeline;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author Anyachkaa
 */
public interface LoadableListenerPipeline extends Listener, LoadablePipeline {

    @Override
    default void load() {
        Bukkit.getPluginManager().registerEvents(this,
                JavaPlugin.getProvidingPlugin(LoadableListenerPipeline.class));
    }
}
