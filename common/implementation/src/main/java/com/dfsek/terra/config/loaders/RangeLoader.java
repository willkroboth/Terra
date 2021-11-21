/*
 * This file is part of Terra.
 *
 * Terra is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Terra is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Terra.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.dfsek.terra.config.loaders;

import com.dfsek.tectonic.exception.LoadException;
import com.dfsek.tectonic.loading.ConfigLoader;
import com.dfsek.tectonic.loading.TypeLoader;

import java.lang.reflect.AnnotatedType;
import java.util.Map;

import com.dfsek.terra.api.util.ConstantRange;
import com.dfsek.terra.api.util.Range;


@SuppressWarnings("unchecked")
public class RangeLoader implements TypeLoader<Range> {
    @Override
    public Range load(AnnotatedType type, Object o, ConfigLoader configLoader) throws LoadException {
        if(o instanceof Map) {
            Map<String, Integer> map = (Map<String, Integer>) o;
            return new ConstantRange(map.get("min"), map.get("max"));
        } else {
            int h = configLoader.loadType(Integer.class, o);
            return new ConstantRange(h, h + 1);
        }
    }
}
