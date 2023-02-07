package com.ilzf.bookInfo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ilzf.base.entity.BaseEntity;
import com.ilzf.base.utils.SuperKitUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("book_info")
public class BookInfoEntity extends BaseEntity {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    @TableField("book_name")
    private String bookName;
    @TableField("book_path")
    private String bookPath;
    @TableField("cover_img")
    private String coverImg;
    @TableField("book_describe")
    private String bookDescribe;
    @TableField("total")
    private Integer total;
    /**
     * 将base64存到本地文件
     * @param bookInfoEntity
     * @param coverImgPath
     * @throws Exception
     */
    public static void toggleCoverImg(BookInfoEntity bookInfoEntity,String coverImgPath) throws Exception {
        FileOutputStream fos = new FileOutputStream(coverImgPath);
        fos.write(bookInfoEntity.getCoverImg().getBytes());
        bookInfoEntity.setCoverImg(coverImgPath);
    }

    /**
     * 从本地文件读取 base64信息
     * @param bookInfoEntity
     * @throws Exception
     */
    public static void toggleCoverImg(BookInfoEntity bookInfoEntity) {
        String coverImg = bookInfoEntity.getCoverImg();
        File file = new File(coverImg);
        if(file.exists()){
            long length = file.length();
            byte[] bb = new byte[(int) length];
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(file);
                fis.read(bb);
            } catch (Exception e) {
                e.printStackTrace();
            }

            bookInfoEntity.setCoverImg(new String(bb));
        }
    }
}
