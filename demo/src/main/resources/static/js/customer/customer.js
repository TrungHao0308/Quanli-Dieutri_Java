function toggleSidebar() {
  const sidebar = document.querySelector(".sidebar");
  const main = document.querySelector(".main-content");
  sidebar.classList.toggle("hidden");
  main.classList.toggle("full");
}

function logout() {
  // Redirect hoặc xử lý đăng xuất tại đây
  alert("Bạn đã đăng xuất!");
  window.location.href = "login.html"; // Chuyển đến trang đăng nhập
}

function showPage(pageId) {
  document.querySelectorAll(".content-page").forEach((page) => {
    page.classList.remove("active");
  });
  document.getElementById(pageId).classList.add("active");
}

// Xử lý sidebar khi tải trang
document.addEventListener("DOMContentLoaded", () => {
  const currentPath = window.location.pathname; // Lấy URL hiện tại
  const navItems = document.querySelectorAll(".sidebar-nav .nav-item");

  // Xóa lớp active khỏi tất cả các mục
  navItems.forEach((item) => {
    item.classList.remove("active");
  });

  // Ánh xạ URL với mục sidebar tương ứng
  const pathToNavMap = {
    "/customer/thongbao": "Thông báo",
    "/customer/dangkidichvu": "Đăng ký dịch vụ",
    "/customer/lichtrinhdieutri": "Lịch trình điều trị",
    "/customer/ketquadieutri": "Kết quả điều trị",
    "/customer/danhgia": "Đánh giá",
    "/customer/hosocanhan": "Hồ sơ cá nhân",
  };

  // Tìm mục tương ứng với URL hiện tại và thêm lớp active
  navItems.forEach((item) => {
    const link = item.querySelector("a");
    const linkText = item.querySelector("span").textContent;
    if (link && pathToNavMap[currentPath] === linkText) {
      item.classList.add("active");
    }
  });
});

// Quản lý trạng thái active khi bấm vào mục sidebar
document.querySelectorAll(".sidebar-nav .nav-item").forEach((item) => {
  item.addEventListener("click", function () {
    document.querySelectorAll(".sidebar-nav .nav-item").forEach((el) => {
      el.classList.remove("active");
    });
    this.classList.add("active");
  });
});
function confirmUpdate() {
  return confirm("Bạn có chắc chắn thay đổi thông tin không?");
}

const container = document.querySelector(".floating-icons-container");
const icons = ["⭐", "❤️"];

function createFloatingIcon() {
  const icon = document.createElement("div");
  icon.classList.add("floating-icon");
  const randomIcon = icons[Math.floor(Math.random() * icons.length)];
  icon.textContent = randomIcon;

  if (randomIcon === "❤️") icon.classList.add("heart");

  icon.style.left = Math.random() * 100 + "vw";
  icon.style.animationDuration = 4 + Math.random() * 4 + "s";
  icon.style.fontSize = 12 + Math.random() * 18 + "px";

  container.appendChild(icon);

  setTimeout(() => {
    icon.remove();
  }, 8000);
}

setInterval(createFloatingIcon, 1000);

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

  // Hiển thị số lượng badge
  const countSpan = document.getElementById("notificationCount");
  const count = list.children.length;
  countSpan.textContent = count;
  countSpan.style.display = count > 0 ? "inline-block" : "none";
}
