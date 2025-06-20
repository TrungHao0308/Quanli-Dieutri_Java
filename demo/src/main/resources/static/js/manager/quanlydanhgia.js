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
document.querySelectorAll(".btn").forEach((btn) => {
  btn.addEventListener("mouseover", function () {
    const tooltip = document.createElement("span");
    tooltip.className = "tooltip";
    tooltip.innerText = this.innerText;
    this.appendChild(totooltip);
    setTimeout(() => tooltip.classList.add("show"), 10);
  });
  btn.addEventListener("mouseout", function () {
    const tooltip = this.querySelector(".tooltip");
    if (tooltip) tooltip.remove();
  });
});
