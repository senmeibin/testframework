// 定义数据输入画面JS类
SysApp.Crm.StudentPopupImport = Class.create();

SysApp.Crm.StudentPopupImport.prototype = Object.extend(new SysApp.Crm.CommonImport(), {
    //显示导入结果
    initTableColList: function () {
        this.colList.push(new ColInfo("导入结果", "importResult", "300", SORT_NO, ALIGN_LEFT, false, CAMEL_NO));
        this.colList.push(new ColInfo("咨询日期", "consultDate", "80", SORT_NO, ALIGN_CENTER));
        this.colList.push(new ColInfo("咨询时间", "consultTime", "80", SORT_NO, ALIGN_CENTER));
        this.colList.push(new ColInfo("学员姓名", "name", "80", SORT_NO, ALIGN_LEFT));
        this.colList.push(new ColInfo("家长姓名", "parentName", "80", SORT_NO, ALIGN_LEFT, false, CAMEL_NO));
        this.colList.push(new ColInfo("性别", "genderName", "40", SORT_NO, ALIGN_CENTER, false, CAMEL_NO));
        this.colList.push(new ColInfo("与学员关系", "relationshipTypeName", "80", SORT_NO, ALIGN_CENTER, false, CAMEL_NO));
        this.colList.push(new ColInfo("手机号码", "mobile", "80", SORT_NO, ALIGN_CENTER, false, CAMEL_NO));
        this.colList.push(new ColInfo("学员年龄", "studentAge", "60", SORT_NO, ALIGN_CENTER, false, CAMEL_NO));
        this.colList.push(new ColInfo("围棋基础", "baseLevelName", "80", SORT_NO, ALIGN_CENTER, false, CAMEL_NO));
        this.colList.push(new ColInfo("咨询方式", "consultMethodName", "80", SORT_NO, ALIGN_CENTER, false, CAMEL_NO));
        this.colList.push(new ColInfo("信息来源", "sourceTypeName", "80", SORT_NO, ALIGN_CENTER, false, CAMEL_NO));
        this.colList.push(new ColInfo("所属校区", "campusName", "100", SORT_NO, ALIGN_CENTER, false, CAMEL_NO));
        this.colList.push(new ColInfo("课程顾问", "consultantUserName", "80", SORT_NO, ALIGN_LEFT, false, CAMEL_NO));
        this.colList.push(new ColInfo("学员状态", "studentStatusName", "80", SORT_NO, ALIGN_CENTER, false, CAMEL_NO));
        this.colList.push(new ColInfo("备注", "remark", "150", SORT_NO, ALIGN_LEFT, false, CAMEL_NO));
    },

    cellClassCallback: function (rowData, colName, colInfo) {
        if (colName == "importResult") {
            if (rowData.importResultFlag == 0) {
                return "warning-blue-cell";
            }
            else {
                return "warning-red-cell";
            }
        }
    },

    //画面DOM初期化后处理
    initInputFormAfter: function () {
    }
});