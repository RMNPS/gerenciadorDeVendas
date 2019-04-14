/*
 * GerenciadorDeVendas: ColunasItemEstoque.groovy
 * Enconding: UTF-8
 * Data de criação: 27/09/2018 12:10:51
 */

package gerenciadordevendas.tablemodel

/**
 *
 * @author Ramon Porto
 */
enum ColunasItemEstoque {
	ID_ESTOQUE("id Estoque"),
        ID_PRODUTO("id Produto"),
        NOME("Nome"),
        FORNECEDOR("Fornecedor"),
        QUANTIDADE("QNT"),
        QUANTIDADE_PRODUTO("QNT Produto"),
        PRECO_CUSTO('R$ Custo'),
        PRECO_A_PRAZO('R$ Venda à Prazo'),
        PRECO_A_VISTA('R$ Venda à Vista'),
        NUMERO_PARCELAS('Nº Parcelas'),
        PRECO_PARCELA('R$ Parcela'),
//        VALIDADE("Validade");

        String nome;

        private ColunasItemEstoque(String nome) {
            this.nome = nome;
        }
}

