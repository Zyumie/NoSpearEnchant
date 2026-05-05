package fr.zyumie.nospearenchant;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class SpearEnchantCleaner {

    private final JavaPlugin plugin;

    public SpearEnchantCleaner(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public boolean isSpear(ItemStack item) {
        if (item == null) return false;

        Material type = item.getType();

        // 👉 A adapter selon la 26.1
        return type.name().contains("SPEAR");
    }

    public void clearEnchants(ItemStack item) {
        item.getEnchantments().keySet()
                .forEach(item::removeEnchantment);
    }

    public void clearExistingSpears() {
        for (Player player : plugin.getServer().getOnlinePlayers()) {
            for (ItemStack item : player.getInventory().getContents()) {
                if (isSpear(item)) {
                    clearEnchants(item);
                }
            }
        }
    }
}