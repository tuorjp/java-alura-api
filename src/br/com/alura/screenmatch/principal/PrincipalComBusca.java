package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.modelos.Titulo;
import br.com.alura.screenmatch.modelos.TituloOmdb;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class PrincipalComBusca {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner scan = new Scanner(System.in);
        System.out.println("Digite o nome do filme: ");
        var busca = scan.nextLine();

        String endereco = "http://www.omdbapi.com/?t=" + busca + "&apikey=91835625";

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(URI.create(endereco))
                .build();

        HttpResponse<String> response = client
                .send(request, HttpResponse.BodyHandlers.ofString());

        String json = response.body();
        System.out.println(json);

        // Passando UPPER_CAMEL_CASE por que no JSON a chave tem a primeira letra maiúscula e a nossa classe não
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .create();

        // Titulo titulo = gson.fromJson(json, Titulo.class);

        TituloOmdb tituloOmdb = gson.fromJson(json, TituloOmdb.class);
        System.out.println(tituloOmdb);

        try {
            Titulo titulo = new Titulo(tituloOmdb);
            System.out.println("Título convertido: ");
            System.out.println(titulo);
        } catch (NumberFormatException e) {
            System.out.println("Aconteceu um erro: ");
            System.out.println(e.getMessage());
        }
    }
}
