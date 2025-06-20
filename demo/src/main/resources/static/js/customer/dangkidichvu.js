document.addEventListener("DOMContentLoaded", () => {
  const form = document.querySelector("form");
  if (form) {
    form.addEventListener("submit", (e) => {
      const requiredFields = form.querySelectorAll("[required]");
      const appointmentDate = document.getElementById("appointmentDate");
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

      // Kiểm tra ngày hẹn (phải là ngày trong tương lai)
      if (appointmentDate && appointmentDate.value) {
        const selectedDate = new Date(appointmentDate.value);
        const today = new Date();
        today.setHours(0, 0, 0, 0);
        if (selectedDate < today) {
          isValid = false;
          appointmentDate.style.borderColor = "#ef4444";
          appointmentDate.nextElementSibling?.remove();
          const error = document.createElement("span");
          error.style.color = "#ef4444";
          error.style.fontSize = "0.8rem";
          error.textContent = "Ngày hẹn phải là ngày trong tương lai";
          appointmentDate.parentElement.appendChild(error);
        }
      }

      if (!isValid) {
        e.preventDefault();
        alert("Vui lòng kiểm tra lại các trường thông tin.");
      }
    });
  }
});
