/*
 * GerenciadorDeVendas: InicializadorBaseDeDados.java
 * Enconding: UTF-8
 * Data de criação: 09/04/2019 10:41:26
 */
package gerenciadordevendas;

import ch.vorburger.exec.ManagedProcessException;
import ch.vorburger.mariadb4j.DB;
import ch.vorburger.mariadb4j.DBConfigurationBuilder;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Ramon Porto
 */
public class InicializadorBaseDeDados {

    public void incializar() {
        DBConfigurationBuilder configBuilder = DBConfigurationBuilder.newBuilder();
        configBuilder.setPort(3746); // OR, default: setPort(0); => autom. detect free port
        configBuilder.setDataDir("./data"); // just an example
        if (!isServerUp()) {
            System.out.println("Iniciando Base de dados...");
            try {
                DB db = DB.newEmbeddedDB(configBuilder.build());
                db.start();

                int tentativas = 0;
                while (tentativas < 10) {
                    if (!isServerUp()) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException ex) {
                        }
                        tentativas++;
                        System.out.println("Tentativa " + tentativas + "...");
                    } else {
                        return;
                    }
                }
            } catch (ManagedProcessException ex) {
                Logger.getLogger(InicializadorBaseDeDados.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void incializar2() {
        String command = "cd mysql & initMysql.bat";
        new File(command).exists();
        String s = null;
        if (!isServerUp()) {
            System.out.println("Iniciando Base de dados...");
            try {
                Process p = Runtime.getRuntime().exec(
                        "cmd.exe", new String[]{
                            "/c",
                            "cd mysql",
                            "start initMysql.bat"
                        },
                        null
                );

//                Process p = Runtime.getRuntime().exec(command);
//                BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
//
                BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
//                
//                // read the output from the command
//                System.out.println("Here is the standard output of the command:\n");
//                while ((s = stdInput.readLine()) != null) {
//                    System.out.println(s);
//                }
//
                // read any errors from the attempted command
                System.out.println("Here is the standard error of the command (if any):\n");
                while ((s = stdError.readLine()) != null) {
                    System.out.println(s);
                }

                int tentativas = 0;
                while (tentativas < 10) {
                    if (!isServerUp()) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException ex) {
                        }
                        tentativas++;
                        System.out.println("Tentativa " + tentativas + "...");
                    } else {
                        return;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
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
