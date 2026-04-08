# Schematic Engine

The engine acts as a wrapper over different schematic implementations using the **Strategy Pattern**. This allows you to hot-swap between our custom Native engine and the popular FAWE (FastAsyncWorldEdit) engine without changing your business logic!

### 1. The Native Provider (`NativeSchematicProvider`)

The asynchronous schematic engine built into the library was coded entirely from scratch. It features massive optimizations tailored for chunk-flow blocks (achieved by splitting blocks mathematically through `CollectionUtility.split()`) to completely prevent server tick freezes. It interacts with an accessible `.json` structure format.

```java
import net.xdevelopment.xlibrary.schematic.nativeapi.NativeSchematicProvider;

// Registering the native FAWE-independent engine
SchematicProvider provider = new NativeSchematicProvider(plugin);
SchematicManager manager = new SchematicManager(provider);
```

### 2. The FAWE Provider (`FaweSchematicProvider`)

If your server heavily relies on FAWE and you prefer using the standard `.schem` format, XLibrary tightly integrates with it.

```java
import net.xdevelopment.xlibrary.schematic.fawe.FaweSchematicProvider;

// Registering the external FastAsyncWorldEdit engine
SchematicProvider provider = new FaweSchematicProvider();
SchematicManager manager = new SchematicManager(provider);
```

> **Note:** Make sure you have `FastAsyncWorldEdit` inside your plugins folder before injecting this provider, otherwise it will fail to initialize.

---

## Operations (Universal API)

Once your `SchematicManager` is initialized with the provider of your choice, the methods for saving, pasting, and undoing **remain exactly the same**!

You are obligated to design a selection wand element (such as an axe dynamically tracked by `SelectionListener`). 
Upon identifying the minimum and maximum terrain points:

```java
File targetFile = new File(plugin.getDataFolder(), "my_schematic.json");

// Final boolean flag true handles ignoring internal air generation
manager.save(targetFile, minLocation, maxLocation, true).thenAccept(file -> {
    player.sendMessage("Schematic fully saved to disk!");
});
```

### Loading Block Map (Paste)

To physically project the environment:

```java
manager.paste(targetFile, pasteLocation).thenAccept(session -> {
    // The Session represents an instantiated tracking object dedicated to this action operation.
    // Explicitly cache it if you strictly intend to execute UNDO capabilities in the future!
    lastSessions.put(player.getUniqueId(), session);
});
```

### Reversing Placement (Undo)

The engine accurately supports native un-pasting rollbacks:

```java
Object session = lastSessions.get(player.getUniqueId());
if (session != null) {
    manager.undo(session);
}
```
