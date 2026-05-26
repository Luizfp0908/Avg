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
import service.EquipeAdversariaService;

import java.util.Optional;

public class EquipeController {
    private final EquipeAdversariaService service = new EquipeAdversariaService();
    private final TableView<EquipeAdversaria> tabela = new TableView<>();

    public Scene criarCena(Stage stage) {
        VBox sidebar = Sidebar.criar(stage, "Equipes");

        Label titulo = new Label("Equipes Adversárias");
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 26));
        titulo.setTextFill(Color.WHITE);

        // Ajustado para o seu modelo exato
        TableColumn<EquipeAdversaria, String> colNome = new TableColumn<>("Nome da Equipe");
        colNome.setCellValueFactory(new PropertyValueFactory<>("nomeEquipe"));

        TableColumn<EquipeAdversaria, String> colCategoria = new TableColumn<>("Categoria");
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        colCategoria.setMaxWidth(150);

        TableColumn<EquipeAdversaria, String> colObs = new TableColumn<>("Observações");
        colObs.setCellValueFactory(new PropertyValueFactory<>("observacoes"));

        tabela.getColumns().addAll(colNome, colCategoria, colObs);
        tabela.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        carregarTabela();

        // Barra de ferramentas
        Button btnNovo = criarBotao("+ Nova Equipe", "#10b981", "#059669");
        btnNovo.setOnAction(e -> abrirFormulario(null));

        Button btnEditar = criarBotao("✏️ Editar", "#f59e0b", "#d97706");
        btnEditar.setOnAction(e -> {
            EquipeAdversaria selecionada = tabela.getSelectionModel().getSelectedItem();
            if (selecionada != null) {
                abrirFormulario(selecionada);
            } else {
                new Alert(Alert.AlertType.WARNING, "Selecione uma equipe na tabela para editar!").showAndWait();
            }
        });

        Button btnExcluir = criarBotao("🗑️ Excluir", "#ef4444", "#dc2626");
        btnExcluir.setOnAction(e -> excluirEquipe());

        HBox toolbar = new HBox(10, btnNovo, btnEditar, btnExcluir);

        VBox conteudo = new VBox(15, titulo, toolbar, tabela);
        conteudo.setPadding(new Insets(35));
        conteudo.setStyle("-fx-background-color: #2d1b69;");

        BorderPane root = new BorderPane();
        root.setLeft(sidebar);
        root.setCenter(conteudo);
        return new Scene(root, 1050, 680);
    }

    private Button criarBotao(String texto, String corNormal, String corHover) {
        Button btn = new Button(texto);
        String estiloBase = "-fx-background-color: " + corNormal + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand;";
        String estiloHover = "-fx-background-color: " + corHover + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand;";
        btn.setStyle(estiloBase);
        btn.setOnMouseEntered(e -> btn.setStyle(estiloHover));
        btn.setOnMouseExited(e -> btn.setStyle(estiloBase));
        return btn;
    }

    private void abrirFormulario(EquipeAdversaria equipeParaEditar) {
        Stage s = new Stage();
        s.setTitle(equipeParaEditar == null ? "Cadastrar Nova Equipe" : "Editar Equipe");
        s.initModality(Modality.APPLICATION_MODAL);
        s.setResizable(false);

        Label lblNome = new Label("Nome da Equipe:"); lblNome.setTextFill(Color.WHITE);
        Label lblCategoria = new Label("Categoria:"); lblCategoria.setTextFill(Color.WHITE);
        Label lblObs = new Label("Observações:"); lblObs.setTextFill(Color.WHITE);

        TextField fNome = new TextField(); fNome.setPromptText("Ex: Castro Vôlei");
        TextField fCategoria = new TextField(); fCategoria.setPromptText("Ex: Adulto");
        TextField fObs = new TextField(); fObs.setPromptText("Ex: Time forte no bloqueio");

        if (equipeParaEditar != null) {
            fNome.setText(equipeParaEditar.getNomeEquipe());
            fCategoria.setText(equipeParaEditar.getCategoria());
            fObs.setText(equipeParaEditar.getObservacoes());
        }

        Button btnSalvar = criarBotao("Salvar Equipe", "#7c3aed", "#6d28d9");
        btnSalvar.setMaxWidth(Double.MAX_VALUE);

        btnSalvar.setOnAction(e -> {
            if (fNome.getText().trim().isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "O nome da equipe é obrigatório!").showAndWait();
                return;
            }

            EquipeAdversaria equipe = (equipeParaEditar == null) ? new EquipeAdversaria("Castro Vôlei", "Adulto", "Time tático e defensivo") : equipeParaEditar;
            equipe.setNomeEquipe(fNome.getText());
            equipe.setCategoria(fCategoria.getText());
            equipe.setObservacoes(fObs.getText());

            service.salvar(equipe);
            carregarTabela();
            s.close();
        });

        VBox layout = new VBox(10, lblNome, fNome, lblCategoria, fCategoria, lblObs, fObs, new Region(), btnSalvar);
        layout.setPadding(new Insets(25));
        layout.setStyle("-fx-background-color: #1e1145;");

        s.setScene(new Scene(layout, 350, 350));
        s.show();
    }

    private void excluirEquipe() {
        EquipeAdversaria selecionada = tabela.getSelectionModel().getSelectedItem();
        if (selecionada == null) {
            new Alert(Alert.AlertType.WARNING, "Selecione uma equipe para excluir!").showAndWait();
            return;
        }

        Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacao.setTitle("Confirmar Exclusão");
        confirmacao.setHeaderText("Excluir " + selecionada.getNomeEquipe() + "?");

        Optional<ButtonType> resultado = confirmacao.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            service.excluir(selecionada);
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