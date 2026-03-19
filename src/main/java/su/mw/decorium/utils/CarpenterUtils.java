package su.mw.decorium.utils;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.registry.Registries;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import su.mw.decorium.registry.blocks.carpenter.Carpenter;

import java.util.ArrayList;
import java.util.List;

public final class CarpenterUtils {
    public static final IntProperty PROPERTY = IntProperty.of("carpenter_texture", 0, getMaxBlockId());

    private static int getMaxBlockId() {
        return Registries.BLOCK.stream().mapToInt(Registries.BLOCK::getRawId).max().orElse(0);
    }

    public static ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        if (stack.isEmpty()) return ActionResult.PASS;

        Block block = Block.getBlockFromItem(stack.getItem());
        if (block == Blocks.AIR) return ActionResult.PASS;

        if (block instanceof Carpenter || !block.getDefaultState().isOpaqueFullCube(world, null)) return ActionResult.PASS;

        int newTextureId = Registries.BLOCK.getRawId(block);
        int currentTextureId = state.get(PROPERTY);

        if (newTextureId == currentTextureId) return ActionResult.PASS;

        if (!player.isCreative()) {
            stack.decrement(1);
        }

        world.setBlockState(pos, state.with(PROPERTY, newTextureId));
        return ActionResult.SUCCESS;
    }

    public static List<ItemStack> getDroppedStacks(BlockState state, LootContextParameterSet.Builder builder) {
        List<ItemStack> drops = new ArrayList<>();
        drops.add(new ItemStack(state.getBlock().asItem()));

        int textureId = state.get(PROPERTY);
        if (textureId != 0) {
            Block materialBlock = Registries.BLOCK.get(textureId);
            if (materialBlock != Blocks.AIR) {
                ItemStack materialStack = new ItemStack(materialBlock);
                if (!materialStack.isEmpty()) {
                    drops.add(materialStack);
                }
            }
        }
        return drops;
    }
}
