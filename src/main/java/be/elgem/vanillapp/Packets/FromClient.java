package be.elgem.vanillapp.Packets;

import be.elgem.vanillapp.utils.EnchantFormatter;
import be.elgem.vanillapp.Main;
import be.elgem.vanillapp.utils.LanguageHandler;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.*;
import com.comphenix.protocol.reflect.StructureModifier;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class FromClient implements PacketListener {
    private Main main;
    private ListeningWhitelist listeningWhitelist;


    public FromClient(Main main) {
        this.main = main;

        listeningWhitelist = ListeningWhitelist.newBuilder().priority(ListenerPriority.HIGHEST).types(new PacketType[]{PacketType.Play.Client.WINDOW_CLICK, PacketType.Play.Client.SET_CREATIVE_SLOT, PacketType.Play.Client.SETTINGS}).build();
    }

    @Override
    public void onPacketSending(PacketEvent packetEvent) {

    }

    @Override
    public void onPacketReceiving(PacketEvent packetEvent) {
        if(!main.getActivateCommand().isEnable()){
            return;
        }

        if(packetEvent.getPacketType().equals(PacketType.Play.Client.SETTINGS)){
            if(packetEvent.getPlayer() != null) {
                LanguageHandler.addLocalization(packetEvent.getPlayer(), packetEvent.getPacket().getStrings().readSafely(0));
            }

        }
        else {
            StructureModifier<ItemStack> itemList = packetEvent.getPacket().getItemModifier();

            for (int i = 0; i < itemList.size(); i++) {
                ItemStack itemStack = itemList.readSafely(i);

                if (itemStack != null && !itemStack.getType().equals(Material.AIR)) {
                    EnchantFormatter.unformatEnchant(itemStack);

                    itemList.writeSafely(i, itemStack);
                }
            }
        }
    }

    @Override
    public ListeningWhitelist getSendingWhitelist() {
        return ListeningWhitelist.EMPTY_WHITELIST;
    }

    @Override
    public ListeningWhitelist getReceivingWhitelist() {
        return listeningWhitelist;
    }

    @Override
    public Plugin getPlugin() {
        return main;
    }
}
