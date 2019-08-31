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

import fr.quatrevieux.araknemu.game.admin.exception.ContextException;
import fr.quatrevieux.araknemu.game.admin.exception.ContextNotFoundException;

import java.util.HashMap;
import java.util.Map;

/**
 * Handle admin users contexts
 */
final public class AdminUserContext {
    final private Map<String, Context> contexts = new HashMap<>();

    final private Context global;
    final private Context self;
    final private Map<String, ContextResolver> resolvers;

    private Context current;

    public AdminUserContext(Context global, Context self, Map<String, ContextResolver> resolvers) {
        this.global = global;
        this.self = self;
        this.current = self;
        this.resolvers = resolvers;
    }

    /**
     * Get the global context
     */
    public Context global() {
        return global;
    }

    /**
     * Get the current user context
     */
    public Context self() {
        return self;
    }

    /**
     * Get the current context
     */
    public Context current() {
        return current;
    }

    /**
     * Change the current context
     */
    public void setCurrent(Context current) {
        this.current = current;
    }

    /**
     * Get an already registered context by its name
     */
    public Context get(String name) throws ContextNotFoundException {
        if (!contexts.containsKey(name)) {
            throw new ContextNotFoundException(name);
        }

        return contexts.get(name);
    }

    /**
     * Set a context value
     */
    public void set(String name, Context context) {
        contexts.put(name, context);
    }

    /**
     * Resolve a context
     *
     * For resolve a player :
     * resolve("player", "PlayerName");
     *
     * @param type The context type
     * @param argument The context resolve argument
     */
    public Context resolve(String type, Object argument) throws ContextException {
        if (!resolvers.containsKey(type)) {
            throw new ContextException("Context type '" + type + "' not found");
        }

        return resolvers.get(type).resolve(global, argument);
    }
}
