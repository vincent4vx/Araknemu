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

package fr.quatrevieux.araknemu.game.handler;

import fr.quatrevieux.araknemu.network.exception.CloseImmediately;
import fr.quatrevieux.araknemu.network.game.GameSession;
import fr.quatrevieux.araknemu.network.in.Packet;
import fr.quatrevieux.araknemu.network.in.PacketHandler;

/**
 * Decorate packet handler to ensure that session has an attached player
 */
final public class EnsurePlaying<P extends Packet> implements PacketHandler<GameSession, P> {
    final private PacketHandler<GameSession, P> inner;

    public EnsurePlaying(PacketHandler<GameSession, P> inner) {
        this.inner = inner;
    }

    @Override
    public void handle(GameSession session, P packet) throws Exception {
        if (session.player() == null)  {
            throw new CloseImmediately();
        }

        inner.handle(session, packet);
    }

    @Override
    public Class<P> packet() {
        return inner.packet();
    }
}
