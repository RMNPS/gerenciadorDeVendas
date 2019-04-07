/*
 * GerenciadorDeVendas: RegistroDeFluxo.groovy
 * Enconding: UTF-8
 * Data de criação: 21/09/2018 11:19:03
 */

package gerenciadordevendas.model

import java.awt.Color
import java.awt.Component
import java.awt.Font
import java.math.BigDecimal
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class RegistroDeFluxo extends BaseEntity implements Comparable<RegistroDeFluxo>{
    
    abstract BigDecimal getTotal();
    
    String getTipo() { "" }
    
    void paintCell(Component c) {
        c.background = Color.WHITE
        c.foreground = Color.BLACK
        c.font = c.font.deriveFont(Font.PLAIN)
    }  
    
    @Override
    int compareTo(RegistroDeFluxo o) { this.dataCriacao <=> o.dataCriacao }
}


