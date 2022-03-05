import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

public class AnnotationValidator {
    private final Map<Class<? extends Annotation>, Rule<?>> rules;

    public AnnotationValidator(List<Rule<?>> rules) {
        this.rules = rules.stream()
            .collect(toMap(Rule::getAnnotationClass, identity()));
    }


    public void validate(Object entity) {
        getAllFieldsList(entity.getClass())
            .stream()
            .peek(f -> f.setAccessible(true))
            .forEach(field -> {
                Annotation[] annotations = field.getAnnotations();
                if (annotations.length == 0) return;

                Object fieldValue = getValue(field, entity);
                for (Annotation annotation : annotations) {
                    doCheck(annotation, field.getName(), fieldValue);
                }
            });
    }

    public static List<Field> getAllFieldsList(final Class<?> cls) {
               AnnotationValidator.notNull(cls, "cls");
               final List<Field> allFields = new ArrayList<>();
                Class<?> currentClass = cls;
                while (currentClass != null) {
                        final Field[] declaredFields = currentClass.getDeclaredFields();
                       Collections.addAll(allFields, declaredFields);
                       currentClass = currentClass.getSuperclass();
                    }
                return allFields;
            }

    public static <T> T notNull(final T object, final String message, final Object... values) {
               return Objects.requireNonNull(object, () -> String.format(message, values));
           }

    private void doCheck(Annotation annotation, String field, Object fieldValue) {
        Rule r = rules.get(annotation.annotationType());
        if (r == null) return;

        r.check(annotation, field, fieldValue);
    }

    private Object getValue(Field field, Object entity) {
        try {
            return field.get(entity);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }
}