package com.ilzf.bookTable.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ilzf.bookTable.entity.BookTableEntity;
import com.ilzf.bookTable.mapper.BookTableMapper;
import com.ilzf.bookTable.service.BookTableService;
import org.springframework.stereotype.Service;

@Service
public class BookTableServiceImpl extends ServiceImpl<BookTableMapper, BookTableEntity> implements BookTableService {
}
