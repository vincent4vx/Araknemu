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

package fr.quatrevieux.araknemu.data.living.entity.account;

import fr.quatrevieux.araknemu.data.living.entity.Item;
import fr.quatrevieux.araknemu.data.value.ItemTemplateEffectEntry;

import java.util.List;

/**
 * Item entry of a account bank
 */
final public class BankItem implements Item {
    final private int accountId;
    final private int serverId;
    final private int entryId;
    final private int itemTemplateId;
    final private List<ItemTemplateEffectEntry> effects;
    private int quantity;

    public BankItem(int accountId, int serverId, int entryId, int itemTemplateId, List<ItemTemplateEffectEntry> effects, int quantity) {
        this.accountId = accountId;
        this.serverId = serverId;
        this.entryId = entryId;
        this.itemTemplateId = itemTemplateId;
        this.effects = effects;
        this.quantity = quantity;
    }

    /**
     * Related account
     * Part of the primary key
     *
     * @see AccountBank#accountId()
     */
    public int accountId() {
        return accountId;
    }

    /**
     * Related server
     * Part of the primary key
     *
     * @see AccountBank#serverId()
     */
    public int serverId() {
        return serverId;
    }

    @Override
    public int entryId() {
        return entryId;
    }

    @Override
    public int itemTemplateId() {
        return itemTemplateId;
    }

    @Override
    public List<ItemTemplateEffectEntry> effects() {
        return effects;
    }

    @Override
    public int quantity() {
        return quantity;
    }

    @Override
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
