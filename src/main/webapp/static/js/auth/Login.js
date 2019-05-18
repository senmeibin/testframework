$(document).ready(function () {
    if ($.cookie("username")) {
        $("input[name=username]").val($.cookie("username"));
    }

    if ($.cookie("rememberMe")) {
        $("input[name=rememberMe]").attr("checked", true);
    }

    $("#loginForm").validate({
        rules: {
            username: "required",
            password: "required"
        },
        messages: {
            username: "请输入用户名",
            password: "请输入密码"
        },
        errorElement: "span"
    });

    $("#loginBtn").on("click", function () {
        if ($("input[name=rememberMe]").is(":checked")) {
            $.cookie("rememberMe", 1, {
                expires: 7
            });

            $.cookie("username", $("input[name=username]").val(), {
                expires: 7
            });
        } else {
            $.cookie("username", null);
            $.cookie("rememberMe", null);
        }
        $("#loginForm").submit();
    });
});
