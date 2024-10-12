package com.example.DS_Assignment3.security;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class UserDeserializer extends JsonDeserializer<CustomUser> {
    @Override
    public CustomUser deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        ObjectMapper objectMapper = (ObjectMapper) p.getCodec();
        JsonNode rootNode = objectMapper.readTree(p);

        String username = rootNode.get("username").asText();
        String password = rootNode.get("password").asText();

        // Extract roles from the JSON as a list of strings
        JsonNode rolesNode = rootNode.get("role");
        String role = rootNode.get("role").asText();

//        if (rolesNode != null) {
//            for (JsonNode roleNode : rolesNode) {
//                roleList.add(roleNode.asText());
//            }
//        }

        // Create and return a CustomUser object
        CustomUser customUser = new CustomUser(username, password, role);
        return customUser;
    }
}
