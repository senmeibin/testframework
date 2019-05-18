package com.bpms.cmn.utility;

import com.bpms.core.utils.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * 验证码工具类
 */
public class ValidateCodeUtils {
    public static final String SESSION_KEY = "SESSION_KEY_VALIDATE_CODE";

    /**
     * 干扰线数量
     */
    public static final int COUNT = 200;

    /**
     * 干扰线的长度=1.414*lineWidth
     */
    public static final int LINE_WIDTH = 2;

    /**
     * 定义图形宽度
     */
    public static int width = 75;

    /**
     * 定义图形高度
     */
    public static int height = 30;

    /**
     * 验证码长度
     */
    public static int length = 4;

    /**
     * 创建图像验证码
     *
     * @param request HttpServletRequest对象
     * @return 验证码图像
     */
    public static BufferedImage createValidateCode(HttpServletRequest request) {
        //获取自定义设定图形宽度
        String widthStr = request.getParameter("w");
        if (StringUtils.isNotEmpty(widthStr) && StringUtils.isNumeric(widthStr)) {
            width = Integer.parseInt(widthStr);
        }
        //获取自定义设定图形高度
        String heightStr = request.getParameter("h");
        if (StringUtils.isNotEmpty(heightStr) && StringUtils.isNumeric(heightStr)) {
            height = Integer.parseInt(heightStr);
        }
        //获取自定义设定验证码长度
        String lengthStr = request.getParameter("l");
        if (StringUtils.isNotEmpty(lengthStr) && StringUtils.isNumeric(lengthStr)) {
            length = Integer.parseInt(lengthStr);
        }

        //在内存中创建图象
        final BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        //获取图形上下文
        final Graphics2D graphics = (Graphics2D) image.getGraphics();

        //设定背景颜色
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, width, height);
        //设定边框颜色
        graphics.setColor(Color.BLACK);
        graphics.drawRect(0, 0, width - 1, height - 1);

        final Random random = new Random();
        //随机产生干扰线，使图象中的认证码不易被其它程序探测到
        for (int i = 0; i < COUNT; i++) {
            graphics.setColor(getRandColor(150, 200));
            //保证画在边框之内
            final int x = random.nextInt(width - LINE_WIDTH - 1) + 1;
            final int y = random.nextInt(height - LINE_WIDTH - 1) + 1;
            final int xl = random.nextInt(LINE_WIDTH);
            final int yl = random.nextInt(LINE_WIDTH);
            graphics.drawLine(x, y, x + xl, y + yl);
        }

        //取随机产生的认证码(4位数字)
        final String validateCode = generateRandomCode(length);
        for (int i = 0; i < validateCode.length(); i++) {
            //设置字体颜色
            graphics.setColor(getRandColor(50, 200));
            //设置字体样式
            graphics.setFont(new Font("Times New Roman", Font.BOLD, 24));
            //设置字符，字符间距，上边距
            graphics.drawString(String.valueOf(validateCode.charAt(i)), ((width - 5) / length * i), height - (height / 25) * 5);
        }

        //将认证码存入SESSION
        request.getSession().setAttribute(SESSION_KEY, validateCode);
        //图象生效
        graphics.dispose();
        return image;
    }

    /**
     * 取得给定范围随机颜色
     *
     * @param min 最小值
     * @param max 最大值
     * @return 颜色
     */
    private static Color getRandColor(int min, int max) {
        final Random random = new Random();
        if (min > 255) {
            min = 255;
        }
        if (max > 255) {
            max = 255;
        }

        final int r = min + random.nextInt(max - min);
        final int g = min + random.nextInt(max - min);
        final int b = min + random.nextInt(max - min);

        return new Color(r, g, b);
    }

    /**
     * 生产随机验证码
     *
     * @param codeLength 验证码的长度
     * @return 验证码
     */
    private static String generateRandomCode(final int codeLength) {
        final StringBuilder sb = new StringBuilder();
        final Random random = new Random();
        //随机可选字符（去除0，1，I，O）
        final String sourceStr = "23456789ABCDEFGHJKLMNPQRSTUVWXYZ";

        for (int i = 0; i < codeLength; i++) {
            sb.append(sourceStr.charAt(random.nextInt(sourceStr.length())));
        }

        return sb.toString();
    }
}