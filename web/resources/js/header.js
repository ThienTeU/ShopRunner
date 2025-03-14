document.addEventListener("DOMContentLoaded", function () {
    // Desktop hover handling
    if (window.innerWidth > 991.98) {
        document.querySelectorAll('.dropdown-submenu').forEach(function (element) {
            element.addEventListener('mouseover', function () {
                let subMenu = this.querySelector('.dropdown-menu');
                if (subMenu) {
                    let rect = subMenu.getBoundingClientRect();
                    if (rect.right > window.innerWidth) {
                        subMenu.style.left = 'auto';
                        subMenu.style.right = '100%';
                    }
                }
            });
        });
    }

    // Mobile click handling
    document.querySelectorAll('.dropdown-submenu > a').forEach(function (element) {
        element.addEventListener('click', function (e) {
            if (window.innerWidth <= 991.98) {
                e.preventDefault();
                e.stopPropagation();
                let subMenu = this.nextElementSibling;
                if (subMenu) {
                    if (subMenu.style.display === 'block') {
                        subMenu.style.display = 'none';
                    } else {
                        // Hide all other submenus at the same level
                        let siblings = this.parentElement.parentElement.querySelectorAll('.dropdown-menu');
                        siblings.forEach(function (menu) {
                            if (menu !== subMenu) {
                                menu.style.display = 'none';
                            }
                        });
                        subMenu.style.display = 'block';
                    }
                }
            }
        });
    });

    // Close dropdowns when clicking outside
    document.addEventListener('click', function (e) {
        if (!e.target.closest('.dropdown-submenu')) {
            document.querySelectorAll('.dropdown-submenu .dropdown-menu').forEach(function (menu) {
                menu.style.display = '';
            });
        }
    });

    // Initialize Bootstrap tooltips
    var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
    var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
        return new bootstrap.Tooltip(tooltipTriggerEl);
    });

    // Initialize Bootstrap popovers
    var popoverTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="popover"]'));
    var popoverList = popoverTriggerList.map(function (popoverTriggerEl) {
        return new bootstrap.Popover(popoverTriggerEl);
    });
});
