package kr.io.etri

import android.util.Log
import androidx.activity.viewModels
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import kr.io.etri.presentation.view.main.MainActivity
import kr.io.etri.presentation.view.model.MainViewModel

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import java.util.concurrent.atomic.AtomicLong

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("kr.io.etri", appContext.packageName)
    }

    @get:Rule(order = 0)
    var hiltTestRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    var composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        hiltTestRule.inject()
    }


    @ExperimentalCoroutinesApi
    @Test
    fun `ViewModelAPi호출테스트`() =  runTest {
       launch {
//            composeTestRule.activity.viewModels<MainViewModel>().value.getLegalQA(string = "범죄").collect {
//                assertEquals("", it)
//            }
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `ViewModelAPi호출값`() =  runBlocking {
//            composeTestRule.activity.viewModels<MainViewModel>().value.getLegalQA(string = "범죄")
//                .map {
//                  it
//                }.collect {
//                    assertEquals("", it)
//                }
    }



    @ExperimentalCoroutinesApi
@Test
fun `Flow의흐름생성소비실험`()
   =  runTest {
        flow {
            repeat(5) { data ->

                emit(data)  // 업스트림을 내보낸 값을 수집 하는 기능 thread-Safe가 없으므로 동시 호출 X
                Log.e("Produced", "Produced : $data")
            }
        }.collect {
                data -> // consumer

            Log.e("Consumer", "Consumer : $data")
        }
    }
/*
*  결과값
*  07:26:42.675  Consumer: Consumer : 0
*  07:26:42.675   Produced: Produced : 0
*  07:26:43.280  Consumer: Consumer : 1
*  07:26:43.280  Produced: Produced : 1
*  07:26:43.885  Consumer: Consumer : 2
*  07:26:43.885  Produced: Produced : 2
*  07:26:44.490  Consumer: Consumer : 3
*  07:26:44.490  Produced: Produced : 3
*  07:26:45.096  Consumer: Consumer : 4
*  07:26:45.096  Produced: Produced : 4
* */

@ExperimentalCoroutinesApi
@Test
fun `Flow흐름실험Intermediary비교추가하기전`() = runTest {
    val flowStart = AtomicLong(0L) // AtomicLong은 Long 자료형을 갖고 있는 Wrapping 클래스이다. Thread-safe로 구현되어 멀티쓰레드에서 synchronized 없이 사용할 수 있다.

    flow {
        repeat(4) { data ->
            delay(200L)
            produceLog(flowStart.get(), data)
            emit(data)
        }
    }.onStart {
        flowStart.set(System.currentTimeMillis()) // 시작 시간 기록
    }.collect { data ->
        delay(700L)
        consumerLog(flowStart.get(), data)
    }
}

/**
 * 08:26:20.906 Time: 2ms / Producer Coroutine : Instr: CustomTestRunner / data : 0 [>>]
 * 08:26:20.918 Time: 30ms / Consumer Coroutine : Instr: CustomTestRunner / data : 0 [<<]
 * 08:26:20.933 Time: 42ms / Producer Coroutine : Instr: CustomTestRunner / data : 1 [>>]
 * 08:26:20.939 Time: 56ms / Consumer Coroutine : Instr: CustomTestRunner / data : 1 [<<]
 * 08:26:20.955 Time: 62ms / Producer Coroutine : Instr: CustomTestRunner / data : 2 [>>]
 * 08:26:20.964 Time: 79ms / Consumer Coroutine : Instr: CustomTestRunner / data : 2 [<<]
 * **/

private fun produceLog(flowStartTime: Long, data: Int) {
    Log.i( // 1. 생산 시간 기록 2. 쓰레드 이름 기록 3. 데이터 기록
       "produce", """
        Time: ${elapsedFromStartTime(flowStartTime)}ms / Producer Coroutine : ${Thread.currentThread().name} / data : $data [>>]
    """.trimIndent()
    )
}

private fun consumerLog(flowStartTime: Long, data: Int) {
    println( // 1. 소비 시간 기록 2. 쓰레드 이름 기록 3. 데이터 기록
        """
        Time: ${elapsedFromStartTime(flowStartTime)}ms / Consumer Coroutine : ${Thread.currentThread().name} / data : $data [<<]
    """.trimIndent()
    )
}

private fun elapsedFromStartTime(flowStartTime: Long) = System.currentTimeMillis() - flowStartTime
}