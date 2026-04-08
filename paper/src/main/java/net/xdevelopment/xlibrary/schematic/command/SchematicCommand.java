package net.xdevelopment.xlibrary.schematic.command;

import net.xdevelopment.xlibrary.command.ArgumentCommand;
import net.xdevelopment.xlibrary.command.Command;
import net.xdevelopment.xlibrary.command.CommandContext;
import net.xdevelopment.xlibrary.command.annotation.CommandName;
import net.xdevelopment.xlibrary.command.annotation.CommandPermission;
import net.xdevelopment.xlibrary.command.annotation.OnlyPlayer;
import net.xdevelopment.xlibrary.core.utility.CollectionUtility;
import net.xdevelopment.xlibrary.schematic.SchematicManager;
import net.xdevelopment.xlibrary.schematic.SchematicMessages;
import net.xdevelopment.xlibrary.schematic.nativeapi.SchematicConverter;
import net.xdevelopment.xlibrary.schematic.selection.PlayerSelection;
import net.xdevelopment.xlibrary.schematic.selection.SelectionListener;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@CommandName("schematic")
@CommandPermission("xlibrary.schematic.use")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SchematicCommand extends Command {

    SchematicManager manager;
    SelectionListener selectionListener;
    File schematicsFolder;
    Map<UUID, Object> lastSessions = new HashMap<>();

    public SchematicCommand(SchematicManager manager, SelectionListener selectionListener, File schematicsFolder) {
        this.manager = manager;
        this.selectionListener = selectionListener;
        this.schematicsFolder = schematicsFolder;

        addArgument(new WandArgument());
        addArgument(new SaveArgument());
        addArgument(new PasteArgument());
        addArgument(new UndoArgument());
        addArgument(new ConvertArgument());
    }

    @Override
    public void execute(@NotNull CommandContext context) {
        context.sendMessage(SchematicMessages.HELP_HEADER);
        context.sendMessage(SchematicMessages.HELP_WAND);
        context.sendMessage(SchematicMessages.HELP_SAVE);
        context.sendMessage(SchematicMessages.HELP_PASTE);
        context.sendMessage(SchematicMessages.HELP_UNDO);
        context.sendMessage(SchematicMessages.HELP_CONVERT);
    }

    @CommandName("wand")
    @OnlyPlayer
    class WandArgument extends ArgumentCommand {
        @Override
        public void execute(CommandContext context) {
            Player player = context.getPlayer();
            player.getInventory().addItem(new ItemStack(SelectionListener.WAND_MATERIAL));
            context.sendMessage(SchematicMessages.WAND_GIVE);
            context.sendMessage(SchematicMessages.WAND_LEFT);
        }
    }

    @CommandName("save")
    @OnlyPlayer
    class SaveArgument extends ArgumentCommand {
        @Override
        public void execute(CommandContext context) {
            if (context.argumentCount() < 1) {
                context.sendMessage(SchematicMessages.HELP_SAVE);
                return;
            }

            Player player = context.getPlayer();
            PlayerSelection selection = selectionListener.getSelection(player);
            if (selection == null || !selection.isComplete()) {
                context.sendMessage(SchematicMessages.NO_SELECTION);
                return;
            }

            String name = context.getArgument(0);
            File file = new File(schematicsFolder, name + ".json");

            context.sendMessage(SchematicMessages.SAVE_START, Map.of("name", name));

            manager.save(file, selection.getMinCorner(), selection.getMaxCorner(), true)
                    .thenAccept(result -> context.sendMessage(SchematicMessages.SAVE_SUCCESS, Map.of("name", name)))
                    .exceptionally(ex -> {
                        context.sendMessage(SchematicMessages.SAVE_FAIL, Map.of("name", name));
                        return null;
                    });
        }
    }

    @CommandName("paste")
    @OnlyPlayer
    class PasteArgument extends ArgumentCommand {
        @Override
        public void execute(CommandContext context) {
            if (context.argumentCount() < 1) {
                context.sendMessage(SchematicMessages.HELP_PASTE);
                return;
            }

            String name = context.getArgument(0);
            File file = new File(schematicsFolder, name + ".json");

            if (!file.exists()) {
                context.sendMessage(SchematicMessages.NO_FILE, Map.of("name", name));
                return;
            }

            context.sendMessage(SchematicMessages.PASTE_START, Map.of("name", name));

            manager.paste(file, context.getPlayer().getLocation())
                    .thenAccept(session -> {
                        if (session != null) lastSessions.put(context.getPlayer().getUniqueId(), session);
                        context.sendMessage(SchematicMessages.PASTE_SUCCESS, Map.of("name", name));
                    })
                    .exceptionally(ex -> {
                        context.sendMessage(SchematicMessages.PASTE_FAIL, Map.of("name", name));
                        return null;
                    });
        }

        @Override
        public List<String> tabComplete(@NotNull CommandContext context) {
            if (context.argumentCount() == 1) {
                File[] files = schematicsFolder.listFiles((dir, fName) -> fName.endsWith(".json"));
                if (files == null) return Collections.emptyList();

                List<String> names = Arrays.stream(files)
                        .map(f -> f.getName().replace(".json", ""))
                        .toList();
                return CollectionUtility.getSequentialMatches(names, context.getArgument(0));
            }
            return super.tabComplete(context);
        }
    }

    @CommandName("undo")
    @OnlyPlayer
    class UndoArgument extends ArgumentCommand {
        @Override
        public void execute(CommandContext context) {
            Player player = context.getPlayer();
            Object session = lastSessions.remove(player.getUniqueId());
            if (session == null) {
                context.sendMessage(SchematicMessages.UNDO_FAIL);
                return;
            }

            manager.undo(session)
                    .thenRun(() -> context.sendMessage(SchematicMessages.UNDO_SUCCESS))
                    .exceptionally(ex -> {
                        context.sendMessage(SchematicMessages.UNDO_FAIL);
                        return null;
                    });
        }
    }

    @CommandName("convert")
    class ConvertArgument extends ArgumentCommand {
        public ConvertArgument() {
            addArgument(new ConvertSchematicArgument());
        }

        @Override
        public void execute(CommandContext context) {
            context.sendMessage(SchematicMessages.HELP_CONVERT);
        }
    }

    @CommandName("schematic")
    class ConvertSchematicArgument extends ArgumentCommand {
        @Override
        public void execute(CommandContext context) {
            context.sendMessage(SchematicMessages.CONVERT_START);

            try {
                int count = SchematicConverter.convertAll(schematicsFolder);
                if (count == 0) {
                    context.sendMessage(SchematicMessages.CONVERT_EMPTY);
                } else {
                    context.sendMessage(SchematicMessages.CONVERT_SUCCESS, Map.of("count", count));
                }
            } catch (Exception ex) {
                context.sendMessage(SchematicMessages.CONVERT_FAIL);
            }
        }
    }
}
