package com.liyaan.myffmepgfirst.bean

data class CopyBean(val age:Int)

val dataCopy = CopyBean(0).copy()




//public final class TestObject {
//    @NotNull
//    public static final String TAG = "TestObject";
//    @NotNull
//    public static final TestObject INSTANCE;
//
//    public final void foo() {
//    }
//
//    private TestObject() {
//    }
//
//    static {
//        TestObject var0 = new TestObject();
//        INSTANCE = var0;
//    }
//}