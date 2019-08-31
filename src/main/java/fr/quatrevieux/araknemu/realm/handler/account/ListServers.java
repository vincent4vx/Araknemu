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

package fr.quatrevieux.araknemu.realm.handler.account;

import fr.quatrevieux.araknemu.network.in.PacketHandler;
import fr.quatrevieux.araknemu.network.realm.RealmSession;
import fr.quatrevieux.araknemu.network.realm.in.AskServerList;
import fr.quatrevieux.araknemu.network.realm.out.ServerList;
import fr.quatrevieux.araknemu.realm.host.HostService;

/**
 * List servers and characters count per server
 */
final public class ListServers implements PacketHandler<RealmSession, AskServerList> {
    final private HostService service;

    public ListServers(HostService service) {
        this.service = service;
    }

    @Override
    public void handle(RealmSession session, AskServerList packet) {
        session.write(
            new ServerList(
                ServerList.ONE_YEAR, // @todo abo
                service.charactersByHost(session.account())
            )
        );
    }

    @Override
    public Class<AskServerList> packet() {
        return AskServerList.class;
    }
}
