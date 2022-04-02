package be.elgem.vanillapp.utils;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class EnchantFormatter {

    public static void formatEnchant(ItemStack itemStack, Player player) {
        if(!itemStack.getItemMeta().hasEnchants()){
            return;
        }

        ItemMeta meta = itemStack.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);


        EnchantLoreBuilder enchantLoreBuilder = new EnchantLoreBuilder(itemStack.getItemMeta(), player);
        meta.setLore(enchantLoreBuilder.addEnchantsToLore());


        itemStack.setItemMeta(meta);
    }

    public static void unformatEnchant(ItemStack itemStack) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.removeItemFlags(ItemFlag.HIDE_ENCHANTS);


        EnchantLoreBuilder builder = new EnchantLoreBuilder(itemStack.getItemMeta(), null);
        meta.setLore(builder.removeEnchantsFromLore());


        itemStack.setItemMeta(meta);
    }
}
