var start = Login = {
    checkLogin: function () {
        //获取cookie中的 数据
        var _ticket = $.cookie("TOKEN_KEY");
        if (!_ticket) {
            return;
        }
        $.ajax({
            url: "http://localhost:8083/user/token/" + _ticket,
            dataType: "jsonp",
            type: "GET",
            success: function (data) {
                if (data.status == 200) {
                    var username = data.data.username;
                    var html = username + "，欢迎来到XX！<a href=\"http://www.xuxang.com/user/logout\" class=\"link-logout\">[退出]</a>";
                    $("#loginbar").html(html);
                }
            }
        });
    }
}

$(function () {
    alert("开始查询!");
    // 查看是否已经登录，如果已经登录查询登录信息
    start.checkLogin();
});
