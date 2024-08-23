package com.ankush.readapp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "highlight")
public class Highlight {

    @Id
    private String id;

    private String bookId;

    private String value;

    private String pageNumber;

    private Integer userId;

}
