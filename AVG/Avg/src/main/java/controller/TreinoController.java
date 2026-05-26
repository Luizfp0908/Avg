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
import model.Treino;
import service.TreinoService;

import java.util.Optional;

public class TreinoController {
    private final TreinoService service = new TreinoService();
    private final TableView<Treino> tabela = new TableView<>();

    public Scene criarCena(Stage stage) {
        VBox sidebar = Sidebar.criar(stage, "Treinos");

        Label titulo = new Label("Controle de Sessões de Treino");
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 26));
        titulo.setTextFill(Color.WHITE);

        // Colunas da Tabela
        TableColumn<Treino, String> colData = new TableColumn<>("Data do Treino");
        colData.setCellValueFactory(new PropertyValueFactory<>("dataTreino"));
        colData.setMaxWidth(150);

        TableColumn<Treino, Integer> colDuracao = new TableColumn<>("Duração (Minutos)");
        colDuracao.setCellValueFactory(new PropertyValueFactory<>("duracaoMinutos"));
        colDuracao.setMaxWidth(150);

        TableColumn<Treino, String> colFoco = new TableColumn<>("Foco Principal");
        colFoco.setCellValueFactory(new PropertyValueFactory<>("focoPrincipal"));

        tabela.getColumns().addAll(colData, colDuracao, colFoco);
        tabela.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        carregarTabela();

        // Barra de Ferramentas
        Button btnNovo = criarBotao("+ Agendar Treino", "#10b981", "#059669");
        btnNovo.setOnAction(e -> abrirFormulario(null));

        Button btnEditar = criarBotao("✏️ Editar", "#f59e0b", "#d97706");
        btnEditar.setOnAction(e -> {
            Treino selecionado = tabela.getSelectionModel().getSelectedItem();
            if (selecionado != null) {
                abrirFormulario(selecionado);
            } else {
                new Alert(Alert.AlertType.WARNING, "Selecione um treino na tabela para editar!").showAndWait();
            }
        });

        Button btnExcluir = criarBotao("🗑️ Excluir", "#ef4444", "#dc2626");
        btnExcluir.setOnAction(e -> excluirTreino());

        HBox toolbar = new HBox(10, btnNovo, btnEditar, btnExcluir);

        VBox conteudo = new VBox(15, titulo, toolbar, tabela);
        conteudo.setPadding(new Insets(35));
        conteudo.setStyle("-fx-background-color: #2d1b69;"); // Fundo escuro

        BorderPane root = new BorderPane();
        root.setLeft(sidebar);
        root.setCenter(conteudo);
        return new Scene(root, 1050, 680);
    }

    private Button criarBotao(String texto, String corNormal, String corHover) {
        Button btn = new Button(texto);
        String estiloBase = "-fx-background-color: " + corNormal + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand; -fx-padding: 8 15 8 15; -fx-background-radius: 5;";
        String estiloHover = "-fx-background-color: " + corHover + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand; -fx-padding: 8 15 8 15; -fx-background-radius: 5;";
        btn.setStyle(estiloBase);
        btn.setOnMouseEntered(e -> btn.setStyle(estiloHover));
        btn.setOnMouseExited(e -> btn.setStyle(estiloBase));
        return btn;
    }

    private void abrirFormulario(Treino treinoParaEditar) {
        Stage s = new Stage();
        s.setTitle(treinoParaEditar == null ? "Agendar Novo Treino" : "Editar Treino");
        s.initModality(Modality.APPLICATION_MODAL);
        s.setResizable(false);

        Label lblData = new Label("Data (DD/MM/AAAA):"); lblData.setTextFill(Color.WHITE);
        Label lblDuracao = new Label("Duração (Minutos):"); lblDuracao.setTextFill(Color.WHITE);
        Label lblFoco = new Label("Foco Principal:"); lblFoco.setTextFill(Color.WHITE);

        TextField fData = new TextField(); fData.setPromptText("Ex: 27/05/2026");
        TextField fDuracao = new TextField(); fDuracao.setPromptText("Ex: 120");
        TextField fFoco = new TextField(); fFoco.setPromptText("Ex: Transição de Bloqueio");

        if (treinoParaEditar != null) {
            fData.setText(treinoParaEditar.getDataTreino());
            fDuracao.setText(String.valueOf(treinoParaEditar.getDuracaoMinutos()));
            fFoco.setText(treinoParaEditar.getFocoPrincipal());
        }

        Button btnSalvar = criarBotao("Salvar Treino", "#7c3aed", "#6d28d9");
        btnSalvar.setMaxWidth(Double.MAX_VALUE);

        btnSalvar.setOnAction(e -> {
            if (fData.getText().trim().isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "A data do treino é obrigatória!").showAndWait();
                return;
            }

            Treino t = (treinoParaEditar == null) ? new Treino() : treinoParaEditar;
            t.setDataTreino(fData.getText());
            t.setFocoPrincipal(fFoco.getText());
            try {
                t.setDuracaoMinutos(Integer.parseInt(fDuracao.getText()));
            } catch (NumberFormatException ex) {
                t.setDuracaoMinutos(0);
            }

            service.salvar(t);
            carregarTabela();
            s.close();
        });

        VBox layout = new VBox(10, lblData, fData, lblDuracao, fDuracao, lblFoco, fFoco, new Region(), btnSalvar);
        layout.setPadding(new Insets(25));
        layout.setStyle("-fx-background-color: #1e1145;");

        s.setScene(new Scene(layout, 350, 350));
        s.show();
    }

    private void excluirTreino() {
        Treino selecionado = tabela.getSelectionModel().getSelectedItem();
        if (selecionado == null) {
            new Alert(Alert.AlertType.WARNING, "Selecione um treino na tabela para excluir!").showAndWait();
            return;
        }

        Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacao.setTitle("Confirmar Exclusão");
        confirmacao.setHeaderText("Deseja cancelar o treino selecionado?");

        Optional<ButtonType> resultado = confirmacao.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            service.excluir(selecionado);
            carregarTabela();
        }
    }

    private void carregarTabela() {
        try {
            tabela.setItems(FXCollections.observableArrayList(service.listarTodos()));
        } catch (Exception e) {
            System.out.println("Erro ao carregar banco: " + e.getMessage());
        }
    }
}