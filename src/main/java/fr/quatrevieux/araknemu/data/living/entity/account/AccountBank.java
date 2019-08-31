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

import fr.quatrevieux.araknemu.data.living.entity.WalletEntity;

/**
 * Store the bank information for a server account
 */
final public class AccountBank implements WalletEntity {
    final private int accountId;
    final private int serverId;
    private long kamas;

    public AccountBank(int accountId, int serverId, long kamas) {
        this.accountId = accountId;
        this.serverId = serverId;
        this.kamas = kamas;
    }

    /**
     * The related account id
     * This value is a part of the primary key
     *
     * @see Account#id()
     */
    public int accountId() {
        return accountId;
    }

    /**
     * The game server id
     * This value is a part of the primary key
     */
    public int serverId() {
        return serverId;
    }

    @Override
    public long kamas() {
        return kamas;
    }

    @Override
    public void setKamas(long kamas) {
        this.kamas = kamas;
    }
}
