window.SysCmn = window.SysCmn || {};
window.SysApp = window.SysApp || {};

SysCmn.namespace = function (ns) {
    if (!ns || !ns.length) {
        return null;
    }

    var levels = ns.split(".");
    var nsobj = SysCmn;

    // SysCmn is implied, so it is ignored if it is included
    for (var i = (levels[0] == "SysCmn") ? 1 : 0; i < levels.length; ++i) {
        nsobj[levels[i]] = nsobj[levels[i]] || {};
        nsobj = nsobj[levels[i]];
    }

    return nsobj;
};

SysApp.namespace = function (ns) {
    if (!ns || !ns.length) {
        return null;
    }

    var levels = ns.split(".");
    var nsobj = SysApp;

    // SysApp is implied, so it is ignored if it is included
    for (var i = (levels[0] == "SysApp") ? 1 : 0; i < levels.length; ++i) {
        nsobj[levels[i]] = nsobj[levels[i]] || {};
        nsobj = nsobj[levels[i]];
    }

    return nsobj;
};
SysApp.namespace("SysApp.Cmn");
SysApp.namespace("SysApp.Portal");
SysApp.namespace("SysApp.Auth");
SysApp.namespace("SysApp.Sys");
SysApp.namespace("SysApp.Demo");
SysApp.namespace("SysApp.Crm");
SysApp.namespace("SysApp.Pms");