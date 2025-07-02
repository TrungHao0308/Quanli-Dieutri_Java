document.addEventListener("DOMContentLoaded", function () {
  const filterButtons = document.querySelectorAll(".category-btn");
  const memberCards = document.querySelectorAll(".member-card");

  filterButtons.forEach((button) => {
    button.addEventListener("click", function () {
      const filter = this.getAttribute("data-filter");

      // Đổi nút active
      filterButtons.forEach((btn) => btn.classList.remove("active"));
      this.classList.add("active");

      // Ẩn/hiện bác sĩ
      memberCards.forEach((card) => {
        const category = card.getAttribute("data-category");
        if (filter === "all" || category === filter) {
          card.style.display = "block";
        } else {
          card.style.display = "none";
        }
      });
    });
  });
});
