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
package com.eliasnogueira.apitest.restassured.specification;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import com.eliasnogueira.apitest.models.Simulation;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;

public final class SimulationsSpecifications {

    private SimulationsSpecifications() {
    }

    public static RequestSpecification cpfParam(String cpfValue) {
        return new RequestSpecBuilder().addPathParam("cpf", cpfValue).build();
    }

    public static RequestSpecification bodyParam(Simulation simulation) {
        return new RequestSpecBuilder().setBody(simulation).setContentType(ContentType.JSON).build();
    }

    public static RequestSpecification bodyAndParam(Simulation simulation, String cpfValue) {
        return new RequestSpecBuilder().addPathParam("cpf", cpfValue)
                .setBody(simulation).setContentType(ContentType.JSON).build();
    }

    public static ResponseSpecification bodyAssertion(int index, Simulation simulation) {
        return new ResponseSpecBuilder().expectStatusCode(SC_OK)
                .expectBody("[" + index + "].id", CoreMatchers.notNullValue())
                .expectBody("[" + index + "].name", CoreMatchers.is(simulation.getName()))
                .expectBody("[" + index + "].cpf", CoreMatchers.is(simulation.getCpf()))
                .expectBody("[" + index + "].email", CoreMatchers.is(simulation.getEmail()))
                .expectBody("[" + index + "].amount", CoreMatchers.is(simulation.getAmount()))
                .expectBody("[" + index + "].installments", CoreMatchers.is(simulation.getInstallments()))
                .expectBody("[" + index + "].insurance", CoreMatchers.is(simulation.isInsurance())
            ).build();
    }

    public static ResponseSpecification bodySize(int size) {
        return new ResponseSpecBuilder().expectStatusCode(SC_OK).expectBody("$", Matchers.hasSize(size)).build();
    }

    public static ResponseSpecification location(String cpf) {
        return new ResponseSpecBuilder().expectStatusCode(SC_CREATED)
                .expectHeader("Location", CoreMatchers.containsString(cpf)).build();
    }
}
