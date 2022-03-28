package com.liyaan.myffmepgfirst

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.flow.*

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import java.lang.RuntimeException
import kotlin.coroutines.CoroutineContext
import kotlin.system.measureTimeMillis

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.liyaan.myffmepgfirst", appContext.packageName)
    }
    @Test
    fun async1() = runBlocking{
        val time = measureTimeMillis {
            val one = async { doGetOne() }
            val two = async {doGetTwo()}
            println("结果:${one.await()+two.await()}")
        }
        println("时间为：$time ms")

    }

    private suspend fun doGetOne():Int{
        delay(1000)
        return 14
    }
    private suspend fun doGetTwo():Int{
        delay(1000)
        return 24
    }

    @Test
    fun async1_mode() = runBlocking{
        //和线程同一个生命周期 当协程停止也跟着销毁
        val job = launch(start = CoroutineStart.DEFAULT){
            delay(10000)
            println("Job finished.")
        }
        delay(1000)
        job.cancel()
    }

    @Test
    fun async1_mode_atomic() = runBlocking{
        //和线程同一个生命周期 当协程停止也跟着销毁
        launch(start = CoroutineStart.ATOMIC){
            println("Job finished.")
        }
        delay(1000)
        println("Job finished. ssssssssssssss")
    }
    @Test
    fun async1_mode_lazy() = runBlocking {
        //当协程调度被取消 该线程直接进入异常结束状态
        val job = async (start = CoroutineStart.LAZY){
            println("Job finished.")
            29
        }


        delay(1000)
        job.cancel()
        println("Job finished. ssssssssssssss")
         job.await()
    }
    fun async1_mode_un_dispatched() = runBlocking {
        //协程创建，在此方法/函数调用栈执行（如果方法是在主线程，设置context为IO也会在主线程执行）
        val job = async (context = Dispatchers.IO,start = CoroutineStart.UNDISPATCHED){
            println("Job finished.")
            29
        }
    }
    //作用域构造器
    fun async1_mode_supervisor_scope() = runBlocking {
        //其中一个报错不会影响其他的
        supervisorScope {
            val job1 = launch {
                delay(1000)
                println("job1 finished")
            }
            val job2 = async {
                delay(500)
                println("job2 finished")
                "job2 result"
            }
        }
    }
    @Test
    fun async1_mode_coroutine_scope() = runBlocking {
        //其中一个报错 其他也会跟着取消执行
        coroutineScope {
            val job1 = launch {
                delay(1000)
                println("job1 finished")
            }
            val job2 = async {
                delay(500)
                println("job2 finished")
                "job2 result"
            }
        }
    }
    //job的生命周期 new（新建） active(活跃) Completing(完成中) Completed（已完成） Cancelling(取消中) Cancelled(已取消)
    //job通过 isActive isCancelled isCompleted 来判断生命周期

    fun simpleFlow7() = flow<Int>{
        for (i in 1..5){
            emit(i)
            println("Emitting $i")
        }
    }

    @Test
    fun testCancelFlowCheck() = runBlocking<Unit> {
        simpleFlow7().collect { value -> if (value==3) cancel()  }
    }
    fun simpleFlow8() = flow<Int>{
        for (i in 1..5){
            delay(100)
            emit(i)
            println("Emitting $i")
        }
    }

    @Test
    fun testFlowBackPressure() = runBlocking<Unit> {
        val time = measureTimeMillis {
            simpleFlow8()
                .buffer(50)
                .collect { value ->
                    delay(300)
                    println("Collected $value ${Thread.currentThread().name}")
                }
        }
        println("Collected in $time ms")
    }

    fun numbers() = flow<Int> {
        try {
            emit(1);
            emit(2)
            println("This line will not execute")
            emit(3)
        }finally {
            println("Finally in numbers")
        }
    }
    @Test
    fun testLengthOperator() = runBlocking {
        numbers().take(2).collect { value -> println(value) }
    }

    @Test
    fun testReduce() = runBlocking {
        val sum = (1..5).asFlow().map { value-> value*value }.reduce { a, b -> a+b }
    }

    @Test
    fun testZip() = runBlocking {
        val numbers = (1..3).asFlow()
        val strs = flowOf("One","Two","Three")
        numbers.zip(strs){a,b->"$a -> $b"}.collect { println(it) }
    }
    @Test
    fun testZip2()= runBlocking {
        val numbers = (1..3).asFlow().onEach { delay(300) }
        val strs = flowOf("One","Two","Three").onEach { delay(400) }
        val startTime = System.currentTimeMillis()
        numbers.zip(strs){ a,b->
            "$a->$b"
        }.collect {
            println("$it at ${System.currentTimeMillis() - startTime} ms from start")
        }
    }

    fun requestFlow(i:Int) = flow<String>{
        emit("$i First")
        delay(500)
        emit("$i Second")
    }
    @Test
    fun testFlatMapConcat() = runBlocking<Unit> {
        val startTime = System.currentTimeMillis()
        (1..3).asFlow().onEach {
            delay(100)
        }.flatMapConcat {
            requestFlow(it)
        }.collect{
            println("$it at ${System.currentTimeMillis() - startTime} ms from start time")
        }
    }

    @Test
    fun testFlatMapMerge() = runBlocking<Unit> {
        val startTime = System.currentTimeMillis()
        (1..5).asFlow().onEach { delay(100) }.flatMapMerge {
            requestFlow(it)
        }.collect {
            println("$it at ${System.currentTimeMillis()-startTime} ms from time")
        }
    }
    @Test
    fun testFlatMapLatest() = runBlocking<Unit>{
        val startTime = System.currentTimeMillis()
        (1..5).asFlow().onEach { delay(100) }.flatMapLatest {
            requestFlow(it)
        }.collect {
            println("$it at ${System.currentTimeMillis()-startTime} ms from time")
        }
    }

    @Test
    fun testFlowException() = runBlocking {
        flow {
            try{
                emit(1)
                throw ArithmeticException("Div 0")
            }catch (e:Exception){
                println(e.toString())
            }
        }.flowOn(Dispatchers.IO).collect {
            println(it)
        }
        flow {
            emit(1)
            throw ArithmeticException("Div 0")
        }.catch { e:Throwable ->
            println(e.toString())
        }.collect {
            println("$it")
        }
        //异常当中如何恢复
        flow{
            emit(1)
            throw ArithmeticException("Div 0")
        }.catch { e:Throwable->
            emit(10)
        }.collect {
            println("$it") // 1 10
        }
        flow{
            throw ArithmeticException("Div 0")
            emit(1)
        }.catch { e:Throwable->
            emit(10)
        }.collect {
            println("$it") // 10
        }
    }

    fun simpleFlow() = (1..3).asFlow()
    @Test
    fun testFlowCompleteInFinally() = runBlocking {
        try {
            simpleFlow().collect{ println(it) }
        }finally {
            println("Done")
        }
    }
    //onCompletion 执行完后执行此函数 也可以判断异常（异常处理还需要借助catch）
    fun simpleFlow2() = flow<Int> {
        emit(1)
        emit(2)
        throw RuntimeException()
    }
    @Test
    fun testFlowCompleteInOnCompletion() = runBlocking {
        //1 2 3 Done
        simpleFlow().onCompletion { println("Done") }.collect{ println(it) }
        //会抛出异常 并打印Done
        simpleFlow2().onCompletion { e-> if (e!=null) println("Done") }.collect{ println(it) }

        simpleFlow2().onCompletion { e-> if (e!=null) println("Done") }.catch { e->
            println("异常打印 拦截")
        }.collect{ println(it) }
    }
    @Test
    fun testChannel() = runBlocking {
        //可以设置 缓存大小（不设置默认是 生产被消费以后才能在生产）
        val channel = Channel<Int>()
        val producer = GlobalScope.launch {
            var i = 0
            while (true){
                delay(1000)
                channel.send(++i)
                println("send $i")
            }
        }
        val consumer = GlobalScope.launch {
            while (true){
                delay(2000)
                val element = channel.receive()
                println("receive $element")
            }
        }
        joinAll(producer,consumer)
    }
    @Test
    fun testIterateChannel() = runBlocking {
        //生成完成->消费者取出来[利用iterator迭代器 for循环]
        val channel = Channel<Int>(Channel.UNLIMITED)
        val producer = GlobalScope.launch {
            for (x in 1..5){
                channel.send(x*x)
                println("send ${x*x}")
            }
        }
        val consumer = GlobalScope.launch {
//            val iterator = channel.iterator()
//            while (iterator.hasNext()){
//                val element = iterator.next()
//                println("receive $element")
//                delay(2000)
//            }
            for (element in channel){
                println("receive $element")
                delay(2000)
            }
        }
        joinAll(producer,consumer)
    }
    @Test
    fun testFastProducerChannel() = runBlocking {
        //生产者 produce
        val receiveChannel:ReceiveChannel<Int> = GlobalScope.produce<Int> {
            repeat(100){
                delay(1000)
                send(it)
            }
        }
        val consumer = GlobalScope.launch {
            for (i in receiveChannel){
                println("received: $i")
            }
        }
        consumer.join()
    }
    @Test
    fun testFastConsumerChannel() = runBlocking {
        //actor 消费者
        val sendChannel:SendChannel<Int> = GlobalScope.actor<Int> {
            while (true){
                val element = receive()
                println(element)
            }
        }
        val producer = GlobalScope.launch {
            for (i in 0..3){
                sendChannel.send(i)
            }
        }
        producer.join()
    }
}