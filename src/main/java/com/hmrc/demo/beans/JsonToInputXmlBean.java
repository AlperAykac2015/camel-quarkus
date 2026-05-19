package com.hmrc.demo.beans;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

@ApplicationScoped
@Named("jsonToInputXmlBean")
public class JsonToInputXmlBean {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public String convert(String json) throws Exception {
        JsonNode root = objectMapper.readTree(json);
        JsonNode persons = root.get("persons");

        StringBuilder xml = new StringBuilder();
        xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        xml.append("<persons>");

        for (JsonNode person : persons) {
            xml.append("<person>");
            appendElement(xml, "id", person.get("id").asText());
            appendElement(xml, "firstName", person.get("firstName").asText());
            appendElement(xml, "lastName", person.get("lastName").asText());
            appendElement(xml, "email", person.get("email").asText());
            appendElement(xml, "age", person.get("age").asText());
            xml.append("</person>");
        }

        xml.append("</persons>");
        return xml.toString();
    }

    private static void appendElement(StringBuilder xml, String name, String value) {
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
}
