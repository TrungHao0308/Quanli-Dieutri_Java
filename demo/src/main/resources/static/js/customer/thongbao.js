document.addEventListener("DOMContentLoaded", () => {
  const links = document.querySelectorAll(".xem-link");

  if (!links.length) {
    console.warn(
      "Không tìm thấy link 'Xem' nào. Kiểm tra danh sách thong-bao-list."
    );
    return;
  }

  links.forEach((link) => {
    link.addEventListener("click", (e) => {
      e.preventDefault();

      const targetId = link.getAttribute("href")?.substring(1);
      if (!targetId) {
        console.error("Link 'Xem' không có href hợp lệ:", link);
        return;
      }

      const target = document.getElementById(targetId);
      if (target) {
        // Cuộn đến phần tử
        target.scrollIntoView({ behavior: "smooth", block: "center" });

        // Xóa highlight cũ
        document.querySelectorAll(".lich-item").forEach((el) => {
          el.classList.remove("highlight");
        });

        // Thêm highlight mới
        target.classList.add("highlight");

        // Xóa highlight sau 2 giây
        setTimeout(() => {
          target.classList.remove("highlight");
        }, 2000);
      } else {
        console.error(`Không tìm thấy phần tử với ID: ${targetId}`);
      }
    });
  });
});
