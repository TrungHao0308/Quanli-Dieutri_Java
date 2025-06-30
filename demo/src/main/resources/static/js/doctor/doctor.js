document.addEventListener("DOMContentLoaded", () => {
  // ✅ Tìm kiếm bệnh nhân
  const searchInput = document.querySelector(".search-input");
  if (searchInput) {
    searchInput.addEventListener("input", (e) => {
      const searchTerm = e.target.value.toLowerCase();
      console.log(`Tìm kiếm: ${searchTerm}`);
      // Có thể thêm API call tại đây nếu muốn tìm kiếm real-time
    });
  }

  // ✅ Xử lý submit form khám
  const examForm = document.getElementById("examinationForm");
  if (examForm) {
    examForm.addEventListener("submit", (e) => {
      e.preventDefault(); // Ngăn gửi form mặc định nếu muốn xử lý AJAX
      const formData = {
        examDate: document.getElementById("examDate").value,
        examType: document.getElementById("examType").value,
        examResults: document.getElementById("examResults").value,
        importantValues: document.getElementById("importantValues").value,
        examNotes: document.getElementById("examNotes").value,
      };
      console.log("Dữ liệu form:", formData);

      // Gửi AJAX hoặc xử lý client-side tại đây nếu muốn
      alert("Kết quả khám đã được lưu!");
      examForm.reset();
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

  // ✅ Xử lý click vào thẻ bệnh nhân để mở modal
  document.querySelectorAll(".patient-card").forEach((card) => {
    card.addEventListener("click", () => {
      modal.style.display = "block";

      // TODO: Có thể thêm gọi API hoặc lấy dữ liệu động ở đây
      document.querySelector(".modal-body").innerHTML = `
        <p><strong>Họ tên:</strong> Nguyễn Thị Lan</p>
        <p><strong>Tuổi:</strong> 32</p>
        <p><strong>Điện thoại:</strong> 0901***</p>
        <p><strong>Lịch hẹn:</strong> 08:30 - Tái khám</p>
      `;
    });
  });
});

document.addEventListener("DOMContentLoaded", function () {
  const emailSelect = document.getElementById("patientEmail");
  const nameInput = document.getElementById("patientName");
  const examDate = document.getElementById("examDate");
  const examNotes = document.getElementById("examNotes");
  const examType = document.getElementById("examType");

  emailSelect.addEventListener("change", function () {
    const email = this.value;
    if (!email) return;

    fetch(`/doctor/api/lichkham/${email}`)
      .then((res) => res.json())
      .then((data) => {
        nameInput.value = data.tenBenhNhan || "";
        examDate.value = data.ngayKham || "";
        examNotes.value = data.chiTiet || "";

        // Gán dịch vụ vào select nếu tồn tại
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
});
