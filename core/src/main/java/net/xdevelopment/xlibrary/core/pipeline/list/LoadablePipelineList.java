package net.xdevelopment.xlibrary.core.pipeline.list;

import net.xdevelopment.xlibrary.core.pipeline.LoadablePipeline;
import net.xdevelopment.xlibrary.core.pipeline.list.exception.LoadablePipelineException;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Anyachkaa
 */
@FieldDefaults(level = AccessLevel.PROTECTED, makeFinal = true)
public class LoadablePipelineList implements LoadablePipeline {

    List<LoadablePipeline> loadableList = new CopyOnWriteArrayList<>();

    public void addLoadableAll(List<LoadablePipeline> loadableList) {
        this.loadableList.addAll(loadableList);
    }

    @Override
    public void load() {
        for (LoadablePipeline pipeline : this.loadableList) {
            try {
                pipeline.load();
            } catch (Exception exception) {
                throw new LoadablePipelineException(exception.getMessage());
            }
        }
    }
}
