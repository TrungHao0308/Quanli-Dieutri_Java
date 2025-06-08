 function selectService(serviceId, cardElement) {
        // Bỏ chọn tất cả các dịch vụ khác
        document.querySelectorAll('.service-card').forEach(card => {
            card.classList.remove('selected');
        });

        // Đánh dấu dịch vụ được chọn
        cardElement.classList.add('selected');

        // Hiển thị form đăng ký
        document.getElementById('registrationForm').classList.add('active');

        // Lưu tên dịch vụ được chọn (nếu cần)
        const serviceInput = document.getElementById('selectedService');
        if (serviceInput) {
            serviceInput.value = serviceId;
        } else {
            // Nếu chưa có input hidden, thêm vào form
            const hiddenInput = document.createElement('input');
            hiddenInput.type = 'hidden';
            hiddenInput.id = 'selectedService';
            hiddenInput.name = 'selectedService';
            hiddenInput.value = serviceId;
            document.getElementById('serviceForm').appendChild(hiddenInput);
        }
    }

    // Hàm xử lý chọn bác sĩ
    function selectDoctor(doctorId, cardElement) {
        // Bỏ chọn tất cả bác sĩ khác
        document.querySelectorAll('.doctor-card').forEach(card => {
            card.classList.remove('selected');
        });

        // Đánh dấu bác sĩ được chọn
        cardElement.classList.add('selected');

        // Lưu thông tin bác sĩ được chọn (nếu cần)
        const doctorInput = document.getElementById('selectedDoctor');
        if (doctorInput) {
            doctorInput.value = doctorId;
        } else {
            const hiddenDoctorInput = document.createElement('input');
            hiddenDoctorInput.type = 'hidden';
            hiddenDoctorInput.id = 'selectedDoctor';
            hiddenDoctorInput.name = 'selectedDoctor';
            hiddenDoctorInput.value = doctorId;
            document.getElementById('serviceForm').appendChild(hiddenDoctorInput);
        }
    }

    // Hàm xử lý submit form (bạn có thể tùy chỉnh)
    function handleSubmit(event) {
        event.preventDefault();
        alert('Form đã được gửi thành công! (Bạn có thể thay thế bằng xử lý thực tế)');
    }
	 function toggleSidebar() {
        const sidebar = document.querySelector('.sidebar');
        const main = document.querySelector('.main-content');
        sidebar.classList.toggle('hidden');
        main.classList.toggle('full');
    }