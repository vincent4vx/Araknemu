package fr.quatrevieux.araknemu.game.spell.effect.area;

import fr.quatrevieux.araknemu.data.value.EffectArea;
import fr.quatrevieux.araknemu.data.world.repository.environment.MapTemplateRepository;
import fr.quatrevieux.araknemu.game.GameBaseCase;
import fr.quatrevieux.araknemu.game.fight.map.FightMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RingAreaTest extends GameBaseCase {
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
        RingArea area = new RingArea(new EffectArea(EffectArea.Type.RING, 2));

        assertEquals(EffectArea.Type.RING, area.type());
        assertEquals(2, area.size());
    }

    @Test
    void resolveSize0() {
        assertEquals(
            Collections.singleton(map.get(123)),
            new RingArea(new EffectArea(EffectArea.Type.RING, 0)).resolve(map.get(123), map.get(123))
        );
    }

    @Test
    void resolve() {
        assertCollectionEquals(
            new RingArea(new EffectArea(EffectArea.Type.RING, 2)).resolve(map.get(123), map.get(137)),

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
}