// document.addEventListener("DOMContentLoaded", () => {
//   const form = document.querySelector("form");
//   if (form) {
//     form.addEventListener("submit", (e) => {
//       const requiredFields = form.querySelectorAll("[required]");
//       const appointmentDate = document.getElementById("appointmentDate");
//       let isValid = true;

//       // Kiểm tra các trường bắt buộc
//       requiredFields.forEach((field) => {
//         if (!field.value.trim()) {
//           isValid = false;
//           field.style.borderColor = "#ef4444";
//           field.nextElementSibling?.remove();
//           const error = document.createElement("span");
//           error.style.color = "#ef4444";
//           error.style.fontSize = "0.8rem";
//           error.textContent = "Vui lòng điền trường này";
//           field.parentElement.appendChild(error);
//         } else {
//           field.style.borderColor = "";
//           field.nextElementSibling?.remove();
//         }
//       });

//       // Kiểm tra ngày hẹn (phải là ngày trong tương lai)
//       if (appointmentDate && appointmentDate.value) {
//         const selectedDate = new Date(appointmentDate.value);
//         const today = new Date();
//         today.setHours(0, 0, 0, 0);
//         if (selectedDate < today) {
//           isValid = false;
//           appointmentDate.style.borderColor = "#ef4444";
//           appointmentDate.nextElementSibling?.remove();
//           const error = document.createElement("span");
//           error.style.color = "#ef4444";
//           error.style.fontSize = "0.8rem";
//           error.textContent = "Ngày hẹn phải là ngày trong tương lai";
//           appointmentDate.parentElement.appendChild(error);
//         }
//       }

//       if (!isValid) {
//         e.preventDefault();
//         alert("Vui lòng kiểm tra lại các trường thông tin.");
//       }
//     });
//   }
// });
// function setEmailBacSi() {
//   const select = document.getElementById("doctor");
//   const email = select.options[select.selectedIndex].getAttribute("data-email");
//   document.getElementById("emailBacSi").value = email || "";
// }
// document.addEventListener("DOMContentLoaded", function () {
//   const serviceSelect = document.getElementById("serviceType");
//   const doctorSelect = document.getElementById("doctor");

//   serviceSelect.addEventListener("change", function () {
//     const chuyenMon = this.value;
//     doctorSelect.innerHTML = `<option value="">Đang tải...</option>`;

//     if (!chuyenMon) {
//       doctorSelect.innerHTML = `<option value="">Chọn bác sĩ</option>`;
//       return;
//     }

//     fetch(`/customer/api/bacsi?chuyenMon=${chuyenMon}`)
//       .then((res) => res.json())
//       .then((doctors) => {
//         doctorSelect.innerHTML = `<option value="">Chọn bác sĩ</option>`;
//         doctors.forEach((doc) => {
//           const opt = document.createElement("option");
//           opt.value = doc.ten;
//           opt.textContent = `BS. ${doc.ten}`;
//           opt.setAttribute("data-email", doc.email);
//           opt.setAttribute("data-caLam", doc.caLam);
//           doctorSelect.appendChild(opt);
//         });
//       })
//       .catch((err) => {
//         console.error("Lỗi khi tải bác sĩ:", err);
//         doctorSelect.innerHTML = `<option value="">Không tải được bác sĩ</option>`;
//       });
//   });
// });

// function setEmailBacSi() {
//   const select = document.getElementById("doctor");
//   const emailField = document.getElementById("emailBacSi");
//   const selectedOption = select.options[select.selectedIndex];
//   emailField.value = selectedOption.getAttribute("data-email") || "";
// }

// doctorSelect.addEventListener("change", () => {
//   const selectedOption = doctorSelect.options[doctorSelect.selectedIndex];
//   const caLam = selectedOption.getAttribute("data-caLam");

//   const timeSelect = document.getElementById("timeSelect");
//   const availableTimes = {
//     "sáng": ["07:00", "07:30", "08:00", "08:30", "09:00", "09:30", "10:00", "10:30", "11:00"],
//     "chiều": ["13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30"]
//   };

//   if (!caLam || !availableTimes[caLam]) {
//     timeSelect.innerHTML = `<option value="">Không có ca làm hợp lệ</option>`;
//     return;
//   }

//   // Đổ danh sách giờ theo ca
//   timeSelect.innerHTML = `<option value="">Chọn giờ khám</option>`;
//   availableTimes[caLam].forEach(time => {
//     const opt = document.createElement("option");
//     opt.value = time;
//     opt.textContent = time;
//     timeSelect.appendChild(opt);
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
  const form = document.querySelector("form");
  const serviceSelect = document.getElementById("serviceType");
  const doctorSelect = document.getElementById("doctor");
  const emailField = document.getElementById("emailBacSi");
  const timeSelect = document.getElementById("timeSelect");

  // ----- Load danh sách dịch vụ từ bảng bac_si_chuyen_mon -----
  fetch("/customer/api/dichvu-tu-bacsis")
    .then((res) => res.json())
    .then((services) => {
      services.forEach((service) => {
        const opt = document.createElement("option");
        opt.value = service;
        opt.textContent = service;
        serviceSelect.appendChild(opt);
      });
    });

  // ----- Khi chọn dịch vụ → load bác sĩ phù hợp -----
  serviceSelect.addEventListener("change", () => {
    const chuyenMon = serviceSelect.value;
    doctorSelect.innerHTML = `<option value="">Đang tải danh sách bác sĩ...</option>`;
    timeSelect.innerHTML = `<option value="">Chọn giờ khám</option>`;
    emailField.value = "";

    if (!chuyenMon) {
      doctorSelect.innerHTML = `<option value="">Chọn bác sĩ</option>`;
      return;
    }

    fetch(`/customer/api/bacsi?chuyenMon=${chuyenMon}`)
      .then((res) => res.json())
      .then((doctors) => {
        doctorSelect.innerHTML = `<option value="">Chọn bác sĩ</option>`;
        doctors.forEach((doc) => {
          const opt = document.createElement("option");
          opt.value = doc.fullName;
          opt.textContent = doc.fullName;
          opt.setAttribute("data-email", doc.email);
          opt.setAttribute("data-caLam", doc.caLam);
          doctorSelect.appendChild(opt);
        });
      });
  });

  // ----- Khi chọn bác sĩ → cập nhật email và giờ làm theo ca -----
  doctorSelect.addEventListener("change", () => {
    const selectedOption = doctorSelect.options[doctorSelect.selectedIndex];
    const caLam = selectedOption.getAttribute("data-caLam");
    const email = selectedOption.getAttribute("data-email");
    emailField.value = email || "";

    const availableTimes = {
      Sáng: [
        "07:00",
        "07:30",
        "08:00",
        "08:30",
        "09:00",
        "09:30",
        "10:00",
        "10:30",
        "11:00",
      ],
      Chiều: [
        "13:00",
        "13:30",
        "14:00",
        "14:30",
        "15:00",
        "15:30",
        "16:00",
        "16:30",
      ],
    };

    if (!caLam || !availableTimes[caLam]) {
      timeSelect.innerHTML = `<option value="">Không có ca làm hợp lệ</option>`;
      return;
    }

    timeSelect.innerHTML = `<option value="">Chọn giờ khám</option>`;
    availableTimes[caLam].forEach((time) => {
      const opt = document.createElement("option");
      opt.value = time;
      opt.textContent = time;
      timeSelect.appendChild(opt);
    });
  });

  // ----- Kiểm tra form khi submit -----
  if (form) {
    form.addEventListener("submit", (e) => {
      const requiredFields = form.querySelectorAll("[required]");
      const appointmentDate = document.getElementById("appointmentDate");
      let isValid = true;

      requiredFields.forEach((field) => {
        field.nextElementSibling?.remove();
        if (!field.value.trim()) {
          isValid = false;
          field.style.borderColor = "#ef4444";
          const error = document.createElement("span");
          error.style.color = "#ef4444";
          error.style.fontSize = "0.8rem";
          error.textContent = "Vui lòng điền trường này";
          field.parentElement.appendChild(error);
        } else {
          field.style.borderColor = "";
        }
      });

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

  // ----- Load thông báo (chuông) -----
  loadNotifications();
});

// Trường hợp muốn gọi lại khi chọn bác sĩ thủ công
function setEmailBacSi() {
  const select = document.getElementById("doctor");
  const selectedOption = select.options[select.selectedIndex];
  const email = selectedOption.getAttribute("data-email");
  document.getElementById("emailBacSi").value = email || "";
}
