// document.addEventListener("DOMContentLoaded", () => {
//   const links = document.querySelectorAll(".xem-link");

//   if (!links.length) {
//     console.warn(
//       "Không tìm thấy link 'Xem' nào. Kiểm tra danh sách thong-bao-list."
//     );
//     return;
//   }

//   links.forEach((link) => {
//     link.addEventListener("click", (e) => {
//       e.preventDefault();

//       const targetId = link.getAttribute("href")?.substring(1);
//       if (!targetId) {
//         console.error("Link 'Xem' không có href hợp lệ:", link);
//         return;
//       }

//       const target = document.getElementById(targetId);
//       if (target) {
//         // Cuộn đến phần tử
//         target.scrollIntoView({ behavior: "smooth", block: "center" });

//         // Xóa highlight cũ
//         document.querySelectorAll(".lich-item").forEach((el) => {
//           el.classList.remove("highlight");
//         });

//         // Thêm highlight mới
//         target.classList.add("highlight");

//         // Xóa highlight sau 2 giây
//         setTimeout(() => {
//           target.classList.remove("highlight");
//         }, 2000);
//       } else {
//         console.error(`Không tìm thấy phần tử với ID: ${targetId}`);
//       }
//     });
//   });
// });

// // --- THÔNG BÁO (chuông) ---
// function toggleNotificationDropdown() {
//   const dropdown = document.getElementById("notificationDropdown");
//   dropdown.style.display =
//     dropdown.style.display === "block" ? "none" : "block";
// }

// function loadNotifications() {
//   fetch("/customer/api/thongbao")
//     .then((response) => response.json())
//     .then((data) => {
//       const list = document.getElementById("notificationList");
//       const badge = document.getElementById("notificationCount");
//       const icon = document.getElementById("notificationIcon");

//       list.innerHTML = ""; // Xóa nội dung cũ
//       if (data.length === 0) {
//         const empty = document.createElement("li");
//         empty.textContent = "Không có thông báo mới.";
//         list.appendChild(empty);
//         badge.style.display = "none";
//         return;
//       }

//       // Có thông báo mới
//       badge.textContent = data.length;
//       badge.style.display = "inline-block";
//       icon.classList.add("ring"); // Thêm hiệu ứng rung

//       data.forEach((item) => {
//         const li = document.createElement("li");
//         li.classList.add("notification-item");

//         // ✅ Lấy thời gian từ backend
//         const time = new Date(item.thoiGian).toLocaleString("vi-VN", {
//           hour12: false,
//           day: "2-digit",
//           month: "2-digit",
//           year: "numeric",
//           hour: "2-digit",
//           minute: "2-digit",
//           second: "2-digit",
//         });

//         li.innerHTML = `
//     <div class="notification-content">📢 ${item.tieuDe}</div>
//     <div class="notification-time">${time}</div>
//   `;

//         li.onclick = () => {
//           window.location.href = item.link;
//         };

//         list.appendChild(li);
//       });
//     })
//     .catch((error) => {
//       console.error("Lỗi khi tải thông báo:", error);
//     });
// }

// // Gọi khi trang tải
// document.addEventListener("DOMContentLoaded", () => {
//   loadNotifications();
// });

// // thông báo thời gian
// function addNotification(message) {
//   const list = document.getElementById("notificationList");
//   const now = new Date();
//   const formattedTime = now.toLocaleString("vi-VN"); // VD: 03/07/2025, 09:10:23

//   const item = document.createElement("li");
//   item.classList.add("notification-item");
//   item.innerHTML = `
//     <div class="notification-content">🔔 ${message}</div>
//     <div class="notification-time">${formattedTime}</div>
//   `;

//   list.prepend(item);

//   // Hiển thị số lượng badge
//   const countSpan = document.getElementById("notificationCount");
//   const count = list.children.length;
//   countSpan.textContent = count;
//   countSpan.style.display = count > 0 ? "inline-block" : "none";
// }

document.addEventListener("DOMContentLoaded", () => {
  // --- PHẦN 1: xử lý link "Xem" ---
  const links = document.querySelectorAll(".xem-link");

  if (!links.length) {
    console.warn(
      "Không tìm thấy link 'Xem' nào. Kiểm tra danh sách thong-bao-list."
    );
  } else {
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
          target.scrollIntoView({ behavior: "smooth", block: "center" });

          document.querySelectorAll(".lich-item").forEach((el) => {
            el.classList.remove("highlight");
          });

          target.classList.add("highlight");

          setTimeout(() => {
            target.classList.remove("highlight");
          }, 2000);
        } else {
          console.error(`Không tìm thấy phần tử với ID: ${targetId}`);
        }
      });
    });
  }

  // --- PHẦN 2: Gọi API để tải thông báo ---
  loadNotifications();
});

// --- THÔNG BÁO (chuông) ---
function toggleNotificationDropdown() {
  const dropdown = document.getElementById("notificationDropdown");
  dropdown.style.display =
    dropdown.style.display === "block" ? "none" : "block";
}

function loadNotifications() {
  fetch("/customer/api/thongbao")
    .then((response) => response.json())
    .then((data) => {
      console.log("Dữ liệu nhận từ server:", data); // Kiểm tra có bị trùng không

      const list = document.getElementById("notificationList");
      const badge = document.getElementById("notificationCount");
      const icon = document.getElementById("notificationIcon");

      list.innerHTML = ""; // Xóa nội dung cũ

      if (data.length === 0) {
        const empty = document.createElement("li");
        empty.textContent = "Không có thông báo mới.";
        list.appendChild(empty);
        badge.style.display = "none";
        return;
      }

      // Có thông báo mới
      badge.textContent = data.length;
      badge.style.display = "inline-block";
      icon.classList.add("ring"); // Thêm hiệu ứng rung

      data.forEach((item) => {
        const li = document.createElement("li");
        li.classList.add("notification-item");

        const time = new Date(item.thoiGian).toLocaleString("vi-VN", {
          hour12: false,
          day: "2-digit",
          month: "2-digit",
          year: "numeric",
          hour: "2-digit",
          minute: "2-digit",
          second: "2-digit",
        });

        li.innerHTML = `
          <div class="notification-content">📢 ${item.tieuDe}</div>
          <div class="notification-time">${time}</div>
        `;

        li.onclick = () => {
          window.location.href = item.link;
        };

        list.appendChild(li);
      });
    })
    .catch((error) => {
      console.error("Lỗi khi tải thông báo:", error);
    });
}

// --- Tạo thông báo mới từ JS (nếu cần dùng ở nơi khác) ---
function addNotification(message) {
  const list = document.getElementById("notificationList");
  const now = new Date();
  const formattedTime = now.toLocaleString("vi-VN");

  const item = document.createElement("li");
  item.classList.add("notification-item");
  item.innerHTML = `
    <div class="notification-content">🔔 ${message}</div>
    <div class="notification-time">${formattedTime}</div>
  `;

  list.prepend(item);

  // Hiển thị số lượng badge
  const countSpan = document.getElementById("notificationCount");
  const count = list.children.length;
  countSpan.textContent = count;
  countSpan.style.display = count > 0 ? "inline-block" : "none";
}
