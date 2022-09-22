package com.legend.androidstudyapp.customerview;

import com.stx.xhb.androidx.entity.BaseBannerInfo;

/**
 * @desc:
 * @author: wanglezhi
 * @createTime: 2022/7/20 11:18 上午
 */
public class BannerBean implements BaseBannerInfo {
    private String info;

    public BannerBean(String info) {
        this.info = info;
    }

    @Override
    public String getXBannerUrl() {
        return info;
    }

    @Override
    public String getXBannerTitle() {
        return null;
    }
}
