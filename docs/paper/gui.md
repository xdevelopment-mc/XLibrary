# GUI Utilities

Building graphical interfaces without XLibrary typically involves creating custom `InventoryHolder` objects, listening heavily to `InventoryClickEvent`, executing checks like `if (e.getInventory() == menu)`, and manually canceling clicks.

With the classes in the `net.xdevelopment.xlibrary.utility.gui` package, this process shrinks to just a few neat lines of code.

## Menu

```java
Menu myMenu = new Menu("my_unique_id", "Inventory Title", 6 /* rows count */);
```

By default, the menu completely neutralizes and cancels all player interaction clicks, thanks to the `interactDisabled = true` flag. You do not even need to register custom specific listeners (as long as you have registered the library's built-in `MenuListener` on server startup).

## MenuSlot

`MenuSlot` encapsulates the visual logic of the item inside the box, alongside its action logic. You can build it fluently through chaining methods:

```java
MenuSlot slot = new MenuSlot(Material.GOLD_INGOT)
        .display("<yellow>Gold</yellow>")
        .lore(List.of("<gray>Click me!</gray>"))
        .amount(5)
        .enchanted(true);
        
myMenu.setSlot(10, slot);
```

## ExecutableClick

To attach an action execution to the slot item upon click — append an `executable`:

```java
slot.executable(new ExecutableClick() {
    @Override
    public void onLeft(Player player) {
        player.sendMessage("You clicked the Left Mouse Button!");
    }
    
    @Override
    public void onRight(Player player) {
        player.sendMessage("You clicked the Right Mouse Button!");
    }
});
```

The underlying `MenuListener` has an integrated Anti-Spam throttle protection, which means players cannot crash or glitch out the menu by spamming clicks!
