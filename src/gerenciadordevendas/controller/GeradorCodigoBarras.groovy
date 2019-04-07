/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gerenciadordevendas.controller;

import gerenciadordevendas.model.Cartao;

/**
 *
 * @author Gabriel
 */
public interface GeradorCodigoBarras {

    String gerarCodigoValido();

    void imprimirCartao(Cartao c);
    
}
