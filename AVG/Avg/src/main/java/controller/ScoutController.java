package controller;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import model.Scout;
import service.ScoutService;

import java.util.List;

public class ScoutController {
    private final int jogoId;
    private final ScoutService service = new ScoutService();

    // Componentes que mudam em tempo real
    private final Label lblPlacarAVV = new Label("0");
    private final Label lblPlacarAdv = new Label("0");
    private final ListView<String> historicoLista = new ListView<>();

    public ScoutController(int jogoId, int setAtual) {
        this.jogoId = jogoId;
    }

    public Scene criarCena(Stage stage) {
        VBox sidebar = Sidebar.criar(stage, "Jogos");

        Label titulo = new Label("📊 Scout em Tempo Real (Jogo #" + jogoId + ")");
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 26));
        titulo.setTextFill(Color.WHITE);

        // --- PLACAR ELETRÔNICO ---
        lblPlacarAVV.setFont(Font.font("Arial", FontWeight.BOLD, 70));
        lblPlacarAVV.setTextFill(Color.web("#10b981")); // Verde AVV

        Label separador = new Label("X");
        separador.setFont(Font.font("Arial", FontWeight.BOLD, 40));
        separador.setTextFill(Color.WHITE);

        lblPlacarAdv.setFont(Font.font("Arial", FontWeight.BOLD, 70));
        lblPlacarAdv.setTextFill(Color.web("#ef4444")); // Vermelho Adv

        HBox placar = new HBox(30,
                criarCaixaPlacar("AVV", lblPlacarAVV),
                separador,
                criarCaixaPlacar("ADVERSÁRIO", lblPlacarAdv)
        );
        placar.setAlignment(Pos.CENTER);
        placar.setPadding(new Insets(20));
        placar.setStyle("-fx-background-color: #1e1145; -fx-background-radius: 15;");

        // --- BOTÕES DE AÇÃO (AVV) ---
        VBox acoesAVV = new VBox(10,
                new Label("🏐 Pontos da AVV:"),
                criarBotaoAcao("Ataque", "AVV", "#3b82f6"),
                criarBotaoAcao("Bloqueio", "AVV", "#8b5cf6"),
                criarBotaoAcao("Saque (Ace)", "AVV", "#10b981"),
                criarBotaoAcao("Erro do Adversário", "AVV", "#f59e0b")
        );
        acoesAVV.getChildren().get(0).setStyle("-fx-text-fill: white; -fx-font-weight: bold;");

        // --- BOTÕES DE AÇÃO (ADVERSÁRIO) ---
        VBox acoesAdv = new VBox(10,
                new Label("❌ Pontos do Adversário:"),
                criarBotaoAcao("Ponto Sofrido", "Adversário", "#ef4444"),
                criarBotaoAcao("Erro de Saque (AVV)", "Adversário", "#dc2626"),
                criarBotaoAcao("Erro de Ataque (AVV)", "Adversário", "#b91c1c")
        );
        acoesAdv.getChildren().get(0).setStyle("-fx-text-fill: white; -fx-font-weight: bold;");

        HBox painelBotoes = new HBox(50, acoesAVV, acoesAdv);
        painelBotoes.setAlignment(Pos.CENTER);

        // --- HISTÓRICO (LOG DA PARTIDA) ---
        historicoLista.setPrefHeight(150);

        Button btnVoltar = new Button("⬅ Voltar para Jogos");
        btnVoltar.setStyle("-fx-background-color: transparent; -fx-border-color: white; -fx-text-fill: white; -fx-cursor: hand; -fx-padding: 8 15; -fx-border-radius: 5;");
        btnVoltar.setOnAction(e -> stage.setScene(new JogoController().criarCena(stage)));

        VBox conteudo = new VBox(20, titulo, btnVoltar, placar, painelBotoes, new Label("Histórico do Set:"), historicoLista);
        conteudo.getChildren().get(5).setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
        conteudo.setPadding(new Insets(30));
        conteudo.setStyle("-fx-background-color: #2d1b69;");

        atualizarTela(); // Carrega os pontos que já existem no banco

        BorderPane root = new BorderPane();
        root.setLeft(sidebar);
        root.setCenter(conteudo);
        return new Scene(root, 1050, 680);
    }

    // Auxiliar: Caixinha preta do Placar
    private VBox criarCaixaPlacar(String nome, Label numero) {
        Label lblNome = new Label(nome);
        lblNome.setTextFill(Color.WHITE);
        lblNome.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        VBox box = new VBox(5, lblNome, numero);
        box.setAlignment(Pos.CENTER);
        return box;
    }

    // Auxiliar: Botão de Registrar Ponto
    private Button criarBotaoAcao(String fundamento, String equipe, String corHex) {
        Button btn = new Button("+1 " + fundamento);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setStyle("-fx-background-color: " + corHex + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand; -fx-padding: 10;");
        btn.setOnAction(e -> {
            service.registrarPonto(jogoId, equipe, fundamento);
            atualizarTela();
        });
        return btn;
    }

    // A Mágica do Tempo Real: Recalcula o placar lendo o banco de dados
    private void atualizarTela() {
        List<Scout> historico = service.listarPorJogo(jogoId);

        int pontosAVV = 0;
        int pontosAdv = 0;
        historicoLista.getItems().clear();

        for (Scout s : historico) {
            if (s.getEquipe().equals("AVV")) {
                pontosAVV++;
                historicoLista.getItems().add(0, "✅ Ponto AVV (" + s.getFundamento() + ")");
            } else {
                pontosAdv++;
                historicoLista.getItems().add(0, "❌ Ponto Adversário (" + s.getFundamento() + ")");
            }
        }

        lblPlacarAVV.setText(String.valueOf(pontosAVV));
        lblPlacarAdv.setText(String.valueOf(pontosAdv));
    }
}