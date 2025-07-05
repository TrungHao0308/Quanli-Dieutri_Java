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

        // ✅ Lấy thời gian từ backend
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

// Gọi khi trang tải
document.addEventListener("DOMContentLoaded", () => {
  loadNotifications();
});

// thông báo thời gian
function addNotification(message) {
  const list = document.getElementById("notificationList");
  const now = new Date();
  const formattedTime = now.toLocaleString("vi-VN"); // VD: 03/07/2025, 09:10:23

  const item = document.createElement("li");
  item.classList.add("notification-item");
  item.innerHTML = `
    <div class="notification-content">🔔 ${message}</div>
    <div class="notification-time">${formattedTime}</div>
  `;

  list.prepend(item);
}
