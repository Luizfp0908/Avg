package controller;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.EquipeAdversaria;
import model.Jogo;
import service.EquipeAdversariaService;
import service.JogoService;

public class JogoController {
    private final JogoService service = new JogoService();
    private final TableView<Jogo> tabela = new TableView<>();

    public Scene criarCena(Stage stage) {
        VBox sidebar = Sidebar.criar(stage, "Jogos");

        Label titulo = new Label("Gestão de Jogos e Partidas");
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 26));
        titulo.setTextFill(Color.WHITE);

        // --- COLUNAS DA TABELA ---
        TableColumn<Jogo, String> colData = new TableColumn<>("Data");
        colData.setCellValueFactory(new PropertyValueFactory<>("dataJogo"));
        colData.setMinWidth(120);

        TableColumn<Jogo, String> colAdv = new TableColumn<>("Adversário");
        colAdv.setCellValueFactory(new PropertyValueFactory<>("nomeAdversaria"));
        colAdv.setMinWidth(250);

        TableColumn<Jogo, String> colPlacar = new TableColumn<>("Placar (Sets)");
        colPlacar.setCellValueFactory(new PropertyValueFactory<>("placarPorSet"));
        colPlacar.setMinWidth(150);

        tabela.getColumns().addAll(colData, colAdv, colPlacar);
        tabela.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // --- ESTILO PROFISSIONAL DA TABELA ---
        tabela.setStyle("-fx-background-color: #1e1145; -fx-table-cell-border-color: #3b2285; -fx-font-size: 14px;");
        tabela.setFixedCellSize(45); // Linhas maiores para melhor legibilidade

        colData.setStyle("-fx-alignment: CENTER;");
        colAdv.setStyle("-fx-alignment: CENTER_LEFT; -fx-padding: 0 0 0 10;");
        colPlacar.setStyle("-fx-alignment: CENTER;");

        carregarTabela();

        // --- BOTÕES ---
        Button btnNovo = criarBotao("+ Novo Jogo", "#10b981", "#059669");
        btnNovo.setOnAction(e -> abrirFormulario(null));

        Button btnEditar = criarBotao("✏️ Editar", "#f59e0b", "#d97706");
        btnEditar.setOnAction(e -> {
            Jogo s = tabela.getSelectionModel().getSelectedItem();
            if (s != null) abrirFormulario(s);
            else mostrarAlerta(Alert.AlertType.WARNING, "Selecione um jogo!");
        });

        Button btnExcluir = criarBotao("🗑️ Excluir", "#ef4444", "#dc2626");
        btnExcluir.setOnAction(e -> excluirJogo());

        Button btnScout = criarBotao("📊 Registrar Scout", "#3b82f6", "#2563eb");
        btnScout.setOnAction(e -> {
            Jogo s = tabela.getSelectionModel().getSelectedItem();
            if(s != null) stage.setScene(new ScoutController(s.getId(), 0).criarCena(stage));
            else mostrarAlerta(Alert.AlertType.WARNING, "Selecione um jogo!");
        });

        Button btnAnalise = criarBotao("📈 Análise / PDF", "#8b5cf6", "#7c3aed");
        btnAnalise.setOnAction(e -> {
            Jogo s = tabela.getSelectionModel().getSelectedItem();
            if(s != null) {
                service.exportarRelatorioParaPDF(s);
                mostrarAlerta(Alert.AlertType.INFORMATION, "PDF gerado com sucesso!");
            } else {
                mostrarAlerta(Alert.AlertType.WARNING, "Selecione um jogo!");
            }
        });

        HBox toolbarCrud = new HBox(10, btnNovo, btnEditar, btnExcluir);
        HBox toolbarAcoes = new HBox(10, btnScout, btnAnalise);
        VBox toolbars = new VBox(10, toolbarCrud, toolbarAcoes);

        VBox conteudo = new VBox(15, titulo, toolbars, tabela);
        conteudo.setPadding(new Insets(35));
        conteudo.setStyle("-fx-background-color: #2d1b69;");

        BorderPane root = new BorderPane();
        root.setLeft(sidebar);
        root.setCenter(conteudo);
        return new Scene(root, 1050, 680);
    }

    private void mostrarAlerta(Alert.AlertType tipo, String msg) { new Alert(tipo, msg).showAndWait(); }

    private Button criarBotao(String texto, String corNormal, String corHover) {
        Button btn = new Button(texto);
        String estilo = "-fx-background-color: %s; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand; -fx-padding: 8 15; -fx-background-radius: 5;";
        btn.setStyle(String.format(estilo, corNormal));
        btn.setOnMouseEntered(e -> btn.setStyle(String.format(estilo, corHover)));
        btn.setOnMouseExited(e -> btn.setStyle(String.format(estilo, corNormal)));
        return btn;
    }

    private void abrirFormulario(Jogo jogoParaEditar) {
        Stage s = new Stage();
        s.initModality(Modality.APPLICATION_MODAL);

        TextField fData = new TextField();
        TextField fPlacar = new TextField();
        ComboBox<EquipeAdversaria> cbEquipe = new ComboBox<>(FXCollections.observableArrayList(new EquipeAdversariaService().listarTodos()));
        cbEquipe.setMaxWidth(Double.MAX_VALUE); // Formulário simétrico

        if (jogoParaEditar != null) {
            fData.setText(jogoParaEditar.getDataJogo());
            fPlacar.setText(jogoParaEditar.getPlacarPorSet());
            cbEquipe.getItems().stream().filter(eq -> eq.getId() == jogoParaEditar.getEquipeAdversariaId()).findFirst().ifPresent(cbEquipe::setValue);
        }

        Button btnSalvar = criarBotao("Salvar Jogo", "#7c3aed", "#6d28d9");
        btnSalvar.setMaxWidth(Double.MAX_VALUE);
        btnSalvar.setOnAction(e -> {
            Jogo j = (jogoParaEditar == null) ? new Jogo() : jogoParaEditar;
            j.setDataJogo(fData.getText());
            j.setPlacarPorSet(fPlacar.getText());
            j.setEquipeAdversariaId(cbEquipe.getValue().getId());
            service.salvar(j);
            carregarTabela();
            s.close();
        });

        VBox layout = new VBox(10, new Label("Data:"), fData, new Label("Equipe Adversária:"), cbEquipe, new Label("Placar (Sets):"), fPlacar, new Region(), btnSalvar);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: #1e1145;");
        s.setScene(new Scene(layout, 300, 350));
        s.show();
    }

    private void excluirJogo() {
        Jogo s = tabela.getSelectionModel().getSelectedItem();
        if (s != null) {
            service.excluir(s);
            carregarTabela();
        }
    }

    private void carregarTabela() {
        tabela.setItems(FXCollections.observableArrayList(service.listarTodos()));
    }
}