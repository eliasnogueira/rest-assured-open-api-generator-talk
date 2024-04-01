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
package com.eliasnogueira.apitest.simulation.spec;

import com.eliasnogueira.apitest.BaseApiConfiguration;
import com.eliasnogueira.apitest.models.Simulation;
import com.eliasnogueira.apitest.restassured.specification.SimulationsSpecifications;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.assertj.core.api.Assertions.assertThat;

@Disabled
class SimulationsTest extends BaseApiConfiguration {

    @Test
    void shouldRetrieveAllSimulations() {
        var simulation = Simulation.builder().name("Tom").cpf("66414919004").email("tom@gmail.com")
                .amount(new BigDecimal("11000.00")).installments(3).insurance(true).build();

        when()
            .get("/simulations/")
        .then()
            .spec(SimulationsSpecifications.bodyAssertion(0, simulation));
    }

    @Test
    void shouldRetrieveAllSimulationsCheckingSize() {
        when()
            .get("/simulations/")
        .then()
            .spec(SimulationsSpecifications.bodySize(2));
    }

    @Test
    void shouldDeleteExistingSimulation() {
        given()
            .spec(SimulationsSpecifications.cpfParam("66414919004"))
        .when()
            .delete("/simulations/{cpf}")
        .then()
            .statusCode(HttpStatus.SC_NO_CONTENT);
    }

    @Test
    void shouldCreateNewSimulation() {
        var simulation = Simulation.builder().name("John").cpf("4750403823739").email("john@gmail.com")
                .amount(new BigDecimal("1000")).installments(8).insurance(true).build();

        given()
            .spec(SimulationsSpecifications.bodyParam(simulation))
        .when()
            .post("/simulations/")
        .then()
            .spec(SimulationsSpecifications.location(simulation.getCpf()));
    }

    @Test
    void shouldUpdateExistingSimulation() {
        String existingCpf = "17822386034";
        var simulation = Simulation.builder().name("Elias").cpf("17822386034").email("elias@eliasnogueira.com")
                .amount(new BigDecimal("3000.00")).installments(5).insurance(true).build();

        var simulationUpdated =
                given()
                    .spec(SimulationsSpecifications.bodyAndParam(simulation, existingCpf))
                .when()
                    .put("/simulations/{cpf}")
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract().as(Simulation.class);

        assertThat(simulationUpdated).usingRecursiveComparison().ignoringFields("id").isEqualTo(simulation);
    }
}
