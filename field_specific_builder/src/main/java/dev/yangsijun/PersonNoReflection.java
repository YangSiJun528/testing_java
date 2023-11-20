package dev.yangsijun;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class PersonNoReflection {
    private final String firstName;
    private final String lastName;
    private final Integer age;

    private PersonNoReflection(Builder builder) {
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

        private final Set<String> allowedFields = new HashSet<>();

        public Builder() {
            // Define the allowed fields
            allowedFields.add("firstName");
            allowedFields.add("lastName");
            allowedFields.add("age");
        }

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            allowedFields.remove("firstName");
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            allowedFields.remove("lastName");
            return this;
        }

        public Builder age(Integer age) {
            this.age = age;
            allowedFields.remove("age");
            return this;
        }

        public PersonNoReflection build() {
            validateFields();
            return new PersonNoReflection(this);
        }

        private void validateFields() {
            if (!allowedFields.isEmpty()) {
                throw new IllegalStateException("Fields not specified: " + allowedFields);
            }
        }
    }
}
