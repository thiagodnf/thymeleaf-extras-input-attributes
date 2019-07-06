package thiagodnf.thymeleaf.extras.input.attributes.processor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractAttributeTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.templatemode.TemplateMode;

import thiagodnf.thymeleaf.extras.input.attributes.annotation.HTMLAutoComplete;
import thiagodnf.thymeleaf.extras.input.attributes.annotation.HTMLAutoFocus;
import thiagodnf.thymeleaf.extras.input.attributes.annotation.HTMLMaxLength;
import thiagodnf.thymeleaf.extras.input.attributes.annotation.HTMLMinLength;
import thiagodnf.thymeleaf.extras.input.attributes.annotation.HTMLReadonly;
import thiagodnf.thymeleaf.extras.input.attributes.annotation.HTMLRequired;
import thiagodnf.thymeleaf.extras.input.attributes.annotation.HTMLSpellCheck;
import thiagodnf.thymeleaf.extras.input.attributes.annotation.HTMLStep;

public class InputAttributesProcessor extends AbstractAttributeTagProcessor {

    private static final String ATTR_NAME = "validation";

    private static final int PRECEDENCE = 10000;
    
    private boolean activate;

    public InputAttributesProcessor(final String dialectPrefix, boolean activate) {
        super(TemplateMode.HTML, // This processor will apply only to HTML mode
                dialectPrefix, // Prefix to be applied to name for matching
                null, // No tag name: match any tag name
                false, // No prefix to be applied to tag name
                ATTR_NAME, // Name of the attribute that will be matched
                true, // Apply dialect prefix to attribute name
                PRECEDENCE, // Precedence (inside dialect's precedence)
                true); // Remove the matched attribute afterwards
        
        this.activate = activate;
    }

    protected void doProcess(
            final ITemplateContext context,  
            final IProcessableElementTag elementTag,
            final AttributeName attributeName, 
            final String attributeValue,
            final IElementTagStructureHandler structureHandler) {

        if(!activate) {
            return;
        }
        
        Object target = context.getSelectionTarget();
        
        Class<?> cls = target.getClass();
        
        List<String> tags = new ArrayList<>();
        
        try {
            
            Field field = getField(cls, attributeValue);
            
            if (field == null) {
                return;
            }

            for (Annotation annotation : field.getAnnotations()) {

                if (annotation instanceof DecimalMin) {
                    tags.add(getMin((DecimalMin) annotation));
                }
                
                if (annotation instanceof Min) {
                    tags.add(getMin((Min) annotation));
                }

                if (annotation instanceof DecimalMax) {
                    tags.add(getMax((DecimalMax) annotation));
                }
                
                if (annotation instanceof Max) {
                    tags.add(getMax((Max) annotation));
                }
                
                
                
                if (annotation instanceof HTMLAutoFocus) {
                    tags.add(((HTMLAutoFocus)annotation).tag());
                }
                
                if (annotation instanceof HTMLAutoComplete) {
                    tags.add(getAutoComplete((HTMLAutoComplete) annotation));
                }
                
                
                if (annotation instanceof HTMLStep) {
                    tags.add(getStep((HTMLStep) annotation));
                }
                
                // Required attributes
                
                if (annotation instanceof NotNull) {
                    tags.add("required");
                }

                if (annotation instanceof NotBlank) {
                    tags.add("required");
                }

                if (annotation instanceof NotEmpty) {
                    tags.add("required");
                }
                
                if (annotation instanceof HTMLRequired) {
                    tags.add(((HTMLRequired)annotation).tag());
                }
                
                
                if (annotation instanceof HTMLSpellCheck) {
                    tags.add(getSpellCheck((HTMLSpellCheck) annotation));
                }
                
                if (annotation instanceof HTMLMinLength) {
                    tags.add(getMinLength((HTMLMinLength) annotation));
                }
                
                if (annotation instanceof HTMLMaxLength) {
                    tags.add(getMaxLength((HTMLMaxLength) annotation));
                }
                
                if (annotation instanceof HTMLReadonly) {
                    tags.add(((HTMLReadonly) annotation).tag());
                }
            }


        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        
        for (String tag : tags) {
            structureHandler.setAttribute(tag, null);
        }
    }
    
    private String getMin(DecimalMin decimalMin) {
        return String.format("min=\"%s\"", decimalMin.value());
    }
    
    private String getMin(Min min) {
        return String.format("min=\"%s\"", min.value());
    }
    
    private String getMax(DecimalMax decimalMax) {
        return String.format("max=\"%s\"", decimalMax.value());
    }
    
    private String getMax(Max max) {
        return String.format("max=\"%s\"", max.value());
    }
    
    private String getStep(HTMLStep step) {
        return String.format("step=\"%s\"", step.value());
    }
    
    private String getSpellCheck(HTMLSpellCheck spellCheck) {
        return String.format("spellcheck=\"%s\"", spellCheck.value());
    }
    
    private String getAutoComplete(HTMLAutoComplete autoComplete) {
        return String.format("autocomplete=\"%s\"", autoComplete.value());
    }
    
    private String getMinLength(HTMLMinLength minLength) {
        return String.format("minlength=\"%s\"", minLength.value());
    }
    
    private String getMaxLength(HTMLMaxLength maxLength) {
        return String.format("maxlength=\"%s\"", maxLength.value());
    }
    
    private boolean containsField(Class<?> cls, String fieldName) {
        return Arrays.stream(cls.getDeclaredFields())
                .anyMatch(f -> f.getName().equals(fieldName));
    }
    
    private Field getField(Class<?> cls, String fieldName) throws NoSuchFieldException, SecurityException {

        if (containsField(cls, fieldName)) {
            return cls.getDeclaredField(fieldName);
        }

        if(cls.getSuperclass() != null) {
            return getField(cls.getSuperclass(), fieldName);
        }
        
        return null;
    }
}