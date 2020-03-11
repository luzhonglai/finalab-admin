String.format = function () {
    if (arguments.length == 0)
        return null;
    var str = arguments[0];
    for (var i = 1; i < arguments.length; i++) {
        var re = new RegExp('\\{' + (i - 1) + '\\}', 'gm');
        str = str.replace(re, arguments[i]);
    }
    return str;
};

var tableMethods = [];
$.extend({
    registTable: function (obj) {
        tableMethods.push(obj);
    },
    initFirstTable: function () {
        if (tableMethods.length == 0) {
            alert("初始化table选项卡失败:未注册table对象.");
            return;
        }
        if (!tableMethods[0].hasOwnProperty('initPage')) {
            alert("初始化table选项卡失败:无[initPage]初始化方法.");
            return;
        }
        tableMethods[0].initPage();
    },
    changeTable: function () {
        $('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
            // 获取已激活的标签页的名称
            var activeTab = $(e.target);
            var li = $(activeTab).parent();
            var index = $(".nav-tabs li").index(li);

            if (index + 1 > tableMethods.length) {
                // alert("切换table失败,table选项卡个数大于注册方法数.");
                $.modal.msgWarning("正在开发中...");
                return;
            }
            tableMethods[index].initPage();
        });
    }
})

var finalab = {
    disable : function (Jobj) {
        Jobj.addClass('disabled'); // 按钮灰掉，但仍可点击。
        Jobj.prop('disabled', true); // 按钮灰掉，且不可点击。
    },
    UnDisable : function (Jobj) {
        Jobj.removeClass('disabled');
        Jobj.attr('disabled', false);
    }
}