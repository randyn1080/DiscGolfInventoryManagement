package com.discgolf.dao;

import com.discgolf.model.Disc;
import com.discgolf.model.DiscSearchCriteria;
import com.discgolf.util.ConnectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.plugins.jpeg.JPEGImageReadParam;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DiscDAOImpl implements DiscDAO {
    private static final Logger logger = LoggerFactory.getLogger(DiscDAOImpl.class);

    @Override
    public Disc createDisc(Disc disc) {
        String sql = "INSERT INTO discs (manufacturer, mold, color, weight, speed, " +
                "glide, turn, fade, special_notes)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        try {
            connection = ConnectionUtil.getConnection();
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, disc.getManufacturer());
            preparedStatement.setString(2, disc.getMold());
            preparedStatement.setString(3, disc.getColor());
            preparedStatement.setInt(4, disc.getWeight());
            preparedStatement.setInt(5, disc.getSpeed());
            preparedStatement.setInt(6, disc.getGlide());
            preparedStatement.setInt(7, disc.getTurn());
            preparedStatement.setInt(8, disc.getFade());
            preparedStatement.setString(9, disc.getSpecialNotes());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                rs = preparedStatement.getGeneratedKeys();
                if (rs.next()) {
                    int generatedId = rs.getInt(1);
                    disc.setDiscID(generatedId);
                    logger.info("Created new disc with ID: {}", generatedId);
                    return disc;
                }

            }
        } catch (SQLException e) {
            logger.error("Error creating disc", e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    logger.error("Error closing result set", e);
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    logger.error("Error closing prepared statement", e);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    logger.error("Error closing connection", e);
                }
            }
        }
        return null;
    }

    @Override
    public List<Disc> getAllDiscs() {
        List<Disc> discs = new ArrayList<Disc>();
        String sql = "SELECT * FROM discs";

        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;

        try {
            connection = ConnectionUtil.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(sql);

            while (rs.next()) {
                discs.add(extractDiscFromResultSet(rs));
            }
            logger.info("Retrieved {} discs from database with getAllDiscs method", discs.size());
        } catch (SQLException e) {
            logger.error("Error while retrieving discs", e);
        } finally {
            // close connection, statement, and resultset
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    logger.error("Error closing ResultSet", e);
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    logger.error("Error closing Statement", e);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    logger.error("Error closing Connection", e);
                }
            }
        }
        return discs;
    }

    @Override
    public Disc getDiscById(int discId) {
        String sql = "SELECT * FROM discs WHERE disc_id = ?";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        try {
            connection = ConnectionUtil.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, discId);
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                Disc disc = extractDiscFromResultSet(rs);
                logger.info("Retrieved disc with ID: {}", disc.getDiscID());
                return disc;
            }
        } catch (SQLException e) {
            logger.error("Error while retrieving disc", e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    logger.error("Error closing ResultSet", e);
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    logger.error("Error closing PreparedStatement", e);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    logger.error("Error closing Connection", e);
                }
            }
        }
        return null;
    }

    @Override
    public List<Disc> searchDiscs(DiscSearchCriteria criteria) {
        List<Disc> discs = new ArrayList<>();
        StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM discs WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (criteria.getManufacturer() != null && !criteria.getManufacturer().trim().isEmpty()) {
            sqlBuilder.append(" AND manufacturer LIKE ?");
            params.add("%" + criteria.getManufacturer() + "%");
        }
        if (criteria.getMold() != null && !criteria.getMold().trim().isEmpty()) {
            sqlBuilder.append(" AND mold LIKE ?");
            params.add("%" + criteria.getMold() + "%");
        }
        if (criteria.getColor() != null && !criteria.getColor().trim().isEmpty()) {
            sqlBuilder.append(" AND color LIKE ?");
            params.add("%" + criteria.getColor() + "%");
        }
        if (criteria.getWeight() != null) {
            sqlBuilder.append(" AND weight = ?");
            params.add(criteria.getWeight());
        }
        if (criteria.getSpeed() != null) {
            sqlBuilder.append(" AND speed = ?");
            params.add(criteria.getSpeed());
        }
        if (criteria.getGlide() != null) {
            sqlBuilder.append(" AND glide = ?");
            params.add(criteria.getGlide());
        }
        if (criteria.getTurn() != null) {
            sqlBuilder.append(" AND turn = ?");
            params.add(criteria.getTurn());
        }
        if (criteria.getFade() != null) {
            sqlBuilder.append(" AND fade = ?");
            params.add(criteria.getFade());
        }

        String sql = sqlBuilder.toString();
        logger.debug("Executing SQL: {}", sql);

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        try {
            connection = ConnectionUtil.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < params.size(); i++) {
                preparedStatement.setObject(i + 1, params.get(i));
            }

            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                discs.add(extractDiscFromResultSet(rs));
            }
            logger.info("Retrieved {} discs from database with searchDiscs method", discs.size());
        } catch (SQLException e) {

        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    logger.error("Error closing ResultSet", e);
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    logger.error("Error closing PreparedStatement", e);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    logger.error("Error closing Connection", e);
                }
            }
        }
        return discs;
    }

    @Override
    public boolean updateDisc(Disc disc) {
        String sql = "UPDATE discs SET manufacturer = ?, mold = ?, color = ?, weight = ?, " +
                "speed = ?, glide = ?, turn = ?, fade = ?, special_notes = ? WHERE disc_id = ?";

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = ConnectionUtil.getConnection();
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, disc.getManufacturer());
            preparedStatement.setString(2, disc.getMold());
            preparedStatement.setString(3, disc.getColor());
            preparedStatement.setInt(4, disc.getWeight());
            preparedStatement.setInt(5, disc.getSpeed());
            preparedStatement.setInt(6, disc.getGlide());
            preparedStatement.setInt(7, disc.getTurn());
            preparedStatement.setInt(8, disc.getFade());
            preparedStatement.setString(9, disc.getSpecialNotes());
            preparedStatement.setInt(10, disc.getDiscID());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                logger.info("Updated new disc with ID: {}", disc.getDiscID());
                return true;
            }
        } catch (SQLException e) {
            logger.error("Error while updating disc with ID: {}", disc.getDiscID(), e);
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    logger.error("Error closing PreparedStatement", e);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch(SQLException e) {
                    logger.error("Error closing Connection", e);
                }
            }
        }
        logger.info("No disc updated with ID: {}", disc.getDiscID());
        return false;
    }

    @Override
    public boolean deleteDisc(int discId) {
        String sql = "DELETE FROM discs WHERE disc_id = ?";

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = ConnectionUtil.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, discId);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                logger.info("Deleted disc with ID: {}", discId);
            }
        } catch (SQLException e) {
            logger.error("Error while deleting disc", e);
        }
        logger.info("No disc deleted with ID: {}", discId);
        return false;
    }

    private Disc extractDiscFromResultSet(ResultSet rs) throws SQLException {
        return new Disc(
                rs.getInt("disc_id"),
                rs.getString("manufacturer"),
                rs.getString("mold"),
                rs.getString("color"),
                rs.getInt("weight"),
                rs.getInt("speed"),
                rs.getInt("glide"),
                rs.getInt("turn"),
                rs.getInt("fade"),
                rs.getString("special_notes")
        );
    }
}
