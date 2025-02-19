function togglePasswordVisibility() {
    var passwordField = document.getElementById('inputPassword');
    if (passwordField.type === 'password') {
        passwordField.type = 'text'; // CON QUESTA AZIONE LA MOSTRO
    } else {
        passwordField.type = 'password'; // CON QUESTA AZIONE LA NASCONDO
    }
}