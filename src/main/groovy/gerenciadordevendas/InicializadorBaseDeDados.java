/*
 * GerenciadorDeVendas: InicializadorBaseDeDados.java
 * Enconding: UTF-8
 * Data de criação: 09/04/2019 10:41:26
 */
package gerenciadordevendas;

import java.io.File;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author Ramon Porto
 */
public class InicializadorBaseDeDados {
    public void incializar() {
        String command = "./mysql/initMysql.bat";
        new File(command).exists();
        
        if (!isServerUp()) {
            System.out.println("Iniciando Base de dados...");
            try {
                Runtime.getRuntime().exec(command);
            } catch (IOException e) {
                throw new RuntimeException("Ocorreu um erro ao iniciar a base de dados", e);
            }
        }
    }
    
    public boolean isServerUp() {
        try {
            Socket socket = new Socket("127.0.0.1", 3746);
            socket.close();
            return true;
        } catch (IOException e) {
            return false;
        }

    }
}
