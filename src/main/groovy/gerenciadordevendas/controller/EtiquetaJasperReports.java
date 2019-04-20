/*
 * GerenciadorDeVendas: ImpressaoCodigoBarras.java
 * Enconding: UTF-8
 * Data de criação: 14/02/2018 11:43:04
 */
package gerenciadordevendas.controller;

import gerenciadordevendas.model.ItemEstoque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Ramon Porto
 */
public class EtiquetaJasperReports {

    private static final String CAMINHO_JASPER = "./print/etiquetas_pequenas.jasper";
    private int posicao;

    public void visualizarEtiquetas(List<ItemEstoque> itensAvisualizar) throws JRException {
        JasperPrint print = geraPrint(itensAvisualizar);

        JasperViewer.viewReport(print, false);
    }

    public void imprimirEtiquetas(List<ItemEstoque> itensAimprimir) throws JRException {
        JasperPrint print = geraPrint(itensAimprimir);

        JasperPrintManager.printReport(print, true);
    }

    private JasperPrint geraPrint(List<ItemEstoque> itensAimprimir) throws JRException {
        Map<String, Object> parametros = geraParametros(itensAimprimir);
        JasperPrint print = JasperFillManager.fillReport(CAMINHO_JASPER, parametros, new JREmptyDataSource());
        return print;
    }

    private Map<String, Object> geraParametros(List<ItemEstoque> itensAimprimir) {

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
        parametros.put("ITENS", itens);
        return parametros;
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
}
