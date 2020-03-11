/**
 * 交易页面js封装
 * */
var qkSubmitUrl = ctx + 'serve/cases/testTrade'// 闪电下单接口
var sunmitOrderUrl = 'http://127.0.0.1:8888/order';//挂单接口
var wsUrl = '';// 交易端webSocket接口地址

$(function() {
        alert('111111');
        $('input[type=radio][name=ordertype]').change(function () {
            alert('45456565');
            if (this.value == 'Market_Order') {
                $("#priceText").show();
                $("#tradePrice").show();
            } else if (this.value == 'Limit_Order') {
                $("#priceText").hide();
                $("#tradePrice").hide();
                // $("#tradePrice").val('');
                // $("#tradePrice").attr('readonly', 'readonly');
            }
        });

})

/**
 * 常量数据
 */
var WebConst = {
    SUCC: '200',
    SELL: '卖',
    BUY: '买',
    BUY_TYPE: 1,
    SELL_TYPE: 2,
    ORDER_LIST_SIZE: 9,
    NUMCH: ['一', '二', '三', '四', '五', '六', '七', '八', '九',]

};
var transaction = {
    url: '',
    /**
     * 左侧标的实时价格
     */
    target: {
        isInit: false,
        targetData: [],
        init: function (datas) {
            transaction.target.targetData = datas;
            for (var i = 0; i < datas.length; i++) {
                var li = transaction.target.createLi(datas[i]);
                $('#targetList').append(li);
            }
            transaction.target.isInit = true;
        },
        refresh: function (data) {
            if (transaction.target.isInit) {
                transaction.target.targetData = data;
                var id = '#' + data.stockId;
                var children = $(id).children();
                $(children[0]).text(data.title);
                $(children[1]).text(data.price);
                tdCommon.changeColor(children[2], data.range);//根据涨跌幅来更改颜色
                $(children[2]).text(data.range + '%');
            } else {
                console.log('股票最新价格列表为初始化');
            }
        },
        createLi: function (data) {
            var color = data.range > 0 ? 'red' : 'green';
            var li = '<li>'
                + '<a id=\'' + data.stockId + '\'href="javascript:transaction.target.onClick(\'' + data.stockId + '\');">'
                + '<span>' + data.title + '</span>'
                + '<span>' + data.price + '</span>'
                + '<span class="' + color + '">' + data.range + '%</span>'
                + '</a>'
                + '</li>'
            return li;
        },
        /**
         * @param stockId 股票id
         */
        onClick: function (stockId) {
            //跟换分时上的股票名称
            var title = $($('#' + stockId).children()[0]).text();
            $('#stockName-1').text(title);
            $('#stockName-2').text(title);
            $('#trade-title').text(title);

            //切换股票，并需要重新加载分时图页面(设置股票id， 昨收)
            var yestclose = stockMap[stockId].liquidationValue;
            transaction.priceMove.clearAndChangeStock(stockId, title, yestclose);
            changeStock(instanceId, stockId);// 调用 ws 切换股票
        }
    },

    /**
     * 分时图价格走势
     */
    priceMove: {
        isInit: false,
        range: 0,//涨跌幅
        stockId: '', // 股票id
        stockName: '',// 股票名称
        mChart: {},
        priceMoveData: [],
        init: function (datas) {
            transaction.priceMove.stockId = datas.stockId;
            transaction.priceMove.stockName = datas.stockName;
            transaction.priceMove.mChart = echarts.init(document.getElementById('m-line'));
            transaction.priceMove.priceMoveData = datas;
            transaction.priceMove.isInit = true;
        },

        refresh: function (data, isIncrement) {
            var myDatas = transaction.priceMove.priceMoveData;
            if (isIncrement) {
                myDatas.data = data;
            } else {
                myDatas.data.push(data);
            }
            var length = myDatas.data.length;
            var currentPrice = myDatas.data[length - 1][1];
            transaction.priceMove.range = ratioCalculate(currentPrice, myDatas.yestclose);
            transaction.storeHouse.refresh({newestPrice: currentPrice});
            transaction.priceMove.mChart.setOption(initMOption(myDatas, 'cn'));
        },
        clearAndChangeStock: function (stockId, stockName, yestcolse) {
            transaction.priceMove.stockId = stockId;
            transaction.priceMove.stockName = stockName;
            transaction.priceMove.range = 0;
            transaction.priceMove.priceMoveData.yestclose = yestcolse;
            transaction.priceMove.priceMoveData.data = [];
        },
    },

    /**
     * 右侧仓位
     */
    storeHouse: {
        storeHouseData: {},
        init: function (data) {
            var childrens = $('#storeHouse').children();
            var children = $(childrens[1]).children();
            var range = transaction.priceMove.range;
            tdCommon.changeColor(children[0], range);
            $(children[0]).text(data.newestPrice);//最新成交价
            $(children[1]).text(data.storeHouse);//仓位
            $(children[2]).text(data.avgBuyPrice);//购买价
        },

        refresh: function (data) {
            var childrens = $('#storeHouse').children();
            var children = $(childrens[1]).children();
            if (tdCommon.notEmpty(data.newestPrice)) {
                var range = transaction.priceMove.range;
                tdCommon.changeColor(children[0], range);//最新成交价, 需要根据最新价格的涨跌幅更改颜色
                $(children[0]).text(data.newestPrice);
            }
            if (tdCommon.notEmpty(data.storeHouse)) {
                $(children[1]).text(data.storeHouse);//仓位
            }
            if (tdCommon.notEmpty(data.avgBuyPrice)) {
                $(children[2]).text(data.avgBuyPrice);//购买价
            }

        }
    },

    /**
     * 闪电下单
     */
    quickOrder: {
        flag: false,//闪电下单开关
        quickOrderArr: [],
        switchs: function () {// 图标开关
            if (transaction.quickOrder.flag) {
                $('#qk-order').addClass('gray');
                $('#qk-order-O').val('').attr('readonly', 'readonly');
                $('#qk-order-V').val('').attr('readonly', 'readonly');
                transaction.quickOrder.flag = false;
            } else {
                $('#qk-order').removeClass('gray');
                $('#qk-order-O').removeAttr('readonly');
                $('#qk-order-V').removeAttr('readonly');
                transaction.quickOrder.flag = true;
            }
        },
        submit: function (data) {// 双击挂单列表事件
             data.style.backgroundColor = '#06c';
            if (!transaction.quickOrder.flag) {
                // $.modal.msgWarning('您未开启闪电下单，请开启后下单');
                alert('您未开启闪电下单，请开启后下单');
                return;
            }
            var children = $(data).children();
            var userId = $(children[1]).text();
            var price = $(children[2]).text();
            var volumn = $(children[3]).text();
            alert(userId + '  ' + price + '  ' + volumn);
            if (title.indexOf(WebConst.SELL) != -1) {// 双击卖单

            } else {// 双击买单

            }

            //TODO 表单值加挂单值计算，然后提交
            // 匹配数据标蓝
            var data = {id: '12132', data: 'nihaoaoah哈哈哈'};
            transaction.submit(qkSubmitUrl, JSON.stringify(data), function (result) {
                if (result.code = WebConst.SUCC) {
                    //将闪电下单数据压入数组，用于匹配标蓝
                    transaction.quickOrder.quickOrderArr.push(data);
                }
            })
        }
    },

    /**
     * 右侧挂单列表
     */
    tradeOrderList: {
        isInit: false,
        init: function (datas) {
            //此处判断数据是否超过 9条
            var dataselllen = datas.sell.length;
            if (dataselllen > WebConst.ORDER_LIST_SIZE) dataselllen = WebConst.ORDER_LIST_SIZE;
            for (var i = 0, j = WebConst.ORDER_LIST_SIZE - 1; i < WebConst.ORDER_LIST_SIZE; i++, j--) {
                var sell = '';
                var c = WebConst.ORDER_LIST_SIZE - dataselllen;
                if (i < c) {
                    sell = transaction.tradeOrderList.createLi(null, WebConst.SELL_TYPE, j, false);
                } else {
                    sell = transaction.tradeOrderList.createLi(datas.sell[i - c], WebConst.SELL_TYPE, j, true);
                }
                $('#sell-list').append(sell);
            }

            var datasbuylen = datas.buy.length;
            if (datasbuylen > WebConst.ORDER_LIST_SIZE) datasbuylen = WebConst.ORDER_LIST_SIZE;
            for (var i = 0; i < WebConst.ORDER_LIST_SIZE; i++) {
                var buy = '';
                if (datasbuylen - 1 - i < 0) {
                    buy = transaction.tradeOrderList.createLi(datas.buy[i], WebConst.BUY_TYPE, i, false);
                } else {
                    buy = transaction.tradeOrderList.createLi(datas.buy[i], WebConst.BUY_TYPE, i, true);
                }
                $('#buy-list').append(buy);
            }
            transaction.tradeOrderList.isInit = true;
        },
        refreshSell: function (sellarr) {
            var selllen = sellarr.length;
            if (selllen > WebConst.ORDER_LIST_SIZE) selllen = WebConst.ORDER_LIST_SIZE;
            var childrens = $('#sell-list').children();
            var c = WebConst.ORDER_LIST_SIZE - selllen;
            for (var i = 0; i < WebConst.ORDER_LIST_SIZE; i++) {
                if (i < c) {// 需要隐藏
                    var children = $(childrens[i]).children();
                    $(children[1]).text('');
                    $(children[2]).text('');
                    $(children[3]).text('');
                    $(childrens[i]).hide();
                } else {// 需要展示
                    var children = $(childrens[i]).children();
                    $(children[1]).text(sellarr[i - c].traderName);
                    $(children[2]).text(sellarr[i - c].price);
                    $(children[3]).text(sellarr[i - c].quantity);
                    $(childrens[i]).show();
                }
            }
        },
        refreshBuy: function (buyArr) {
            var buylen = buyArr.length;
            if (buylen > WebConst.ORDER_LIST_SIZE) buylen = WebConst.ORDER_LIST_SIZE;
            var childrens = $('#buy-list').children();
            for (var i = 0; i < WebConst.ORDER_LIST_SIZE; i++) {
                if (buylen - 1 - i < 0) {// 需要隐藏
                    var children = $(childrens[i]).children();
                    $(children[1]).text('');
                    $(children[2]).text('');
                    $(children[3]).text('');
                    $(childrens[i]).hide();
                } else {
                    var children = $(childrens[i]).children();
                    $(children[1]).text(buyArr[i].traderName);
                    $(children[2]).text(buyArr[i].price);
                    $(children[3]).text(buyArr[i].quantity);
                    $(childrens[i]).show();
                }
            }
        },
        //根据type类型创建挂单li元素
        createLi: function (data, type, index, isShow) {// type 1 : 买， 2 ： 卖
            data = tdCommon.isEmpty(data) ? {traderId: '', price: '', quantity: ''} : data;
            var hide = isShow ? '' : 'hidden'
            var s = type == WebConst.BUY_TYPE ? '买' : '卖';
            var color = type == WebConst.BUY_TYPE ? 'red' : 'green';
            return '<li ondblclick="transaction.quickOrder.submit(this)" ' + hide + '>' +
                '<span>' + s + WebConst.NUMCH[index] + '</span>' +
                '<span class="gray">' + data.traderName + '</span>' +
                '<span class="' + color + '">' + data.price + '</span>' +
                '<span>' + data.quantity + '</span>' +
                '</li>'
        }
    },

    /**
     * 新闻
     */
    news: {
        timer: -1,
        timeCount: 0,
        submit: function () {
            var price = $('#news-price').text();
            var flag = $('#trade-flag').text();
            var quantity = $('#news-quantity').text();
            var stockId = $('#news-stockId').text();
        },
        onNews: function (newsData) {
            if (transaction.news.timer != -1) {
                window.clearInterval(transaction.news.timer);
            }
            $('#user-news').show();
            
            $('#news-price').text(Math.abs(newsData.price));
            $('#trade-flag').text(Number(newsData.price) < 0 ? '卖出' : '买入');
            $('#news-quantity').text(newsData.quantity);
            $('#news-stockid').text(newsData.stockId);
            
            transaction.news.timeCount = newsData.timeout;
            transaction.news.timer = window.setInterval(function () {
                transaction.news.timeCount--;
                if (transaction.news.timeCount <= 0) {
                    $('#user-news').hide();
                    window.clearInterval(transaction.news.timer);
                }
                $('#news-timer').text(transaction.news.timeCount);
            }, newsData.timeout);
        }
    },

    /**
     * 交易下单，已挂单
     */
    tradeOption: {
        submitOrder: function (tradeType) {
            var orderType = $('#order-option input[name="ordertype"]:checked').val();
            var quantity = $('#tradeQuantity').val();
            var price = $('#tradePrice').val();
            var stockId = transaction.priceMove.stockId;
            var param = {
                courseId: courseId,
                instanceId: instanceId,
                financialType: stockMap[stockId].financialType,
                orderType: orderType,
                tradeType: tradeType,
                quantity: quantity,
                price: price
            };
            console.log('交易下单 -----  ' + JSON.stringify(param));
            transaction.submit(orderUrl, JSON.stringify(param), function (result) {
                console.log('-------------*****' + result + "  " + JSON.stringify(result));
                alert(result + "  " + JSON.stringify(result));
            })
        }
    },

    /**
     * 持仓盈亏明细
     */
    storeHouseDetail: {},

    /**
     * 平仓盈亏明细
     */
    coverStoreHouse: {},

    /**
     * 交易明细
     */
    tradeDetail: {},

    /**
     * 用户信息
     */
    userData: {},

    submit: function (url, data, fun) {
        $.ajax({
            type: 'post',
            url: url,
            contentType: "application/json; charset=utf-8",
            data: data,
            dataType: "json",
            success: function (message) {
                fun(message);
            },
            error: function (message) {
                console.log('调用服务端异常 url - ' + url + ' param - ' + data + 'message - ' + message);
            }
        });
    }
};

var tdCommon = {
    // 根据涨跌幅改变标签颜色 obj 标签对象， range 涨幅
    changeColor: function (obj, range) {
        range > 0 ? $(obj).removeClass('green').addClass('red') : $(obj).removeClass('red').addClass('green');
    },
    isEmpty: function (str) {
        if (str == null || str === undefined || str == '') {
            return true;
        }
        return false;
    },
    notEmpty: function (str) {
        return !tdCommon.isEmpty(str);
    },
    isRealNum: function (val) {
        // isNaN()函数 把空串 空格 以及NUll 按照0来处理 所以先去除
        if (val === "" || val == null) {
            return false;
        }
        if (!isNaN(val)) {
            return true;
        } else {
            return false;
        }
    }

}


// ---------自测代码------------------
$(function () {
    //--------------标的最近测试--------------------
    console.log('init list');
    //datas元素字段示例 {id:'', title :'',priceArr:'',range :''};
    // var datas = [];
    // for (var i = 0; i < 10; i++) {
    //     var data = {};
    //     data.stockId = i;
    //     data.title = '中石油-' + i;
    //     data.price = Math.floor(Math.random() * 10000) / 100;
    //     if (Math.floor(Math.random() * 100) % 2 == 0) {
    //         data.range = '-' + (Math.floor(Math.random() * 1000) / 100);
    //     } else {
    //         data.range = (Math.floor(Math.random() * 1000) / 100);
    //     }
    //     datas.push(data);
    // }
    // transaction.target.init(datas);
    // var targetcount = 0;
    // var interva = window.setInterval(function () {
    //     var data = {};
    //     targetcount++;
    //     data.id = Math.floor(Math.random() * 10);
    //     data.price = Math.floor(Math.random() * 10000) / 100;
    //     var targetRange = (Math.floor(Math.random() * 1000) / 100);
    //     if (countNum % 2 == 0) {
    //         data.range = -targetRange;
    //     } else {
    //         data.range = targetRange;
    //     }
    //     transaction.target.refresh(data);
    // }, 1300);
    //--------------价格走势测试------------------

    // var priceData = {data: [], yestclose: '38.5', stockId: 'dfs'}
    // transaction.priceMove.init(priceData);
    // var timeount = 0;
    //
    // var priceArr = [];
    // var interval = window.setInterval(function () {
    //     timeount++;
    //     if (timeount == 240) {
    //         timeount = 0;
    //         transaction.priceMove.clear();
    //         window.clearInterval(interval);
    //     }
    //     var data = [timeount, Math.random() * 200, Math.random() * 100, Math.round(Math.random() * 1000)]
    //     priceArr.push(data);
    //     transaction.priceMove.refresh(priceArr, true);
    // }, 1000);

    //-----------仓位测试-------------

    // var storeHouseData = {
    //     range: '9.8',
    //     newestPrice: '36.7',
    //     storeHouse: '300',
    //     avgBuyPrice: '46.9'
    // };
    // transaction.storeHouse.init(storeHouseData);

    // var countNum = 0
    // var storeHouseInterval = window.setInterval(function () {
    //     countNum++
    //     var range = Math.floor(Math.random() * 100) / 10;
    //     var newprice = Math.floor(Math.random() * 1000) / 10;
    //     var shoreHouseV = Math.floor(Math.random() * 1000) / 10;
    //     if (countNum % 2 == 0) {
    //         range = -range;
    //     }
    //     storeHouseData = {};
    //     storeHouseData.range = range;
    //     storeHouseData.newestPrice = newprice;
    //     storeHouseData.storeHouse = shoreHouseV;
    //     transaction.storeHouse.refresh(storeHouseData);
    //
    // }, 1000);


    //-----------------右侧挂单列表--------
    //init 入参实例 {sell: [{userId:'', price:'', volumn:''}], buy: [{userId:'', price:'', volumn:''}]}
    // var selldatas = {sell: [], buy: []};
    // for (var i = 0; i < 7; i++) {
    //     var data = {
    //         traderId: Math.floor(Math.random() * 1000),
    //         price: (Math.floor(Math.random() * 1000)) / 10,
    //         quantity: Math.floor(Math.random() * 20000)
    //     };
    //     selldatas.sell.push(data);
    //     selldatas.buy.push(data);
    // }
    // transaction.tradeOrderList.init(selldatas);
    //
    // var tradeInterval = window.setInterval(function () {
    //     selldatas = {sell: [], buy: []};
    //     var rdm = Math.floor(Math.random() * 10) - 1;
    //     for (var i = 0; i < rdm; i++) {
    //         var selldata = {
    //             traderId: Math.floor(Math.random() * 1000),
    //             price: (Math.floor(Math.random() * 1000)) / 10,
    //             quantity: Math.floor(Math.random() * 20000)
    //         };
    //         selldatas.sell.push(selldata);
    //         selldatas.buy.push(selldata);
    //     }
    //     transaction.tradeOrderList.refreshSell(selldatas.sell);
    //     transaction.tradeOrderList.refreshBuy(selldatas.buy);
    // }, 1000);

    //----------------------------------------
    // alert(tdCommon.isRealNum());
})







