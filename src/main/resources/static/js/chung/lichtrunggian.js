async function fetchLichKham(email) {
  const response = await fetch(`/doctor/api/lichkham/${email}`);
  if (!response.ok) return;
  const data = await response.json();
  document.getElementById("tenBenhNhan").value = data.tenBenhNhan || "";
  document.getElementById("tenDichVu").value = data.tenDichVu || "";
  document.getElementById("ngayKham").value = data.ngayKham || "";
  document.getElementById("chiTiet").value = data.chiTiet || "";
}
