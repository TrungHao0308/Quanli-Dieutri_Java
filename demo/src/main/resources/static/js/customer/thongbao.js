document.addEventListener("DOMContentLoaded", () => {
  const notificationItems = document.querySelectorAll(".notification-item");
  notificationItems.forEach((item) => {
    item.addEventListener("click", () => {
      item.style.backgroundColor = "#f3f4f6";
      item.querySelector("h3").style.opacity = "0.7";
      // Có thể gửi yêu cầu đến server để đánh dấu là đã đọc
      console.log(
        "Thông báo đã được đánh dấu là đọc:",
        item.querySelector("h3").textContent
      );
    });
  });
});
