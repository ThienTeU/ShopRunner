
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">

    <!-- Basic -->
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">

    <!-- Mobile Metas -->
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Site Metas -->
    <title>Sport Blogs</title>
    <meta name="keywords" content="">
    <meta name="description" content="">
    <meta name="author" content="">

    <!-- Site Icons -->
    <link rel="shortcut icon" href="images/favicon.ico" type="image/x-icon" />
    <link rel="apple-touch-icon" href="images/apple-touch-icon.png">

    <!-- Design fonts -->
    <link href="https://fonts.googleapis.com/css?family=Poppins:300,400,500,600,700" rel="stylesheet"> 

    <!-- Bootstrap core CSS -->
    <link href="css/css_post/css/bootstrap.css" rel="stylesheet">

    <!-- FontAwesome Icons core CSS -->
    <!--    <link href="css/css_post/css/font-awesome.min.css" rel="stylesheet">-->

    <!-- Custom styles for this template -->
    <!--    <link href="css/css_post/style.css" rel="stylesheet">-->

    <!-- Responsive styles for this template -->
    <!--    <link href="css/css_post/css/responsive.css" rel="stylesheet">-->

    <!-- Colors for this template -->


    <!-- Version Tech CSS for this template -->
    <!--    <link href="css/css_post/css/version/tech.css" rel="stylesheet">-->
    <link href="img/favicon.ico" rel="icon">

    <!-- Google Web Fonts -->
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap" rel="stylesheet">  

    <!-- Font Awesome -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">

    <!-- Libraries Stylesheet -->
    <link href="lib/animate/animate.min.css" rel="stylesheet">
    <link href="lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">

    <!-- Customized Bootstrap Stylesheet -->
    <link href="css/style.css" rel="stylesheet">
    <link rel="stylesheet" href="css/postList.css">

    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>


<body>
    
        <%@include file="component/header.jsp" %>
    <%@ page pageEncoding="UTF-8" %>
    <!--    topbar-->

    <div class="container-fluid">
        <div class="row bg-secondary py-1 px-xl-5">
            <div class="col-lg-6 d-none d-lg-block">
                <div class="d-inline-flex align-items-center h-100">

                </div>
            </div>
            <div class="col-lg-6 text-center text-lg-right">

                <div class="d-inline-flex align-items-center d-block d-lg-none">
                    <a href="" class="btn px-0 ml-2">
                        <i class="fas fa-heart text-dark"></i>
                        <span class="badge text-dark border border-dark rounded-circle" style="padding-bottom: 2px;">0</span>
                    </a>
                    <a href="" class="btn px-0 ml-2">
                        <i class="fas fa-shopping-cart text-dark"></i>
                        <span class="badge text-dark border border-dark rounded-circle" style="padding-bottom: 2px;">0</span>
                    </a>
                </div>
            </div>
        </div>
    <!--end top bar-->
    
    <div id="wrapper" class="d-flex justify-content-center">
    <div class="col-lg-6 col-md-8 col-12">
        <form action="PostListController" method="post" class="d-flex justify-content-center">
            <div class="input-group">
                <input type="text" class="form-control" placeholder="What are you search for?" name="search">
                <div class="input-group-append">
                    <span class="input-group-text bg-transparent text-primary">
                        <button type="submit" class="fa fa-search" style="text-decoration: none; border: none"></button>
                    </span>
                </div>
            </div>
        </form>
    </div>
</div>

    
    <div id="wrapper">
        <form action="PostListController">
            <div class="option">
                <a href="PostListController?num=0">Mới nhất</a>
                <c:forEach items="${requestScope.postCategoryDTOs}" var="c">
                    <a href="PostListController?num=${c.getCategoryID()}">${c.getName()}</a>
                </c:forEach>
            </div>
        </form>
        <section id="blog">
            <c:if test="${size < 1}">
                <div class="blog-heading">
                    <span>Chúng ta có bài viết gì mới hôm nay?</span>
                    <h3>Xin lỗi! không có bài viết nào cả</h3>
                </div>
            </c:if>
            <c:if test="${size > 0}">
                <div class="blog-heading">
                    <span>Chúng ta có bài viết gì mới hôm nay?</span>
                    <h3>Các bài viết gần đây</h3>
                </div>
                <div class="blog-container">
                    <!-- blog1 -->
                    <c:forEach items="${requestScope.postDTOs}" var="c">
                        <div class="blog-box">
                            <!-- img -->
                            <div class="blog-img">
                                <img src="images/Post/${c.getPostImg()}"
                                     alt="">
                            </div>
                            <!-- text -->
                            <div class="blog-text">
                                <span>${c.getDateCreated()}</span>
                                <a href="postDetail?postID=${c.getPostID()}" class="blog-title">${c.getTitle()}</a>
                                <p>${c.getDescription()}</p>
                                <a href="postDetail?postID=${c.getPostID()}">Xem thêm</a>
                                <p>Số lượt xem: ${c.getViews()}</p>
                            </div>
                                <!-- Phân trang -->
<div class="pagination">
    <c:if test="${currentPage > 1}">
        <a href="PostListController?page=${currentPage - 1}">« Trước</a>
    </c:if>

    <c:forEach var="i" begin="1" end="${totalPages}">
        <a href="PostListController?page=${i}" class="${i == currentPage ? 'active' : ''}">${i}</a>
    </c:forEach>

    <c:if test="${currentPage < totalPages}">
        <a href="PostListController?page=${currentPage + 1}">Sau »</a>
    </c:if>
</div>
                        </div>
                    </c:forEach>



                </div>
            </c:if>

            <!-- blog container -->



        </section>
        <%@include file="component/footer.jsp" %>
    </div><!-- end wrapper -->

    <!-- Core JavaScript
    ================================================== -->
    <script src="css/css_post/js/jquery.min.js"></script>
    <script src="css/css_post/js/tether.min.js"></script>
    <script src="css/css_post/js/bootstrap.min.js"></script>
    <script src="css/css_post/js/custom.js"></script>
    <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.bundle.min.js"></script>
    <script src="lib/easing/easing.min.js"></script>
    <script src="lib/owlcarousel/owl.carousel.min.js"></script>

    <!-- Contact Javascript File -->
    <script src="mail/jqBootstrapValidation.min.js"></script>
    <script src="mail/contact.js"></script>

    <!-- Template Javascript -->
    <script src="js/main.js"></script>
</body>
</html>