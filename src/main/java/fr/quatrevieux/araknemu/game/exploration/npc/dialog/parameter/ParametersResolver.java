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

package fr.quatrevieux.araknemu.game.exploration.npc.dialog.parameter;

import fr.quatrevieux.araknemu.game.exploration.ExplorationPlayer;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Resolve question parameters
 */
final public class ParametersResolver {
    final private Map<String, VariableResolver> resolvers = new HashMap<>();
    final private Logger logger;

    public ParametersResolver(VariableResolver[] resolvers, Logger logger) {
        this.logger = logger;

        for (VariableResolver resolver : resolvers) {
            register(resolver);
        }
    }

    /**
     * Resolve the parameter value from the player
     *
     * @param name The parameter name
     * @param player The player
     *
     * @return The parameter value
     */
    public Object resolve(String name, ExplorationPlayer player) {
        if (isVariable(name)) {
            final String varName = name.substring(1, name.length() - 1);

            if (resolvers.containsKey(varName)) {
                return resolvers.get(varName).value(player);
            }

            logger.warn("Undefined dialog variable {}", varName);
        }

        return name;
    }

    /**
     * Register a new variable resolver
     */
    public void register(VariableResolver resolver) {
        resolvers.put(resolver.name(), resolver);
    }

    /**
     * Check if the parameter is a variable
     * A variable is rounded by brackets (ex. "[name]")
     *
     * A variable parameter should be resolved from the player
     * If the parameter is not a variable (i.e. is a constant), the value should be returned without transformations
     *
     * @param name The parameter name
     *
     * @return true if the parameter is a variable, or false for a constant
     */
    private boolean isVariable(String name) {
        return name.charAt(0) == '[' && name.charAt(name.length() - 1) == ']';
    }
}
