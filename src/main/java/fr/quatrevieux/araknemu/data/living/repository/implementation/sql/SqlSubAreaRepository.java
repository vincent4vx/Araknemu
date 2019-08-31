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

package fr.quatrevieux.araknemu.data.living.repository.implementation.sql;

import fr.quatrevieux.araknemu.core.dbal.ConnectionPool;
import fr.quatrevieux.araknemu.core.dbal.repository.RepositoryException;
import fr.quatrevieux.araknemu.core.dbal.repository.RepositoryUtils;
import fr.quatrevieux.araknemu.core.dbal.util.ConnectionPoolUtils;
import fr.quatrevieux.araknemu.data.constant.Alignment;
import fr.quatrevieux.araknemu.data.living.entity.environment.SubArea;
import fr.quatrevieux.araknemu.data.living.repository.environment.SubAreaRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

/**
 * SQL implementation for subarea repository
 */
final class SqlSubAreaRepository implements SubAreaRepository {
    private static class Loader implements RepositoryUtils.Loader<SubArea> {
        @Override
        public SubArea create(ResultSet rs) throws SQLException {
            return new SubArea(
                rs.getInt("SUBAREA_ID"),
                rs.getInt("AREA_ID"),
                rs.getString("SUBAREA_NAME"),
                rs.getBoolean("CONQUESTABLE"),
                Alignment.byId(rs.getInt("ALIGNMENT"))
            );
        }

        @Override
        public SubArea fillKeys(SubArea entity, ResultSet keys) throws SQLException {
            throw new UnsupportedOperationException();
        }
    }

    final private ConnectionPoolUtils pool;
    final private RepositoryUtils<SubArea> utils;

    public SqlSubAreaRepository(ConnectionPool pool) {
        this.pool = new ConnectionPoolUtils(pool);
        this.utils = new RepositoryUtils<>(this.pool, new Loader());
    }

    @Override
    public void initialize() throws RepositoryException {
        try {
            pool.query(
                "CREATE TABLE SUBAREA (" +
                    "SUBAREA_ID INTEGER PRIMARY KEY," +
                    "AREA_ID INTEGER," +
                    "SUBAREA_NAME VARCHAR(200)," +
                    "CONQUESTABLE INTEGER(1)," +
                    "ALIGNMENT INTEGER(1)" +
                ")"
            );
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public void destroy() throws RepositoryException {
        try {
            pool.query("DROP TABLE SUBAREA");
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public SubArea get(SubArea entity) throws RepositoryException {
        return utils.findOne(
            "SELECT * FROM SUBAREA WHERE SUBAREA_ID = ?",
            rs -> rs.setInt(1, entity.id())
        );
    }

    @Override
    public boolean has(SubArea entity) {
        return utils.aggregate(
            "SELECT COUNT(*) FROM SUBAREA WHERE SUBAREA_ID = ?",
            rs -> rs.setInt(1, entity.id())
        ) > 0;
    }

    @Override
    public Collection<SubArea> all() {
        return utils.findAll("SELECT * FROM SUBAREA");
    }
}
