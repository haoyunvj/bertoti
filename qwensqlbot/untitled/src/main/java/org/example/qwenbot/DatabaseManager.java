package org.example.qwenbot;

import java.sql.*;

public class DatabaseManager {
    private Connection connection;
    private static final String DB_URL = "jdbc:sqlite:qwen_bot.db";

    public DatabaseManager() {
        try {
            // Criar conexão com o banco de dados
            connection = DriverManager.getConnection(DB_URL);
            System.out.println("Conexão com SQLite estabelecida");

            // Inicializar tabelas
            initializeTables();
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
    }

    private void initializeTables() {
        try (Statement statement = connection.createStatement()) {
            // Criar tabela de mensagens
            String createMessagesTable = "CREATE TABLE IF NOT EXISTS messages (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "user_input TEXT NOT NULL," +
                    "bot_response TEXT NOT NULL," +
                    "timestamp DATETIME DEFAULT CURRENT_TIMESTAMP" +
                    ")";
            statement.execute(createMessagesTable);

            // Criar tabela de configurações
            String createSettingsTable = "CREATE TABLE IF NOT EXISTS settings (" +
                    "key TEXT PRIMARY KEY," +
                    "value TEXT NOT NULL" +
                    ")";
            statement.execute(createSettingsTable);

            System.out.println("Tabelas inicializadas com sucesso");
        } catch (SQLException e) {
            System.err.println("Erro ao inicializar tabelas: " + e.getMessage());
        }
    }

    public void saveConversation(String userInput, String botResponse) {
        String sql = "INSERT INTO messages (user_input, bot_response) VALUES (?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, userInput);
            pstmt.setString(2, botResponse);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao salvar conversa: " + e.getMessage());
        }
    }

    public void saveSetting(String key, String value) {
        String sql = "INSERT OR REPLACE INTO settings (key, value) VALUES (?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, key);
            pstmt.setString(2, value);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao salvar configuração: " + e.getMessage());
        }
    }

    public String getSetting(String key) {
        String sql = "SELECT value FROM settings WHERE key = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, key);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getString("value");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao recuperar configuração: " + e.getMessage());
        }

        return null;
    }

    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Conexão com o banco de dados fechada");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao fechar conexão: " + e.getMessage());
        }
    }
}