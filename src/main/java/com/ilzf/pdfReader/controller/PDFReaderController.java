package com.ilzf.pdfReader.controller;

import com.ilzf.base.controller.BaseController;
import com.ilzf.base.entity.ResultEntity;
import com.ilzf.bookInfo.entity.BookInfoEntity;
import com.ilzf.bookInfo.service.BookInfoService;
import com.ilzf.pdfReader.entity.CatalogEntity;
import com.ilzf.pdfReader.utils.PDFReaderUtil;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/PDFReaderController")
public class PDFReaderController extends BaseController {

    @Resource
    BookInfoService bookInfoService;

    /**
     * 获取图书页数base64编码图片
     * @return
     * @throws Exception
     */
    @RequestMapping("/getPageBase64")
    public ResultEntity<?> getPageBase64() throws Exception {
        String pageIndex = super.request.getParameter("pageIndex");
        String id = super.request.getParameter("id");
        BookInfoEntity byId = bookInfoService.getById(id);
        if(byId == null){
            return ResultEntity.error("无效数据");
        }
        String base64Current = PDFReaderUtil.getBase64Current(byId, Integer.parseInt(pageIndex));
        return ResultEntity.success(base64Current,true);
    }

    /**
     * 获取页面的图片
     * @param response
     * @throws Exception
     */
    @RequestMapping("/getImg")
    public void getImg(HttpServletResponse response) throws Exception {
        String pageIndex = super.request.getParameter("pageIndex");
        String id = super.request.getParameter("id");
        BookInfoEntity byId = bookInfoService.getById(id);
        if(byId == null){
            return;
        }
        PDFReaderUtil.writeImgInResponse(byId,Integer.parseInt(pageIndex),response);
    }

    /**
     * 获取目录
     * @return
     * @throws Exception
     */
    @RequestMapping("/getCatalog")
    public ResultEntity<?> getCatalog(@RequestBody Map<String,String> req) throws Exception {
        String id = req.get("id");
        if(id == null){
            return ResultEntity.error("书籍获取失败");
        }
        BookInfoEntity byId = bookInfoService.getById(id);
        File file = new File(byId.getBookPath());
        List<CatalogEntity> catalogEntities = PDFReaderUtil.buildCatalogEntityTree(file);
        return ResultEntity.success(catalogEntities);
    }
}
