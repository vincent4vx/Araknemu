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
 * Copyright (c) 2017-2021 Vincent Quatrevieux
 */

package fr.quatrevieux.araknemu.game.fight.module;

import fr.quatrevieux.araknemu.core.event.Listener;
import fr.quatrevieux.araknemu.game.fight.castable.effect.EffectsHandler;
import fr.quatrevieux.araknemu.game.fight.castable.effect.handler.invocation.InvocationHandler;
import fr.quatrevieux.araknemu.game.monster.MonsterService;

/**
 * Module for handle Raulebaque spell
 *
 * This spell will reset all fighters position to the initial one
 */
final public class InvocationModule implements FightModule {
    final private MonsterService service;

    public InvocationModule(MonsterService service) {
        this.service = service;
    }

    @Override
    public void effects(EffectsHandler handler) {
        handler.register(181, new InvocationHandler(service));
    }

    @Override
    public Listener[] listeners() {
        return new Listener[0];
    }
}
