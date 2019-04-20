package gerenciadordevendas;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

final class Regras {
    

    /**
     * Tempo em dias de inatividade da Conta
     */
    public static int TEMPO_INATIVIDADE;
    
    /**
     * Tempo em dias até o pagamento da conta
     */
    public static int TEMPO_LIMITE_PAGAMENTO_CONTA;
    /**
     * Quantidade máxima de um produto
     */
    public static int QUANTIDADE_MAXIMA_PRODUTO;
    /**
     * Limite Padrão em Reais
     */
    public static double LIMITE_PADRAO_CONTA;
    /**
     * Limite Padrão para dinheiro informado pelo Cliente.
     */
    public static double LIMITE_DINHEIRO = 1000000;
    
    /**
     * Limite Padrão para descontos em %.
     */
    public static BigDecimal LIMITE_PORCENTAGEM_DESCONTO = 95g;
    
    
    public static String ULTIMO_CAMINHO_IMAGEM = null;
    
    
    
    public static BigDecimal DESCONTO_A_VISTA = 5g
    
    
    static final void load(){
        Properties props = new Properties();
        FileInputStream file;
        try {
            file = new FileInputStream( "./properties/regras.properties");
            props.load(file);
        }  catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        
        TEMPO_INATIVIDADE = Integer.parseInt(props.getProperty("tempo_inatividade"));
        QUANTIDADE_MAXIMA_PRODUTO = Integer.parseInt(props.getProperty("quantidade_maxima_produto"));
        LIMITE_PADRAO_CONTA = Double.parseDouble(props.getProperty("limite_padrao_conta"));
        LIMITE_DINHEIRO = Double.parseDouble(props.getProperty("limite_dinheiro"));	
        TEMPO_LIMITE_PAGAMENTO_CONTA = Integer.parseInt(props.getProperty("tempo_limite_pagamento_conta"));	
        LIMITE_PORCENTAGEM_DESCONTO = new BigDecimal(props.getProperty("limite_porcentagem_desconto")?: 95);
        DESCONTO_A_VISTA =  new BigDecimal(props.getProperty("desconto_a_vista")?: 95);
    }
    
    static final void save(){
        Properties props = new Properties();
        FileInputStream file;
        try {
            file = new FileInputStream( "./properties/regras.properties");
            props.load(file);
        }  catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        props.setProperty("tempo_inatividade", ""+ TEMPO_INATIVIDADE);
        props.setProperty("quantidade_maxima_produto", ""+ QUANTIDADE_MAXIMA_PRODUTO);
        props.setProperty("limite_padrao_conta", ""+ LIMITE_PADRAO_CONTA);
        props.setProperty("limite_dinheiro", ""+ LIMITE_DINHEIRO);
        props.setProperty("tempo_limite_pagamento_conta", ""+ TEMPO_LIMITE_PAGAMENTO_CONTA);
        props.setProperty("limite_porcentagem_desconto", ""+LIMITE_PORCENTAGEM_DESCONTO);
        props.setProperty("desconto_a_vista", ""+DESCONTO_A_VISTA);
        
        try {
            props.store(new FileOutputStream("./properties/regras.properties"), null);
        } catch (IOException ex) {
            Logger.getLogger(Regras.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
