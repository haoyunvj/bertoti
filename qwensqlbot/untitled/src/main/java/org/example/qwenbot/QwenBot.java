package org.example.qwenbot;

import org.example.llm.OllamaClient;

import java.util.Scanner;

public class QwenBot {
    private final DatabaseManager dbManager;
    private final OllamaClient ollamaClient;
    private final Scanner scanner;

    public QwenBot() {
        this.dbManager = new DatabaseManager();
        this.ollamaClient = new OllamaClient();
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("Iniciando QwenBot...");

        // Testar conexão com Ollama
        if (!ollamaClient.testConnection()) {
            System.err.println("Não foi possível conectar ao servidor Ollama.");
            System.err.println("Verifique se o Ollama está rodando com o comando: ollama run qwen2.5:3b");
            return;
        }

        System.out.println("Conexão com Ollama estabelecida!");
        System.out.println("Bot pronto para conversar. Digite 'sair' para encerrar.");

        // Loop principal do bot
        boolean running = true;
        while (running) {
            System.out.print("\nVocê: ");
            String userInput = scanner.nextLine().trim();

            if (userInput.equalsIgnoreCase("sair")) {
                running = false;
                continue;
            }

            // Obter resposta do modelo
            System.out.println("Bot está pensando...");
            String botResponse = ollamaClient.generateResponse(userInput);

            // Exibir resposta
            System.out.println("\nBot: " + botResponse);

            // Salvar conversa no banco de dados
            dbManager.saveConversation(userInput, botResponse);
        }

        // Encerrar recursos
        System.out.println("Encerrando bot...");
        dbManager.close();
        scanner.close();
    }

    public static void main(String[] args) {
        QwenBot bot = new QwenBot();
        bot.start();
    }
}