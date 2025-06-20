document.addEventListener('DOMContentLoaded', function () {
  const form = document.querySelector('.filter-form');

  form.addEventListener('submit', function (e) {
    e.preventDefault();

    // Lấy dữ liệu từ các trường form
    const template = form.querySelector('select').value;
    const status = form.querySelector('input[placeholder="Trạng thái"]').value;
    const result = form.querySelector('input[placeholder="Kết quả trả về"]').value;
    const customer = form.querySelector('input[placeholder="Tên khách hàng"]').value;
    const tax = form.querySelector('input[placeholder="Mã số thuế"]').value;

    // In thông tin lọc để kiểm tra
    alert(`Lọc báo cáo:\nMẫu số: ${template}\nTrạng thái: ${status}\nKhách hàng: ${customer}\nMã số thuế: ${tax}`);

    // TODO: Gửi dữ liệu này qua fetch/AJAX nếu backend có xử lý
  });
});
