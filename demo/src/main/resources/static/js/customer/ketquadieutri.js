document.addEventListener("DOMContentLoaded", () => {
  const table = document.querySelector(".results-table");
  if (table) {
    const headers = table.querySelectorAll("th");
    headers.forEach((header, index) => {
      header.addEventListener("click", () => {
        const tbody = table.querySelector("tbody");
        const rows = Array.from(tbody.querySelectorAll("tr"));
        // Skip sorting if table is empty
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
      });
    });
  }
});
