/*
 * GerenciadorDeVendas: EstadoContaFluxo.groovy
 * Enconding: UTF-8
 * Data de criação: 19/09/2018 17:25:00
 */

package gerenciadordevendas.model

import java.sql.Timestamp
import java.awt.Color
import java.awt.Font
import java.awt.Component

/**
 *
 * @author Ramon Porto
 */
final class EstadoContaFluxo extends RegistroDeFluxo {

    final String estado

    EstadoContaFluxo(String estado) {
        this.estado = estado
        this.dataCriacao = new Timestamp(Calendar.instance.timeInMillis)
    }

    @Override
    void paintCell(Component c) {
        c.background = new Color(239, 83, 80)
        c.foreground = Color.WHITE
        c.font = c.font.deriveFont(Font.BOLD)
    }
    
    @Override
    BigDecimal getTotal() { null }
    
    @Override
    String getTipo() { return estado }
}

