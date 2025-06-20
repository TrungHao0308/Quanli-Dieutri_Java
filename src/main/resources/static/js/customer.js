function toggleSidebar() {
    const sidebar = document.querySelector('.sidebar');
    const main = document.querySelector('.main-content');
    sidebar.classList.toggle('hidden');
    main.classList.toggle('full');
}

function logout() {
    // Redirect hoặc xử lý đăng xuất tại đây
    alert('Bạn đã đăng xuất!');
    window.location.href = 'login.html'; // Chuyển đến trang đăng nhập
}

function showPage(pageId) {
    document.querySelectorAll('.content-page').forEach(page => {
        page.classList.remove('active');
    });
    document.getElementById(pageId).classList.add('active');
}
