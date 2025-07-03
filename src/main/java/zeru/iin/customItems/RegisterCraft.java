package zeru.iin.customItems;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import zeru.iin.IIN;

public class RegisterCraft {
    private final IIN plugin;
    private final CustomItemCreator creator;
    public RegisterCraft(IIN plugin, CustomItemCreator creator) {
        this.plugin = plugin;
        this.creator = creator;
    }

    public void registerAll() {
        registerMace3x3();
    }

    private void registerMace3x3() {
        ItemStack result = creator.createItem(CustomItemType.MACE_3X3);

        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(plugin, "mace3x3"), result);
        recipe.shape("ABA", "CDC", "EFE");
        recipe.setIngredient('A', Material.GOLD_BLOCK);
        recipe.setIngredient('B', Material.CRYING_OBSIDIAN);
        recipe.setIngredient('C', Material.DEEPSLATE_EMERALD_ORE);
        recipe.setIngredient('D', Material.NETHERITE_PICKAXE);
        recipe.setIngredient('E', Material.OXIDIZED_COPPER);
        recipe.setIngredient('F', Material.NETHERITE_BLOCK);

        Bukkit.addRecipe(recipe);
    }
}
