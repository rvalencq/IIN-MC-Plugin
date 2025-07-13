package zeru.iin.customItems;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;
import java.util.Map;

public enum CustomItemType {
    MACE_3X3(
            "mace3x3",
            Material.NETHERITE_PICKAXE,
            ChatColor.LIGHT_PURPLE + "Mace 3x3",
            Map.of(),
            List.of(),
            null
    ),
    MINER_POTION(
            "minerPotion",
            Material.POTION,
            ChatColor.GOLD + "Miner Potion",
            Map.of(),
            List.of(new PotionEffect(PotionEffectType.HASTE, 9600, 7)),
            Color.YELLOW
    ),
    TIMBA_COIN(
            "timbaCoin",
            Material.STICK,
            ChatColor.AQUA + "Timba Coin",
            Map.of(),
            List.of(),
            null
    );

    private final String id;
    private final Material material;
    private final String displayName;
    private final Map<Enchantment, Integer> enchantments;
    private final List<PotionEffect> potionEffects;
    private final Color potionColor;

    CustomItemType(String id, Material material, String displayName,
                   Map<Enchantment, Integer> enchantments,
                   List<PotionEffect> potionEffects,
                   Color potionColor) {
        this.id = id;
        this.material = material;
        this.displayName = displayName;
        this.enchantments = enchantments;
        this.potionEffects = potionEffects;
        this.potionColor = potionColor;
    }

    public String getId() { return id; }
    public Material getMaterial() { return material; }
    public String getDisplayName() { return displayName; }
    public Map<Enchantment, Integer> getEnchantments() { return enchantments; }
    public List<PotionEffect> getPotionEffects() { return potionEffects; }
    public Color getPotionColor() { return potionColor; }
}
