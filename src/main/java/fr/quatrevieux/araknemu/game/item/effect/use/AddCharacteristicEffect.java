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

package fr.quatrevieux.araknemu.game.item.effect.use;

import fr.quatrevieux.araknemu.data.constant.Characteristic;
import fr.quatrevieux.araknemu.game.exploration.ExplorationPlayer;
import fr.quatrevieux.araknemu.game.item.effect.UseEffect;
import fr.quatrevieux.araknemu.network.game.out.info.Information;
import fr.quatrevieux.araknemu.util.RandomUtil;

/**
 * Effect handler for add characteristic
 */
final public class AddCharacteristicEffect implements UseEffectHandler {
    final private Characteristic characteristic;

    final private RandomUtil random = new RandomUtil();

    public AddCharacteristicEffect(Characteristic characteristic) {
        this.characteristic = characteristic;
    }

    @Override
    public void apply(UseEffect effect, ExplorationPlayer caster) {
        int value = random.rand(effect.arguments());

        caster.player().properties().characteristics().base().add(characteristic, value);

        Information info = Information.characteristicBoosted(characteristic, value);

        if (info != null) {
            caster.send(Information.characteristicBoosted(characteristic, value));
        }
    }

    @Override
    public boolean check(UseEffect effect, ExplorationPlayer caster) {
        return true;
    }

    @Override
    public boolean checkTarget(UseEffect effect, ExplorationPlayer caster, ExplorationPlayer target, int cell) {
        return false;
    }
}
