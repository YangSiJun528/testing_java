package dev.yangsijun;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class Person {
    private final String firstName;
    private final String lastName;
    private final Integer age;

    private Person(Builder builder) {
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.age = builder.age;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Integer getAge() {
        return age;
    }

    public static class Builder {
        private String firstName;
        private String lastName;
        private Integer age;
        private final Map<String, Boolean> isSpecified = new HashMap<>();

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            isSpecified.put("firstName", true);
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            isSpecified.put("lastName", true);
            return this;
        }

        public Builder age(Integer age) {
            this.age = age;
            isSpecified.put("age", true);
            return this;
        }


        public Person build() {
            for (Field field : this.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                try {
                    if (field.get(this) == null && !isSpecified.getOrDefault(field.getName(), false)) {
                        throw new IllegalStateException(field.getName() + " must be specified");
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Reflection failed", e);
                }
            }
            return new Person(this);
        }
    }
}
