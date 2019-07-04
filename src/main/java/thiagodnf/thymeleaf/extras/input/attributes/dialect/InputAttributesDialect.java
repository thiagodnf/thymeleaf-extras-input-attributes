package thiagodnf.thymeleaf.extras.input.attributes.dialect;

import java.util.HashSet;
import java.util.Set;

import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;

import thiagodnf.thymeleaf.extras.input.attributes.processor.InputAttributesProcessor;

public class InputAttributesDialect extends AbstractProcessorDialect {

    private boolean active;
    
    public static final String NAME = "Thymeleaf Extra Input Attributes";

    public static final String PREFIX = "th";

    public static final int PRECEDENCE = 1000;
    
    public InputAttributesDialect() {
        this(true);
    }
    
    public InputAttributesDialect(boolean active) {
        super(NAME, PREFIX, PRECEDENCE);
        
        this.active = active;
    }

    /*
     * Initialize the dialect's processors.
     *
     * Note the dialect prefix is passed here because, although we set "hello" to be
     * the dialect's prefix at the constructor, that only works as a default, and at
     * engine configuration time the user might have chosen a different prefix to be
     * used.
     */
    public Set<IProcessor> getProcessors(final String dialectPrefix) {
        
        final Set<IProcessor> processors = new HashSet<IProcessor>();
        
        processors.add(new InputAttributesProcessor(dialectPrefix, active));
        
        return processors;
    }
}
