package com.jsorrell.skyblock.helpers;

import carpet.CarpetSettings;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.mob.PiglinBruteEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.AbstractRandom;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.SpawnHelper;

public class PiglinBruteSpawnPredicate implements SpawnRestriction.SpawnPredicate<PiglinBruteEntity> {
  @Override
  public boolean test(EntityType<PiglinBruteEntity> type, ServerWorldAccess world, SpawnReason spawnReason, BlockPos pos, AbstractRandom random) {
    // Conditionally implement registering SpawnRestriction.Location.ON_GROUND
    if (CarpetSettings.piglinsSpawningInBastions) {
      BlockPos underBlockPos = pos.down();
      BlockState underBlock = world.getBlockState(underBlockPos);
      if (!underBlock.allowsSpawning(world, underBlockPos, type)) {
        return false;
      }
      BlockPos aboveBlockPos = pos.up();
      return SpawnHelper.isClearForSpawn(world, pos, world.getBlockState(pos), world.getFluidState(pos), type) &&
        SpawnHelper.isClearForSpawn(world, aboveBlockPos, world.getBlockState(aboveBlockPos), world.getFluidState(aboveBlockPos), type) &&
        PiglinBruteEntity.canSpawnInDark(type, world, spawnReason, pos, random);
    }

    return true;
  }
}
