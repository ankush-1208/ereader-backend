package com.ankush.readapp.handler;

import com.ankush.readapp.constants.Constants;
import com.ankush.readapp.dto.BookMetadata;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Slf4j
@Component(Constants.FileHandler.epubHandler)
public class EpubHandler implements FileHandler {
    @Override
    public BookMetadata extractMetadata(MultipartFile file) {
        var metadata = new BookMetadata();
        try (ZipInputStream zipInputStream = new ZipInputStream(file.getInputStream())) {
            ZipEntry entry;
            // Iterate over entries in the EPUB file
            while ((entry = zipInputStream.getNextEntry()) != null) {
                if (entry.getName().contains("content.opf")) { // Look for the metadata file
                    // Extract metadata
                    metadata = extractMetadata(zipInputStream);
                    break;
                }
            }
        } catch (IOException | ParserConfigurationException | SAXException e) {
            log.error("Failed to extract metadata from EPUB file, stacktrace: {}", e.getStackTrace());
        }

        return metadata;
    }

    private BookMetadata extractMetadata(ZipInputStream metadataStream) throws ParserConfigurationException, IOException, SAXException {
        BookMetadata bookMetadata = new BookMetadata();
        var dbFactory = DocumentBuilderFactory.newInstance();
        var dBuilder = dbFactory.newDocumentBuilder();
        var doc = dBuilder.parse(metadataStream);
        doc.getDocumentElement().normalize();
        bookMetadata.setTitle(extractValueFromTag(doc.getElementsByTagName("dc:title"))); ;
        bookMetadata.setAuthor(extractValueFromTag(doc.getElementsByTagName("dc:creator")));
        bookMetadata.setLanguage(extractValueFromTag(doc.getElementsByTagName("dc:language")));
        bookMetadata.setGenre(extractValueFromTag(doc.getElementsByTagName("dc:subject")));
        return bookMetadata;
    }

    private String extractValueFromTag(NodeList node) {
        var text = "";
        if (node.getLength() > 0) {
            var textElement = (Element) node.item(0);
            text = textElement.getTextContent();
        }

        return text;
    }
}
