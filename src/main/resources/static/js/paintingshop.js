//应征
function enlist() {
    var demandId = $("#demand_id").val();
    var price = $("#price").val();
    var closingDate = $("#closingdate").val();
    var content = $("#content").val();

    if (price != 0 && closingDate != "") {
        if (content == "") {
            content = "这个人很懒，没有给你留言。"
        }
        price.replace(/\b(0+)/gi, "");
        $.ajax({
            type: "POST",
            url: "/enlist",
            contentType: 'application/json',
            data: JSON.stringify({
                "demandId": demandId,
                "price": price,
                "closingDate": closingDate,
                "content": content,
            }),
            success: function (response) {
                if (response.code == 200) {
                    window.location.reload();
                    confirm("应征成功");
                } else if (response.code == 2003) {
                    var isAccepted = confirm(response.message);
                    if (isAccepted) {
                        window.open("http://localhost:8080/login");
                        window.localStorage.setItem("closable", "true");
                    }
                } else if (response.code == 2008) {
                    confirm(response.message);
                } else {
                    alert(response.message);
                }

                console.log(response);
            },
            dataType: "json"
        })
    } else {
        if (price == null || price == 0) {
            window.location.reload();
            confirm("必须输入价格");
        }
        if (closingDate == null || closingDate == "") {
            window.location.reload();
            confirm("必须输入完成时间");
        }

    }


}

//提交需求
function publish() {


    var title = $("#title").val();
    var price = $("#price").val();
    var closingdate = $("#closingdate").val();
    var description = $("#description").val();
    var tag = $("#tag").val();
    var purpose = $("#purpose").val();
    var specifications = $("#specifications").val();


    $.ajax({
        type: "POST",
        url: "/publish",
        contentType: 'application/json',
        data: JSON.stringify({
            "title": title,
            "price": price,
            "closingDate": closingdate,
            "description": description,
            "tag": tag,
            "purpose": purpose,
            "specifications": specifications
        }),
        success: function (response) {
            if (response.code == 200) {
                window.location.href = "http://localhost:8080/profile/demands";
            } else if (response.code == 2003) {
                var isAccepted = confirm(response.message);
                if (isAccepted) {
                    window.open("http://localhost:8080/login");
                    window.localStorage.setItem("closable", "true");
                }
            } else if (response.code == 2007) {
                confirm(response.message);
            } else {
                alert(response.message);
            }

            console.log(response);
        },
        dataType: "json"
    })

}

//应征弹框
function showCreateModal(title) {
    $("#enlistFileTitle").text(title);
    $('#enlistFileMModal').modal('show');
}

//评价弹框
function showEvaluateModal(title) {
    $("#evaluateFileTitle").text(title);
    $('#evaluateFileMModal').modal('show');
}


//选定应征弹框
function showEnlistModal(title) {
    $("#enlistChoiceFileTitle").text(title);
    $('#enlistChoiceFileMModal').modal('show');
}

//申请成为画师
function showPainterApply(title) {
    $("#painterFileTitle").text(title);
    $('#painterFileMModal').modal('show');
}
//修改个人签名弹窗
function showSignApply(title) {
    $("#signFileTitle").text(title);
    $('#signFileMModal').modal('show');
}
//修改密码弹窗
function showPasswordApply(title) {
    $("#passwordFileTitle").text(title);
    $('#passwordFileMModal').modal('show');
}
//修改支付宝弹窗
function showAlipayApply(title) {
    $("#alipayFileTitle").text(title);
    $('#alipayFileMModal').modal('show');
}

//发送画师申请
function apply() {
    $.ajax({
        type: "POST",
        url: "/apply",
        contentType: 'application/json',
        data: JSON.stringify({}),
        success: function (response) {
            if (response.code == 200) {
                window.location.reload();
                confirm("成功发送申请，请耐心等待~");
            } else if (response.code == 2003) {
                var isAccepted = confirm(response.message);
                if (isAccepted) {
                    window.open("http://localhost:8080/login");
                    window.localStorage.setItem("closable", "true");
                }
            } else if (response.code == 2013) {
                confirm(response.message);
            } else {
                alert(response.message);
            }

            console.log(response);
        },
        dataType: "json"
    })

}

//你不是画师
function noPainter() {
    var isAccepted = confirm("您还不是画师，您可以点击确认前去验证。");
    if (isAccepted) {
        showPainterApply();
    }
}

//提交订单
function createOrder() {

    var customerId = $("#customerId").val();
    var demandId = $("#demandId").val();
    var painterId = $("#painterId").val();
    var price = $("#price").val();
    var enlistId = $("#enlistId").val();
    var demandState = $("#demandState").val();

    if (demandState == 1) {
        window.location.reload();
        confirm("该企划状态不允许选定应征！");
    } else {
        $.ajax({
            type: "POST",
            url: "/order",
            contentType: 'application/json',
            data: JSON.stringify({
                "customerId": customerId,
                "demandId": demandId,
                "painterId": painterId,
                "price": price,
                "enlistId": enlistId
            }),
            success: function (response) {
                if (response.code == 200) {
                    window.location.href = "http://localhost:8080/profile/myOrder";
                } else if (response.code == 2003) {
                    var isAccepted = confirm(response.message);
                    if (isAccepted) {
                        window.open("http://localhost:8080/login");
                        window.localStorage.setItem("closable", "true");
                    }
                } else if (response.code == 2007) {
                    confirm(response.message);
                } else {
                    alert(response.message);
                }

                console.log(response);
            },
            dataType: "json"
        })

    }

}


//关注
function follow() {

    var heroineId = $("#heroineId").val();

    $.ajax({
        type: "POST",
        url: "/follow",
        contentType: 'application/json',
        data: JSON.stringify({
            "heroineId": heroineId,
        }),
        success: function (response) {
            if (response.code == 200) {
                window.location.reload();
            } else if (response.code == 2003) {
                var isAccepted = confirm(response.message);
                if (isAccepted) {
                    window.open("http://localhost:8080/login");
                    window.localStorage.setItem("closable", "true");
                }
            } else if (response.code == 2020) {
                confirm(response.message);
            } else {
                alert(response.message);
            }

            console.log(response);
        },
        dataType: "json"
    })

}


//取消关注
function unFollow() {

    var heroineId = $("#heroineId").val();

    $.ajax({
        type: "POST",
        url: "/unFollow",
        contentType: 'application/json',
        data: JSON.stringify({
            "heroineId": heroineId,
        }),
        success: function (response) {
            if (response.code == 200) {
                window.location.reload();
            } else if (response.code == 2003) {
                var isAccepted = confirm(response.message);
                if (isAccepted) {
                    window.open("http://localhost:8080/login");
                    window.localStorage.setItem("closable", "true");
                }
            } else {
                alert(response.message);
            }

            console.log(response);
        },
        dataType: "json"
    })

}

//评价订单
function orderEvaluate() {


    var receiver = $("#receiver").val();
    var receiverType = $("#receiverType").val();
    var demandId = $("#demandId").val();
    var orderId = $("#orderId").val();
    var enlistId = $("#enlistId").val();
    var content = $("#content").val();

    if (content != "") {

        $.ajax({
            type: "POST",
            url: "/evaluate",
            contentType: 'application/json',
            data: JSON.stringify({
                "receiver": receiver,
                "receiverType": receiverType,
                "demandId": demandId,
                "orderId": orderId,
                "enlistId": enlistId,
                "content": content,
            }),
            success: function (response) {
                if (response.code == 200) {
                    window.location.reload();
                } else if (response.code == 2003) {
                    var isAccepted = confirm(response.message);
                    if (isAccepted) {
                        window.open("http://localhost:8080/login");
                        window.localStorage.setItem("closable", "true");
                    }
                } else {
                    alert(response.message);
                }

                console.log(response);
            },
            dataType: "json"
        })
    } else {
        window.location.reload();
        confirm("请输入评价");
    }


}

//审核通过
function approved() {

    var userId = $("#userId").val();


    $.ajax({
        type: "POST",
        url: "/examine",
        contentType: 'application/json',
        data: JSON.stringify({
            "userId": userId,
            "result": 1,

        }),
        success: function (response) {
            if (response.code == 200) {
                window.location.reload();
            } else {
                alert(response.message);
            }

            console.log(response);
        },
        dataType: "json"
    })


}

//审核不通过
function auditFailed() {
    var userId = $("#userId").val();


    $.ajax({
        type: "POST",
        url: "/examine",
        contentType: 'application/json',
        data: JSON.stringify({
            "userId": userId,
            "result": 2,

        }),
        success: function (response) {
            if (response.code == 200) {
                window.location.reload();
            } else {
                alert(response.message);
            }

            console.log(response);
        },
        dataType: "json"
    })
}

//修改个人简介
function modifySign() {
    var content = $("#content").val();


    $.ajax({
        type: "POST",
        url: "/modify",
        contentType: 'application/json',
        data: JSON.stringify({
            "type": 1,          //修改简介
            "content": content,

        }),
        success: function (response) {
            if (response.code == 200) {
                window.location.reload();
            } else {
                alert(response.message);
            }

            console.log(response);
        },
        dataType: "json"
    })

}
//修改登陆密码
function modifyPassword() {
    var content = $("#content").val();


    $.ajax({
        type: "POST",
        url: "/modify",
        contentType: 'application/json',
        data: JSON.stringify({
            "type": 2,          //修改密码
            "content": content,

        }),
        success: function (response) {
            if (response.code == 200) {
                window.location.reload();
            } else {
                alert(response.message);
            }

            console.log(response);
        },
        dataType: "json"
    })

}
//修改支付宝
function modifyAlipay() {
    var content = $("#contentAlipay").val();


    $.ajax({
        type: "POST",
        url: "/modify",
        contentType: 'application/json',
        data: JSON.stringify({
            "type": 3,          //修改支付宝
            "content": content,

        }),
        success: function (response) {
            if (response.code == 200) {
                window.location.reload();
            } else {
                alert(response.message);
            }

            console.log(response);
        },
        dataType: "json"
    })

}



