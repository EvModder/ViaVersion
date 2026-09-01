/*
 * This file is part of ViaVersion - https://github.com/ViaVersion/ViaVersion
 * Copyright (C) 2016-2026 ViaVersion and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.viaversion.viaversion.protocols.v1_21_11to26_1;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.protocol.packet.PacketWrapperImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

final class Protocol1_21_11To26_1Test {

    @Test
    void writesOverworldAndEndClockUpdates() {
        final PacketWrapper wrapper = new PacketWrapperImpl(0, null, null);

        Protocol1_21_11To26_1.writeLegacyClockUpdates(wrapper, 1234L, true);

        assertEquals(2, wrapper.get(Types.VAR_INT, 0));
        assertEquals(0, wrapper.get(Types.VAR_INT, 1));
        assertEquals(1, wrapper.get(Types.VAR_INT, 2));
        assertEquals(1234L, wrapper.get(Types.VAR_LONG, 0));
        assertEquals(1234L, wrapper.get(Types.VAR_LONG, 1));
        assertEquals(1F, wrapper.get(Types.FLOAT, 1));
        assertEquals(1F, wrapper.get(Types.FLOAT, 3));
    }

    @Test
    void assignsLegacyDefaultClocksByDimension() {
        final CompoundTag overworld = new CompoundTag();
        final CompoundTag overworldCaves = new CompoundTag();
        final CompoundTag end = new CompoundTag();
        final CompoundTag nether = new CompoundTag();

        Protocol1_21_11To26_1.addLegacyDefaultClock("minecraft:overworld", overworld);
        Protocol1_21_11To26_1.addLegacyDefaultClock("minecraft:overworld_caves", overworldCaves);
        Protocol1_21_11To26_1.addLegacyDefaultClock("minecraft:the_end", end);
        Protocol1_21_11To26_1.addLegacyDefaultClock("minecraft:the_nether", nether);

        assertEquals("minecraft:overworld", overworld.getString("default_clock"));
        assertEquals("minecraft:overworld", overworldCaves.getString("default_clock"));
        assertEquals("minecraft:the_end", end.getString("default_clock"));
        assertFalse(nether.contains("default_clock"));
    }
}
