package be.elgem.vanillapp.utils;

import be.elgem.vanillapp.Main;
import com.google.common.io.ByteStreams;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;


import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class LanguageHandler {
    private static final HashMap<Player, String> localizationMap = new HashMap<>();

    private final FileConfiguration language;

    private final Main main;

    private static final List<String> stringLocalization = Arrays.asList("bg_BG.yml",
            "cs_CZ.yml",
            "da_DK.yml",
            "de_DE.yml",
            "el_GR.yml",
            "en_GB.yml",
            "en_US.yml",
            "es_Es.yml",
            "es_MX.yml",
            "fi_FI.yml",
            "fr_CA.yml",
            "fr_FR.yml",
            "hu_HU.yml",
            "id_ID.yml",
            "it_IT.yml",
            "ja_JP.yml");

    public LanguageHandler(Player player){
        this.main = Main.getMain();

        getAllConfigFiles();

        String localization = LanguageHandler.getLocalisation(player);

        File langFolder = new File(main.getDataFolder(), "lang");

        if(localization == null){
            this.language = YamlConfiguration.loadConfiguration(new File(langFolder, "en_US.yml"));
        }
        else{
            this.language = YamlConfiguration.loadConfiguration(new File(langFolder, localization + ".yml"));
        }

    }

    public void getAllConfigFiles() {
        if(!main.getDataFolder().exists()){
            main.getDataFolder().mkdir();
            File langFolder = new File(main.getDataFolder(), "lang");
            if(!langFolder.exists()){
                langFolder.mkdir();
            }
        }

        for(String fileName : stringLocalization){
            InputStream input = main.getResource(fileName);
            File langFile = new File(main.getDataFolder(), "/lang/" + fileName);

            if(input == null && langFile.exists()){
                continue;
            }
            try {
                OutputStream output = new FileOutputStream(langFile);
                ByteStreams.copy(input, output);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public String getEnchantName(Enchantment enchantment) {
        return language.getString(enchantment.getName());
    }

    public static void addLocalization(Player player, String localization){
        LanguageHandler.localizationMap.putIfAbsent(player, localization);
    }

    public static void removeLocalization(Player player){
        LanguageHandler.localizationMap.remove(player);
    }

    private static String getLocalisation(Player player) {
        return localizationMap.get(player);
    }
}