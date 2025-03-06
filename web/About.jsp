<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Giới thiệu - Runner Shop</title>
    
    <!-- SEO Meta Tags -->
    <meta name="description" content="Runner Shop - Cửa hàng thời trang thể thao chính hãng hàng đầu Việt Nam">
    <meta name="keywords" content="thời trang thể thao, giày thể thao, quần áo nam, quần áo nữ">
    
    <!-- CSS Libraries -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="stylesheet" href="https://unpkg.com/aos@2.3.1/dist/aos.css">
    
    <style>
        :root {
            --primary-color: #ff6600;
            --secondary-color: #333333;
            --light-gray: #f8f9fa;
            --dark-gray: #343a40;
            --border-radius: 8px;
            --box-shadow: 0 2px 15px rgba(0,0,0,0.1);
            --transition: all 0.3s ease;
        }

        /* Hero Section */
        .hero-section {
            position: relative;
            background: linear-gradient(rgba(0,0,0,0.7), rgba(0,0,0,0.7)),
                        url('resources/img/about-hero.jpg') center/cover no-repeat;
            height: 60vh;
            display: flex;
            align-items: center;
            justify-content: center;
            color: white;
            text-align: center;
        }

        .hero-content {
            max-width: 800px;
            padding: 0 20px;
        }

        .hero-title {
            font-size: 3.5rem;
            font-weight: 700;
            margin-bottom: 1rem;
        }

        .hero-subtitle {
            font-size: 1.2rem;
            opacity: 0.9;
        }

        /* Story Section */
        .story-section {
            padding: 80px 0;
            background-color: var(--light-gray);
        }

        .story-image {
            border-radius: var(--border-radius);
            box-shadow: var(--box-shadow);
            overflow: hidden;
        }

        .story-image img {
            width: 100%;
            height: 100%;
            object-fit: cover;
            transition: var(--transition);
        }

        .story-image:hover img {
            transform: scale(1.05);
        }

        .story-content {
            padding: 30px;
        }

        .section-title {
            color: var(--primary-color);
            font-size: 2.5rem;
            margin-bottom: 1.5rem;
            font-weight: 700;
        }

        /* Values Section */
        .values-section {
            padding: 80px 0;
            background-color: white;
        }

        .value-card {
            text-align: center;
            padding: 30px;
            border-radius: var(--border-radius);
            background: white;
            box-shadow: var(--box-shadow);
            transition: var(--transition);
        }

        .value-card:hover {
            transform: translateY(-10px);
        }

        .value-icon {
            width: 80px;
            height: 80px;
            line-height: 80px;
            border-radius: 50%;
            background: var(--primary-color);
            color: white;
            font-size: 2rem;
            margin: 0 auto 20px;
        }

        /* Team Section */
        .team-section {
            padding: 80px 0;
            background-color: var(--light-gray);
        }

        .team-card {
            text-align: center;
            background: white;
            border-radius: var(--border-radius);
            overflow: hidden;
            box-shadow: var(--box-shadow);
            transition: var(--transition);
        }

        .team-card:hover {
            transform: translateY(-10px);
        }

        .team-image {
            position: relative;
            overflow: hidden;
        }

        .team-image img {
            width: 100%;
            height: 300px;
            object-fit: cover;
        }

        .team-social {
            position: absolute;
            bottom: -50px;
            left: 0;
            right: 0;
            background: rgba(255,102,0,0.9);
            padding: 15px;
            transition: var(--transition);
        }

        .team-card:hover .team-social {
            bottom: 0;
        }

        .team-social a {
            color: white;
            margin: 0 10px;
            font-size: 1.2rem;
            transition: var(--transition);
        }

        .team-social a:hover {
            transform: translateY(-3px);
        }

        .team-info {
            padding: 20px;
        }

        .team-name {
            font-size: 1.2rem;
            font-weight: 600;
            margin-bottom: 5px;
        }

        .team-position {
            color: var(--primary-color);
            font-style: italic;
        }

        /* Stats Section */
        .stats-section {
            padding: 60px 0;
            background: linear-gradient(rgba(255,102,0,0.9), rgba(255,102,0,0.9)),
                        url('resources/img/stats-bg.jpg') center/cover fixed;
            color: white;
        }

        .stat-item {
            text-align: center;
        }

        .stat-number {
            font-size: 3rem;
            font-weight: 700;
            margin-bottom: 10px;
        }

        .stat-label {
            font-size: 1.1rem;
            opacity: 0.9;
        }

        /* Contact Section */
        .contact-section {
            padding: 80px 0;
            background-color: white;
        }

        .contact-info {
            background: var(--light-gray);
            padding: 30px;
            border-radius: var(--border-radius);
        }

        .contact-item {
            display: flex;
            align-items: center;
            margin-bottom: 20px;
        }

        .contact-icon {
            width: 50px;
            height: 50px;
            line-height: 50px;
            text-align: center;
            background: var(--primary-color);
            color: white;
            border-radius: 50%;
            margin-right: 15px;
        }

        .map-container {
            height: 400px;
            border-radius: var(--border-radius);
            overflow: hidden;
        }

        /* Responsive Styles */
        @media (max-width: 768px) {
            .hero-title {
                font-size: 2.5rem;
            }

            .section-title {
                font-size: 2rem;
            }

            .stat-number {
                font-size: 2.5rem;
            }
        }

        @media (max-width: 576px) {
            .hero-title {
                font-size: 2rem;
            }

            .value-card, .team-card {
                margin-bottom: 30px;
            }
        }
    </style>
</head>

<body>
    <!-- Include Header -->
    <%@ include file="/model/header.jsp" %>

    <!-- Hero Section -->
    <section class="hero-section">
        <div class="hero-content" data-aos="fade-up">
            <h1 class="hero-title">Chào mừng đến với Runner Shop</h1>
            <p class="hero-subtitle">Nơi mang đến những sản phẩm thể thao chất lượng hàng đầu</p>
        </div>
    </section>

    <!-- Story Section -->
    <section class="story-section">
        <div class="container">
            <div class="row align-items-center">
                <div class="col-lg-6" data-aos="fade-right">
                    <div class="story-image">
                        <img src="resources/img/about-story.jpg" alt="Our Story">
                    </div>
                </div>
                <div class="col-lg-6" data-aos="fade-left">
                    <div class="story-content">
                        <h2 class="section-title">Câu chuyện của chúng tôi</h2>
                        <p>Runner Shop được thành lập vào năm 2020 với sứ mệnh mang đến những sản phẩm thể thao chất lượng cao cho người tiêu dùng Việt Nam.</p>
                        <p>Chúng tôi tin rằng mọi người đều xứng đáng được trải nghiệm những sản phẩm thể thao tốt nhất để nâng cao hiệu suất tập luyện và tận hưởng cuộc sống năng động.</p>
                        <p>Với đội ngũ nhân viên nhiệt huyết và giàu kinh nghiệm, Runner Shop cam kết mang đến dịch vụ tốt nhất và những sản phẩm chất lượng cao nhất cho khách hàng.</p>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <!-- Values Section -->
    <section class="values-section">
        <div class="container">
            <h2 class="section-title text-center mb-5" data-aos="fade-up">Giá trị cốt lõi</h2>
            <div class="row">
                <div class="col-lg-4" data-aos="fade-up" data-aos-delay="100">
                    <div class="value-card">
                        <div class="value-icon">
                            <i class="fas fa-medal"></i>
                        </div>
                        <h3>Chất lượng</h3>
                        <p>Cam kết mang đến những sản phẩm chất lượng cao nhất cho khách hàng</p>
                    </div>
                </div>
                <div class="col-lg-4" data-aos="fade-up" data-aos-delay="200">
                    <div class="value-card">
                        <div class="value-icon">
                            <i class="fas fa-heart"></i>
                        </div>
                        <h3>Tận tâm</h3>
                        <p>Luôn lắng nghe và phục vụ khách hàng với tinh thần tận tâm nhất</p>
                    </div>
                </div>
                <div class="col-lg-4" data-aos="fade-up" data-aos-delay="300">
                    <div class="value-card">
                        <div class="value-icon">
                            <i class="fas fa-shield-alt"></i>
                        </div>
                        <h3>Uy tín</h3>
                        <p>Xây dựng thương hiệu dựa trên sự tin tưởng của khách hàng</p>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <!-- Stats Section -->
    <section class="stats-section">
        <div class="container">
            <div class="row">
                <div class="col-lg-3 col-md-6" data-aos="fade-up" data-aos-delay="100">
                    <div class="stat-item">
                        <div class="stat-number" data-count="5000">0</div>
                        <div class="stat-label">Khách hàng hài lòng</div>
                    </div>
                </div>
                <div class="col-lg-3 col-md-6" data-aos="fade-up" data-aos-delay="200">
                    <div class="stat-item">
                        <div class="stat-number" data-count="500">0</div>
                        <div class="stat-label">Sản phẩm</div>
                    </div>
                </div>
                <div class="col-lg-3 col-md-6" data-aos="fade-up" data-aos-delay="300">
                    <div class="stat-item">
                        <div class="stat-number" data-count="50">0</div>
                        <div class="stat-label">Thương hiệu</div>
                    </div>
                </div>
                <div class="col-lg-3 col-md-6" data-aos="fade-up" data-aos-delay="400">
                    <div class="stat-item">
                        <div class="stat-number" data-count="3">0</div>
                        <div class="stat-label">Chi nhánh</div>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <!-- Team Section -->
    <section class="team-section">
        <div class="container">
            <h2 class="section-title text-center mb-5" data-aos="fade-up">Đội ngũ của chúng tôi</h2>
            <div class="row">
                <div class="col-lg-4" data-aos="fade-up" data-aos-delay="100">
                    <div class="team-card">
                        <div class="team-image">
                            <img src="resources/img/team-1.jpg" alt="Team Member">
                            <div class="team-social">
                                <a href="#"><i class="fab fa-facebook"></i></a>
                                <a href="#"><i class="fab fa-twitter"></i></a>
                                <a href="#"><i class="fab fa-linkedin"></i></a>
                            </div>
                        </div>
                        <div class="team-info">
                            <h3 class="team-name">Nguyễn Văn A</h3>
                            <p class="team-position">Giám đốc điều hành</p>
                        </div>
                    </div>
                </div>
                <div class="col-lg-4" data-aos="fade-up" data-aos-delay="200">
                    <div class="team-card">
                        <div class="team-image">
                            <img src="resources/img/team-2.jpg" alt="Team Member">
                            <div class="team-social">
                                <a href="#"><i class="fab fa-facebook"></i></a>
                                <a href="#"><i class="fab fa-twitter"></i></a>
                                <a href="#"><i class="fab fa-linkedin"></i></a>
                            </div>
                        </div>
                        <div class="team-info">
                            <h3 class="team-name">Trần Thị B</h3>
                            <p class="team-position">Giám đốc marketing</p>
                        </div>
                    </div>
                </div>
                <div class="col-lg-4" data-aos="fade-up" data-aos-delay="300">
                    <div class="team-card">
                        <div class="team-image">
                            <img src="resources/img/team-3.jpg" alt="Team Member">
                            <div class="team-social">
                                <a href="#"><i class="fab fa-facebook"></i></a>
                                <a href="#"><i class="fab fa-twitter"></i></a>
                                <a href="#"><i class="fab fa-linkedin"></i></a>
                            </div>
                        </div>
                        <div class="team-info">
                            <h3 class="team-name">Lê Văn C</h3>
                            <p class="team-position">Giám đốc sản phẩm</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <!-- Contact Section -->
    <section class="contact-section">
        <div class="container">
            <h2 class="section-title text-center mb-5" data-aos="fade-up">Liên hệ với chúng tôi</h2>
            <div class="row">
                <div class="col-lg-6" data-aos="fade-right">
                    <div class="contact-info">
                        <div class="contact-item">
                            <div class="contact-icon">
                                <i class="fas fa-map-marker-alt"></i>
                            </div>
                            <div>
                                <h4>Địa chỉ</h4>
                                <p>123 Đường ABC, Quận XYZ, TP.HCM</p>
                            </div>
                        </div>
                        <div class="contact-item">
                            <div class="contact-icon">
                                <i class="fas fa-phone"></i>
                            </div>
                            <div>
                                <h4>Điện thoại</h4>
                                <p>0123 456 789</p>
                            </div>
                        </div>
                        <div class="contact-item">
                            <div class="contact-icon">
                                <i class="fas fa-envelope"></i>
                            </div>
                            <div>
                                <h4>Email</h4>
                                <p>info@runnershop.com</p>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-lg-6" data-aos="fade-left">
                    <div class="map-container">
                        <iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3919.1252426979694!2d106.71237151533423!3d10.801617892304734!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x317528a459cb43ab%3A0x6c3d29d370b52a7e!2zVHLGsOG7nW5nIMSQ4bqhaSBo4buNYyBIdXRlY2g!5e0!3m2!1svi!2s!4v1647007543690!5m2!1svi!2s" 
                                width="100%" 
                                height="100%" 
                                style="border:0;" 
                                allowfullscreen="" 
                                loading="lazy">
                        </iframe>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <!-- Include Footer -->
    <%@ include file="/model/footer.jsp" %>

    <!-- Scripts -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://unpkg.com/aos@2.3.1/dist/aos.js"></script>
    <script>
        // Initialize AOS
        AOS.init({
            duration: 1000,
            once: true
        });

        // Counter Animation
        function animateCounter(element) {
            const target = parseInt(element.getAttribute('data-count'));
            let count = 0;
            const duration = 2000; // 2 seconds
            const increment = target / (duration / 16); // 60fps

            const timer = setInterval(() => {
                count += increment;
                if (count >= target) {
                    element.textContent = target;
                    clearInterval(timer);
                } else {
                    element.textContent = Math.floor(count);
                }
            }, 16);
        }

        // Start counter animation when elements are in view
        const observer = new IntersectionObserver((entries) => {
            entries.forEach(entry => {
                if (entry.isIntersecting) {
                    const counters = entry.target.querySelectorAll('.stat-number');
                    counters.forEach(counter => animateCounter(counter));
                    observer.unobserve(entry.target);
                }
            });
        });

        observer.observe(document.querySelector('.stats-section'));
    </script>
</body>
</html>
