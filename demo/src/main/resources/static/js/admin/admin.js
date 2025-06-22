console.log("📌 JS loaded!");

// Sample data
let users = [];
let filteredUsers = [];

document.addEventListener("DOMContentLoaded", function () {
  fetch("/admin/users")
    .then((res) => res.json())
    .then((data) => {
      users = data.map((user) => ({
        id: user.id,
        fullName: user.fullName,
        email: user.email,
        role: user.roles[0]?.name?.replace("ROLE_", "").toLowerCase(),
        status: user.isActive ? "active" : "inactive",
        createdAt: user.createdAt || "Chưa có",
        lastLogin: user.lastLogin || "Chưa đăng nhập",
        permissions: [],
      }));

      filteredUsers = [...users]; // ✅ chỉ gán giá trị, không thêm `let`
      renderUsers();
      updateStats();
    });

  setupEventListeners();
});

const permissions = {
  doctor: [
    { id: "view_patients", name: "Xem thông tin bệnh nhân" },
    { id: "edit_medical_records", name: "Chỉnh sửa hồ sơ bệnh án" },
    { id: "prescribe_medicine", name: "Kê đơn thuốc" },
    { id: "view_lab_results", name: "Xem kết quả xét nghiệm" },
    { id: "schedule_appointments", name: "Đặt lịch khám" },
  ],
  manager: [
    { id: "view_reports", name: "Xem báo cáo" },
    { id: "manage_schedules", name: "Quản lý lịch trình" },
    { id: "view_statistics", name: "Xem thống kê" },
    { id: "manage_staff", name: "Quản lý nhân viên" },
    { id: "financial_reports", name: "Báo cáo tài chính" },
    { id: "system_settings", name: "Cài đặt hệ thống" },
  ],
};

let currentEditId = null;

function setupEventListeners() {
  document.getElementById("searchInput").addEventListener("input", filterUsers);
  document.getElementById("roleFilter").addEventListener("change", filterUsers);
  document
    .getElementById("statusFilter")
    .addEventListener("change", filterUsers);
  document
    .getElementById("userForm")
    .addEventListener("submit", handleFormSubmit);
}

function renderUsers() {
  const tbody = document.getElementById("usersTableBody");
  tbody.innerHTML = "";

  filteredUsers.forEach((user) => {
    const row = document.createElement("tr");
    row.innerHTML = `
                    <td>${user.id}</td>
                    
                    <td>${user.fullName}</td>
                    <td>${user.email}</td>
                    <td><span class="role ${user.role}">
  ${
    user.role === "doctor"
      ? "Bác sĩ"
      : user.role === "manager"
      ? "Quản lý"
      : user.role === "admin"
      ? "Quản trị viên"
      : "Bệnh nhân"
  }
</span></td>

                    <td><span class="status ${user.status}">${
      user.status === "active" ? "Hoạt động" : "Không hoạt động"
    }</span></td>
                    <td>${formatDate(user.createdAt)}</td>
                    <td>${formatDate(user.lastLogin)}</td>
                    <td class="actions">
                        <button class="btn btn-sm btn-warning" onclick="editUser(${
                          user.id
                        })" title="Chỉnh sửa">
                            ✏️
                        </button>
                        <button class="btn btn-sm btn-success" onclick="viewPermissions(${
                          user.id
                        })" title="Xem quyền">
                            🔒
                        </button>
                        <button class="btn btn-sm btn-danger" onclick="deleteUser(${
                          user.id
                        })" title="Xóa">
                            🗑️
                        </button>
                    </td>
                `;
    tbody.appendChild(row);
  });
}

function updateStats() {
  const totalUsers = users.length;
  const totalDoctors = users.filter((u) => u.role === "doctor").length;
  const totalManagers = users.filter((u) => u.role === "manager").length;
  const activeUsers = users.filter((u) => u.status === "active").length;

  document.getElementById("totalUsers").textContent = totalUsers;
  document.getElementById("totalDoctors").textContent = totalDoctors;
  document.getElementById("totalManagers").textContent = totalManagers;
  document.getElementById("activeUsers").textContent = activeUsers;
}

function filterUsers() {
  const searchTerm = document.getElementById("searchInput").value.toLowerCase();
  const roleFilter = document.getElementById("roleFilter").value;
  const statusFilter = document.getElementById("statusFilter").value;

  filteredUsers = users.filter((user) => {
    const matchesSearch =
      user.fullName.toLowerCase().includes(searchTerm) ||
      user.email.toLowerCase().includes(searchTerm) ||
      user.username.toLowerCase().includes(searchTerm);
    const matchesRole = !roleFilter || user.role === roleFilter;
    const matchesStatus = !statusFilter || user.status === statusFilter;

    return matchesSearch && matchesRole && matchesStatus;
  });

  renderUsers();
}

function openModal(editId = null) {
  currentEditId = editId;
  const modal = document.getElementById("userModal");
  const form = document.getElementById("userForm");
  const title = document.getElementById("modalTitle");
  document.getElementById("password").value = "";

  if (editId) {
    const user = users.find((u) => u.id === editId);
    title.textContent = "Chỉnh sửa tài khoản";
    document.getElementById("fullName").value = user.fullName;
    document.getElementById("email").value = user.email;
    document.getElementById("password").required = false;
    document.getElementById("role").value = user.role;
  } else {
    title.textContent = "Thêm tài khoản mới";
    form.reset();
    document.getElementById("password").required = true;
  }

  modal.classList.add("show");
}

function closeModal() {
  document.getElementById("userModal").classList.remove("show");
  currentEditId = null;
}

function updatePermissions() {
  // Removed - no longer needed since we're not showing permissions in form
}

function handleFormSubmit(e) {
  e.preventDefault();

  const formData = new FormData(e.target);
  const roleInput = formData.get("role");
  const errorMessageDiv = document.getElementById("errorMessage");

  // Kiểm tra định dạng email
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  if (!emailRegex.test(formData.get("email"))) {
    errorMessageDiv.textContent = "❗ Email không đúng định dạng!";
    errorMessageDiv.style.display = "block";
    return;
  }

  // Kiểm tra độ dài mật khẩu
  if (formData.get("password") && formData.get("password").length < 6) {
    errorMessageDiv.textContent = "❗ Mật khẩu phải có ít nhất 6 ký tự!";
    errorMessageDiv.style.display = "block";
    return;
  }

  // Kiểm tra các trường bắt buộc
  if (
    !formData.get("fullName") ||
    !formData.get("email") ||
    !formData.get("role")
  ) {
    errorMessageDiv.textContent = "❗ Vui lòng điền đầy đủ thông tin!";
    errorMessageDiv.style.display = "block";
    return;
  }

  if (!formData.get("password") && !currentEditId) {
    errorMessageDiv.textContent = "❗ Vui lòng nhập mật khẩu.";
    errorMessageDiv.style.display = "block";
    return;
  }

  const customerData = {
    fullName: formData.get("fullName"),
    email: formData.get("email"),
    password: formData.get("password"),
    role: roleInput, // Gửi "doctor" hoặc "manager"
  };

  if (currentEditId) {
    customerData.id = currentEditId;
  }

  console.log("📤 Gửi dữ liệu:", customerData);
  console.log("📤 Bắt đầu gửi dữ liệu đến /admin/save", customerData);

  fetch("/admin/save", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(customerData),
  })
    .then((response) => {
      if (!response.ok) {
        return response.text().then((text) => {
          throw new Error(text || "Thêm/cập nhật tài khoản thất bại");
        });
      }
      return response.text();
    })
    .then((text) => {
      console.log("📥 Server response:", text);
      if (text.trim() !== "Success") {
        throw new Error(text.trim());
      }
      errorMessageDiv.style.display = "none";
      alert(
        currentEditId
          ? "✅ Cập nhật tài khoản thành công!"
          : "✅ Tạo tài khoản thành công!"
      );
      closeModal();
      currentEditId = null;
      location.reload();
    })
    .catch((error) => {
      console.error("Lỗi chi tiết:", error);
      errorMessageDiv.textContent = "❌ Lỗi: " + error.message;
      errorMessageDiv.style.display = "block";
    });
}

function formatDate(dateString) {
  if (dateString === "Chưa đăng nhập") return dateString;
  const date = new Date(dateString);
  return date.toLocaleDateString("vi-VN");
}

// Close modal when clicking outside
window.addEventListener("click", function (e) {
  const modal = document.getElementById("userModal");
  if (e.target === modal) {
    closeModal();
  }
});

// Handle form submission
document.addEventListener("DOMContentLoaded", function () {
  const form = document.getElementById("userForm");
  if (form) {
    form.addEventListener("submit", handleFormSubmit);
    console.log("✅ Đã gắn handleFormSubmit vào form");
  } else {
    console.warn("❌ Không tìm thấy form userForm để gắn sự kiện");
  }
});

document.addEventListener("DOMContentLoaded", function () {
  const searchInput = document.getElementById("searchInput");
  const roleFilter = document.getElementById("roleFilter");
  const searchIcon = document.querySelector(".search-icon");

  // Fetch danh sách users từ server
  fetch("/admin/users")
    .then((res) => res.json())
    .then((data) => {
      users = data.map((user) => ({
        id: user.id,
        fullName: user.fullName,
        email: user.email,
        role: user.roles[0]?.name.replace("ROLE_", "").toLowerCase() || "", // chỉ lấy vai trò đầu tiên
      }));
      filteredUsers = users;
      renderUsers(filteredUsers);
    });

  // Hàm lọc
  function applyFilters() {
    const keyword = searchInput.value.trim().toLowerCase();
    const selectedRole = roleFilter.value.trim().toLowerCase();

    filteredUsers = users.filter((user) => {
      const matchesKeyword =
        user.fullName.toLowerCase().includes(keyword) ||
        user.email.toLowerCase().includes(keyword);

      const matchesRole = selectedRole === "all" || user.role === selectedRole;

      return matchesKeyword && matchesRole;
    });

    renderUsers(filteredUsers);
  }

  // Ấn Enter để tìm
  searchInput.addEventListener("keypress", (e) => {
    if (e.key === "Enter") {
      applyFilters();
    }
  });

  // Click biểu tượng 🔍 để tìm
  searchIcon.addEventListener("click", () => {
    applyFilters();
  });

  // Lọc theo vai trò
  roleFilter.addEventListener("change", () => {
    applyFilters();
  });
});

// Hiển thị danh sách người dùng
function renderUsers(data) {
  const tableBody = document.getElementById("usersTableBody");
  tableBody.innerHTML = "";

  if (!data.length) {
    tableBody.innerHTML = `<tr><td colspan="8">Không có kết quả nào.</td></tr>`;
    return;
  }

  data.forEach((user) => {
    const row = document.createElement("tr");
    row.innerHTML = `
      <td>${user.id}</td>
      <td>${user.fullName}</td>
      <td>${user.email}</td>
      <td>${getRoleLabel(user.role)}</td>
      <td>${user.active ? "Hoạt động" : "Không hoạt động"}</td>
      <td>${formatDate(user.createdAt)}</td>
      <td>${formatDate(user.lastLogin)}</td>
      <td>
        <button onclick="openModal(${user.id})">✏️</button>
      </td>
    `;
    tableBody.appendChild(row);
  });
}

// Hiển thị tên vai trò tiếng Việt
function getRoleLabel(role) {
  switch (role) {
    case "admin":
      return "Quản trị viên";
    case "manager":
      return "Quản lý";
    case "doctor":
      return "Bác sĩ";
    case "customer":
      return "Bệnh nhân";
    default:
      return role;
  }
}
function applyFilters() {
  const keyword = searchInput.value.trim().toLowerCase();
  const selectedRole = roleFilter.value.trim().toLowerCase();

  filteredUsers = users.filter((user) => {
    const matchesKeyword =
      user.fullName.toLowerCase().includes(keyword) ||
      user.email.toLowerCase().includes(keyword);

    const matchesRole =
      selectedRole === "all" ||
      selectedRole === "" ||
      user.role === selectedRole;

    return matchesKeyword && matchesRole;
  });

  renderUsers(filteredUsers);
}
roleFilter.addEventListener("change", () => {
  applyFilters();
});
