/**
 * Dashboard Page JS
 * Uses App.api from app.config.js
 */

document.addEventListener('DOMContentLoaded', function() {
    console.log('[Dashboard] Page loaded');
    
    // Verify App.config is loaded
    if (typeof App === 'undefined') {
        console.error('[Dashboard] App.config.js not loaded!');
        return;
    }

    // Update current time
    updateCurrentTime();
    setInterval(updateCurrentTime, 60000); // Update every minute

    // Load dashboard data
    loadDashboardSummary();
    loadRevenueChart();
    loadTopProductsChart();
    loadRecentOrders();
    
    // Setup event listeners
    setupEventListeners();
});

/**
 * Update current time display
 */
function updateCurrentTime() {
    const now = new Date();
    const timeStr = now.toLocaleString('vi-VN', {
        hour: '2-digit',
        minute: '2-digit',
        day: '2-digit',
        month: '2-digit',
        year: 'numeric'
    });
    const timeEl = document.getElementById('currentTime');
    if (timeEl) timeEl.textContent = timeStr;
}

/**
 * Setup event listeners
 */
function setupEventListeners() {
    // Year selector for revenue chart
    const yearSelect = document.getElementById('revenueYearSelect');
    if (yearSelect) {
        yearSelect.addEventListener('change', function() {
            const year = this.value;
            loadRevenueChart(year);
        });
    }
}

/**
 * Load dashboard summary statistics
 */
function loadDashboardSummary() {
    // Show loading state
    showLoadingState();
    
    // ===== REAL API CALL =====
    App.api.get(App.API.DASHBOARD.SUMMARY())
        .then(response => {
            const data = response.data;
            updateSummaryCards(data);
        })
        .catch(error => {
            console.error('[Dashboard] Error loading summary:', error);
            // Fallback to mock data if API fails
            useMockSummaryData();
        });
}

/**
 * Show loading state for summary cards
 */
function showLoadingState() {
    const loadingHTML = '<span class="loading"></span>';
    document.getElementById('totalOrders').innerHTML = loadingHTML;
    document.getElementById('totalRevenue').innerHTML = loadingHTML;
    document.getElementById('totalProducts').innerHTML = loadingHTML;
    document.getElementById('totalUsers').innerHTML = loadingHTML;
}

/**
 * Update summary cards with real data
 */
function updateSummaryCards(data) {
    document.getElementById('totalOrders').innerHTML = 
        `<strong>${formatNumber(data.totalOrders || 0)}</strong>`;
    
    document.getElementById('totalRevenue').innerHTML = 
        `<strong>${formatCurrency(data.totalRevenue || 0)}</strong>`;
    
    document.getElementById('totalProducts').innerHTML = 
        `<strong>${formatNumber(data.totalProducts || 0)}</strong>`;
    
    document.getElementById('totalUsers').innerHTML = 
        `<strong>${formatNumber(data.totalUsers || 0)}</strong>`;
    
    document.getElementById('lowStockProducts').textContent = 
        formatNumber(data.lowStockProducts || 0);
    
    document.getElementById('newUsersThisMonth').textContent = 
        formatNumber(data.newUsersThisMonth || 0);
}

/**
 * Fallback to mock data if API not available
 */
function useMockSummaryData() {
    console.warn('[Dashboard] Using mock data');
    setTimeout(() => {
        updateSummaryCards({
            totalOrders: 1234,
            totalRevenue: 125500000,
            totalProducts: 156,
            totalUsers: 5678,
            lowStockProducts: 12,
            newUsersThisMonth: 89
        });
    }, 500);
}

function loadRevenueChart() {
    const options = {
        series: [{
            name: 'Doanh thu',
            data: [30, 40, 35, 50, 49, 60, 70, 91, 125, 110, 95, 85]
        }],
        chart: {
            type: 'area',
            height: 350,
            toolbar: {
                show: false
            }
        },
        colors: ['#2563eb'],
        dataLabels: {
            enabled: false
        },
        stroke: {
            curve: 'smooth',
            width: 3
        },
        fill: {
            type: 'gradient',
            gradient: {
                shadeIntensity: 1,
                opacityFrom: 0.7,
                opacityTo: 0.2,
                stops: [0, 90, 100]
            }
        },
        xaxis: {
            categories: ['T1', 'T2', 'T3', 'T4', 'T5', 'T6', 'T7', 'T8', 'T9', 'T10', 'T11', 'T12']
        },
        yaxis: {
            title: {
                text: 'Triệu VNĐ'
            }
        },
        tooltip: {
            y: {
                formatter: function(val) {
                    return val.toFixed(1) + ' triệu VNĐ';
                }
            }
        }
    };

    const chart = new ApexCharts(document.querySelector("#revenueChart"), options);
    chart.render();
}

function loadTopProductsChart() {
    const options = {
        series: [35, 25, 20, 12, 8],
        chart: {
            type: 'donut',
            height: 300
        },
        labels: ['Giày thể thao', 'Giày da', 'Giày sandal', 'Giày boot', 'Khác'],
        colors: ['#2563eb', '#10b981', '#f59e0b', '#ef4444', '#64748b'],
        legend: {
            position: 'bottom'
        },
        dataLabels: {
            enabled: true,
            formatter: function(val) {
                return val.toFixed(1) + '%';
            }
        },
        tooltip: {
            y: {
                formatter: function(val) {
                    return val + '%';
                }
            }
        }
    };

    const chart = new ApexCharts(document.querySelector("#topProductsChart"), options);
    chart.render();
}

/**
 * Load recent orders
 */
function loadRecentOrders() {
    const tbody = document.getElementById('recentOrdersTable');
    
    // ===== REAL API CALL =====
    App.api.get(App.API.DASHBOARD.RECENT_ORDERS(5))
        .then(response => {
            const orders = response.data;
            renderRecentOrders(orders);
        })
        .catch(error => {
            console.error('[Dashboard] Error loading recent orders:', error);
            // Fallback to mock data if API fails
            useMockRecentOrders();
        });
}

/**
 * Render recent orders table
 */
function renderRecentOrders(orders) {
    const tbody = document.getElementById('recentOrdersTable');
    
    if (!orders || orders.length === 0) {
        tbody.innerHTML = '<tr><td colspan="6" class="text-center text-muted">Chưa có đơn hàng nào</td></tr>';
        return;
    }
    
    tbody.innerHTML = orders.map(order => `
        <tr>
            <td><strong>${order.id || order.orderId}</strong></td>
            <td>${order.customerName || order.customer}</td>
            <td>${formatDate(order.createdAt || order.date)}</td>
            <td><strong>${formatCurrency(order.totalAmount || order.total)}</strong></td>
            <td>${getStatusBadge(order.status)}</td>
            <td>
                <button class="btn btn-sm btn-info" onclick="viewOrder('${order.id || order.orderId}')">
                    <i class="bi bi-eye"></i>
                </button>
            </td>
        </tr>
    `).join('');
}

/**
 * Fallback mock data for recent orders
 */
function useMockRecentOrders() {
    console.warn('[Dashboard] Using mock orders data');
    setTimeout(() => {
        const mockOrders = [
            { id: 'ORD001', customer: 'Nguyễn Văn A', date: '2025-12-03', total: 1500000, status: 'pending' },
            { id: 'ORD002', customer: 'Trần Thị B', date: '2025-12-03', total: 2300000, status: 'processing' },
            { id: 'ORD003', customer: 'Lê Văn C', date: '2025-12-02', total: 890000, status: 'completed' },
            { id: 'ORD004', customer: 'Phạm Thị D', date: '2025-12-02', total: 3200000, status: 'completed' },
            { id: 'ORD005', customer: 'Hoàng Văn E', date: '2025-12-01', total: 1200000, status: 'cancelled' }
        ];
        renderRecentOrders(mockOrders);
    }, 600);
}

function getStatusBadge(status) {
    const badges = {
        'pending': '<span class="badge bg-warning">Chờ xử lý</span>',
        'processing': '<span class="badge bg-info">Đang xử lý</span>',
        'completed': '<span class="badge bg-success">Hoàn thành</span>',
        'cancelled': '<span class="badge bg-danger">Đã hủy</span>'
    };
    return badges[status] || '<span class="badge bg-secondary">Không xác định</span>';
}

function viewOrder(orderId) {
    window.location.href = `/admin/orders/${orderId}`;
}
