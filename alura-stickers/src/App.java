import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args) throws Exception {

        // fazer uma conexão HTTP e buscar os top 250 filmes
        // para não exibir a API-KEY no código é possível criar uma variábel de ambiente
        // para receber essa informação
        // no terminar linux executar comando export IMDB_API_KEY="k_x3pev8lm"
        // para conferir valor na variável comando echo $IMDB_API_KEY
        // na URL fechar aspas no barra / e acrescentar váriável que declarou para
        // receber a variável de ambiente
        // exemplo String imdbKey = Sustem.getenv("IMDB_API+KEY");
        // exemplo String url = "https://imdb-api.com/en/API/Top250Movies" + imdbKey;
        String url = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/TopMovies.json";
        URI endereco = URI.create(url);
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String body = response.body();
        // System.out.println(body);

        // extrair só os dados que interessam (titulo, poster, classificação)
        var parser = new JsonParser();
        List<Map<String, String>> listaDeFilmes = parser.parse(body);
        // System.out.println(listaDeFilmes.size()); //quantidade de resultados(itens)
        // System.out.println(listaDeFilmes.get(0)); // da lista pegar o item 0

        // exibir e manipular os dados
        // for (Map<String, String> filme : listaDeFilmes) { //listar todos itens
        for (int i = 0; i < 3; i++) {
            Map<String, String> filme = listaDeFilmes.get(i);
            System.out.println("\u001b[1mTítulo:\u001b[0m " + filme.get("title"));
            System.out.println("\u001b[3mURL da imagem:\u001b[0m " + filme.get("image"));
            // System.out.println(filme.get("imDbRating")); //valor recebido da API
            double classificao = Double.parseDouble(filme.get("imDbRating"));
            int numeroEstrelinhas = (int) classificao;
            for (int n = 1; n <= numeroEstrelinhas; n++) {
                System.out.print("⭐"); // no terminal para demonstrar será necessário digitar código chcp65001
            }
            System.out.println("\n");
        }
    }
}
