package com.hkkj.oa.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hkkj.oa.dao.LogSystemMapper;
import com.hkkj.oa.entity.LogSystem;
import com.hkkj.oa.service.ILogSystemService;

@Service
@Transactional
public class LogSystemService implements ILogSystemService {
	@Autowired
	private LogSystemMapper logSystemMapper;

	@Override
	public int saveLogSystem(LogSystem logSystem) {
		return logSystemMapper.insertSelective(logSystem);
	}
}