package gerenciadordevendas.controller;

import java.io.FileOutputStream
//import com.itextpdf.text.Chunk
//import com.itextpdf.text.Document
//import com.itextpdf.text.DocumentException
//import com.itextpdf.text.Font
//import com.itextpdf.text.Image
//import com.itextpdf.text.PageSize
//import com.itextpdf.text.Paragraph
//import com.itextpdf.text.Phrase
//import com.itextpdf.text.pdf.BarcodeEAN
//import com.itextpdf.text.pdf.PdfContentByte
//import com.itextpdf.text.pdf.PdfWriter
import gerenciadordevendas.JPA
import gerenciadordevendas.model.Cartao
import java.awt.Desktop
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import javax.persistence.EntityManager
import javax.persistence.Query
import javax.swing.JOptionPane

class CodigoBarrasIText implements GeradorCodigoBarras {

    @Override
    void imprimirCartao(Cartao c) {
//
//        println("Barcode Linha de Código ");
//        String path = "./impressao/Codigo_Barras" + c.id + ".pdf";
//        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
//
//        try {
//
//            /* Aqui começamos a utilizar as classes do iText: o documento
//                criado acima será direcionado para um arquivo PDF. */
//            FileOutputStream out = new FileOutputStream(path);
//            PdfWriter writer = PdfWriter.getInstance(document, out);
//
//            //abrindo o documento.
//            document.open();
//
//            Paragraph paragraph = new Paragraph("CÓDIGO DE BARRAS");
//            paragraph.alignment = Paragraph.ALIGN_CENTER;
//            document.add(paragraph);
//
//            paragraph = new Paragraph(c.cliente.nome);
//            Font clienteFont = new Font();
//            clienteFont.size = 16;
//            paragraph.font = clienteFont;
//            document.add(paragraph);
//
//            document.add(new Paragraph(" "));
//            document.add(new Paragraph(" "));
//            document.add(new Paragraph(" "));
//
//            //Comecando a configurar o cod de barras
//            PdfContentByte cb = writer.directContent;
//
//            BarcodeEAN codeEAN = new BarcodeEAN();
//
//            //O iText suporta os principais tipos de código de barra, como Barcode39,
//            //  Barcode128 (128, 128_UCC, 128_RAW),  BarcodeEAN (EAN13, EAN8, UPCA, UPCE), EANSUP, etc
//            codeEAN.codeType = codeEAN.EAN13;
//
//            codeEAN.code = c.codigoBarras;
//
//            Image imageEAN = codeEAN.createImageWithBarcode(cb, null, null);
//            document.add(new Phrase(new Chunk(imageEAN, 0, 0)));
//
//            int response = JOptionPane.showConfirmDialog(null, "Cartão impresso em PDF em:\n"
//                    + new File(path).getAbsolutePath() + "\nDeseja abrir?", "Impressão", JOptionPane.YES_NO_OPTION);
//            if (response == JOptionPane.YES_OPTION && Desktop.isDesktopSupported()) {
//
//                try {
//                    Desktop.desktop.open(new File(path));
//                } catch (IOException ex) {
//                    JOptionPane.showMessageDialog(null, "Não foi possível abrir o arquivo, "
//                            + "pois nao foi encontrado um aplicativo associado com PDF",
//                            "Erro", JOptionPane.ERROR_MESSAGE);
//                    throw new RuntimeException(ex);
//                }
//            }
//        } catch (FileNotFoundException | DocumentException ex) {
//            throw new RuntimeException(ex);
//        }
//        document.close();
//
    }

    @Override
    String gerarCodigoValido() {
        String stringCode;
        int tentativas = 0;
        while (true) {
            tentativas++;
            long code = (long) (1000000000000L + Math.random() * 8999999999999L);
            stringCode = String.valueOf(code);
            if (isValidBarCodeEAN(stringCode)) {
                break;
            }
        }
        println("Tentativas de validação: " + tentativas);
        println("Código válido = " + stringCode);
        return stringCode;
    }

    private static boolean isValidBarCodeEAN(String barCode) {
        Query query = JPA.getEM().createQuery("SELECT c FROM Cartao c WHERE c.codigoBarras = :codigoBarras");
        if (barCode.length() == 8 || barCode.length() == 13) {
            String checkSum = "131313131313"
            int sum = 0
            int digit = Integer.parseInt("" + barCode.charAt(barCode.length() - 1))
            String ean = barCode.substring(0, barCode.length() - 1)

            for (int i = 0; i <= ean.length() - 1; i++) {
                sum += (Integer.parseInt("" + ean.charAt(i))) * (Integer.parseInt("" + checkSum.charAt(i)))
            }
            int calculated = 10 - (sum % 10)
            return (digit == calculated) && query.setParameter("codigoBarras", barCode).getResultList().isEmpty()
        } else {
            return false;
        }
    }
}
