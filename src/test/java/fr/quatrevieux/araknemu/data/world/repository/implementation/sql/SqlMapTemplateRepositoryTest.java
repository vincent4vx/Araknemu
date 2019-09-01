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

package fr.quatrevieux.araknemu.data.world.repository.implementation.sql;

import fr.quatrevieux.araknemu.core.dbal.repository.EntityNotFoundException;
import fr.quatrevieux.araknemu.core.dbal.executor.ConnectionPoolExecutor;
import fr.quatrevieux.araknemu.data.value.Dimensions;
import fr.quatrevieux.araknemu.data.world.entity.environment.MapTemplate;
import fr.quatrevieux.araknemu.data.world.transformer.FightPlacesTransformer;
import fr.quatrevieux.araknemu.data.world.transformer.MapCellTransformer;
import fr.quatrevieux.araknemu.game.GameBaseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SqlMapTemplateRepositoryTest extends GameBaseCase {
    private SqlMapTemplateRepository repository;

    @Override
    @BeforeEach
    public void setUp() throws Exception {
        super.setUp();

        repository = new SqlMapTemplateRepository(
            new ConnectionPoolExecutor(app.database().get("game")),
            new MapCellTransformer(),
            new FightPlacesTransformer()
        );

        dataSet.pushMaps();
    }

    @Test
    void getById() {
        MapTemplate map = repository.get(10300);

        assertEquals(10300, map.id());
        assertEquals("662838776047515721434a62545f5478543541592532356f25324257716f6e28657d2a5d4471206273545f5e4a614a442c2c73432c35515a553b386f6525324274262f3a747b6b675440557f5260754d6a52343d433b52755e6e6c3b437d417e22514524216771304a6e7e553c32794646287f3e57544c48526a284e2158376b4a414039752c2a2f303d6a5e746f2e323031385e6f483e785929262435777c3141463f363a43784e73345f5e406d2e235d423248427767582c5e4a6a4d234e3f61506d32595e7a2c4a2532355a7f5b5c474e6f732532426062775824253235", map.key());
        assertEquals(new Dimensions(15, 17), map.dimensions());
        assertEquals("0802221747", map.date());
        assertEquals(479, map.cells().size());

        assertEquals(4, map.cells().get(308).movement());
        assertTrue(map.cells().get(308).lineOfSight());
    }

    @Test
    void getWithPlaces() {
        assertArrayEquals(
            repository.get(10340).fightPlaces(),
            new List[] {
                Arrays.asList(55, 83, 114, 127, 128, 170, 171, 183, 185, 198),
                Arrays.asList(48, 63, 75, 90, 92, 106, 121, 122, 137, 150)
            }
        );
    }

    @Test
    void getNotFound() {
        assertThrows(EntityNotFoundException.class, () -> repository.get(-1));
    }

    @Test
    void getByEntity() {
        MapTemplate map = repository.get(new MapTemplate(10300, null, null, null, null, null));

        assertEquals(10300, map.id());
        assertEquals(479, map.cells().size());
    }

    @Test
    void has() {
        assertFalse(repository.has(new MapTemplate(-1, null, null, null, null, null)));
        assertTrue(repository.has(new MapTemplate(10300, null, null, null, null, null)));
    }

    @Test
    void all() {
        assertEquals(3, repository.all().size());
    }
}
