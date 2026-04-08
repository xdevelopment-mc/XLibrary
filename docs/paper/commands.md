# Command API

The command framework in XLibrary allows writing commands via annotations, supports automatic argument routing (nested `subcommands`), and features a built-in `CommandContext` wrapper.

## Command Creation

Create a class extending `Command`, then add the `@CommandName` and `@CommandPermission` annotations.

```java
import net.xdevelopment.xlibrary.command.Command;
import net.xdevelopment.xlibrary.command.annotation.CommandName;

@CommandName("hello")
@CommandPermission("plugin.hello")
public class HelloCommand extends Command {

    @Override
    public void execute(CommandContext context) {
        context.sendMessage("<green>Hello, server!</green>");
    }
}
```

## ArgumentCommand (Sub-Commands)

Instead of long `if (args[0].equals("give"))` chains, you can simply register `ArgumentCommand` sub-commands:

```java
@CommandName("economy")
public class EconomyCommand extends Command {

    public EconomyCommand() {
        argument(new GiveArgument());
        argument(new TakeArgument());
    }

    @Override
    public void execute(CommandContext context) {
        context.sendMessage("Usage: /economy <give|take>");
    }

    @CommandName("give")
    @OnlyPlayer // Forbids console from running this branch
    class GiveArgument extends ArgumentCommand {
        @Override
        public void execute(CommandContext context) {
            Player player = context.player(); // Strict Player return (thanks to @OnlyPlayer)
            player.sendMessage("You received gold!");
        }
    }
    
    // ...
}
```

## CommandContext

`CommandContext` neatly wraps the Sender and command arguments:
- `context.sender()` — returns the Bukkit `CommandSender`.
- `context.player()` — safe cast to `Player` (throws an exception if it is console, so always ensure you use `@OnlyPlayer` beforehand).
- `context.argument(0)` — grabs the first argument *relative to the current sub-command*. For example, in `/economy give 100`, calling `context.argument(0)` inside the `give` sub-command will return `"100"`, not `"give"`.

## Command Registration

```java
SimpleCommandManager commandManager = new SimpleCommandManager(plugin);
commandManager.register(new EconomyCommand());
```
