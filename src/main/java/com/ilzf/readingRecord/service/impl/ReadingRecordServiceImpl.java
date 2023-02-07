package com.ilzf.readingRecord.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ilzf.readingRecord.entity.ReadingRecordEntity;
import com.ilzf.readingRecord.mapper.ReadingRecordMapper;
import com.ilzf.readingRecord.service.ReadingRecordService;
import org.springframework.stereotype.Service;

@Service
public class ReadingRecordServiceImpl extends ServiceImpl<ReadingRecordMapper, ReadingRecordEntity> implements ReadingRecordService {
}
