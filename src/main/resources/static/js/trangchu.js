document.addEventListener("DOMContentLoaded", function () {
  const faqQuestions = document.querySelectorAll(".faq-question");

  faqQuestions.forEach((question) => {
    question.addEventListener("click", function () {
      // Lấy phần tử cha chứa cả câu hỏi và câu trả lời
      const faqItem = this.parentElement;

      // Toggle class 'active' cho phần tử faq-item
      faqItem.classList.toggle("active");

      // Chuyển đổi dấu + thành dấu - và ngược lại
      const icon = this.querySelector("span");
      if (faqItem.classList.contains("active")) {
        icon.style.transform = "rotate(45deg)";
      } else {
        icon.style.transform = "rotate(0deg)";
      }
    });
  });
});
