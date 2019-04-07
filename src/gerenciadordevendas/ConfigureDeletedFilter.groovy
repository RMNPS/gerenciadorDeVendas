/*
 * GerenciadorDeVendas: ConfigureBsFilter.java
 * Enconding: UTF-8
 * Data de criação: 08/03/2018 16:43:08
 */
package gerenciadordevendas;

import java.util.List;
import org.eclipse.persistence.descriptors.ClassDescriptor;
import org.eclipse.persistence.expressions.Expression;
import org.eclipse.persistence.expressions.ExpressionBuilder;
import org.eclipse.persistence.mappings.DatabaseMapping;
import org.eclipse.persistence.mappings.OneToManyMapping;
import org.eclipse.persistence.sessions.factories.DescriptorCustomizer;

/**
 *
 * @author Ramon Porto
 */
public class ConfigureDeletedFilter implements DescriptorCustomizer {

    @Override
    public void customize(ClassDescriptor descriptor) throws Exception {
        List<DatabaseMapping> mappings = descriptor.getMappings();
        for (DatabaseMapping mapping1 : mappings) {
            
            if (mapping1.isOneToManyMapping()) {
                OneToManyMapping mapping = (OneToManyMapping) mapping1;
                String mappedBy = mapping.getMappedBy();
                ExpressionBuilder eb = new ExpressionBuilder(mapping
                        .getReferenceClass());
                Expression fkExp = eb.getField(mappedBy).equal(eb.getParameter("conta"));
                Expression activeExp = eb.get("deleted").equal(false);

                mapping.setSelectionCriteria(fkExp.and(activeExp));
            }
        }
    }
}
