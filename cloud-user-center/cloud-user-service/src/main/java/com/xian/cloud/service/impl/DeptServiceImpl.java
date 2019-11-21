package com.xian.cloud.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xian.cloud.dto.DeptDTO;
import com.xian.cloud.entity.DeptEntity;
import com.xian.cloud.dao.DeptMapper;
import com.xian.cloud.service.DeptService;
import com.xian.cloud.utils.PreUtil;
import com.xian.cloud.vo.DeptTreeVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 部门管理 服务实现类
 * </p>
 *
 * @author lihaodong
 * @since 2019-04-21
 */
@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, DeptEntity> implements DeptService {

    @Override
    public List<DeptEntity> selectDeptList() {
        List<DeptEntity> depts = baseMapper.selectList(Wrappers.<DeptEntity>lambdaQuery().select(DeptEntity::getDeptId, DeptEntity::getName, DeptEntity::getParentId, DeptEntity::getSort, DeptEntity::getCreateTime));
        List<DeptEntity> sysDepts = depts.stream()
                .filter(sysDept -> sysDept.getParentId() == 0 || ObjectUtil.isNull(sysDept.getParentId()))
                .peek(sysDept -> sysDept.setLevel(0))
                .collect(Collectors.toList());
        PreUtil.findChildren(sysDepts, depts);
        return sysDepts;
    }


    @Override
    public boolean updateDeptById(DeptDTO entity) {
        DeptEntity sysDept = new DeptEntity();
        BeanUtils.copyProperties(entity, sysDept);
        sysDept.setUpdateTime(LocalDateTime.now());
        return this.updateById(sysDept);
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeById(Serializable id) {
        // 部门层级删除
        List<Integer> idList = this.list(Wrappers.<DeptEntity>query().lambda().eq(DeptEntity::getParentId, id)).stream().map(DeptEntity::getDeptId).collect(Collectors.toList());
        // 删除自己
        idList.add((Integer) id);
        return super.removeByIds(idList);
    }

    @Override
    public String selectDeptNameByDeptId(int deptId) {
        return baseMapper.selectOne(Wrappers.<DeptEntity>query().lambda().select(DeptEntity::getName).eq(DeptEntity::getDeptId, deptId)).getName();
    }

    @Override
    public List<DeptEntity> selectDeptListBydeptName(String deptName) {
        return null;
    }

    @Override
    public List<Integer> selectDeptIds(int deptId) {
        DeptEntity department = this.getDepartment(deptId);
        List<Integer> deptIdList = new ArrayList<>();
        if (department != null) {
            deptIdList.add(department.getDeptId());
            addDeptIdList(deptIdList, department);
        }
        return deptIdList;
    }

    @Override
    public List<DeptTreeVo> getDeptTree() {
        List<DeptEntity> depts = baseMapper.selectList(Wrappers.<DeptEntity>query().select("dept_id", "name", "parent_id", "sort", "create_time"));
        List<DeptTreeVo> collect = depts.stream().filter(sysDept -> sysDept.getParentId() == 0 || ObjectUtil.isNull(sysDept.getParentId()))
                .map(sysDept -> {
                    DeptTreeVo deptTreeVo = new DeptTreeVo();
                    deptTreeVo.setId(sysDept.getDeptId());
                    deptTreeVo.setLabel(sysDept.getName());
                    return deptTreeVo;

                }).collect(Collectors.toList());

        PreUtil.findChildren1(collect,depts);
        return collect;
    }


    /**
     * 根据部门ID获取该部门及其下属部门树
     */
    private DeptEntity getDepartment(Integer deptId) {
        List<DeptEntity> departments = baseMapper.selectList(Wrappers.<DeptEntity>query().select("dept_id", "name", "parent_id", "sort", "create_time"));
        Map<Integer, DeptEntity> map = departments.stream().collect(
                Collectors.toMap(DeptEntity::getDeptId, department -> department));

        for (DeptEntity dept : map.values()) {
            DeptEntity parent = map.get(dept.getParentId());
            if (parent != null) {
                List<DeptEntity> children = parent.getChildren() == null ? new ArrayList<>() : parent.getChildren();
                children.add(dept);
                parent.setChildren(children);
            }
        }
        return map.get(deptId);
    }
    private void addDeptIdList(List<Integer> deptIdList, DeptEntity department) {
        List<DeptEntity> children = department.getChildren();
        if (children != null) {
            for (DeptEntity d : children) {
                deptIdList.add(d.getDeptId());
                addDeptIdList(deptIdList, d);
            }
        }
    }


}
