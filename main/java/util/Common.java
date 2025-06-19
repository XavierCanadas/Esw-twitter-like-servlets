package util;

import jakarta.servlet.http.HttpServletRequest;

public class Common {

    public static String setPictureUrl(String imageName, HttpServletRequest request) {

        String baseUrl = request.getScheme() + "://" +
                request.getServerName() +
                (request.getServerPort() == 80 || request.getServerPort() == 443 ? "" : ":" + request.getServerPort()) +
                request.getContextPath() + "/assets/";


        if (imageName == null || imageName.isEmpty()) {
            return baseUrl + "default.jpg";
        }

        return baseUrl + imageName;
    }
}
