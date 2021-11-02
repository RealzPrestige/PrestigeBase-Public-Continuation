package dev.prestige.base.utils;

import dev.prestige.base.PrestigeBase;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;

public class BlockUtil {
    public enum AirMode {
        NoAir,
        AirOnly,
        Ignored
    }

    public static List<BlockPos> getBlocksInRadius(double radius, AirMode airMode) {
        ArrayList<BlockPos> posList = new ArrayList<>();
        BlockPos pos = new BlockPos(Math.floor(PrestigeBase.mc.player.posX), Math.floor(PrestigeBase.mc.player.posY), Math.floor(PrestigeBase.mc.player.posZ));
        for (int x = pos.getX() - (int) radius; x <= pos.getX() + radius; ++x) {
            for (int y = pos.getY() - (int) radius; y < pos.getY() + radius; ++y) {
                for (int z = pos.getZ() - (int) radius; z <= pos.getZ() + radius; ++z) {
                    double distance = (pos.getX() - x) * (pos.getX() - x) + (pos.getZ() - z) * (pos.getZ() - z) + (pos.getY() - y) * (pos.getY() - y);
                    BlockPos position = new BlockPos(x, y, z);
                    if (distance < radius * radius) {
                        if (airMode.equals(AirMode.NoAir) && PrestigeBase.mc.world.getBlockState(position).getBlock().equals(Blocks.AIR))
                            continue;
                        if (airMode.equals(AirMode.AirOnly) && !PrestigeBase.mc.world.getBlockState(position).getBlock().equals(Blocks.AIR))
                            continue;
                        posList.add(position);
                    }
                }
            }
        }
        return posList;
    }

    public static void placeBlock(BlockPos blockPos, boolean packet) {
        for (EnumFacing enumFacing : EnumFacing.values()) {
            if (!(PrestigeBase.mc.world.getBlockState(blockPos.offset(enumFacing)).equals(Blocks.AIR))) {
                for (Entity entity : PrestigeBase.mc.world.loadedEntityList)
                    if (new AxisAlignedBB(blockPos).intersects(entity.getEntityBoundingBox()))
                        return;

                PrestigeBase.mc.player.connection.sendPacket(new CPacketEntityAction(PrestigeBase.mc.player, CPacketEntityAction.Action.START_SNEAKING));

                if (packet)
                    PrestigeBase.mc.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(blockPos.offset(enumFacing), enumFacing.getOpposite(), EnumHand.MAIN_HAND, 0, 0, 0));
                else
                    PrestigeBase.mc.playerController.processRightClickBlock(PrestigeBase.mc.player, PrestigeBase.mc.world, blockPos.offset(enumFacing), enumFacing.getOpposite(), new Vec3d(blockPos), EnumHand.MAIN_HAND);

                PrestigeBase.mc.player.connection.sendPacket(new CPacketEntityAction(PrestigeBase.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                return;
            }
        }
    }
}
