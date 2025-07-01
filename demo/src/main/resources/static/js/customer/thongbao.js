// document.addEventListener("DOMContentLoaded", () => {
//   const notificationItems = document.querySelectorAll(".notification-item");
//   notificationItems.forEach((item) => {
//     item.addEventListener("click", () => {
//       item.style.backgroundColor = "#f3f4f6";
//       item.querySelector("h3").style.opacity = "0.7";
//       // Có thể gửi yêu cầu đến server để đánh dấu là đã đọc
//       console.log(
//         "Thông báo đã được đánh dấu là đọc:",
//         item.querySelector("h3").textContent
//       );
//     });
//   });
// });
// document.addEventListener("DOMContentLoaded", () => {
//   document.querySelectorAll(".xem-link").forEach((link) => {
//     link.addEventListener("click", (e) => {
//       e.preventDefault();
//       const targetId = link.getAttribute("href").substring(1);
//       const target = document.getElementById(targetId);
//       if (target) {
//         target.scrollIntoView({ behavior: "smooth" });
//         target.classList.add("highlight");
//         setTimeout(() => target.classList.remove("highlight"), 2000);
//       }
//     });
//   });
// });
// document.addEventListener("DOMContentLoaded", function () {
//   const links = document.querySelectorAll(".xem-link");

//   links.forEach((link) => {
//     link.addEventListener("click", function (e) {
//       e.preventDefault();

//       const targetId = this.getAttribute("href").substring(1);
//       const targetElement = document.getElementById(targetId);

//       if (targetElement) {
//         // Xoá các highlight cũ
//         document.querySelectorAll(".lich-item").forEach((el) => {
//           el.classList.remove("highlight");
//         });

//         // Thêm highlight
//         targetElement.classList.add("highlight");

//         // Tự động xóa sau 2 giây
//         setTimeout(() => {
//           targetElement.classList.remove("highlight");
//         }, 2000);

// });

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
