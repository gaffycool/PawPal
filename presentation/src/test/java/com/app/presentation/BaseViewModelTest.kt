package com.app.presentation

import com.app.commontest.BaseTest
import com.app.commontest.MainCoroutineRule
import com.app.presentation.base.InteractorRunner
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule

@ExperimentalCoroutinesApi
abstract class BaseViewModelTest<ClassUnderTest> : BaseTest<ClassUnderTest>() {

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    lateinit var interactorRunner: InteractorRunner

    @Before
    override fun setUp() {
        super.setUp()
        interactorRunner = InteractorRunner()
    }

    protected fun <VS> testViewState(
        given: () -> Unit = {},
        whenAction: () -> VS,
        then: (VS) -> Unit,
    ) {
        given()
        val actionResult = whenAction()
        then(actionResult)
    }
}