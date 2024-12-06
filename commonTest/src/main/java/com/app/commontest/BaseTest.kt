package com.app.commontest

abstract class BaseTest<ClassUnderTest> {
    abstract var classUnderTest: ClassUnderTest

    open fun setUp() { // if we don't call below, we will get NullPointerException
        io.mockk.MockKAnnotations.init(this, relaxUnitFun = true)
    }
}