package com.example.db;


import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

public class DatabaseService {
    private static final Logger LOGGER = Logger.getLogger(DatabaseService.class.getName());

    private static DatabaseService INSTANCE;

    private DatabaseService(){}

    public static DatabaseService getDatabaseService(){
        if (Objects.isNull(INSTANCE)){
            INSTANCE = new DatabaseService();
        }
        return INSTANCE;
    }

    public void insertCSV(List<String[]> data, String tableName) throws SQLException {
        DBProperties properties = DBProperties.getProperties();
        try (Connection connection = DriverManager.getConnection(
                properties.getUrl(),
                properties.getUser(),
                properties.getPassword()
        )) {
            CreateTables(connection);
            addRecording(connection, data, tableName);
        } catch (SQLException e) {
            LOGGER.info(e.getMessage());
        }
    }

    public List<List<String>> findTransactions(String string_to_find) {
        DBProperties properties = DBProperties.getProperties();
        try (Connection connection = DriverManager.getConnection(
                properties.getUrl(),
                properties.getUser(),
                properties.getPassword()
        )) {
            return findTransactionQuery(connection, string_to_find);
        } catch (SQLException e) {
            LOGGER.info(e.getMessage());
        }
        return null;
    }

    private List<List<String>> findTransactionQuery(@NotNull Connection connection, String string_to_find) throws SQLException {
        //TODO: найти другой способ запроса, тут очевидная уязвимость
        String query = "SELECT * FROM tr_types AS trt INNER JOIN transactions_cut AS trc ON trc.tr_type = trt.tr_type WHERE LOWER(tr_description) LIKE LOWER('%" + string_to_find + "%');";

        List<List<String>> transactions = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    List<String> current_transaction = new ArrayList<>();
                    current_transaction.add(result.getString(1));
                    current_transaction.add(result.getString(2));
                    current_transaction.add(result.getString(3));
                    current_transaction.add(result.getString(4));
                    current_transaction.add(result.getString(5));
                    //current_transaction.add(result.getString(6));
                    current_transaction.add(result.getString(7));
                    current_transaction.add(result.getString(8));
                    transactions.add(current_transaction);
                }
            }
        }

        return transactions;
    }


    private void addRecording(@NotNull Connection connection, List<String[]> data, String tableName) throws SQLException {
        StringBuilder header = new StringBuilder();

        for (int i = 0; i < data.get(0).length; i++) {
            header.append(data.get(0)[i]);
            if (i != data.get(0).length - 1) {
                header.append(", ");
            }
        }
        switch (tableName) {
            case "transactions_cut":
                for (int i = 1; i < data.size(); i++) {
                    addTransaction(connection, data.get(i), tableName, header.toString());
                }
                break;
            case "tr_types":
                for (int i = 1; i < data.size(); i++) {
                    addTypes(connection, data.get(i), tableName, header.toString());
                }
                break;
            case "tr_mcc_codes":
                for (int i = 1; i < data.size(); i++) {
                    mcc_codes(connection, data.get(i), tableName, header.toString());
                }
                break;
            case "gender_train_cut":
                for (int i = 1; i < data.size(); i++) {
                    addGender(connection, data.get(i), tableName, header.toString());
                }
                break;
            default:
                LOGGER.info("Передалась не существующая таблица - " + tableName);
                break;
        }
    }

    private void addTransaction(@NotNull Connection connection, @NotNull String[] recording, String tableName, String header) throws SQLException {
        String query = createInsertQuery(tableName, recording, header);
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            try {
                statement.setLong(1, Long.parseLong(recording[0]));
                statement.setString(2, recording[1]);
                statement.setLong(3, Long.parseLong(recording[2]));
                statement.setLong(4, Long.parseLong(recording[3]));
                statement.setDouble(5, Double.parseDouble(recording[4]));
                statement.setString(6, recording[5]);
            } catch (Exception ex) {
                LOGGER.info(ex.getMessage());
            }
            statement.executeUpdate();
        } catch (RuntimeException e) {
            LOGGER.info("Ошибка в addRecording" + " { " + e.getMessage() + "}");
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }

    }

    private void addTypes(@NotNull Connection connection, String[] recording, String tableName, String header) throws SQLException {
        String query = createInsertQuery(tableName, recording, header);
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            try {
                statement.setInt(1, Integer.parseInt(recording[0]));
                statement.setString(2, recording[1]);
            } catch (Exception ex) {
                LOGGER.info(ex.getMessage());
            }
            statement.executeUpdate();
        } catch (RuntimeException e) {
            LOGGER.info("Ошибка в addRecording" + " { " + e.getMessage() + "}");
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }
    }

    private void mcc_codes(@NotNull Connection connection, String[] recording, String tableName, String header) throws SQLException {
        String query = createInsertQuery(tableName, recording, header);

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            try {
                statement.setLong(1, Long.parseLong(recording[0]));
                statement.setString(2, recording[1]);
            } catch (Exception ex) {
                LOGGER.info(ex.getMessage());
            }
            statement.executeUpdate();
        } catch (RuntimeException e) {
            LOGGER.info("Ошибка в addRecording" + " { " + e.getMessage() + "}");
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }
    }

    private void addGender(@NotNull Connection connection, String[] recording, String tableName, String header) throws SQLException {
        String query = createInsertQuery(tableName, recording, header);

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            try {
                statement.setLong(1, Long.parseLong(recording[0]));
                statement.setInt(2, Integer.parseInt(recording[1]));
            } catch (Exception ex) {
                LOGGER.info(ex.getMessage());
            }
            statement.executeUpdate();
        } catch (RuntimeException e) {
            LOGGER.info("Ошибка в addRecording" + " { " + e.getMessage() + "}");
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }
    }

    private String createInsertQuery(String tableName, String[] recording, String header) {
        StringBuilder query = new StringBuilder("INSERT INTO " + tableName + "(" + header + ") VALUES (");

        for (int i = 0; i < recording.length; i++) {
            query.append("?");

            if (i != recording.length - 1) {
                query.append(", ");
            }
        }

        query.append(")");

        return query.toString();
    }

    private void CreateTables(@NotNull Connection connection) throws SQLException {
        int countOfTables = 4;
        String[] query = new String[countOfTables];

        query[0] = "CREATE TABLE IF NOT EXISTS transactions_cut (customer_id BIGINT not NULL, tr_datetime VARCHAR not NULL, " +
                "mcc_code BIGINT not NULL, tr_type BIGINT not NULL, amount DECIMAL not NULL, " +
                "term_id VARCHAR)";

        query[1] = "CREATE TABLE IF NOT EXISTS tr_types (tr_type INTEGER not NULL, tr_description VARCHAR not NULL, PRIMARY KEY (tr_type))";

        query[2] = "CREATE TABLE IF NOT EXISTS tr_mcc_codes (mcc_code INTEGER not NULL, mcc_description VARCHAR not NULL, PRIMARY KEY (mcc_code))";

        query[3] = "CREATE TABLE IF NOT EXISTS gender_train_cut (customer_id INTEGER not NULL, gender VARCHAR not NULL, PRIMARY KEY (customer_id))";

        for (int i = 0; i < countOfTables; i++) {
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(query[i]);
            } catch (RuntimeException e) {
                LOGGER.info("Ошибка в CreateTable на шаге " + i + " { " + e.getMessage() + "}");
                connection.rollback();
            } finally {
                connection.setAutoCommit(true);
            }
        }
    }
}
