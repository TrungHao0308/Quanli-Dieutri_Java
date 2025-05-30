 function switchTab(tabName) {
            // Hide all tab contents
            document.querySelectorAll('.tab-content').forEach(content => {
                content.classList.remove('active');
            });
            
            // Remove active class from all tab buttons
            document.querySelectorAll('.tab-button').forEach(button => {
                button.classList.remove('active');
            });
            
            // Show selected tab content
            document.getElementById(tabName).classList.add('active');
            
            // Add active class to clicked button
            event.target.closest('.tab-button').classList.add('active');
        }

        // Star rating functionality
        document.querySelectorAll('.star-rating').forEach(rating => {
            const stars = rating.querySelectorAll('.star');
            const ratingType = rating.getAttribute('data-rating');
            const hiddenInput = document.getElementById(ratingType);
            const label = document.getElementById(ratingType.replace('-rating', '-label'));
            
            const ratingLabels = {
                1: 'Rất không hài lòng',
                2: 'Không hài lòng', 
                3: 'Bình thường',
                4: 'Hài lòng',
                5: 'Rất hài lòng'
            };

            stars.forEach((star, index) => {
                star.addEventListener('click', () => {
                    const value = index + 1;
                    hiddenInput.value = value;
                    label.textContent = ratingLabels[value];
                    
                    // Update star display
                    stars.forEach((s, i) => {
                        if (i < value) {
                            s.classList.add('active');
                        } else {
                            s.classList.remove('active');
                        }
                    });
                });

                star.addEventListener('mouseenter', () => {
                    const value = index + 1;
                    label.textContent = ratingLabels[value];
                    
                    // Highlight stars on hover
                    stars.forEach((s, i) => {
                        if (i < value) {
                            s.style.color = '#fbbf24';
                        } else {
                            s.style.color = '#e2e8f0';
                        }
                    });
                });

                rating.addEventListener('mouseleave', () => {
                    const currentValue = hiddenInput.value;
                    if (currentValue) {
                        label.textContent = ratingLabels[currentValue];
                    } else {
                        label.textContent = 'Chọn đánh giá';
                    }
                    
                    // Reset stars to current rating
                    stars.forEach((s, i) => {
                        if (currentValue && i < currentValue) {
                            s.style.color = '#fbbf24';
                        } else {
                            s.style.color = '#e2e8f0';
                        }
                    });
                });
            });
        });

        // Form submission
        document.getElementById('reviewForm').addEventListener('submit', function(e) {
            e.preventDefault();
            
            // Validate required ratings
            const requiredRatings = ['service-rating', 'doctor-rating', 'overall-rating'];
            let isValid = true;
            
            requiredRatings.forEach(rating => {
                if (!document.getElementById(rating).value) {
                    isValid = false;
                    alert('Vui lòng đánh giá đầy đủ các mục bắt buộc');
                    return;
                }
            });
            
            if (!isValid) return;
            
            // Get form data
            const formData = new FormData(this);
            const reviewData = {
                service: formData.get('service'),
                doctor: formData.get('doctor'),
                serviceRating: formData.get('service-rating'),
                doctorRating: formData.get('doctor-rating'),
                overallRating: formData.get('overall-rating'),
                positiveFeedback: formData.get('positive-feedback'),
                improvementFeedback: formData.get('improvement-feedback'),
                overallComment: formData.get('overall-comment'),
                anonymous: formData.get('anonymous') === 'on',
                allowContact: formData.get('allow-contact') === 'on'
            };
            
            // Simulate API call
            submitReview(reviewData);
        });

        function submitReview(data) {
            // Show loading state
            const submitBtn = document.querySelector('button[type="submit"]');
            const originalText = submitBtn.innerHTML;
            submitBtn.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Đang gửi...';
            submitBtn.disabled = true;
            
            // Simulate API call
            setTimeout(() => {
                alert('Đánh giá của bạn đã được gửi thành công! Cảm ơn bạn đã chia sẻ.');
                resetForm();
                
                // Reset button
                submitBtn.innerHTML = originalText;
                submitBtn.disabled = false;
                
                // Switch to history tab
                switchTabByName('review-history');
                
                // Add new review to history (simulation)
                addReviewToHistory(data);
                
            }, 2000);
        }

        function addReviewToHistory(data) {
            const historyContainer = document.querySelector('#review-history .card');
            const serviceNames = {
                'ivf': 'Thụ tinh trong ống nghiệm (IVF)',
                'iui': 'Thụ tinh nhân tạo (IUI)',
                'consultation': 'Tư vấn khám ban đầu',
                'monitoring': 'Theo dõi chu kỳ',
                'surgery': 'Phẫu thuật',
                'hormone-therapy': 'Điều trị hormone'
            };
            
            const doctorNames = {
                'dr-nguyen': 'BS. Nguyễn Thị Minh Châu',
                'dr-tran': 'BS. Trần Văn Đức',
                'dr-le': 'BS. Lê Thị Hương',
                'dr-pham': 'BS. Phạm Minh Tuấn'
            };
            
            const newReviewHTML = `
                <div class="review-item" style="border-color: var(--success-color); background-color: #f0fdf4;">
                    <div class="review-header">
                        <div class="review-info">
                            <h4>${serviceNames[data.service]} <span style="color: var(--success-color); font-size: 0.8rem;">(Mới gửi)</span></h4>
                            <div class="review-meta">
                                ${doctorNames[data.doctor]} • ${new Date().toLocaleDateString('vi-VN')}
                            </div>
                        </div>
                        <div class="review-rating">
                            ${generateStars(data.overallRating)}
                        </div>
                    </div>
                    <div class="review-content">
                        ${data.overallComment || data.positiveFeedback || 'Đánh giá đã được gửi thành công.'}
                    </div>
                    <div class="review-status">
                        <span class="status-badge ${data.anonymous ? 'status-anonymous' : 'status-public'}">
                            ${data.anonymous ? 'Ẩn danh' : 'Công khai'}
                        </span>
                        <span>• Vừa gửi</span>
                    </div>
                </div>
            `;
            
            // Insert at the beginning (after the h3)
            const h3 = historyContainer.querySelector('h3');
            h3.insertAdjacentHTML('afterend', newReviewHTML);
        }

        function generateStars(rating) {
            let starsHTML = '';
            for (let i = 1; i <= 5; i++) {
                starsHTML += `<span class="star ${i <= rating ? 'active' : ''}">★</span>`;
            }
            return starsHTML;
        }

        function switchTabByName(tabName) {
            document.querySelectorAll('.tab-content').forEach(content => {
                content.classList.remove('active');
            });
            
            document.querySelectorAll('.tab-button').forEach(button => {
                button.classList.remove('active');
            });
            
            document.getElementById(tabName).classList.add('active');
            
            if (tabName === 'new-review') {
                document.querySelectorAll('.tab-button')[0].classList.add('active');
            } else {
                document.querySelectorAll('.tab-button')[1].classList.add('active');
            }
        }

        function resetForm() {
            document.getElementById('reviewForm').reset();
            
            // Reset star ratings
            document.querySelectorAll('.star-rating').forEach(rating => {
                const stars = rating.querySelectorAll('.star');
                const ratingType = rating.getAttribute('data-rating');
                const hiddenInput = document.getElementById(ratingType);
                const label = document.getElementById(ratingType.replace('-rating', '-label'));
                
                hiddenInput.value = '';
                label.textContent = 'Chọn đánh giá';
                
                stars.forEach(star => {
                    star.classList.remove('active');
                    star.style.color = '#e2e8f0';
                });
            });
        }

        // Sidebar toggle functionality
        function toggleSidebar() {
            const sidebar = document.querySelector('.sidebar');
            const main = document.querySelector('.main-content');
            sidebar.classList.toggle('hidden');
            main.classList.toggle('full');
        }

        // Logout functionality
        function logout() {
            if (confirm('Bạn có chắc chắn muốn đăng xuất?')) {
                alert('Đã đăng xuất thành công!');
                // Redirect to login page
                // window.location.href = 'login.html';
            }
        }

        // Service change handler - could load related doctors
        document.getElementById('service').addEventListener('change', function() {
            const doctorSelect = document.getElementById('doctor');
            // In a real application, this would filter doctors based on service
            // For now, we keep all doctors available
            console.log('Service selected:', this.value);
        });

        // Initialize page
        document.addEventListener('DOMContentLoaded', function() {
            console.log('Reviews page loaded successfully');
            
            // Set focus on first form field
            document.getElementById('service').focus();
        });