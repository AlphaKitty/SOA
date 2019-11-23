<%@ page language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="zh">

<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge" charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=9"/>
    <!-- 响应试标记 -->
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- bootstrap4.0 样式 -->
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>登录页面</title>
        <!-- <link rel="stylesheet" type="text/css" href="./css/login.css"> -->
        <link rel="stylesheet" href="${pageContext.request.contextPath }/jquery/bootstrap/css/tether.min.css"/>
        <link rel="shortcut icon " type="images/x-icon" href="${pageContext.request.contextPath}/images/favicon.ico">
        <link rel="stylesheet" href="${pageContext.request.contextPath }/jquery/bootstrap/css/bootstrap.min.css"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/ace/css/font-awesome.min.css"/>
        <link rel="stylesheet"
              href="${pageContext.request.contextPath }/jquery/bootstrap-validator/css/bootstrapValidator.css"/>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/login.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/ace/custom-style.css"/>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/ace/css/ace.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/jquery/bt-icons/icon.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/jquery/bt-icons/style.css">
        <script type="text/javascript">
            var basePath = "${pageContext.request.contextPath}";
        </script>
    </head>
    <title>首页</title>
    <style>
        .input-group .input-group-addon {
            float: left;
        }

        .form-control {
            width: 260px !important;
            height: 34px;
            float: left !important;
        }

        .on {
            display: none;
            margin-top: 0;
        }
    </style>
</head>

<body class="defauleSkin dialog-html">
<div id="play">
    <div class="col-xs-1 col-sm-1 language">
        <div class="btn-group">
            <button data-toggle="dropdown" class="btn btn-primary btn-white dropdown-toggle">
                简体中文
            </button>

            <ul class="dropdown-menu">
                <li><a href="#">简体中文</a></li>

                <li><a href="#">English</a></li>
            </ul>
        </div>
    </div>
    <ul id="ul">
        <li class="li1">
            <img src="${pageContext.request.contextPath}/images/banner.png" alt="">
        </li>
        <li class="li2">
            <img src="${pageContext.request.contextPath}/images/banner1.jpg" alt="">
        </li>
        <li class="li3">
            <img src="${pageContext.request.contextPath}/images/banner2.jpg" alt="">
        </li>
    </ul>
    <ol id="ol">
        <li class="bg"></li>
        <li></li>
        <li></li>
    </ol>

    <div class="container">
        <form class="resources-from" method="post" id="defaultForm" role="form">
            <div class="row">
                <div class="col-sm-12 column">
                    <div class="logo">
                        <img src="${pageContext.request.contextPath }/images/logo.png">
                    </div>
                </div>
            </div>
            <div class="row form-input">
                <div class="col-sm-12 column">
                    <div class="form-group">
                        <div class="input-group ">
                            <span class="input-group-addon icon fa bt-icon43  "></span>
                            <input class="form-control loginInput" type="text" maxlength="30" name="usname" value=""
                                   id="username" placeholder="请输入用户名"/>
                        </div>
                    </div>
                </div>
            </div> <!-- row -->
            <div class="row form-input">
                <div class="col-sm-12  column">
                    <div class="form-group">
                        <div class="input-group ">
                            <span class="input-group-addon icon fa bt-icon19  "></span>
                            <input class="form-control loginInput" type="password" name="psword" id="password" value=""
                                   placeholder="请输入密码"/>
                        </div>
                    </div>
                </div>
            </div> <!-- row -->

            <div class="row form-input">
                <div class="col-sm-12 column">
                    <div class="form-group">
                        <div class="input-group ">
                            <a href="javascript:" class="show">高级选项</a>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row form-input on">
                <div class="col-sm-12 column">
                    <div class="form-group">
                        <div class="input-group ">
                            <span class="input-group-addon icon bt-icon29  "></span>
                            <input class="form-control loginInput" type="text" maxlength="50" name="cstmId" id="cstmId"
                                   value="demo"/>
                        </div>
                    </div>
                </div>
            </div>
            <!-- row -->

            <div class="row">
                <div class="col-sm-12  column">
                    <div class="input-group">
                        <button type="button" class="btn btn-primary login-btn" id="ck_rmbUser" onclick="Save()">登录
                        </button>
                    </div>
                </div>
            </div> <!-- row -->
            <div class="row">
                <div class="col-sm-12  column">
                    <div class="autominate-l">
                        <label class="inline">
                            <input type="checkbox" name="remember-me" id="remember-me" class="ace">
                            <span class="lbl">记住密码</span>
                        </label>
                    </div>
                    <div class="autominate-r">
                        <label class="inline">
                            <input type="checkbox" name="rememberMe" id="rememberMe" class="ace">
                            <span class="lbl">自动登录</span>
                        </label>
                    </div>
                </div>
            </div><!-- row -->
        </form>
    </div>
</div>
<!-- bootstrap插件 -->
<script type="text/javascript" src="${pageContext.request.contextPath }/jquery/jquery/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/jquery/jquery/jquery.cookie.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/jquery/bootstrap/js/tether.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/jquery/custom/bunnytouch.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/jquery/bootstrap/js/bootstrap.min.js"></script>
<%-- <script type="text/javascript" src="${pageContext.request.contextPath }/ace/js/ace.min.js"></script> --%>
<script src="${pageContext.request.contextPath}/jquery/bootstrap-validator/js/bootstrapValidator.min.js"></script>
<script src="${pageContext.request.contextPath}/jquery/bootstrap-validator/js/language/<fmt:message key="locale"/>.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        if ($.cookie("rmbUser") == "true") {
            $("#remember-me").attr("checked", true);
            $("#rememberMe").attr("checked", true);
            $("#username").val($.cookie("username"));
            $("#password").val($.cookie("password"));
        }
    });

    //记住用户名密码
    function Save() {
        if ($("#remember-me").prop("checked")) {
            var str_username = $("#username").val();
            var str_password = $("#password").val();
            $("#rememberMe").prop("checked");
            $.cookie("rmbUser", "true", {expires: 7}); //存储一个带7天期限的cookie
            $.cookie("username", str_username, {expires: 7});
            $.cookie("password", str_password, {expires: 7});
        } else {
            $.cookie("rmbUser", "false", {expire: -1});
            $.cookie("username", "", {expires: -1});
            $.cookie("password", "", {expires: -1});
        }
    }

    /*$(document).ready(function(){
        //读取 localStage 本地存储，填充用户名密码,如果自动登录有值直接跳转；
        //相反，跳转到本页面,等待登陆处理
        var storage = window.localStorage;
        var getname = storage["username"];
        var getPwd = storage["password"];
        var getisstroepwd = storage["isstorePwd"];
        var getisautologin = storage["isautologin"];
        if("yes" == getisstroepwd)
        {
            if("yes" == getisautologin)
            {
                if(( ('' != getname) ||(null != getname)) && (('' != getPwd) ||(null != getPwd)))
                {
                    //lacoste 已经保存 登陆信息 直接登陆
                    $("#username").val(getname);
                    $("#password").val(getPwd);
                    window.location="";
                }
            }
        else
            {
                $("#username").val(getname);
                $("#password").val(getPwd);
                document.getElementById("remember-me").checked = true;
            }
        }
    });
    function loginCheckAjaxFunction()
    {
        //ajax 登陆验证
        var userAccout=$("#username").val();
        var userPassWord=$("#password").val();
        var storage = window.localStorage;
        //记住密码
        if(document.getElementById("remember-me").checked)
        {
            //存储到loaclStage
            storage["username"] = userAccout;
            storage["password"] = userPassWord;
            storage["isstorePwd"] = "yes";
        }
    else
        {
            storage["username"] = userAccout;
            storage["isstorePwd"] = "no";
        }
        //下次自动登录
        if(document.getElementById("rememberMe").checked)
        {
            //存储到loaclStage
            storage["username"] = userAccout;
            storage["password"] = userPassWord;
            storage["isstorePwd"] = "yes";
            storage["isautologin"] = "yes";
        }
    else
        {
            storage["username"] = userAccout;
            storage["isautologin"] = "no";
        }
    }*/

    $(function () { /* 文档加载，执行一个函数*/
        $(".login-btn").on("click", function () {
            $('#defaultForm').submit();
        });
        //密码回车登录
        $('.loginInput').keydown(function (event) {

            if (event.keyCode == 13) {
                $('#defaultForm').submit();
            }
        });
        $('#defaultForm').bootstrapValidator({
            message: 'This value is not valid',
            feedbackIcons: { /*input状态样式图片*/
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: { /*验证：规则*/
                usname: { //验证input项：验证规则
                    message: 'The username is not valid',

                    validators: {
                        notEmpty: { //非空验证：提示消息
                            message: '用户名不能为空'
                        },
                        stringLength: {
                            min: 6,
                            max: 30,
                            message: '用户名长度必须在6到30之间'
                        },
                        threshold: 6, //有6字符以上才发送ajax请求，（input中输入一个字符，插件会向服务器发送一次，设置限制，6字符以上才开始）
                        /* remote: { //ajax验证。server result:{"valid",true or false} 向服务发送当前input name值，获得一个json数据。例表示正确：{"valid",true}
                            url: 'exist2.do', //验证地址
                            message: '用户已存在', //提示消息
                            delay: 2000, //每输入一个字符，就发ajax请求，服务器压力还是太大，设置2秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
                            type: 'POST' //请求方式

                        }, */
                        regexp: {
                            regexp: /^[a-zA-Z0-9_\.]+$/,
                            message: '用户名由数字字母下划线.组成'
                        }
                    }
                },
                psword: {
                    message: '密码无效',
                    validators: {
                        notEmpty: {
                            message: '密码不能为空'
                        },
                        stringLength: {
                            min: 6,
                            max: 30,
                            message: '密码长度必须在6到30之间'
                        },

                    }
                },
            }
        })
            .on('success.form.bv', function (e) { //点击提交之后
                // Prevent form submission
                e.preventDefault();
                // Get the form instance
                var $form = $(e.target);

                // Get the BootstrapValidator instance
                var bv = $form.data('bootstrapValidator');
                // Use Ajax to submit form data 提交至form标签中的action，result自定义
                $.ajax({
                    type: "POST",
                    url: basePath + "/admin/user/login",
                    data: $form.serialize(),
                    dataType: "json",
                    success: function (result) {
                        if (result.status == 200) {
                            window.location.href = basePath + "/home/index";
                        }
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        if (XMLHttpRequest.status) {
                            var failedText;
                            if (XMLHttpRequest.responseText.search("Authentication Failed: Customer is UnAvailable") > -1) {
                                failedText = '客户域被冻结';
                            } else if (XMLHttpRequest.responseText.search("Authentication Failed: User is UnAvailable") > -1) {
                                failedText = '此用户被冻结';
                            } else {
                                failedText = '用户名或者密码错误';
                            }
                            var $div = $("input[name='psword']").parent("div").parent("div");
                            $("#errorPsword").remove();
                            $div.append('<small class="help-block" data-bv-validator="remote" data-bv-for="psword" data-bv-result="VALID" id="errorPsword">' + failedText + '</small>');
                            $div.addClass("has-feedback has-error");
                        }
                    }
                });
            });
    });
</script>
<script>
    // 高级选项
    $('.show').click(function () {
        if ($(".on").css("display") == "none") {
            $(".on").show();
        } else {
            $(".on").hide();
        }
    });
</script>
<script>
    // 主导航 轮播图
    var index = 0;
    $(function () {
        play();
    });
    $(window).on("resize", function () {
        var obj = {
            width: $(window).width(),
            height: $(window).height()
        };
        doResize(obj);
    });

    function play() {
        var obj = {
            width: $(window).width(),
            height: $(window).height()
        };
        doResize(obj);
        var index = 0;
        run();

        function run() {
            autochange = setInterval(function () {
                if (index < ($("#play>#ul>li img").length - 1)) {
                    index++;
                } else {
                    index = 0;
                }


                // 调用轮播函数
                changeTo(index);
            }, 5000);
        }

        $('#play>#ol>li').each(function (k) {
            $(this).hover(function () {
                $(this).css('cursor', 'pointer');
                clearInterval(autochange);
                changeTo(k);
                index = k;
            }, function () {
                autochange = setInterval(function () {
                    if (index < $('#play>ul>li img').length - 1) {
                        index++;
                    } else {
                        index = 0;
                    }
                    // 调用轮播函数
                    changeTo(index);
                }, 5000);
            });
        });

        $('#play>ul>li img').each(function () {
            $(this).mouseover(function () {
                clearInterval(autochange);
                $(this).css('cursor', 'pointer');
                $('#prev').css('display', 'block');
                $('#next').css('display', 'block');
            });
            $(this).mouseout(function () {
                run();
                $('#prev').css('display', 'none');
                $('#next').css('display', 'none');
            });
        });
    }

    function changeTo(num) {
        $('#play>#ul>li img').css({"opacity": 0, "z-index": 1});
        $('#play>ul>li img').eq(num).css({"opacity": 1, "z-index": 99});
        $('#play>#ol>li').eq(num).addClass('bg').removeClass('bgc').siblings().removeClass('bg').addClass('bgc');
    }

    function doResize(obj) {
        $('#play').changeSize(obj);
        $('.li1').changeSize(obj);
        $('.li2').changeSize(obj);
        $('.li3').changeSize(obj);
    }
</script>
</body>

</html>
