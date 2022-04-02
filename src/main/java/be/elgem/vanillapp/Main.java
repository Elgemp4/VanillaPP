package be.elgem.vanillapp;

import be.elgem.vanillapp.Listeners.AnvilListener;
import be.elgem.vanillapp.Listeners.JoinLeaveListener;
import be.elgem.vanillapp.Packets.FromClient;
import be.elgem.vanillapp.Packets.FromServer;
import be.elgem.vanillapp.command.Activate;
import be.elgem.vanillapp.utils.LanguageHandler;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class Main extends JavaPlugin {
    private Activate activateCommand;

    public static Main main;

    @Override
    public void onEnable() {
        super.onEnable();

        main = this;

        addCommands();

        addListeners();

        addPacketListener();
    }

    private void addListeners() {
        this.getServer().getPluginManager().registerEvents(new AnvilListener(), this);
        this.getServer().getPluginManager().registerEvents(new JoinLeaveListener(), this);
    }

    private void addCommands() {
        activateCommand = new Activate(this);
        this.getServer().getPluginCommand("enable").setExecutor(activateCommand);
    }

    private void addPacketListener() {
        ProtocolManager manager = ProtocolLibrary.getProtocolManager();

        manager.addPacketListener(new FromClient(this));
        manager.addPacketListener(new FromServer(this));
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public Activate getActivateCommand() {
        return activateCommand;
    }

    public static Main getMain() {
        return main;
    }
}
