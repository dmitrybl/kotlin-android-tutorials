package com.example.recyclertest.mock

import java.util.*
import kotlin.collections.ArrayList

class MockGenerator {

    companion object {
        fun generate(count: Int): List<Mock> {
            val mocks = ArrayList<Mock>(count)

            val random = Random()

            for(i in 0..count) {
                mocks.add(Mock(UUID.randomUUID().toString(), random.nextInt(200)))
            }

            return mocks
        }

    }
}