//MainScript.js类文件定义开始------------------------------------------------------------//
$M = function () {
}

//前后空格过滤
$M.trim = function (o) {
    if ($M.isNull(o)) {
        return "";
    }
    //DOM对象的场合
    if (typeof (o) == "object") {
        o.value = $.trim(o.value);
        return o.value;
    }
    else {
        return $.trim(o);
    }
}

//取得字符串的Byte数
$M.getByteLength = function (v) {
    var intLen1 = v.length;
    var intLen2 = intLen1;

    for (var i = 0; i < intLen1; i++) {
        var temp = v.substring(i, i + 1);
        //全角のチェック
        if (temp.match(/[ -~｡-ﾟ]/) == null) {
            intLen2++;
        }
    }

    return intLen2;
}

//Message内容参数格式化
$M.formatMessage = function (msg, params) {
    try {
        if (typeof (params) == "string") {
            msg = msg.replace("{0}", params);
        }
        else if (typeof (params) == "object") {
            for (var i = 0; i < params.length; i++) {
                msg = msg.replace("{" + i + "}", params[i]);
            }
        }
        else {
            msg = msg.replace("{0}", params);
        }
    }
    catch (e) {
    }
    return msg;
}

//格式化URL(http前缀追加) 
$M.formatUrl = function (url) {
    if ($M.isEmpty(url.value)) {
        return;
    }
    if ($M.trim(url.value).indexOf("http://") == 0) {
        return;
    }
    if ($M.trim(url.value).indexOf("https://") == 0) {
        return;
    }
    if (url.value.indexOf("http://") == -1 || url.value.indexOf("http://") > 0) {
        url.value = "http://" + url.value;
    }
}

//EnterKeyの誤動作防止用
$M.disableEnterKey = function (e) {
    //Enterキーではない場合、OK
    if (e.keyCode != Event.KEY_RETURN)
        return;

    //FireFox
    if (navigator.userAgent.indexOf("Firefox") > -1) {
        var type = e.target.type;
        var tag = e.target.tagName;
        //IE
    } else {
        var type = e.srcElement.type;
        var tag = e.srcElement.tagName;
    }

    //INPUTタグ、TEXTAREAタグではない場合、OK
    if (tag != 'INPUT' && tag != 'TEXTAREA')
        return;

    //submit, textarea, reset, button以外の場合は、NG
    if (type != 'submit' && type != 'textarea' && type != 'reset' && type != 'button' && type != 'image') {
        Event.stop(e);
        return false;
    }
}

//BRタグを改行に変換して返します
$M.brToEnter = function (v) {
    return v.replace(/<[B|b][R|r] *\/??>/g, "\n");
}

//改行をBRタグに変換して返します
$M.enterToBr = function (v) {
    if ($M.isNull(v)) return "";
    return v.replace(/\n/g, "<br />");
}

//文件扩张名取得(不含.号)
$M.getExtension = function (filename) {
    var index = filename.lastIndexOf(".");
    if (index == -1) {
        return "";
    }
    return filename.substring(index + 1, filename.length).toLowerCase();
}

//特殊字符转换处理
$M.encodeValue = function (v) {
    if (v.indexOf("%") != -1) {
        v = v.replaceAll("%", "%25");
    }

    if (v.indexOf("&") != -1) {
        v = v.replaceAll("&", "%26");
    }
    while (v.indexOf("+") != -1) {
        v = v.replace('+', "%2B");
    }

    return v;
}

//NULL対象かどうか？
$M.isNull = function (obj) {
    if (obj == null || typeof obj == undefined || obj == "null") {
        return true;
    }
    return false;
}

//NULL対象かどうか？Emptyかどうか？
$M.isEmpty = function (obj) {
    if (this.isNull(obj)) {
        return true;
    }

    if (typeof obj == "string" && $.trim(obj) == "") {
        return true;
    }

    return false;
}

$M.isNotEmpty = function (obj) {
    return !$M.isEmpty(obj);
}

$M.isNotNull = function (obj) {
    return !$M.isNull(obj);
}

//金额格式化处理
//参数说明：
//  num     要格式化的数字 
//  n       保留小数位(可选)
//  sign    金额符号(可选)
$M.formatNum = function (num, n, sign) {
    //如果是字符格式，代表金额符号
    if (typeof n == "string") {
        sign = n;
    }

    if ($M.isEmpty(sign)) {
        sign = "";
    }

    //数字チェックエラー場合、処理中止
    if (!/^(\+|-)?(\d+)(\.\d+)?$/.test(num)) {
        return num;
    }

    if (typeof num == "string") {
        num = parseFloat(num);
    }

    if (isNaN(num)) {
        return num;
    }

    num = String(num.toFixed(n));

    var re = /(-?\d+)(\d{3})/;

    while (re.test(num)) num = num.replace(re, "$1,$2");

    return sign + num;
}

//金额格式化处理(人民币)
//参数说明：
//  num     要格式化的数字 
//  n       保留小数位(可选)
$M.formatCny = function (num, n) {
    if ($M.isEmpty(n)) n = window.amountPrecision;
    return $M.formatNum(num, n, "¥");
}

//金额格式化处理(日元)
//参数说明：
//  num     要格式化的数字 
//  n       保留小数位(可选)
$M.formatJpy = function (num, n) {
    if ($M.isEmpty(n)) n = 0;
    return $M.formatNum(num, n, "¥");
}

//金额格式化处理(美元)
//参数说明：
//  num     要格式化的数字 
//  n       保留小数位(可选)
$M.formatDollar = function (num, n) {
    if ($M.isEmpty(n)) n = 2;
    return $M.formatNum(num, n, "$");
}

//金额格式->数值转化
$M.convertNum = function (num) {
    if ($M.isEmpty(num)) {
        return num;
    }
    num = String(num);
    var symbolPosition = num.substring(0, 1);

    //非负数的场合
    if (symbolPosition != "-") {
        if (isNaN(parseInt(symbolPosition, 10))) {
            //删除金额符号
            num = num.substring(1);
        }
    }

    return parseFloat(num.replaceAll(",", ""));
}

$M.toFixed = function (num, n) {
    if ($M.isEmpty(num)) return 0;

    if ($M.isEmpty(n)) n = 2;

    num = num * 100;
    return num.toFixed(n) / 100;
}

//Cssファイルをロードする 
// key    Cssファイル保存用Key
// path   Cssファイルのパス
$M.loadCssFile = function (key, path) {
    var unloaded = (typeof (window.cssFileLoaded) == 'undefined' || window.cssFileLoaded[key] == 'undefined');
    if (unloaded) {
        var cs = document.createElement("link");
        cs.rel = "stylesheet";
        cs.href = path;
        cs.type = "text/css";
        document.getElementsByTagName('head')[0].appendChild(cs);

        window.cssFileLoaded = {};
        window.cssFileLoaded[key] = 1;
    }
}

//Lock Buttons, TextBoxes, RadioBoxes, ListBoxes in Window
// win ロック範囲(Window或はDIV対象)
$M.lockFormControl = function (win) {
    try {
        $M._lockControl(win, true);
    }
    catch (e) {
    }
}

//UnLock Buttons, TextBoxes, RadioBoxes, ListBoxes in Window
// win          アンロック範囲(Window或はDIV対象)
// restoreFlag  元の状態に戻すフラグ(True:元の状態に戻す/指定しない或はFalse:全対象アンロック)
$M.unLockFormControl = function (win, restoreFlag) {
    try {
        $M._lockControl(win, false, restoreFlag);
    }
    catch (e) {
    }
}

//Lock Buttons, TextBoxes, RadioBoxes, ListBoxes, iFrames in Window
// win ロック範囲(Window或はDIV対象) 
$M.lockWindow = function (win) {
    try {
        $M._lockControl(win, true);

        var frames = $M._getLockDocument(win).getElementsByTagName("iframe");

        for (var i = 0; i < frames.length; i++) {
            //iframeロード中の場合、iframeのloadイベントに_lockFrame処理を追加する
            if (frames[i].contentWindow.document.body == null ||
                frames[i].contentWindow.document.body.innerHTML == "") {
                Event.observe(frames[i].contentWindow, 'load', $M._lockFrame.bind(this, frames[i].contentWindow));
            }
            else {
                $M._lockControl(frames[i].contentWindow, true);
            }
        }
    }
    catch (e) {
    }
}

$M._lockFrame = function (frame) {
    $M._lockControl(frame, true);
}

//UnLock Buttons, TextBoxes, RadioBoxes, ListBoxes, iFrames in Window
// win          アンロック範囲(Window或はDIV対象)
// restoreFlag  元の状態に戻すフラグ(True:元の状態に戻す/False(指定しない):全対象アンロック)
$M.unLockWindow = function (win, restoreFlag) {
    try {
        $M._lockControl(win, false, restoreFlag);

        var frames = $M._getLockDocument(win).getElementsByTagName("iframe");

        for (var i = 0; i < frames.length; i++) {
            $M._lockControl(frames[i].contentWindow, false, restoreFlag);
        }
    }
    catch (e) {
    }
}

//Lock/UnLock処理
// win          ロック/アンロック範囲(Window或はDIV対象)
// lockFlag     ロックフラグ(true:ロック処理/false:アンロック処理)
// restoreFlag  元の状態に戻すフラグ(True:元の状態に戻す/False(指定しない):全対象アンロック)
$M._lockControl = function (win, lockFlag, restoreFlag) {
    var tags = ['input', 'select', 'textarea'];
    for (var i = 0; i < tags.length; i++) {
        var elements = $M._getLockDocument(win).getElementsByTagName(tags[i]);

        for (var j = 0; j < elements.length; j++) {
            //ロック処理
            if (lockFlag) {
                //ロック前の状態をdisabledFlagに設定する
                elements[j].disabledFlag = elements[j].disabled;
                if (elements[j].disabled == false && elements[j].type != 'hidden') {
                    elements[j].disabled = true;
                }
            }
            //アンロック処理
            else {
                if (elements[j].disabled && elements[j].type != 'hidden') {
                    //元の状態に戻す場合
                    if (restoreFlag) {
                        if (elements[j].disabledFlag != true) {
                            elements[j].disabled = false;
                        }
                    }
                    else {
                        elements[j].disabled = false;
                    }
                    elements[j].disabledFlag = false;
                }
            }
        }
    }
}

$M._getLockDocument = function (win) {
    //全画面ロックの場合
    if (win.tagName != "DIV" && win.tagName != "TABLE") {
        return win.document;
    }
    //指定されたDIVロックの場合
    else {
        return win;
    }
}

//innerText FireFox対応
$M.innerText = function (obj, text) {
    if ($M.isFF()) {
        obj.textContent = text;
    } else {
        obj.innerText = text;
    }
}

//get split char , if IE ("\n")  , if firefox :unescape("%0A");
$M.getSplitChar = function () {
    //firefox
    if ($M.isFF()) {
        return unescape("%0A");
    }
    //IE
    return "\n";
};

//指定Html対象の絶対位置を取得する
$M.getElementPosition = function (source) {
    var pt = {x: 0, y: 0};
    do {
        if (source == null) {
            break;
        }
        pt.x += source.offsetLeft + $M.stringToInt(Element.getStyle(source, "borderLeftWidth"));
        pt.y += source.offsetTop + $M.stringToInt(Element.getStyle(source, "borderTopWidth"));
        source = source.offsetParent;
    }
    while (source);

    return pt;
}

//IE7の判断 
$M.isIE7 = function () {
    return $M.isIEVersion(7);
}

//IE8の判断 
$M.isIE8 = function () {
    return $M.isIEVersion(8);
}

//IE9の判断
$M.isIE9 = function () {
    return $M.isIEVersion(9);
}

//IE9の判断
$M.isIE10 = function () {
    return $M.isIEVersion(10);
}

$M.isIEVersion = function (v) {
    return ($M.getIEVersion() == v);
}

//IEバージョンを取得する
$M.getIEVersion = function () {
    if (!$M.isIE()) {
        return -1;
    }
    var versionInfo = window.navigator.appVersion.split("MSIE");
    return parseFloat(versionInfo[1]);
}
//IEを判別処理
$M.isIE = function () {
    //IE10以下判断方法
    var isIE = window.navigator.userAgent.indexOf("MSIE") >= 1;

    if (isIE) return true;

    //IE11以上判断方法
    return (!!window.ActiveXObject || "ActiveXObject" in window);
};

//FireFoxを判別処理 
// mv  :メジャーバージョン指定
// sv  :マイナーバージョン指定
// type         :1=指定バージョンと一致するか、2=指定バージョン以下か、3=指定バージョン以上か
// ※引数を指定しない場合は、FireFoxかどうかのみを判断します。
$M.isFF = function (mv, sv, type) {
    var ua = window.navigator.userAgent;

    //FFで無い場合はfalse
    if (ua.indexOf("Firefox") == -1)
        return false;

    //引数指定が１つでも指定無しの場合はFFかどうかだけ判定するので、trueで返す
    if (mv == undefined && sv == undefined && type == undefined)
        return true;

    //引数が指定されているが、指定されてない引数がある場合はfalseで返す
    if ((mv != undefined || sv != undefined || type != undefined)
        && (mv == undefined || sv == undefined || type == undefined))
        return false;

    //引数が数値以外の場合はfalse
    if (typeof (mv) != 'number' || typeof (sv) != 'number' || typeof (type) != 'number')
        return false;

    //UAからバージョン部分を抜き出す (例："3.6")
    var version = ua.substr(ua.indexOf("Firefox") + 8, 3);
    var major = version.split('.')[0];
    var minor = version.split('.')[1];

    //バージョンがmajor・minor共に一致かどうか
    if (type == 1) {
        return (major == mv && minor == sv);
        //指定バージョン以下か
    } else if (type == 2) {
        return ((major < mv) || (major == mv && minor <= sv));
        //指定バージョン以上か
    } else if (type == 3) {
        return ((major > mv) || (major == mv && minor >= sv));
    }
    return false;
}

//FireFox3を判別処理 
$M.isFF3 = function () {
    return window.navigator.userAgent.indexOf("Firefox/3") >= 1;
};

//Safariを判別処理 
$M.isSafari = function () {
    return window.navigator.userAgent.indexOf("Safari") >= 1;
};

//微信浏览器判断
$M.isWeixinBrowser = function () {
    var ua = window.navigator.userAgent.toLowerCase();
    if (ua.match(/MicroMessenger/i) == 'micromessenger') {
        return true;
    } else {
        return false;
    }
};

//標準モードで動作しているかどうかを自動判別する
//標準(true)、互換(false)
$M.isCompatModeStandard = function () {
    return (document.compatMode == 'CSS1Compat');
}

//ブラウザのscrollTopを取得する
$M.getScrollTop = function (obj) {
    //ターゲット設定。未指定の場合はdocument。
    obj = obj || document;

    //標準モードの場合
    if ($M.isCompatModeStandard()) {
        //safariの場合
        if ($M.isSafari())
            return obj.body.scrollTop;
        //その他の場合(IE,FF,Opera)
        else
            return obj.documentElement.scrollTop;
        //互換モードの場合(全ブラウザ共通)
    } else {
        return obj.body.scrollTop;
    }
}

//ブラウザのscrollLeftを取得する
$M.getScrollLeft = function (obj) {
    //ターゲット設定。未指定の場合はdocument。
    obj = obj || document;

    //標準モードの場合
    if ($M.isCompatModeStandard()) {
        //safariの場合
        if ($M.isSafari())
            return obj.body.scrollLeft;
        //その他の場合(IE,FF,Opera)
        else
            return obj.documentElement.scrollLeft;
        //互換モードの場合(全ブラウザ共通)
    } else {
        return obj.body.scrollLeft;
    }
}

//ブラウザのscrollHeightを取得する
$M.getScrollHeight = function (obj) {
    //ターゲット設定。未指定の場合はdocument。
    obj = obj || document;

    //標準モードの場合
    if ($M.isCompatModeStandard()) {
        //safariの場合
        if ($M.isSafari())
            return obj.body.scrollHeight;
        //その他の場合(IE,FF,Opera)
        else
            return obj.documentElement.scrollHeight;
        //互換モードの場合(全ブラウザ共通)
    } else {
        return obj.body.scrollHeight;
    }
}

//ブラウザのscrollWidthを取得する
$M.getScrollWidth = function (obj) {
    //ターゲット設定。未指定の場合はdocument。
    obj = obj || document;

    //標準モードの場合
    if ($M.isCompatModeStandard()) {
        //safariの場合
        if ($M.isSafari())
            return obj.body.scrollWidth;
        //その他の場合(IE,FF,Opera)
        else
            return obj.documentElement.scrollWidth;
        //互換モードの場合(全ブラウザ共通)
    } else {
        return obj.body.scrollWidth;
    }
}

//ブラウザのclientHeightを取得する
$M.getClientHeight = function (obj) {
    //ターゲット設定。未指定の場合はdocument。
    obj = obj || document;

    //標準モードの場合
    if ($M.isCompatModeStandard())
        return obj.documentElement.clientHeight;
    //互換モードの場合(全ブラウザ共通)
    else
        return obj.body.clientHeight;
}

//ブラウザのclientWidthを取得する
$M.getClientWidth = function (obj) {
    //ターゲット設定。未指定の場合はdocument。
    obj = obj || document;

    //標準モードの場合
    if ($M.isCompatModeStandard())
        return obj.documentElement.clientWidth;
    //互換モードの場合(全ブラウザ共通)
    else
        return obj.body.clientWidth;
}

//ブラウザのclientTopを取得する
$M.getClientTop = function (obj) {
    //ターゲット設定。未指定の場合はdocument。
    obj = obj || document;

    //標準モードの場合
    if ($M.isCompatModeStandard())
        return obj.documentElement.clientTop;
    //互換モードの場合(全ブラウザ共通)
    else
        return obj.body.clientTop;
}

//ブラウザのclientLeftを取得する
$M.getClientLeft = function (obj) {
    //ターゲット設定。未指定の場合はdocument。
    obj = obj || document;

    //標準モードの場合
    if ($M.isCompatModeStandard())
        return obj.documentElement.clientLeft;
    //互換モードの場合(全ブラウザ共通)
    else
        return obj.body.clientLeft;
}

$M.goLogin = function () {
    //認証画面に遷移する
    window.location = window.contextPath + "/login";
}

//Ajax处理结果是否失败？
$M.isAjaxFail = function (r) {
    if (typeof r == "object") {
        return false;
    }
    return $M.isSessionTimeout(r) || $M.isSystemError(r) || $M.is401Error(r) || $M.is404Error(r) || $M.is500Error(r) || $M.isAccessForbiddenError(r) || $M.isMaintenanceError(r);
}

//是否会话超时？
$M.isSessionTimeout = function (r) {
    //セッションタイムアウトの場合、メッセージを表示してらか、ログイン認証画面に遷移する
    if (r.indexOf('SESSION_TIME_OUT') >= 0) {
        //セッションがタイムアウトしました。再度ログインしてください。
        SysCmn.CmnMsg.showMessage("本次会话已失效，请再次登录。", MSG_TYPE_ERROR, $M.goLogin);
        return true;
    }
    return false;
}

//是否响应为空？
$M.isResponseEmpty = function (r) {
    if ($M.isEmpty(r)) {
        SysCmn.CmnMsg.showMessage("页面长时间未操作，系统响应异常，请刷新页面后再执行相关操作。", MSG_TYPE_ERROR);
        return true;
    }
    return false;
}

//是否包含系统错误？
$M.isSystemError = function (r) {
    //System错误的场合
    if (r.indexOf("SYSTEM_ERROR_FLAG") >= 0) {
        SysCmn.CmnMsg.showMessage("发生系统错误，请与系统管理员联系。", MSG_TYPE_ERROR);
        return true
    }
    return false
}

//是否包含401错误？
$M.is401Error = function (r) {
    if (r.indexOf("SYSTEM_401_FLAG") >= 0) {
        SysCmn.CmnMsg.showMessage("401-没有访问权限或操作权限。", MSG_TYPE_ERROR);
        return true
    }
    return false
}

//是否包含404错误？
$M.is404Error = function (r) {
    if (r.indexOf("SYSTEM_404_FLAG") >= 0) {
        SysCmn.CmnMsg.showMessage("404-您所访问的页面不存在。", MSG_TYPE_ERROR);
        return true
    }
    return false
}

//是否包含500错误？
$M.is500Error = function (r) {
    if (r.indexOf("SYSTEM_500_FLAG") >= 0) {
        SysCmn.CmnMsg.showMessage("500-发生系统错误，请与系统管理员联系。", MSG_TYPE_ERROR);
        return true
    }
    return false
}

//是否包含权限禁止错误？
$M.isAccessForbiddenError = function (r) {
    if (r.indexOf("SYSTEM_ACCESS_FORBIDDEN_FLAG") >= 0) {
        SysCmn.CmnMsg.showMessage("应用模块的访问权限暂未开通，请与系统管理员联系。", MSG_TYPE_ERROR);
        return true
    }
    return false
}

//是否包含系统维护错误？
$M.isMaintenanceError = function (r) {
    if (r.indexOf("SYSTEM_MAINTENANCE_FLAG") >= 0) {
        SysCmn.CmnMsg.showMessage("应用模块系统维护中，请稍后重试。", MSG_TYPE_ERROR);
        return true
    }
    return false
}

//文字列からIntに変更する
$M.stringToInt = function (v) {
    var value = parseInt(v, 10);
    value = isNaN(value) ? 0 : value;

    return value;
}

//指定Domの位置を取得する
$M.getElementPos = function (e) {
    var left = 0;
    var top = 0;
    while (e.offsetParent) {
        left += e.offsetLeft + $M.stringToInt(Element.getStyle(e, "borderLeftWidth"));
        top += e.offsetTop + $M.stringToInt(Element.getStyle(e, "borderTopWidth"));
        e = e.offsetParent;
    }

    left += e.offsetLeft + $M.stringToInt(Element.getStyle(e, "borderLeftWidth"));
    top += e.offsetTop + $M.stringToInt(Element.getStyle(e, "borderTopWidth"));

    return {x: left, y: top};
}

//Mouseの絶対位置を取得する
$M.getMousePos = function (event) {
    var x = 0;
    var y = 0;

    if (event.pageX || event.pageY) {
        x = event.pageX;
        y = event.pageY;
    } else {

        x = event.clientX + $M.getScrollLeft() - $M.getClientLeft();
        y = event.clientY + $M.getScrollTop() - $M.getClientTop();
    }

    return {x: x, y: y};
}

//DOM对象交换
$M.swapElement = function (srcObj, destObj) {
    var nextSibling = srcObj.nextSibling;
    var parentNode = srcObj.parentNode;
    destObj.parentNode.replaceChild(srcObj, destObj);
    if (nextSibling != null) {
        parentNode.insertBefore(destObj, nextSibling);
    } else {
        parentNode.appendChild(destObj);
    }
}

//Mouseの位置を取得する
$M.getMouseOffset = function (e, event) {
    var event = event || window.event;

    var ElementPos = $M.getElementPos(e);
    var MousePos = $M.getMousePos(event);
    return {x: MousePos.x - ElementPos.x, y: MousePos.y - ElementPos.y};
}

//JSファイル内部用のポップアップウィンドウ名称
$M.popupWindowName = "POPUP_WINDOW_NAME";

//子画面(Popup Window)を開くための関数(必ずこの関数を利用してWindowを開く)
$M.openWindow = function (url, name, f, replace) {
    url += "&refreshDate=" + new Date().getTime();
    //Case１：自身が子画面の場合、自画面の更新処理
    if (window.name != null && typeof typeof (window.name) != "undefined" && window.name.indexOf(name) == 0) {
        name = window.name;
        return window.open(url, name, f, replace);
    }
    //Case２：自身が親画面、かつ子画面が存在する場合、子画面の更新処理
    else if (window.name != null && typeof (window.name) != "undefined" && window.name.indexOf(name) == 4) {
        name = window.name.substr(4);
        var obj = window.open(url, name, f, replace);
        if (obj != null) {
            obj.focus();
        }
        return obj;
    }
    //Case３：自身が親画面、かつ子画面が存在しない場合、子画面の新規Open処理
    else {
        name = (name + "_" + new Date().getTime());
        var obj = window.open(url, name, f, replace);
        window.name = "SUB_" + name;
        return obj;
    }
}

//左侧补足指定的符号
$M.addLeft = function (v, length, ch) {
    v = $M.trim(v);
    var sb = '';
    for (var i = 0; i < length - v.length; i++) {
        sb = sb + ch;
    }
    sb = sb + v;
    return sb;
};

//指定された文字列、HTML画面に表示する時の幅を取得する
$M.getStringHtmlDimensions = function (s) {
    if (document.body == null) {
        alert("画面加载中，API利用不可。");
        return 0;
    }

    //Unique ID
    var id = "string_html_width_span_id_0123456789";
    var span = $E(id);

    //存在しない場合、新規作成する
    if (span == null) {
        var span = document.createElement("SPAN");

        //改行禁止    
        $(span).css("white-space", "nowrap");

        //長すぎ文字列の場合、横ScrollBarを出さないため、hideを設定する
        $(span).hide();

        $(span).css("position", "absolute");
        $(span).css("top", "-100px");
        span.id = id;
        span.innerHTML = s;

        document.body.appendChild(span);
    }
    else {
        span.innerHTML = s;
    }

    var retValue = Element.getDimensions(span);

    span.innerHTML = "";

    return retValue;
};

//绑定温馨提示折叠功能
$M.bindPromptEvent = function () {
    //温馨提示-折叠 批量添加箭头、提示语
    $(".prompt-open label").append(" <span class=\"prompt-off\">（点击查看详细内容）</span><span class=\"prompt-on\">（点击隐藏详细内容）</span>").after("<em class=\"fa fa-sort-down\"></em><em class=\"fa fa-sort-up\"></em>");

    //获取cookie记录，初始化温馨提示折叠
    var promptIds = $.cookie("promptIds");
    if (!$M.isEmpty(promptIds)) {
        var promptIdsList = promptIds.split(",");
        for (var i = 0; i < promptIdsList.length; i++) {
            if ($M.isEmpty(promptIdsList[i])) {
                continue;
            }
            $("#" + promptIdsList[i]).addClass("prompt-close");
        }
    }

    //绑定点击事件
    $(".prompt-open label").click(function () {
        $(this).parent().toggleClass("prompt-close");

        var domId = $(this).parent().attr("id");
        var promptIds = $.cookie("promptIds");

        //Cookie不为空的场合
        if (!$M.isEmpty(promptIds)) {
            //cookie中已存在数据，与现有数据组合到一起
            promptIds = promptIds + "," + domId;
        }
        else {
            promptIds = domId;
        }

        //判断当前提示的折叠状态
        var isClose = $(this).parent().hasClass("prompt-close");
        //折叠状态，向cookie追加数据
        if (isClose) {
            $.cookie("promptIds", promptIds);
        }
        //展开状态，从cookie中删除本区域的折叠数据
        else {
            promptIds = promptIds.replaceAll("," + domId, "");
            promptIds = promptIds.replaceAll(domId, "");
            $.cookie("promptIds", promptIds);
        }
    });
};

//指定された文字列、HTML画面に表示する時の幅を取得する
$M.getStringHtmlWidth = function (s) {
    if (document.body == null) {
        alert("画面加载中，API利用不可。");
        return 0;
    }

    return $M.getStringHtmlDimensions(s).width;
};

$M.getEvent = function () {
    var e = null
    if (typeof event == "undefined") {
        if (arguments.callee.caller != null) e = arguments.callee.caller.arguments[0];
    }
    else {
        e = window.event;
    }
    return e;
};

//Deep clone object.
Object.deepClone = function (obj) {
    if (typeof (obj) != 'object') return obj;

    var c = new obj.constructor();
    for (var key in obj) {
        if (c[key] != obj[key]) {
            if (typeof (obj[key]) == 'object')
                c[key] = Object.deepClone(obj[key]);
            else
                c[key] = obj[key];
        }
    }
    c.toString = obj.toString;
    c.valueOf = obj.valueOf;
    return c;
}

Array.prototype.put = function (key, value) {
    try {
        key = "_" + key.toString();
        this[key] = value;

    } catch (e) {
        return false;
    }
    return true;
}

Array.prototype.get = function (key) {
    var value = null;
    try {
        key = "_" + key.toString();
        if (this[key]) value = this[key];
    } catch (e) {
    }
    return value;
}

var ID_CARD_POWERS = ["7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7", "9", "10", "5", "8", "4", "2"];
var ID_CARD_PARITY = ["1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"];
//校验18位的身份证号码
//  -1:错误
//  object:校验成功
$M.validIDCard = function (id_card) {
    id_card = id_card.toUpperCase();
    //空值的场合
    if ($M.isEmpty(id_card)) return -1;

    //长度不满18位
    if (id_card.length != 18) return -1;

    var num = id_card.substr(0, 17);
    var parity_bit = id_card.substr(17);
    var power = 0;

    var result = {};
    result.birthday = id_card.substr(6, 4) + "/" + id_card.substr(10, 2) + "/" + id_card.substr(12, 2);

    for (var i = 0; i < 17; i++) {
        //校验每一位的合法性
        if (num.charAt(i) < '0' || num.charAt(i) > '9') {
            return -1;
        } else {
            //加权
            power += parseInt(num.charAt(i), 10) * parseInt(ID_CARD_POWERS[i], 10);
            //设置性别
            if (i == 16 && parseInt(num.charAt(i), 10) % 2 == 0) {
                //女
                result.gender = "02";
            } else {
                //男
                result.gender = "01";
            }
        }
    }

    //取模
    var mod = parseInt(power, 10) % 11;
    if (ID_CARD_PARITY[mod] == parity_bit) {
        return result;
    }
    return -1;
};

(function ($) {
    "use strict";
    $(document).ready(function () {
        try {
            // tool tips
            $('.tooltips').tooltip();

            // popovers
            $('.popovers').popover({
                html: true
            });
        }
        catch (e) {
            //bootstrap.js未加载
        }
    });
})(jQuery);
//MainScript.js类文件定义结束------------------------------------------------------------//


//AjaxRequest.js类文件定义开始-----------------------------------------------------------//
SysAjax = function () {
}

SysAjax.prototype.send = function (url, method, callback, data, onFailure) {
    if (typeof onFailure != "function") {
        onFailure = this.onFailure;
    }

    //Ajax请求URL地址中添加时间戳
    var time = new Date().getTime();
    url = (url.indexOf("?") == -1) ? (url + "?ajaxTime=" + time) : (url + "&ajaxTime=" + time);

    return new Ajax.Request(
        url,
        {
            method: method,
            parameters: data,
            onComplete: callback,
            onFailure: onFailure
        });
}

SysAjax.prototype.sendPOST = function (url, callback, data, onFailure) {
    this.send(url, "post", callback, data, onFailure);
}

SysAjax.prototype.sendGET = function (url, callback, data, onFailure) {
    return this.send(url, "get", callback, data, onFailure);
}

SysAjax.prototype.onFailure = function () {
    SysCmn.CmnMsg.showMessage("发生Ajax通信错误，请与系统管理员联系。", MSG_TYPE_ERROR);
}

AjaxIns = new SysAjax();
//AjaxRequest.js类文件定义结束-----------------------------------------------------------//


//DateUtility.js类文件定义开始-----------------------------------------------------------//
window.DATE_FORMAT = "yyyy/MM/dd";
window.DATE_FORMAT_YM = "yyyy/MM";
window.NS_DATE = "yyyyMMdd";
window.DATE_TIME = "yyyy/MM/dd HH:mm:ss";

//日付を追加する
Date.prototype.addDay = function (days) {
    return new Date(this.getTime() + days * 24 * 60 * 60 * 1000)
}

//日付を追加する
//   interval    追加タイプ(YEAR、MONTH、WEEK、DAY、HOUR、MINUTE、SECOND)
//   number      指定追加タイプによて日付を追加する
//   date        指定日付  
Date.prototype.addDate = function (interval, number) {
    switch (interval) {
        case "YEAR":
            this.setFullYear(this.getFullYear() + number);
            break;
        case "MONTH":
            this.setMonth(this.getMonth() + number);
            break;
        case "WEEK":
            this.setDate(this.getDate() + number * 7);
            break;
        case "DAY":
            this.setDate(this.getDate() + number);
            break;
        case "HOUR":
            this.setHour(this.getHour() + number);
            break;
        case "MINUTE":
            this.setMinutes(this.getMinutes() + number);
            break;
        case "SECOND":
            this.setSeconds(this.getSeconds() + number);
            break;
    }
    return this;
}

// 曜日を取得する
Date.prototype.getWeekName = function () {
    var day = this.getDay();
    switch (day) {
        case 0:
            return "星期日";
            break;
        case 1:
            return "星期一";
            break;
        case 2:
            return "星期二";
            break;
        case 3:
            return "星期三";
            break;
        case 4:
            return "星期四";
            break;
        case 5:
            return "星期五";
            break;
        case 6:
            return "星期六";
            break;
    }
}

//函数：格式化日期   
//参数：formatStr-格式化字符串   
//d：将日显示为不带前导零的数字，如1   
//dd：将日显示为带前导零的数字，如01   
//ddd：将日显示为缩写形式，如Sun   
//dddd：将日显示为全名，如Sunday   
//M：将月份显示为不带前导零的数字，如一月显示为1   
//MM：将月份显示为带前导零的数字，如01  
//MMM：将月份显示为缩写形式，如Jan  
//MMMM：将月份显示为完整月份名，如January  
//yy：以两位数字格式显示年份  
//yyyy：以四位数字格式显示年份  
//h：使用12小时制将小时显示为不带前导零的数字，注意||的用法  
//hh：使用12小时制将小时显示为带前导零的数字  
//H：使用24小时制将小时显示为不带前导零的数字  
//HH：使用24小时制将小时显示为带前导零的数字  
//m：将分钟显示为不带前导零的数字  
//mm：将分钟显示为带前导零的数字  
//s：将秒显示为不带前导零的数字  
//ss：将秒显示为带前导零的数字  
//l：将毫秒显示为不带前导零的数字  
//ll：将毫秒显示为带前导零的数字  
//tt：显示am/pm  
//TT：显示AM/PM  
//返回：格式化后的日期  
Date.prototype.format = function (formatStr) {
    var date = this;
    /*  
     函数：填充0字符  
     参数：value-需要填充的字符串, length-总长度  
     返回：填充后的字符串  
     */
    var zeroize = function (value, length) {
        if (!length) {
            length = 2;
        }
        value = String(value);
        for (var i = 0, zeros = ''; i < (length - value.length); i++) {
            zeros += '0';
        }
        return zeros + value;
    };
    return formatStr.replace(/"[^"]*"|'[^']*'|\b(?:d{1,4}|M{1,4}|yy(?:yy)?|([hHmstT])\1?|[lLZ])\b/g, function ($0) {
        switch ($0) {
            case 'd':
                return date.getDate();
            case 'dd':
                return zeroize(date.getDate());
            case 'ddd':
                return ['Sun', 'Mon', 'Tue', 'Wed', 'Thr', 'Fri', 'Sat'][date.getDay()];
            case 'dddd':
                return ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'][date.getDay()];
            case 'M':
                return date.getMonth() + 1;
            case 'MM':
                return zeroize(date.getMonth() + 1);
            case 'MMM':
                return ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'][date.getMonth()];
            case 'MMMM':
                return ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'][date.getMonth()];
            case 'yy':
                return String(date.getFullYear()).substr(2);
            case 'yyyy':
                return date.getFullYear();
            case 'h':
                return date.getHours() % 12 || 12;
            case 'hh':
                return zeroize(date.getHours() % 12 || 12);
            case 'H':
                return date.getHours();
            case 'HH':
                return zeroize(date.getHours());
            case 'm':
                return date.getMinutes();
            case 'mm':
                return zeroize(date.getMinutes());
            case 's':
                return date.getSeconds();
            case 'ss':
                return zeroize(date.getSeconds());
            case 'l':
                return date.getMilliseconds();
            case 'll':
                return zeroize(date.getMilliseconds());
            case 'tt':
                return date.getHours() < 12 ? 'am' : 'pm';
            case 'TT':
                return date.getHours() < 12 ? 'AM' : 'PM';
        }
    });
}

Date.prototype.formatYM = function () {
    return this.format(DATE_FORMAT_YM);
}

Date.prototype.formatYMD = function () {
    return this.format(DATE_FORMAT);
}

Date.prototype.formatYMDHMS = function () {
    return this.format(DATE_TIME)
};

//Format Date to String
Date.prototype.formatString = function (split, full) {
    var year = (this.getFullYear()).toString();
    var month = (this.getMonth() + 1).toString();
    var day = (this.getDate()).toString();

    if (full == true) {
        if (month.length < 2) month = "0" + month;
        if (day.length < 2) day = "0" + day;
    }

    return year + split + month + split + day;
}

Date.prototype.diff = function (date) {
    return (this.getTime() - date.getTime()) / (24 * 60 * 60 * 1000);
}
//DateUtility.js类文件定义结束-----------------------------------------------------------//


//StringUtility.js类文件定义开始---------------------------------------------------------//
//メッセージ内容をフォーマットする。 
//@param params メッセージパラメタ
//@return フォーマット後のメッセージ内容
String.prototype.formatMessage = function (params) {
    try {
        var message = this;
        if (typeof (params) == "string") {
            message = message.replace("{0}", params);
        }
        else if (typeof (params) == "object") {
            for (var i = 0; i < params.length; i++) {
                message = message.replace("{" + i + "}", params[i]);
            }
        }
        else {
            message = message.replace("{0}", params);
        }
    }
    catch (e) {
    }
    return message;
}

String.prototype.htmlToString = function () {
    return this.replace(/<(.*)>/, "&lt;$1&gt;");
}

String.prototype.replaceAll = function (src, des) {
    return this.replace(new RegExp(src, "gm"), des);
}

String.prototype.formatY = function () {
    if (this.isNullDate()) {
        return "";
    }
    if (this.length >= 10) {
        return this.substr(0, 4)
    }
    return "";
}
String.prototype.formatM = function () {
    if (this.isNullDate()) {
        return "";
    }
    if (this.length >= 10) {
        return this.substr(5, 2)
    }
    return "";
}

String.prototype.formatD = function () {
    if (this.isNullDate()) {
        return "";
    }
    if (this.length >= 10) {
        return this.substr(8, 2)
    }
    return "";

}
String.prototype.formatYM = function () {
    if (this.isNullDate()) {
        return "";
    }
    if (this.length >= 7) {
        return this.substr(0, 7)
    }
    return "";
}

String.prototype.formatMD = function () {
    if (this.isNullDate()) {
        return "";
    }
    if (this.length >= 10) {
        return this.substr(5, 5)
    }
    return "";
}

String.prototype.formatYMD = function () {
    if (this.isNullDate()) {
        return "";
    }
    if (this.length >= 10) {
        return this.substr(0, 10)
    }
    return "";
}

String.prototype.formatYMDHM = function () {
    if (this.isNullDate()) {
        return "";
    }
    if (this.length >= 16) {
        return this.substr(0, 16)
    }
    return "";
}

String.prototype.formatYMDHMS = function () {
    if (this.isNullDate()) {
        return "";
    }
    if (this.length >= 19) {
        return this.substr(0, 19)
    }
    return "";
}

String.prototype.formatH = function () {
    if (this.isNullDate()) {
        return "";
    }
    if (this.length >= 19) {
        return this.substr(11, 2)
    }
    return "";
}
String.prototype.formatMin = function () {
    if (this.isNullDate()) {
        return "";
    }
    if (this.length >= 19) {
        return this.substr(14, 2)
    }
    return "";
}
String.prototype.formatS = function () {
    if (this.isNullDate()) {
        return "";
    }
    if (this.length >= 19) {
        return this.substr(17, 2)
    }
    return "";
}
String.prototype.formatHM = function () {
    if (this.isNullDate()) {
        return "";
    }
    if (this.length >= 19) {
        return this.substr(11, 5)
    }
    return "";
}
String.prototype.formatMS = function () {
    if (this.isNullDate()) {
        return "";
    }

    if (this.length >= 19) {
        return this.substr(14, 5)
    }
    return "";
}
String.prototype.formatHMS = function () {
    if (this.isNullDate()) {
        return "";
    }
    if (this.length >= 19) {
        return this.substr(11, 8)
    }
    return "";
}

String.prototype.formatMDHM = function () {
    if (this.isNullDate()) {
        return "";
    }
    if (this.length >= 19) {
        return this.substr(5, 11)
    }
    return "";
}

String.prototype.isNullDate = function () {
    if (this.indexOf("1753-01-01") == 0) {
        return true;
    }
    if (this.indexOf("1900-01-01") == 0) {
        return true;
    }
    if (this.indexOf("1753/01/01") == 0) {
        return true;
    }
    if (this.indexOf("1900/01/01") == 0) {
        return true;
    }
    return false;
}

//HTMLタグをエスケープ処理します。
//Prototype.jsのescapeHTMLでは改行が自動で消えてしまいますが、こちらでは消えません。
String.prototype.escapeHTMLKeepLineBreak = function () {
    return this.replace(/&/g, "&amp;").replace(/"/g, "&quot;").replace(/</g, "&lt;").replace(/>/g, "&gt;");
}

String.prototype.splitByEnter = function () {
    var arr = this.split($M.getSplitChar());
    for (var i = 0; i < arr.length; i++) {
        arr[i] = $.trim(arr[i]);
    }
    return arr;
}
//StringUtility.js类文件定义结束---------------------------------------------------------//


//JsonUtility.js类文件定义开始-----------------------------------------------------------//
window.SERVER_TIMEZONE = 8;
window.CLIENT_TIMEZONE = new Date().getTimezoneOffset() / 60 * -1;

JsonUtility = function () {
}

//JSON文字列からJSON対象に変更する
JsonUtility.Parse = function (jsonString) {
    if (typeof jsonString == "object") {
        return jsonString;
    }
    try {
        if ($M.isNull(jsonString) || $.trim(jsonString) == "") {
            return {};
        }

        var json = eval("(" + jsonString + ")");

        if (json == jsonString) {
            SysCmn.CmnMsg.showMessage("JSON字符串错误。", MSG_TYPE_ERROR);
        }
        return json;
    }
    catch (e) {
        SysCmn.CmnMsg.showMessage("JSON字符串错误[" + e + "]。", MSG_TYPE_ERROR);
        return jsonString;
    }
};

//JSON対象からJSON文字列に変更する
/**
 * @return {string}
 */
JsonUtility.ToJSON = function (jsonObject) {
    var S = [];
    var J = "";
    var T = "";
    //配列の場合
    if (Object.prototype.toString.apply(jsonObject) === '[object Array]') {
        for (var i = 0; i < jsonObject.length; i++) {
            S.push(JsonUtility.ToJSON(jsonObject[i]));
        }
        J = '[' + S.join(',') + ']';
    }
    //日付の場合
    else if (Object.prototype.toString.apply(jsonObject) === '[object Date]') {
        J = "\\/Date(" + jsonObject.getTime() + ")\\/";
    }
    //Functionの場合
    else if (Object.prototype.toString.apply(jsonObject) === '[object RegExp]' || Object.prototype.toString.apply(jsonObject) === '[object Function]') {
        J = jsonObject.toString();
    }
    //対象の場合
    else if (Object.prototype.toString.apply(jsonObject) === '[object Object]') {
        if (jsonObject == null) {
            return "";
        }
        for (var i in jsonObject) {
            //文字列の場合
            if (typeof (jsonObject[i]) == 'string') {
                var value = jsonObject[i];
                value = value.replaceAll("\\\\", "\\\\");
                value = value.replaceAll('"', '\\"')
                value = $M.encodeValue(value);

                T = '"' + value + '"';
            }
            //対象の場合
            else if (typeof (jsonObject[i]) === 'object') {
                T = JsonUtility.ToJSON(jsonObject[i]);
            }
            //その以外の場合
            else {
                T = '"' + jsonObject[i] + '"';
            }

            if (typeof T == "string") {
                if (T.indexOf("Date(") > 0) {
                    SysCmn.CmnMsg.showMessage("JsonUtility.ToJSON.T = " + T, MSG_TYPE_INFO);
                    T = "\\" + T + "\\";
                }
            }

            S.push('"' + i + '":' + T);
        }
        J = '{' + S.join(',') + '}';
    }
    else if (Object.prototype.toString.apply(jsonObject) === '[object String]') {
        J = "\"" + jsonObject + "\"";
    }
    else {
        if (jsonObject == null) {
            J = "\"\"";
        }
        else {
            J = "\"" + jsonObject + "\"";
        }
    }

    return J;
};
//JsonUtility.js类文件定义结束-----------------------------------------------------------//


//ControlUtility.js类文件定义开始--------------------------------------------------------//
SysCmn.CtrlUtil = function () {

}

//共通Popup画面（例：グループ選択、ディレクトリ選択、ファイル選択、ポートレット選択など）がIEドキュメント中央に設定する。 
// @param obj ポップアップコントロールContainer
// @param src イベント/HtmlElement/表示位置モード(center)
SysCmn.CtrlUtil.SetPopupPosition = function (obj, src) {
    obj = $($E(obj));

    //親画面可視領域の高さ
    var h = $M.getClientHeight();
    //親画面可視領域の長さ
    var w = $M.getClientWidth();

    //コントロールの高さ
    var ctl_h = parseInt(obj.height(), 10);
    //コントロールの長さ
    var ctl_w = parseInt(obj.width(), 10);

    //親画面の高さ＜コントロールの高さの場合
    if (h < ctl_h) h = ctl_h;
    //親画面の長さ＜コントロールの長さの場合
    if (w < ctl_w) w = ctl_w;

    var scrollTop = $M.getScrollTop();
    var scrollLeft = $M.getScrollLeft();

    if (src != null) {
        //絶対位置取得用の変数
        var position = null;
        try {
            //表示位置モード指定場合
            if (src == "center") {
                //Windowの上下中心に表示する
                obj.css("top", ((h - ctl_h) / 2 + scrollTop));
            }
            else if (src == "top") {
                obj.css("top", (scrollTop + 50));
            }
            else if (src == "firstTop") {
                obj.css("top", scrollTop);
            }
            else {
                //HtmlElement指定場合
                if (typeof (src.id) != "undefined") {
                    position = $M.getElementPosition(src);
                }
                //イベント指定場合
                else {
                    position = $M.getElementPosition(Event.element(src));
                }

                if (position.y - scrollTop > (ctl_h + 20)) {
                    obj.css("top", (position.y - (ctl_h + 20)));
                }
                else {
                    obj.css("top", scrollTop);
                }
            }
        }
        catch (e) {
            obj.css("top", scrollTop);
        }
    }
    else {
        obj.css("top", scrollTop);
    }

    //Windowの左右中心に表示する
    obj.css("left", ((w - ctl_w) / 2 + scrollLeft));
}

SysCmn.DragDrop = function () {
}

SysCmn.DragDrop.Start = function (id, event) {
    if (!Event.isLeftClick(event)) {
        return;
    }

    var obj = $E(id);

    //最大化窗口的场合禁止移动
    if (obj.style.width == "100%" || obj.style.height == "100%") {
        return;
    }

    var left = !!obj.style.left ? obj.style.left : 0;
    var top = !!obj.style.top ? obj.style.top : 0;

    var x = parseInt(left, 10);
    var y = parseInt(top, 10);
    var deltaX = Event.pointerX(event) - x;
    var deltaY = Event.pointerY(event) - y;

    Event.observe(document, 'mousemove', MouseMoveHandler);
    Event.observe(document, 'mouseup', MouseUpHandler);
    Event.observe(document, "selectstart", SelectStartHandler);

    Event.stop(event);

    //マウス移動処理（ドラッグ＆ドロップ処理開始）  
    function MouseMoveHandler(event) {
        $(obj).css("left", Event.pointerX(event) - deltaX);
        $(obj).css("top", Event.pointerY(event) - deltaY);
        SelectStartHandler();
        Event.stop(event);
    }

    //マウスアップ処理（ドラッグ＆ドロップ処理完了）
    function MouseUpHandler(event) {
        Event.stopObserving(document, 'mousemove', MouseMoveHandler);
        Event.stopObserving(document, 'mouseup', MouseUpHandler);
        Event.stopObserving(document, "selectstart", SelectStartHandler);
        Event.stop(event);
    }

    //画面上でドラッグでテキスト選択が出来ないよう、onSelectStartでreturn falseを返すだけのFunction。IEのため。
    /**
     * @return {boolean}
     */
    function SelectStartHandler() {
        return false;
    }
}

//SysCmn.EstopLayerクラスを定義する
SysCmn.EstopLayer = function () {
}

SysCmn.EstopLayer.prototype.tagTypeList = ['A', 'INPUT', 'SELECT', 'TEXTAREA', 'IFRAME'];

//Create the estop layer's filters parameter.
SysCmn.EstopLayer.prototype.filters = [];

//Create the document object array parameter.
SysCmn.EstopLayer.prototype.userCtrls = [];

//filter's arg
SysCmn.EstopLayer.prototype.opacity = 40;

//Sets or retrieves the stacking order of positioned objects.
SysCmn.EstopLayer.prototype.zIndex = 1000;

//Sets or retrieves the color behind the content of the object. 
SysCmn.EstopLayer.prototype.backgroundColor = "#000000";

//When the filter had append to body the appendCompleteFlag is true, if not the appendCompleteFlag is false.
SysCmn.EstopLayer.prototype.appendCompleteFlag = false;

//Store the dom that will be control the TabIndex value.
SysCmn.EstopLayer.prototype.DHTMLList = [];

//表示処理
SysCmn.EstopLayer.prototype.show = function (ctlFrame) {
    $(document.body).css("overflow-x", "hidden");

    //Set the dom Tab.
    this.setTabIndex(document.body, SysCmn.EstopLayer.tagTypeList, -1);
    this.setTabIndex(ctlFrame, SysCmn.EstopLayer.tagTypeList, 0);

    if (this.userCtrls.length > 0 && this.userCtrls[this.userCtrls.length - 1].id == $E(ctlFrame).id) return;
    //Create the div element.
    var filter = document.createElement("div");
    var jFilter = $(filter);
    //设置遮荫层属性
    filter.id = ctlFrame.id + 'ctlEstopLayerDiv_1q2w3e4r5t6y7u8i9o0p1a2s3d4f5g6h7j8k9l0';
    jFilter.css("left", 0);
    jFilter.css("top", 0);
    jFilter.css("display", "block");
    jFilter.css("position", "absolute");
    jFilter.css("border", "none");
    jFilter.css("background-color", this.backgroundColor);
    //IE
    jFilter.css("filter", "alpha(opacity='" + this.opacity + "')");
    //FireFox
    jFilter.css("opacity", this.opacity / 100);
    jFilter.css("visibility", "visible");

    //該当filterのDOM対象はDocumentに追加する
    this.appendChildToBody(filter);

    var jFrame = $(ctlFrame);

    var dataIndex = jFrame.data("zindex");
    //Frame上未设置data-zindex，自动设置Frame的优先层级
    if ($M.isEmpty(dataIndex)) {
        //设置Popup控件的堆叠顺序
        jFrame.css("z-index", this.zIndex + 1);

        //设置过滤层的堆叠顺序
        jFilter.css("z-index", this.zIndex);
    }
    else {
        jFrame.css("z-index", dataIndex + 1);

        //设置过滤层的堆叠顺序
        jFilter.css("z-index", dataIndex);
    }

    this.zIndex = this.zIndex + 2;
    //Set window to observe the ReSize event.
    Event.observe(window, 'resize', this.reSize.bind(this));

    //Push the filter to filters.
    this.filters.push(filter);

    //Push the UserControl's Dom to the userCtrls.
    this.userCtrls.push($E(ctlFrame));

    //Implement the ReSize method, so that to set width and height both the div and iframe.
    this.reSize();
};

SysCmn.EstopLayer.prototype.appendChildToBody = function (filter) {
    if ($M.isIE() && document.readyState != "complete") {
        window.setTimeout(function () {
            SysCmn.EstopLayer.appendChildToBody.bind(filter);
        }, 100);
        return;
    }
    document.body.appendChild(filter);
    this.appendCompleteFlag = true;
};

//非表示処理
SysCmn.EstopLayer.prototype.hide = function (ctlFrame) {
    if (ctlFrame == null || $E(ctlFrame) == null) {
        alert("SysCmn.EstopLayer.prototype.hide()方法的参数不足。");
        return;
    }

    //Rollback the dom TabIndex's value.
    this.rollbackTabIndex();

    if (this.userCtrls.length == 0 || this.userCtrls[this.userCtrls.length - 1].id != $E(ctlFrame).id) {
        return;
    }

    //filter対象を表示しない
    $(this.filters[this.filters.length - 1]).css("visibility", "hidden");

    //filter対象を削除する
    this.elementRemoveFromBody(this.filters[this.filters.length - 1]);

    //親コントロールが存在場合
    if (this.filters.length > 1) {
        //親コントロールのTabIndexを再度設定する
        this.setTabIndex(document.body, SysCmn.EstopLayer.tagTypeList, -1);
        this.setTabIndex(this.userCtrls[this.userCtrls.length - 2], SysCmn.EstopLayer.tagTypeList, 0);
    }

    //Delete the the point estop's relation.
    this.filters.pop();

    //Delete the the point UserControl's relation.
    this.userCtrls.pop();

    if (this.userCtrls.length == 0) {
        $(document.body).css("overflow-x", "auto");
    }

    //Implement the ReSize method, so that to set width and height both the div and iframe.
    this.reSize();

    this.zIndex = this.zIndex - 2;
};

SysCmn.EstopLayer.prototype.elementRemoveFromBody = function (filter) {
    try {
        if (!this.appendCompleteFlag) {
            window.setTimeout(function () {
                SysCmn.EstopLayer.elementRemoveFromBody.bind(filter);
            }, 100);
            return;
        }
        Element.remove(filter);
    }
    catch (e) {
    }
};

//リサイズ処理
SysCmn.EstopLayer.prototype.reSize = function () {
    var scrollHeight = $M.getScrollHeight();
    var clientHeight = $M.getClientHeight();
    var scrollWidth = $M.getScrollWidth();
    var clientWidth = $M.getClientWidth();

    //Set the estop layer's height.
    for (var i = 0; i < this.filters.length; i++) {
        if (scrollHeight > clientHeight) {
            $(this.filters[i]).css("height", scrollHeight);
        }
        else {
            $(this.filters[i]).css("height", clientHeight);
        }

        //Set the estop layer's width.
        if (scrollWidth > clientWidth) {
            $(this.filters[i]).css("width", scrollWidth);
        }
        else {
            $(this.filters[i]).css("width", clientWidth);
        }
    }
};

//指定Element内部の指定HtmlTagのTabIndexを設定する
SysCmn.EstopLayer.prototype.setTabIndex = function (element, typeList, tabIndex) {
    //指定HtmlTagの全部対象を取得する
    var ctlList = [];
    for (var i = 0; i < typeList.length; i++) {
        var list = $E(element).getElementsByTagName(typeList[i]);
        ctlList.push(list);
    }

    for (var i = 0; i < ctlList.length; i++) {
        for (var j = 0; j < ctlList[i].length; j++) {
            var item = ctlList[i][j];
            //tabIndexバックアップ処理
            if (item.storeTabIndex == 'undefined') {
                item.storeTabIndex = item.tabIndex;
            }
            item.tabIndex = tabIndex;

            //親画面の場合　And　IEの場合　And　(Submitボタン 或は Radioボタン)の場合 And disabledプロパティが存在する場合
            //指定項目にtoggleDisabledプロパティを作成する
            if (tabIndex == -1 &&
                $M.isIE() &&
                item.tagName == 'INPUT' &&
                (item.type == 'submit' || item.type == 'radio') && !item.disabled) {
                item.disabled = true;
                item.toggleDisabled = true;
            }

            //指定コントロール内部のSubmit/Radioボタンの有効化処理
            if (tabIndex == 0) {
                if ($M.isIE() &&
                    item.tagName == 'INPUT' &&
                    (item.type == 'submit' || item.type == 'radio') &&
                    item.toggleDisabled != 'undefined' &&
                    item.toggleDisabled) {
                    item.disabled = false;
                    item.toggleDisabled = false;
                }
            }

            this.DHTMLList.push(item);
        }
    }
};

//タブインデックスのロールバック処理
SysCmn.EstopLayer.prototype.rollbackTabIndex = function () {
    for (var i = 0; i < this.DHTMLList.length; i++) {
        var item = this.DHTMLList[i];
        if (item.storeTabIndex != 'undefined') {
            item.tabIndex = item.storeTabIndex;
        }

        //Rollback submit/radio button.
        if ($M.isIE() &&
            item.tagName == 'INPUT' &&
            (item.type == 'submit' || item.type == 'radio') &&
            item.toggleDisabled != 'undefined' &&
            item.toggleDisabled) {
            item.disabled = false;
            item.toggleDisabled = false;
        }
    }
    this.DHTMLList.clear();
};

//SysCmn.EstopLayerインスタンスを作成する
SysCmn.EstopLayer = new SysCmn.EstopLayer();
//ControlUtility.js类文件定义结束--------------------------------------------------------//


//IScrollUtility.js类文件定义开始--------------------------------------------------------//
//iScroll全局配置参数
var iscrollGlobalOptions = {
    mouseWheel: true,
    scrollbars: true,
    disablePointer: true,
    interactiveScrollbars: true,
    disableMouse: true
};

IScrollUtility = function () {
}

IScrollUtility.getGlobalOptions = function () {
    return iscrollGlobalOptions;
};
//IScrollUtility.js类文件定义结束--------------------------------------------------------//


