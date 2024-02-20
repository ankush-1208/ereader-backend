package com.ankush.readapp.entity;

import com.ankush.readapp.enums.FileType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "books")
public class Book {

    @Id
    private String id;

    private String title;

    private String author;

    private String language;

    private String genre;

    private String fileName;

    private String userId;

    private FileType fileType;

    private Date createdDate;

}
