package com.hmrc.demo.beans;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ApplicationScoped
@Named("personComplexTransformBean")
public class PersonComplexTransformBean {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final Map<String, String> COUNTRY_NAMES = Map.of(
            "GB", "United Kingdom",
            "US", "United States",
            "DE", "Germany",
            "FR", "France",
            "TR", "Türkiye"
    );

    public String transform(String json) throws Exception {
        JsonNode root = objectMapper.readTree(json);
        JsonNode persons = root.get("persons");

        List<EnrichedPerson> successfulPersons = new ArrayList<>();
        List<PersonError> errors = new ArrayList<>();

        for (JsonNode person : persons) {
            int id = person.get("id").asInt();
            String firstName = person.get("firstName").asText();
            String lastName = person.get("lastName").asText();
            String email = person.get("email").asText();
            int age = person.get("age").asInt();
            String countryCode = person.get("countryCode").asText();

            if (age < 18) {
                errors.add(new PersonError(id, "Person is minor and cannot be enriched"));
                continue;
            }

            if (!email.contains("@")) {
                errors.add(new PersonError(id, "Invalid email address"));
                continue;
            }

            String countryName = COUNTRY_NAMES.get(countryCode);

            if (countryName == null) {
                errors.add(new PersonError(id, "Unsupported country code: " + countryCode));
                continue;
            }

            String riskLevel = calculateRiskLevel(age, countryCode);

            successfulPersons.add(new EnrichedPerson(
                    id,
                    firstName + " " + lastName,
                    countryName,
                    riskLevel,
                    true
            ));
        }

        return toXml(successfulPersons, errors);
    }

    private static String calculateRiskLevel(int age, String countryCode) {
        if ("US".equals(countryCode) && age > 70) {
            return "MEDIUM";
        }

        if (age > 80) {
            return "HIGH";
        }

        return "LOW";
    }

    private static String toXml(List<EnrichedPerson> persons, List<PersonError> errors) {
        StringBuilder xml = new StringBuilder();

        xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        xml.append("<people successCount=\"").append(persons.size()).append("\" errorCount=\"").append(errors.size()).append("\">");

        for (EnrichedPerson person : persons) {
            xml.append("<personSummary>");
            element(xml, "personId", String.valueOf(person.id()));
            element(xml, "fullName", person.fullName());
            element(xml, "country", person.country());
            element(xml, "riskLevel", person.riskLevel());
            element(xml, "adult", String.valueOf(person.adult()));
            xml.append("</personSummary>");
        }

        if (!errors.isEmpty()) {
            xml.append("<errors>");
            for (PersonError error : errors) {
                xml.append("<error>");
                element(xml, "personId", String.valueOf(error.personId()));
                element(xml, "message", error.message());
                xml.append("</error>");
            }
            xml.append("</errors>");
        }

        xml.append("</people>");
        return xml.toString();
    }

    private static void element(StringBuilder xml, String name, String value) {
        xml.append("<").append(name).append(">");
        xml.append(escapeXml(value));
        xml.append("</").append(name).append(">");
    }

    private static String escapeXml(String value) {
        if (value == null) {
            return "";
        }

        return value
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&apos;");
    }

    record EnrichedPerson(
            int id,
            String fullName,
            String country,
            String riskLevel,
            boolean adult
    ) {}

    record PersonError(
            int personId,
            String message
    ) {}
}
