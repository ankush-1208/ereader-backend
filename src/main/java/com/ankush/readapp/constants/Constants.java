package com.ankush.readapp.constants;

import java.util.List;

public class Constants {

    public static final List<String> ALLOWED_FILE_FORMATS = List.of("pdf", "epub");

    public static class FileHandler {
        public static final String epubHandler = "epubHandler";
        public static final String pdfHandler = "pdfHandler";
    }
}
