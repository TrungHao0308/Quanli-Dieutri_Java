function toggleSidebar() {
  const sidebar = document.querySelector(".sidebar");
  const main = document.querySelector(".main-content");
  sidebar.classList.toggle("hidden");
  main.classList.toggle("full");
}

function logout() {
  // Redirect hoặc xử lý đăng xuất tại đây
  alert("Bạn đã đăng xuất!");
  window.location.href = "login.html"; // Chuyển đến trang đăng nhập
}

function showPage(pageId) {
  document.querySelectorAll(".content-page").forEach((page) => {
    page.classList.remove("active");
  });
  document.getElementById(pageId).classList.add("active");
}

// Xử lý sidebar khi tải trang
document.addEventListener("DOMContentLoaded", () => {
  const currentPath = window.location.pathname; // Lấy URL hiện tại
  const navItems = document.querySelectorAll(".sidebar-nav .nav-item");

  // Xóa lớp active khỏi tất cả các mục
  navItems.forEach((item) => {
    item.classList.remove("active");
  });

  // Ánh xạ URL với mục sidebar tương ứng
  const pathToNavMap = {
    "/customer/thongbao": "Thông báo",
    "/customer/dangkidichvu": "Đăng ký dịch vụ",
    "/customer/lichtrinhdieutri": "Lịch trình điều trị",
    "/customer/ketquadieutri": "Kết quả điều trị",
    "/customer/danhgia": "Đánh giá",
    "/customer/hosocanhan": "Hồ sơ cá nhân",
  };

  // Tìm mục tương ứng với URL hiện tại và thêm lớp active
  navItems.forEach((item) => {
    const link = item.querySelector("a");
    const linkText = item.querySelector("span").textContent;
    if (link && pathToNavMap[currentPath] === linkText) {
      item.classList.add("active");
    }
  });
});

// Quản lý trạng thái active khi bấm vào mục sidebar
document.querySelectorAll(".sidebar-nav .nav-item").forEach((item) => {
  item.addEventListener("click", function () {
    document.querySelectorAll(".sidebar-nav .nav-item").forEach((el) => {
      el.classList.remove("active");
    });
    this.classList.add("active");
  });
});
