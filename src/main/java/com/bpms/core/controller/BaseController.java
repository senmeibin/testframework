package com.bpms.core.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bpms.core.AppContext;
import com.bpms.core.consts.AppCodeConsts;
import com.bpms.core.consts.CmnConsts;
import com.bpms.core.entity.AjaxResult;
import com.bpms.core.entity.BaseEntity;
import com.bpms.core.entity.CoreEntity;
import com.bpms.core.entity.DropdownEntity;
import com.bpms.core.excel.ExcelColInfo;
import com.bpms.core.excel.ExcelExportTemplate;
import com.bpms.core.excel.ExcelUtils;
import com.bpms.core.exception.ServiceException;
import com.bpms.core.security.ShiroUser;
import com.bpms.core.service.BaseService;
import com.bpms.core.sync.SyncDataService;
import com.bpms.core.utils.*;
import com.bpms.sys.entity.User;
import com.bpms.sys.entity.ext.AttachmentImportExt;
import com.bpms.sys.entity.ext.CustomSettingExt;
import com.bpms.sys.entity.ext.UserExt;
import com.bpms.sys.service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Maps;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.lang.annotation.Annotation;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/***
 * 带检索功能的Controller基类
 */
@SuppressWarnings("unchecked")
public abstract class BaseController<T extends CoreEntity> {
    /**
     * 日志输出对象
     */
    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected UserService userService;

    /**
     * DictionaryService对象
     */
    @Autowired
    protected DictionaryService dictionaryService;

    /**
     * ParameterService对象
     */
    @Autowired
    protected ParameterService parameterService;

    /**
     * CustomSettingService对象
     */
    @Autowired
    protected CustomSettingService customSettingService;

    /**
     * ColumnSettingService对象
     */
    @Autowired
    protected ColumnSettingService columnSettingService;

    /**
     * SyncDataService对象
     */
    @Autowired
    protected SyncDataService syncDataService;

    /**
     * 取得HttpResponse实例对象
     *
     * @return HttpResponse实例对象
     */
    protected HttpServletResponse getResponse() {
        return AppContext.getResponse();
    }

    /**
     * 取得HttpRequest实例对象
     *
     * @return HttpRequest实例对象
     */
    protected HttpServletRequest getRequest() {
        return AppContext.getRequest();
    }


    /**
     * 取得HttpSession实例对象
     *
     * @return HttpSession实例对象
     */
    protected HttpSession getSession() {
        return AppContext.getSession();
    }

    /**
     * 取得数据输入实体
     *
     * @return 数据输入实体
     */
    protected T getInputEntity() {
        return (T) AppContext.getRequest().getAttribute(this.getClass().getName().toUpperCase() + "_INPUT_ENTITY");
    }

    /**
     * 设置数据输入实体
     *
     * @param inputEntity 数据输入实体
     */
    protected void setInputEntity(T inputEntity) {
        AppContext.getRequest().setAttribute(this.getClass().getName().toUpperCase() + "_INPUT_ENTITY", inputEntity);
    }

    /**
     * 取得列表画面标志位
     *
     * @return true:列表画面
     */
    protected Boolean isListPage() {
        Boolean isListPage = (Boolean) AppContext.getRequest().getAttribute(this.getClass().getName().toUpperCase() + "_IS_LIST_PAGE");
        if (isListPage == null) {
            isListPage = false;
        }
        return isListPage;
    }

    /**
     * 设置列表画面标志位
     *
     * @param isListPage true:列表画面
     */
    protected void setListPage(Boolean isListPage) {
        AppContext.getRequest().setAttribute(this.getClass().getName().toUpperCase() + "_IS_LIST_PAGE", isListPage);
    }

    /**
     * 取得输入画面标志位
     *
     * @return true:输入画面
     */
    protected Boolean isInputPage() {
        Boolean isInputPage = (Boolean) AppContext.getRequest().getAttribute(this.getClass().getName().toUpperCase() + "_IS_INPUT_PAGE");
        if (isInputPage == null) {
            isInputPage = false;
        }
        return isInputPage;
    }

    /**
     * 设置输入画面标志位
     *
     * @param isInputPage true:输入画面
     */
    protected void setInputPage(Boolean isInputPage) {
        AppContext.getRequest().setAttribute(this.getClass().getName().toUpperCase() + "_IS_INPUT_PAGE", isInputPage);
    }

    /**
     * 从Session中取得检索条件
     *
     * @return 检索条件
     */
    protected String getSessionSearchCondition() {
        return (String) this.getSession().getAttribute(this.getClass().getName().toUpperCase() + "_SEARCH_CONDITION");
    }

    /**
     * 向Session中设置检索条件
     *
     * @param conditionJson 检索条件
     */
    public void setSessionSearchCondition(String conditionJson) {
        if (StringUtils.equals(this.getRequest().getParameter("autoSaveSearchCondition"), "true")) {
            this.getSession().setAttribute(this.getClass().getName().toUpperCase() + "_SEARCH_CONDITION", conditionJson);
        }
    }

    /**
     * 从Session中取得分页条件
     *
     * @return 分页条件
     */
    public String getSessionSearchPager() {
        return (String) this.getSession().getAttribute(this.getClass().getName().toUpperCase() + "_SEARCH_PAGER");
    }

    /**
     * 向Session中设置分页条件
     *
     * @param pagerJson 分页条件
     */
    public void setSessionSearchPager(String pagerJson) {
        if (StringUtils.equals(this.getRequest().getParameter("autoSaveSearchCondition"), "true")) {
            this.getSession().setAttribute(this.getClass().getName().toUpperCase() + "_SEARCH_PAGER", pagerJson);
        }
    }

    /**
     * 取得Service实例[抽象方法]
     *
     * @return BaseService
     */
    public abstract BaseService<T> getService();

    /**
     * 取得数据一览JSP页面
     *
     * @return 数据一览JSP页面
     */
    public String getListJsp() {
        return this.getRequestMappingPath() + "/" + this.getEntityName() + "List";
    }

    /**
     * 取得数据输入JSP页面
     *
     * @return 数据输入JSP页面
     */
    public String getInputJsp() {
        return this.getRequestMappingPath() + "/" + this.getEntityName() + "Input";
    }

    /**
     * 取得数据导入JSP页面
     *
     * @return 数据导入JSP页面
     */
    public String getImportJsp() {
        return this.getRequestMappingPath() + "/" + this.getEntityName() + "Import";
    }

    /**
     * 是否是数据添加模式
     *
     * @return true:添加模式
     */
    protected boolean isAddMode() {
        if (this.getInputEntity() == null) {
            return true;
        }

        Object pkValue = EntityUtils.getPkValue(this.getInputEntity());
        if (pkValue == null) {
            return true;
        }
        if (pkValue instanceof Long && ((Long) pkValue).longValue() == 0) {
            return true;
        }
        if (pkValue instanceof Integer && ((Integer) pkValue) == 0) {
            return true;
        }
        if (pkValue instanceof BigInteger && ((BigInteger) pkValue).intValue() == 0) {
            return true;
        }
        if (pkValue instanceof String && StringUtils.isEmpty((String) pkValue)) {
            return true;
        }
        if (this.getInputEntity().isAddMode()) {
            return true;
        }

        return false;
    }

    /**
     * 是否是数据编辑模式
     *
     * @return true:编辑模式
     */
    protected boolean isEditMode() {
        return !this.isAddMode();
    }

    /**
     * 是否使用JqueryUI？
     *
     * @param useJqueryUI 是否使用JqueryUI(true：使用)
     */
    protected void setUseJqueryUI(boolean useJqueryUI) {
        AppContext.getRequest().setAttribute("useJqueryUI", useJqueryUI);
    }

    /**
     * 是否使用UEditor富文本编辑？
     *
     * @param useUEditor 是否使用UEditor(true：使用)
     */
    protected void setUseUEditor(boolean useUEditor) {
        AppContext.getRequest().setAttribute("useUEditor", useUEditor);
    }

    /**
     * 是否使用UEditor富文本编辑？
     *
     * @param useUEditor 是否使用UEditor(true：使用)
     * @param configPath UEditor配置文件路径
     */
    protected void setUseUEditor(boolean useUEditor, String configPath) {
        AppContext.getRequest().setAttribute("useUEditor", useUEditor);
        AppContext.getRequest().setAttribute("configPath", configPath);
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
    @RequestMapping(value = {"list", ""}, method = RequestMethod.GET, name = "一览")
    public String list(Model model) throws IllegalAccessException, IOException, InstantiationException, ServiceException {
        //设置数据列表画面全局标识变量
        this.setListPage(true);
        this.setInputPage(false);

        long startTime = System.currentTimeMillis();
        //初期化列表画面
        this.initListPage(model);
        if (log.isDebugEnabled()) {
            log.debug(String.format("%s处理时间：%s ms", "BaseControl.initListPage", (System.currentTimeMillis() - startTime)));
            startTime = System.currentTimeMillis();
        }

        //初期化编辑画面
        this.initInputPage(model);
        if (log.isDebugEnabled()) {
            log.debug(String.format("%s处理时间：%s ms", "BaseControl.initInputPage", (System.currentTimeMillis() - startTime)));
            startTime = System.currentTimeMillis();
        }

        //下拉框选项内容做成
        this.initOptionList(model);
        if (log.isDebugEnabled()) {
            log.debug(String.format("%s处理时间：%s ms", "BaseControl.initOptionList", (System.currentTimeMillis() - startTime)));
            startTime = System.currentTimeMillis();
        }

        //返回数据一览JSP页面
        String listPage = this.getListJsp();
        if (log.isDebugEnabled()) {
            log.debug(String.format("%s处理时间：%s ms", "BaseControl.getListJsp", (System.currentTimeMillis() - startTime)));
        }

        //初期化自定义设置相关默认值
        this.initCustomSetting(model);

        //是否同步数据的页面
        model.addAttribute("isSyncDataPage", syncDataService.isSyncData(this.getEntityClass().getName()));

        return listPage;
    }

    /**
     * 初期化自定义设置相关默认值
     *
     * @param model Model对象
     */
    protected void initCustomSetting(Model model) {
        //自定义列设置信息
        model.addAttribute("jsonColumnSetting_" + this.getEntityName(), this.escapeHtml(this.getColumnSetting()));

        //自定义检索条件设置
        model.addAttribute("jsonSearchSetting_" + this.getEntityName(), this.escapeHtml(this.getSearchSetting()));

        //自定义区块设置
        model.addAttribute("jsonBlockSetting_" + this.getEntityName(), this.escapeHtml(this.getBlockSetting()));

        //自定义分页大小
        model.addAttribute("pageSize", this.getPageSize());
    }

    /**
     * 初期化自定义设置相关默认值
     *
     * @param model          Model对象
     * @param jsListInstance 列表画面JS实例对象字符串
     */
    protected void initCustomSetting(Model model, String jsListInstance) {
        //自定义列设置信息
        model.addAttribute("jsonColumnSetting_" + this.getEntityName(), this.escapeHtml(this.getColumnSetting(jsListInstance)));

        //自定义检索条件设置
        model.addAttribute("jsonSearchSetting_" + this.getEntityName(), this.escapeHtml(this.getSearchSetting(jsListInstance)));

        //自定义分页大小
        model.addAttribute("pageSize", this.getPageSize(jsListInstance));
    }

    /**
     * 数据输入画面初期化
     *
     * @param model Model对象
     * @param uid   主键UID
     * @return 数据输入JSP页面
     * @throws IOException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    @RequestMapping(value = "input", method = RequestMethod.GET, name = "新增/编辑")
    public String input(Model model, @RequestParam(required = false) String uid) throws IllegalAccessException, IOException, InstantiationException {
        //是否同步数据的页面
        model.addAttribute("isSyncDataPage", syncDataService.isSyncData(this.getEntityClass().getName()));

        //设置数据输入画面全局标识变量
        this.setInputPage(true);
        this.setListPage(false);

        //初期化编辑画面
        this.initInputPage(model, uid);

        //下拉框选项内容做成
        this.initOptionList(model);

        //返回数据输入JSP页面
        return this.getInputJsp();
    }

    /**
     * 初期化编辑画面
     *
     * @param model Model对象
     * @throws IllegalAccessException
     * @throws IOException
     * @throws InstantiationException
     */
    public void initInputPage(Model model) throws IllegalAccessException, IOException, InstantiationException {
        //初始化InputEntity实体对象
        this.initInputEntity(model, null);
    }

    /**
     * 初期化编辑画面
     *
     * @param model Model对象
     * @param uid   主键UID
     * @throws IllegalAccessException
     * @throws IOException
     * @throws InstantiationException
     */
    public void initInputPage(Model model, String uid) throws IllegalAccessException, IOException, InstantiationException {
        //初始化InputEntity实体对象
        this.initInputEntity(model, uid);
    }

    /**
     * 初始化InputEntity实体对象
     *
     * @param model Model对象
     * @param uid   主键UID
     * @throws IllegalAccessException
     * @throws IOException
     * @throws InstantiationException
     */
    protected void initInputEntity(Model model, String uid) throws IllegalAccessException, IOException, InstantiationException {
        //初始化InputEntity实体对象[前处理]
        this.initInputEntityBefore(model, uid);

        //UID不存在的场合
        if (StringUtils.isEmpty(uid)) {
            this.setInputEntity(this.getEntityClass().newInstance());
        }
        else {
            //根据UID取得实体对象
            this.setInputEntity(this.getService().getDetail(uid));
            if (this.getInputEntity() != null) {
                this.getInputEntity().setEditMode(1);
            }
            //实体对象不存在的场合
            else {
                this.setInputEntity(this.getEntityClass().newInstance());
            }
        }

        //初始化InputEntity实体对象[后处理]
        this.initInputEntityAfter(model, uid);

        //设置数据模型JSON字符
        this.setInputEntityToModel(model);
    }

    /**
     * 初始化InputEntity实体对象[前处理]
     *
     * @param model Model对象
     * @param uid   主键UID
     */
    protected void initInputEntityBefore(Model model, String uid) {

    }

    /**
     * 初始化InputEntity实体对象[后处理]
     *
     * @param model Model对象
     * @param uid   主键UID
     */
    protected void initInputEntityAfter(Model model, String uid) {

    }

    /**
     * 设置数据模型JSON字符
     *
     * @param model Model对象
     * @throws IllegalAccessException
     * @throws IOException
     * @throws InstantiationException
     */
    protected void setInputEntityToModel(Model model) throws IllegalAccessException, IOException, InstantiationException {
        //设置数据模型JSON字符
        model.addAttribute("jsonInputEntity_" + this.getEntityName(), this.toJSON(this.getInputEntity(), true));
    }

    /**
     * 初期化列表画面
     *
     * @param model Model对象
     * @throws IllegalAccessException
     * @throws IOException
     */
    public void initListPage(Model model) throws IllegalAccessException, IOException, ServiceException {
        this.initListPage(model, this.getService().getSearchSQL(null));
    }

    /**
     * 初期化列表画面
     *
     * @param model Model对象
     * @param sql   查询SQL文
     * @throws IllegalAccessException
     * @throws IOException
     */
    public void initListPage(Model model, String sql) throws IllegalAccessException, IOException, ServiceException {
        String conditionJson = this.getSessionSearchCondition();

        //检索条件存在的场合
        if (StringUtils.isNotEmpty(conditionJson)) {
            boolean isForceRememberSearchCondition = (this.isEntryFromMenu() && this.isForceRememberSearchCondition());

            if (!this.isEntryFromMenu() || isForceRememberSearchCondition) {
                //上次检索条件[一览画面检索条件恢复用]
                model.addAttribute("jsonSearchCondition_" + this.getEntityName(), this.escapeHtml(conditionJson));
            }

            if (!this.isEntryFromMenu()) {
                //取得分页条件
                PageRequest pager = this.getPageRequest(this.getPager(this.getSessionSearchPager()));

                //取得查询条件
                Map<String, Object> condition = this.getCondition(conditionJson);
                //列表一览数据
                Page<T> listData = this.search(sql, condition, pager);

                //设置一览JSON数据
                model.addAttribute("jsonDataList_" + this.getEntityName(), this.toJSON(listData, true));
            }
        }
    }

    /**
     * 初始化下拉框选项内容
     *
     * @param model Model对象
     * @throws IllegalAccessException
     * @throws IOException
     */
    public void initOptionList(Model model) throws IllegalAccessException, IOException {

    }

    /**
     * 是否从菜单进入画面？
     *
     * @return true:是
     */
    public boolean isEntryFromMenu() {
        return StringUtils.equals(this.getRequest().getParameter("entry"), "menu");
    }

    /**
     * 是否强制记忆检索条件？
     *
     * @return true:是
     */
    public boolean isForceRememberSearchCondition() {
        return StringUtils.equals(this.getRequest().getParameter("frsc"), "true");
    }

    /**
     * 根据主键UID查询实体详细
     *
     * @param uid 主键UID
     * @return 实体对象
     */
    @RequestMapping(value = "{uid}", method = RequestMethod.GET, name = "详细")
    public T findOne(@PathVariable String uid) {
        return getService().findOne(uid);
    }

    /**
     * 根据主键UID查询实体详细
     *
     * @param uid 主键UID
     * @return 实体对象
     */
    @ResponseBody
    @RequestMapping(value = "/getDetail", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, name = "详细")
    public Object getDetail(@RequestParam String uid) {
        return getService().getDetail(uid);
    }

    /**
     * 数据导出
     *
     * @param conditionJson 查询条件JSON字符串
     * @param colList       列信息JSON字符串
     * @param fileName      文件名称
     * @param extensionName 文件扩展名称
     * @return 字节流
     */
    @RequestMapping(value = "export", method = RequestMethod.POST, name = "导出")
    public ResponseEntity<byte[]> export(@RequestParam String conditionJson, @RequestParam String colList, @RequestParam String fileName, @RequestParam(defaultValue = "xlsx") String extensionName) {
        try {
            //取得查询条件
            Map<String, Object> condition = this.getCondition(conditionJson);

            //导出最大记录数限制
            int exportMaxRecordCountLimit = this.parameterService.getIntValue(AppCodeConsts.APP_COMMON, "EXPORT_MAX_RECORD_COUNT_LIMIT", 150000);

            //取得分页条件
            PageRequest pageRequest = this.getPageRequest(this.getPager(this.getSessionSearchPager()));
            //指定导出记录数
            pageRequest = new PageRequest(0, exportMaxRecordCountLimit, pageRequest.getSort());
            //取得标准查询SQL文
            String sql = getService().getSearchSQL(condition);
            //是否返回map，默认为空，如果需要返回map，在对应jsp页面加上search-mode=ignore_search的隐含域（值一般设定为Y)
            String isReturnMap = (String) SearchConditionUtils.getConditionValue(condition, "isReturnMap");

            if (StringUtils.isNotEmpty(isReturnMap)) {
                return this.exportExcel(this.getService().searchMap(sql, condition, pageRequest, exportMaxRecordCountLimit).getContent(), colList, fileName, extensionName);
            }
            //一般返回对应页面实体时
            else {
                return this.exportExcel(this.getService().search(getEntityClass(), sql, condition, pageRequest, exportMaxRecordCountLimit).getContent(), colList, fileName, extensionName);
            }
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }

    /***
     * 导出Excel数据
     *
     * @param list          实体列表
     * @param colList       列信息JSON字符串
     * @param fileName      文件名称
     * @param extensionName 文件扩展名称
     * @return 字节流
     */
    public <E> ResponseEntity<byte[]> exportExcel(List<E> list, @RequestParam String colList, @RequestParam String fileName, @RequestParam(defaultValue = "xlsx") String extensionName) throws Exception {
        return exportExcel(list, colList, fileName, extensionName, null, 0);
    }

    /***
     * 导出Excel数据
     *
     * @param list               实体列表
     * @param colList            列信息JSON字符串
     * @param fileName           文件名称
     * @param extensionName      文件扩展名称
     * @param reportTemplatePath Excel模板文件路径
     * @param dataRowIndex       数据开始行号
     * @return 字节流
     */
    protected <E> ResponseEntity<byte[]> exportExcel(List<E> list, String colList, String fileName, String extensionName, String reportTemplatePath, int dataRowIndex) throws Exception {
        List<ExcelColInfo> defaultColList = JsonUtils.parseJSON(colList, new TypeReference<List<ExcelColInfo>>() {
        });

        //列表JS实例对象字符串
        String pageInstance = this.getRequest().getParameter("pageInstance");
        //同一个pageInstance 有多个模式时对应
        String mode = this.getRequest().getParameter("mode");
        if (StringUtils.isNoneBlank(mode)) {
            pageInstance = pageInstance + "_" + mode;
        }

        //自定义列导出标志位
        String exportCustomColList = this.getRequest().getParameter("exportCustomColList");

        //列表JS实例对象字符串存在的场合
        if (StringUtils.isEmpty(exportCustomColList) && StringUtils.isNotEmpty(pageInstance)) {
            //根据列表JS实例对象字符串取得自定义设置列信息
            List<String> columnSettingList = JsonUtils.parseJSON(this.columnSettingService.getColumnSetting(pageInstance), new TypeReference<List<String>>() {
            });

            //自定义列表示有设置的场合
            if (CollectionUtils.isNotEmpty(columnSettingList)) {
                List<ExcelColInfo> allColList = JsonUtils.parseJSON(getRequest().getParameter("allColList"), new TypeReference<List<ExcelColInfo>>() {
                });

                if (CollectionUtils.isNotEmpty(allColList)) {
                    //清空默认列表
                    defaultColList.clear();

                    //根据自定义设置列信息，动态设置需要导出的列集合
                    for (ExcelColInfo excelColInfo : allColList) {
                        //数据导出列（无条件导出）
                        if (excelColInfo.getOnlyForExport()) {
                            defaultColList.add(excelColInfo);
                            continue;
                        }

                        for (String columnSetting : columnSettingList) {
                            if (StringUtils.equals(excelColInfo.getColName(), columnSetting)) {
                                defaultColList.add(excelColInfo);
                            }
                        }
                    }
                }
            }
        }

        for (int i = 0; i < defaultColList.size(); i++) {
            ExcelColInfo colInfo = defaultColList.get(i);
            //移除操作列
            if (StringUtils.equals(colInfo.getColName(), "operation")) {
                defaultColList.remove(colInfo);
                i--;
            }

            //备注列特殊化处理
            if (StringUtils.equals(colInfo.getColName(), "remark")) {
                colInfo.setWidth("300px");
            }

            //标题中的%号特殊化处理
            colInfo.setTitle(colInfo.getTitle().replace("%25", "%"));
        }

        HttpHeaders headers = new HttpHeaders();

        ExcelExportTemplate<E> template = new ExcelExportTemplate();
        template.setSheetName(fileName);
        template.setColList(defaultColList);
        template.setContents(list);

        byte[] body;
        //标准数据导出的场合
        if (StringUtils.isEmpty(reportTemplatePath)) {
            body = ExcelUtils.exportFile(template);
        }
        //模板数据导出的场合
        else {
            XSSFWorkbook workbook = ExcelUtils.getExcelTemplate(reportTemplatePath);
            body = ExcelUtils.exportFile(template, workbook, dataRowIndex);
        }

        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.add("Content-Disposition", "attachment;filename=" + this.convertDownloadFileName(fileName) + "_" + UniqueUtils.getUID() + "." + extensionName);
        return new ResponseEntity<>(body, headers, HttpStatus.OK);
    }

    /**
     * 数据删除处理
     *
     * @param uid 删除主键集合(逗号分隔)
     * @return AjaxResult
     */
    @ResponseBody
    @RequestMapping(value = "delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, name = "删除")
    public AjaxResult delete(@RequestParam String uid) {
        //删除处理
        getService().delete(uid);

        return AjaxResult.createSuccessResult("数据删除成功。");
    }

    /**
     * 停用/启用处理
     *
     * @param uid          主键UID
     * @param recordStatus 记录状态
     * @return AjaxResult
     */
    @ResponseBody
    @RequestMapping(value = "/updateStatus", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, name = "停用/启用")
    public AjaxResult updateStatus(@RequestParam String uid, @RequestParam String recordStatus) {
        //停用处理
        if (StringUtils.equals(recordStatus, CmnConsts.RECORD_STATUS_ACTIVE)) {
            int result = this.getService().updateRecordStatus(uid, CmnConsts.RECORD_STATUS_STOP);
            if (result == 1) {
                return AjaxResult.createSuccessResult("停用处理成功。");
            }
            else {
                return AjaxResult.createSuccessResult("停用处理失败。");
            }
        }
        else {
            int result = this.getService().updateRecordStatus(uid, CmnConsts.RECORD_STATUS_ACTIVE);
            if (result == 1) {
                return AjaxResult.createSuccessResult("启用处理成功。");
            }
            else {
                return AjaxResult.createSuccessResult("启用处理失败。");
            }
        }
    }

    /**
     * 数据保存处理
     *
     * @param inputJson Json字符串
     * @return 处理结果
     */
    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, name = "保存")
    public Object save(@RequestParam String inputJson) {
        //输入画面InputJson字符串解析成CoreEntity对象
        T entity = this.parseInputJson(inputJson);

        //保存类型(1：保存/2：保存并提交)
        entity.setSaveType(this.getRequest().getParameter("saveType"));

        //数据保存处理
        getService().save(entity);

        return this.setSaveResult(entity);
    }

    /**
     * 输入画面InputJson字符串解析成CoreEntity对象
     *
     * @param inputJson Json字符串
     * @return CoreEntity对象
     */
    public T parseInputJson(String inputJson) {
        return JsonUtils.parseJSON(inputJson, this.getEntityClass());
    }

    /**
     * 设定保存处理结果
     *
     * @param entity 实体对象
     * @return AjaxResult
     */
    public AjaxResult setSaveResult(T entity) {
        return AjaxResult.createSuccessResult(EntityUtils.getPkValue(entity), "数据保存成功。", entity);
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
    @RequestMapping(value = "/search", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, name = "检索")
    public Page<T> search(@RequestParam(required = false) String pagerJson, @RequestParam(required = false) String conditionJson) throws IOException, IllegalAccessException, ServiceException {
        //取得查询条件
        Map<String, Object> condition = this.getCondition(conditionJson);

        //保存最新的检索条件
        this.setSessionSearchCondition(conditionJson);
        this.setSessionSearchPager(pagerJson);

        //取得标准查询SQL文
        String sql = getService().getSearchSQL(condition);

        //数据查询
        return this.search(sql, condition, this.getPageRequest(this.getPager(pagerJson)));
    }

    /**
     * 一览数据查询【无分页】
     *
     * @param pagerJson     分页JSON字符串
     * @param conditionJson 查询条件JSON字符串
     * @return 一览数据集
     * @throws IOException
     * @throws IllegalAccessException
     */
    @ResponseBody
    @RequestMapping(value = "searchNoPage", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, name = "检索（无分页）")
    public List<T> searchNoPage(@RequestParam(required = false) String pagerJson, @RequestParam(required = false) String conditionJson) throws IOException, IllegalAccessException, ServiceException {
        //取得查询条件
        Map<String, Object> condition = Maps.newHashMap();
        //有条件的场合
        if (StringUtils.isNotEmpty(conditionJson)) {
            //取得查询条件
            condition = getCondition(conditionJson);

            //保存最新的检索条件
            this.setSessionSearchCondition(conditionJson);
        }

        this.setSessionSearchPager(pagerJson);

        //取得标准查询SQL文
        String sql = getService().getSearchSQL(condition);

        //数据查询
        return search(sql, condition);
    }

    /**
     * 无任何检索条件默认分页排序查询
     *
     * @return 一览数据集
     */
    public Page<T> search() {
        return getService().search(this.getEntityClass(), getService().getSearchSQL(null), null, this.getDefaultPageRequest());
    }

    /**
     * 无任何检索条件分页排序查询
     *
     * @param pager 分页排序对象
     * @return 一览数据集
     */
    public Page<T> search(PageRequest pager) {
        return getService().search(this.getEntityClass(), getService().getSearchSQL(null), null, pager);
    }

    /**
     * 指定检索条件分页排序查询
     *
     * @param condition 原始检索条件集合
     * @param pager     分页排序对象
     * @return 一览数据集
     */
    public Page<T> search(Map<String, Object> condition, PageRequest pager) {
        //取得标准查询SQL文
        String sql = getService().getSearchSQL(null);

        return getService().search(this.getEntityClass(), sql, condition, pager);
    }

    /**
     * 查询记录件数
     * [注：根据记录件数查询专用SQL文查询记录件数]
     *
     * @param sql       记录件数查询专用SQL
     * @param condition 原始检索条件集合
     * @return 记录件数
     */
    public int searchRecordCount(String sql, Map<String, Object> condition) {
        return getService().searchRecordCount(sql, condition);
    }

    /**
     * 指定SQL的检索条件分页排序查询
     *
     * @param sql       查询SQL文
     * @param condition 原始检索条件集合
     * @param pager     分页排序对象
     * @return 一览数据集
     */
    public Page<T> search(String sql, Map<String, Object> condition, PageRequest pager) {
        return getService().search(this.getEntityClass(), sql, condition, pager);
    }

    /**
     * 指定SQL的检索条件分页排序查询
     *
     * @param sql         查询SQL文
     * @param condition   原始检索条件集合
     * @param pager       分页排序对象
     * @param recordCount 记录件数
     * @return 一览数据集
     */
    public Page<T> search(String sql, Map<String, Object> condition, PageRequest pager, int recordCount) {
        return getService().search(this.getEntityClass(), sql, condition, pager, recordCount);
    }

    /**
     * 列表查询
     *
     * @param sql 查询SQL文
     * @return 实体对象列表
     */
    public List<T> search(String sql) {
        return this.getService().search(this.getEntityClass(), sql, null);
    }

    /**
     * 列表查询
     *
     * @param sql       查询SQL文
     * @param condition 原始检索条件集合
     * @return 实体对象列表
     */
    public List<T> search(String sql, Map<String, Object> condition) {
        return this.getService().search(this.getEntityClass(), sql, condition);
    }

    /**
     * 列表查询
     *
     * @param sql       查询SQL文
     * @param condition 原始检索条件集合
     * @param sort      排序对象
     * @return 实体对象列表
     */
    public List<T> search(String sql, Map<String, Object> condition, Sort sort) {
        return this.getService().search(this.getEntityClass(), sql, condition, sort);
    }

    /**
     * 取得默认分页对象
     *
     * @return 分页排序对象
     */
    public PageRequest getDefaultPageRequest() {
        Sort sort = new Sort(Sort.Direction.DESC, EntityUtils.getPkName(this.getEntityClass()) + CmnConsts.CAMEL_FIELD_FLAG);
        return new PageRequest(1, 10, sort);
    }

    /**
     * 根据分页JSON字符串构建分页Map对象
     */
    protected Map getPager(String pagerJson) {
        return JsonUtils.parseJSON(pagerJson, Map.class);
    }

    /**
     * 根据检索条件JSON字符串构建检索条件Map对象
     *
     * @param conditionJson 检索条件JSON字符串
     * @return 建检索条件Map对象
     */
    public Map<String, Object> getCondition(String conditionJson) {
        Map<String, Object> condition = JsonUtils.parseJSON(conditionJson, Map.class);

        //需要自定义拼接SQL条件的场合
        if (SearchConditionUtils.isNeedPrepareSql(condition)) {
            customSearchCondition(condition);
        }
        //去掉空值key
        Map<String, Object> result = Maps.newHashMap(condition);
        for (String key : condition.keySet()) {
            if (condition.get(key) == null || Objects.equals(condition.get(key), StringUtils.EMPTY)) {
                result.remove(key);
            }
        }
        return result;
    }

    /**
     * 客户化自定义检索条件
     *
     * @param condition 检索条件
     */
    protected void customSearchCondition(Map<String, Object> condition) {

    }

    /**
     * 根据分页Map对象构建分页实体对象
     *
     * @param pager 分页Map对象
     * @return 分页实体对象
     */
    @SuppressWarnings({"rawtypes"})
    protected PageRequest getPageRequest(Map pager) {
        //排序字段
        String sortColumn = pager.get("sortColumn") == null ? null : String.valueOf(pager.get("sortColumn"));

        //默认排序对象
        Sort pageSort = this.getDefaultSort();

        //排序字段非空的场合
        if (!StringUtils.isEmpty(sortColumn) && !StringUtils.equals(sortColumn, "undefined")) {
            //排序方式
            String sortMode = String.valueOf(pager.get("sortMode"));

            //默认升序排列
            pageSort = new Sort(Sort.Direction.DESC, sortColumn);

            //降序排列的场合
            if (StringUtils.isNotEmpty(sortMode) && StringUtils.equals(sortMode.toLowerCase(), "asc")) {
                pageSort = new Sort(Sort.Direction.ASC, sortColumn);
            }
        }

        //页码
        int pageNum = Integer.valueOf(String.valueOf(pager.get("pageNum"))) - 1;

        //每页记录数
        int pageSize = Integer.valueOf(String.valueOf(pager.get("pageSize")));
        if (pageSize == 0) {
            pageSize = CmnConsts.PAGE_SIZE;
        }

        //无需分页的场合
        if (pageSize == -1) {
            pageSize = Integer.MAX_VALUE;
        }

        //强制排序字段存在的场合
        if (this.getForceSortField() != null) {
            pageSort = pageSort.and(this.getForceSortField());
        }

        return new PageRequest(pageNum, pageSize, pageSort);
    }

    /**
     * 取得默认排序字段[子类可覆盖]
     *
     * @return 默认排序字段
     */
    public Sort getDefaultSort() {
        //默认按UID排序
        return new Sort(Sort.Direction.DESC, EntityUtils.getPkName(this.getEntityClass()) + CmnConsts.CAMEL_FIELD_FLAG);
    }

    /**
     * 取得强制排序字段
     *
     * @return 强制排序字段
     */
    public Sort getForceSortField() {
        return null;
    }

    /**
     * 数据重复性校验[Ajax专用方法]
     *
     * @param pkName               主键字段名称
     * @param pkValue              主键字段值
     * @param relationFieldName    关联外键字段名称
     * @param relationFieldValue   关联外键字段值
     * @param targetFieldName      被校验目标字段名称
     * @param targetFieldValue     被校验目标字段值
     * @param recordStatusIsActive 数据是否有效
     * @param targetFieldDesc      被校验目标字段描述
     * @return AjaxResult
     */
    @ResponseBody
    @RequestMapping(value = "validateDuplication", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, name = "重复校验")
    public AjaxResult validateDuplication(@RequestParam(required = false) String pkName, @RequestParam(required = false) String pkValue, @RequestParam(required = false) String relationFieldName,
                                          @RequestParam(required = false) String relationFieldValue, @RequestParam(required = false) String targetFieldName, @RequestParam(required = false) String targetFieldValue,
                                          @RequestParam(required = false) String targetFieldDesc, @RequestParam(required = false) Boolean recordStatusIsActive) {
        if (this.getService().isDuplication(pkName, pkValue, relationFieldName, relationFieldValue, targetFieldName, targetFieldValue, recordStatusIsActive)) {
            return AjaxResult.createFailResult(String.format("指定的[%s]已存在，请重新输入。", targetFieldDesc));
        }
        else {
            return AjaxResult.createSuccessResult("");
        }
    }

    /**
     * 取得实体类名[子类可覆盖]
     *
     * @return 实体类
     */
    @SuppressWarnings("rawtypes")
    protected Class<T> getEntityClass() {
        return this.getService().getEntityClass();
    }

    /**
     * 取得实体类名称[不带package路径]
     *
     * @return 实体类名称
     */
    protected String getEntityName() {
        return EntityUtils.getEntityName(this.getEntityClass());
    }

    /**
     * 取得指定表、指定字段数据列表【下拉框内容填充专用】
     *
     * @param tableName  表名称
     * @param valueField 下拉框值字段
     * @param textField  下拉框文本字段
     * @param condition  检索条件
     * @return 数据列表
     */
    public List<DropdownEntity> getDropdownList(String tableName, String valueField, String textField, Map<String, Object> condition) {
        return this.getService().getDropdownList(tableName, valueField, textField, condition);
    }

    /**
     * 取得指定表、指定字段数据列表【下拉框内容填充专用】
     *
     * @param tableName  表名称
     * @param valueField 下拉框值字段
     * @param textField  下拉框文本字段
     * @param condition  检索条件
     * @param orderBy    排序条件
     * @return 数据列表
     */
    public List<DropdownEntity> getDropdownList(String tableName, String valueField, String textField, Map<String, Object> condition, String orderBy) {
        return this.getService().getDropdownList(tableName, valueField, textField, condition, orderBy);
    }

    /**
     * 取得指定表、指定字段数据列表【下拉框内容填充专用】
     *
     * @param tableName  表名称
     * @param valueField 下拉框值字段(多字段合并显示时以 | 或者 - 分隔，譬如要取id 和uid 2个作为value值时，写为 id|uid，或者id-uid）
     * @param textField  下拉框文本字段（多字段合并显示时以 | 或者 - 分隔）
     * @return 数据列表
     */
    public List<DropdownEntity> getDropdownList(String tableName, String valueField, String textField) {
        return this.getService().getDropdownList(tableName, valueField, textField);
    }

    /**
     * 取得指定表、指定字段数据列表【下拉框内容填充专用】
     *
     * @param tableName  表名称
     * @param valueField 下拉框值字段
     * @param textField  下拉框文本字段
     * @param orderBy    排序条件
     * @return 数据列表
     */
    public List<DropdownEntity> getDropdownList(String tableName, String valueField, String textField, String orderBy) {
        return this.getService().getDropdownList(tableName, valueField, textField, orderBy);
    }

    /**
     * 取得控制器RequestMapping路径
     *
     * @return RequestMapping路径
     */
    protected String getRequestMappingPath() {
        Annotation[] annotations = getClass().getAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation instanceof RequestMapping) {
                return ((RequestMapping) annotation).value()[0];
            }
        }
        return StringUtils.EMPTY;
    }

    /**
     * 附件文件下载中文文件名称转换（如：GBK转换为ISO-8859-1）
     *
     * @param fileName 文件名称
     * @return 转换后的文件名称
     */
    public String convertDownloadFileName(String fileName) {
        if (StringUtils.isEmpty(fileName)) {
            return fileName;
        }
        try {
            String userAgent = this.getRequest().getHeader("user-agent");

            //Edge 浏览器的场合
            if (userAgent.toLowerCase().contains("edge")) {
                return new String(fileName.getBytes("GBK"), "ISO-8859-1");
            }
            //Firefox 与 Chrome 浏览器的场合
            else if (userAgent.toLowerCase().contains("firefox") || userAgent.toLowerCase().contains("chrome")) {
                return new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
            }
            //IE浏览器的场合
            else {
                return new String(fileName.getBytes("GBK"), "ISO-8859-1");
            }
        } catch (UnsupportedEncodingException ex) {
            return fileName;
        }
    }

    /**
     * 根据大区分CD取得字典列表数据
     *
     * @param mainCd 大区分CD
     * @return 字典列表数据
     */
    public List<DropdownEntity> getDictionaryList(String mainCd) {
        return this.dictionaryService.getDictionaryList(mainCd);
    }

    /**
     * 取得当前用户Subject
     *
     * @return Subject
     */
    public Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    /**
     * 取出Shiro中的当前用户信息
     *
     * @return 当前用户信息
     */
    public ShiroUser getCurrentUser() {
        return (ShiroUser) this.getSubject().getPrincipal();
    }

    /**
     * 取出Shiro中的当前用户UID.
     *
     * @return 当前用户UID
     */
    public String getCurrentUserId() {
        return getCurrentUser().getUserUid();
    }

    /**
     * 同步指定表的接口数据
     *
     * @param tableName 表名称
     * @return AjaxResult
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping(value = "/syncData", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, name = "数据同步")
    public AjaxResult syncData(@RequestParam String tableName) throws IOException {
        if (StringUtils.isEmpty(tableName)) {
            return AjaxResult.createFailResult("数据同步失败(未指定同步表名称)。");
        }

        //接口数据同步
        boolean result = this.getService().syncData(tableName, this.getEntityClass());

        if (result) {
            return AjaxResult.createSuccessResult("数据同步成功。");
        }
        else {
            return AjaxResult.createFailResult("数据同步失败。");
        }
    }

    /**********************************
     * 快捷方法定义
     **********************************/
    public String escapeHtml(String value) {
        return StringEscapeUtils.escapeHtml4(value);
    }

    public String toJSON(Object object) throws JsonProcessingException {
        return this.toJSON(object, false);
    }

    public String toJSON(Object object, boolean isEscapeHtml) throws JsonProcessingException {
        if (isEscapeHtml) {
            return this.escapeHtml(JsonUtils.toJSON(object));
        }
        return JsonUtils.toJSON(object);
    }

    /**
     * 取得自定义一览列设置
     *
     * @return 自定义一览列JSON字符串
     */
    public String getColumnSetting() {
        return this.columnSettingService.getColumnSetting(getJsListInstance());
    }

    /**
     * 取得自定义一览列设置
     *
     * @param jsListInstance 列表画面JS实例对象字符串
     * @return 自定义一览列JSON字符串
     */
    public String getColumnSetting(String jsListInstance) {
        return this.columnSettingService.getColumnSetting(jsListInstance);
    }

    /**
     * 取得自定义检索条件设置
     *
     * @return 自定义检索条件设置JSON字符串
     */
    public String getSearchSetting() {
        try {
            return JsonUtils.toJSON(customSettingService.getCustomSetting(getJsListInstance(), 2));
        } catch (JsonProcessingException e) {
            return StringUtils.EMPTY;
        }
    }

    /**
     * 取得自定义检索条件设置
     *
     * @param jsListInstance 列表画面JS实例对象字符串
     * @return 自定义检索条件设置JSON字符串
     */
    public String getSearchSetting(String jsListInstance) {
        try {
            return JsonUtils.toJSON(customSettingService.getCustomSetting(jsListInstance, 2));
        } catch (JsonProcessingException e) {
            return StringUtils.EMPTY;
        }
    }

    /**
     * 取得自定义分页大小
     *
     * @return 分页大小
     */
    public Integer getPageSize() {
        List<CustomSettingExt> customSettingList = customSettingService.getCustomSetting(getJsListInstance(), 3);
        return customSettingList.isEmpty() ? CmnConsts.PAGE_SIZE : NumberUtils.toInt(customSettingList.get(0).getParameters(), CmnConsts.PAGE_SIZE);
    }

    /**
     * 取得自定义分页大小
     *
     * @param jsListInstance 列表画面JS实例对象字符串
     * @return 分页大小
     */
    public Integer getPageSize(String jsListInstance) {
        List<CustomSettingExt> customSettingList = customSettingService.getCustomSetting(jsListInstance, 3);
        return customSettingList.isEmpty() ? CmnConsts.PAGE_SIZE : NumberUtils.toInt(customSettingList.get(0).getParameters(), CmnConsts.PAGE_SIZE);
    }

    /**
     * 取得List画面JS实例对象字符串
     * (如：SysApp.Cm.ContractListIns)
     *
     * @return List画面JS实例对象字符串
     */
    protected String getJsListInstance() {
        //应用编号
        String[] names = this.getEntityClass().getName().split("\\.");
        String appCode = names[2];
        appCode = appCode.substring(0, 1).toUpperCase() + appCode.substring(1);

        return String.format("SysApp.%s.%sListIns", appCode, this.getEntityName());
    }

    /**
     * 取得自定义区块设置
     *
     * @return 自定义区块设置JSON字符串
     */
    public String getBlockSetting() {
        try {
            List<CustomSettingExt> blockSettingList = customSettingService.getCustomSetting(getJsListInstance(), 4);
            if (blockSettingList.size() > 0) {
                return JsonUtils.toJSON(blockSettingList);
            }
        } catch (JsonProcessingException e) {
            return StringUtils.EMPTY;
        }
        return StringUtils.EMPTY;
    }

    /**
     * 取得自定义区块设置
     *
     * @param jsListInstance 列表画面JS实例对象字符串
     * @param model          Model对象
     * @return 自定义区块设置JSON字符串
     */
    public String getBlockSetting(String jsListInstance, Model model) {
        try {
            List<CustomSettingExt> blockSettingList = customSettingService.getCustomSetting(jsListInstance, 4);
            if (blockSettingList.size() > 0) {
                CustomSettingExt blockSetting = blockSettingList.get(0);

                //获取参数
                JSONArray blockSettingArray = JSONObject.parseArray(blockSetting.getParameters());

                //循环处理参数
                for (int i = 0; i < blockSettingArray.size(); i++) {
                    JSONObject jsonObject = blockSettingArray.getJSONObject(i);
                    model.addAttribute((String) jsonObject.get("id"), jsonObject.get("checked"));
                }
                return JsonUtils.toJSON(blockSettingList);
            }
        } catch (JsonProcessingException e) {
            return StringUtils.EMPTY;
        }
        return StringUtils.EMPTY;
    }

    /**
     * 保存附件文件
     *
     * @param file       附件文件
     * @param attachment 实体对象
     * @return 实体对象
     */
    protected AttachmentImportExt saveAttachment(MultipartFile file, AttachmentImportExt attachment) {
        return this.getService().saveAttachment(file, attachment);
    }

    /**
     * 文件下载 （主要用于批量导入时excel 模板）
     *
     * @param absolutePath  文件存放绝对路径
     * @param physicalFile  模板文件名（带工程目录的相对路径）
     * @param logicFileName 伦理显示用文件名
     * @param mimeType      MIME类型
     * @return 文件体response
     * @throws IOException
     */
    private ResponseEntity<byte[]> downloadFile(String absolutePath, String physicalFile, String logicFileName, String mimeType) throws IOException {
        try {
            if (log.isInfoEnabled()) {
                log.info("absolutePath={} ", absolutePath);
            }
            //创建文件流
            InputStream inputStream = new FileInputStream(absolutePath + physicalFile);
            OutputStream outputStream = this.getResponse().getOutputStream();
            String fileName = this.convertDownloadFileName(logicFileName);
            //设置输出的格式
            this.getResponse().reset();
            this.getResponse().setContentType(mimeType);
            this.getResponse().setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

            //循环取出流中的数据
            byte[] buffer = new byte[1024];
            int len;
            //读取数据流
            while ((len = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, len);
            }

            //关闭流
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(outputStream);
        } catch (IOException e) {
            //报错  重定向FileNotFound页面
            this.getResponse().sendRedirect("/error/FileNotFound");
            if (log.isErrorEnabled()) {
                log.error("文件下载出错，文件可能不存在。", e);
            }
            return null;
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 模板文件下载 （主要用于批量导入时excel 模板）
     *
     * @param physicalFile  模板文件名（带工程目录的相对路径）
     * @param logicFileName 伦理显示用文件名
     * @return
     * @throws IOException
     */
    protected ResponseEntity<byte[]> downloadTemplateFile(String physicalFile, String logicFileName) throws IOException {
        return this.downloadFile(AppContext.getAppPath(), physicalFile, logicFileName, "application/octet-stream");
    }

    /**
     * 附件文件下载
     *
     * @param fileName 文件名称
     * @param filePath 文件路径
     * @param mimeType MIME类型
     * @return ResponseEntity
     * @throws IOException
     */
    protected ResponseEntity<byte[]> downloadFile(String fileName, String filePath, String mimeType) throws IOException {
        //系统中文件上传的根目录 取得
        String uploadPath = this.parameterService.getValue(AppCodeConsts.APP_COMMON, "FILE_UPLOAD_PATH", this.getRequest().getServletContext().getRealPath("/"));
        return this.downloadFile(uploadPath, filePath, fileName, mimeType);
    }

    /**
     * 自动拼装用户坐席以及校验密码
     *
     * @return 用户实体(带有用户分机号, 坐席, 坐席密码)
     */
    public User ctiLogin() {
        UserExt user = this.userService.getDetail(getCurrentUser().getUserUid());
        //如果用户分机号不为空，用户坐席为空，则构造用户坐席号（例NormalAgent61147）
        if (StringUtils.isNotEmpty(user.getUserExtensionNumber()) && StringUtils.isEmpty(user.getUserAgent())) {
            user.setUserAgent("NormalAgent6" + user.getUserExtensionNumber().substring(1));
            //如果用户坐席密码为空，则设置密码为000000
            if (StringUtils.isEmpty(user.getUserAgentPassword())) {
                user.setUserAgentPassword("000000");
            }
            userService.getDao().save(user);
        }
        return user;
    }

    /**
     * 获取新增模式带UID的空实体对象
     *
     * @return Entity实体对象
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "getEmptyEntity", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, name = "获取带UID的对象")
    public T getEmptyEntity() throws Exception {
        T entity = this.getEntityClass().newInstance();
        //新增模式
        entity.setEditMode(0);
        ((BaseEntity) entity).setUid(UniqueUtils.getUID());
        return entity;
    }

    /**
     * 根据用户list集合转化成下拉框需要数据集合
     *
     * @param userList 用户list集合
     * @return 下拉框数据list集合
     */
    public List<DropdownEntity> getDropdownList(List<UserExt> userList) {
        //只显示用户名称，不显示完整的部门信息
        return this.getDropdownList(userList, false);
    }

    /**
     * 根据用户list集合转化成下拉框需要数据集合
     *
     * @param userList         用户list集合
     * @param showFullDeptName 是否显示完整的部门信息
     * @return 下拉框数据list集合
     */
    public List<DropdownEntity> getDropdownList(List<UserExt> userList, boolean showFullDeptName) {
        List<DropdownEntity> userListDropdown = new ArrayList<>();
        for (UserExt user : userList) {
            DropdownEntity dropdown = new DropdownEntity();
            //显示完整的部门信息
            if (showFullDeptName) {
                dropdown.setSubName(user.getUserName() + "-" + user.getDeptFullName());
            }
            //不显示完整部门信息，只显示用户名称
            else {
                dropdown.setSubName(user.getUserName());
            }
            dropdown.setSubCd(user.getUid());
            userListDropdown.add(dropdown);
        }
        return userListDropdown;
    }

    /**
     * 系统建设中画面
     */
    @RequestMapping(value = "doing", method = RequestMethod.GET)
    public String doing(Model model) {
        return "Doing";
    }
}