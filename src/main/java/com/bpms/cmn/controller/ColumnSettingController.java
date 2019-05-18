package com.bpms.cmn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 显示列设置控制器类
 */
@Controller(value = "CmnColumnSettingController")
@RequestMapping("/cmn/columnsetting")
public class ColumnSettingController extends com.bpms.sys.controller.ColumnSettingController {

}
