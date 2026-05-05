package fr.zyumie.nospearenchant;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

public class SpearEnchantBlocker implements Listener {

    private final SpearEnchantCleaner cleaner;

    public SpearEnchantBlocker(SpearEnchantCleaner cleaner) {
        this.cleaner = cleaner;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (!event.getPlayer().getServer().getPluginManager()
                .getPlugin("NoSpearEnchant")
                .getConfig().getBoolean("clear-existing-spear-enchants")) return;

        for (ItemStack item : event.getPlayer().getInventory().getContents()) {
            if (cleaner.isSpear(item)) {
                cleaner.clearEnchants(item);
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onEnchant(EnchantItemEvent event) {
        ItemStack item = event.getItem();

        if (!cleaner.isSpear(item)) return;

        event.setCancelled(true);
        cleaner.clearEnchants(item);
    }

    @EventHandler
    public void onAnvil(PrepareAnvilEvent event) {
        ItemStack result = event.getResult();

        if (!cleaner.isSpear(result)) return;

        event.setResult(null);
    }
}