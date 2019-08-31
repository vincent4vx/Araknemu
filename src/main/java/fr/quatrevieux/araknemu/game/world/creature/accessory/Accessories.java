/*
 * This file is part of Araknemu.
 *
 * Araknemu is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Araknemu is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Araknemu.  If not, see <https://www.gnu.org/licenses/>.
 *
 * Copyright (c) 2017-2019 Vincent Quatrevieux
 */

package fr.quatrevieux.araknemu.game.world.creature.accessory;

import java.util.ArrayList;
import java.util.List;

/**
 * Player accessories
 */
public interface Accessories {
    /**
     * Get an accessory by its type
     */
    public Accessory get(AccessoryType type);

    /**
     * Get all accessories
     */
    default public List<Accessory> all() {
        ArrayList<Accessory> accessories = new ArrayList<>(AccessoryType.values().length);

        for (AccessoryType type : AccessoryType.values()) {
            accessories.add(get(type));
        }

        return accessories;
    }
}
