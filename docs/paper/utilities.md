# Paper Utilities

The `net.xdevelopment.xlibrary.utility` package provides additional helper classes specific to the Bukkit/Paper API.

## ColorUtility (MiniMessage)

Gone are the days of `ChatColor.translateAlternateColorCodes('&', message)`! XLibrary actively promotes modern Adventure API `MiniMessage` standards.

With `ColorUtility`, you can format your chat strings using HTML-like tags, gradients, and seamlessly parse placeholders:

```java
import net.xdevelopment.xlibrary.utility.ColorUtility;

// Basic text formatting
Component text = ColorUtility.colorize("<gradient:red:blue>Hello World</gradient>");

// Using Placeholders
Map<String, Object> replacements = Map.of(
    "player", player.getName(),
    "money", 500
);

Component msg = ColorUtility.colorize("You gave <money> coins to <player>!", replacements);
player.sendMessage(msg);
```

You can pass `String`, integers, or even other `Component` objects via the Map into `ColorUtility` — it will automatically bind the correct tag resolvers!

## HeadUtility

Quickly generate Player Head `ItemStack` objects without tedious Reflection.

```java
// From a Base64 Texture
ItemStack customHead = HeadUtility.headBuilder("PLAYER_HEAD;eyJ0ZXh0dXJlcyI...");

// It is also supported natively inside MenuSlots:
myMenu.setSlot(10, new MenuSlot("PLAYER_HEAD;eyJ0ZXh..."));
```
