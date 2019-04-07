/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gerenciadordevendas.conversao;

/**
 *
 * @author ramon
 * @param <T1> Objeto de entrada
 * @param <T2> Objeto de saída
 */
public interface Conversao<T1,T2> {
    T2 converte(T1 objeto) throws ConversaoException;
}
