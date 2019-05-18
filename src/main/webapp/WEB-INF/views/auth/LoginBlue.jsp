<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctag" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!doctype html>
<html>
<head>
    <title>${systemName}-欢迎登录</title>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <link type="image/x-icon" href="${staticContentsServer}/favicon.ico?${version}" rel="shortcut icon">
    <%--滚动--%>
    <link rel="stylesheet" type="text/css" href="${staticContentsServer}/static/plugins/swiper/swiper.min.css?${version}"/>
    <script type="text/javascript" src="${staticContentsServer}/static/plugins/swiper/swiper.min.js?${version}"></script>
    <link rel="stylesheet" type="text/css" href="${staticContentsServer}/static/css/auth/auth.css?${version}"/>

    <script type="text/javascript" src="${staticContentsServer}/static/js/Namespace.js?${version}"></script>
    <script type="text/javascript" src="${staticContentsServer}/static/js/jquery.js?${version}"></script>
    <script type="text/javascript" src="${staticContentsServer}/static/js/prototype.js?${version}"></script>
    <script type="text/javascript" src="${staticContentsServer}/static/js/MainScript.min.js?${version}"></script>
    <script type="text/javascript" src="${staticContentsServer}/static/js/Core.min.js?${version}"></script>
    <script type="text/javascript" src="${staticContentsServer}/static/plugins/dcjqaccordion/js/jquery.cookie.js?${version}"></script>
    <script type="text/javascript" src="${staticContentsServer}/static/js/auth/Login.js?${version}"></script>
    <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
    <script>
        $(function () {
            var heightTop = $(window).height();
            //轮播，垂直中
            $(".swiper-container").css("margin-top", (heightTop - 460) / 2);
            //inpu区，垂直居中
            $(".unified-login-input").css("top", (heightTop - 430) / 2);
            //渐变背景，高度100%
            $(".unified-login-blue-bg").height(heightTop);

            //轮播配置
            var mySwiper = new Swiper('.swiper-container', {
                //自动滑动
                autoplay: {
                    delay: 6000,
                    //触碰仍然自动播放
                    disableOnInteraction: false
                },
                //循环
                loop: true,
                //禁止鼠标拖动翻页
                //simulateTouch : false,
                //分页
                pagination: {
                    el: '.swiper-pagination',
                    clickable: true
                },
                on: {
                    //初始化完成后加载
                    init: function () {
                        $(".swiper-wrapper").addClass("animation");
                        setTimeout(function () {
                            $(".swiper-wrapper").removeClass("animation");
                        }, 1000);
                    },
                    //翻页开始前加载
                    slideChangeTransitionStart: function () {
                        $(".swiper-wrapper").removeClass("animation");
                    },
                    //翻页完成后加载
                    slideChangeTransitionEnd: function () {
                        $(".swiper-wrapper").addClass("animation");
                        setTimeout(function () {
                            $(".swiper-wrapper").removeClass("animation");
                        }, 1000);
                    }
                }
            })
        })
    </script>
</head>
<body class="unified-login-blue">

<!--背景-->
<div class="unified-login-blue-bg"></div>
<!--轮播-->
<div class="swiper-container">
    <div class="swiper-wrapper">

        <c:choose>
            <c:when test="${enterprise != null}">
                <div class="swiper-slide">
                    <div>
                    <pre class="text-newline">
                    <span class="inner-pre login-content-font">
                            ${enterprise.getIntroduction()}
                    </span>
                </pre>
                    </div>
                    <div>
                        <table border="0" width="500" class="login-content-font">
                            <tr>
                                <td width="100">联系人：</td>
                                <td width="400">${enterprise.getContactName()}</td>
                            </tr>
                            <tr>
                                <td width="100">联系电话：</td>
                                <td width="400">${enterprise.getContactTelephone()}</td>
                            </tr>
                        </table>
                    </div>
                </div>
                <c:forEach var="attachment" items="${attachmentExtList}">
                    <div class="swiper-slide">
                        <div><img src="${ctx}/cmn/fileupload/preview?uid=${attachment.getUid()}"></div>
                    </div>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <div class="swiper-slide">
                    <div>
                        <div class="move-up"><img src="${staticContentsServer}/static/images/auth/blue/img-11.png"></div>
                        <div class="move-down"><img src="${staticContentsServer}/static/images/auth/blue/img-12.png"></div>
                        <div class="move-up"><img src="${staticContentsServer}/static/images/auth/blue/img-13.png"></div>
                    </div>
                </div>


                <div class="swiper-slide">
                    <div>
                        <div class="move-up"><img src="${staticContentsServer}/static/images/auth/blue/img-21.png"></div>
                        <div class="move-down"><img src="${staticContentsServer}/static/images/auth/blue/img-22.png"></div>
                        <div class="move-up"><img src="${staticContentsServer}/static/images/auth/blue/img-23.png"></div>
                    </div>
                </div>
                <div class="swiper-slide">
                    <div>
                        <div class="move-down"><img src="${staticContentsServer}/static/images/auth/blue/img-41.png"></div>
                        <div class="move-down"><img src="${staticContentsServer}/static/images/auth/blue/img-42.png"></div>
                        <div class="move-up"><img src="${staticContentsServer}/static/images/auth/blue/img-43.png"></div>
                    </div>
                </div>
                <div class="swiper-slide">
                    <div>
                        <div class="move-down"><img src="${staticContentsServer}/static/images/auth/blue/img-61.png"></div>
                        <div><img src="${staticContentsServer}/static/images/auth/blue/img-62.png"></div>
                        <div class="move-up"><img src="${staticContentsServer}/static/images/auth/blue/img-63.png"></div>
                    </div>
                </div>
                <div class="swiper-slide">
                    <div>
                        <div class="move-up"><img src="${staticContentsServer}/static/images/auth/blue/img-71.png"></div>
                        <div class="move-down"><img src="${staticContentsServer}/static/images/auth/blue/img-72.png"></div>
                        <div class="move-up"><img src="${staticContentsServer}/static/images/auth/blue/img-73.png"></div>
                    </div>
                </div>
                <div class="swiper-slide">
                    <div>
                        <div class="move-up"><img src="${staticContentsServer}/static/images/auth/blue/img-81.png"></div>
                        <div class="move-down"><img src="${staticContentsServer}/static/images/auth/blue/img-82.png"></div>
                        <div class="move-up"><img src="${staticContentsServer}/static/images/auth/blue/img-83.png"></div>
                    </div>
                </div>
                <div class="swiper-slide">
                    <div>
                        <div class="move-down"><img src="${staticContentsServer}/static/images/auth/blue/img-91.png"></div>
                        <div class="move-down"><img src="${staticContentsServer}/static/images/auth/blue/img-92.png"></div>
                        <div class="move-up"><img src="${staticContentsServer}/static/images/auth/blue/img-93.png"></div>
                    </div>
                </div>
                <div class="swiper-slide">
                    <div>
                        <div class="move-up"><img src="${staticContentsServer}/static/images/auth/blue/img-101.png"></div>
                        <div class="move-down"><img src="${staticContentsServer}/static/images/auth/blue/img-102.png"></div>
                        <div class="move-up"><img src="${staticContentsServer}/static/images/auth/blue/img-103.png"></div>
                    </div>
                </div>
            </c:otherwise>
        </c:choose>





    </div>
    <div class="swiper-pagination"></div>
</div>
<!--头部-->
<div class="unified-login-top">
    <!--logo-->
    <div class="unified-login-logo"><i class="fa fa-windows"></i>&nbsp;${systemName}</div>
</div>
<!--登录区-->
<div class="unified-login-input">
    <div class="unified-login-edit">
        <form id="loginForm" action="${ctx}/login" method="post">
            <input type="hidden" id="SESSION_TIME_OUT" value="true"/>
            <h3>${systemName}<c:if test="${enterprise != null}">-${enterprise.getEnterpriseName()}</c:if></h3>
            <!--表单-->
            <ul>
                <div id="loginErrorMessage" style="color: Red;">${error}${accessIpError}</div>
                <li><i class="fa fa-user-circle-o"></i><input class="unified-input" name="username" type="text" value="${username}" placeholder="请输入用户名"></li>
                <li><i class="fa fa-key"></i><input class="unified-input" name="password" type="password" value="${password}" placeholder="请输入密码"></li>
                <li style='display:none;'><i class="fa fa-key"></i><input class="unified-input" name="tenant" type="text" value="${enterprise.getUid()}"  placeholder="请输入企业代码"></li>

                <li style="overflow: hidden;">
                    <div class="unified-left checkbox"><label><input name="rememberMe" type="checkbox" value="${rememberMe}" id="rememberMe"> 记住账号</label></div>
                    <div class="unified-right"><a href="${ctx}/auth/retrievepassword">忘记密码？</a></div>
                </li>
                <li>
                    <button type="submit" id="loginBtn" class="unified-button">立即登录</button>
                </li>
            </ul>
        </form>
    </div>
    <!--提示信息-->
    <div class="unified-login-note">浏览器 <strong style="color: #fff;">推荐使用</strong><br>谷歌(Chrome)、火狐(Firefox)、360(极速模式)、IE10(以上)版本！</div>
</div>
<!--底部导航-->
<div class="unified-login-footer">
    <ul>
        <li style="border: none;width: 100%;color: #FFF;">版权所有：${systemDate.substring(0, 4)} &copy; ${companyName}-${systemName}</li>
    </ul>
</div>
<script type="text/javascript" src="${staticContentsServer}/static/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<script type="text/javascript" src="${staticContentsServer}/static/plugins/jquery-validation/jquery.validate.min.js" type="text/javascript"></script>
</body>
</html>
<%
    //清空IP访问限制错误消息
    session.removeAttribute("accessIpError");
%>
