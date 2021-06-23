package com.dfsek.terra.api.config;

import com.dfsek.terra.api.registry.Registry;
import com.dfsek.terra.api.world.TerraWorld;
import com.dfsek.terra.api.world.biome.generation.BiomeProvider;

import java.util.Set;

public interface WorldConfig {
    @SuppressWarnings("unchecked")
    <T> Registry<T> getRegistry(Class<T> clazz);

    TerraWorld getWorld();

    SamplerCache getSamplerCache();

    Set<UserDefinedCarver> getCarvers();

    BiomeProvider getProvider();
}