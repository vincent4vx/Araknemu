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

package fr.quatrevieux.araknemu.game.spell.effect;

import fr.quatrevieux.araknemu.data.value.SpellTemplateEffect;
import fr.quatrevieux.araknemu.game.spell.effect.area.SpellEffectArea;
import fr.quatrevieux.araknemu.game.spell.effect.target.EffectTarget;

/**
 * Adapt {@link SpellTemplateEffect}
 */
final public class SpellTemplateEffectAdapter implements SpellEffect {
    final private SpellTemplateEffect effect;
    final private SpellEffectArea area;
    final private EffectTarget target;

    public SpellTemplateEffectAdapter(SpellTemplateEffect effect, SpellEffectArea area, EffectTarget target) {
        this.effect = effect;
        this.area = area;
        this.target = target;
    }

    @Override
    public int effect() {
        return effect.effect();
    }

    @Override
    public int min() {
        return effect.min();
    }

    @Override
    public int max() {
        return effect.max();
    }

    @Override
    public int special() {
        return effect.special();
    }

    @Override
    public int duration() {
        return effect.duration();
    }

    @Override
    public int probability() {
        return effect.probability();
    }

    @Override
    public String text() {
        return effect.text();
    }

    @Override
    public SpellEffectArea area() {
        return area;
    }

    @Override
    public EffectTarget target() {
        return target;
    }
}
