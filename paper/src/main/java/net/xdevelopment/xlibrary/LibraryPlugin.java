package net.xdevelopment.xlibrary;

import net.xdevelopment.xlibrary.command.SimpleCommandManager;
import net.xdevelopment.xlibrary.schematic.SchematicManager;
import net.xdevelopment.xlibrary.schematic.command.SchematicCommand;
import net.xdevelopment.xlibrary.schematic.nativeapi.NativeSchematicProvider;
import net.xdevelopment.xlibrary.schematic.selection.SelectionListener;
import net.xdevelopment.xlibrary.utility.gui.MenuListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class LibraryPlugin extends JavaPlugin {

    private SimpleCommandManager commandManager;

    @Override
    public void onEnable() {
        File schematicsFolder = new File(getDataFolder(), "schematics");
        schematicsFolder.mkdirs();

        NativeSchematicProvider provider = new NativeSchematicProvider(this);
        SchematicManager manager = new SchematicManager(provider);
        SelectionListener listener = new SelectionListener();

        getServer().getPluginManager().registerEvents(listener, this);
        getServer().getPluginManager().registerEvents(new MenuListener(), this);

        commandManager = new SimpleCommandManager(this);
        commandManager.register(new SchematicCommand(manager, listener, schematicsFolder));
    }
}
