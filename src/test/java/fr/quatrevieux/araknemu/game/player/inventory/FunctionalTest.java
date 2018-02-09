package fr.quatrevieux.araknemu.game.player.inventory;

import fr.quatrevieux.araknemu.game.GameBaseCase;
import fr.quatrevieux.araknemu.game.item.ItemService;
import fr.quatrevieux.araknemu.game.world.item.Item;
import fr.quatrevieux.araknemu.game.world.item.inventory.exception.InventoryException;
import fr.quatrevieux.araknemu.network.game.out.object.AddItem;
import fr.quatrevieux.araknemu.network.game.out.object.ItemPosition;
import fr.quatrevieux.araknemu.network.game.out.object.ItemQuantity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FunctionalTest extends GameBaseCase {
    private PlayerInventory inventory;
    private ItemService itemService;

    @Override
    @BeforeEach
    public void setUp() throws Exception {
        super.setUp();

        dataSet.pushItemTemplates();

        itemService = container.get(ItemService.class);
        inventory = gamePlayer(true).inventory();
        requestStack.clear();
    }

    @Test
    void addNewItem() throws InventoryException {
        Item item = itemService.create(284);

        InventoryEntry entry = inventory.add(item);

        assertEquals(-1, entry.position());
        assertEquals(1, entry.quantity());
        assertSame(item, entry.item());
        assertSame(entry, inventory.get(entry.id()));

        requestStack.assertLast(
            new AddItem(entry)
        );
    }

    @Test
    void addManyItems() throws InventoryException {
        Item item = itemService.create(284);

        InventoryEntry entry = inventory.add(item, 10);

        assertEquals(-1, entry.position());
        assertEquals(10, entry.quantity());
        assertSame(item, entry.item());
        assertSame(entry, inventory.get(entry.id()));

        requestStack.assertLast(
            new AddItem(entry)
        );
    }

    @Test
    void addMultipleItems() throws InventoryException {
        List<InventoryEntry> entries = Arrays.asList(
            inventory.add(itemService.create(2411)),
            inventory.add(itemService.create(2414)),
            inventory.add(itemService.create(2416))
        );

        assertIterableEquals(entries, inventory);

        requestStack.assertAll(
            new AddItem(entries.get(0)),
            new AddItem(entries.get(1)),
            new AddItem(entries.get(2))
        );
    }

    @Test
    void moveBadQuantity() throws InventoryException {
        InventoryEntry entry = inventory.add(itemService.create(2411));
        requestStack.clear();

        assertThrows(InventoryException.class, () -> entry.move(6, 3));

        requestStack.assertEmpty();
        assertEquals(-1, entry.position());
        assertEquals(1, entry.quantity());
    }

    @Test
    void moveBadSlot() throws InventoryException {
        InventoryEntry entry = inventory.add(itemService.create(2411));
        requestStack.clear();

        assertThrows(InventoryException.class, () -> entry.move(1, 1));

        requestStack.assertEmpty();
        assertEquals(-1, entry.position());
        assertEquals(1, entry.quantity());
    }

    @Test
    void moveSlotFull() throws InventoryException {
        inventory.add(itemService.create(39), 1, 0);
        InventoryEntry entry = inventory.add(itemService.create(2425));
        requestStack.clear();

        assertThrows(InventoryException.class, () -> entry.move(0, 1));

        requestStack.assertEmpty();
        assertEquals(-1, entry.position());
        assertEquals(1, entry.quantity());
    }

    @Test
    void moveSuccess() throws InventoryException {
        InventoryEntry entry = inventory.add(itemService.create(2425));
        requestStack.clear();

        entry.move(0, 1);

        requestStack.assertLast(new ItemPosition(entry));
        assertEquals(0, entry.position());
        assertEquals(1, entry.quantity());
    }

    @Test
    void moveSuccessFromStackedItem() throws InventoryException {
        InventoryEntry entry = inventory.add(itemService.create(2425), 10);
        requestStack.clear();

        entry.move(0, 1);

        InventoryEntry newEntry = inventory.get(2);

        requestStack.assertAll(
            new AddItem(newEntry),
            new ItemQuantity(entry)
        );

        assertEquals(-1, entry.position());
        assertEquals(9, entry.quantity());

        assertEquals(0, newEntry.position());
        assertEquals(1, newEntry.quantity());
    }
}