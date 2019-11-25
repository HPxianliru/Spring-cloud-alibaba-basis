package com.xian.common.utils.data;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 分页查询条件封装
 *
 * @author xlr
 * @date 2017/12/10
 */
@SuppressWarnings("unchecked")
public class PageQuery<T> extends Page<T> {
    private static final long serialVersionUID = 6666126323467141114L;

    private static final String PAGE = "page";

    private static final String LIMIT = "limit";

    private static final String ASC = "asc";

    private static final String DESC = "desc";

    public PageQuery(Map<String, Object> params) {
        super(Integer.parseInt(params.getOrDefault(PAGE, 1).toString())
                , Integer.parseInt(params.getOrDefault(LIMIT, 10).toString()));

        final List<String> ascs = (List<String>) params.getOrDefault(ASC, Collections.emptyList());
        this.setAscs(ascs);
        final List<String> descs = (List<String>) params.getOrDefault(DESC, Collections.emptyList());
        this.setDescs(descs);

        params.remove(PAGE);
        params.remove(LIMIT);
        params.remove(ASC);
        params.remove(DESC);

    }
}
