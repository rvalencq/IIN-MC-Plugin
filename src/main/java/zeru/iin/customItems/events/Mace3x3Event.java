package zeru.iin.customItems.events;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import zeru.iin.IIN;
import zeru.iin.customItems.CustomItemCreator;
import zeru.iin.customItems.CustomItemType;

import java.util.HashMap;
import java.util.Random;
import java.util.Set;

public class Mace3x3Event implements Listener {
    private final CustomItemCreator creator;
    private final IIN plugin;
    public Mace3x3Event(IIN plugin, CustomItemCreator creator) {
        this.creator = creator;
        this.plugin = plugin;
    }

    // Craft
    @EventHandler
    public void onPrepareCraft(PrepareItemCraftEvent event) {
        CraftingInventory inv = event.getInventory();
        ItemStack[] matrix = inv.getMatrix();

        if (matrix.length != 9) return;
        if (matrix[4] == null || matrix[4].getType() != Material.NETHERITE_PICKAXE) {
            return;
        }
        boolean recipe =
                isRecipe(matrix[0], Material.GOLD_BLOCK, 32) ||
                        isRecipe(matrix[1], Material.CRYING_OBSIDIAN, 64) ||
                        isRecipe(matrix[2], Material.GOLD_BLOCK, 32) ||
                        isRecipe(matrix[3], Material.DEEPSLATE_EMERALD_ORE, 3) ||
                        isRecipe(matrix[4], Material.NETHERITE_PICKAXE, 1) ||
                        isRecipe(matrix[5], Material.DEEPSLATE_EMERALD_ORE, 3) ||
                        isRecipe(matrix[6], Material.OXIDIZED_COPPER, 32) ||
                        isRecipe(matrix[7], Material.NETHERITE_BLOCK, 1) ||
                        isRecipe(matrix[8], Material.OXIDIZED_COPPER, 32);

        boolean correct =
                is(matrix[0], Material.GOLD_BLOCK, 32) &&
                        is(matrix[1], Material.CRYING_OBSIDIAN, 64) &&
                        is(matrix[2], Material.GOLD_BLOCK, 32) &&
                        is(matrix[3], Material.DEEPSLATE_EMERALD_ORE, 3) &&
                        is(matrix[4], Material.NETHERITE_PICKAXE, 1) &&
                        is(matrix[5], Material.DEEPSLATE_EMERALD_ORE, 3) &&
                        is(matrix[6], Material.OXIDIZED_COPPER, 32) &&
                        is(matrix[7], Material.NETHERITE_BLOCK, 1) &&
                        is(matrix[8], Material.OXIDIZED_COPPER, 32);

        if (recipe) {
            inv.setResult(null);
        } else if (correct) {
            inv.setResult(creator.createItem(CustomItemType.MACE_3X3));
        }
    }

    @EventHandler
    public void onCraft(CraftItemEvent event) {
        CraftingInventory inv = event.getInventory();
        ItemStack[] matrix = inv.getMatrix();

        if (matrix.length != 9) return;
        if (matrix[4] == null || matrix[4].getType() != Material.NETHERITE_PICKAXE) {
            return;
        }

        boolean correct =
                is(matrix[0], Material.GOLD_BLOCK, 32) &&
                        is(matrix[1], Material.CRYING_OBSIDIAN, 64) &&
                        is(matrix[2], Material.GOLD_BLOCK, 32) &&
                        is(matrix[3], Material.DEEPSLATE_EMERALD_ORE, 3) &&
                        is(matrix[4], Material.NETHERITE_PICKAXE, 1) &&
                        is(matrix[5], Material.DEEPSLATE_EMERALD_ORE, 3) &&
                        is(matrix[6], Material.OXIDIZED_COPPER, 32) &&
                        is(matrix[7], Material.NETHERITE_BLOCK, 1) &&
                        is(matrix[8], Material.OXIDIZED_COPPER, 32);

        if (!correct) return;

        new BukkitRunnable() {
            @Override
            public void run() {
                ItemStack[] updatedMatrix = inv.getMatrix();
                for (int i = 0; i < 9; i++) {
                    ItemStack item = updatedMatrix[i];
                    if (item == null) continue;

                    int amountToRemove = switch (i) {
                        case 0, 2, 6, 8 -> 32;
                        case 1 -> 64;
                        case 3, 5 -> 3;
                        case 4, 7 -> 1;
                        default -> 0;
                    };
                    amountToRemove--; // Si, minecraft resta ya uno por el crafteo, chupala minecraft
                    item.setAmount(item.getAmount() - amountToRemove);
                }
                inv.setMatrix(updatedMatrix);
            }
        }.runTask(plugin);
    }

    private boolean is(ItemStack item, Material expected, int amount) {
        return item != null && item.getType() == expected && item.getAmount() >= amount;
    }

    private boolean isRecipe(ItemStack item, Material expected, int requiredAmount) {
        return item.getType() == expected && item.getAmount() < requiredAmount;
    }

    /* Sin Uso (Quien sabe cuando lo complete)
    private static final Map<Material, Float> HARDNESS = Map.ofEntries(
            Map.entry(Material.STONE, 1.5f),
            Map.entry(Material.DIRT, 0.5f),
            Map.entry(Material.NETHERRACK, 0.4f),
            Map.entry(Material.OBSIDIAN, 50f),
            Map.entry(Material.DEEPSLATE, 3f),
            Map.entry(Material.GRASS_BLOCK, 0.6f),
            Map.entry(Material.SAND, 0.5f)
    );
    */

    // Mine 3x3 Logic
    private static final Set<Material> unbreakableBlocks = Set.of(
            Material.BEDROCK,
            Material.BARRIER,
            Material.END_PORTAL,
            Material.END_PORTAL_FRAME,
            Material.COMMAND_BLOCK,
            Material.STRUCTURE_BLOCK,
            Material.JIGSAW,
            Material.NETHER_PORTAL
    );

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (player.getGameMode() != GameMode.SURVIVAL) return;

        ItemStack inMainHand = player.getInventory().getItemInMainHand();

        if (creator.isCustomItem(inMainHand, CustomItemType.MACE_3X3)) {
            Block centralBlock = event.getBlock();
            Material centralBlockType = centralBlock.getType();
            mine3x3Area(player, centralBlock, inMainHand, centralBlockType);
        }
    }

    public void mine3x3Area(Player player, Block centralBlock, ItemStack tool, Material enableType) {
        BlockFace facing = getFacing(player);
        boolean isVertical = isLookingVertical(player);
        Block center = centralBlock;
        int minedBlocks = 0;

        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                for (int dz = -1; dz <= 1; dz++) {
                    Block target = isVertical
                            ? center.getRelative(dx, 0, dz)
                            : (facing == BlockFace.EAST || facing == BlockFace.WEST)
                            ? center.getRelative(0, dy, dz)
                            : center.getRelative(dx, dy, 0);

                    if (target.equals(center)) continue;
                    if (target.getType() != enableType) continue;
                    if (!canBreak(tool, target)) continue;

                    boolean success = target.breakNaturally(tool);
                    if (success) {
                        minedBlocks++;
                        try {
                            player.incrementStatistic(Statistic.MINE_BLOCK, target.getType());
                        } catch (IllegalArgumentException ignored) {}
                    }
                }
            }
        }

        useToolPerBlock(player, tool, minedBlocks);
    }


    public BlockFace getFacing(Player player) {
        float yaw = player.getLocation().getYaw();
        yaw = (yaw % 360 + 360) % 360;

        if (yaw >= 45 && yaw < 135) return BlockFace.EAST;
        if (yaw >= 135 && yaw < 225) return BlockFace.SOUTH;
        if (yaw >= 225 && yaw < 315) return BlockFace.WEST;
        return BlockFace.NORTH;
    }

    public boolean isLookingVertical(Player player) {
        float pitch = player.getLocation().getPitch();
        return pitch < -45 || pitch > 45;
    }

    public boolean canBreak(ItemStack tool, Block target) {
        Material type = target.getType();

        if (type.isAir() || !type.isBlock()) return false;
        if (unbreakableBlocks.contains(type)) return false;

        return tool.getType().getMaxDurability() > 0;
    }

    public void useToolPerBlock(Player player, ItemStack tool, int minedBlocks) {
        if (tool == null || tool.getType().getMaxDurability() <= 0) return;
        short newDamage = tool.getDurability();

        int levelUnbreaking = tool.getEnchantmentLevel(Enchantment.UNBREAKING);
        Random random = new Random();

        for (int i = 0; i < minedBlocks; i++) {
            boolean lostDurability = (levelUnbreaking == 0) || (random.nextInt(levelUnbreaking + 1) == 0);
            if (lostDurability) {
                newDamage++;
            }
        }

        tool.setDurability(newDamage);

        if (newDamage >= tool.getType().getMaxDurability()) {
            player.getInventory().setItemInMainHand(null);
            player.getWorld().playSound(player.getLocation(), "entity.item.break", 1.0f, 1.0f);
        }
    }
}
