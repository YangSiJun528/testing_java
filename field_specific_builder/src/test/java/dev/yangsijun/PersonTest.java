package dev.yangsijun;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PersonTest {

    @Test
    void 필드_미지정_예외() {
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            Person person = new Person.Builder()
                    .firstName("John")
                    .lastName("Doe")
                    .build();
        });
        System.out.println("Person Builder ERROR : " + exception.getMessage());
    }

    @Test
    void 모든_필드_지정_성공() {
        assertDoesNotThrow(() -> {
            Person person = new Person.Builder()
                    .firstName("John")
                    .lastName("Doe")
                    .age(25)
                    .build();
        });
    }

    @Test
    void 널_필드_포함_모든_필드_지정_성공() {
        assertDoesNotThrow(() -> {
            Person person = new Person.Builder()
                    .firstName("John")
                    .lastName(null)
                    .age(25)
                    .build();
        });
    }

    @Test
    void 모든_필드_널_성공() {
        assertDoesNotThrow(() -> {
            Person person = new Person.Builder()
                    .firstName(null)
                    .lastName(null)
                    .age(null)
                    .build();
        });
    }
}
