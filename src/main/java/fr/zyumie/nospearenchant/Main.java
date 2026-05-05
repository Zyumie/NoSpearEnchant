package fr.zyumie.nospearenchant;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class Main extends JavaPlugin {

    private long lastConfigModified;
    private boolean alreadyCleared = false;

    private SpearEnchantCleaner cleaner;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        cleaner = new SpearEnchantCleaner(this);

        // Enregistre les events
        getServer().getPluginManager().registerEvents(new SpearEnchantBlocker(cleaner), this);

        File configFile = new File(getDataFolder(), "config.yml");
        lastConfigModified = configFile.lastModified();

        // Watch config
        getServer().getScheduler().runTaskTimer(this, () -> {
            long modified = configFile.lastModified();

            if (modified != lastConfigModified) {
                lastConfigModified = modified;
                reloadConfig();

                if (getConfig().getBoolean("clear-existing-spear-enchants") && !alreadyCleared) {
                    cleaner.clearExistingSpears();
                    alreadyCleared = true;
                }
            }

        }, 20L, 20L * 5);
    }
}