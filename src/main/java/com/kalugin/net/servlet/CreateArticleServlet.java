package com.kalugin.net.servlet;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.kalugin.net.dto.UserDto;
import com.kalugin.net.helper.CloudinaryHelper;
import com.kalugin.net.helper.ImageHelper;
import com.kalugin.net.model.Article;
import com.kalugin.net.service.impl.ArticleServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@MultipartConfig
@WebServlet(name = "createArticleServlet", urlPatterns = "/createArticle")
public class CreateArticleServlet extends HttpServlet {
    private final ArticleServiceImpl articleService = new ArticleServiceImpl();
    private final Cloudinary cloudinary = CloudinaryHelper.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendRedirect("addArticle.ftl");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        UserDto user = (UserDto) session.getAttribute("user");

        String title = req.getParameter("title");
        String content = req.getParameter("content");
        int userId = user.getId();
        Part part = req.getPart("photo");

        if(!title.equals("") && !content.equals("")) {
            File file = ImageHelper.makeFile(part);
            Map upload = cloudinary.uploader().upload(file, ObjectUtils.asMap("public_id", file.getName()));

            Date date = new Date();
            SimpleDateFormat formatForDateNow = new SimpleDateFormat("dd.MM.yyyy");

            Article article = new Article(userId, title, content, (String) upload.get("url"), formatForDateNow.format(date));
            articleService.save(article);
        }

        resp.sendRedirect("/cabinet");
    }
}
