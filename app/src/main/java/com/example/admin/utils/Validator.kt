package com.example.admin.utils

import android.util.Patterns
import com.example.admin.databinding.*


class Validator {

    companion object {

        private const val emptyError = "El campo no puede ser vacío"

        fun signInValidator(binding: ActivitySignInBinding): Boolean {
            var valid = true

            binding.dniError.text = ""
            binding.nameError.text = ""
            binding.lastNameError.text = ""
            binding.mailError.text = ""
            binding.passwordError.text = ""
            binding.repeatPasswordError.text = ""

            if (!Patterns.EMAIL_ADDRESS.matcher(binding.mailInput.text).matches()) {
                binding.mailError.text = "El formato de mail es incorrecto"
                valid = false
            }

            if (binding.passwordInput.text.length < 8) {
                binding.passwordError.text = "La contraseña no puede ser menor a 8 dígitos"
                valid = false
            }

            if (binding.passwordInput.text.toString() != binding.repeatPasswordInput.text.toString()) {
                binding.repeatPasswordError.text = "Las contraseñas no coinciden"
                valid = false
            }

            if (binding.dniInput.text.isEmpty()) {
                binding.dniError.text = emptyError
                valid = false
            }
            if (binding.nameInput.text.isEmpty()) {
                binding.nameError.text = emptyError
                valid = false
            }
            if (binding.lastNameInput.text.isEmpty()) {
                binding.lastNameError.text = emptyError
                valid = false
            }
            if (binding.mailInput.text.isEmpty()) {
                binding.mailError.text = emptyError
                valid = false
            }
            if (binding.passwordInput.text.isEmpty()) {
                binding.passwordError.text = emptyError
                valid = false
            }
            if (binding.repeatPasswordInput.text.isEmpty()) {
                binding.repeatPasswordError.text = emptyError
                valid = false
            }

            return valid
        }

        fun logInValidator(binding: ActivityLoginBinding): Boolean {
            var valid = true

            binding.dniError.text = ""
            binding.passwordError.text = ""

            if (binding.dniInput.text.isEmpty()) {
                binding.dniError.text = emptyError
                valid = false
            }
            if (binding.passwordInput.text.isEmpty()) {
                binding.passwordError.text = emptyError
                valid = false
            }

            return valid
        }

        fun emailValidator(binding: ActivityEmailBinding): Boolean {
            var valid = true

            binding.mailError.text = ""

            if (!Patterns.EMAIL_ADDRESS.matcher(binding.mailInput.text).matches()) {
                binding.mailError.text = "El formato de mail es incorrecto"
                valid = false
            }

            if (binding.mailInput.text.isEmpty()) {
                binding.mailError.text = emptyError
                valid = false
            }

            return valid
        }

        fun passwordValidator(binding: ActivityForgotPasswordBinding): Boolean {
            var valid = true

            binding.tokenError.text = ""
            binding.passwordError.text = ""
            binding.repeatPasswordError.text = ""

            if (binding.passwordInput.text.length < 8) {
                binding.passwordError.text = "La contraseña no puede ser menor a 8 dígitos"
                valid = false
            }

            if (binding.passwordInput.text.toString() != binding.repeatPasswordInput.text.toString()) {
                binding.repeatPasswordError.text = "Las contraseñas no coinciden"
                valid = false
            }

            if (binding.tokenInput.text.isEmpty()) {
                binding.tokenError.text = emptyError
                valid = false
            }

            if (binding.passwordInput.text.isEmpty()) {
                binding.passwordError.text = emptyError
                valid = false
            }
            if (binding.repeatPasswordInput.text.isEmpty()) {
                binding.repeatPasswordError.text = emptyError
                valid = false
            }

            return valid
        }

        fun authValidator(binding: ActivityNewAuthorizationBinding, image: Boolean): Boolean {
            var valid = true

            binding.titleError.text = ""
            binding.familyError.text = ""
            binding.imageError.text = ""
            binding.typeError.text = ""

            if (binding.titleInput.text.isEmpty()) {
                binding.titleError.text = emptyError
                valid = false
            }

            if (binding.typeSpinner.selectedItem.toString().isEmpty()) {
                binding.typeError.text = "Tenés que seleccionar una opción"
                valid = false
            }

            if (binding.familySpinner.selectedItem.toString().isEmpty()) {
                binding.familyError.text = "Tenés que seleccionar una opción"
                valid = false
            }

            if (!image) {
                binding.imageError.text = "Tenés que adjuntar una imagen"
                valid = false
            }

            return valid
        }
    }
}