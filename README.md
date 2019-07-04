# Thymeleaf Extras Input Attributes

Thymeleaf dialect for input attributes

## Setup

```xml
<dependency>
	<groupId>thiagodnf.thymeleaf.extras.input.attributes</groupId>
	<artifactId>thymeleaf-extras-input-attributes</artifactId>
	<version>1.0.0</version>
</dependency>
```

## Code

```java
@Configuration
public class ThymeleafConfiguration {
	
    @Bean
    public InputAttributesDialect getInputAttributesDialect() {
	    return new InputAttributesDialect(true);
    }
}
```

## Usage

### Source

```java
public class SignupDTO {

    @NotBlank
    @HTMLAutoFocus
    @HTMLSpellCheck("false")
    @HTMLAutoComplete("given-name")
    private String firstname;
    
}
```

### Result

```html
<input type="text" id="firstname" name="firstname" required="" autofocus="" spellcheck="false" autocomplete="given-name">
```
