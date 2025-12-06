/**
 * Layout JS - Admin Panel
 * Note: Axios config & interceptors are handled by app.config.js
 */

document.addEventListener("DOMContentLoaded", function() {
    console.log('[Admin Layout] Initialized');
    
    // Verify App.config is loaded
    if (typeof App === 'undefined') {
        console.error('[Admin Layout] App.config.js not loaded! Please load it before layout.js');
        return;
    }
    
    // Check authentication status
    if (!App.isAuthenticated()) {
        console.warn('[Admin Layout] User not authenticated');
    } else {
        const userInfo = App.getUserInfo();
        console.log('[Admin Layout] User authenticated:', userInfo?.username);
    }
    
    // ==================== GLOBAL HELPERS ====================
    
    /**
     * Confirm delete with modal
     */
    window.confirmDelete = function(message, onConfirm) {
        Modal.confirm(
            'Xác nhận xóa', 
            message || 'Bạn có chắc chắn muốn xóa? Hành động này không thể hoàn tác.',
            onConfirm
        );
    };
    
    /**
     * Format currency (VND)
     */
    window.formatCurrency = function(amount) {
        if (amount === null || amount === undefined) return '0 ₫';
        return new Intl.NumberFormat('vi-VN', {
            style: 'currency',
            currency: 'VND'
        }).format(amount);
    };
    
    /**
     * Format date (dd/mm/yyyy)
     */
    window.formatDate = function(date) {
        if (!date) return '';
        return new Date(date).toLocaleDateString('vi-VN');
    };
    
    /**
     * Format datetime (dd/mm/yyyy hh:mm)
     */
    window.formatDateTime = function(date) {
        if (!date) return '';
        return new Date(date).toLocaleString('vi-VN');
    };
    
    /**
     * Format number with thousands separator
     */
    window.formatNumber = function(number) {
        if (number === null || number === undefined) return '0';
        return new Intl.NumberFormat('vi-VN').format(number);
    };
    
    /**
     * Truncate text with ellipsis
     */
    window.truncateText = function(text, maxLength = 50) {
        if (!text) return '';
        return text.length > maxLength ? text.substring(0, maxLength) + '...' : text;
    };
    
    /**
     * Handle logout
     */
    window.handleLogout = function() {
        Modal.confirm(
            'Xác nhận đăng xuất',
            'Bạn có chắc chắn muốn đăng xuất?',
            function() {
                App.clearAuth();
                window.location.href = '/admin/login?success=' + encodeURIComponent('Đăng xuất thành công');
            }
        );
    };
    
    /**
     * Copy to clipboard
     */
    window.copyToClipboard = function(text) {
        navigator.clipboard.writeText(text).then(() => {
            Toast.success('Đã sao chép vào clipboard');
        }).catch(() => {
            Toast.error('Không thể sao chép');
        });
    };
    
    console.log('[Admin Layout] Global helpers registered');
});
