package com.dfsek.terra.population;

import com.dfsek.terra.TerraProfiler;
import com.dfsek.terra.biome.TerraBiomeGrid;
import com.dfsek.terra.biome.UserDefinedBiome;
import com.dfsek.terra.config.genconfig.BiomeConfig;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;
import org.polydev.gaea.biome.Biome;
import org.polydev.gaea.population.GaeaBlockPopulator;
import org.polydev.gaea.profiler.ProfileFuture;
import org.polydev.gaea.world.Flora;

import java.util.Random;

public class FloraPopulator extends GaeaBlockPopulator {
    @Override
    public void populate(@NotNull World world, @NotNull Random random, @NotNull Chunk chunk) {
        ProfileFuture flora = TerraProfiler.fromWorld(world).measure("FloraTime");
        for(int x = 0; x < 16; x++) {
            for(int z = 0; z < 16; z++) {
                UserDefinedBiome biome = (UserDefinedBiome) TerraBiomeGrid.fromWorld(world).getBiome((chunk.getX() << 4) + x, (chunk.getZ() << 4) + z);
                if(biome.getDecorator().getFloraChance() <= 0 || random.nextInt(100) > biome.getDecorator().getFloraChance())
                    continue;
                try {
                    BiomeConfig c = BiomeConfig.fromBiome(biome);
                    for(int i = 0; i < c.getFloraAttempts(); i++) {
                        Flora item = biome.getDecorator().getFlora().get(random);
                        Block highest = item.getHighestValidSpawnAt(chunk, x, z);
                        if(highest != null && c.getFloraHeights(item).isInRange(highest.getY())) item.plant(highest.getLocation());
                    }
                } catch(NullPointerException ignored) {}
            }
        }
        if(flora!=null) flora.complete();
    }
}
