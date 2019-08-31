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

package fr.quatrevieux.araknemu.network.game.out.game;

import fr.quatrevieux.araknemu.game.world.creature.Sprite;

import java.util.Collection;

/**
 * Add sprites to the current map
 *
 * https://github.com/Emudofus/Dofus/blob/1.29/dofus/aks/Game.as#L434
 */
final public class AddSprites {
    private Collection<? extends Sprite> sprites;

    public AddSprites(Collection<? extends Sprite> sprites) {
        this.sprites = sprites;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(64 * sprites.size());

        sb.append("GM");

        for(Sprite sprite : sprites){
            sb.append("|+").append(sprite);
        }

        return sb.toString();
    }
}
