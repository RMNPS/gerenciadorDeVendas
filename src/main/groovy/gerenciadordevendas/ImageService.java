/*
 * GerenciadorDeVendas: ImageService.java
 * Enconding: UTF-8
 * Data de criação: 16/04/2019 11:36:14
 */
package gerenciadordevendas;

import com.google.common.io.Files;
import gerenciadordevendas.model.Empresa;
import gerenciadordevendas.model.EntidadeComImagem;
import gerenciadordevendas.telas.PanelAdicionarImagem;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import javax.persistence.EntityManager;

/**
 *
 * @author Ramon Porto
 */
public class ImageService {

    public String substituirImagem(PanelAdicionarImagem panel, EntidadeComImagem entidade) throws IOException {
        String caminhoImagem = panel.getCaminhoImagem();
        

        if (entidade.getImagem() != null) {
            deletarImagem(entidade);
        }

        if (caminhoImagem != null) {
            String pastaImagens = entidade.getClass().getSimpleName();

            String caminhoProjeto = Paths.get("").toAbsolutePath().toString();
            if (!new File(caminhoProjeto + pastaImagens).exists()) {
                new File(caminhoProjeto + pastaImagens).mkdir();
            }

            String fileExtension = Files.getFileExtension(caminhoImagem);
            int id = entidade.getId();
            if (id == 0) {
                EntityManager em = JPA.getEM();
                id = EntityService.getLastIDplus1(em, Empresa.class);
                id++;
            }
            String caminhoImagemProjeto = pastaImagens + id + fileExtension;

            Files.copy(new File(caminhoImagem), new File(caminhoProjeto + caminhoImagemProjeto));
            
            return caminhoImagemProjeto;
        }
        return null;
    }

    public void deletarImagem(EntidadeComImagem entidade) throws IOException {
        String caminho = Paths.get("").toAbsolutePath().toString();
        
        java.nio.file.Files.deleteIfExists(Paths.get(caminho + entidade.getImagem()));
    }

}
