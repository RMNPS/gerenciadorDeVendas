/*
 * Data de criação: 01/02/2019 11:10:56
 */
package gerenciadordevendas;


import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Ramon Porto
 */
public final class FilesWindowOpener {

    private FilesWindowOpener() {
    }

    public static File getCaminhoSelecionarImagem(String caminhoPadrao) {
        JFileChooser fileChooser = caminhoPadrao == null ? new JFileChooser() : new JFileChooser(caminhoPadrao);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Imagem (*.png, *.jpeg, *.jpg)", "png", "jpeg", "jpg"));

        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        }
        return null;
    }
    public static File getCaminhoSelecionarImagem() {
       return getCaminhoSelecionarImagem(null);
    }
    
    public static File getCaminhoSelecionarArquivo(FileNameExtensionFilter extensionFilter) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(extensionFilter);

        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        }
        return null;
    }

    public static File getCaminhoGravarArquivo(FileNameExtensionFilter extensionFilter) {
        return getCaminhoGravarArquivo(null, extensionFilter);
    }

    /** @param nomeSugeridoComExt Nome sugerido com extensão. Ex.: arquivo.jspv
     * @param extensionFilter Filtro de extensão
     * @return Retorna o caminho de onde se quer guardar o arquivo
     */
    public static File getCaminhoGravarArquivo(String nomeSugeridoComExt, FileNameExtensionFilter extensionFilter) {
        JFileChooser fileChooser = new JFileChooser() {
            @Override
            public void approveSelection() {
                if (getSelectedFile().exists()) {
                    int resposta = JOptionPane.showConfirmDialog(null, "O arquivo já existe. Deseja sobrescrevê-lo?", "JSPV", JOptionPane.YES_NO_OPTION);
                    if (resposta != JOptionPane.YES_OPTION) {
                        return;
                    }
                }
                super.approveSelection();
            }
        };
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(extensionFilter);
        if (nomeSugeridoComExt != null) {
            fileChooser.setSelectedFile(new File("./" + nomeSugeridoComExt));
        }
        if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        }
        return null;
    }

    public static File[] getMultiplosArquivos() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(true);
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Arquivo Excel (*.xlsx)", "xlsx"));

        if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFiles();
        }
        return null;
    }
    
    public static String getCaminhoPasta() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);
        if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile().getAbsolutePath();
        }
        return null;
    }
    
}
