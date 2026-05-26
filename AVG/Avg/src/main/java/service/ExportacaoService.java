package service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import model.Scout;
import java.io.FileOutputStream;
import java.util.List;

public class ExportacaoService {

    public void gerarRelatorioScoutPDF(String titulo, List<Scout> scouts, String caminhoFicheiro) {
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);

        try {
            PdfWriter.getInstance(document, new FileOutputStream(caminhoFicheiro));
            document.open();

            // Título do Relatório
            Font fonteTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, BaseColor.BLACK);
            Paragraph pTitulo = new Paragraph(titulo, fonteTitulo);
            pTitulo.setAlignment(Element.ALIGN_CENTER);
            pTitulo.setSpacingAfter(30);
            document.add(pTitulo);

            // Tabela de Dados
            PdfPTable table = new PdfPTable(3);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10);
            table.setWidths(new float[]{1.5f, 2f, 1f});

            // Cabeçalho da tabela
            adicionarCabecalho(table, "Equipe");
            adicionarCabecalho(table, "Fundamento");
            adicionarCabecalho(table, "ID Jogo");

            // Conteúdo
            Font fonteConteudo = FontFactory.getFont(FontFactory.HELVETICA, 12);
            for (Scout s : scouts) {
                table.addCell(new Phrase(s.getEquipe(), fonteConteudo));
                table.addCell(new Phrase(s.getFundamento(), fonteConteudo));
                table.addCell(new Phrase(String.valueOf(s.getJogoId()), fonteConteudo));
            }

            document.add(table);

            // Rodapé
            Paragraph pRodape = new Paragraph("\nRelatório gerado pelo AVV Performance",
                    FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 10));
            pRodape.setAlignment(Element.ALIGN_RIGHT);
            document.add(pRodape);

            System.out.println("Relatório PDF gerado com sucesso: " + caminhoFicheiro);

        } catch (Exception e) {
            System.err.println("Erro ao gerar PDF: " + e.getMessage());
        } finally {
            if (document.isOpen()) document.close();
        }
    }

    private void adicionarCabecalho(PdfPTable table, String texto) {
        PdfPCell cell = new PdfPCell(new Phrase(texto, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.WHITE)));
        cell.setBackgroundColor(new BaseColor(45, 27, 105)); // Roxo AVV
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(8);
        table.addCell(cell);
    }
}