package com.ankush.readapp.interceptor;

import com.ankush.readapp.annotations.FileUploadEndpoint;
import com.ankush.readapp.constants.Constants;
import com.ankush.readapp.exception.BadRequestException;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.experimental.NonFinal;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.util.List;

@Component
public class FileInterceptor implements HandlerInterceptor {

    private static final List<String> contentTypes = List.of("application/pdf", "application/epub+zip");

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws Exception {
        var isValidFileUploadMethod = handler instanceof HandlerMethod handlerMethod &&
                handlerMethod.hasMethodAnnotation(FileUploadEndpoint.class);
        if (isValidFileUploadMethod) {
            performValidations(request);
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    private void performValidations(HttpServletRequest request) throws ServletException, IOException {
        var parts = request.getParts();
        if (CollectionUtils.isEmpty(parts)) {
            throw new BadRequestException("No file uploaded");
        }

        // For now
        if (parts.size() > 1) {
            throw new BadRequestException("Only 1 file allowed to upload");
        }

        parts.forEach(part -> {
            if (part.getSize() == 0) {
                throw new BadRequestException("File cannot be empty");
            }

            if (!contentTypes.contains(part.getContentType())) {
                throw new BadRequestException("File type not supported");
            }

            var fileName = part.getSubmittedFileName();
            if (StringUtils.isNotBlank(fileName)) {
                var extension = fileName.substring(fileName.lastIndexOf(".") + 1);
                var validExtension = Constants.ALLOWED_FILE_FORMATS.contains(extension);
                if (!validExtension) {
                    throw new BadRequestException("The provided extension is not supported");
                }
            }
        });
    }

}
