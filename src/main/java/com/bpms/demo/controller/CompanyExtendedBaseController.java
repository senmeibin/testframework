package com.bpms.demo.controller;

import com.bpms.core.entity.BaseEntity;
import com.bpms.core.utils.UniqueUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.ui.Model;

public abstract class CompanyExtendedBaseController<T extends BaseEntity> extends DemoBaseController<T> {
    /**
     * 将首字母转为小写
     *
     * @param str
     * @return
     */
    public static String toLowerCaseFirstOne(String str) {
        if (Character.isLowerCase(str.charAt(0)))
            return str;
        else
            return (new StringBuilder()).append(Character.toLowerCase(str.charAt(0))).append(str.substring(1)).toString();
    }

    /**
     * 初始化InputEntity实体对象[后处理]
     *
     * @param model Model对象
     * @param uid   主键UID
     */
    @Override
    protected void initInputEntityAfter(Model model, String uid) {
        if (this.isAddMode()) {
            //主键
            this.getInputEntity().setUid(UniqueUtils.getUID());
            //公司UID
            try {
                BeanUtils.setProperty(this.getInputEntity(), "companyUid", this.getRequest().getParameter("companyUid"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        model.addAttribute("companyUid", this.getRequest().getParameter("companyUid"));
        model.addAttribute(this.toLowerCaseFirstOne(this.getEntityName()) + "Uid", this.getInputEntity().getUid());
    }
}