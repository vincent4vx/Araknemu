package fr.quatrevieux.araknemu.data.world.repository.implementation.sql;

import fr.quatrevieux.araknemu.core.dbal.ConnectionPool;
import fr.quatrevieux.araknemu.core.dbal.repository.RepositoryException;
import fr.quatrevieux.araknemu.core.dbal.repository.RepositoryUtils;
import fr.quatrevieux.araknemu.core.dbal.util.ConnectionPoolUtils;
import fr.quatrevieux.araknemu.data.world.entity.environment.npc.Question;
import fr.quatrevieux.araknemu.data.world.entity.environment.npc.ResponseAction;
import org.apache.commons.lang3.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Response action repository implementation for SQL database
 *
 * @see ResponseAction
 */
final class ResponseActionRepository implements fr.quatrevieux.araknemu.data.world.repository.environment.npc.ResponseActionRepository {
    private class Loader implements RepositoryUtils.Loader<ResponseAction> {
        @Override
        public ResponseAction create(ResultSet rs) throws SQLException {
            return new ResponseAction(
                rs.getInt("RESPONSE_ID"),
                rs.getString("ACTION"),
                rs.getString("ARGUMENTS")
            );
        }

        @Override
        public ResponseAction fillKeys(ResponseAction entity, ResultSet keys) {
            throw new RepositoryException("Read-only entity");
        }
    }

    final private ConnectionPoolUtils pool;
    final private RepositoryUtils<ResponseAction> utils;

    public ResponseActionRepository(ConnectionPool pool) {
        this.pool = new ConnectionPoolUtils(pool);

        utils = new RepositoryUtils<>(this.pool, new Loader());
    }

    @Override
    public void initialize() throws RepositoryException {
        try {
            pool.query(
                "CREATE TABLE NPC_RESPONSE_ACTION(" +
                    "RESPONSE_ID INTEGER," +
                    "ACTION VARCHAR(12)," +
                    "ARGUMENTS VARCHAR(255)," +
                    "PRIMARY KEY (RESPONSE_ID, ACTION)" +
                ")"
            );
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public void destroy() throws RepositoryException {
        try {
            pool.query("DROP TABLE NPC_RESPONSE_ACTION");
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public ResponseAction get(ResponseAction entity) throws RepositoryException {
        return utils.findOne(
            "SELECT * FROM NPC_RESPONSE_ACTION WHERE RESPONSE_ID = ? AND ACTION = ?",
            stmt -> {
                stmt.setInt(1, entity.responseId());
                stmt.setString(2, entity.action());
            }
        );
    }

    @Override
    public boolean has(ResponseAction entity) throws RepositoryException {
        return utils.aggregate(
            "SELECT COUNT(*) FROM NPC_RESPONSE_ACTION WHERE RESPONSE_ID = ? AND ACTION = ?",
            stmt -> {
                stmt.setInt(1, entity.responseId());
                stmt.setString(2, entity.action());
            }
        ) > 0;
    }

    @Override
    public Map<Integer, List<ResponseAction>> all() throws RepositoryException {
        return
            utils
                .findAll("SELECT * FROM NPC_RESPONSE_ACTION")
                .stream()
                .collect(Collectors.groupingBy(ResponseAction::responseId))
        ;
    }

    @Override
    public Map<Integer, List<ResponseAction>> byQuestion(Question question) {
        switch (question.responseIds().length) {
            case 0:
                return Collections.emptyMap();

            case 1:
                final List<ResponseAction> ras = utils.findAll(
                    "SELECT * FROM NPC_RESPONSE_ACTION WHERE RESPONSE_ID = ?",
                    rs -> rs.setInt(1, question.responseIds()[0])
                );

                return ras.isEmpty() ? Collections.emptyMap() : Collections.singletonMap(question.responseIds()[0], ras);

            default:
                return utils.findAll(
                        "SELECT * FROM NPC_RESPONSE_ACTION WHERE RESPONSE_ID IN(" + StringUtils.repeat("?, ", question.responseIds().length - 1) + "?)",
                        rs -> {
                            for (int i = 0; i < question.responseIds().length; ++i) {
                                rs.setInt(i + 1, question.responseIds()[i]);
                            }
                        }
                    )
                    .stream()
                    .collect(Collectors.groupingBy(ResponseAction::responseId))
                ;
        }
    }
}