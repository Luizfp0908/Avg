package controller;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Sidebar {

    public static VBox criar(Stage stage, String telaAtiva) {
        // Logo e Título do Menu
        Label lblNome = new Label("🏐 AVV");
        lblNome.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        lblNome.setTextFill(Color.WHITE);
        lblNome.setPadding(new Insets(20));

        // Construção dinâmica do Menu
        VBox menu = new VBox(5,
                menuItem(stage, "📊 Dashboard", "Dashboard", telaAtiva),
                menuItem(stage, "👤 Atletas", "Atletas", telaAtiva),
                menuItem(stage, "🏆 Adversários", "Equipes", telaAtiva),
                menuItem(stage, "🎯 Jogos", "Jogos", telaAtiva),
                menuItem(stage, "💪 Treinos", "Treinos", telaAtiva)
        );

        // Espaçador para empurrar o botão de sair para o rodapé
        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        // Botão de Sair com feedback visual
        Button btnSair = new Button("🚪 Sair");
        btnSair.setMaxWidth(Double.MAX_VALUE);
        btnSair.setStyle("-fx-background-color: #ef476f; -fx-text-fill: white; -fx-cursor: hand; -fx-font-weight: bold;");

        // Efeito de Hover no botão de sair
        btnSair.setOnMouseEntered(e -> btnSair.setStyle("-fx-background-color: #d90429; -fx-text-fill: white; -fx-cursor: hand; -fx-font-weight: bold;"));
        btnSair.setOnMouseExited(e -> btnSair.setStyle("-fx-background-color: #ef476f; -fx-text-fill: white; -fx-cursor: hand; -fx-font-weight: bold;"));

        btnSair.setOnAction(e -> stage.setScene(new LoginController().criarCena(stage)));
        VBox.setMargin(btnSair, new Insets(15));

        // Container final da Sidebar
        VBox sidebar = new VBox(lblNome, menu, spacer, btnSair);
        sidebar.setPrefWidth(220); // Um pouco mais largo para acomodar bem os textos
        sidebar.setStyle("-fx-background-color: #1e1145; -fx-border-color: transparent #3b2285 transparent transparent; -fx-border-width: 0 1 0 0;");
        return sidebar;
    }

    private static Button menuItem(Stage stage, String texto, String chave, String telaAtiva) {
        Button btn = new Button(texto);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setAlignment(Pos.CENTER_LEFT);
        btn.setPadding(new Insets(12, 20, 12, 20));

        boolean ativo = chave.equals(telaAtiva);

        if (ativo) {
            // Estilo do botão ATIVO
            btn.setStyle("-fx-background-color: #4a3490; -fx-text-fill: white; -fx-font-weight: bold; -fx-border-color: transparent transparent transparent #7c3aed; -fx-border-width: 0 0 0 4;");
        } else {
            // Estilo do botão INATIVO
            btn.setStyle("-fx-background-color: transparent; -fx-text-fill: #c4b5fd; -fx-cursor: hand; -fx-font-weight: normal;");

            // 💡 O Golpe de Mestre: Simulando o :hover com eventos de mouse
            btn.setOnMouseEntered(e -> btn.setStyle("-fx-background-color: rgba(124, 58, 237, 0.15); -fx-text-fill: white; -fx-cursor: hand; -fx-font-weight: normal;"));
            btn.setOnMouseExited(e -> btn.setStyle("-fx-background-color: transparent; -fx-text-fill: #c4b5fd; -fx-cursor: hand; -fx-font-weight: normal;"));

            // Lógica de navegação
            btn.setOnAction(e -> {
                switch (chave) {
                    case "Dashboard" -> stage.setScene(new DashboardController().criarCena(stage));
                    case "Atletas"   -> stage.setScene(new AtletaController().criarCena(stage));
                    case "Equipes"   -> stage.setScene(new EquipeController().criarCena(stage));
                    case "Jogos"     -> stage.setScene(new JogoController().criarCena(stage));
                    case "Treinos"   -> stage.setScene(new TreinoController().criarCena(stage));
                }
            });
        }
        return btn;
    }
}