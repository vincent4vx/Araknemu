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

package fr.quatrevieux.araknemu.game.monster.group.generator;

import fr.quatrevieux.araknemu.data.world.entity.monster.MonsterGroupData;
import fr.quatrevieux.araknemu.game.monster.Monster;
import fr.quatrevieux.araknemu.game.monster.MonsterService;
import fr.quatrevieux.araknemu.util.RandomUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Generate a random monster group
 *
 * - A random group size will be choose
 * - For each monster, a random one is choose from group data
 */
final public class RandomMonsterListGenerator implements MonsterListGenerator {
    final private MonsterService service;
    final private RandomUtil random;

    final static private int[][] SIZE_PROBABILITIES = new int[][] {
        {50, 50},
        {33, 34, 33},
        {22, 26, 26, 26},
        {15, 20, 25, 25, 15},
        {10, 15, 20, 20, 20, 15},
        {9, 11, 15, 20, 20, 16, 9},
        {9, 11, 13, 17, 17, 13, 11, 9},
    };

    public RandomMonsterListGenerator(MonsterService service) {
        this.service = service;
        this.random = new RandomUtil();
    }

    @Override
    public List<Monster> generate(MonsterGroupData data) {
        final int size = groupSize(data.maxSize());

        List<Monster> monsters = new ArrayList<>(size);

        for (int i = size; i > 0; --i) {
            monsters.add(monster(data));
        }

        return monsters;
    }

    /**
     * Get a random group size from the maximal size
     */
    private int groupSize(int maxSize) {
        if (maxSize == 1) {
            return 1;
        }

        // size - 2 because size 0 do not exists, and size 1 has no randomization
        final int[] probabilities = SIZE_PROBABILITIES[maxSize - 2];

        int dice = random.rand(1, 100);

        for (int size = 1; size <= probabilities.length; ++size) {
            dice -= probabilities[size - 1];

            if (dice <= 0) {
                return size;
            }
        }

        throw new IllegalArgumentException("Invalid group size " + maxSize);
    }

    /**
     * Select a random monster from the group data, considering the rate
     */
    private Monster monster(MonsterGroupData data) {
        int value = random.integer(data.totalRate());

        for (MonsterGroupData.Monster monsterData : data.monsters()) {
            value -= monsterData.rate();

            if (value < 0) {
                return service.load(monsterData.id()).random(monsterData.level());
            }
        }

        throw new IllegalArgumentException("Invalid group monsters");
    }
}
