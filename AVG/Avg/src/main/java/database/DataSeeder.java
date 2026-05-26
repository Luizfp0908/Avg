package database;

import dao.AtletaDAO;
import dao.EquipeAdversariaDAO;
import model.Atleta;
import model.EquipeAdversaria;

public class DataSeeder {
    public static void popularSeVazio() {
        AtletaDAO atletaDAO = new AtletaDAO();
        EquipeAdversariaDAO equipeDAO = new EquipeAdversariaDAO();

        if (atletaDAO.listarTodos().isEmpty()) {
            System.out.println("Populando banco com elenco completo da AVV...");

            // 1. Inserindo Equipes
            equipeDAO.inserir(new EquipeAdversaria("Castro Vôlei", "Adulto", "Time tático e defensivo"));
            equipeDAO.inserir(new EquipeAdversaria("Ponta Grossa Vôlei", "Sub-19", "Equipe veloz"));
            equipeDAO.inserir(new EquipeAdversaria("Londrina E.C.", "Adulto", "Favorito ao título"));

            // 2. Inserindo Elenco da AVV (Massa de dados)
            String[][] atletas = {
                    {"Luiz Francisco", "10", "Ponteiro"},
                    {"Gabriel Palomares", "7", "Levantador"},
                    {"Alany Moreira", "5", "Líbero"},
                    {"Camilli Vitória", "12", "Central"},
                    {"João Silva", "1", "Ponteiro"},
                    {"Mateus Souza", "18", "Oposto"},
                    {"Felipe Santos", "9", "Central"},
                    {"Lucas Oliveira", "4", "Levantador"},
                    {"Rafael Mendes", "2", "Líbero"},
                    {"Bruno Costa", "15", "Ponteiro"}
            };

            for (String[] a : atletas) {
                Atleta atleta = new Atleta();
                atleta.setNomeCompleto(a[0]);
                atleta.setNumeroCamisa(Integer.parseInt(a[1]));
                atleta.setPosicao(a[2]);
                atletaDAO.inserir(atleta);
            }

            System.out.println("Banco populado com sucesso!");
        }
    }
}
