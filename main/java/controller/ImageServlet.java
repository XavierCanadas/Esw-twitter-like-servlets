package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@WebServlet("/images/*")
public class ImageServlet extends HttpServlet {

    // Define absolute path to your images folder
    private static final String IMAGES_DIRECTORY = "images";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestedImage = request.getPathInfo();

        String a = this.getServletContext().getRealPath("WEB-INF/nodes.txt");

        // Security check to prevent directory traversal attacks
        if (requestedImage == null || requestedImage.contains("..") || requestedImage.contains("//")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid image path");
            return;
        }

        // Remove leading slash if present
        if (requestedImage.startsWith("/")) {
            requestedImage = requestedImage.substring(1);
        }

        String imagesDirectory = getServletContext().getRealPath("images");
        File imageFile = new File(imagesDirectory, requestedImage);

        if (!imageFile.exists() || imageFile.isDirectory()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // Set the content type based on the file extension
        String contentType = getServletContext().getMimeType(imageFile.getName());
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        response.setContentType(contentType);

        // Set the content length
        response.setContentLength((int) imageFile.length());

        // Copy the file to the response output stream
        Files.copy(imageFile.toPath(), response.getOutputStream());
    }
}
