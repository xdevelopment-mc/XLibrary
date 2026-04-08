package net.xdevelopment.xlibrary.core.pipeline;

/**
 * @author Anyachkaa
 */
public interface ReLoadablePipeline extends UnLoadablePipeline {

    default void reload() throws Exception {
        unload();
        load();
    }
}
