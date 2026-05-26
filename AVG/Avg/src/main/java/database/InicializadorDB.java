package database;

import java.sql.Connection;
import java.sql.Statement;

public class InicializadorDB {

    public static void inicializar() {
        try (Connection conn = ConexaoDB.getConexao();
             Statement stmt = conn.createStatement()) {

            // 1. Tabela de Atletas
            stmt.execute("CREATE TABLE IF NOT EXISTS atleta (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nome_completo TEXT NOT NULL, " +
                    "numero_camisa INTEGER, " +
                    "posicao TEXT)");

            // 2. Tabela de Equipes Adversárias
            stmt.execute("CREATE TABLE IF NOT EXISTS equipe_adversaria (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nome_equipe TEXT NOT NULL, " +
                    "categoria TEXT, " +
                    "observacoes TEXT)");

            // 3. Tabela de Jogos (Com Chave Estrangeira ligando à equipe)
            stmt.execute("CREATE TABLE IF NOT EXISTS jogo (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "data_jogo TEXT NOT NULL, " +
                    "equipe_adversaria_id INTEGER, " +
                    "placar_por_set TEXT, " +
                    "FOREIGN KEY(equipe_adversaria_id) REFERENCES equipe_adversaria(id))");

            // 4. Tabela de Treinos
            stmt.execute("CREATE TABLE IF NOT EXISTS treino (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "data_treino TEXT NOT NULL, " +
                    "duracao_minutos INTEGER, " +
                    "foco_principal TEXT)");

            // 5. Tabela de Scout (Linha do tempo do jogo)
            stmt.execute("CREATE TABLE IF NOT EXISTS scout (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "jogo_id INTEGER, " +
                    "equipe TEXT NOT NULL, " +
                    "fundamento TEXT NOT NULL, " +
                    "FOREIGN KEY(jogo_id) REFERENCES jogo(id))");

            System.out.println("Banco de dados SQLite verificado e inicializado com sucesso!");

        } catch (Exception e) {
            System.err.println("Erro critico ao inicializar o banco de dados: " + e.getMessage());
        }
    }
}