/*
 * GerenciadorDeVendas: ImpressaoCodigoBarras.java
 * Enconding: UTF-8
 * Data de criação: 14/02/2018 11:43:04
 */
package gerenciadordevendas.controller;

import gerenciadordevendas.JPA;
import gerenciadordevendas.model.ItemEstoque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.HashPrintServiceAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.PrintServiceAttributeSet;
import javax.print.attribute.standard.MediaSizeName;
import javax.swing.JFrame;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimplePrintServiceExporterConfiguration;
import net.sf.jasperreports.swing.JRViewer;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Ramon Porto
 */
public class EtiquetaJasperReports {

    private static final String CAMINHO_JASPER = "./print/etiquetas_pequenas.jasper";
    private int posicao;

    public void imprimirEtiquetas(List<ItemEstoque> itensAimprimir) {
//        File caminhoGravarArquivo = gerenciadordevendas.FilesWindowOpener.getCaminhoGravarArquivo(new FileNameExtensionFilter("Arquivo PDF (*.pdf)", "pdf"));
//        String out = "./impressao/etiquetas_pequenas.pdf";
//        if (!caminhoGravarArquivo.getAbsolutePath().endsWith(".pdf")) {
//            caminhoGravarArquivo = new File(caminhoGravarArquivo.getAbsolutePath() + ".pdf");
//        }
        Map<String, Object> parametros = new HashMap<>();

        List<ItemEstoque> itens = new ArrayList<>();

        for (int i = 0; i < posicao - 1; i++) {
            itens.add(ItemEstoque.newItemEmBranco());
        }
        for (ItemEstoque ie : itensAimprimir) {

            for (int i = 0; i < ie.getQuantidade(); i++) {
                itens.add(ie);
            }
        }
        itens.addAll(itensAimprimir);

        parametros.put("ITENS", itens);
        try {
            JasperPrint print = JasperFillManager.fillReport(CAMINHO_JASPER, parametros, new JREmptyDataSource());
//            print.setLeftMargin(0);
//            print.setRightMargin(posicao);

//            JasperPrintManager.printPage(print, 0, true);
            JasperViewer.viewReport(print, false);
//            JFrame frame = new JFrame("Report");
//            JRViewer viwer = new JRViewer(print);
//            viwer.setSize(800, 600);
//            frame.getContentPane().add(viwer);
//            frame.setLocationRelativeTo(null);
//            frame.pack();
//            frame.setVisible(true);
        } catch (JRException ex) {
            Logger.getLogger(CodigoBarrasJasperReports.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void print(JasperPrint print) throws JRException {
        long start = System.currentTimeMillis();
        PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
        printRequestAttributeSet.add(MediaSizeName.ISO_A4);

        PrintServiceAttributeSet printServiceAttributeSet = new HashPrintServiceAttributeSet();
        //printServiceAttributeSet.add(new PrinterName("Epson Stylus 820 ESC/P 2", null));
        //printServiceAttributeSet.add(new PrinterName("hp LaserJet 1320 PCL 6", null));
        //printServiceAttributeSet.add(new PrinterName("PDFCreator", null));

        JRPrintServiceExporter exporter = new JRPrintServiceExporter();

        exporter.setExporterInput(new SimpleExporterInput(print));
        SimplePrintServiceExporterConfiguration configuration = new SimplePrintServiceExporterConfiguration();
        configuration.setPrintRequestAttributeSet(printRequestAttributeSet);
        configuration.setPrintServiceAttributeSet(printServiceAttributeSet);
        configuration.setDisplayPageDialog(false);
        configuration.setDisplayPrintDialog(true);
        exporter.setConfiguration(configuration);
        exporter.exportReport();

        System.err.println("Printing time : " + (System.currentTimeMillis() - start));
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
