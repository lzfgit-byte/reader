package com.ilzf.bookInfo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ilzf.bookInfo.entity.BookInfoEntity;
import com.ilzf.bookInfo.mapper.BookInfoMapper;
import com.ilzf.bookInfo.service.BookInfoService;
import org.springframework.stereotype.Service;

@Service
public class BookInfoServiceImpl extends ServiceImpl<BookInfoMapper, BookInfoEntity> implements BookInfoService {

}
