package be.elgem.vanillapp.Packets;

import be.elgem.vanillapp.utils.EnchantFormatter;
import be.elgem.vanillapp.Main;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListeningWhitelist;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.events.PacketListener;
import com.comphenix.protocol.reflect.StructureModifier;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class FromServer implements PacketListener {
    private Main main;

    private ListeningWhitelist listeningWhitelist;

    public FromServer(Main main) {
        this.main = main;

        listeningWhitelist = ListeningWhitelist.newBuilder().highest().types(new PacketType[]{PacketType.Play.Server.WINDOW_ITEMS, PacketType.Play.Server.SET_SLOT}).build();
    }


    @Override
    public void onPacketSending(PacketEvent packetEvent) {
        if(!main.getActivateCommand().isEnable()){
            return;
        }

        if(packetEvent.getPacketType().equals(PacketType.Play.Server.WINDOW_ITEMS)){
            List<ItemStack> sendItems = packetEvent.getPacket().getItemListModifier().readSafely(0);

            for(int i = 0; i < sendItems.size(); i++) {
                ItemStack itemStack = sendItems.get(i);

                if(itemStack != null && !itemStack.getType().equals(Material.AIR)) {
                    EnchantFormatter.formatEnchant(itemStack, packetEvent.getPlayer());

                    sendItems.set(i, itemStack);
                }
            }

            packetEvent.getPacket().getItemListModifier().writeSafely(0, sendItems);
        }
        else{
            StructureModifier<ItemStack> itemsList = packetEvent.getPacket().getItemModifier();

            for(int i = 0; i< itemsList.size(); i++){
                ItemStack itemStack = itemsList.readSafely(i);

                if(itemStack != null && !itemStack.getType().equals(Material.AIR)) {
                    EnchantFormatter.formatEnchant(itemStack, packetEvent.getPlayer());

                    itemsList.writeSafely(i, itemStack);
                }
            }
        }
    }

    @Override
    public void onPacketReceiving(PacketEvent packetEvent) {

    }

    @Override
    public ListeningWhitelist getSendingWhitelist() {
        return listeningWhitelist;
    }

    @Override
    public ListeningWhitelist getReceivingWhitelist() {
        return ListeningWhitelist.EMPTY_WHITELIST;
    }

    @Override
    public Plugin getPlugin() {
        return null;
    }
}
