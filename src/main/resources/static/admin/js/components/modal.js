// Modal Component JS

const Modal = {
    show: function(title, content, footer = null) {
        const modalEl = document.getElementById('globalModal');
        const modalTitle = document.getElementById('globalModalTitle');
        const modalBody = document.getElementById('globalModalBody');
        const modalFooter = document.getElementById('globalModalFooter');
        
        if (!modalEl) return;

        if (modalTitle) modalTitle.textContent = title;
        if (modalBody) modalBody.innerHTML = content;
        if (footer && modalFooter) modalFooter.innerHTML = footer;

        const modal = new bootstrap.Modal(modalEl);
        modal.show();
    },

    hide: function() {
        const modalEl = document.getElementById('globalModal');
        if (modalEl) {
            const modal = bootstrap.Modal.getInstance(modalEl);
            if (modal) modal.hide();
        }
    },

    confirm: function(title, message, onConfirm) {
        const footer = `
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
            <button type="button" class="btn btn-primary" id="modalConfirmBtn">Xác nhận</button>
        `;

        this.show(title, `<p>${message}</p>`, footer);

        setTimeout(() => {
            const confirmBtn = document.getElementById('modalConfirmBtn');
            if (confirmBtn) {
                confirmBtn.addEventListener('click', () => {
                    if (onConfirm) onConfirm();
                    this.hide();
                }, { once: true });
            }
        }, 100);
    }
};

window.Modal = Modal;
