// Sidebar Component JS

document.addEventListener("DOMContentLoaded", () => {
    const sidebar = document.getElementById("sidebar");
    const toggleBtn = document.getElementById("toggleSidebar");
    const toggleBtnHeader = document.getElementById("toggleSidebarHeader");
    const hiddenToggle = document.getElementById("hiddenToggle");
    const mainContent = document.querySelector(".main-content");

    // Check localStorage for saved state
    const isCollapsedStored = localStorage.getItem('adminSidebarCollapsed') === 'true';
    
    if (isCollapsedStored) {
        sidebar.classList.add("collapsed");
        mainContent.classList.add("expanded");
        if (hiddenToggle) hiddenToggle.classList.remove("d-none");
    }

    function toggleSidebar() {
        const isCollapsed = sidebar.classList.toggle("collapsed");
        mainContent.classList.toggle("expanded");

        if (hiddenToggle) {
            if (isCollapsed) {
                hiddenToggle.classList.remove("d-none");
            } else {
                hiddenToggle.classList.add("d-none");
            }
        }

        // Save state to localStorage
        localStorage.setItem('adminSidebarCollapsed', isCollapsed);
    }

    if (toggleBtn) {
        toggleBtn.addEventListener("click", toggleSidebar);
    }

    if (toggleBtnHeader) {
        toggleBtnHeader.addEventListener("click", toggleSidebar);
    }

    // Mobile: Close sidebar when clicking outside
    document.addEventListener("click", (e) => {
        if (window.innerWidth <= 768) {
            if (!sidebar.contains(e.target) && !e.target.closest('.sidebar-toggle-mobile')) {
                sidebar.classList.remove("active");
            }
        }
    });
});
