// Service management variablesAdd commentMore actions
let currentEditId = null;
let isLoading = false;

// Open edit modal for a service
function openEditModal(serviceId) {
  if (isLoading) return;

  if (!serviceId || isNaN(serviceId)) {
    console.error("Invalid service ID:", serviceId);
    alert("ID dịch vụ không hợp lệ!");
    return;
  }

  currentEditId = serviceId;
  isLoading = true;

  // Show loading state
  const modal = document.getElementById("editModal");
  if (modal) {
    modal.style.display = "block";
  }
  fetch(`/manager/quanlydichvu/${serviceId}`) // Cần thêm endpoint này trong ManagerControllerAdd commentMore actions
    .then((response) => {
      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }
      return response.json();
    })
    .then((service) => {
      // Populate form fields
      const fields = {
        editServiceName: service.serviceName || "",
        editPrice: service.price || "",
        editDescription: service.description || "",
        editCategory: service.category || "",
      };

      Object.entries(fields).forEach(([fieldId, value]) => {
        const field = document.getElementById(fieldId);
        if (field) {
          field.value = value;
        } else {
          console.warn(`Field ${fieldId} not found`);
        }
      });
    })
    .catch((error) => {
      console.error("Error fetching service:", error);
      alert("Lỗi khi tải thông tin dịch vụ: " + error.message);
      closeEditModal();
    })
    .finally(() => {
      isLoading = false;
    });
}

// Smooth scroll to sections

// Initialize tooltips for buttons
document.querySelectorAll(".btn").forEach((btn) => {
  btn.addEventListener("mouseover", function () {
    const tooltip = document.createElement("span");
    tooltip.className = "tooltip";
    tooltip.innerText = this.innerText;
    this.appendChild(tooltip);
    setTimeout(() => tooltip.classList.add("show"), 10);
  });
  btn.addEventListener("mouseout", function () {
    const tooltip = this.querySelector(".tooltip");
    if (tooltip) tooltip.remove();
  });
});

// Manage active state for sidebar navigation
document.querySelectorAll(".sidebar-nav .nav-item").forEach((item) => {
  item.addEventListener("click", function () {
    // Remove active class from all items
    document.querySelectorAll(".sidebar-nav .nav-item").forEach((el) => {
      el.classList.remove("active");
    });
    // Add active class to clicked item
    this.classList.add("active");
  });
});

// Set active state based on current URL when page loads
document.addEventListener("DOMContentLoaded", function () {
  const currentPath = window.location.pathname;
  document.querySelectorAll(".sidebar-nav .nav-item a").forEach((link) => {
    const href = link.getAttribute("href");
    // Compare current path with href, accounting for Thymeleaf's context path
    if (currentPath.includes(href.replace("@{", "").replace("}", ""))) {
      link.parentElement.classList.add("active");
    }
  });
});
document.addEventListener("DOMContentLoaded", function () {
  const canvas = document.getElementById("snow-canvas");
  if (!canvas) {
    console.error("Không tìm thấy phần tử #snow-canvas");
    return;
  }

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

      if (f.y > canvas.height || f.x > canvas.width) {
        f.y = 0;
        f.x = Math.random() * canvas.width;
      }
    }
  }

  createSnowflakes();
  setInterval(drawSnowflakes, 33);
});
