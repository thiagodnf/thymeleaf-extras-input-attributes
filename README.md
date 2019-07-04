# Thymeleaf Extras Input Attributes

Thymeleaf dialect for input attributes

## Setup

```xml
<dependency>
    <groupId>com.github.mxab.thymeleaf.extras</groupId>
    <artifactId>thymeleaf-extras-data-attribute</artifactId>
    <version>2.0.1</version>
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
