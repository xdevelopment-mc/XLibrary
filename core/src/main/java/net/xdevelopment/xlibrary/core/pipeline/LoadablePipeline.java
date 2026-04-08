package net.xdevelopment.xlibrary.core.pipeline;

import net.xdevelopment.xlibrary.core.pipeline.list.exception.LoadablePipelineException;

/**
 * @author Anyachkaa
 */
public interface LoadablePipeline {

    void load() throws LoadablePipelineException;
}
