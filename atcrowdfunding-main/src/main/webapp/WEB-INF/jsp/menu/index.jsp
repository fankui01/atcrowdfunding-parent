<%--
  Created by IntelliJ IDEA.
  User: 范奎
  Date: 2020/7/27
  Time: 14:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <%@include file="/WEB-INF/common/css.jsp" %>
    <style>
        .tree li {
            list-style-type: none;
            cursor: pointer;
        }

        table tbody tr:nth-child(odd) {
            background: #F4F4F4;
        }

        table tbody td:nth-child(even) {
            color: #C00;
        }
    </style>
</head>

<body>

<jsp:include page="/WEB-INF/common/top.jsp"></jsp:include>

<div class="container-fluid">
    <div class="row">
        <jsp:include page="/WEB-INF/common/menu.jsp"></jsp:include>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
                </div>
                <div class="panel-body">
                    <ul id="treeDemo" class="ztree"></ul>
                </div>
            </div>
        </div>
    </div>
</div>

<%@include file="/WEB-INF/common/js.jsp" %>
<script type="text/javascript">
    $(function () {
        $(".list-group-item").click(function () {
            if ($(this).find("ul")) {
                $(this).toggleClass("tree-closed");
                if ($(this).hasClass("tree-closed")) {
                    $("ul", this).hide("fast");
                } else {
                    $("ul", this).show("fast");
                }
            }
        });
        showTree();
    });

    function showTree() {
        var setting = {
            data: {
                simpleData: {
                    enable: true,
                    pIdKey: 'pid'
                }
            },
            view: {
                // 增加自定义图标
                addDiyDom: customeIcon,
                // 鼠标移入到节点上显示按钮组
                addHoverDom: customeAddBtn,
                // 鼠标移出节点后不显示按钮组
                removeHoverDom: customeRemoveBtn
            }
        };

        var zNodes = {};
        $.get("${PATH}/menu/loadTree", {}, function(result) {
            zNodes = result;
            // 增加根节点
            zNodes.push({"id": 0, "name": "系统权限菜单", "icon": "glyphicon glyphicon-th-list", "children": []});
            // 初始化数
            var treeObj = $.fn.zTree.init($("#treeDemo"), setting, zNodes);
            // 获取数并展开所有节点
            // var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
            treeObj.expandAll(true);
        });
    }

    // 给树节点增加自定义字体图标
    // treeId：表示生成数的id
    // treeNode：节点对象，一个节点对象就相当于一个TMenu对象
    function customeIcon(treeId, treeNode) {
        // tId：由treeId + "_" + 序号
        // tId + "_ico"：获取显示字体图标的元素
        // tId + "_span"：获取显示节点名称的元素
        $("#" + treeNode.tId + "_ico").removeClass();
        $("#" + treeNode.tId + "_span").before("<span class='" + treeNode.icon + "'></span>");
    }

    // 鼠标移入到节点上显示按钮组
    function customeAddBtn(treeId, treeNode){
        var aObj = $("#" + treeNode.tId + "_a");
        // 禁用链接
        aObj.attr("href", "javascript:;");
        // 禁用单击事件
        aObj.attr("onclick", "return false;");

        if (treeNode.editNameFlag || $("#btnGroup" + treeNode.tId).length > 0) {
            return;
        }
        var s = '<span id="btnGroup' + treeNode.tId + '">';
        if ( treeNode.level == 0 ) { // 根节点
            s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" href="#" >&nbsp;&nbsp;<i class="fa fa-fw fa-plus rbg "></i></a>';
        } else if ( treeNode.level == 1 ) { // 分支节点
            s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;"  href="#" title="修改权限信息">&nbsp;&nbsp;<i class="fa fa-fw fa-edit rbg "></i></a>';
            if (treeNode.children.length == 0) {
                s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" href="#" >&nbsp;&nbsp;<i class="fa fa-fw fa-times rbg "></i></a>';
            }
            s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" href="#" >&nbsp;&nbsp;<i class="fa fa-fw fa-plus rbg "></i></a>';
        } else if ( treeNode.level == 2 ) { // 叶子节点
            s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;"  href="#" title="修改权限信息">&nbsp;&nbsp;<i class="fa fa-fw fa-edit rbg "></i></a>';
            s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" href="#">&nbsp;&nbsp;<i class="fa fa-fw fa-times rbg "></i></a>';
        }
        s += '</span>';
        aObj.after(s);
    }

    // 鼠标移出节点后不显示按钮组
    function customeRemoveBtn(treeId, treeNode){
        $("#btnGroup" + treeNode.tId).remove();
    }

</script>
</body>
</html>
