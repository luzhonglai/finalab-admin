/**
 * 交易页面js封装
 * */
var qkSubmitUrl = '/serve/cases/testTrade'// 闪电下单接口
var sellMap = {};
var buyMap  = {};
$(function () {
    $(document).ready(function () {
        $('input[type=radio][name=ordertype]').change(function (i) {
            var tabRowDom = $(this).parents('.tab-row');
            if (this.value == 'Market_Order') {
                tabRowDom.find(".priceText").hide();
                tabRowDom.find(".tradePrice").hide();
                tabRowDom.find(".tradePrice").val('');
            } else if (this.value == 'Limit_Order') {
                tabRowDom.find(".priceText").show();
                tabRowDom.find(".tradePrice").show();
            }
        });
        $('input[type=radio][name=ordertype1]').change(function (i) {
            var tabRowDom = $(this).parents('.tab-row');
            if (this.value == 'Market_Order') {
                tabRowDom.find(".priceText").hide();
                tabRowDom.find(".tradePrice").hide();
                tabRowDom.find(".tradePrice").val('');
            } else if (this.value == 'Limit_Order') {
                tabRowDom.find(".priceText").show();
                tabRowDom.find(".tradePrice").show();
            }
        });
    });
    $(".priceText").hide();
    $(".tradePrice").hide();
    $(".tradePrice").val('');
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
    ORDER_LIST_SIZE: 7,
    NUMCH: ['一', '二', '三', '四', '五', '六', '七', '八', '九',],

    TRADETYPE_BUY: 'BID',  //挂单类型-买单
    TRADETYPE_SELL: 'ASK', //挂单类型-卖单
    ORDERTYPE_MARKET: 'Market_Order', //市价单
    ORDERTYPE_LIMIT: 'Limit_Order',   //限价单
    ORDERACTION_BID: 'BID', //买单
    ORDERACTION_ASK: 'ASK',//卖单
    ORDERACTION_BID_BOLT: 'BID_BOLT',//闪电下单-买单
    ORDERACTION_ASK_BOLT: 'ASK_BOLT' //闪电下单-卖单

};
var transaction = {
    url: '',
    /**
     * 左侧标的实时价格
     */
    toLocaleString(num){
        return num.toString().replace(/\d{1,3}(?=(\d{3})+(\.\d*)?$)/g,'$&,');
    },
    target: {
        isInit: false,
        targetData: [],
        targetDataMap: {},
        tooltipLocation: '',
        quantityArr: [],
        init: function (datas) {
            transaction.target.targetData = datas;
            var quantityArr = []; 
            for (var i = 0; i < datas.length; i++) {
                var li = transaction.target.createLi(datas[i]);
                // 股票菜单选择添加
                $('.stock-menu').append(`<option>${datas[i].stockId}</option>`)
                if(datas.length !== this.quantityArr.length) this.quantityArr.push({stockId: datas[i].stockId, timeLine: 0});
                $('#targetList').append(li);
            }
            if(!JSON.parse(window.sessionStorage.getItem('tradeQuantity'))){
                window.sessionStorage.setItem('tradeQuantity', JSON.stringify(this.quantityArr))
            }
            transaction.target.isInit = true;
        },
        refresh: function (datas) {
            // console.log('target refresh.....------' + JSON.stringify(datas));
            if (transaction.target.isInit) {
                transaction.target.targetData = datas;
                for (var i = 0; i < datas.length; i++) {
                    var id = '#' + datas[i].stockId;
                    var children = $(id).children();
                    var stockName = datas[i].stockName;
                    if (tdCommon.notEmpty(stockName)) {
                        $(children[0]).text(stockName);
                    }
                    var price = datas[i].price;
                    if (tdCommon.notEmpty(price)) {
                        $(children[1]).text(price);
                    }
                    //根据涨跌幅来更改颜色
                    var vol = datas[i].vol;
                    if (tdCommon.notEmpty(vol)) {
                        $(children[2]).text(vol);
                    }
                    transaction.target.targetDataMap[datas[i].stockId] = datas[i];
                    var location = transaction.target.tooltipLocation;
                    if (location != '') {
                        console.log("当前气泡位置：" + location);
                        var child = $('#' + location).children();
                        $('#tt_sotckId').text(' 标的名称:' + $(child[0]).text() + ' ');
                        $('#tt_newPrice').text(' 最新价:' + $(child[1]).text() + ' ');
                        $('#tt_quantity').text(' 成交量:' + $(child[2]).text() + ' ');
                    }
                    // console.log('刷新股票列表------' + JSON.stringify(transaction.target.targetDataMap))
                }
            } else {
                console.log('股票最新价格列表为初始化');
            }
        },
        createLi: function (data) {
            var li =
                '<li class="tooltip-options" ' +
                'data-placement="bottom" data-html ="true" data-toggle="tooltip" ' +
                'data-original-title="<p id=\'tt_sotckId\' align=\'left\'>loading...</p><p id=\'tt_newPrice\' align=\'left\'> loading...</p><p id=\'tt_quantity\' align=\'left\'> loading...</p>"' +
                'id=\'' + data.stockId  + 'li\'>'

                + '<a onmouseover="javascript:transaction.target.markPosition(this.id, this.id)"'
                + 'onmouseout="javascript:transaction.target.markPosition(this.id, \'\')"' +
                ' id=\'' + data.stockId + '\'href="javascript:transaction.target.onClick(\'' + data.stockId + '\');">'
                + '<span style="width: 50%">' + data.title + '</span>'
                + '<span style="width: 25%">' + transaction.toLocaleString(data.price) + '</span>'
                + '<span style="width: 25%">' + data.vol + '</span>'
                + '</a>'
                + '</li>'
            return li;
        },
        /**
         * @param stockId 股票id
         */
        onClick: function (stockId) {
            console.log("点击股票 ----- " + transaction.priceMove.stockName + '   ' + transaction.priceMove.stockId);
            //跟换分时上的股票名称
            var title = $($('#' + stockId).children()[0]).text();
            $('#stockName-1').text(title);
            $('#stockName-2').text(title);
            $('#trade-title').text(title);
            // $('.DisplayUnit').each(function(){
            //     $(this).text('（' + stockMap[stockId].DisplayUnit + '）');
            // })
            //判断该股票是否允许交易
            var isTradeable = stockMap[stockId].IsTradeable; //1允许， 0：不允许
            if (isTradeable == '1') {
                $('.tradeSell').each(function(i){
                    finalab.UnDisable($('.tradeSell').eq(i));
                    finalab.UnDisable($('.tradeBuy').eq(i));
                })
            } else {
                $('.tradeSell').each(function(i){
                    finalab.disable($('.tradeSell').eq(i));
                    finalab.disable($('.tradeBuy').eq(i));
                })
            }

            //切换股票，并需要重新加载分时图页面(设置股票id， 昨收)
            var yestclose = getStartPrice(stockId);
            console.log('点击 onclick 执行----- yestclose ： ' + yestclose);
            transaction.priceMove.clearAndChangeStock(stockId, title, yestclose);
            changeStock(instanceId, stockId);// 调用 ws 切换股票
        },
        //股票列表气泡鼠标事件
        markPosition: function(id, location) {
            transaction.target.tooltipLocation = location;
            if(location == '') {
                 console.log("鼠标移开：" + location);
                id = '#' + id + 'li';
                $(id).tooltip('destroy');
                $("#id").focus();
            }
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
        getNewestPrice: function () {
            var localData = transaction.priceMove.priceMoveData;
            if (tdCommon.isEmpty(localData) || localData.length == 0) {
                return 0;
            }
            return localData.data[length - 1][1];
        }
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
            $(children[0]).text(transaction.toLocaleString(data.newestPrice));//最新成交价
            $(children[1]).text(data.storeHouse);
            var base = stockMap[transaction.priceMove.stockName].UnitMultiplier;
            var avgBuyPrice = data.avgBuyPrice.includes('--')? '--' : Math.abs(data.avgBuyPrice)* Math.abs(data.storeHouse) / Math.abs(data.storeHouse) / base;
            $(children[2]).text(transaction.toLocaleString(avgBuyPrice.toFixed(2)));//购买价
        },

        refresh: function (data) {
            var childrens = $('#storeHouse').children();
            var children = $(childrens[1]).children();
            if (tdCommon.notEmpty(data.newestPrice)) {
                var range = transaction.priceMove.range;
                tdCommon.changeColor(children[0], range);//最新成交价, 需要根据最新价格的涨跌幅更改颜色
                $(children[0]).text(transaction.toLocaleString(data.newestPrice));
            }
            if (tdCommon.notEmpty(data.storeHouse)) {
                $(children[1]).text(data.storeHouse);//仓位
            }
            if (tdCommon.notEmpty(data.avgBuyPrice)) {
                var base = stockMap[transaction.priceMove.stockName].UnitMultiplier;
                var avgBuyPrice = data.avgBuyPrice.includes('--')? '--' : Math.abs(data.avgBuyPrice) * Math.abs(data.storeHouse) / Math.abs(data.storeHouse)/ base;
                $(children[2]).text(transaction.toLocaleString(avgBuyPrice.toFixed(2)));//购买价
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
            if (!transaction.tradable.istradable()) {
                return;
            };
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
            if (!transaction.tradable.istradable()) {
                return;
            };
            if (!transaction.quickOrder.flag) {
                $.modal.msgWarning('您未开启闪电下单，请开启后下单');
                return;
            }
            var quickQuantity = $('#qk-order-V').val();
            if (tdCommon.isEmpty(quickQuantity)) {
                $.modal.msgWarning("闪电下单，请输入仓位！");
                $('#qk-order-V').focus();
                return;
            }
            //获取当前右侧挂单列表的 ul id 和 li 的 序号，做为计数器，双击的li底色置蓝，3秒后去掉底色
            data.style.color = '#fceec7'
            data.style.backgroundColor = '#a2c7ff';
            var ul = data.parentElement.id;
            var index = $(data).index()+1;
            if (ul == 'buy-list') {
                buyMap[index] =  3;
            } else if (ul == 'sell-list') {
                sellMap[index] = 3;
            }

            var children = $(data).children();


            var quantity = parseInt(Math.round(($('#qk-order-V').val())*100)/100);
            var tradeType = data.textContent.indexOf(WebConst.SELL) != -1 ? WebConst.TRADETYPE_SELL : WebConst.TRADETYPE_BUY;
            var price = null;
            if(tradeType=='ASK')
            {
                price=(Math.round(($(children[2]).text())*100)/100 - Math.round(($('#qk-order-O').val())*100)/100).toFixed(2);
            }else{
                price=(Math.round(($(children[2]).text())*100)/100 + Math.round(($('#qk-order-O').val())*100)/100).toFixed(2);
            }
            var orderAction = data.textContent.indexOf(WebConst.SELL) != -1 ? WebConst.ORDERACTION_ASK_BOLT : WebConst.ORDERACTION_BID_BOLT;
            var maxTradeSize = stockMap[transaction.priceMove.stockId].MaxTradeSize;
            if (Number(quantity) > maxTradeSize) {
                $.modal.msgWarning('交易数量过多');
                return;
            }
            var qk_order_data = new transaction.orderObject(price, quantity, tradeType, WebConst.ORDERTYPE_LIMIT, orderAction);
            // 匹配数据标蓝
            transaction.submit(orderUrl, JSON.stringify(qk_order_data), function (result) {
                var act =  qk_order_data.tradeType == WebConst.TRADETYPE_BUY? '买' : '卖';
                if (result.code == 0) {
                    //将闪电下单数据压入数组，用于匹配标蓝
                    transaction.quickOrder.quickOrderArr.push(data);
                    /*$.modal.msgSuccess( '<p>闪电下单-' + act + '单成功</p><p>股票名：' + qk_order_data.stockName + '</p><p>价格: ' + price + '</p><p>数量: ' + quantity  + '</p>');*/
                } else {
                    $.modal.msgError( '<p>闪电下单-' + act + '单失败</p><p>股票名：' + qk_order_data.stockName + '</p><p>价格: ' + price + '</p><p>数量: ' + quantity  + '</p><p>原因：'+ result.msg +'</p>');
                }
            })
        }
    },
    //定义订单对象
    orderObject: function(price, quantity, tradeType, orderType, orderAction, stockId, stockName){
        var orderData = new Map;
        orderData['instanceId'] = instanceId;
        orderData['courseId'] = courseId;
        orderData['stockId'] = typeof(stockId) == 'undefined' ? transaction.priceMove.stockId : stockId;
        orderData['stockName'] = typeof(stockName) == 'undefined' ? stockMap[transaction.priceMove.stockId].Description : stockName;
        orderData['tradeType'] = tradeType;
        orderData['price'] = price;
        orderData['quantity'] = quantity;
        orderData['orderType'] = orderType;
        orderData['financialType'] = stockMap[transaction.priceMove.stockId].Type.toLowerCase();
        orderData['orderAction'] = orderAction;
        return orderData;
    },

    //定义加仓对象
    addObject: function(stockId, price, quantity, tradeType, stockName) {
        var addData = new Map;
        addData['instanceId'] = instanceId;
        addData['stockId'] = stockId;
        addData['phaseNum'] = thePeriod;
        addData['price'] = price;
        addData['quantity'] = quantity;
        addData['tradeType'] = tradeType;
        addData['traderId'] = userId;
        addData['traderName'] = userName;
        addData['stockName'] = stockName;
        return addData;
    },
    /**
     * 右侧挂单列表
     */
    tradeOrderList: {
        isInit: false,
        init: function (datas) {
            //此处判断数据是否超过 9条
            /*卖单列表*/
            var dataselllen = datas.sell.length;
            if (WebConst.ORDER_LIST_SIZE == 1) {
                $("#tempDiv").height("27%");
            } else {
                $("#tempDiv").height("0%");
            }
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
            /*买单列表*/
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
            if (selllen == 0) {
                return;
            }
            if (selllen == 1) {
                $("#tempDiv").height("27%");
            } else {
                $("#tempDiv").height("0%");
            }
            if (selllen > WebConst.ORDER_LIST_SIZE) selllen = WebConst.ORDER_LIST_SIZE;
            var childrens = $('#sell-list').children();
            var c = WebConst.ORDER_LIST_SIZE - selllen;
            for (var i = 0; i < WebConst.ORDER_LIST_SIZE; i++) {
                var sec = sellMap[i + 1];
                if (sec == 0) {
                    $(childrens[i]).removeAttr('style');
                } else if (sec > 0) {
                    sellMap[i + 1] = --sec;
                }
                if (i < c) {// 需要隐藏
                    var children = $(childrens[i]).children();

                    $(children[1]).text('');
                    $(children[2]).text('');
                    $(children[3]).text('');
                    $(childrens[i]).hide();
                } else {// 需要展示
                    var children = $(childrens[i]).children();
                    // children.context.style.backgroundColor = '#fff';
                    $(children[1]).text(sellarr[WebConst.ORDER_LIST_SIZE - i - 1].traderName);
                    $(children[2]).text(transaction.toLocaleString(sellarr[WebConst.ORDER_LIST_SIZE - i - 1].price));
                    $(children[3]).text(sellarr[WebConst.ORDER_LIST_SIZE - i - 1].quantity);
                    $(childrens[i]).show();
                }
            }
        },
        refreshBuy: function (buyArr) {
            var buylen = buyArr.length;
            if (buylen == 0) {
                return;
            }
            if (buylen == 1) {
                $("#tempDiv").height("27%");
            } else {
                $("#tempDiv").height("0%");
            }
            if (buylen > WebConst.ORDER_LIST_SIZE) buylen = WebConst.ORDER_LIST_SIZE;
            var childrens = $('#buy-list').children();
            for (var i = 0; i < WebConst.ORDER_LIST_SIZE; i++) {
                var sec = buyMap[i + 1];
                if (sec == 0) {
                    $(childrens[i]).removeAttr('style');
                } else if (sec > 0) {
                    buyMap[i + 1] = --sec;
                }
                if (buylen - 1 - i < 0) {// 需要隐藏
                    var children = $(childrens[i]).children();
                    $(children[1]).text('');
                    $(children[2]).text('');
                    $(children[3]).text('');
                    $(childrens[i]).hide();
                } else {
                    var children = $(childrens[i]).children();
                    // children.context.style.backgroundColor = '#fff';
                    $(children[1]).text(buyArr[i].traderName);
                    $(children[2]).text(transaction.toLocaleString(buyArr[i].price));
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
            var parce = transaction.toLocaleString(data.price)
            return '<li ondblclick="transaction.quickOrder.submit(this)" ' + hide + ' onselectstart="return false">' +
                '<span>' + s + WebConst.NUMCH[index] + '</span>' +
                '<span class="gray">' + data.traderName + '</span>' +
                '<span class="' + color + '">' + parce + '</span>' +
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
        timeLine: 0,
        totalTime: 0,
        isNewsTimer: true, // 新闻倒计时开关
        submit: function (isCompel) {
            if(isCompel){
                $('#isCompel,#market-news').hide()
            } else {
                transaction.news.priceOrderSwitch(false);
            }
            var news_price = $('#news-price').text();
            var flag = $('#trade-flag').text();
            var news_quantity = $('#news-quantity').text();
            var news_stockId = $('#news-stockid').text();
            var news_stockName = $('#news-stock-name').text();
            var tradeType = flag === '卖出' ? WebConst.TRADETYPE_SELL : WebConst.TRADETYPE_BUY;
            var userNewsAddData = new transaction.addObject(news_stockId, news_price, news_quantity, tradeType, news_stockName);
            transaction.submit(addUrl, JSON.stringify(userNewsAddData), function (result) {
                if (result.code == 0) {
                    transaction.news.timeCount = 0;
                    $.modal.msgSuccess( '<p>新闻订单-' + flag + '成功</p><p>股票名：' + userNewsAddData.stockName + '</p><p>价格: ' + userNewsAddData.price + '</p><p>数量: ' + userNewsAddData.quantity  + '</p>');
                } else {
                    transaction.news.priceOrderSwitch(false);
                    $.modal.msgError( '<p>新闻订单-' + flag + '失败</p><p>股票名：' + userNewsAddData.stockName + '</p><p>价格: ' + userNewsAddData.price + '</p><p>数量: ' + userNewsAddData.quantity  + '</p>');
                }
            });
        },
        onMarketNews: function (newsData) {
            var newsText = '：' + newsData.content;
            $('#market-news').show();
            $('#market-news').text(newsText);
            // 强制市场新闻
            if(newsData.price !=='#'){
                $('#new-origin-quantity').text(Number(newsData.quantity));
                $('#news-stock-name').text(stockMap[newsData.stockId].Description);
                $('#news-price').text(Number(newsData.price).toFixed(2));
                $('#trade-flag').text(Number(newsData.quantity) < 0 ? '卖出' : '买入');
                $('#news-quantity').text(Math.abs(newsData.quantity));
                $('#news-stockid').text(newsData.stockId);
            }
            if(newsData.price !=='#' && newsData.isCompel == 0){
                $('#isCompel').attr('href','javascript:transaction.news.submit(true);')
                .removeAttr('style',"background-color: #e4e4e4")
                .removeAttr('disabled', 'true');
            }
            if(newsData.isCompel == 1) {
                this.submit();
            }
        },
        onUserNews: function (newsData,speedTimer) {
            transaction.news.timeCount = newsData.timeout;
            $('#new-origin-quantity').text(Number(newsData.quantity));
            $('#news-stock-name').text(stockMap[newsData.stockId].Description);
            $('#news-price').text(newsData.price.toFixed(2));
            $('#trade-flag').text(Number(newsData.quantity) < 0 ? '卖出' : '买入');
            $('#news-quantity').text(Math.abs(newsData.quantity));
            $('#news-stockid').text(newsData.stockId);
            $('#news-timer').text('('+transaction.news.timeCount+'S)');
            $('#user-news').show();
            transaction.news.onTimer(speedTimer)

        },
        //控制新闻下单 “是” 按钮置灰动作，防止用户重复下单
        priceOrderSwitch: function(act) {
            if (act) {
                $('#news_place_order').attr('href','javascript:transaction.news.submit();')
                    .removeAttr('style',"background-color: #e4e4e4")
                    .removeAttr('disabled', 'true');
            } else {
                $('#news_place_order').attr('href','javascript:return false;')
                    .attr('style',"background-color: #e4e4e4")
                    .attr('disabled', 'true');
            }
        },
        onTimer: function(speedTimer){
            if (transaction.news.timer != -1) {
                window.clearInterval(transaction.news.timer);
            }
            transaction.news.timer = window.setInterval(function () {
                if(!transaction.news.isNewsTimer) {
                    window.clearInterval(transaction.news.timer);
                }
                if(transaction.news.timeLine >= transaction.news.totalTime){
                    $('#user-news').hide();
                    transaction.news.priceOrderSwitch(true);
                    window.clearInterval(transaction.news.timer);
                }
                transaction.news.timeCount--;
                $('#news-timer').text('(' + transaction.news.timeCount + 'S)');
                if (transaction.news.timeCount <= 0) {
                    $('#user-news').hide();
                    transaction.news.priceOrderSwitch(true);
                    window.clearInterval(transaction.news.timer);
                }
            }, speedTimer || 1000);
        }

    },

    /**
     * 交易下单，已挂单
     */

    tradeOption: {
        arr:[1,],
        submitOrder: function (tradeType,index) {
            tdCommon.disabled($('.tradeSell').eq(index), 3000);//按钮置灰
            tdCommon.disabled($('.tradeBuy').eq(index), 3000);
            var orderType = $('.radiolist').eq(index).find(`input[name="${index==1 ? 'ordertype1':'ordertype'}"]:checked`).val();
            var stockId = $('.stock-menu option:selected').eq(index).val();
            var quantity = $('.tradeQuantity').eq(index).val();
            var price = $('.tradePrice').eq(index).val();
            var maxTradeSize = stockMap[stockId].MaxTradeSize;
            var stockArr = copyStock.filter(function(item,i) {
                return item.stockId == stockId;
            })[0];
            if(stockArr) var nowQuantity = stockArr.nowQuantity;
            var totalNum = (nowQuantity || 0) + Number(quantity);
            if (totalNum > maxTradeSize) {
                $.modal.msgWarning('交易数量过多');
                var quantity = $('.tradeQuantity').eq(index).val('');
                return; 
            }
            var params = {
                    instanceId: instanceId,
                    traderId: userId,
                    courseId: courseId,
                    loopNum: loopNum,
                    stockId: stockId
                    // thePeriod: thePeriod,
            }
            transaction.submit(constraintUrl, JSON.stringify(params),function(result) {
                if(result.code==0) {
                    if (orderType == 'Limit_Order') {
                        if (tdCommon.isEmpty(quantity) || tdCommon.isEmpty(price)) {
                            $.modal.msgWarning('请输入数量与价格');
                            return;
                        }
                        var minPrice = stockMap[stockId].MinPrice;
                        var maxPrice = stockMap[stockId].MaxPrice;
                        if (Number(price) < minPrice || Number(price) > maxPrice) {
                            $.modal.msgWarning('交易价格不符合预设值');
                            var price = $('.tradePrice').eq(index).val('');
                            return;
                        }
                    } else {
                        if (tdCommon.isEmpty(quantity)) {
                            $.modal.msgWarning('请输入数量');
                            return;
                        }
                    }
        
                    var param = {
                        stockId: stockId,
                        stockName: stockMap[stockId].Description,
                        courseId: courseId,
                        instanceId: instanceId,
                        financialType: stockMap[stockId].Type.toLowerCase(),
                        orderType: orderType,
                        tradeType: tradeType,
                        orderAction: tradeType,
                        quantity: quantity,
                        price: price
                    };
                    console.log('交易下单 - ' + JSON.stringify(param));
                    transaction.submit(orderUrl, JSON.stringify(param), function (result) {
                        if (result.code == 0) {
                            // $.modal.msgSuccess('挂单成功');
                            // $('.tradeQuantity').eq(index).val('');
                            // $('.tradePrice').eq(index).val('');
                            $('#collect').append(
                                '<option  value="' + quantity + '"></option>'
                            )
                        } else {
                            $.modal.msgError('挂单失败');
                        }
                        tdCommon.unDisabled($('.tradeSell').eq(index));
                        tdCommon.unDisabled($('.tradeBuy').eq(index));
                    })
                }else{
                    $.modal.msgWarning('超出交易约束');
                }
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
    },

    derivedVarHandle: function () {

    },

    //首先，遍历stockMap[]，将每个股票对应的期权取到，放到一个set中
    optionMatcher: {
        doMatch: function () {
            var options = [];
            //收集股票和期票存下来 stock 和 option
            $.each(stockMap, function (index, item) {
                if (item.Type.toLowerCase() == 'stock'|| item.Type.toLowerCase() == 'option') {
                    var curStockId = item.Ticker;
                    options.push(curStockId);
                }
            });
            stockAndOptionMatchMap = options;
        },
        calculated: function (msg) {
            var derivedList = [];
            var data = msg;
            if (typeof(data) == 'undefined') data = [];
            $.each(data, function (i, item) {
                var derObj = {};
                var stockName = item.stockName;
                // 股票基数
                var base = stockMap[stockName].UnitMultiplier;
                // 股票基数1 期权基数100
                var newBase = item.type == 'Stock' ? 1 : base;
                var Gammaprice;
                var Thetaprice;
                var Deltaprice = item.dvDeltaprice.toFixed(2);
                if(item.type == 'Stock'){
                    Gammaprice = 0;
                    Thetaprice = 0;
                } else {
                    Gammaprice = item.dvGammaprice.toFixed(2);
                    Thetaprice = item.dvThetaprice.toFixed(2);
                }
                if(typeof(datas['rows']) == 'undefined') datas['rows'] = [];
                // 提取上次数据为零没有显示
                var getStock = datas['rows'].filter(function(item,i) {return item.TargetName == stockName});
                // 校验是否股票和期权股票数据
                if(stockAndOptionMatchMap.includes(stockName)){
                    derObj['TargetName'] = stockName;
                    derObj['Delta'] = parseInt(Deltaprice * item.vol * newBase, 10);
                    derObj['Gamma'] = parseInt(Gammaprice * item.vol * newBase, 10);
                    derObj['Theta'] = - parseInt(Thetaprice * item.vol * newBase, 10);
                    derivedList.push(derObj);
                }
            });
            //总计数据
            var totalStock = derivedList.reduce(function(init,item) {
                var Delta = (init.Delta + item.Delta);
                var Gamma = (init.Gamma + item.Gamma || 0);
                var Theta = (init.Theta + item.Theta || 0);
                return {
                    TargetName: '总计',
                    Delta: Delta,
                    Gamma: Gamma,
                    Theta: Theta,
                }
            })
            // 总计股票数据和
            derivedList.push(totalStock)
            datas['rows'] = derivedList;
        }
    },
    tradable: {
        istradable: function() {
            let isTradeable = stockMap[transaction.priceMove.stockId].IsTradeable;
            if (isTradeable == 0) {
                $.modal.msgWarning('此金融产品目前不可以交易');
                return false;
            }
            return true;
        }
    }


};

var tdCommon = {
    // 根据涨跌幅改变标签颜色 obj 标签对象， range 涨幅
    changeColor: function (obj, range) {
        range > 0 ? $(obj).removeClass('green').addClass('red') : $(obj).removeClass('red').addClass('green');
    },
    isEmpty: function (str) {
        if (str === undefined || str == null || str == '') {
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
    },
    timeout: function (Jobj, sec) {
        window.setTimeout(function () {
            Jobj.removeClass('disabled');
            Jobj.attr('disabled', false);
            console.log('remove 2222222222222222');
        }, sec);
    },
    disabled: function (Jobj, sec) {
        Jobj.addClass('disabled'); // 按钮灰掉，但仍可点击。
        Jobj.prop('disabled', true); // 按钮灰掉，且不可点击。
        tdCommon.timeout(Jobj, sec);
    },
    unDisabled: function (Jobj) {
        Jobj.removeClass('disabled');
        Jobj.attr('disabled', false);
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







