function toggleSidebar() {
  const sidebar = document.querySelector(".sidebar");
  sidebar.classList.toggle("collapsed");
  const mainContent = document.querySelector(".main-content");
  mainContent.style.marginLeft = sidebar.classList.contains("collapsed")
    ? "0"
    : "250px";
  mainContent.style.width = sidebar.classList.contains("collapsed")
    ? "100%"
    : "calc(100% - 250px)";
}

// Smooth scroll to sections
document.querySelectorAll('a[href^="#"]').forEach((anchor) => {
  anchor.addEventListener("click", function (e) {
    e.preventDefault();
    document.querySelector(this.getAttribute("href")).scrollIntoView({
      behavior: "smooth",
    });
  });
});

// Initialize tooltips for buttons
// document.querySelectorAll(".btn").forEach((btn) => {
//   btn.addEventListener("mouseover", function () {
//     const tooltip = document.createElement("span");
//     tooltip.className = "tooltip";
//     tooltip.innerText = this.innerText;
//     this.appendChild(tooltip);
//     setTimeout(() => tooltip.classList.add("show"), 10);
//   });
//   btn.addEventListener("mouseout", function () {
//     const tooltip = this.querySelector(".tooltip");
//     if (tooltip) tooltip.remove();
//   });
// });

// Manage active state for sidebar navigation
document.querySelectorAll(".sidebar-nav .nav-item").forEach((item) => {
  item.addEventListener("click", function () {
    document.querySelectorAll(".sidebar-nav .nav-item").forEach((el) => {
      el.classList.remove("active");
    });
    this.classList.add("active");
  });
});

function handleDoctorAction(button) {
  const id = button.getAttribute("data-id");
  const action = button.innerText.trim();
  const specialtySelect = document.getElementById(`specialty-${id}`);
  const shiftSelect = document.getElementById(`shift-${id}`);

  if (action === "Cập nhật") {
    const specialty = specialtySelect.value;
    const shift = shiftSelect.value;

    if (!specialty || !shift) {
      alert("Vui lòng chọn chuyên môn và ca làm trước khi cập nhật.");
      return;
    }

    fetch("/manager/themBacSi", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ id, specialty, shift }),
    })
      .then((res) => {
        if (res.ok) {
          specialtySelect.disabled = true;
          shiftSelect.disabled = true;
          button.innerText = "Hủy";
        } else {
          alert("Lỗi khi cập nhật thông tin bác sĩ. Vui lòng thử lại.");
        }
      })
      .catch((error) => {
        console.error("Error:", error);
        alert(
          "Đã xảy ra lỗi. Vui lòng kiểm tra kết nối mạng hoặc liên hệ quản trị viên."
        );
      });
  } else if (action === "Hủy") {
    fetch(`/manager/huyBacSi/${id}`, {
      method: "DELETE",
    })
      .then((res) => {
        if (res.ok) {
          specialtySelect.disabled = false;
          shiftSelect.disabled = false;
          specialtySelect.value = "";
          shiftSelect.value = "";
          button.innerText = "Cập nhật";
        } else {
          alert("Lỗi khi hủy thông tin bác sĩ. Vui lòng thử lại.");
        }
      })
      .catch((error) => {
        console.error("Error:", error);
        alert(
          "Đã xảy ra lỗi. Vui lòng kiểm tra kết nối mạng hoặc liên hệ quản trị viên."
        );
      });
  }
}
