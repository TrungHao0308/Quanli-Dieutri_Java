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
// --- LIÊN KẾT DỊCH VỤ VÀ BÁC SĨ ---
document.addEventListener("DOMContentLoaded", () => {
  const serviceSelect = document.getElementById("service");
  const doctorSelect = document.getElementById("doctor");

  serviceSelect.addEventListener("change", () => {
    const selectedService = serviceSelect.value;

    // Xóa danh sách cũ
    doctorSelect.innerHTML = `<option value="">Chọn bác sĩ</option>`;
    doctorSelect.disabled = true;

    if (selectedService) {
      fetch(
        `/customer/api/bacsi?chuyenMon=${encodeURIComponent(selectedService)}`
      )
        .then((response) => response.json())
        .then((data) => {
          if (data.length === 0) {
            const option = document.createElement("option");
            option.textContent = "Không có bác sĩ phù hợp";
            option.disabled = true;
            doctorSelect.appendChild(option);
            return;
          }

          data.forEach((bacSi) => {
            const option = document.createElement("option");
            option.value = bacSi.fullName;
            option.textContent = `${bacSi.fullName} (${bacSi.email})`;
            doctorSelect.appendChild(option);
          });

          doctorSelect.disabled = false;
        })
        .catch((error) => {
          console.error("Lỗi khi tải bác sĩ:", error);
          const option = document.createElement("option");
          option.textContent = "Lỗi khi tải dữ liệu";
          option.disabled = true;
          doctorSelect.appendChild(option);
        });
    }
  });
});
