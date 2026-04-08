# Getting Started

Welcome to XLibrary — a powerful framework for developing Paper plugins!

XLibrary is built to replace the standard approach to creating plugins. The library allows you to easily manage configurations, build multi-level commands with TabCompletion, interact with GUI menus cleanly without inventory event spaghetti, and manipulate schematics directly through its high-performance engine.

## Installation

To use XLibrary in your plugin, add it to your dependencies in `build.gradle.kts`:

```kotlin
repositories {
    mavenCentral()
    // Specify your local/private repository here if XLibrary is hosted there.
}

dependencies {
    compileOnly("net.xdevelopment:xlibrary-paper:1.0.0-SNAPSHOT")
}
```

Do not forget to specify `depend` in your plugin's `plugin.yml`:
```yaml
name: MyAwesomePlugin
version: 1.0.0
main: com.example.plugin.MyPlugin
api-version: 1.21
depend: [XLibrary]
```

## Quick Example

Here is an example of how easily you can create a graphical menu:

```java
import net.xdevelopment.xlibrary.utility.gui.Menu;
import net.xdevelopment.xlibrary.utility.gui.slot.MenuSlot;
import org.bukkit.Material;

Menu myMenu = new Menu("my_menu", "Super Menu", 3);
myMenu.setSlot(13, new MenuSlot(Material.DIAMOND)
    .display("<green>Click me</green>")
    .executable(new ExecutableClick() {
        @Override
        public void onLeft(Player player) {
            player.sendMessage("You clicked!");
        }
    })
);

player.openInventory(myMenu.getInventory());
```
