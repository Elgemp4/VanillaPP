package be.elgem.vanillapp.command;

import be.elgem.vanillapp.Main;
import com.comphenix.protocol.PacketType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Activate implements CommandExecutor {
    private Main main;

    private boolean isEnable = true;

    public Activate(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(label.equals("enable")){
            if(args[0].equals("false")) {
                this.isEnable = false;
            }
            else if(args[0].equals("true")){
                this.isEnable = true;
            }
            else{
                sender.sendMessage("Mauvaise utilisation de la commande : /enable [true | false]");
            }
        }
        return false;
    }

    public boolean isEnable() {
        return isEnable;
    }
}
