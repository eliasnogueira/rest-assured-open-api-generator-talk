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
package se.jfokus.workshop.restriction.architecture;

import com.eliasnogueira.credit.model.MessageV1;
import org.junit.jupiter.api.Test;
import se.jfokus.workshop.api.service.RestrictionsApiService;

import static org.assertj.core.api.Assertions.assertThat;

class RestrictionsTest {

    private final RestrictionsApiService restrictionsApiService = new RestrictionsApiService();

    @Test
    void shouldReturnRestriction() {
        String cpf = "60094146012";
        MessageV1 message = restrictionsApiService.queryCpfWithRestriction(cpf);

        assertThat(message.getMessage()).contains(cpf);
    }

    @Test
    void shouldReturnNoRestriction() {
        boolean isRestricted = restrictionsApiService.queryCpf("123456789");
        assertThat(isRestricted).isFalse();
    }
}
