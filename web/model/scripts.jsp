<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
<!-- Lazy Loading -->
<script src="https://web.nvnstatic.net/js/lazyLoad/lazysizes.min.js" async></script>

<script>
  document.addEventListener("DOMContentLoaded", function() {
    // Desktop hover handling
    if (window.innerWidth > 991.98) {
      document.querySelectorAll('.dropdown-submenu').forEach(function(element) {
        element.addEventListener('mouseover', function() {
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
    document.querySelectorAll('.dropdown-submenu > a').forEach(function(element) {
      element.addEventListener('click', function(e) {
        if (window.innerWidth <= 991.98) {
          e.preventDefault();
          e.stopPropagation();
          let subMenu = this.nextElementSibling;
          if (subMenu) {
            if (subMenu.style.display === 'block') {
              subMenu.style.display = 'none';
            } else {
              let siblings = this.parentElement.parentElement.querySelectorAll('.dropdown-menu');
              siblings.forEach(function(menu) {
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

    // Smooth scroll for anchor links
    document.querySelectorAll('a[href^="#"]').forEach(anchor => {
      anchor.addEventListener('click', function (e) {
        e.preventDefault();
        document.querySelector(this.getAttribute('href')).scrollIntoView({
          behavior: 'smooth'
        });
      });
    });
  });
  
  
  
</script>