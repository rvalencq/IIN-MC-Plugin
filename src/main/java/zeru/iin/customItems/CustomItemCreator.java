package zeru.iin.customItems;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import zeru.iin.IIN;

public class CustomItemCreator {
    private final IIN plugin;

    public CustomItemCreator(IIN plugin) {
        this.plugin = plugin;
    }

    public ItemStack createItem(CustomItemType type) {
        ItemStack item = new ItemStack(type.getMaterial());
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            meta.setDisplayName(type.getDisplayName());

            // Custom ID
            NamespacedKey key = new NamespacedKey(plugin, type.getId());
            meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, type.getId());

            // Enchants
            type.getEnchantments().forEach((enchant, level) ->
                    meta.addEnchant(enchant, level, true)
            );

            item.setItemMeta(meta);
            // Potion
            if (meta instanceof PotionMeta potionMeta) {
                for (PotionEffect effect : type.getPotionEffects()) {
                    potionMeta.addCustomEffect(effect, true);
                }

                if (type.getPotionColor() != null) {
                    potionMeta.setColor(type.getPotionColor());
                }
                item.setItemMeta(potionMeta);
            }
        }
        return item;
    }

    public boolean isCustomItem(ItemStack item, CustomItemType type) {
        if (item == null || !item.hasItemMeta()) return false;
        ItemMeta meta = item.getItemMeta();
        NamespacedKey key = new NamespacedKey(plugin, type.getId());
        if (!meta.getPersistentDataContainer().has(key, PersistentDataType.STRING)) return false;

        String value = meta.getPersistentDataContainer().get(key, PersistentDataType.STRING);
        return type.getId().equals(value);
    }
}
