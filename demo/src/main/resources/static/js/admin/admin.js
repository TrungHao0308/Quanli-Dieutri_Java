// Sample data
        let users = [
            {
                id: 1,
                username: 'dr.nguyen',
                fullName: 'BS. Nguyễn Văn An',
                email: 'nguyen.van.an@hospital.com',
                role: 'doctor',
                status: 'active',
                permissions: ['view_patients', 'edit_medical_records', 'prescribe_medicine'],
                createdAt: '2024-01-15',
                lastLogin: '2024-06-20'
            },
            {
                id: 2,
                username: 'manager.tran',
                fullName: 'Trần Thị Bình',
                email: 'tran.thi.binh@hospital.com',
                role: 'manager',
                status: 'active',
                permissions: ['view_reports', 'manage_schedules', 'view_statistics'],
                createdAt: '2024-02-01',
                lastLogin: '2024-06-19'
            },
            {
                id: 3,
                username: 'dr.le',
                fullName: 'BS. Lê Văn Cường',
                email: 'le.van.cuong@hospital.com',
                role: 'doctor',
                status: 'inactive',
                permissions: ['view_patients', 'edit_medical_records'],
                createdAt: '2024-03-10',
                lastLogin: '2024-06-15'
            }
        ];

        const permissions = {
            doctor: [
                { id: 'view_patients', name: 'Xem thông tin bệnh nhân' },
                { id: 'edit_medical_records', name: 'Chỉnh sửa hồ sơ bệnh án' },
                { id: 'prescribe_medicine', name: 'Kê đơn thuốc' },
                { id: 'view_lab_results', name: 'Xem kết quả xét nghiệm' },
                { id: 'schedule_appointments', name: 'Đặt lịch khám' }
            ],
            manager: [
                { id: 'view_reports', name: 'Xem báo cáo' },
                { id: 'manage_schedules', name: 'Quản lý lịch trình' },
                { id: 'view_statistics', name: 'Xem thống kê' },
                { id: 'manage_staff', name: 'Quản lý nhân viên' },
                { id: 'financial_reports', name: 'Báo cáo tài chính' },
                { id: 'system_settings', name: 'Cài đặt hệ thống' }
            ]
        };

        let currentEditId = null;
        let filteredUsers = [...users];

        // Initialize
        document.addEventListener('DOMContentLoaded', function() {
            renderUsers();
            updateStats();
            setupEventListeners();
        });

        function setupEventListeners() {
            document.getElementById('searchInput').addEventListener('input', filterUsers);
            document.getElementById('roleFilter').addEventListener('change', filterUsers);
            document.getElementById('statusFilter').addEventListener('change', filterUsers);
            document.getElementById('userForm').addEventListener('submit', handleFormSubmit);
        }

        function renderUsers() {
            const tbody = document.getElementById('usersTableBody');
            tbody.innerHTML = '';

            filteredUsers.forEach(user => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${user.id}</td>
                    <td><strong>${user.username}</strong></td>
                    <td>${user.fullName}</td>
                    <td>${user.email}</td>
                    <td><span class="role ${user.role}">${user.role === 'doctor' ? 'Bác sĩ' : 'Quản lý'}</span></td>
                    <td><span class="status ${user.status}">${user.status === 'active' ? 'Hoạt động' : 'Không hoạt động'}</span></td>
                    <td>${formatDate(user.createdAt)}</td>
                    <td>${formatDate(user.lastLogin)}</td>
                    <td class="actions">
                        <button class="btn btn-sm btn-warning" onclick="editUser(${user.id})" title="Chỉnh sửa">
                            ✏️
                        </button>
                        <button class="btn btn-sm btn-success" onclick="viewPermissions(${user.id})" title="Xem quyền">
                            🔒
                        </button>
                        <button class="btn btn-sm btn-danger" onclick="deleteUser(${user.id})" title="Xóa">
                            🗑️
                        </button>
                    </td>
                `;
                tbody.appendChild(row);
            });
        }

        function updateStats() {
            const totalUsers = users.length;
            const totalDoctors = users.filter(u => u.role === 'doctor').length;
            const totalManagers = users.filter(u => u.role === 'manager').length;
            const activeUsers = users.filter(u => u.status === 'active').length;

            document.getElementById('totalUsers').textContent = totalUsers;
            document.getElementById('totalDoctors').textContent = totalDoctors;
            document.getElementById('totalManagers').textContent = totalManagers;
            document.getElementById('activeUsers').textContent = activeUsers;
        }

        function filterUsers() {
            const searchTerm = document.getElementById('searchInput').value.toLowerCase();
            const roleFilter = document.getElementById('roleFilter').value;
            const statusFilter = document.getElementById('statusFilter').value;

            filteredUsers = users.filter(user => {
                const matchesSearch = user.fullName.toLowerCase().includes(searchTerm) ||
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
            const modal = document.getElementById('userModal');
            const form = document.getElementById('userForm');
            const title = document.getElementById('modalTitle');

            if (editId) {
                const user = users.find(u => u.id === editId);
                title.textContent = 'Chỉnh sửa tài khoản';
                
                document.getElementById('username').value = user.username;
                document.getElementById('fullName').value = user.fullName;
                document.getElementById('email').value = user.email;
                document.getElementById('password').required = false;
                document.getElementById('role').value = user.role;
            } else {
                title.textContent = 'Thêm tài khoản mới';
                form.reset();
                document.getElementById('password').required = true;
            }

            modal.classList.add('show');
        }

        function closeModal() {
            document.getElementById('userModal').classList.remove('show');
            currentEditId = null;
        }

        function updatePermissions() {
            // Removed - no longer needed since we're not showing permissions in form
        }

        function handleFormSubmit(e) {
            e.preventDefault();
            
            const formData = new FormData(e.target);
            
            const userData = {
                username: formData.get('username'),
                fullName: formData.get('fullName'),
                email: formData.get('email'),
                role: formData.get('role'),
                status: 'active', // Mặc định là active
                permissions: permissions[formData.get('role')] ? permissions[formData.get('role')].map(p => p.id) : []
            };

            if (currentEditId) {
                // Update existing user
                const userIndex = users.findIndex(u => u.id === currentEditId);
                users[userIndex] = {
                    ...users[userIndex],
                    ...userData
                };
                alert('Cập nhật tài khoản thành công!');
            } else {
                // Add new user
                const newUser = {
                    id: Math.max(...users.map(u => u.id)) + 1,
                    ...userData,
                    createdAt: new Date().toISOString().split('T')[0],
                    lastLogin: 'Chưa đăng nhập'
                };
                users.push(newUser);
                alert('Thêm tài khoản thành công!');
            }

            closeModal();
            filterUsers();
            updateStats();
        }

        function editUser(id) {
            openModal(id);
        }

        function deleteUser(id) {
            const user = users.find(u => u.id === id);
            if (confirm(`Bạn có chắc chắn muốn xóa tài khoản "${user.fullName}"?`)) {
                users = users.filter(u => u.id !== id);
                filterUsers();
                updateStats();
                alert('Xóa tài khoản thành công!');
            }
        }

        function viewPermissions(id) {
            const user = users.find(u => u.id === id);
            const rolePermissions = permissions[user.role] || [];
            
            const permissionNames = user.permissions.map(permId => {
                const perm = rolePermissions.find(p => p.id === permId);
                return perm ? perm.name : permId;
            });

            alert(`Quyền của ${user.fullName}:\n\n${permissionNames.join('\n')}`);
        }

        function formatDate(dateString) {
            if (dateString === 'Chưa đăng nhập') return dateString;
            const date = new Date(dateString);
            return date.toLocaleDateString('vi-VN');
        }

        // Close modal when clicking outside
        window.addEventListener('click', function(e) {
            const modal = document.getElementById('userModal');
            if (e.target === modal) {
                closeModal();
            }
        });
