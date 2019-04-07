package gerenciadordevendas.conversao;

import gerenciadordevendas.FilesWindowOpener;
import gerenciadordevendas.model.ItemEstoque;
import gerenciadordevendas.model.Produto;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.poi.ooxml.POIXMLProperties;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

public final class ExportadorEstoqueExcel {

    private boolean autoEspacarColunas;
    private List<ItemEstoque> itens;

    public ExportadorEstoqueExcel(List<ItemEstoque> itens) {
        this.itens = itens;
    }
    
    

    public void autoEspacarColunas(boolean autoEspacarColunas) {
        this.autoEspacarColunas = autoEspacarColunas;
    }

    private File selecionarArquivo() {
        return FilesWindowOpener.getCaminhoGravarArquivo("Internacionalizacao.xlsx",
                new FileNameExtensionFilter("Arquivo Excel (*.xlsx)", "xlsx"));
    }

    public void exportar() throws IOException {
        File file = selecionarArquivo();
        if (file == null) {
            return;
        }
        exportar(file);
    }

    public void exportar(File file) throws IOException {

        int numeroColunas = 4;

        FileOutputStream outputStream = null;
        Workbook workbook = new SXSSFWorkbook(200);

        Sheet sheet = workbook.createSheet();

        POIXMLProperties properties = ((SXSSFWorkbook) workbook).getXSSFWorkbook().getProperties();
        POIXMLProperties.CustomProperties cust = properties.getCustomProperties();
        cust.addProperty("Author", "GerenciadorVendas");
        cust.addProperty("Table", "Estoque");
        if (autoEspacarColunas) {
            ((SXSSFSheet) sheet).trackAllColumnsForAutoSizing();
        }
        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        cell.setCellValue("uuid");
        cell = row.createCell(1);
        cell.setCellValue("Texto Portugu�s");
        cell = row.createCell(2);
        cell.setCellValue("Texto Espanhol");
        cell = row.createCell(3);
        cell.setCellValue("Texto Ingl�s");

        
        int linha = 0;
        for (ItemEstoque objeto : itens) {
            linha++;
            
            row = sheet.createRow(linha);

            cell = row.createCell(0);
            cell.setCellValue(objeto.getUuid());
            
            Produto produto = objeto.getProduto();
            
            cell = row.createCell(1);
            cell.setCellValue(produto.getUuid());
            cell = row.createCell(2);
            cell.setCellValue(produto.getNome());
            
        }
        if (autoEspacarColunas) {
            for (int i = 0; i < numeroColunas; i++) {
                sheet.autoSizeColumn(i);
            }
        }
        try {
            if (!file.getAbsolutePath().endsWith(".xlsx")) {
                file = new File(file.getAbsolutePath() + ".xlsx");
            }
            outputStream = new FileOutputStream(file);
            workbook.write(outputStream);
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
        }

    }

 
}
