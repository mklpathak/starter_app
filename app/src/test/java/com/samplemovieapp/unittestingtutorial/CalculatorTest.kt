package com.samplemovieapp.unittestingtutorial

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class CalculatorTest {

    @Mock
    lateinit var operators: Operators

    lateinit var calculator: Calculator

    @Before
    fun onSetup() {
        calculator = Calculator(operators)
    }


    @Test
    fun givenValidInput_whenAdd_shouldCallAddOperator() {
        val a = 10
        val b = 20
        calculator.addTwoNumbers(a, b)
        Mockito.verify(operators).add(a,b)
    }

    @Test
    fun givenValidInput_whenSubtract_shouldCallSubtractOperator() {
        val a = 10
        val b = 20
        calculator.subtractTwoNumbers(a, b)
        Mockito.verify(operators).subtract(a,b)
    }
    @Test
    fun givenValidInput_whenMultiply_shouldCallMultiplyOperator() {
        val a = 10
        val b = 20
        calculator.multiplyTwoNumbers(a, b)
        Mockito.verify(operators).multiply(a,b)

    }
    @Test
    fun givenValidInput_whenDivide_shouldCallDivideOperator() {
        val a = 10
        val b = 20
        calculator.divideTwoNumbers(a, b)
        Mockito.verify(operators).divide(a,b)

    }
}