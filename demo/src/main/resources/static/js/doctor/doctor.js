document.addEventListener("DOMContentLoaded", () => {
  // ✅ Tìm kiếm bệnh nhân
  const searchInput = document.querySelector(".search-input");
  if (searchInput) {
    searchInput.addEventListener("input", (e) => {
      const searchTerm = e.target.value.toLowerCase();
      console.log(`Tìm kiếm: ${searchTerm}`);
    });
  }

  // ✅ Xử lý submit form khám qua AJAX
  const examForm = document.getElementById("examinationForm");
  if (examForm) {
    examForm.addEventListener("submit", async (e) => {
      e.preventDefault();

      const formData = new FormData(examForm);

      try {
        const response = await fetch("/doctor/examination/submit", {
          method: "POST",
          body: formData,
        });

        if (response.ok) {
          alert("Kết quả đã được lưu!");
          examForm.reset();
        } else {
          alert("Gửi thất bại");
        }
      } catch (err) {
        console.error("Lỗi gửi:", err);
        alert("Có lỗi xảy ra");
      }
    });
  }

  // ✅ Modal bệnh nhân
  const modal = document.getElementById("patientModal");
  const closeModal = document.querySelector(".modal .close");

  if (modal && closeModal) {
    closeModal.addEventListener("click", () => {
      modal.style.display = "none";
    });

    window.addEventListener("click", (event) => {
      if (event.target === modal) {
        modal.style.display = "none";
      }
    });
  }

  // ✅ Mở modal chi tiết bệnh nhân (giả lập)
  document.querySelectorAll(".patient-card").forEach((card) => {
    card.addEventListener("click", () => {
      modal.style.display = "block";
      document.querySelector(".modal-body").innerHTML = `
        <p><strong>Họ tên:</strong> Nguyễn Thị Lan</p>
        <p><strong>Tuổi:</strong> 32</p>
        <p><strong>Điện thoại:</strong> 0901***</p>
        <p><strong>Lịch hẹn:</strong> 08:30 - Tái khám</p>
      `;
    });
  });

  // ✅ Tự động gán thông tin khi chọn email bệnh nhân
  const emailSelect = document.getElementById("patientEmail");
  const nameInput = document.getElementById("patientName");
  const examDate = document.getElementById("examDate");
  const examNotes = document.getElementById("examNotes");
  const examType = document.getElementById("examType");

  if (emailSelect) {
    emailSelect.addEventListener("change", function () {
      const email = this.value;
      if (!email) return;

      fetch(`/doctor/api/lichkham/${email}`)
        .then((res) => res.json())
        .then((data) => {
          nameInput.value = data.tenBenhNhan || "";
          examDate.value = data.ngayKham || "";
          examNotes.value = data.chiTiet || "";

          if (data.tenDichVu) {
            const option = Array.from(examType.options).find(
              (opt) => opt.text.trim() === data.tenDichVu.trim()
            );
            if (option) examType.value = option.value;
          }
        })
        .catch((err) => {
          console.error("Lỗi khi lấy thông tin:", err);
        });
    });
  }
});
document.addEventListener("DOMContentLoaded", () => {
  const canvas = document.getElementById("bubble-canvas");
  const ctx = canvas.getContext("2d");

  canvas.width = window.innerWidth;
  canvas.height = window.innerHeight;

  window.addEventListener("resize", () => {
    canvas.width = window.innerWidth;
    canvas.height = window.innerHeight;
  });

  let bubbles = [];
  let particles = [];
  let mouse = { x: 0, y: 0 };

  function randomColor() {
    const hues = [180, 200, 220, 240, 260]; // xanh, tím, lam
    const h = hues[Math.floor(Math.random() * hues.length)];
    const s = Math.random() * 30 + 50; // 50–80%
    const l = Math.random() * 20 + 70; // 70–90%
    return `hsla(${h}, ${s}%, ${l}%, `;
  }

  function createBubble(source) {
    const radius = Math.random() * 8 + 4;
    const speedY = Math.random() * 1.4 + 0.6;
    const speedX = Math.random() * 0.3 + 0.2;

    // Cập nhật: thổi lệch vào giữa
    const offset = canvas.width * 0.2; // 20% từ hai bên
    const centerX = canvas.width / 2;

    const startX =
      source === "left"
        ? Math.random() * offset + 20 // ví dụ: 20 → 220
        : canvas.width - (Math.random() * offset + 20); // ví dụ: 1280 → 1080

    const dir = source === "left" ? 1 : -1;

    bubbles.push({
      x: startX,
      y: canvas.height - 10,
      r: radius,
      dx: speedX * dir * 0.7, // hướng nhẹ vào giữa
      dy: -speedY,
      opacity: Math.random() * 0.4 + 0.4,
      exploded: false,
      angle: Math.random() * Math.PI * 2,
      color: randomColor(),
    });
  }

  function drawBubbles() {
    bubbles.forEach((b, i) => {
      if (!b.exploded) {
        b.x += Math.sin(b.angle) * 0.4;
        b.angle += 0.05;

        b.x += b.dx;
        b.y += b.dy;

        ctx.beginPath();
        ctx.arc(b.x, b.y, b.r, 0, Math.PI * 2);
        ctx.fillStyle = `${b.color}${b.opacity})`; // hsla
        ctx.fill();

        const dist = Math.hypot(mouse.x - b.x, mouse.y - b.y);
        if (dist < b.r + 5) {
          explode(b);
          bubbles.splice(i, 1);
        }
      }
    });

    bubbles = bubbles.filter((b) => b.y + b.r > 0);
  }

  function explode(bubble) {
    for (let i = 0; i < 10; i++) {
      particles.push({
        x: bubble.x,
        y: bubble.y,
        r: Math.random() * 2 + 1,
        dx: (Math.random() - 0.5) * 2,
        dy: (Math.random() - 0.5) * 2,
        life: 30,
        opacity: 1,
        color: bubble.color,
      });
    }
  }

  function drawParticles() {
    for (let i = particles.length - 1; i >= 0; i--) {
      let p = particles[i];
      ctx.beginPath();
      ctx.arc(p.x, p.y, p.r, 0, Math.PI * 2);
      ctx.fillStyle = `${p.color}${p.opacity})`;
      ctx.fill();

      p.x += p.dx;
      p.y += p.dy;
      p.opacity -= 0.03;
      p.life--;

      if (p.life <= 0 || p.opacity <= 0) {
        particles.splice(i, 1);
      }
    }
  }

  setInterval(() => {
    createBubble("left");
    createBubble("right");
  }, 250);

  function animate() {
    ctx.clearRect(0, 0, canvas.width, canvas.height);
    drawBubbles();
    drawParticles();
    requestAnimationFrame(animate);
  }

  animate();

  window.addEventListener("mousemove", (e) => {
    mouse.x = e.clientX;
    mouse.y = e.clientY;
  });
});
