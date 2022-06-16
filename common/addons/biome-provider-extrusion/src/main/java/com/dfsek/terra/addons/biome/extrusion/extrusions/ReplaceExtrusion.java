package com.dfsek.terra.addons.biome.extrusion.extrusions;

import com.dfsek.terra.addons.biome.extrusion.api.Extrusion;
import com.dfsek.terra.addons.biome.extrusion.api.ReplaceableBiome;
import com.dfsek.terra.api.noise.NoiseSampler;
import com.dfsek.terra.api.util.Range;
import com.dfsek.terra.api.util.collection.ProbabilityCollection;
import com.dfsek.terra.api.world.biome.Biome;

import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Collectors;


/**
 * Sets biomes at locations based on a sampler.
 */
public class ReplaceExtrusion implements Extrusion {
    private final NoiseSampler sampler;
    
    private final Range range;
    
    private final ProbabilityCollection<ReplaceableBiome> biomes;
    
    private final String tag;
    
    public ReplaceExtrusion(NoiseSampler sampler, Range range, ProbabilityCollection<ReplaceableBiome> biomes, String tag) {
        this.sampler = sampler;
        this.range = range;
        this.biomes = biomes;
        this.tag = tag;
    }
    
    @Override
    public Biome extrude(Biome original, int x, int y, int z, long seed) {
        if(!original.getTags().contains(tag)) return original;
        return range.ifInRange(y, () -> biomes.get(sampler, x, y, z, seed).get(original), original);
    }
    
    @Override
    public Collection<Biome> getBiomes() {
        return biomes
                .getContents()
                .stream()
                .filter(Predicate.not(ReplaceableBiome::isSelf))
                .map(ReplaceableBiome::get)
                .collect(Collectors.toSet());
    }
}