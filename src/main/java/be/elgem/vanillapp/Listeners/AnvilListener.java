package be.elgem.vanillapp.Listeners;

import be.elgem.vanillapp.Managers.BuffedAnvil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;

public class AnvilListener implements Listener {
    private BuffedAnvil buffedAnvil;

    public AnvilListener() {

    }

    @EventHandler
    private void onAnvil(PrepareAnvilEvent event) {
        buffedAnvil = new BuffedAnvil(event);
    }

}
