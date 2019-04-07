/*
 * GerenciadorDeVendas: DescontoTest.java
 * Enconding: UTF-8
 * Data de criação: 08/03/2018 11:20:32
 */
package gerenciadordevendas.modelo;

import gerenciadordevendas.model.Vendedor;
import gerenciadordevendas.Regras;
import gerenciadordevendas.exception.TransacaoException;
import java.awt.Component;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.GregorianCalendar;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Ramon Porto
 */
public class DescontoTest {
    
    public DescontoTest() {
        Regras.load();
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    @Test(expected = NullPointerException.class)
    public void textConstructorRecebeValorNull(){
        Desconto instance = new Desconto(new Conta(), null);
    }
    
    @Test(expected = TransacaoException.class)
    public void textConstructorRecebeValorNegativo(){
        BigDecimal valor = new BigDecimal("-1");
        Desconto instance = new Desconto(new Conta(), valor);
    }
    
    @Test(expected = TransacaoException.class)
    public void textConstructorRecebeValorMaiorLimite(){
        BigDecimal valor = new BigDecimal(Regras.LIMITE_DINHEIRO + 1);
        Desconto instance = new Desconto(new Conta(), valor);
    }

     @Test
    public void testAplicarMontante() {
        System.out.println("aplicar");
        BigDecimal montante = new BigDecimal("10");
        Desconto instance = new Desconto(new Conta(), new BigDecimal("5"));
        BigDecimal result = instance.aplicar(montante);
        assertEquals(new BigDecimal("5"), result);
        
        //
        instance = new Desconto(new Conta(), new BigDecimal("5"));
        result = instance.aplicar(result);
        assertEquals(new BigDecimal("0"), result);
    }
    
    
    @Test(expected = TransacaoException.class)
    public void testAplicarMontanteNegativo() {
        BigDecimal montante = new BigDecimal("-1");

        new Desconto().aplicar(montante);
    }

    /**
     * Test of getVenda method, of class Desconto.
     */
    @Test
    public void testeVenda() {
        System.out.println("testeVenda");
        Venda venda = new Venda(Conta.padrao(), Vendedor.padrao());
        Desconto instance = new Desconto(venda, BigDecimal.ONE);
        Venda result = instance.getVenda();
        assertEquals(venda, result);
        
    }

    /**
     * Test of setVenda method, of class Desconto.
     */
    @Test(expected = NullPointerException.class)
    public void testVendaNull() {
        System.out.println("testVendaNull");
        Venda venda = null;
        Desconto instance = new Desconto(venda, BigDecimal.ONE);
    }

    @Test(expected = NullPointerException.class)
    public void testContaNull() {
        System.out.println("testContaNull");
        Conta conta = null;
        Desconto instance = new Desconto(conta, BigDecimal.ONE);
    }

    @Test
    public void testContaEquals() {
        System.out.println("testContaEquals");
        Conta c = new Conta();
        Desconto instance = new Desconto(c, BigDecimal.ONE);
        Conta result = instance.getConta();
        assertEquals(c, result);
    }

    /**
     * Test of getValor method, of class Desconto.
     */
    @Test
    public void testGetValor() {
        System.out.println("getValor");
        Desconto instance = new Desconto(new Conta(), new BigDecimal("33"));
        BigDecimal expResult = new BigDecimal("33");
        BigDecimal result = instance.getValor();
        assertEquals(expResult, result);
    }

    /**
     * Test of getTotal method, of class Desconto.
     */
    @Test
    public void testGetTotal() {
        System.out.println("getTotal");
        Desconto instance = new Desconto(new Conta(), new BigDecimal("100"));
        BigDecimal expResult = new BigDecimal("100");
        BigDecimal result = instance.getTotal();
        assertEquals(expResult, result);
    }

    /**
     * Test of compareTo method, of class Desconto.
     */
    @Test
    public void testCompareTo() {
        System.out.println("compareTo");
        
        Timestamp data1 = new Timestamp(new GregorianCalendar(2018, 1, 1).getTimeInMillis());
        Timestamp data2 = new Timestamp(new GregorianCalendar(2018, 1, 1).getTimeInMillis());
        
        RegistroDeFluxo o = new Desconto();
        o.setDataCriacao(data1);
        Desconto instance = new Desconto();
        instance.setDataCriacao(data2);
        int expResult = 0;
        int result = instance.compareTo(o);
        assertEquals(expResult, result);
        
        data2 = new Timestamp(new GregorianCalendar(2018, 0, 1).getTimeInMillis());
        instance.setDataCriacao(data2);
        assertEquals(-1, instance.compareTo(o));
        
        data2 = new Timestamp(new GregorianCalendar(2018, 3, 1).getTimeInMillis());
        instance.setDataCriacao(data2);
        assertEquals(1, instance.compareTo(o));
    }

    /**
     * Test of paintCell method, of class Desconto.
     */
    @Test(expected = NullPointerException.class)
    public void testPaintCell() {
        System.out.println("paintCell");
        Component c = null;
        Desconto instance = new Desconto();
        instance.paintCell(c);
    }

    /**
     * Test of getTipo method, of class Desconto.
     */
    @Test
    public void testGetTipo() {
        System.out.println("getTipo");
        Desconto instance = new Desconto();
        String expResult = "Desconto";
        String result = instance.getTipo(); //Usado para exibição em tela
        assertEquals(expResult, result);
    }

    
}
