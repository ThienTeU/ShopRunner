<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!-- Font Awesome -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
<!-- Bootstrap CSS -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">

<style>
        /* Newsletter Section */
    .newsletter-section {
        background-color: var(--primary-color);
        padding: 60px 0;
        color: white;
    }

    .newsletter-title {
        font-size: 2rem;
        margin-bottom: 1rem;
    }

    .newsletter-description {
        margin-bottom: 2rem;
        opacity: 0.9;
    }

    .newsletter-form .input-group {
        max-width: 500px;
        margin: 0 auto;
    }

    .newsletter-form .form-control {
        padding: 15px;
        border-radius: 30px 0 0 30px;
        border: none;
    }

    .newsletter-form .btn {
        padding: 15px 30px;
        border-radius: 0 30px 30px 0;
        font-weight: 600;
    }

    /* Main Footer */
    .footer-main {
        background-color: var(--dark-gray);
        padding: 80px 0 50px;
        color: #fff;
    }

    .footer-column {
        margin-bottom: 30px;
    }

    .footer-logo {
        max-width: 150px;
        height: auto;
    }

    .widget-title {
        color: #fff;
        font-size: 1.2rem;
        font-weight: 600;
        margin-bottom: 25px;
        position: relative;
    }

    .widget-title::after {
        content: '';
        position: absolute;
        left: 0;
        bottom: -10px;
        width: 30px;
        height: 2px;
        background-color: var(--primary-color);
    }

    .company-description {
        color: #ccc;
        margin: 20px 0;
        line-height: 1.6;
    }

    .social-links {
        display: flex;
        gap: 15px;
    }

    .social-link {
        width: 40px;
        height: 40px;
        display: flex;
        align-items: center;
        justify-content: center;
        border-radius: 50%;
        background-color: rgba(255,255,255,0.1);
        color: #fff;
        transition: var(--transition);
    }

    .social-link:hover {
        background-color: var(--primary-color);
        color: #fff;
        transform: translateY(-3px);
    }

    .footer-links {
        list-style: none;
        padding: 0;
        margin: 0;
    }

    .footer-links li {
        margin-bottom: 12px;
    }

    .footer-links a {
        color: #ccc;
        text-decoration: none;
        transition: var(--transition);
        display: inline-block;
    }

    .footer-links a:hover {
        color: var(--primary-color);
        transform: translateX(5px);
    }

    .contact-info .contact-item {
        display: flex;
        align-items: flex-start;
        margin-bottom: 20px;
        color: #ccc;
    }

    .contact-item i {
        margin-right: 15px;
        color: var(--primary-color);
    }

    .contact-item p {
        margin: 0;
    }

    .contact-item a {
        color: #ccc;
        text-decoration: none;
        transition: var(--transition);
    }

    .contact-item a:hover {
        color: var(--primary-color);
    }

    /* Footer Bottom */
    .footer-bottom {
        background-color: #1a1a1a;
        padding: 20px 0;
        color: #ccc;
    }

    .copyright {
        margin: 0;
    }

    .payment-methods {
        display: flex;
        align-items: center;
        justify-content: flex-end;
        gap: 15px;
    }

    .payment-methods span {
        margin-right: 15px;
    }

    .payment-methods img {
        height: 30px;
        width: auto;
    }

    /* Responsive Styles */
    @media (max-width: 768px) {
        .footer-column {
            text-align: center;
        }

        .widget-title::after {
            left: 50%;
            transform: translateX(-50%);
        }

        .social-links {
            justify-content: center;
        }

        .contact-info .contact-item {
            justify-content: center;
        }

        .payment-methods {
            justify-content: center;
            margin-top: 20px;
        }

        .copyright {
            text-align: center;
        }
    }

    @media (max-width: 576px) {
        .newsletter-title {
            font-size: 1.5rem;
        }

        .newsletter-form .input-group {
            flex-direction: column;
        }

        .newsletter-form .form-control,
        .newsletter-form .btn {
            border-radius: 30px;
            margin: 10px 0;
        }

        .payment-methods {
            flex-wrap: wrap;
        }
    }
  :root {
    --primary-color: #ff6600;
    --secondary-color: #333;
    --hover-bg: #f8f9fa;
    --shadow: 0 2px 10px rgba(0,0,0,0.1);
  }

  /* Navbar Styles */
  .navbar {
    padding: 12px 0;
    background: #fff;
    box-shadow: var(--shadow);
    position: sticky;
    top: 0;
    z-index: 1000;
  }

  .navbar-brand img {
    height: 45px;
    transition: transform 0.3s ease;
  }

  .navbar-brand:hover img {
    transform: scale(1.05);
  }

  .nav-link {
    color: var(--secondary-color);
    font-weight: 500;
    padding: 8px 16px;
    transition: all 0.3s ease;
    position: relative;
  }

  .nav-link:hover {
    color: var(--primary-color);
  }

  .nav-link.active::after {
    content: '';
    position: absolute;
    bottom: 0;
    left: 50%;
    transform: translateX(-50%);
    width: 30px;
    height: 2px;
    background-color: var(--primary-color);
  }

  /* Mega Menu Styles */
  .mega-menu {
    padding: 25px;
    width: 800px;
    border: none;
    box-shadow: 0 5px 25px rgba(0,0,0,0.1);
    border-radius: 12px;
    margin-top: 10px;
    animation: fadeIn 0.3s ease;
    left: 50% !important;
    transform: translateX(-50%);
    position: fixed;
    background: #fff;
  }

  .mega-menu-header {
    text-align: center;
    padding-bottom: 15px;
    border-bottom: 1px solid #eee;
  }

  .mega-menu-title {
    color: var(--primary-color);
    font-size: 1.5em;
    margin-bottom: 5px;
    font-weight: 600;
  }

  .mega-menu-subtitle {
    color: #666;
    font-size: 0.9em;
    margin-bottom: 0;
  }

  /* Category Menu Styles */
  .category-menu {
    list-style: none;
    padding: 0;
    margin: 0;
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    gap: 20px;
  }

  .category-item {
    position: relative;
  }

  .category-item > .category-link {
    font-weight: 600;
    font-size: 1.1em;
    color: var(--primary-color);
    border-bottom: 2px solid #eee;
    margin-bottom: 10px;
    padding: 8px 0;
    display: block;
  }

  .main-category {
    font-weight: 600;
    font-size: 1.1em;
    color: var(--secondary-color);
    padding: 10px 15px;
    border-radius: 8px;
    display: flex;
    align-items: center;
    transition: all 0.3s ease;
  }

  .main-category:hover {
    background-color: var(--hover-bg);
    color: var(--primary-color);
  }

  .category-icon {
    margin-right: 10px;
    font-size: 0.8em;
    color: var(--primary-color);
    transition: transform 0.3s ease;
  }

  .main-category:hover .category-icon {
    transform: translateX(5px);
  }

  /* Sub Menu Styles */
  .sub-menu {
    display: none;
    padding-left: 15px;
    margin-top: 5px;
  }

  .category-item:hover > .sub-menu {
    display: block;
  }

  .sub-menu .sub-menu {
    background: #f8f9fa;
    border-radius: 8px;
    margin-left: 15px;
  }

  .category-link {
    display: block;
    padding: 8px 15px;
    color: var(--secondary-color);
    text-decoration: none;
    transition: all 0.3s ease;
    border-radius: 6px;
    position: relative;
  }

  .category-link:hover {
    color: var(--primary-color);
    background-color: rgba(255, 102, 0, 0.05);
  }

  .category-link.has-children::after {
    content: '\f105';
    font-family: 'Font Awesome 5 Free';
    font-weight: 900;
    position: absolute;
    right: 15px;
    top: 50%;
    transform: translateY(-50%);
    font-size: 0.8em;
    opacity: 0.5;
    transition: all 0.3s ease;
  }

  .category-link:hover::after {
    opacity: 1;
    transform: translateY(-50%) translateX(5px);
  }

  .sub-category {
    font-size: 0.95em;
    padding: 8px 15px 8px 25px;
    color: #555;
    display: flex;
    justify-content: space-between;
    align-items: center;
  }

  /* Navigation Icons */
  .nav-icon {
    font-size: 20px;
    color: var(--secondary-color);
    position: relative;
    margin-left: 25px;
    transition: color 0.3s ease;
  }

  .nav-icon:hover {
    color: var(--primary-color);
  }

  .badge-count {
    position: absolute;
    top: -8px;
    right: -8px;
    background: var(--primary-color);
    color: #fff;
    font-size: 12px;
    padding: 2px 6px;
    border-radius: 10px;
    min-width: 18px;
    text-align: center;
    animation: pulse 2s infinite;
  }

  /* Dropdown Menu */
  .dropdown-menu {
    border: none;
    box-shadow: var(--shadow);
    border-radius: 8px;
    padding: 8px 0;
    left: 50% !important;
    transform: translateX(-50%);
  }

  .dropdown-item {
    padding: 8px 20px;
    transition: all 0.3s ease;
  }

  .dropdown-item:hover {
    background-color: var(--hover-bg);
    color: var(--primary-color);
    transform: translateX(5px);
  }

  /* Animations */
  @keyframes fadeIn {
    from { 
      opacity: 0; 
      transform: translateY(-10px) translateX(-50%); 
    }
    to { 
      opacity: 1; 
      transform: translateY(0) translateX(-50%); 
    }
  }

  @keyframes pulse {
    0% { transform: scale(1); }
    50% { transform: scale(1.1); }
    100% { transform: scale(1); }
  }

  /* Responsive Styles */
  @media (max-width: 991.98px) {
    .mega-menu {
      width: 100%;
      left: 0 !important;
      transform: none;
      position: relative;
      padding: 15px;
    }
    
    .category-menu {
      grid-template-columns: 1fr;
      gap: 15px;
    }
    
    .category-link.has-children::after {
      content: '\f107';
    }
    
    .sub-menu {
      background: transparent;
      padding-left: 15px;
    }

    .nav-icon {
      margin-left: 15px;
    }

    .navbar-collapse {
      background: #fff;
      padding: 15px;
      border-radius: 8px;
      margin-top: 10px;
      box-shadow: var(--shadow);
    }
  }
  
  
</style>
