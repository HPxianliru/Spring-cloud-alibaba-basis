package com.xian.cloud.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xian.cloud.entity.JobEntity;
import com.xian.cloud.dao.JobMapper;
import com.xian.cloud.service.DeptService;
import com.xian.cloud.service.JobService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 岗位管理 服务实现类
 * </p>
 *
 * @author xlr
 * @since 2019-11-01
 */
@Service
public class JobServiceImpl extends ServiceImpl<JobMapper, JobEntity> implements JobService {

    @Autowired
    private DeptService deptService;

    @Override
    public IPage<JobEntity> selectJobList(int page, int pageSize, String jobName) {
        LambdaQueryWrapper<JobEntity> jobLambdaQueryWrapper = Wrappers.<JobEntity>lambdaQuery();
        if (StringUtils.isNotEmpty(jobName)) {
            jobLambdaQueryWrapper.eq(JobEntity::getJobName, jobName);
        }
        IPage<JobEntity> sysJobIPage = baseMapper.selectPage(new Page<>(page, pageSize), jobLambdaQueryWrapper);
        List<JobEntity> sysJobList = sysJobIPage.getRecords();
        if (CollectionUtil.isNotEmpty(sysJobList)){
            List<JobEntity> collect = sysJobList.stream().peek(sysJob -> sysJob.setDeptName(deptService.selectDeptNameByDeptId(sysJob.getDeptId()))).sorted((JobEntity o1, JobEntity o2) -> (o1.getSort() - o2.getSort())).collect(Collectors.toList());
            return sysJobIPage.setRecords(collect);
        }
        return sysJobIPage;
    }

    @Override
    public List<JobEntity> selectJobListByDeptId(Integer deptId) {
        return baseMapper.selectList(Wrappers.<JobEntity>lambdaQuery().select(JobEntity::getId, JobEntity::getJobName).eq(JobEntity::getDeptId,deptId));
    }

    @Override
    public String selectJobNameByJobId(Integer jobId) {
        return baseMapper.selectOne(Wrappers.<JobEntity>lambdaQuery().select(JobEntity::getJobName).eq(JobEntity::getId,jobId)).getJobName();
    }

}
