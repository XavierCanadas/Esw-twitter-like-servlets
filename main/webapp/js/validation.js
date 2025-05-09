const form = document.getElementById('registerForm');
const username = document.getElementById('username');
const password = document.getElementById('password');
const confirmPassword = document.getElementById('confirmPassword');
const gender = document.getElementById('gender');
const birthdate = document.getElementById('birthdate');
const polis = document.getElementById('polis');

// Username validation
username.addEventListener('input', () => {
  if (username.value.length < 2 || username.value.length > 15) {
    username.setCustomValidity("Username must be between 2 and 15 characters.");
  } else {
    username.setCustomValidity("");
  }
});

// Password pattern validation
password.addEventListener('input', () => {
  const passwordPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*]).{8,}$/;
  if (!passwordPattern.test(password.value)) {
    password.setCustomValidity("Password must have at least 8 characters, including uppercase, lowercase, number, and special character.");
  } else {
    password.setCustomValidity("");
  }

  // Recheck confirm password when password changes
  if (confirmPassword.value) {
    confirmPassword.setCustomValidity(
        confirmPassword.value !== password.value ? "Passwords do not match." : ""
    );
  }
});

// Confirm password validation
confirmPassword.addEventListener('input', () => {
  confirmPassword.setCustomValidity(
      confirmPassword.value !== password.value ? "Passwords do not match." : ""
  );
});

// Gender validation
gender.addEventListener('change', () => {
  gender.setCustomValidity(gender.value ? "" : "Please select a gender");
});

// Birthdate validation
birthdate.addEventListener('input', () => {
  const today = new Date();
  const selectedDate = new Date(birthdate.value);

  if (isNaN(selectedDate.getTime())) {
    birthdate.setCustomValidity("Please enter a valid date");
  } else if (selectedDate > today) {
    birthdate.setCustomValidity("Birth date cannot be in the future");
  } else {
    let age = today.getFullYear() - selectedDate.getFullYear();
    const monthDiff = today.getMonth() - selectedDate.getMonth();

    if (monthDiff < 0 || (monthDiff === 0 && today.getDate() < selectedDate.getDate())) {
      age--;
    }

    if (age < 18) {
      birthdate.setCustomValidity("You must be at least 18 years old");
    } else {
      birthdate.setCustomValidity("");
    }
  }
});

// Polis validation
polis.addEventListener('change', () => {
  polis.setCustomValidity(polis.value ? "" : "Please select a polis");
});

// Handle server errors
Object.entries(serverErrors).forEach(([field, message]) => {
  const input = document.getElementsByName(field)[0];
  if (input) {
    input.setCustomValidity(message);
    input.reportValidity();
    input.addEventListener('input', () => input.setCustomValidity(''));
  }
});

// Check before submit
form.addEventListener('submit', event => {
  // Manually trigger validation for all fields
  [username, password, confirmPassword, gender, birthdate, polis].forEach(input => {
    const event = new Event('input', { bubbles: true });
    input.dispatchEvent(event);
  });

  if (!form.checkValidity()) {
    event.preventDefault();
    form.reportValidity();
  }
});
