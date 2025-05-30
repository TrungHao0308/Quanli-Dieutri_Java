function toggleSidebar() {
            const sidebar = document.querySelector('.sidebar');
            const main = document.querySelector('.main-content');
            sidebar.classList.toggle('hidden');
            main.classList.toggle('full');
        }

        // Tab Management
        function openTab(evt, tabName) {
            var i, tabcontent, tablinks;
            
            // Hide all tab content
            tabcontent = document.getElementsByClassName("tab-content");
            for (i = 0; i < tabcontent.length; i++) {
                tabcontent[i].classList.remove("active");
            }
            
            // Remove active class from all tab buttons
            tablinks = document.getElementsByClassName("tab-btn");
            for (i = 0; i < tablinks.length; i++) {
                tablinks[i].classList.remove("active");
            }
            
            // Show the selected tab content and mark the button as active
            document.getElementById(tabName).classList.add("active");
            evt.currentTarget.classList.add("active");
        }

        // Avatar Preview
        function previewAvatar(input) {
            if (input.files && input.files[0]) {
                const reader = new FileReader();
                
                reader.onload = function(e) {
                    const preview = document.getElementById('avatar-preview');
                    const text = document.getElementById('avatar-text');
                    
                    preview.src = e.target.result;
                    preview.style.display = 'block';
                    text.style.display = 'none';
                };
                
                reader.readAsDataURL(input.files[0]);
            }
        }

        // Profile Form Submission
        document.getElementById('profile-form').addEventListener('submit', function(e) {
            e.preventDefault();
            
            // Simulate API call
            setTimeout(() => {
                showAlert('success-message');
                
                // Update sidebar user name
                const fullname = document.getElementById('fullname').value;
                const userName = document.querySelector('.user-details h3');
                if (userName) {
                    userName.textContent = fullname;
                }
            }, 500);
        });

        // Reset Form
        function resetForm() {
            document.getElementById('profile-form').reset();
            
            // Reset avatar
            const preview = document.getElementById('avatar-preview');
            const text = document.getElementById('avatar-text');
            preview.style.display = 'none';
            text.style.display = 'block';
        }

        // Password Strength Check
        function checkPasswordStrength() {
            const password = document.getElementById('new-password').value;
            
            // Check length
            const lengthCheck = document.getElementById('length-check');
            if (password.length >= 8) {
                lengthCheck.innerHTML = '<i class="fas fa-check check"></i> Ít nhất 8 ký tự';
                lengthCheck.style.color = 'var(--success-color)';
            } else {
                lengthCheck.innerHTML = '<i class="fas fa-times"></i> Ít nhất 8 ký tự';
                lengthCheck.style.color = '#64748b';
            }
            
            // Check uppercase
            const uppercaseCheck = document.getElementById('uppercase-check');
            if (/[A-Z]/.test(password)) {
                uppercaseCheck.innerHTML = '<i class="fas fa-check check"></i> Có chữ hoa';
                uppercaseCheck.style.color = 'var(--success-color)';
            } else {
                uppercaseCheck.innerHTML = '<i class="fas fa-times"></i> Có chữ hoa';
                uppercaseCheck.style.color = '#64748b';
            }
            
            // Check lowercase
            const lowercaseCheck = document.getElementById('lowercase-check');
            if (/[a-z]/.test(password)) {
                lowercaseCheck.innerHTML = '<i class="fas fa-check check"></i> Có chữ thường';
                lowercaseCheck.style.color = 'var(--success-color)';
            } else {
                lowercaseCheck.innerHTML = '<i class="fas fa-times"></i> Có chữ thường';
                lowercaseCheck.style.color = '#64748b';
            }
            
            // Check number
            const numberCheck = document.getElementById('number-check');
            if (/[0-9]/.test(password)) {
                numberCheck.innerHTML = '<i class="fas fa-check check"></i> Có số';
                numberCheck.style.color = 'var(--success-color)';
            } else {
                numberCheck.innerHTML = '<i class="fas fa-times"></i> Có số';
                numberCheck.style.color = '#64748b';
            }
            
            // Check special characters
            const specialCheck = document.getElementById('special-check');
            if (/[!@#$%^&*(),.?":{}|<>]/.test(password)) {
                specialCheck.innerHTML = '<i class="fas fa-check check"></i> Có ký tự đặc biệt';
                specialCheck.style.color = 'var(--success-color)';
            } else {
                specialCheck.innerHTML = '<i class="fas fa-times"></i> Có ký tự đặc biệt';
                specialCheck.style.color = '#64748b';
            }
        }

        // Password Form Submission
        document.getElementById('password-form').addEventListener('submit', function(e) {
            e.preventDefault();
            
            const currentPassword = document.getElementById('current-password').value;
            const newPassword = document.getElementById('new-password').value;
            const confirmPassword = document.getElementById('confirm-password').value;
            
            // Validate passwords
            if (newPassword !== confirmPassword) {
                showPasswordError('Mật khẩu xác nhận không khớp!');
                return;
            }
            
            if (newPassword.length < 8) {
                showPasswordError('Mật khẩu phải có ít nhất 8 ký tự!');
                return;
            }
            
            // Simulate API call
            setTimeout(() => {
                showAlert('password-success');
                document.getElementById('password-form').reset();
                
                // Reset password strength indicators
                const checks = ['length-check', 'uppercase-check', 'lowercase-check', 'number-check', 'special-check'];
                checks.forEach(id => {
                    const element = document.getElementById(id);
                    const text = element.textContent.substring(2); // Remove icon
                    element.innerHTML = '<i class="fas fa-times"></i> ' + text;
                    element.style.color = '#64748b';
                });
            }, 500);
        });

        // Notification Form Submission
        document.getElementById('notification-form').addEventListener('submit', function(e) {
            e.preventDefault();
            
            // Simulate API call
            setTimeout(() => {
                showAlert('notification-success');
            }, 500);
        });

        // Show Alert Messages
        function showAlert(alertId) {
            const alert = document.getElementById(alertId);
            alert.classList.add('show');
            
            setTimeout(() => {
                alert.classList.remove('show');
            }, 3000);
        }

        // Show Password Error
        function showPasswordError(message) {
            const errorAlert = document.getElementById('password-error');
            const errorText = document.getElementById('password-error-text');
            
            errorText.textContent = message;
            errorAlert.classList.add('show');
            
            setTimeout(() => {
                errorAlert.classList.remove('show');
            }, 5000);
        }

        // Logout Function
        function logout() {
            if (confirm('Bạn có chắc chắn muốn đăng xuất?')) {
                // Simulate logout
                alert('Đăng xuất thành công!');
                // Redirect to login page
                // window.location.href = 'login.html';
            }
        }

        // Mobile Sidebar Toggle
        function toggleMobileSidebar() {
            const sidebar = document.querySelector('.sidebar');
            sidebar.classList.toggle('open');
        }

        // Close sidebar when clicking outside on mobile
        document.addEventListener('click', function(event) {
            const sidebar = document.querySelector('.sidebar');
            const toggleBtn = document.querySelector('.btn-toggle-sidebar');
            
            if (window.innerWidth <= 768 && !sidebar.contains(event.target) && !toggleBtn.contains(event.target)) {
                sidebar.classList.remove('open');
            }
        });

        // Initialize page
        document.addEventListener('DOMContentLoaded', function() {
            // Set current date for activity log
            const now = new Date();
            const timeElements = document.querySelectorAll('.activity-time');
            
            // You can customize these dates as needed
            const dates = [
                'Hôm nay, 14:30',
                'Hôm qua, 09:15', 
                '3 ngày trước, 16:45',
                '1 tuần trước, 08:20',
                '2 tuần trước, 11:30'
            ];
            
            timeElements.forEach((element, index) => {
                if (dates[index]) {
                    element.textContent = dates[index];
                }
            });
        });