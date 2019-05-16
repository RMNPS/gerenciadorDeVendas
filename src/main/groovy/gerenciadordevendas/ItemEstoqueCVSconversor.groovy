/*
 * GerenciadorDeVendas: ItemEstoqueCVSconversor.groovy
 * Enconding: UTF-8
 * Data de criação: 16/05/2019 16:33:01
 */

package gerenciadordevendas

import gerenciadordevendas.model.ItemEstoque

/**
 *
 * @author Ramon Porto
 */
class ItemEstoqueCVSconversor {
    def converte(List<ItemEstoque> itens) {
        File file = new File("out.txt")
        file.write "First line\n"
        file << "Second line\n"
        
        for (item in itens) {
            def dado = '' << item.id << '\t' << item.
            file.write 
        }
    }
}

