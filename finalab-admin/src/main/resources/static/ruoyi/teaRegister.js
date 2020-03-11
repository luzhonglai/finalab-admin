
$(function() {
    var icon = "<i class='fa fa-times-circle'></i> ";
    $("#tregtForm").validate({
        rules: {
            loginName:{
                required:true,
                minlength: 6,
                maxlength: 16,
                remote: {
                    url:  "/system/user/checkLoginNameUnique",
                    type: "post",
                    dataType: "json",
                    data: {
                        loginName : function() {
                            return $.common.trim($("input[name='loginName']").val());
                        }
                    },
                    dataFilter: function(data, type) {
                        return $.validate.unique(data);
                    }
                }
            },
            userName: {
                required: true
            },
            password: {
                required:true,
                minlength: 6,
                maxlength: 12
            },
            tkey:{
                required:true,
                minlength: 6,
                maxlength: 12,
                remote: {
                    url:  "/system/role/checkTkey",
                    type: "post",
                    dataType: "json",
                    data: {
                        tkey : function() {
                            return $.common.trim($("input[name='tkey']").val());
                        }
                    },
                    dataFilter: function(data, type) {
                        return !$.validate.unique(data);
                    }
                }
            },
        },
        messages: {
            loginName: {
                required: icon + "请输入您的登录账号",
                remote: icon + "该账号已存在",
            },
            userName: {
                required: icon + "请输入您的用户名",
            },
            password: {
                required: icon + "请输入您的密码",
            },
            tkey:{
                required: icon + "请输入口令",
                minlength: icon + "口令不能小于6位",
                maxlength: icon + "口令不能大于12位",
                remote: '口令有误'
            }
        }
    });
});


function regTeacher() {
    if (!$.validate.form()){
        return;
    }
    $.modal.loading("正在注册...");
    $.ajax({
        url: "/Register/teacher",
        data: $('#tregtForm').serialize(),
        async: true,
        dataType: "json",
        type: "POST",
        success: function (data) {
            if (data.code == 0) {
                $.modal.closeLoading();
                $.modal.alertSuccess(data.msg);
                location.href = '/index';
            } else {
                $.modal.closeLoading();
                $.modal.alertError(data.msg);
            }
        },
        error: function (data) {
            $.modal.closeLoading();
            $.modal.alertError(data.msg);
        }
    });
}
