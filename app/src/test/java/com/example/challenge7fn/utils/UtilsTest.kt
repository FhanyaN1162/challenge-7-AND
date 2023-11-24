package com.example.challenge7fn.utils

import com.example.challenge7fn.Utils
import org.junit.Test
import org.junit.Assert.*
import com.example.challenge7fn.items.CartItem
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue

    class UtilsTest {

        @Test
        fun isValidUsername_validUsername_returnTrue() {
            val username = "user123"
            assertTrue(Utils.isValidUsername(username))
        }

        @Test
        fun isValidUsername_invalidUsername_returnFalse() {
            val username = "us@er"
            assertFalse(Utils.isValidUsername(username))
        }

        @Test
        fun isValidEmail_validEmail_returnTrue() {
            val email = "test@example.com"
            assertTrue(Utils.isValidEmail(email))
        }

        @Test
        fun isValidEmail_invalidEmail_returnFalse() {
            val email = "invalidemail@.com"
            assertFalse(Utils.isValidEmail(email))
        }

        @Test
        fun isValidPhoneNumber_validPhoneNumber_returnTrue() {
            val phoneNumber = "1234567890"
            assertTrue(Utils.isValidPhoneNumber(phoneNumber))
        }

        @Test
        fun isValidPhoneNumber_invalidPhoneNumber_returnFalse() {
            val phoneNumber = "12345"
            assertFalse(Utils.isValidPhoneNumber(phoneNumber))
        }

        @Test
        fun isValidPassword_validPassword_returnTrue() {
            val password = "strongPassword"
            assertTrue(Utils.isValidPassword(password))
        }

        @Test
        fun isValidPassword_invalidPassword_returnFalse() {
            val password = "weak"
            assertFalse(Utils.isValidPassword(password))
        }

        @Test
        fun calculateTotalPrice_isCorrect() {
            val cartItem = CartItem(foodName = "Sample Food", totalPrice = 0, price = 10, quantity = 5, imageResourceId = "")
            assertEquals(50, cartItem.calculateTotalPrice())
        }

    }