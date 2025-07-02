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
