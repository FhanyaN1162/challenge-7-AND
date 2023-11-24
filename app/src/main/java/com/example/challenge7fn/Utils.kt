package com.example.challenge7fn

object Utils {

    fun isValidUsername(username: String): Boolean {
        // Logika validasi username
        return username.matches(Regex("[a-zA-Z0-9]+"))
    }

    fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
        return email.matches(emailRegex.toRegex())
    }

    fun isValidPhoneNumber(phoneNumber: String): Boolean {
        // Logika validasi nomor telepon
        return phoneNumber.matches(Regex("[0-9]+")) && phoneNumber.length >= 10
    }

    fun isValidPassword(password: String): Boolean {
        // Logika validasi password
        return password.length >= 6
    }
}
