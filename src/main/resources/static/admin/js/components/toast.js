// Toast Component JS

const Toast = {
    show: function(message, type = 'success', duration = 3000) {
        const toastEl = document.getElementById('globalToast');
        const toastBody = document.getElementById('globalToastMessage');
        
        if (!toastEl || !toastBody) {
            console.error('Toast elements not found');
            return;
        }

        // Set message
        toastBody.textContent = message;

        // Set color based on type
        toastEl.className = 'toast align-items-center border-0';
        
        switch(type) {
            case 'success':
                toastEl.classList.add('text-bg-success');
                break;
            case 'error':
            case 'danger':
                toastEl.classList.add('text-bg-danger');
                break;
            case 'warning':
                toastEl.classList.add('text-bg-warning');
                break;
            case 'info':
                toastEl.classList.add('text-bg-info');
                break;
            default:
                toastEl.classList.add('text-bg-primary');
        }

        // Show toast
        const toast = new bootstrap.Toast(toastEl, {
            delay: duration
        });
        toast.show();
    },

    success: function(message, duration = 3000) {
        this.show(message, 'success', duration);
    },

    error: function(message, duration = 3000) {
        this.show(message, 'error', duration);
    },

    warning: function(message, duration = 3000) {
        this.show(message, 'warning', duration);
    },

    info: function(message, duration = 3000) {
        this.show(message, 'info', duration);
    }
};

// Make Toast globally available
window.Toast = Toast;
