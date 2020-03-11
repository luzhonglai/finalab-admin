
$(function() {
    console.log("stu123")

    var icon = "<i class='fa fa-times-circle'></i> ";
    $("#regForm").validate({
        rules: {
            loginName:{
                required:true,
                minlength: 2,
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
            }
        },
        messages: {
            loginName: {
                required: icon + "请输入您的登录账号",
            },
            userName: {
                required: icon + "请输入您的用户名",
            },
            password: {
                required: icon + "请输入您的密码",
            }
        }

    })
    window.setInterval(function () {
        var userName = $("input[name='userName']").val()
        var password = $("input[name='password']").val()
        var loginName = $("input[name='loginName']").val()
        if(loginName == "" && password == "" & userName == ""){
            $("input[name='userName']").val("")
            $("input[name='password']").val('')
            $("input[name='loginName']").val('')
        }
    },1000)

});

$.validator.setDefaults({
    submitHandler: function() {
        if ($.validate.form()) {
            reg();
        }
    }
});

function reg() {
    $.modal.loading("正在注册...");
    var loginName = $.common.trim($("input[name='loginName']").val());
    var userName = $.common.trim($("input[name='userName']").val());
    var password = $.common.trim($("input[name='password']").val());

    $.ajax({
        url: "/Register/student",
        data: {
            "loginName":loginName,
            "userName":userName,
            "password":password
        },
        async: true,
        dataType: "json",
        type: "POST",
        success: function (data) {
            window.open("/login",'_self')
            if (data.code == 0) {
                $.modal.closeLoading();
                $.modal.alertSuccess(data.msg);
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
