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

package fr.quatrevieux.araknemu.game.spell.boost.spell;

import fr.quatrevieux.araknemu.data.value.Interval;
import fr.quatrevieux.araknemu.game.spell.SpellConstraints;
import fr.quatrevieux.araknemu.game.spell.boost.SpellModifiers;

/**
 * Spell constraints with modifiers
 */
final public class BoostedSpellConstraints implements SpellConstraints {
    final private SpellConstraints constraints;
    final private SpellModifiers modifiers;

    public BoostedSpellConstraints(SpellConstraints constraints, SpellModifiers modifiers) {
        this.constraints = constraints;
        this.modifiers = modifiers;
    }

    @Override
    public Interval range() {
        int boost = modifiers.range();

        if (boost == 0) {
            return constraints.range();
        }

        return new Interval(
            constraints.range().min(),
            constraints.range().max() + boost
        );
    }

    @Override
    public boolean lineLaunch() {
        return constraints.lineLaunch() && !modifiers.launchOutline();
    }

    @Override
    public boolean lineOfSight() {
        return constraints.lineOfSight() && !modifiers.lineOfSight();
    }

    @Override
    public boolean freeCell() {
        return constraints.freeCell();
    }

    @Override
    public int launchPerTurn() {
        return constraints.launchPerTurn() + modifiers.launchPerTurn();
    }

    @Override
    public int launchPerTarget() {
        return constraints.launchPerTarget() + modifiers.launchPerTarget();
    }

    @Override
    public int launchDelay() {
        int base = modifiers.hasFixedDelay()
            ? modifiers.fixedDelay()
            : constraints.launchDelay()
        ;

        return base - modifiers.delay();
    }

    @Override
    public int[] requiredStates() {
        return constraints.requiredStates();
    }

    @Override
    public int[] forbiddenStates() {
        return constraints.forbiddenStates();
    }
}
