function togglePasswordVisibility() {
    var passwordField = document.getElementById('inputPassword');
    var confirmPasswordField = document.getElementById('inputConfirmPassword');

    if (passwordField.type === 'password') {
        passwordField.type = 'text';
        confirmPasswordField.type = 'text';
    } else {
        passwordField.type = 'password';
        confirmPasswordField.type = 'password';
    }
}

function validatePasswordMatch() {
    var password = document.getElementById('inputPassword').value;
    var confirmPassword = document.getElementById('inputConfirmPassword').value;
    var submitButton = document.querySelector('button[type="submit"]');
    var passwordError = document.getElementById('passwordError');

    if (password !== confirmPassword || password === '' || confirmPassword === '') {
        passwordError.style.display = 'block';
        submitButton.disabled = true;
    } else {
        passwordError.style.display = 'none';
        submitButton.disabled = false;
    }
}

function validateForm() {
    var password = document.getElementById('inputPassword').value;
    var confirmPassword = document.getElementById('inputConfirmPassword').value;
    var passwordError = document.getElementById('passwordError');

    if (password !== confirmPassword) {
        passwordError.style.display = 'block';
        document.getElementById('inputConfirmPassword').scrollIntoView({ behavior: 'smooth', block: 'center' });
        document.getElementById('inputConfirmPassword').focus();
        return false;
    }
    return true;
}

document.getElementById('inputPassword').addEventListener('input', validatePasswordMatch);
document.getElementById('inputConfirmPassword').addEventListener('input', validatePasswordMatch);
