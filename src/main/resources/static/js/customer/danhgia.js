document.addEventListener("DOMContentLoaded", () => {
  const form = document.querySelector("form");
  if (form) {
    form.addEventListener("submit", (e) => {
      const requiredFields = form.querySelectorAll("[required]");
      let isValid = true;

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

      const comment = document.getElementById("comment");
      if (comment && comment.value.trim().length < 10) {
        isValid = false;
        comment.style.borderColor = "#ef4444";
        comment.nextElementSibling?.remove();
        const error = document.createElement("span");
        error.style.color = "#ef4444";
        error.style.fontSize = "0.8rem";
        error.textContent = "Nhận xét phải có ít nhất 10 ký tự";
        comment.parentElement.appendChild(error);
      }

      if (!isValid) {
        e.preventDefault();
        alert("Vui lòng kiểm tra lại các trường thông tin.");
      }
    });
  }
});
