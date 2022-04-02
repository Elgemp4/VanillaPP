package be.elgem.vanillapp.Managers;

import be.elgem.vanillapp.Enum.ETypeOfItem;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.Repairable;

import java.util.Map;

public class BuffedAnvil {
    private final PrepareAnvilEvent event;

    private AnvilItem targetItem, sacrificeItem, resultItem;

    public BuffedAnvil(PrepareAnvilEvent event) {
        this.event = event;

        handleLogic();
    }

    private void handleLogic() {
        if(setItems()){
            if(AnvilItem.canItemsCombine(targetItem, sacrificeItem)){
                this.resultItem = new AnvilItem(targetItem);

                combineEnchants();
                if(resultItem.getItemType()==ETypeOfItem.TOOL){
                    combineDurability();
                }

                actualizeItemCost();

                event.setResult(resultItem);
            }
        }
    }

    private boolean setItems() {
        AnvilInventory inventory = event.getInventory();

        this.targetItem = new AnvilItem(inventory.getItem(0));
        this.sacrificeItem = new AnvilItem(inventory.getItem(1));

        return (targetItem.getItemType() != ETypeOfItem.NONE) && (sacrificeItem.getItemType() != ETypeOfItem.NONE);
    }

    private void combineEnchants() {
        Map<Enchantment, Integer> thisEnchantMap = targetItem.getItemEnchants();
        Map<Enchantment, Integer> combineEnchantMap = sacrificeItem.getItemEnchants();

        for(Enchantment enchantment : combineEnchantMap.keySet()){
            if(thisEnchantMap.containsKey(enchantment)){
                int level = AnvilItem.calculateLevel(thisEnchantMap.get(enchantment), combineEnchantMap.get(enchantment));
                resultItem.addEnchant(enchantment, level);
            }
            else {
                resultItem.addEnchant(enchantment, combineEnchantMap.get(enchantment));
            }
        }
    }

    private void combineDurability() {//ADD 2 LEVEL XP
        if(!(targetItem.getItemType() == ETypeOfItem.TOOL && sacrificeItem.getItemType() == ETypeOfItem.TOOL)) {
            return;
        }

        Damageable thisTool = (Damageable) targetItem.getItemMeta();
        Damageable combineTool = (Damageable) sacrificeItem.getItemMeta();

        int maxDurability = targetItem.getType().getMaxDurability();
        int durabilityBonus = (int) (maxDurability * 0.12);
        int thisToolDurability = maxDurability - thisTool.getDamage();
        int combineToolDurability = maxDurability - combineTool.getDamage();
        int newDurability = maxDurability - Math.min(maxDurability, thisToolDurability + combineToolDurability + durabilityBonus);

        if (thisTool.hasDamage()) {
            thisTool.setDamage(newDurability);
            resultItem.setItemMeta(thisTool);
        }
    }

    private void actualizeItemCost() {
        if(resultItem.getItemMeta() instanceof Repairable){
            Repairable repairable = (Repairable) resultItem.getItemMeta();

            int anvilUseCount = (int) Math.sqrt(repairable.getRepairCost() + 1) + 1;
            repairable.setRepairCost((int) (Math.pow(anvilUseCount, 2) - 1));
            resultItem.setItemMeta(repairable);
        }
    }
}
