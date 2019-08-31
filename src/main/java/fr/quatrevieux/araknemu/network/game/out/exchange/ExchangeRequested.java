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

package fr.quatrevieux.araknemu.network.game.out.exchange;

import fr.quatrevieux.araknemu.game.exploration.exchange.ExchangeType;
import fr.quatrevieux.araknemu.game.world.creature.Creature;

/**
 * Send the exchange request to the parties
 *
 * https://github.com/Emudofus/Dofus/blob/1.29/dofus/aks/Exchange.as#L160
 */
final public class ExchangeRequested {
    final private Creature initiator;
    final private Creature target;
    final private ExchangeType type;

    public ExchangeRequested(Creature initiator, Creature target, ExchangeType type) {
        this.initiator = initiator;
        this.target = target;
        this.type = type;
    }

    @Override
    public String toString() {
        return "ERK" + initiator.id() + "|" + target.id() + "|" + type.ordinal();
    }
}
