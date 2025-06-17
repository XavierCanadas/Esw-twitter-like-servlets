function initValidation(serverErrors)  {

  const form = document.getElementById('registerForm');
  const email = document.getElementById('email');
  const username = document.getElementById('username');
  const password = document.getElementById('password');
  const confirmPassword = document.getElementById('confirmPassword');
  const gender = document.getElementById('gender');
  const birthdateString = document.getElementById('birthdateString'); // TODO: change to handle date input instead of string
  const polis = document.getElementById('polis');

  // Email validation
  email.addEventListener('input', () => {
    const emailPattern = /^[a-zA-Z0-9_+&*-]+(?:\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\.)+[a-zA-Z]{2,7}$/;
    if (!emailPattern.test(email.value)) {
      email.setCustomValidity("Please enter a valid email address.");
    } else {
      email.setCustomValidity("");
    }
  })

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
  birthdateString.addEventListener('input', () => {
    const today = new Date();
    const selectedDate = new Date(birthdateString.value);

    console.log(`Selected date: ${selectedDate}`);

    if (isNaN(selectedDate.getTime())) {
      birthdateString.setCustomValidity("Please enter a valid date");
    } else if (selectedDate > today) {
      birthdateString.setCustomValidity("Birth date cannot be in the future");
    } else {
      let age = today.getFullYear() - selectedDate.getFullYear();
      const monthDiff = today.getMonth() - selectedDate.getMonth();

      if (monthDiff < 0 || (monthDiff === 0 && today.getDate() < selectedDate.getDate())) {
        age--;
      }

      if (age < 18) {
        birthdateString.setCustomValidity("You must be at least 18 years old");
      } else {
        birthdateString.setCustomValidity("");
      }
    }
  });



  // Polis validation
  polis.addEventListener('change', () => {
    polis.setCustomValidity(polis.value ? "" : "Please select a polis");
  });


  // Handle server errors - with null check
  if (serverErrors && typeof serverErrors === 'object') {
    Object.entries(serverErrors).forEach(([field, message]) => {
      const input = document.getElementsByName(field)[0];
      if (input) {
        input.setCustomValidity(message);
        input.reportValidity();
        input.addEventListener('input', () => {
          input.setCustomValidity('');
          input.reportValidity();
        });
      }
    });
  }

  // Check before submit
  form.addEventListener('submit', event => {
    // Manually trigger validation for all fields
    [email, username, password, confirmPassword, gender, birthdateString, polis].forEach(input => {
      const event = new Event('input', { bubbles: true });
      input.dispatchEvent(event);
    });

    if (!form.checkValidity()) {
      event.preventDefault();
      form.reportValidity();
    }
  });

}