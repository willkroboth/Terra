package com.dfsek.terra.biome.pipeline;

import com.dfsek.terra.api.math.vector.Vector2;
import com.dfsek.terra.api.world.biome.TerraBiome;
import com.dfsek.terra.biome.pipeline.expand.BiomeExpander;
import com.dfsek.terra.biome.pipeline.mutator.BiomeMutator;
import com.dfsek.terra.biome.pipeline.source.BiomeSource;

public class TerraBiomeHolder implements BiomeHolder {
    private final Vector2 origin;
    private final int width;
    private TerraBiome[][] biomes;

    public TerraBiomeHolder(int width, Vector2 origin) {
        this.width = width;
        biomes = new TerraBiome[width][width];
        this.origin = origin;
    }

    private TerraBiomeHolder(TerraBiome[][] biomes, Vector2 origin, int width) {
        this.biomes = biomes;
        this.origin = origin;
        this.width = width;
    }

    @Override
    public BiomeHolder expand(BiomeExpander expander) {
        TerraBiome[][] old = biomes;
        int newWidth = width * 2 - 1;

        biomes = new TerraBiome[newWidth][newWidth];

        System.out.println(biomes.length);

        for(int x = 0; x < width; x++) {
            for(int z = 0; z < width; z++) {
                biomes[x * 2][z * 2] = old[x][z];
                if(z != width - 1)
                    biomes[x * 2][z * 2 + 1] = expander.getBetween(x + origin.getX(), z + 1 + origin.getZ(), old[x][z], old[x][z + 1]);
                if(x != width - 1)
                    biomes[x * 2 + 1][z * 2] = expander.getBetween(x + 1 + origin.getX(), z + origin.getZ(), old[x][z], old[x + 1][z]);
                if(x != width - 1 && z != width - 1)
                    biomes[x * 2 + 1][z * 2 + 1] = expander.getBetween(x + 1 + origin.getX(), z + 1 + origin.getZ(), old[x][z], old[x + 1][z + 1], old[x][z + 1], old[x + 1][z]);
            }
        }
        return new TerraBiomeHolder(biomes, origin.setX(origin.getX() * 2 - 1).setZ(origin.getZ() * 2 - 1), newWidth);
    }

    @Override
    public void mutate(BiomeMutator mutator) {
        for(int x = 0; x < width; x++) {
            for(int z = 0; z < width; z++) {
                BiomeMutator.ViewPoint viewPoint = new BiomeMutator.ViewPoint(new TerraBiome[][] {
                        {getBiomeRaw(x - 1, z + 1), getBiomeRaw(x, z + 1), getBiomeRaw(x + 1, z + 1)},
                        {getBiomeRaw(x - 1, z), getBiomeRaw(x, z), getBiomeRaw(x + 1, z)},
                        {getBiomeRaw(x - 1, z - 1), getBiomeRaw(x, z - 1), getBiomeRaw(x + 1, z - 1)}
                });
                biomes[x][z] = mutator.mutate(viewPoint, x + origin.getX(), z + origin.getZ());
            }
        }
    }

    @Override
    public void fill(BiomeSource source) {
        for(int x = 0; x < width; x++) {
            for(int z = 0; z < width; z++) {
                biomes[x][z] = source.getBiome(origin.getX() + x, origin.getZ() + z);
            }
        }
    }

    private TerraBiome getBiomeRaw(int x, int z) {
        if(x >= width || z >= width || x < 0 || z < 0) return null;
        return biomes[x][z];
    }

    @Override
    public TerraBiome getBiome(int x, int z) {
        if(x >= width || z >= width || x < 0 || z < 0) return null;
        return biomes[x][z];
    }
}
