package com.bpms.sys.controller;

import com.bpms.core.exception.ServiceException;
import com.bpms.sys.entity.ext.AttachmentImportExt;
import com.bpms.sys.service.AttachmentImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 附件导入控制器类
 */
@Controller
@RequestMapping("/sys/attachmentimport")
public class AttachmentImportController extends SysBaseController<AttachmentImportExt> {
    /**
     * Service对象
     */
    @Autowired
    protected AttachmentImportService attachmentImportService;

    /**
     * 取得Service对象
     */
    @Override
    public AttachmentImportService getService() {
        return attachmentImportService;
    }

    /**
     * 数据一览画面初期化
     *
     * @param model Model对象
     * @return 数据一览JSP页面
     * @throws IOException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    @Override
    @RequestMapping(value = {"list", ""}, method = RequestMethod.GET)
    public String list(Model model) throws IllegalAccessException, IOException, InstantiationException {
        return super.list(model);
    }

    /**
     * 初始化下拉框选项内容
     *
     * @param model Model对象
     * @throws IllegalAccessException
     * @throws IOException
     */
    @Override
    public void initOptionList(Model model) throws IllegalAccessException, IOException {
        Map<String, Object> dicMap = new HashMap<>();

        //应用名称
        dicMap.put("appCode", this.getDropdownList("sys_application", "app_code", "app_name"));

        model.addAttribute("jsonOptionList_" + this.getEntityName(), this.toJSON(dicMap, true));
    }

    /**
     * 一览数据标准分页排序查询
     *
     * @param pagerJson     分页JSON字符串
     * @param conditionJson 查询条件JSON字符串
     * @return 一览数据集
     * @throws IOException
     * @throws IllegalAccessException
     */
    @ResponseBody
    @RequestMapping(value = "/searchWithShortDetailMessage", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, name = "检索")
    public Page<AttachmentImportExt> searchWithShortDetailMessage(@RequestParam(required = false) String pagerJson, @RequestParam(required = false) String conditionJson) throws IOException, IllegalAccessException, ServiceException {
        //取得查询条件
        Map<String, Object> condition = this.getCondition(conditionJson);

        //保存最新的检索条件
        this.setSessionSearchCondition(conditionJson);
        this.setSessionSearchPager(pagerJson);

        //取得标准查询SQL文
        String sql = getService().getSQL("sys/attachmentimport/searchWithShortDetailMessage");

        //数据查询
        return this.search(sql, condition, this.getPageRequest(this.getPager(pagerJson)));
    }


    /**
     * 附件文件下载
     *
     * @param uid 附件UID
     * @return ResponseEntity
     * @throws IOException
     */
    @RequestMapping(value = "download", method = RequestMethod.GET)
    public ResponseEntity<byte[]> download(@RequestParam("uid") String uid) throws IOException {
        //取得附件文件实体
        AttachmentImportExt attachment = attachmentImportService.findOne(uid);

        //附件文件实体不存在的场合
        if (attachment == null) {
            this.getResponse().sendRedirect("/error/FileNotFound");
            if (log.isInfoEnabled()) {
                log.info("附件文体不存在。");
            }
            return null;
        }

        return this.downloadFile(attachment.getFileName(), attachment.getFilePath(), attachment.getMimeType());
    }
}
