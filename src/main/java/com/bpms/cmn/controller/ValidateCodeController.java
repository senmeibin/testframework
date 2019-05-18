package com.bpms.cmn.controller;

import com.bpms.cmn.utility.ValidateCodeUtils;
import com.bpms.core.entity.AjaxResult;
import com.bpms.core.utils.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 图形验证码控制器
 */
@Controller
@RequestMapping("/cmn/validatecode")
public class ValidateCodeController {
    /**
     * 获取图形验证码
     * 前端可传递w：图形码宽度，h：图形码高度，l：验证码长度
     *
     * @param request  HttpServletRequest对象
     * @param response HttpServletResponse对象
     * @return 图形码图片流
     * @throws Exception
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<byte[]> validateCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //设置页面不缓存
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        try {
            //输出图象到页面
            ImageIO.write(ValidateCodeUtils.createValidateCode(request), "JPEG", response.getOutputStream());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 校验验证码是否正确
     *
     * @param validateCode 验证码
     * @param request      HttpServletRequest对象
     * @param response     HttpServletResponse对象
     * @return AjaxResult对象
     */
    public AjaxResult checkValidateCode(String validateCode, HttpServletRequest request, HttpServletResponse response) {
        String sessionValidateCode = (String) request.getSession().getAttribute(ValidateCodeUtils.SESSION_KEY);
        //全部转换为大写
        validateCode = validateCode.toUpperCase();
        //验证码相同场合
        if (StringUtils.equals(validateCode, sessionValidateCode)) {
            return AjaxResult.createSuccessResult();
        }
        return AjaxResult.createFailResult("请输入正确的验证码。");
    }
}