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

package com.dfsek.terra.commands.profiler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dfsek.terra.api.Platform;
import com.dfsek.terra.api.command.CommandTemplate;
import com.dfsek.terra.api.command.annotation.Command;
import com.dfsek.terra.api.command.annotation.type.DebugCommand;
import com.dfsek.terra.api.entity.CommandSender;
import com.dfsek.terra.api.inject.annotations.Inject;


@Command
@DebugCommand
public class ProfileQueryCommand implements CommandTemplate {
    private static final Logger logger = LoggerFactory.getLogger(ProfileQueryCommand.class);
    
    @Inject
    private Platform platform;
    
    @Override
    public void execute(CommandSender sender) {
        StringBuilder data = new StringBuilder("Terra Profiler data dump: \n");
        platform.getProfiler().getTimings().forEach((id, timings) -> data.append(id).append(": ").append(timings.toString()).append('\n'));
        logger.info(data.toString());
        sender.sendMessage("Profiler data dumped to console.");
    }
}
