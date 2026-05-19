package com.hmrc.demo.beans;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hmrc.demo.error.InvalidAgeException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

@ApplicationScoped
@Named("ageValidationBean")
public class AgeValidationBean {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public String validate(String json) throws Exception {
        JsonNode root = objectMapper.readTree(json);
        JsonNode persons = root.get("persons");

        if (persons == null || !persons.isArray()) {
            return json;
        }

        for (int i = 0; i < persons.size(); i++) {
            JsonNode person = persons.get(i);
            JsonNode ageNode = person.get("age");

            if (ageNode == null || !ageNode.isInt()) {
                throw new InvalidAgeException("Invalid age: age is required and must be an integer.");
            }

            int age = ageNode.asInt();

            if (age <= 0 || age >= 100) {
                throw new InvalidAgeException("Invalid age: AGE must be positive and less than 100.");
            }
        }

        return json;
    }
}
