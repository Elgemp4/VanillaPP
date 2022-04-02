package be.elgem.vanillapp.utils;

import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EnchantLoreBuilder {
    private ItemMeta meta;

    private Map<Enchantment, Integer> enchantmentMap;

    private Player player;

    public EnchantLoreBuilder(ItemMeta meta, Player player) {
        this.meta = meta;
        this.enchantmentMap = meta.getEnchants();
        this.player = player;
    }

    public ArrayList<String> addEnchantsToLore() {
        ArrayList<String> lore = new ArrayList<>();

        List<Enchantment> enchantmentList = enchantmentMap.keySet().stream().toList();

        for (int i = 0; i < enchantmentList.size(); i++) {
            Enchantment enchantment = enchantmentList.get(i);

            LanguageHandler languageHandler = new LanguageHandler(player);

            lore.add(i, ChatColor.GRAY + languageHandler.getEnchantName(enchantment) + " " + NumberConversion.convertIntegerToRoman(enchantmentMap.get(enchantment)));
        }

        return lore;
    }

    public ArrayList<String> removeEnchantsFromLore() {
        int loreSize = meta.getLore().size();
        int numberOfEnchants = enchantmentMap.size();
        ArrayList<String> newLore = new ArrayList<>();

        for (int i = numberOfEnchants; i < loreSize; i++) {
            newLore.add(meta.getLore().get(i));
        }

        return newLore;
    }
}
