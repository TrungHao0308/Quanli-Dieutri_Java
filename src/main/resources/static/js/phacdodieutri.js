// JavaScript for switching tabs
document.addEventListener("DOMContentLoaded", () => {
  const tabs = document.querySelectorAll(".protocol-tab");
  const contents = document.querySelectorAll(".protocol-content");

  tabs.forEach((tab, index) => {
    tab.addEventListener("click", () => {
      tabs.forEach((t) => t.classList.remove("active"));
      tab.classList.add("active");

      contents.forEach((content, cIndex) => {
        content.style.display = cIndex === index ? "block" : "none";
      });
    });
  });
});
