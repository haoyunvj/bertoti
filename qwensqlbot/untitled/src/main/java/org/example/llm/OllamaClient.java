package org.example.llm;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import okhttp3.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class OllamaClient {
    private static final String OLLAMA_API_URL = "http://localhost:11434/api/generate";
    private static final String MODEL_NAME = "qwen2.5:3b";
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private final OkHttpClient httpClient;
    private final Gson gson;

    public OllamaClient() {
        // Configurar cliente HTTP com timeout maior para modelos grandes
        this.httpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();
        this.gson = new Gson();
    }

    public String generateResponse(String prompt) {
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("model", MODEL_NAME);
        requestBody.addProperty("prompt", prompt);
        requestBody.addProperty("stream", false);

        RequestBody body = RequestBody.create(requestBody.toString(), JSON);
        Request request = new Request.Builder()
                .url(OLLAMA_API_URL)
                .post(body)
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Erro na API: " + response.code() + " - " + response.message());
            }

            if (response.body() != null) {
                String responseBody = response.body().string();
                JsonObject jsonResponse = gson.fromJson(responseBody, JsonObject.class);

                if (jsonResponse.has("response")) {
                    return jsonResponse.get("response").getAsString();
                } else {
                    throw new IOException("Resposta sem conteúdo esperado");
                }
            } else {
                throw new IOException("Resposta vazia");
            }
        } catch (IOException e) {
            System.err.println("Erro ao comunicar com Ollama: " + e.getMessage());
            return "Desculpe, ocorreu um erro ao processar sua solicitação.";
        }
    }

    public boolean testConnection() {
        try {
            String response = generateResponse("Olá, teste de conexão");
            return response != null && !response.isEmpty();
        } catch (Exception e) {
            System.err.println("Falha no teste de conexão: " + e.getMessage());
            return false;
        }
    }
}