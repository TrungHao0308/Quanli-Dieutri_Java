
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
