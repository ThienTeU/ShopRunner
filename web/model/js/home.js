document.addEventListener('DOMContentLoaded', function () {
    // Lazy loading initialization
    var lazyLoadImages = document.querySelectorAll('img.lazyload');
    lazyLoadImages.forEach(function (img) {
        img.setAttribute('data-src', img.getAttribute('src'));
        img.setAttribute('src', 'data:image/gif;base64,R0lGODlhAQABAIAAAP///wAAACH5BAEAAAAALAAAAAABAAEAAAICRAEAOw==');
    });

    // Newsletter Form Handler
    document.getElementById('newsletterForm')?.addEventListener('submit', function (e) {
        e.preventDefault();
        const email = this.querySelector('input[type="email"]').value;
        if (email) {
            alert('Cảm ơn bạn đã đăng ký! Chúng tôi sẽ gửi voucher qua email của bạn.');
            this.reset();
        }
    });

    // Back to Top Button
    const backToTop = document.createElement('a');
    backToTop.href = '#';
    backToTop.className = 'back-to-top';
    backToTop.innerHTML = '<i class="fas fa-arrow-up"></i>';
    document.body.appendChild(backToTop);

    window.addEventListener('scroll', () => {
        if (window.pageYOffset > 300) {
            backToTop.classList.add('visible');
        } else {
            backToTop.classList.remove('visible');
        }
    });

    backToTop.addEventListener('click', (e) => {
        e.preventDefault();
        window.scrollTo({top: 0, behavior: 'smooth'});
    });
});

// Price Format Helper
function formatPrice(price) {
    return new Intl.NumberFormat('vi-VN', {
        style: 'currency',
        currency: 'VND'
    }).format(price);
}

// Search Validation
function validateSearch() {
    const searchInput = document.querySelector('input[name="query"]');
    if (searchInput.value.trim().length < 2) {
        alert('Vui lòng nhập ít nhất 2 ký tự để tìm kiếm');
        return false;
    }
    return true;
}
