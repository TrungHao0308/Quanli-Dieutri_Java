  function toggleSidebar() {
            const sidebar = document.querySelector('.sidebar');
            const main = document.querySelector('.main-content');
            sidebar.classList.toggle('hidden');
            main.classList.toggle('full');
        }

        // Tab switching functionality
        function switchTab(tabName) {
            // Hide all tab contents
            const tabContents = document.querySelectorAll('.tab-content');
            tabContents.forEach(content => content.classList.remove('active'));
            
            // Remove active class from all tab buttons
            const tabButtons = document.querySelectorAll('.tab-btn');
            tabButtons.forEach(btn => btn.classList.remove('active'));
            
            // Show selected tab content
            document.getElementById(tabName).classList.add('active');
            
            // Add active class to clicked button
            event.target.classList.add('active');
        }

        // Treatment detail modal functionality
        function openTreatmentDetail(treatmentId) {
            const modal = document.getElementById('treatmentModal');
            const modalTitle = document.getElementById('modalTitle');
            const modalBody = document.getElementById('modalBody');
            
            // Sample data - in real application, this would come from API
            const treatmentData = {
                'TT001': {
                    title: 'Điều trị Tâm lý trẻ em - TT001',
                    status: 'Hoàn thành',
                    doctor: 'BS. Nguyễn Văn A',
                    period: '01/01/2024 - 15/03/2024',
                    sessions: '12/12 buổi',
                    cost: '6,000,000 VNĐ',
                    diagnosis: 'Rối loạn lo âu phân ly ở trẻ em',
                    treatment: 'Liệu pháp nhận thức hành vi (CBT) kết hợp với liệu pháp chơi',
                    progress: [
                        'Buổi 1-3: Đánh giá tâm lý và xây dựng mối quan hệ tin cậy',
                        'Buổi 4-6: Dạy kỹ năng thư giãn và quản lý lo âu',
                        'Buổi 7-9: Thực hành các tình huống thực tế',
                        'Buổi 10-12: Đánh giá kết quả và lập kế hoạch duy trì'
                    ],
                    results: 'Triệu chứng lo âu giảm 70%, khả năng thích ứng xã hội cải thiện đáng kể',
                    notes: 'Bệnh nhân có sự hợp tác tốt, gia đình tham gia tích cực vào quá trình điều trị'
                },
                'TT002': {
                    title: 'Tư vấn gia đình - TT002',
                    status: 'Đang tiến hành',
                    doctor: 'BS. Trần Thị B',
                    period: '15/04/2024 - 30/06/2024',
                    sessions: '6/10 buổi',
                    cost: '8,000,000 VNĐ',
                    diagnosis: 'Xung đột gia đình, khó khăn trong giao tiếp',
                    treatment: 'Liệu pháp gia đình hệ thống và kỹ năng giao tiếp',
                    progress: [
                        'Buổi 1-2: Đánh giá động lực gia đình và xác định vấn đề',
                        'Buổi 3-4: Dạy kỹ năng giao tiếp hiệu quả',
                        'Buổi 5-6: Thực hành giải quyết xung đột',
                        'Buổi 7-10: Củng cố và duy trì thay đổi tích cực (Dự kiến)'
                    ],
                    results: 'Đang tiến triển tốt, mức độ xung đột giảm 40%',
                    notes: 'Cần tiếp tục duy trì sự tham gia của tất cả thành viên gia đình'
                },
                'TT003': {
                    title: 'Trị liệu nhóm - TT003',
                    status: 'Đã hủy',
                    doctor: 'BS. Lê Văn C',
                    period: '01/02/2024 - 15/02/2024',
                    sessions: '2/8 buổi',
                    cost: '1,500,000 VNĐ',
                    diagnosis: 'Rối loạn lo âu xã hội',
                    treatment: 'Liệu pháp nhóm tập trung vào kỹ năng xã hội',
                    progress: [
                        'Buổi 1: Làm quen và thiết lập quy tắc nhóm',
                        'Buổi 2: Chia sẻ kinh nghiệm và khó khăn cá nhân'
                    ],
                    results: 'Liệu trình bị gián đoạn do lý do cá nhân của bệnh nhân',
                    notes: 'Bệnh nhân có thể tiếp tục với hình thức điều trị cá nhân'
                }
            };
            
            const data = treatmentData[treatmentId];
            if (data) {
                modalTitle.textContent = data.title;
                modalBody.innerHTML = `
                    <div class="detail-section">
                        <h4 class="detail-title">Thông tin chung</h4>
                        <div class="info-grid">
                            <div class="info-item">
                                <div class="info-item-label">Trạng thái</div>
                                <div class="info-item-value">${data.status}</div>
                            </div>
                            <div class="info-item">
                                <div class="info-item-label">Bác sĩ điều trị</div>
                                <div class="info-item-value">${data.doctor}</div>
                            </div>
                            <div class="info-item">
                                <div class="info-item-label">Thời gian</div>
                                <div class="info-item-value">${data.period}</div>
                            </div>
                            <div class="info-item">
                                <div class="info-item-label">Số buổi</div>
                                <div class="info-item-value">${data.sessions}</div>
                            </div>
                            <div class="info-item">
                                <div class="info-item-label">Chi phí</div>
                                <div class="info-item-value">${data.cost}</div>
                            </div>
                        </div>
                    </div>
                    
                    <div class="detail-section">
                        <h4 class="detail-title">Chẩn đoán</h4>
                        <p>${data.diagnosis}</p>
                    </div>
                    
                    <div class="detail-section">
                        <h4 class="detail-title">Phương pháp điều trị</h4>
                        <p>${data.treatment}</p>
                    </div>
                    
                    <div class="detail-section">
                        <h4 class="detail-title">Tiến trình điều trị</h4>
                        <ul class="medical-list">
                            ${data.progress.map(item => `<li>${item}</li>`).join('')}
                        </ul>
                    </div>
                    
                    <div class="detail-section">
                        <h4 class="detail-title">Kết quả</h4>
                        <p>${data.results}</p>
                    </div>
                    
                    <div class="detail-section">
                        <h4 class="detail-title">Ghi chú của bác sĩ</h4>
                        <p>${data.notes}</p>
                    </div>
                `;
                
                modal.style.display = 'block';
            }
        }

        function closeTreatmentDetail() {
            document.getElementById('treatmentModal').style.display = 'none';
        }

        // Close modal when clicking outside of it
        window.onclick = function(event) {
            const modal = document.getElementById('treatmentModal');
            if (event.target == modal) {
                modal.style.display = 'none';
            }
        }

        // Logout functionality
        function logout() {
            if (confirm('Bạn có chắc chắn muốn đăng xuất?')) {
                alert('Đã đăng xuất thành công!');
                // In real application, redirect to login page
                // window.location.href = 'login.html';
            }
        }

        // Add click handlers for tab buttons
        document.addEventListener('DOMContentLoaded', function() {
            const tabButtons = document.querySelectorAll('.tab-btn');
            tabButtons.forEach(button => {
                button.addEventListener('click', function() {
                    const tabName = this.textContent.includes('Lịch sử') ? 'treatment-history' : 'medical-record';
                    switchTab(tabName);
                });
            });
        });