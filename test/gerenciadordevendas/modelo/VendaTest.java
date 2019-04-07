/*
 * GerenciadorDeVendas: VendaTest.java
 * Enconding: UTF-8
 * Data de criação: 08/03/2018 11:18:32
 */
package gerenciadordevendas.modelo;

import gerenciadordevendas.model.Vendedor;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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
public class VendaTest {
    
    public VendaTest() {
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

    /**
     * Test of setOperador method, of class Venda.
     */
    @Test(expected = NullPointerException.class)
    public void testOperadorNull() {
        System.out.println("testOperadorNull");
        Vendedor operador = null;
        Venda instance = new Venda(Conta.padrao(), operador);
        instance.setOperador(operador);
        
    }

    @Test
    public void testOperador() {
        System.out.println("getOperador");
        Venda instance = new Venda(Conta.padrao(), Vendedor.padrao());
        Vendedor expResult = Vendedor.padrao();
        Vendedor result = instance.getOperador();
        assertEquals(expResult, result);
    }
    
    @Test(expected = NullPointerException.class)
    public void testContaNull() {
        System.out.println("testContaNull");
        Venda instance = new Venda(null, Vendedor.padrao());
    }

    @Test(expected = NullPointerException.class)
    public void testSetContaNull() {
        System.out.println("testSetContaNull");
        Venda instance = new Venda(Conta.padrao(), Vendedor.padrao());
        instance.setConta(null);
    }

    @Test
    public void testConta() {
        System.out.println("testConta");
        Venda instance = new Venda(Conta.padrao(), Vendedor.padrao());
        Conta expResult = Conta.padrao();
        Conta result = instance.getConta();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetListaProdutos() {
        System.out.println("getListaProdutos");
        Venda instance = new Venda(Conta.padrao(), Vendedor.padrao());
        List<ItemVenda> result = instance.getListaProdutos();
        assertEquals(new ArrayList<>(), result);
    }

    @Test(expected = NullPointerException.class)
    public void testAddDescontoNull() {
        System.out.println("addDesconto");
        Desconto d = null;
        Venda instance = new Venda();
        instance.addDesconto(d);
    }
    
    @Test
    public void testAddDesconto() {
        System.out.println("addDesconto");
        
        Venda instance = new Venda();
        Desconto d = new Desconto(instance, BigDecimal.ONE);
        instance.addDesconto(d);
        
        assertEquals(1, instance.getDescontos().size());
        assertEquals(d, instance.getDescontos().get(0));
    }

    /**
     * Test of addItem method, of class Venda.
     */
    @Test
    public void testAddItem() {
        System.out.println("addItem");
        ItemVenda iv = null;
        Venda instance = new Venda();
        instance.addItem(iv);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeItem method, of class Venda.
     */
    @Test
    public void testRemoveItem_ItemVenda() {
        System.out.println("removeItem");
        ItemVenda iv = null;
        Venda instance = new Venda();
        instance.removeItem(iv);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeItem method, of class Venda.
     */
    @Test
    public void testRemoveItem_int() {
        System.out.println("removeItem");
        int index = 0;
        Venda instance = new Venda();
        instance.removeItem(index);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTotal method, of class Venda.
     */
    @Test
    public void testGetTotal() {
        System.out.println("getTotal");
        Venda instance = new Venda();
        BigDecimal expResult = null;
        BigDecimal result = instance.getTotal();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of finalizarVenda method, of class Venda.
     */
    @Test
    public void testFinalizarVenda() {
        System.out.println("finalizarVenda");
        Venda instance = new Venda();
//        instance.finalizarVenda();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setEstado method, of class Venda.
     */
    @Test
    public void testSetEstado() {
        System.out.println("setEstado");
        Venda.Estado estado = null;
        Venda instance = new Venda();
        instance.setEstado(estado);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEstado method, of class Venda.
     */
    @Test
    public void testGetEstado() {
        System.out.println("getEstado");
        Venda instance = new Venda();
        Venda.Estado expResult = null;
        Venda.Estado result = instance.getEstado();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class Venda.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Venda instance = new Venda();
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTipo method, of class Venda.
     */
    @Test
    public void testGetTipo() {
        System.out.println("getTipo");
        Venda instance = new Venda();
        String expResult = "";
        String result = instance.getTipo();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
