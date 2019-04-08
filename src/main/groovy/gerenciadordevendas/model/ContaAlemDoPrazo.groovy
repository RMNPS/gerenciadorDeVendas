/*
 * GerenciadorDeVendas: ContaAlemDoPrazo1.groovy
 * Enconding: UTF-8
 * Data de criação: 19/09/2018 17:15:31
 */

package gerenciadordevendas.model

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.Optional;

class ContaAlemDoPrazo implements AvaliacaoConta {

    private static final long DIA_COMPLETO = 24l * 60l * 60l * 1000l;
    
    @Override
    Optional<String> avaliar(Conta c) {

//        Date primeiraCompraAposPagamento = null;
//        LinkedList<RegistroDeFluxo> fluxoCliente = new LinkedList<>();
//
//        fluxoCliente += c.vendas;
//        fluxoCliente += c.pagamentos;
//
//        Collections.sort(fluxoCliente, {o1, o2
//                -> o1.dataCriacao.compareTo(o2.dataCriacao)});
//
//        if (!c.pagamentos.isEmpty()) {
//            LinkedList<Pagamento> pagamentos = new LinkedList<>(c.pagamentos);
//            Pagamento ultimoPagamento = pagamentos.last;
//
//            int indexPagamento = fluxoCliente.indexOf(ultimoPagamento);
//
//            for (int i = indexPagamento; i < fluxoCliente.size(); i++) {
//                if (fluxoCliente.get(i) instanceof Venda) {
//                    primeiraCompraAposPagamento = fluxoCliente.get(i).dataCriacao;
//                    break;
//                }
//            }
//        } else if (!c.vendas.isEmpty()) {
//            LinkedList<Venda> vendas = new LinkedList<>(c.vendas);
//
//            primeiraCompraAposPagamento = vendas.first.dataCriacao;
//        }
//
//        if (primeiraCompraAposPagamento != null) {
//            Calendar calendar = Calendar.instance;
//            calendar.add(Calendar.DAY_OF_MONTH, -c.prazo);
//
//            if (primeiraCompraAposPagamento.before(calendar.time)) {
//                long tempoUltrapassado = calendar.time.time - primeiraCompraAposPagamento.time;
//
//                return Optional.of("A conta ultrapassou " + tempoUltrapassado / DIA_COMPLETO + " dias o prazo limite de " + c.prazo + " dias");
//            }
//        }
//
//        return Optional.empty();
    }

}


