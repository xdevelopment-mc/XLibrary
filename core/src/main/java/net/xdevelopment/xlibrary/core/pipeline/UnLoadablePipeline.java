package net.xdevelopment.xlibrary.core.pipeline;

import net.xdevelopment.xlibrary.core.pipeline.list.exception.UnLoaderPipelineException;

/**
 * @author Anyachkaa
 */
public interface UnLoadablePipeline extends LoadablePipeline {

    void unload() throws UnLoaderPipelineException;
}
