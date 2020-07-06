/*
 * Copyright 2020, Yahoo Inc.
 * Licensed under the Apache License, Version 2.0
 * See LICENSE file in project root for terms.
 */
package com.yahoo.elide.contrib.dynamicconfighelpers;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.hjson.JsonValue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.InputStreamReader;
import java.io.Reader;

public class DynamicConfigSchemaValidatorTest {

    private static final String TABLE = "table";
    private static final String SECURITY = "security";
    private static final String VARIABLE = "variable";

    // Security config test
    @DisplayName("Valid Security config")
    @ParameterizedTest
    @ValueSource(strings = {
            "/security/valid/security.hjson",
            "/models/security.hjson",
            "/security/valid/security.json"})
    public void testValidSecuritySchema(String resource) throws Exception {
        String jsonConfig = loadHjsonFromClassPath(resource);
        assertTrue(DynamicConfigSchemaValidator.verifySchema(SECURITY, jsonConfig));
    }

    @DisplayName("Invalid Security config")
    @ParameterizedTest
    @ValueSource(strings = {
            "/security/invalid/security.json",
            "/security/invalid/security.hjson"})
    public void testInvalidSecuritySchema(String resource) throws Exception {
        String jsonConfig = loadHjsonFromClassPath(resource);
        assertFalse(DynamicConfigSchemaValidator.verifySchema(SECURITY, jsonConfig));
    }

    // Variable config test
    @DisplayName("Valid Variable config")
    @ParameterizedTest
    @ValueSource(strings = {
            "/variables/valid/variables.json",
            "/variables/valid/variables.hjson",
            "/models/variables.hjson"})
    public void testValidVariableSchema(String resource) throws Exception {
        String jsonConfig = loadHjsonFromClassPath(resource);
        assertTrue(DynamicConfigSchemaValidator.verifySchema(VARIABLE, jsonConfig));
    }

    @DisplayName("Invalid Variable config")
    @ParameterizedTest
    @ValueSource(strings = {
            "/variables/invalid/variables.hjson",
            "/variables/invalid/variables.json"})
    public void testInvalidVariableSchema(String resource) throws Exception {
        String jsonConfig = loadHjsonFromClassPath(resource);
        assertFalse(DynamicConfigSchemaValidator.verifySchema(VARIABLE, jsonConfig));
    }

    // Table config test
    @DisplayName("Valid Table config")
    @ParameterizedTest
    @ValueSource(strings = {
            "/tables/valid/table.json",
            "/tables/valid/table.hjson",
            "/models/tables/table1.hjson",
            "/models/tables/table2.hjson",
            "/models_missing/tables/table1.hjson"})
    public void testValidTableSchema(String resource) throws Exception {
        String jsonConfig = loadHjsonFromClassPath(resource);
        assertTrue(DynamicConfigSchemaValidator.verifySchema(TABLE, jsonConfig));
    }

    @DisplayName("Invalid Table config")
    @ParameterizedTest
    @ValueSource(strings = {
            "/tables/invalid/table.json",
            "/tables/invalid/table.hjson"})
    public void testInvalidTableSchema(String resource) throws Exception {
        String jsonConfig = loadHjsonFromClassPath(resource);
        assertFalse(DynamicConfigSchemaValidator.verifySchema(TABLE, jsonConfig));
    }

    private String loadHjsonFromClassPath(String resource) throws Exception {
        Reader reader = new InputStreamReader(
                DynamicConfigSchemaValidatorTest.class.getResourceAsStream(resource));
        return JsonValue.readHjson(reader).toString();
    }
}
