package zeru.iin.customItems;

import org.bukkit.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionType;
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
        // registerMinerPotion();
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

    private void registerMinerPotion() {
        ItemStack potion = creator.createItem(CustomItemType.MINER_POTION);

        ItemStack strengthPotion = new ItemStack(Material.POTION);
        PotionMeta strengthPotionItemMeta = (PotionMeta) strengthPotion.getItemMeta();
        strengthPotionItemMeta.setBasePotionType(PotionType.LONG_STRENGTH);
        strengthPotion.setItemMeta(strengthPotionItemMeta);

        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(plugin, "minerPotion"), potion);
        recipe.shape("ABA", "CDE", "AFA");
        recipe.setIngredient('A', Material.NETHER_WART);
        recipe.setIngredient('B', Material.DIAMOND_BLOCK);
        recipe.setIngredient('C', Material.IRON_BLOCK);
        recipe.setIngredient('D', new RecipeChoice.ExactChoice(strengthPotion));
        recipe.setIngredient('E', Material.GOLD_BLOCK);
        recipe.setIngredient('F', Material.COPPER_BLOCK);

        Bukkit.addRecipe(recipe);
    }

}
