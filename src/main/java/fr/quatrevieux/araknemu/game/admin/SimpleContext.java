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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Simple context
 */
final public class SimpleContext implements Context {
    final private Context parent;

    final private Map<String, Command> commands = new HashMap<>();
    final private Map<String, Context> children = new HashMap<>();

    public SimpleContext(Context parent) {
        this.parent = parent;
    }

    @Override
    public Command command(String name) throws CommandNotFoundException {
        if (commands.containsKey(name)) {
            return commands.get(name);
        }

        return parent.command(name);
    }

    @Override
    public Collection<Command> commands() {
        Collection<Command> allCommands = new ArrayList<>(commands.values());

        allCommands.addAll(parent.commands());

        return allCommands;
    }

    @Override
    public Context child(String name) throws ContextNotFoundException {
        if (children.containsKey(name)) {
            return children.get(name);
        }

        return parent.child(name);
    }

    /**
     * Add a new command to the context
     */
    public SimpleContext add(Command command) {
        commands.put(command.name(), command);

        return this;
    }

    /**
     * Add a new child context
     *
     * @param name The child name
     * @param child The child
     */
    public SimpleContext add(String name, Context child) {
        children.put(name, child);

        return this;
    }
}
