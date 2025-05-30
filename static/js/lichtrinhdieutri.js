function filterSchedule(type) {
    // Xóa class active của tất cả tab
    document.querySelectorAll('.tab-button').forEach(btn => {
        btn.classList.remove('active');
    });

    // Đặt lại tab đang chọn
    const button = document.querySelector(`.tab-button[onclick*="${type}"]`);
    if (button) button.classList.add('active');

    // Hiển thị/thay đổi các schedule-card
    document.querySelectorAll('.schedule-card').forEach(card => {
        const cardType = card.getAttribute('data-type');
        if (type === 'all' || cardType === type) {
            card.style.display = 'block';
        } else {
            card.style.display = 'none';
        }
    });
}
function toggleView(view) {
    // Bỏ class active trên tất cả toggle-btn
    document.querySelectorAll('.toggle-btn').forEach(btn => btn.classList.remove('active'));

    // Thêm class active cho nút đang bấm
    const activeBtn = document.querySelector(`.toggle-btn[onclick*="${view}"]`);
    if (activeBtn) activeBtn.classList.add('active');

    // Hiển thị/Ẩn các phần tử
    const listView = document.getElementById('list-view');
    const calendarView = document.querySelector('.calendar-container');

    if (view === 'list') {
        listView.style.display = 'grid';
        calendarView.style.display = 'none';
    } else if (view === 'calendar') {
        listView.style.display = 'none';
        calendarView.style.display = 'block';
    }
}
// Dữ liệu mẫu - Lịch điều trị
const events = [
    { date: '2025-05-28', label: 'Tiêm thuốc' },
    { date: '2025-05-29', label: 'Xét nghiệm' },
    { date: '2025-05-30', label: 'Siêu âm' },
];

let currentMonth = new Date().getMonth();
let currentYear = new Date().getFullYear();

function renderCalendar(month, year) {
    const monthYear = document.getElementById('monthYear');
    const calendarGrid = document.getElementById('calendarGrid');
    calendarGrid.innerHTML = '';

    const monthNames = ['Tháng 1', 'Tháng 2', 'Tháng 3', 'Tháng 4', 'Tháng 5', 'Tháng 6', 'Tháng 7', 'Tháng 8', 'Tháng 9', 'Tháng 10', 'Tháng 11', 'Tháng 12'];
    monthYear.textContent = `${monthNames[month]} ${year}`;

    const firstDay = new Date(year, month, 1).getDay(); // Chủ nhật = 0
    const daysInMonth = new Date(year, month + 1, 0).getDate();

    // Đệm ô trống
    for (let i = 0; i < (firstDay === 0 ? 6 : firstDay - 1); i++) {
        const emptyCell = document.createElement('div');
        calendarGrid.appendChild(emptyCell);
    }

    // Tạo ô ngày
    for (let day = 1; day <= daysInMonth; day++) {
        const dateStr = `${year}-${String(month + 1).padStart(2, '0')}-${String(day).padStart(2, '0')}`;
        const dayDiv = document.createElement('div');
        dayDiv.classList.add('calendar-day');
        dayDiv.innerHTML = `<span>${day}</span>`;

        const today = new Date();
        if (day === today.getDate() && month === today.getMonth() && year === today.getFullYear()) {
            dayDiv.classList.add('today');
        }

        const event = events.find(e => e.date === dateStr);
        if (event) {
            dayDiv.classList.add('event');
            const label = document.createElement('div');
            label.classList.add('event-label');
            label.textContent = event.label;
            dayDiv.appendChild(label);
        }

        calendarGrid.appendChild(dayDiv);
    }
}

function changeMonth(direction) {
    currentMonth += direction;
    if (currentMonth < 0) {
        currentMonth = 11;
        currentYear--;
    } else if (currentMonth > 11) {
        currentMonth = 0;
        currentYear++;
    }
    renderCalendar(currentMonth, currentYear);
}

function toggleView(view) {
    document.querySelectorAll('.toggle-btn').forEach(btn => btn.classList.remove('active'));
    const activeBtn = document.querySelector(`.toggle-btn[onclick*="${view}"]`);
    if (activeBtn) activeBtn.classList.add('active');

    const listView = document.getElementById('list-view');
    const calendarView = document.querySelector('.calendar-container');

    if (view === 'list') {
        listView.style.display = 'grid';
        calendarView.style.display = 'none';
    } else if (view === 'calendar') {
        listView.style.display = 'none';
        calendarView.style.display = 'block';
    }
}

// Khởi tạo
document.addEventListener('DOMContentLoaded', () => {
    renderCalendar(currentMonth, currentYear);
});
    function toggleSidebar() {
        const sidebar = document.querySelector('.sidebar');
        const mainContent = document.querySelector('.main-content');

        if (sidebar.classList.contains('hidden')) {
            sidebar.classList.remove('hidden');
            mainContent.classList.remove('full');
        } else {
            sidebar.classList.add('hidden');
            mainContent.classList.add('full');
        }
    }