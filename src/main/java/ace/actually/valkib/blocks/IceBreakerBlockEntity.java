package ace.actually.valkib.blocks;

import ace.actually.valkib.ValkIB;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.valkyrienskies.core.api.ships.ServerShip;
import org.valkyrienskies.mod.common.VSGameUtilsKt;

public class IceBreakerBlockEntity extends BlockEntity {
    public IceBreakerBlockEntity(BlockPos pos, BlockState state) {
        super(ValkIB.MY_BE.get(), pos, state);
    }

    public static void tick(Level world, BlockPos pos, BlockState state, IceBreakerBlockEntity be)
    {
        if(world instanceof ServerLevel serverWorld)
        {
            if(VSGameUtilsKt.isBlockInShipyard(serverWorld,pos))
            {
                ServerShip ship = VSGameUtilsKt.getShipManagingPos(serverWorld,pos);
                if(ship!=null)
                {
                    Vec3 middle = VSGameUtilsKt.toWorldCoordinates(ship, Vec3.atCenterOf(pos));

                    BlockPos block = new BlockPos((int) middle.x, (int) middle.y, (int) middle.z);
                    for (int i = -1; i < 2; i++) {
                        for (int j = -1; j < 2; j++) {
                            for (int k = -1; k < 2; k++) {
                                if(serverWorld.getBlockState(block.offset(i,j,k)).is(ValkIB.BREAKABLES))
                                {
                                    serverWorld.destroyBlock(block.offset(i,j,k),true);
                                    serverWorld.setBlockAndUpdate(block.offset(i,j,k), Blocks.WATER.defaultBlockState());
                                }
                            }
                        }
                    }
                }

            }
        }

    }
}
