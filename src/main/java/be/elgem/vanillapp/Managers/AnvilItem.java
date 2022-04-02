package be.elgem.vanillapp.Managers;

import be.elgem.vanillapp.Enum.ETypeOfItem;
import org.bukkit.Material;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

import java.util.Map;

public class AnvilItem extends ItemStack {
    private ETypeOfItem type;


    public AnvilItem(ItemStack item) {
        if(item==null){
            return;
        }

        this.setType(item.getType());
        this.setItemMeta(item.getItemMeta());
        this.setData(item.getData());
        this.setAmount(1);

        findItemType();
    }

    private void findItemType(){
        if(this.getType().equals(Material.ENCHANTED_BOOK)){
            type = ETypeOfItem.ENCHANTED_BOOK;
        }
        else if(this.getItemMeta() instanceof Damageable){
            type = ETypeOfItem.TOOL;
        }
        else{
            type = ETypeOfItem.OTHER;
        }
    }



    public void addEnchant(Enchantment enchantment, int level) {
        if(type == ETypeOfItem.ENCHANTED_BOOK){
            EnchantmentStorageMeta meta = (EnchantmentStorageMeta) this.getItemMeta();
            meta.addStoredEnchant(enchantment, level, true);
            this.setItemMeta(meta);
        }
        else{
            this.addUnsafeEnchantment(enchantment, level);
        }
    }

    ///////////////
    ////GETTERS////
    ///////////////
    public Map<Enchantment, Integer> getItemEnchants() {
        if(type == ETypeOfItem.ENCHANTED_BOOK){
            return ((EnchantmentStorageMeta) this.getItemMeta()).getStoredEnchants();
        }
        else{
            return this.getEnchantments();
        }
    }

    public ETypeOfItem getItemType() {
        return type;
    }

    public static int calculateLevel(int targetLevel, int sacrificeLevel) {
        if(targetLevel==sacrificeLevel) {
            return targetLevel + 1;
        }
        else return Math.max(targetLevel, sacrificeLevel);
    }

    private boolean isEnchantInLimit() {
        //TODO
        return true;
    }


    //////////////////////
    ////STATIC METHODS////
    //////////////////////
    public static boolean canItemsCombine(AnvilItem targetItem, AnvilItem sacrificeItem) {
        if(targetItem.getType().equals(sacrificeItem.getType())){
            return true;
        }
        else if(sacrificeItem.getItemType() == ETypeOfItem.ENCHANTED_BOOK){
            return true;
        }
        else{
            return false;
        }
    }
}
