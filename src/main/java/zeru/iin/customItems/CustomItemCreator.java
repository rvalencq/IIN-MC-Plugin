package zeru.iin.customItems;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
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

            NamespacedKey key = new NamespacedKey(plugin, type.getId());
            meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, type.getId());

            item.setItemMeta(meta);
        }
        return item;
    }

    public boolean isCustomItem(ItemStack item, CustomItemType type) {
        if (item == null || !item.hasItemMeta()) return false;
        ItemMeta meta = item.getItemMeta();
        NamespacedKey key = new NamespacedKey(plugin, type.getId());
        String value = meta.getPersistentDataContainer().get(key, PersistentDataType.STRING);
        return type.getId().equals(value);
    }
}
