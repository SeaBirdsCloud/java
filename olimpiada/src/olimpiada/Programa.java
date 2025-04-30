package olimpiada;
import java.util.*;

class Pais {
    String nome;
    String continente;
    int ouro = 0, prata = 0, bronze = 0;

    public Pais(String nome, String continente) {
        this.nome = nome;
        this.continente = continente;
    }

    public int totalMedalhas() {
        return ouro * 3 + prata * 2 + bronze;
    }

    public String toString() {
        return nome + " (" + continente + ")";
    }
}

class Atleta {
    String nome;
    String sexo;
    Pais pais;
    String modalidade;

    public Atleta(String nome, String sexo, Pais pais, String modalidade) {
        this.nome = nome;
        this.sexo = sexo;
        this.pais = pais;
        this.modalidade = modalidade;
    }

    public String toString() {
        return nome + " | " + sexo + " | " + pais.nome + " | " + modalidade;
    }
}

class Rodada {
    Atleta atleta;
    double pontuacao;

    public Rodada(Atleta atleta, double pontuacao) {
        this.atleta = atleta;
        this.pontuacao = pontuacao;
    }
}

public class Programa {
	
	public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Pais> paises = new ArrayList<>();
        List<String> modalidades = new ArrayList<>();
        List<Atleta> atletas = new ArrayList<>();
        List<Rodada> rodadas = new ArrayList<>();

        System.out.println("=== Cadastro de Países ===");
        System.out.print("Quantos países deseja cadastrar? ");
        int qtdPaises = Integer.parseInt(scanner.nextLine());
        for (int i = 0; i < qtdPaises; i++) {
            System.out.print("Nome do país: ");
            String nome = scanner.nextLine();
            System.out.print("Continente: ");
            String continente = scanner.nextLine();
            paises.add(new Pais(nome, continente));
        }

        System.out.println("\n=== Cadastro de Modalidades ===");
        System.out.print("Quantas modalidades deseja cadastrar? ");
        int qtdMod = Integer.parseInt(scanner.nextLine());
        for (int i = 0; i < qtdMod; i++) {
            System.out.print("Nome da modalidade " + (i + 1) + ": ");
            modalidades.add(scanner.nextLine());
        }

        System.out.println("\n=== Cadastro de Atletas ===");
        System.out.print("Quantos atletas deseja cadastrar? ");
        int qtdAtletas = Integer.parseInt(scanner.nextLine());
        for (int i = 0; i < qtdAtletas; i++) {
            System.out.print("Nome do atleta: ");
            String nome = scanner.nextLine();
            System.out.print("Sexo (M/F): ");
            String sexo = scanner.nextLine();

            System.out.println("Escolha o país:");
            for (int j = 0; j < paises.size(); j++) {
                System.out.println(j + " - " + paises.get(j));
            }
            int indicePais = Integer.parseInt(scanner.nextLine());

            System.out.println("Escolha a modalidade:");
            for (int j = 0; j < modalidades.size(); j++) {
                System.out.println(j + " - " + modalidades.get(j));
            }
            int indiceMod = Integer.parseInt(scanner.nextLine());

            atletas.add(new Atleta(nome, sexo, paises.get(indicePais), modalidades.get(indiceMod)));
        }

        System.out.println("\n=== Cadastro das Partidas ===");
        System.out.print("Quantas partidas deseja cadastrar? ");
        int qtdRodadas = Integer.parseInt(scanner.nextLine());

        for (int i = 0; i < qtdRodadas; i++) {
            System.out.println(">>> Partida " + (i + 1));
            System.out.println("Escolha os atletas participantes da mesma modalidade:");

            // Filtrar atletas por modalidade
            for (int m = 0; m < modalidades.size(); m++) {
                System.out.println(m + " - " + modalidades.get(m));
            }
            int modalidadeEscolhida = Integer.parseInt(scanner.nextLine());
            String mod = modalidades.get(modalidadeEscolhida);

            List<Atleta> participantes = new ArrayList<>();
            for (int j = 0; j < atletas.size(); j++) {
                if (atletas.get(j).modalidade.equals(mod)) {
                    System.out.println(j + " - " + atletas.get(j));
                    participantes.add(atletas.get(j));
                }
            }

            System.out.print("Quantos atletas participarão dessa partida? ");
            int qtdParticipantes = Integer.parseInt(scanner.nextLine());
            for (int k = 0; k < qtdParticipantes; k++) {
                System.out.print("Digite o índice do atleta " + (k + 1) + ": ");
                int indexAtleta = Integer.parseInt(scanner.nextLine());
                System.out.print("Pontuação do atleta: ");
                double pontuacao = Double.parseDouble(scanner.nextLine());
                rodadas.add(new Rodada(atletas.get(indexAtleta), pontuacao));
            }
        }

        // Processar medalhas por modalidade
        Map<String, List<Rodada>> rodadasPorModalidade = new HashMap<>();
        for (Rodada r : rodadas) {
            rodadasPorModalidade.putIfAbsent(r.atleta.modalidade, new ArrayList<>());
            rodadasPorModalidade.get(r.atleta.modalidade).add(r);
        }

        for (String modalidade : rodadasPorModalidade.keySet()) {
            List<Rodada> lista = rodadasPorModalidade.get(modalidade);
            lista.sort((a, b) -> Double.compare(b.pontuacao, a.pontuacao)); // ordem decrescente

            if (lista.size() > 0) lista.get(0).atleta.pais.ouro++;
            if (lista.size() > 1) lista.get(1).atleta.pais.prata++;
            if (lista.size() > 2) lista.get(2).atleta.pais.bronze++;
        }

        // Exibir resultados
        System.out.println("\n=== Classificação das Rodadas ===");
        for (Rodada r : rodadas) {
            System.out.printf("Atleta: %s | Modalidade: %s | País: %s | Pontuação: %.1f\n",
                    r.atleta.nome, r.atleta.modalidade, r.atleta.pais.nome, r.pontuacao);
        }

        System.out.println("\n=== Quadro de Medalhas ===");
        paises.sort((a, b) -> Integer.compare(b.totalMedalhas(), a.totalMedalhas()));
        int pos = 1;
        for (Pais p : paises) {
            System.out.printf("%dº - %s (Ouro: %d, Prata: %d, Bronze: %d, Total de pontos: %d)\n",
                    pos++, p.nome, p.ouro, p.prata, p.bronze, p.totalMedalhas());
        }

        scanner.close();
    }
	
}