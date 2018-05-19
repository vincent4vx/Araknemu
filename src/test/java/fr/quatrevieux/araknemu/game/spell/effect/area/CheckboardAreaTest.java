package fr.quatrevieux.araknemu.game.spell.effect.area;

import fr.quatrevieux.araknemu.data.value.EffectArea;
import fr.quatrevieux.araknemu.data.world.repository.environment.MapTemplateRepository;
import fr.quatrevieux.araknemu.game.GameBaseCase;
import fr.quatrevieux.araknemu.game.fight.map.FightMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class CheckboardAreaTest extends GameBaseCase {
    private FightMap map;

    @Override
    @BeforeEach
    public void setUp() throws Exception {
        super.setUp();

        dataSet.pushMaps();
        map = new FightMap(container.get(MapTemplateRepository.class).get(10340));
    }

    @Test
    void getters() {
        CheckboardArea area = new CheckboardArea(new EffectArea(EffectArea.Type.CHECKERBOARD, 2));

        assertEquals(EffectArea.Type.CHECKERBOARD, area.type());
        assertEquals(2, area.size());
    }

    @Test
    void resolveSize0() {
        assertEquals(
            Collections.singleton(map.get(123)),
            new CheckboardArea(new EffectArea(EffectArea.Type.CHECKERBOARD, 0)).resolve(map.get(123), map.get(123))
        );
    }

    @Test
    void resolvePairSize() {
        assertCollectionEquals(
            new CheckboardArea(new EffectArea(EffectArea.Type.CHECKERBOARD, 2)).resolve(map.get(123), map.get(137)),

            map.get(123),

            map.get(95),
            map.get(153),
            map.get(151),
            map.get(93),
            map.get(124),
            map.get(152),
            map.get(122),
            map.get(94)
        );
    }

    @Test
    void resolveImpairSize() {
        assertCollectionEquals(
            new CheckboardArea(new EffectArea(EffectArea.Type.CHECKERBOARD, 1)).resolve(map.get(123), map.get(137)),

            map.get(109),
            map.get(138),
            map.get(137),
            map.get(108)
        );
    }
}