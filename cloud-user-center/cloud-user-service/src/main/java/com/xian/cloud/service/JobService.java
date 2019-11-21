package com.xian.cloud.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xian.cloud.entity.JobEntity;

import java.util.List;

/**
 * <p>
 * 岗位管理 服务类
 * </p>
 *
 * @author xlr
 * @since 2019-11-01
 */
public interface JobService extends IService<JobEntity> {

    /**
     * 分页查询岗位列表
     * @param page
     * @param pageSize
     * @param jobName
     * @return
     */
    IPage<JobEntity> selectJobList(int page, int pageSize, String jobName);


    /**
     * 根据部门id查询所属下的岗位信息
     * @param deptId
     * @return
     */
    List<JobEntity> selectJobListByDeptId(Integer deptId);


    String selectJobNameByJobId(Integer jobId);

}
