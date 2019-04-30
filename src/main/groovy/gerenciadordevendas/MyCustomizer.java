/*
 * GerenciadorDeVendas: MyCustomizer.java
 * Enconding: UTF-8
 * Data de criação: 30/04/2019 15:19:12
 */
package gerenciadordevendas;

import org.eclipse.persistence.config.DescriptorCustomizer;
import org.eclipse.persistence.descriptors.ClassDescriptor;

/**
 *
 * @author Ramon Porto
 */
public class MyCustomizer implements DescriptorCustomizer {
    @Override
    public void customize(ClassDescriptor descriptor) throws Exception {
        try {
           descriptor.getQueryManager().setDeleteSQLString("UPDATE item SET ACTIVE = '0' WHERE id = #ID");
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
