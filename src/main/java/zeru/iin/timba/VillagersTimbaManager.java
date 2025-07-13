package zeru.iin.timba;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import zeru.iin.customItems.CustomItemCreator;
import zeru.iin.customItems.CustomItemType;

import java.util.ArrayList;
import java.util.List;

public class VillagersTimbaManager {
    private final CustomItemCreator creator;
    public VillagersTimbaManager(CustomItemCreator creator) {
        this.creator = creator;
    }

    private MerchantRecipe createTrade(ItemStack result, List<ItemStack> ingredients) {
        MerchantRecipe recipe = new MerchantRecipe(result, Integer.MAX_VALUE);
        for (ItemStack ingredient : ingredients) {
            recipe.addIngredient(ingredient);
        }
        recipe.setIgnoreDiscounts(true);
        recipe.setExperienceReward(false);
        recipe.setPriceMultiplier(0);
        return recipe;
    }

    private List<MerchantRecipe> getGetterTrades() {
        List<MerchantRecipe> trades = new ArrayList<>();
        ItemStack timbaCoin = creator.createItem(CustomItemType.TIMBA_COIN);

        timbaCoin.setAmount(3);
        trades.add(createTrade(
                timbaCoin,
                List.of(
                        new ItemStack(Material.NETHERITE_INGOT, 1)
                )
        ));

        timbaCoin.setAmount(1);
        trades.add(createTrade(
                timbaCoin,
                List.of(new ItemStack(Material.DIAMOND, 3))
        ));

        return trades;
    }

    public void villagerGetterTimba(Player player) {
        List<MerchantRecipe> recipes = getGetterTrades();

        Villager villager = (Villager) player.getWorld().spawnEntity(player.getLocation(), EntityType.VILLAGER);
        villager.setProfession(Villager.Profession.TOOLSMITH);
        villager.setCustomName("Consigue Tus Monedas Aca");
        villager.setCustomNameVisible(true);
        villager.setAI(false);
        villager.setInvulnerable(true);
        villager.setVillagerLevel(5);
        villager.setRecipes(recipes);
    }

    private List<MerchantRecipe> getSellerTrades() {
        List<MerchantRecipe> trades = new ArrayList<>();
        ItemStack timbaCoin = creator.createItem(CustomItemType.TIMBA_COIN);

        timbaCoin.setAmount(3);
        trades.add(createTrade(
                new ItemStack(Material.NETHERITE_CHESTPLATE),
                List.of(
                        timbaCoin)
        ));

        timbaCoin.setAmount(1);
        trades.add(createTrade(
                new ItemStack(Material.NETHERITE_HELMET),
                List.of(
                        timbaCoin)
        ));

        return trades;
    }

    public void villagerSellerTimba(Player player) {
        List<MerchantRecipe> recipes = getSellerTrades();

        Villager villager = (Villager) player.getWorld().spawnEntity(player.getLocation(), EntityType.VILLAGER);
        villager.setProfession(Villager.Profession.TOOLSMITH);
        villager.setCustomName("Intercambia Tus Monedas Aca");
        villager.setCustomNameVisible(true);
        villager.setAI(false);
        villager.setInvulnerable(true);
        villager.setVillagerLevel(5);
        villager.setRecipes(recipes);
    }

}
