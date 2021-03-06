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

package fr.quatrevieux.araknemu.game.listener.player.inventory;

import fr.quatrevieux.araknemu.core.di.ContainerException;
import fr.quatrevieux.araknemu.data.living.entity.player.PlayerItem;
import fr.quatrevieux.araknemu.data.living.repository.player.PlayerItemRepository;
import fr.quatrevieux.araknemu.game.GameBaseCase;
import fr.quatrevieux.araknemu.game.item.inventory.event.ObjectDeleted;
import fr.quatrevieux.araknemu.game.player.inventory.InventoryEntry;
import fr.quatrevieux.araknemu.game.item.inventory.ItemEntry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SaveDeletedItemTest extends GameBaseCase {
    private SaveDeletedItem listener;


    @Override
    @BeforeEach
    public void setUp() throws Exception {
        super.setUp();

        dataSet.use(PlayerItem.class);
        listener = new SaveDeletedItem(
            container.get(PlayerItemRepository.class)
        );
    }

    @Test
    void onBadEntry() {
        listener.on(
            new ObjectDeleted(Mockito.mock(ItemEntry.class))
        );
    }

    @Test
    void onObjectDeleted() throws ContainerException {
        InventoryEntry entry = new InventoryEntry(
            null,
            new PlayerItem(1, 1, 284, new ArrayList<>(), 5, -1),
            null
        );

        container.get(PlayerItemRepository.class).add(entry.entity());

        listener.on(
            new ObjectDeleted(entry)
        );

        assertFalse(container.get(PlayerItemRepository.class).has(entry.entity()));
    }
}