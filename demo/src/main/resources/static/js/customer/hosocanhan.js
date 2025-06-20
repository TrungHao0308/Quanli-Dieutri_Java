document.addEventListener("DOMContentLoaded", () => {
  const form = document.querySelector("form");
  if (form) {
    form.addEventListener("submit", (e) => {
      const requiredFields = form.querySelectorAll("[required]");
      const email = document.getElementById("email");
      const phone = document.getElementById("phone");
      let isValid = true;

      // Kiểm tra các trường bắt buộc
      requiredFields.forEach((field) => {
        if (!field.value.trim()) {
          isValid = false;
          field.style.borderColor = "#ef4444";
          field.nextElementSibling?.remove();
          const error = document.createElement("span");
          error.style.color = "#ef4444";
          error.style.fontSize = "0.8rem";
          error.textContent = "Vui lòng điền trường này";
          field.parentElement.appendChild(error);
        } else {
          field.style.borderColor = "";
          field.nextElementSibling?.remove();
        }
      });

      // Kiểm tra định dạng email
      if (email && email.value) {
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailRegex.test(email.value)) {
          isValid = false;
          email.style.borderColor = "#ef4444";
          email.nextElementSibling?.remove();
          const error = document.createElement("span");
          error.style.color = "#ef4444";
          error.style.fontSize = "0.8rem";
          error.textContent = "Email không hợp lệ";
          email.parentElement.appendChild(error);
        }
      }

      // Kiểm tra định dạng số điện thoại
      if (phone && phone.value) {
        const phoneRegex = /^[0-9]{10}$/;
        if (!phoneRegex.test(phone.value)) {
          isValid = false;
          phone.style.borderColor = "#ef4444";
          phone.nextElementSibling?.remove();
          const error = document.createElement("span");
          error.style.color = "#ef4444";
          error.style.fontSize = "0.8rem";
          error.textContent = "Số điện thoại phải có 10 chữ số";
          phone.parentElement.appendChild(error);
        }
      }

      if (!isValid) {
        e.preventDefault();
        alert("Vui lòng kiểm tra lại các trường thông tin.");
      }
    });
  }
});
