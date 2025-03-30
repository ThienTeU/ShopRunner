package Controller;

import DAL.BannerDAO;
import Model.Banner;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.Part;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "BannerController", urlPatterns = {"/managerbanner"})
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024, // 1 MB
    maxFileSize = 1024 * 1024 * 5,   // 5 MB
    maxRequestSize = 1024 * 1024 * 10 // 10 MB
)
public class BannerController extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(BannerController.class.getName());
    private static final String UPLOAD_DIRECTORY = "banner_images";
    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList(".jpg", ".jpeg", ".png", ".gif");

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        try {
            switch (action) {
                case "list":
                    listBanners(request, response);
                    break;
                case "add":
                    showAddForm(request, response);
                    break;
                case "create":
                    createBanner(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "update":
                    updateBanner(request, response);
                    break;
                case "delete":
                    deleteBanner(request, response);
                    break;
                case "updateStatus":
                    updateBannerStatus(request, response);
                    break;
                case "updateOrder":
                    updateBannerOrder(request, response);
                    break;
                default:
                    listBanners(request, response);
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error in BannerController", ex);
            request.setAttribute("error", "Có lỗi xảy ra: " + ex.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    private void listBanners(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            BannerDAO bannerDAO = new BannerDAO();
            List<Banner> banners = bannerDAO.getAllBanners();
            request.setAttribute("banners", banners);
            request.getRequestDispatcher("AdminManage/Managerbanner.jsp").forward(request, response);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error listing banners", e);
            request.setAttribute("error", "Không thể tải danh sách banner");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    private void showAddForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("addbanner.jsp").forward(request, response);
    }

    private void createBanner(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Validate file upload
Part filePart = request.getPart("imageFile");
if (filePart == null || filePart.getSize() == 0) {
    request.setAttribute("error", "Vui lòng chọn ảnh banner!");
    request.getRequestDispatcher("editbanner.jsp").forward(request, response);
    return;
}


            String fileName = getSubmittedFileName(filePart);
            String fileExtension = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
            if (!ALLOWED_EXTENSIONS.contains(fileExtension)) {
                request.setAttribute("error", "Chỉ chấp nhận file ảnh có định dạng: " + String.join(", ", ALLOWED_EXTENSIONS));
                request.getRequestDispatcher("addbanner.jsp").forward(request, response);
                return;
            }

            // Get form data
            String linkUrl = request.getParameter("linkUrl");
            int displayOrder;
            try {
                displayOrder = Integer.parseInt(request.getParameter("displayOrder"));
                if (displayOrder <= 0) {
                    request.setAttribute("error", "Thứ tự hiển thị phải lớn hơn 0!");
                    request.getRequestDispatcher("addbanner.jsp").forward(request, response);
                    return;
                }
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Thứ tự hiển thị không hợp lệ!");
                request.getRequestDispatcher("addbanner.jsp").forward(request, response);
                return;
            }

            boolean status = request.getParameter("status") != null;

            // Check if display order exists
            BannerDAO bannerDAO = new BannerDAO();
            if (bannerDAO.isDisplayOrderExists(displayOrder)) {
                request.setAttribute("error", "Thứ tự hiển thị này đã tồn tại!");
                request.getRequestDispatcher("addbanner.jsp").forward(request, response);
                return;
            }

            String uploadPath = getUploadPath(request);
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uniqueFileName = System.currentTimeMillis() + "_" + fileName;
            String filePath = uploadPath + File.separator + uniqueFileName;
            filePart.write(filePath);

            Banner banner = new Banner();
            banner.setImage_url("banner_images/" + uniqueFileName);
            banner.setLink_url(linkUrl);
            banner.setDisplay_order(displayOrder);
            banner.setStatus(status);

            if (bannerDAO.addBanner(banner)) {
                response.sendRedirect("managerbanner?action=list");
            } else {
                request.setAttribute("error", "Không thể thêm banner");
                request.getRequestDispatcher("addbanner.jsp").forward(request, response);
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error creating banner", e);
            request.setAttribute("error", "Có lỗi xảy ra khi thêm banner");
            request.getRequestDispatcher("addbanner.jsp").forward(request, response);
        }
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int bannerId = Integer.parseInt(request.getParameter("id"));
            BannerDAO bannerDAO = new BannerDAO();
            Banner banner = bannerDAO.getBannerById(bannerId);
            
            if (banner != null) {
                request.setAttribute("banner", banner);
                request.getRequestDispatcher("editbanner.jsp").forward(request, response);
            } else {
                response.sendRedirect("managerbanner?action=list");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect("managerbanner?action=list");
        }
    }

    private void updateBanner(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int bannerId = Integer.parseInt(request.getParameter("id"));
            BannerDAO bannerDAO = new BannerDAO();
            Banner banner = bannerDAO.getBannerById(bannerId);

            if (banner == null) {
                response.sendRedirect("managerbanner?action=list");
                return;
            }

            int displayOrder;
            try {
                displayOrder = Integer.parseInt(request.getParameter("displayOrder"));
                if (displayOrder <= 0) {
                    request.setAttribute("error", "Thứ tự hiển thị phải lớn hơn 0!");
                    request.setAttribute("banner", banner);
                    request.getRequestDispatcher("editbanner.jsp").forward(request, response);
                    return;
                }
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Thứ tự hiển thị không hợp lệ!");
                request.setAttribute("banner", banner);
                request.getRequestDispatcher("editbanner.jsp").forward(request, response);
                return;
            }

            if (bannerDAO.isDisplayOrderExists(displayOrder, bannerId)) {
                request.setAttribute("error", "Thứ tự hiển thị này đã tồn tại!");
                request.setAttribute("banner", banner);
                request.getRequestDispatcher("editbanner.jsp").forward(request, response);
                return;
            }

            Part filePart = request.getPart("imageFile");
            if (filePart != null && filePart.getSize() > 0) {
                String fileName = getSubmittedFileName(filePart);
                String fileExtension = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
                
                if (!ALLOWED_EXTENSIONS.contains(fileExtension)) {
                    request.setAttribute("error", "Chỉ chấp nhận file ảnh có định dạng: " + String.join(", ", ALLOWED_EXTENSIONS));
                    request.setAttribute("banner", banner);
                    request.getRequestDispatcher("editbanner.jsp").forward(request, response);
                    return;
                }

                String oldFilePath = request.getServletContext().getRealPath("") + 
                                   File.separator + banner.getImage_url();
                File oldFile = new File(oldFilePath);
                if (oldFile.exists()) {
                    oldFile.delete();
                }

                String uploadPath = getUploadPath(request);
                String uniqueFileName = System.currentTimeMillis() + "_" + fileName;
                String filePath = uploadPath + File.separator + uniqueFileName;
                filePart.write(filePath);
                banner.setImage_url("banner_images/" + uniqueFileName);
            }

            banner.setLink_url(request.getParameter("linkUrl"));
            banner.setDisplay_order(displayOrder);
            banner.setStatus(request.getParameter("status") != null);

            if (bannerDAO.updateBanner(banner)) {
                response.sendRedirect("managerbanner?action=list");
            } else {
                request.setAttribute("error", "Không thể cập nhật banner");
                request.setAttribute("banner", banner);
                request.getRequestDispatcher("editbanner.jsp").forward(request, response);
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error updating banner", e);
            request.setAttribute("error", "Có lỗi xảy ra khi cập nhật banner");
            request.getRequestDispatcher("editbanner.jsp").forward(request, response);
        }
    }

    private void deleteBanner(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int bannerId = Integer.parseInt(request.getParameter("id"));
            BannerDAO bannerDAO = new BannerDAO();
            Banner banner = bannerDAO.getBannerById(bannerId);

            if (banner != null) {
                String filePath = request.getServletContext().getRealPath("") + 
                                File.separator + banner.getImage_url();
                File file = new File(filePath);
                if (file.exists()) {
                    file.delete();
                }

                if (bannerDAO.deleteBanner(bannerId)) {
                    response.sendRedirect("managerbanner?action=list");
                } else {
                    request.setAttribute("error", "Không thể xóa banner");
                    listBanners(request, response);
                }
            } else {
                response.sendRedirect("managerbanner?action=list");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect("managerbanner?action=list");
        }
    }

    private void updateBannerStatus(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int bannerId = Integer.parseInt(request.getParameter("id"));
            boolean status = Boolean.parseBoolean(request.getParameter("status"));
            
            BannerDAO bannerDAO = new BannerDAO();
            Banner banner = bannerDAO.getBannerById(bannerId);
            
            if (banner != null) {
                banner.setStatus(status);
                bannerDAO.updateBanner(banner);
            }
            
            response.sendRedirect("managerbanner?action=list");
        } catch (NumberFormatException e) {
            response.sendRedirect("managerbanner?action=list");
        }
    }

    private void updateBannerOrder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int bannerId = Integer.parseInt(request.getParameter("id"));
            int newOrder = Integer.parseInt(request.getParameter("order"));
            
            if (newOrder <= 0) {
                response.sendRedirect("managerbanner?action=list");
                return;
            }
            
            BannerDAO bannerDAO = new BannerDAO();
            Banner banner = bannerDAO.getBannerById(bannerId);
            
            if (banner != null && !bannerDAO.isDisplayOrderExists(newOrder, bannerId)) {
                banner.setDisplay_order(newOrder);
                bannerDAO.updateBanner(banner);
            }
            
            response.sendRedirect("managerbanner?action=list");
        } catch (NumberFormatException e) {
            response.sendRedirect("managerbanner?action=list");
        }
    }

    private String getSubmittedFileName(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                String fileName = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                return fileName.substring(fileName.lastIndexOf('/') + 1)
                        .substring(fileName.lastIndexOf('\\') + 1);
            }
        }
        return null;
    }

    private String getUploadPath(HttpServletRequest request) {
        return request.getServletContext().getRealPath("") + File.separator + UPLOAD_DIRECTORY;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}