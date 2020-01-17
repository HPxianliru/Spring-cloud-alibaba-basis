package com.xian.cloud.thread.model;

import lombok.Data;

import java.util.List;

@Data
public class JDCouponResponse {


    private String msg;
    private int code;
    private DataBean data;
    private boolean success;

    @Data
    public static class DataBean {
        private List<SkuListBean> skuList;

        @Data
        public static class SkuListBean {
            /**
             * skuName : 南极人（NANJIREN） 足浴盆 全自动深桶洗脚盆泡脚盆足疗加热足浴器 豪华款
             * pinGouPrice : null
             * couponDiscount : 50
             * reasons : null
             * comments : 177
             * imageUrl : http://img14.360buyimg.com/ads/jfs/t1/72281/16/14557/148186/5dbfc48fE290faef3/8637c1a40f4d1d90.jpg
             * couponUrl : https://coupon.m.jd.com/coupons/show.action?linkKey=AAROH%2FxIpeffAs%2F%2BnaABEFoem7IhYBgDIXqNjeQ4gSQTvwe0lpDcZeFpdj7b%2FcUBtf3FTZMgMmBBHaa6okTWlAUqSELLEA%3D%3D&to=mall.jd.com/index-10058808.html
             * goodCommentsShare : 98.0
             * jdPrice : 299.00
             * couponPrice : 249.00
             * source : 0_0_2
             * skuId : 51543903168
             */

            private String skuName;
            //拼团购
            private Object pinGouPrice;
            //优惠券金额
            private int couponDiscount;

            private Object reasons;
            private int comments;
            //商品图片地址
            private String imageUrl;
            //连接地址
            private String couponUrl;

            private double goodCommentsShare;
            private String jdPrice;
            private String couponPrice;
            private String source;
            private long skuId;
        }
    }
}
