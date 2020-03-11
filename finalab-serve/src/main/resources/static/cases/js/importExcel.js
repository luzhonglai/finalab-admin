/**
 * 导入文件
 */
function importExp() {
    var formData = new FormData();
    var name = $("#excelFile").val();
    formData.append("file",$("#excelFile")[0].files[0]);
    formData.append("name",name);
    $.ajax({
        url : 'importExcel',
        type : 'POST',
        async : false,
        data : formData,
        // 告诉jQuery不要去处理发送的数据
        processData : false,
        // 告诉jQuery不要去设置Content-Type请求头
        contentType : false,
        beforeSend:function(){
            console.log("正在进行，请稍候");
        },
        success : function(responseStr) {

            if(responseStr.code==0){
                $.modal.msgSuccess(responseStr.msg);
            }else{
                $.modal.msgError(responseStr.msg);
            }
        }
    });
}

