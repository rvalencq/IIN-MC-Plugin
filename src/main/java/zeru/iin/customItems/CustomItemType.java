package zeru.iin.customItems;

import org.bukkit.ChatColor;
import org.bukkit.Material;

public enum CustomItemType {
    MACE_3X3("mace3x3", Material.NETHERITE_PICKAXE, ChatColor.LIGHT_PURPLE + "Mace 3x3");

    private final String id;
    private final Material material;
    private final String displayName;

    CustomItemType(String id, Material material, String displayName) {
        this.id = id;
        this.material = material;
        this.displayName = displayName;
    }

    public String getId() {
        return id;
    }

    public Material getMaterial() {
        return material;
    }

    public String getDisplayName() {
        return displayName;
    }
}
