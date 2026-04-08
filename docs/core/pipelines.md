# Pipelines

XLibrary implements a Pipeline system that simplifies plugin lifecycle management (loading and unloading modules).

## LoadablePipeline and UnLoadablePipeline

Often there is a need to load managers, configs, or register listeners.

```java
public class MyFeatureManager implements LoadablePipeline, UnLoadablePipeline {
    @Override
    public void load() {
        // Initialization logic
    }

    @Override
    public void unload() {
        // Shutdown and cleanup logic
    }
}
```

The system becomes extremely powerful with `LoadableListenerPipeline`:
```java
public class MyListener implements LoadableListenerPipeline {
    @EventHandler
    public void onLogin(PlayerJoinEvent event) {
        event.getPlayer().sendMessage("Hello!");
    }
}
```

Classes implementing `LoadableListenerPipeline` will automatically register themselves as Bukkit Listeners when `load()` is executed!

## Main Pipeline

You can register all pipelines into a single manageable object:

```java
Pipeline mainPipeline = new Pipeline();

mainPipeline.addLoader(new MyFeatureManager());
mainPipeline.addLoader(new MyListener());

// Inside onEnable:
mainPipeline.loadAll();

// Inside onDisable:
mainPipeline.unLoadAll();
```
