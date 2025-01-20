package com.px.modulars.blood.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.px.modulars.blood.entity.BloodPressure;
import com.px.modulars.blood.mapper.BloodPressureMapper;
import com.px.modulars.blood.service.BloodPressureService;
import org.springframework.stereotype.Service;

@Service
public class BloodPressureServiceImpl extends ServiceImpl<BloodPressureMapper, BloodPressure> implements BloodPressureService {
}
