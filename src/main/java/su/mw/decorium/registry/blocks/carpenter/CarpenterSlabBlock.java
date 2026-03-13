package su.mw.decorium.registry.blocks.carpenter;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.enums.SlabType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.state.StateManager;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import su.mw.decorium.utils.CarpenterUtils;

import java.util.List;

public final class CarpenterSlabBlock extends SlabBlock implements Carpenter {
    public CarpenterSlabBlock(Settings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState().with(CarpenterUtils.PROPERTY, 0).with(TYPE, SlabType.BOTTOM).with(WATERLOGGED, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(CarpenterUtils.PROPERTY);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        return CarpenterUtils.onUse(state, world, pos, player, hand);
    }

    @Override
    public List<ItemStack> getDroppedStacks(BlockState state, LootContextParameterSet.Builder builder) {
        return CarpenterUtils.getDroppedStacks(state, builder);
    }
}
