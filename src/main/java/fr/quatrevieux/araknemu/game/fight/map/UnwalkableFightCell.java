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

package fr.quatrevieux.araknemu.game.fight.map;

import fr.quatrevieux.araknemu.data.world.entity.environment.MapTemplate;
import fr.quatrevieux.araknemu.game.fight.exception.FightMapException;
import fr.quatrevieux.araknemu.game.fight.fighter.Fighter;

import java.util.Optional;

/**
 * Non walkable fight cell
 */
final public class UnwalkableFightCell implements FightCell {
    final private FightMap map;
    final private MapTemplate.Cell template;
    final private int id;

    public UnwalkableFightCell(FightMap map, MapTemplate.Cell template, int id) {
        this.map = map;
        this.template = template;
        this.id = id;
    }

    @Override
    public FightMap map() {
        return map;
    }

    @Override
    public int id() {
        return id;
    }

    @Override
    public boolean walkable() {
        return false;
    }

    @Override
    public boolean walkableIgnoreFighter() {
        return false;
    }

    @Override
    public boolean sightBlocking() {
        return !template.lineOfSight();
    }

    @Override
    public Optional<Fighter> fighter() {
        return Optional.empty();
    }

    @Override
    public void set(Fighter fighter) {
        throw new FightMapException("Cannot add fighter on unwalkable cell");
    }

    @Override
    public void removeFighter() {
        throw new FightMapException("Cannot remove fighter on unwalkable cell");
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof UnwalkableFightCell && equals((FightCell) obj);
    }
}
