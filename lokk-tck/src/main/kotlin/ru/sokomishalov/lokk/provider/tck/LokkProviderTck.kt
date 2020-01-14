/**
 * Copyright 2019-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
@file:Suppress("FunctionName")

package ru.sokomishalov.lokk.provider.tck

/**
 * @author sokomishalov
 */

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import ru.sokomishalov.lokk.provider.LokkProvider
import ru.sokomishalov.lokk.provider.withLokk
import java.time.Duration.ofMinutes
import java.util.concurrent.atomic.AtomicInteger

/**
 * @author sokomishalov
 */
abstract class LokkProviderTck {

    protected abstract val lokkProvider: LokkProvider

    @Test
    fun `Lock at least for duration`() {
        val counter = AtomicInteger(0)
        val iterations = 5

        repeat(iterations) {
            runBlocking {
                lokkProvider.withLokk(name = "lockAtLeastFor", atLeastFor = ofMinutes(10)) {
                    counter.incrementAndGet()
                }
            }
        }

        assertEquals(1, counter.get())
    }

    @Test
    fun `Lock at most for duration`() {
        val counter = AtomicInteger(0)
        val iterations = 5

        repeat(iterations) {
            runBlocking {
                lokkProvider.withLokk(name = "lockAtMostFor", atMostFor = ofMinutes(10)) {
                    counter.incrementAndGet()
                }
            }
        }

        assertEquals(iterations, counter.get())
    }
}