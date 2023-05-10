/*
 * MIT License
 *
 * Copyright (c) 2023 Elias Nogueira
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.eliasnogueira.apitest.simulation.raw;

import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import com.eliasnogueira.apitest.BaseApiConfiguration;
import com.eliasnogueira.apitest.models.Simulation;

import java.math.BigDecimal;

import static io.restassured.RestAssured.*;
import static org.assertj.core.api.Assertions.*;

class SimulationsTest extends BaseApiConfiguration {

@Test
void shouldRetrieveAllSimulations() {
    when()
        .get("/simulations/")
    .then()
        .statusCode(HttpStatus.SC_OK)
        .body(
                "[0].id", CoreMatchers.notNullValue(),
                "[0].name", CoreMatchers.is("Unknown"),
                "[0].cpf", CoreMatchers.is("66414919004"),
                "[0].email", CoreMatchers.is("tom@gmail.com"),
                "[0].amount", CoreMatchers.is(new BigDecimal("11000.00")),
                "[0].installments", CoreMatchers.is(0),
                "[0].insurance", CoreMatchers.is(true)
        );
}
    @Test
    void shouldRetrieveAllSimulationsCheckingSize() {
        when()
            .get("/simulations/")
        .then()
            .statusCode(HttpStatus.SC_OK)
            .body("$", Matchers.hasSize(2));
    }

    @Test
    void shouldDeleteExistingSimulation() {
        given()
            .pathParam("cpf", "66414919004")
        .when()
            .delete("/simulations/{cpf}")
        .then()
            .statusCode(HttpStatus.SC_NO_CONTENT);
    }

    @Test
    void shouldCreateNewSimulation() {
        var simulation = Simulation.builder().name("John").cpf("4750403823739")
                .email("john@gmail.com").amount(new BigDecimal("10.0000"))
                .installments(8).insurance(true).build();

        given()
            .body(simulation)
            .contentType(ContentType.JSON)
        .when()
            .post("/simulations/")
        .then().log().all()
            .statusCode(HttpStatus.SC_CREATED)
            .header("Location", CoreMatchers.containsString(simulation.getCpf()));
    }

    @Test
    void shouldUpdateExistingSimulation() {
        String existingCpf = "17822386034";
        var simulation = Simulation.builder().name("Elias").cpf("17822386034")
                .email("elias@eliasnogueira.com").amount(new BigDecimal("3000.00"))
                .installments(5).insurance(true).build();

        var simulationUpdated =
            given()
                .pathParam("cpf", existingCpf)
                .body(simulation)
                .contentType(ContentType.JSON)
            .when()
                .put("/simulations/{cpf}")
            .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().as(Simulation.class);

        assertThat(simulationUpdated).isEqualTo(simulation);
    }
}
