/*
 * GerenciadorDeVendas: ImpressaoCodigoBarras.java
 * Enconding: UTF-8
 * Data de criação: 14/02/2018 11:43:04
 */
package gerenciadordevendas.controller;

import gerenciadordevendas.JPA;
import gerenciadordevendas.model.Cartao;
import gerenciadordevendas.model.Cliente;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.Exporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;

/**
 *
 * @author Ramon Porto
 * @deprecated precisa ser consertado
 */
@Deprecated
public class CodigoBarrasJasperReports {

    private static final String CAMINHO_JASPER = "./print/clienteReport.jasper";

    public void imprimirCartao(Cartao cartao) {
        String out = "./impressao/codigo_barras" + cartao.getCliente().getId() + ".pdf";
        
        Map<String, Object> parametros = new HashMap<>();

        try {
            JasperPrint print = JasperFillManager.fillReport(CAMINHO_JASPER, parametros,
                    new JRBeanCollectionDataSource(Collections.singletonList(cartao)));

            Exporter exporter = new JRPdfExporter();
            exporter.exporterInput = new SimpleExporterInput(print);
            exporter.exporterOutput = new SimpleWriterExporterOutput(new FileOutputStream(out));
            exporter.exportReport();
            
            int response = JOptionPane.showConfirmDialog(null, "Cartão impresso em PDF em:\n"
                    + new File(out).getAbsolutePath() + "\nDeseja abrir?", "Impressão", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                if (Desktop.isDesktopSupported()) {
                    try {
                        Desktop.getDesktop().open(new File(out));
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Não foi possível abrir o arquivo, "
                                + "pois nao foi encontrado um aplicativo associado com PDF", 
                                "Erro", JOptionPane.ERROR_MESSAGE);
                        ex.printStackTrace();
                    }
                }
            }
        } catch (JRException | FileNotFoundException ex) {
            Logger.getLogger(CodigoBarrasJasperReports.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String gerarCodigoValido() {
        EntityManager em = JPA.getEM();
        String stringCode;
        int tentativas = 0;
        while (true) {
            tentativas++;
            long code = (long) (1000000000000L + Math.random() * 8999999999999L);
            stringCode = String.valueOf(code);
            if (isValidBarCodeEAN(stringCode, em)) {
                break;
            }
        };
        println("Tentativas de validação: $tentativas");
        println("Código válido = $stringCode");
        return stringCode;
    }

    private static boolean isValidBarCodeEAN(String barCode, EntityManager em) {
        Query query = em.createQuery("SELECT c FROM Cartao c WHERE c.codigoBarras = :codigoBarras");
        if (barCode.length() == 8 || barCode.length() == 13) {
            String checkSum = "131313131313";
            int sum = 0;
            int digit = Integer.parseInt("" + barCode.charAt(barCode.length() - 1));
            String ean = barCode.substring(0, barCode.length() - 1);

            for (int i = 0; i <= ean.length() - 1; i++) {
                sum += (Integer.parseInt("" + ean.charAt(i))) * (Integer.parseInt("" + checkSum.charAt(i)));
            }
            int calculated = 10 - (sum % 10);
            return (digit == calculated) && query.setParameter("codigoBarras", barCode).getResultList().isEmpty();
        }
        return false;
    }
}
