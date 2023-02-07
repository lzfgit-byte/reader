package com.ilzf.pdfReader.entity;

import lombok.Data;

import java.util.List;

/**
 * PDF 目录
 */
@Data
public class CatalogEntity {
    private String name;
    private Integer pageIndex;
    private List<CatalogEntity> childrens;
}
