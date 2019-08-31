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

package fr.quatrevieux.araknemu.core.di.item;

import fr.quatrevieux.araknemu.core.di.Container;
import fr.quatrevieux.araknemu.core.di.ContainerException;

/**
 * Decorate a container item to cache its value (useful for persistent values)
 * @param <T> Item type
 */
final public class CachedItem<T> implements ContainerItem<T> {
    final private ContainerItem<T> inner;
    private T value;

    public CachedItem(ContainerItem<T> inner) {
        this.inner = inner;
    }

    @Override
    public Class<T> type() {
        return inner.type();
    }

    @Override
    public T value(Container container) throws ContainerException {
        if (value == null) {
            value = inner.value(container);
        }

        return value;
    }
}
