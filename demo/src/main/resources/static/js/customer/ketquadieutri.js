function loadTreatmentResults() {
  fetch("/api/customer/ketquadieutri")
    .then((res) => res.json())
    .then((data) => {
      const tbody = document.querySelector(".results-table tbody");
      tbody.innerHTML = "";

      if (data.length === 0) {
        const tr = document.createElement("tr");
        tr.innerHTML = `<td colspan="4">Không có kết quả nào để hiển thị.</td>`;
        tbody.appendChild(tr);
        return;
      }

      data.forEach((item) => {
        const tr = document.createElement("tr");
        tr.innerHTML = `
          <td>${item.date}</td>
          <td>${item.testType}</td>
          <td>${item.result}</td>
          <td>${item.doctorComment}</td>
        `;
        tbody.appendChild(tr);
      });

      // Gọi lại hàm thêm sự kiện sắp xếp sau khi load dữ liệu
      attachSortEvents();
    })
    .catch((err) => console.error("Lỗi tải kết quả:", err));
}

// Hàm gắn sự kiện click để sắp xếp bảng
function attachSortEvents() {
  const table = document.querySelector(".results-table");
  if (!table) return;

  const headers = table.querySelectorAll("th");
  headers.forEach((header, index) => {
    header.onclick = () => {
      const tbody = table.querySelector("tbody");
      const rows = Array.from(tbody.querySelectorAll("tr"));
      if (rows.length === 0 || rows[0].querySelector("td[colspan]")) return;

      const isAscending = header.dataset.sort !== "asc";
      rows.sort((a, b) => {
        const aText = a.children[index].textContent.trim();
        const bText = b.children[index].textContent.trim();

        if (header.textContent.includes("Ngày")) {
          const aDate = new Date(aText);
          const bDate = new Date(bText);
          return isAscending ? aDate - bDate : bDate - aDate;
        }

        return isAscending
          ? aText.localeCompare(bText, "vi")
          : bText.localeCompare(aText, "vi");
      });

      header.dataset.sort = isAscending ? "asc" : "desc";
      tbody.innerHTML = "";
      rows.forEach((row) => tbody.appendChild(row));
    };
  });
}

// Khi trang load
// document.addEventListener("DOMContentLoaded", () => {
//   loadTreatmentResults();
//   setInterval(loadTreatmentResults, 5000); // cập nhật mỗi 5 giây
// });
