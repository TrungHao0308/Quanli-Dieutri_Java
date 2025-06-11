document.addEventListener("DOMContentLoaded", () => {
  // Toggle hiện/ẩn mật khẩu
  function setupTogglePassword(inputId, toggleId) {
    const input = document.getElementById(inputId);
    const toggle = document.getElementById(toggleId);

    if (toggle && input) {
      toggle.addEventListener("click", () => {
        const isHidden = input.type === "password";
        input.type = isHidden ? "text" : "password";

        const icon = toggle.querySelector("i");
        if (icon) {
          icon.classList.toggle("fa-eye");
          icon.classList.toggle("fa-eye-slash");
        }
      });
    }
  }

  setupTogglePassword("password", "passwordToggle");
  setupTogglePassword("confirmPassword", "confirmPasswordToggle");

  // Kiểm tra độ mạnh mật khẩu
  const passwordInput = document.getElementById("password");
  const strengthBar = document.getElementById("passwordStrengthBar");
  const strengthText = document.getElementById("passwordStrengthText");

  passwordInput.addEventListener("input", () => {
    const val = passwordInput.value;
    let strength = 0;

    if (val.length >= 6) strength++;
    if (/[A-Z]/.test(val)) strength++;
    if (/[0-9]/.test(val)) strength++;
    if (/[^A-Za-z0-9]/.test(val)) strength++;

    let color = "red", text = "Yếu";
    if (strength >= 3) { color = "orange"; text = "Trung bình"; }
    if (strength >= 4 && val.length >= 8) { color = "green"; text = "Mạnh"; }

    strengthBar.style.width = `${strength * 25}%`;
    strengthBar.style.backgroundColor = color;
    strengthText.textContent = `Độ mạnh: ${text}`;
  });

  // Kiểm tra khớp mật khẩu
  const confirmPasswordInput = document.getElementById("confirmPassword");
  const confirmPasswordError = document.getElementById("confirmPasswordError");

  confirmPasswordInput.addEventListener("input", () => {
    if (confirmPasswordInput.value !== passwordInput.value) {
      confirmPasswordError.style.display = "block";
    } else {
      confirmPasswordError.style.display = "none";
    }
  });

  // Validate khi submit
  document.getElementById("registerForm").addEventListener("submit", (e) => {
    let valid = true;

    const requiredFields = ["firstName", "lastName", "email", "phone", "birthdate", "gender", "password", "confirmPassword"];
    requiredFields.forEach(id => {
      const field = document.getElementById(id);
      const error = document.getElementById(id + "Error");
      if (!field.value) {
        error && (error.style.display = "block");
        valid = false;
      } else {
        error && (error.style.display = "none");
      }
    });

    const email = document.getElementById("email").value;
    if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)) {
      document.getElementById("emailError").style.display = "block";
      valid = false;
    }

    const phone = document.getElementById("phone").value;
    if (!/^\d{9,11}$/.test(phone)) {
      document.getElementById("phoneError").style.display = "block";
      valid = false;
    }

    if (confirmPasswordInput.value !== passwordInput.value) {
      confirmPasswordError.style.display = "block";
      valid = false;
    }

    if (!document.getElementById("terms").checked) {
      alert("Bạn cần đồng ý với điều khoản");
      valid = false;
    }

    if (!valid) e.preventDefault();
  });
});