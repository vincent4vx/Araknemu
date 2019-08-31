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

package fr.quatrevieux.araknemu.data.living.repository.player;

import fr.quatrevieux.araknemu.core.dbal.repository.MutableRepository;
import fr.quatrevieux.araknemu.core.dbal.repository.RepositoryException;
import fr.quatrevieux.araknemu.data.living.entity.player.Player;
import fr.quatrevieux.araknemu.data.value.ServerCharacters;

import java.util.Collection;

/**
 * Repository for {@link Player} entity
 */
public interface PlayerRepository extends MutableRepository<Player> {
    /**
     * Get list of players by account
     *
     * @param accountId The account race
     * @param serverId The server
     */
    Collection<Player> findByAccount(int accountId, int serverId);

    /**
     * Check if the name is already used into the current server
     *
     * @param player The criteria
     *
     * @return true if exists
     */
    boolean nameExists(Player player);

    /**
     * Get the account characters count
     *
     * @param player The player data
     */
    int accountCharactersCount(Player player);

    /**
     * Get list of servers with characters count
     *
     * @param accountId The account id to find
     *
     * @return The list of servers. The returned server can be down, but cannot have a count of zero
     */
    Collection<ServerCharacters> accountCharactersCount(int accountId);

    /**
     * Get the player entity by race, and ensure that account and server is valid
     */
    Player getForGame(Player player);

    /**
     * Save the player entity
     *
     * @throws fr.quatrevieux.araknemu.core.dbal.repository.EntityNotFoundException When the entity cannot be updated
     */
    void save(Player player) throws RepositoryException;
}
