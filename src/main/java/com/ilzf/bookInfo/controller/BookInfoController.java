package com.ilzf.bookInfo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ilzf.base.controller.BaseController;
import com.ilzf.base.entity.PageEntity;
import com.ilzf.base.entity.ResultEntity;
import com.ilzf.base.utils.SuperKitUtil;
import com.ilzf.bookInfo.entity.BookInfoEntity;
import com.ilzf.bookInfo.mapper.BookInfoMapper;
import com.ilzf.bookInfo.service.BookInfoService;
import com.ilzf.pdfReader.utils.PDFReaderUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/bookInfoController")
public class BookInfoController extends BaseController {
    @Resource
    BookInfoService bookInfoService;

    /**
     * 获取用户下的图书
     *
     * @return
     */
    @RequestMapping("/getBookByUserId")
    public ResultEntity<List<BookInfoEntity>> getBookByUserId() {

        return null;
    }

    /**
     * 获取一本书的信息
     * @param id
     * @return
     */
    @RequestMapping("/getOne")
    public ResultEntity<BookInfoEntity> getOne(@RequestParam(value = "id") String id){
        return ResultEntity.success(bookInfoService.getById(id));
    }

    /**
     * 获取全部的图书
     *
     * @return
     */
    @RequestMapping("/getAllBooks")
    public ResultEntity<?> getAllBooks(@RequestParam(value = "current", defaultValue = "1") String current,
                                       @RequestParam(value = "current", defaultValue = "10") String size) {
        IPage<BookInfoEntity> page = new Page<>(Integer.parseInt(current), Integer.parseInt(size));
        IPage<BookInfoEntity> list = bookInfoService.page(page, null);
        list.getRecords().forEach(BookInfoEntity::toggleCoverImg);
        return ResultEntity.success(PageEntity.getPageEntity(list.getRecords(), Integer.parseInt(current), list.getTotal()));
    }

    @RequestMapping("/uploadFile")
    public ResultEntity<?> uploadFile(@RequestParam(value = "file") MultipartFile file) {
        if (file == null){
            return ResultEntity.error();
        }
        String name = file.getOriginalFilename();
        assert name != null;
        String[] split = name.split("\\.");
        if(!(split.length > 0 && "pdf".equals(split[split.length-1]))){
            return ResultEntity.error("请上传pdf文件");
        }
        File target = null;
        try {
            String filePath = SuperKitUtil.getUploadFilePath() + name;
            target = new File(filePath);
            if(target.exists() && !target.delete()){
               return ResultEntity.error("文件已存在");
            }
            file.transferTo(target);
            BookInfoEntity bookInfoEntity = PDFReaderUtil.getBookInfoEntity(target);
            bookInfoEntity.setBookPath(filePath);
            //转换存储 封页
            String coverImg = SuperKitUtil.getUploadFilePath() + split[0];
            BookInfoEntity.toggleCoverImg(bookInfoEntity,coverImg);
            bookInfoService.save(bookInfoEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.error();
        }
        return ResultEntity.success();
    }

}
