<%@ page contentType="text/html;charset=UTF-8" %>
<%@page isELIgnored="false" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <meta charset="utf-8">
    <title>wangEditor</title>
    <!-- 引入 wangEditor.js -->
    <script src="${ctxStatic}/wangEditor/wangEditor.min.js"></script>

</head>
<style>
    .ShaShiDi{
        width:500px;
        height:400px;
        display:flex;
        align-items:center;
        justify-content:center;
    }

    .ShaShiDi img{
        width:100%;
        height:auto;
    }
    .myclore{
        color: #fff;
        background-color: #31b0d5;
        border-color: #269abc;
    }
    .mycss{
        color: #fff;
        background-color: #31b0d5;
        border-color: #269abc;
    }
</style>
<body>
<div id="div1">
</div>
<textarea name="ueditorContent" id="ueditorContent" style="width:100%; height:200px;display:none" ></textarea>
<script type="text/javascript">
    var E = window.wangEditor;
    var editor = new E('#div1');
   /* var $ueditorContent = $('#ueditorContent');*/
    editor.customConfig.onchange = function (html) {
        // 监控变化，同步更新到 textarea
       /* $ueditorContent.val(html);*/
    };
    editor.customConfig.uploadImgServer = 'a/test/testData/fileUp' ; // 上传图片到服务器，
    // 隐藏“网络图片”tab
    // editor.customConfig.showLinkImg = false;
    editor.customConfig.uploadImgHooks = {
        // （但是，服务器端返回的必须是一个 JSON 格式字符串！！！否则会报错）
        customInsert: function (insertImg, result, editor) {
            // insertImg 是插入图片的函数，editor 是编辑器对象，result 是服务器端返回的结果：
            var url = result.obj;
            insertImg(url);
        },
    },
        editor.create();
    // 初始化 textarea 的值,向后台提交textarea中的值
  /*  $ueditorContent.val(editor.txt.html())*/
    console.info(editor.txt.html());
</script>
</body>
</html>