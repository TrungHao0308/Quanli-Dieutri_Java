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
          button.classList.remove("btn-update");
          button.classList.add("btn-cancel");
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
          button.classList.remove("btn-cancel");
          button.classList.add("btn-update");
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

const canvas = document.getElementById("snow-canvas");
const ctx = canvas.getContext("2d");

function resizeCanvas() {
  canvas.width = window.innerWidth;
  canvas.height = window.innerHeight;
}
window.addEventListener("resize", resizeCanvas);
resizeCanvas();

let snowflakes = [];

function createSnowflakes() {
  for (let i = 0; i < 100; i++) {
    const type = Math.random() > 0.5 ? "circle" : "flake";
    snowflakes.push({
      x: Math.random() * canvas.width,
      y: Math.random() * canvas.height,
      r: Math.random() * 3 + 1,
      d: Math.random() * 1 + 0.5,
      type: type,
      color: type === "circle" ? "#ffffff" : "#7dcfff", // trắng và xanh dương
      char: type === "flake" ? "❄" : "",
    });
  }
}

function drawSnowflakes() {
  ctx.clearRect(0, 0, canvas.width, canvas.height);
  ctx.font = "20px Arial";
  ctx.textAlign = "center";

  for (let i = 0; i < snowflakes.length; i++) {
    let f = snowflakes[i];
    if (f.type === "circle") {
      ctx.beginPath();
      ctx.fillStyle = f.color;
      ctx.arc(f.x, f.y, f.r, 0, Math.PI * 2, true);
      ctx.fill();
    } else {
      ctx.fillStyle = f.color;
      ctx.fillText(f.char, f.x, f.y);
    }
  }

  moveSnowflakes();
}

function moveSnowflakes() {
  for (let i = 0; i < snowflakes.length; i++) {
    let f = snowflakes[i];
    f.y += f.d;
    f.x += f.d * 0.3;

    // Reset nếu vượt khung
    if (f.y > canvas.height || f.x > canvas.width) {
      f.y = 0;
      f.x = Math.random() * canvas.width;
    }
  }
}

createSnowflakes();
setInterval(drawSnowflakes, 33);
