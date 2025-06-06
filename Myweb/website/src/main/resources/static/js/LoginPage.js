// JavaScript cho chức năng hiện/ẩn mật khẩu
document.addEventListener("DOMContentLoaded", function () {
  const passwordToggle = document.getElementById("passwordToggle");
  const passwordInput = document.getElementById("password");

  passwordToggle.addEventListener("click", function () {
    const icon = passwordToggle.querySelector("i");

    if (passwordInput.type === "password") {
      passwordInput.type = "text";
      icon.classList.remove("fa-eye");
      icon.classList.add("fa-eye-slash");
    } else {
      passwordInput.type = "password";
      icon.classList.remove("fa-eye-slash");
      icon.classList.add("fa-eye");
    }
  });

  // Xử lý form đăng nhập
  const loginForm = document.getElementById("loginForm");

  loginForm.addEventListener("submit", function (e) {
    e.preventDefault();
    // Thêm logic xử lý đăng nhập ở đây
    alert("Chức năng đăng nhập đang được phát triển");
  });
});
