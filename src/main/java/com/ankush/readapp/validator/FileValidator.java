package com.ankush.readapp.validator;

import com.ankush.readapp.constants.Constants;
import com.ankush.readapp.exception.BadRequestException;
import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileValidator {

    /**
     * Validates the file, checks if its empty and supported
     *
     * @param file The uploaded file
     */
    public void validateFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new BadRequestException("File is empty");
        }

        String fileName = file.getOriginalFilename();
        if (StringUtils.isNotBlank(fileName)) {
            String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
            boolean validExtension = Constants.ALLOWED_FILE_FORMATS.contains(extension);
            if (!validExtension) {
                throw new BadRequestException("The provided extension is not supported");
            }
        }
    }

}
