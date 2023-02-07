package com.ilzf.pdfReader.utils;

import com.ilzf.bookInfo.entity.BookInfoEntity;
import com.ilzf.pdfReader.entity.CatalogEntity;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.interactive.action.PDActionGoTo;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDPageDestination;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDDocumentOutline;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineItem;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineNode;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

public class PDFReaderUtil {
    public static Map<String, PDDocument> documentList = new HashMap<>();
    public static int coverDpi = 30;
    public static int readDpi = 150;


    /**
     * 根据传入的信息获取pdf页数的图片
     *
     * @param document
     * @param cur
     * @return
     * @throws IOException
     */
    public static String getBase64Current(PDDocument document, int cur) {

        PDFRenderer renderer = new PDFRenderer(document);
        String base64 = "";
        try {
            BufferedImage image = renderer.renderImageWithDPI(cur, readDpi);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            ImageIO.write(image, "png", stream);
            base64 = "data:image/jpg;base64," + Base64.encodeBase64String(stream.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return base64;
    }

    public static String getBase64Current(BookInfoEntity entity, int cur) {
        PDDocument document = documentList.get(entity.getBookName());
        if (document == null) {
            try {
                File file = new File(entity.getBookPath());
                if (!file.exists()) return "";
                document = getPDDocument(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return getBase64Current(document, cur);
    }

    /**
     * 将PDF图片写入到返回流中
     *
     * @param document
     * @param cur
     * @param response
     * @throws IOException
     */
    public static void writeImgInResponse(PDDocument document, int cur, HttpServletResponse response) {
        PDFRenderer renderer = new PDFRenderer(document);
        try {
            BufferedImage image = renderer.renderImageWithDPI(cur, readDpi);
            ImageIO.write(image, "png", response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeImgInResponse(BookInfoEntity entity, int cur, HttpServletResponse response) {
        PDDocument document = documentList.get(entity.getBookName());
        if (document == null) {
            try {
                File file = new File(entity.getBookPath());
                if (!file.exists()) return;
                document = getPDDocument(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        writeImgInResponse(document, cur, response);
    }

    /**
     * 构建目录结构
     *
     * @param bookmark
     * @param res
     * @throws Exception
     */
    public static void buildCatalogEntityTree(PDOutlineNode bookmark, List<CatalogEntity> res) {
        PDOutlineItem current = bookmark.getFirstChild();
        while (current != null) {
            CatalogEntity entity = new CatalogEntity();
            entity.setChildrens(new ArrayList<>());
            entity.setName(current.getTitle());
            int pages = 0;
            try {
                if (current.getDestination() instanceof PDPageDestination) {
                    PDPageDestination pd = (PDPageDestination) current.getDestination();
                    pages = pd.retrievePageNumber();
                    entity.setPageIndex(pages);
                }
                if (current.getAction() instanceof PDActionGoTo) {
                    PDActionGoTo gta = (PDActionGoTo) current.getAction();
                    if (gta.getDestination() instanceof PDPageDestination) {
                        PDPageDestination pd = (PDPageDestination) gta.getDestination();
                        pages = pd.retrievePageNumber();
                        entity.setPageIndex(pages);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            buildCatalogEntityTree(current, entity.getChildrens());
            current = current.getNextSibling();
            res.add(entity);
        }
    }

    public static void setCache(String key, PDDocument document) {
        documentList.put(key, document);
    }

    public static PDDocument getCache(String key) {
        return documentList.getOrDefault(key, null);
    }

    /**
     * 构建结构树
     *
     * @param file
     * @throws IOException
     */
    public static List<CatalogEntity> buildCatalogEntityTree(File file) throws Exception {
        List<CatalogEntity> res = new ArrayList<>();
        PDDocument document = getCache(file.getName());
        if (document == null) {
            document = PDDocument.load(file);
            setCache(file.getName(), document);
        }

        PDDocumentOutline documentOutline = document.getDocumentCatalog().getDocumentOutline();
        buildCatalogEntityTree(documentOutline, res);
        return res;
    }

    /**
     * 获取 PDDocument
     *
     * @param file
     * @return
     * @throws IOException
     */
    public static PDDocument getPDDocument(File file) throws IOException {
        PDDocument load = PDDocument.load(file);
        setCache(file.getName(), load);
        return load;
    }

    /**
     * 获取封页数据
     *
     * @param document
     * @return
     */
    public static String getCoverImg(PDDocument document) {
        PDFRenderer renderer = new PDFRenderer(document);
        String base64 = "";
        try {
            BufferedImage image = renderer.renderImageWithDPI(0, coverDpi);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            ImageIO.write(image, "png", stream);
            base64 = "data:image/jpg;base64," + Base64.encodeBase64String(stream.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return base64;
    }

    /**
     * 获取数据基本信息 设置数据名字,总页数,封面
     *
     * @param file
     * @return
     */
    public static BookInfoEntity getBookInfoEntity(File file) {
        BookInfoEntity entity = new BookInfoEntity();
        PDDocument document = null;
        try {
            document = PDDocument.load(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert document != null;
        int numberOfPages = document.getNumberOfPages();
        entity.setBookName(file.getName());
        entity.setTotal(numberOfPages);
        entity.setCoverImg(getCoverImg(document));
        return entity;
    }
}
