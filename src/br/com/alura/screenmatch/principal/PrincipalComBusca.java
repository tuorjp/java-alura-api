package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.excecao.ErroDeConversaoDeAno;
import br.com.alura.screenmatch.modelos.Titulo;
import br.com.alura.screenmatch.modelos.TituloOmdb;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PrincipalComBusca {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner scan = new Scanner(System.in);
        String busca = "";
        List<Titulo> titulos = new ArrayList<>();

        while (busca != "sair") {
            System.out.println("Digite o nome do filme: ");
            busca = scan.nextLine();

            if(busca.equalsIgnoreCase("sair")) {
                break;
            }

            // Passando UPPER_CAMEL_CASE por que no JSON a chave tem a primeira letra maiúscula e a nossa classe não
            Gson gson = new GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                    .setPrettyPrinting()
                    .create();

            String endereco = "http://www.omdbapi.com/?t=" + busca.replace(" ", "+") + "&apikey=91835625";

            try {
                HttpClient client = HttpClient.newHttpClient();

                HttpRequest request = HttpRequest
                        .newBuilder()
                        .uri(URI.create(endereco))
                        .build();

                HttpResponse<String> response = client
                        .send(request, HttpResponse.BodyHandlers.ofString());

                String json = response.body();
                System.out.println(json);

                // Titulo titulo = gson.fromJson(json, Titulo.class);

                TituloOmdb tituloOmdb = gson.fromJson(json, TituloOmdb.class);
                System.out.println(tituloOmdb);


                Titulo titulo = new Titulo(tituloOmdb);
                System.out.println("Título convertido: ");
                System.out.println(titulo);

                // Escrevendo filme num arquivo
//                FileWriter escrita = new FileWriter("filmes.txt");
//                escrita.write(titulo.toString());
//                escrita.close();

                titulos.add(titulo);
                System.out.println(titulos);

                FileWriter escrita = new FileWriter("filmes.json");
                escrita.write(gson.toJson(titulos));
                escrita.close();
            } catch (NumberFormatException e) {
                System.out.println("Aconteceu um erro: ");
                System.out.println(e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("Erro de argumento ilegal: ");
                System.out.println(e.getMessage());
            } catch (ErroDeConversaoDeAno e) {
                System.out.println("Erro ao converter ano: ");
                System.out.println(e.getMessage());
            }
        }

        System.out.println("Programa finalizado");
    }
}
