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

package fr.quatrevieux.araknemu.game.admin;

import fr.quatrevieux.araknemu.game.admin.exception.CommandNotFoundException;
import fr.quatrevieux.araknemu.game.admin.exception.ContextNotFoundException;

import java.util.Collection;
import java.util.Collections;

/**
 * Null object for context
 */
final public class NullContext implements Context {
    @Override
    public Command command(String name) throws CommandNotFoundException {
        throw new CommandNotFoundException(name);
    }

    @Override
    public Collection<Command> commands() {
        return Collections.emptyList();
    }

    @Override
    public Context child(String name) throws ContextNotFoundException {
        throw new ContextNotFoundException(name);
    }
}
