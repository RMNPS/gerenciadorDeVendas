/*
 * GerenciadorDeVendas: ImpressaoCodigoBarras.java
 * Enconding: UTF-8
 * Data de criação: 14/02/2018 11:43:04
 */
package gerenciadordevendas.controller;

import gerenciadordevendas.JPA;
import gerenciadordevendas.model.ItemEstoque;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.swing.filechooser.FileNameExtensionFilter;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;

/**
 *
 * @author Ramon Porto
 */
public class EtiquetaJasperReports {

    private static final String CAMINHO_JASPER = "./print/etiquetas_pequenas.jasper";
    private int posicao;

    public void imprimirEtiquetas(List<ItemEstoque> itensAimprimir) {
        File caminhoGravarArquivo = gerenciadordevendas.FilesWindowOpener.getCaminhoGravarArquivo(new FileNameExtensionFilter("Arquivo PDF (*.pdf)", "pdf"));
//        String out = "./impressao/etiquetas_pequenas.pdf";
        if (!caminhoGravarArquivo.getAbsolutePath().endsWith(".pdf")) {
            caminhoGravarArquivo = new File(caminhoGravarArquivo.getAbsolutePath() + ".pdf");
        }
        Map<String, Object> parametros = new HashMap<>();

        List<ItemEstoque> itens = new ArrayList<>();

        for (int i = 0; i < posicao - 1; i++) {
            itens.add(ItemEstoque.newItemEmBranco());
        }
        itens.addAll(itensAimprimir);

        parametros.put("ITENS", itens);
        try {
            JasperPrint print = JasperFillManager.fillReport(CAMINHO_JASPER, parametros, new JREmptyDataSource());
            print.setLeftMargin(0);
            print.setRightMargin(posicao);
            
            JasperPrintManager.printPage(print, 0, true);
            
            

//            Exporter exporter = new JRPdfExporter();
//            exporter.setExporterInput(new SimpleExporterInput(print));
//            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput((new FileOutputStream(caminhoGravarArquivo))));
//            exporter.exportReport();
//
//            int response = JOptionPane.showConfirmDialog(null, "Etiquetas selecionadas impressas em PDF em:\n"
//                    + caminhoGravarArquivo.getAbsolutePath() + "\nDeseja abrir?", "Impressão", JOptionPane.YES_NO_OPTION);
//            if (response == JOptionPane.YES_OPTION) {
//                if (Desktop.isDesktopSupported()) {
//                    try {
//                        Desktop.getDesktop().open(caminhoGravarArquivo);
//                    } catch (IOException ex) {
//                        JOptionPane.showMessageDialog(null, "Não foi possível abrir o arquivo, "
//                                + "pois nao foi encontrado um aplicativo associado com PDF",
//                                "Erro", JOptionPane.ERROR_MESSAGE);
//                        ex.printStackTrace();
//                    }
//                }
//            }
        } catch (JRException ex) {
            Logger.getLogger(CodigoBarrasJasperReports.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getPosicao() {
        return posicao;
    }

    /**
     * Define a posição que deve começar a impressão das etiquetas
     *
     * @param posicao inicia em 1 até 65
     */
    public void setPosicao(int posicao) {
        if (posicao < 1 || posicao > 65) {
            throw new IllegalArgumentException("Posição " + posicao + "ultrapassou os limites de 1 a 65");
        }
        this.posicao = posicao;
    }

    public static void main(String[] args) {
        EntityManager em = JPA.getEM();
        List itens = em.createQuery("Select e from ItemEstoque e where e.deleted = FALSE").getResultList();
        EtiquetaJasperReports etiquetaJasperReports = new EtiquetaJasperReports();
        etiquetaJasperReports.setPosicao(2);
        etiquetaJasperReports.imprimirEtiquetas(itens);
    }
}
