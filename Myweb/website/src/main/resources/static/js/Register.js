document.addEventListener('DOMContentLoaded', () => {
  const form = document.getElementById('registerForm');

  const firstName = document.getElementById('firstName');
  const lastName = document.getElementById('lastName');
  const email = document.getElementById('email');
  const phone = document.getElementById('phone');
  const birthdate = document.getElementById('birthdate');
  const gender = document.getElementById('gender');
  const password = document.getElementById('password');
  const confirmPassword = document.getElementById('confirmPassword');
  const terms = document.getElementById('terms');

  // Error elements
  const firstNameError = document.getElementById('firstNameError');
  const lastNameError = document.getElementById('lastNameError');
  const emailError = document.getElementById('emailError');
  const phoneError = document.getElementById('phoneError');
  const confirmPasswordError = document.getElementById('confirmPasswordError');

  const passwordToggle = document.getElementById('passwordToggle');
  const confirmPasswordToggle = document.getElementById('confirmPasswordToggle');
  const passwordStrengthBar = document.getElementById('passwordStrengthBar');
  const passwordStrengthText = document.getElementById('passwordStrengthText');

  // Show/Hide password
  function togglePasswordVisibility(input, toggleIcon) {
    if (input.type === 'password') {
      input.type = 'text';
      toggleIcon.classList.remove('fa-eye');
      toggleIcon.classList.add('fa-eye-slash');
    } else {
      input.type = 'password';
      toggleIcon.classList.remove('fa-eye-slash');
      toggleIcon.classList.add('fa-eye');
    }
  }

  passwordToggle.addEventListener('click', () => {
    togglePasswordVisibility(password, passwordToggle.querySelector('i'));
  });

  confirmPasswordToggle.addEventListener('click', () => {
    togglePasswordVisibility(confirmPassword, confirmPasswordToggle.querySelector('i'));
  });

  // Password strength check
  function checkPasswordStrength(pwd) {
    let strength = 0;
    if (pwd.length >= 8) strength++;
    if (/[A-Z]/.test(pwd)) strength++;
    if (/[a-z]/.test(pwd)) strength++;
    if (/\d/.test(pwd)) strength++;
    if (/[\W_]/.test(pwd)) strength++;

    return strength; // 0 to 5
  }

  password.addEventListener('input', () => {
    const val = password.value;
    const strength = checkPasswordStrength(val);
    const percent = (strength / 5) * 100;

    passwordStrengthBar.style.width = percent + '%';

    let color = 'red';
    let text = 'Yếu';
    if (strength >= 4) {
      color = 'green';
      text = 'Mạnh';
    } else if (strength >= 3) {
      color = 'orange';
      text = 'Trung bình';
    }

    passwordStrengthBar.style.backgroundColor = color;
    passwordStrengthText.textContent = `Độ mạnh mật khẩu: ${text}`;
  });

  // Validation functions
  function validateEmail(email) {
    // Simple email regex
    const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return re.test(email);
  }

  function validatePhone(phone) {
    // Vietnamese phone number format (simple)
    const re = /^(0|\+84)(3|5|7|8|9)\d{8}$/;
    return re.test(phone);
  }

  form.addEventListener('submit', e => {
    let isValid = true;

    // Reset errors
    [firstNameError, lastNameError, emailError, phoneError, confirmPasswordError].forEach(el => {
      el.style.display = 'none';
    });

    if (firstName.value.trim() === '') {
      firstNameError.style.display = 'block';
      isValid = false;
    }

    if (lastName.value.trim() === '') {
      lastNameError.style.display = 'block';
      isValid = false;
    }

    if (!validateEmail(email.value.trim())) {
      emailError.style.display = 'block';
      isValid = false;
    }

    if (!validatePhone(phone.value.trim())) {
      phoneError.style.display = 'block';
      isValid = false;
    }

    if (password.value !== confirmPassword.value) {
      confirmPasswordError.style.display = 'block';
      isValid = false;
    }

    if (!terms.checked) {
      alert('Bạn phải đồng ý với Điều khoản sử dụng');
      isValid = false;
    }

    if (!isValid) {
      e.preventDefault(); // Ngăn submit nếu lỗi
    }
  });
});
