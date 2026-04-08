package net.xdevelopment.xlibrary.core.pipeline.list;

import net.xdevelopment.xlibrary.core.pipeline.UnLoadablePipeline;
import net.xdevelopment.xlibrary.core.pipeline.list.exception.UnLoaderPipelineException;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Anyachkaa
 */
@FieldDefaults(level = AccessLevel.PROTECTED, makeFinal = true)
public class UnLoadablePipelineList extends LoadablePipelineList implements UnLoadablePipeline {

    List<UnLoadablePipeline> unLoadableList = new CopyOnWriteArrayList<>();

    public void addUnLoadableAll(List<UnLoadablePipeline> unLoadable) {
        this.unLoadableList.addAll(unLoadable);
    }

    @Override
    public void unload() {
        for (UnLoadablePipeline pipeline : this.unLoadableList) {
            try {
                pipeline.unload();
            } catch (Exception exception) {
                throw new UnLoaderPipelineException(exception.getMessage());
            }
        }
    }
}
