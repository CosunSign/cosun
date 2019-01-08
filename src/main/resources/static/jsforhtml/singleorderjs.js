function trimstr(str) { //删除左右两端的空格
    if (str != null) {
        return str.replace(/(^\s*)|(\s*$)/g, "");
    }
    return "";
}

function submitartdealdatafail(alertip, submitflag) {
    alert(alertip);

}

function submitartdealdata() {
    var submitflag = true;
    var alerttip = "";
    var flag = false;
    var showmessage = "";
    var tipmessage = "颜色值等信息补充";
    //
    var needrefersamplestr = document.getElementById("needrefersample");//复选框

    if (needrefersamplestr.checked == true) {
        flag = true;
        showmessage += needrefersamplestr.value + " ";
    }
    var uvprintstr = document.getElementById("uvprint");
    var uvprintplusmessagestr = $("#uvprintplusmessage").val();
    if (uvprintstr.checked == true) {
        flag = true;
        showmessage += uvprintstr.value + " ";
        if (tipmessage == uvprintplusmessagestr || trimstr(uvprintplusmessagestr).length <= 0) {
            alerttip = "请为UV选择颜色."
            submitflag = false;
            submitartdealdatafail(alerttip, submitflag);
            return;
        }
        showmessage += trimstr(uvprintplusmessagestr) + " ";
    }

    var polishingstr = document.getElementById("polishing");//复选框
    var sandblastingstr = document.getElementById("sandblasting");//复选框
    var doruststr = document.getElementById("dorust");//复选框
    var silkprintstr = document.getElementById("silkprint");//复选框
    var silkprintplusmesstr = $("#silkprintplusmes").val();
    if (polishingstr.checked == true) {
        flag = true;
        showmessage += polishingstr.value + " ";
    }
    if (sandblastingstr.checked == true) {
        flag = true;
        showmessage += sandblastingstr.value + " ";
    }
    if (doruststr.checked == true) {
        flag = true;
        showmessage += doruststr.value + " ";
    }

    if (silkprintstr.checked == true) {
        flag = true;
        showmessage += silkprintstr.value + " ";
        if (tipmessage == silkprintplusmesstr || trimstr(silkprintplusmesstr).length <= 0) {
            alerttip = "请为丝印打印选择颜色."
            submitflag = false;
            submitartdealdatafail(alerttip, submitflag);
            return;
        }
        showmessage += trimstr(silkprintplusmesstr) + " ";
    }

    var wiredrawlinesstr = $('#wiredrawlines option:selected').val();
    var wiredrawingstr = document.getElementById("wiredrawing");//复选框
    if (wiredrawingstr.checked == true) {
        flag = true;
        showmessage += wiredrawingstr.value + " ";
        if (wiredrawlinesstr == "拉丝纹路" || wiredrawlinesstr == 0) {
            alerttip = "请为拉丝选择纹路."
            submitflag = false;
            submitartdealdatafail(alerttip, submitflag);
            return;
        }
        showmessage += wiredrawlinesstr + " ";
    }

    var watertranprintplusmesstr = $("#watertranprintplusmes").val();
    var watertranprintstr = document.getElementById("watertranprint");//复选框
    if (watertranprintstr.checked == true) {
        flag = true;
        showmessage += watertranprintstr.value + " ";
        if (tipmessage == watertranprintplusmesstr || trimstr(watertranprintplusmesstr).length <= 0) {
            alerttip = "请为水转印选择颜色."
            submitflag = false;
            submitartdealdatafail(alerttip, submitflag);
            return;
        }
        showmessage += trimstr(watertranprintplusmesstr) + " ";
    }

    var bakingpaintplusmesstr = $("#bakingpaintplusmes").val();
    var bakingpaintstr = document.getElementById("bakingpaint");//复选框
    if (bakingpaintstr.checked == true) {
        flag = true;
        showmessage += bakingpaintstr.value + " ";
        if (tipmessage == bakingpaintplusmesstr || trimstr(bakingpaintplusmesstr).length <= 0) {
            alerttip = "请为烤漆选择颜色."
            submitflag = false;
            submitartdealdatafail(alerttip, submitflag);
            return;
        }
        showmessage += trimstr(bakingpaintplusmesstr) + " ";
    }

    var hottransprintplusmesstr = $("#hottransprintplusmes").val();
    var hottransprintstr = document.getElementById("hottransprint");//复选框
    if (hottransprintstr.checked == true) {
        flag = true;
        showmessage += hottransprintstr.value + " ";
        if (tipmessage == hottransprintplusmesstr || trimstr(hottransprintplusmesstr).length <= 0) {
            alerttip = "请为热转印选择颜色."
            submitflag = false;
            submitartdealdatafail(alerttip, submitflag);
            return;
        }
        showmessage += trimstr(hottransprintplusmesstr) + " ";
    }

    var dustplusmessagestr = $("#dustplusmessage").val();
    var dustingstr = document.getElementById("dusting");//复选框
    if (dustingstr.checked == true) {
        flag = true;
        showmessage += dustingstr.value + " ";
        if (tipmessage == dustplusmessagestr || trimstr(dustplusmessagestr).length <= 0) {
            alerttip = "请为喷粉选择颜色."
            submitflag = false;
            submitartdealdatafail(alerttip, submitflag);
            return;
        }
        showmessage += trimstr(dustplusmessagestr) + " ";
    }

    var patchplusmesstr = $("#patchplusmes").val();
    var patchstr = document.getElementById("patch");//复选框
    if (patchstr.checked == true) {
        flag = true;
        showmessage += patchstr.value + " ";
        if (tipmessage == patchplusmesstr || trimstr(patchplusmesstr).length <= 0) {
            alerttip = "请为贴膜选择颜色."
            submitflag = false;
            submitartdealdatafail(alerttip, submitflag);
            return;
        }
        showmessage += trimstr(patchplusmesstr) + " ";
    }

    var elecplateplusmesstr = $("#elecplateplusmes").val();
    var elecplatestr = document.getElementById("elecplate");//复选框
    if (elecplatestr.checked == true) {
        flag = true;
        showmessage += elecplatestr.value + " ";
        if (tipmessage == elecplateplusmesstr || trimstr(elecplateplusmesstr).length <= 0) {
            alerttip = "请为电镀选择颜色."
            submitflag = false;
            submitartdealdatafail(alerttip, submitflag);
            return;
        }
        showmessage += trimstr(elecplateplusmesstr) + " ";
    }

    var corrosionplusmesstr = $("#corrosionplusmes").val();
    var corrosionstr = document.getElementById("corrosion");//复选框
    if (corrosionstr.checked == true) {
        flag = true;
        showmessage += corrosionstr.value + " ";
        if (tipmessage == corrosionplusmesstr || trimstr(corrosionplusmesstr).length <= 0) {
            alerttip = "请为腐蚀选择颜色."
            submitflag = false;
            submitartdealdatafail(alerttip, submitflag);
            return;
        }
        showmessage += trimstr(corrosionplusmesstr) + " ";
    }

    var waterplatingplusmesstr = $("#waterplatingplusmes").val();
    var waterplatingstr = document.getElementById("waterplating");//复选框
    if (waterplatingstr.checked == true) {
        flag = true;
        showmessage += waterplatingstr.value + " ";
        if (tipmessage == waterplatingplusmesstr || trimstr(waterplatingplusmesstr).length <= 0) {
            alerttip = "请为水镀选择颜色."
            submitflag = false;
            submitartdealdatafail(alerttip, submitflag);
            return;
        }
        showmessage += trimstr(waterplatingplusmesstr) + " ";
    }

    var superglueplusmesstr = $("#superglueplusmes").val();
    var supergluestr = document.getElementById("superglue");//复选框
    if (supergluestr.checked == true) {
        flag = true;
        showmessage += supergluestr.value + " ";
        if (tipmessage == superglueplusmesstr || trimstr(superglueplusmesstr).length <= 0) {
            alerttip = "请为灌超级胶选择颜色."
            submitflag = false;
            submitartdealdatafail(alerttip, submitflag);
            return;
        }
        showmessage += trimstr(superglueplusmesstr) + " ";
    }

    var anodicoxidplusmesstr = $("#anodicoxidplusmes").val();
    var anodicoxidstr = document.getElementById("anodicoxid");//复选框
    if (anodicoxidstr.checked == true) {
        flag = true;
        showmessage += anodicoxidstr.value + " ";
        if (tipmessage == anodicoxidplusmesstr || trimstr(anodicoxidplusmesstr).length <= 0) {
            alerttip = "请为阳极氧化选择颜色."
            submitflag = false;
            submitartdealdatafail(alerttip, submitflag);
            return;
        }
        showmessage += trimstr(anodicoxidplusmesstr) + " ";
    }

    var resinplusmesstr = $("#resinplusmes").val();
    var resinstr = document.getElementById("resin");//复选框
    if (resinstr.checked == true) {
        flag = true;
        showmessage += resinstr.value + " ";
        if (tipmessage == resinplusmesstr || trimstr(resinplusmesstr).length <= 0) {
            alerttip = "请为灌树脂选择颜色."
            submitflag = false;
            submitartdealdatafail(alerttip, submitflag);
            return;
        }
        showmessage += trimstr(resinplusmesstr) + " ";
    }

    var paintfillplusmesstr = $("#paintfillplusmes").val();
    var paintfillstr = document.getElementById("paintfill");//复选框
    if (paintfillstr.checked == true) {
        flag = true;
        showmessage += paintfillstr.value + " ";
        if (tipmessage == paintfillplusmesstr || trimstr(paintfillplusmesstr).length <= 0) {
            alerttip = "请为填漆选择颜色."
            submitflag = false;
            submitartdealdatafail(alerttip, submitflag);
            return;
        }
        showmessage += trimstr(paintfillplusmesstr) + " ";
    }

    var coverprotemethodplumesstr = $("#coverprotemethodplumes").val();
    var coverprotemethodstr = document.getElementById("coverprotemethod");//复选框
    if (coverprotemethodstr.checked == true) {
        flag = true;
        showmessage += coverprotemethodstr.value + " ";
        if (tipmessage == coverprotemethodplumesstr || trimstr(coverprotemethodplumesstr).length <= 0 || coverprotemethodplumesstr == 0) {
            alerttip = "请为表面保护方式选择颜色."
            submitflag = false;
            submitartdealdatafail(alerttip, submitflag);
            return;
        }
        showmessage += trimstr(coverprotemethodplumesstr) + " ";
    }

    var otherartsstr = $("#otherarts").val();
    if ("其它工艺" != otherartsstr && trimstr(otherartsstr).length > 0) {
        flag = true;
        showmessage += trimstr(otherartsstr) + " ";
    }
    if (trimstr(showmessage).length <= 0) {
        showmessage = "无";
    }
    if (submitflag) {
        document.getElementById("coverbehidepagetwo").style.display = "none";
        document.getElementById("smallshowdivforartdeal").style.display = "none";
        var tipsforwherestr = $("#tipsforwhere").val();
        $("#" + tipsforwherestr).val(showmessage);

    } else {
        alert(alerttip);
    }

}

function closeartdealdatadiv() {

}



function closesmallshowdivforartdeal() {
    document.getElementById("coverbehidepagetwo").style.display = "none";
    document.getElementById("smallshowdivforartdeal").style.display = "none";
}



function showstrakediv() {
    var flag = true;
    var checkArry = document.getElementById("strake");
    var materialattr = $("#materialattr6").val();

    if (checkArry.checked != true) {
        flag = false;
    }
    if (materialattr == null) {
        flag = false;
    }
    if (flag) {
        //打开面板前清空面板信息
        clearsmallartsfordealdivdata();
        opensmallartsfordealdiv(500, 500);
        $("#tipsforwhere").val("strake" + "art");
    }
}

function showaluminumplatediv() {
    var flag = true;
    var checkArry = document.getElementById("aluminumplate");
    var thickness = $("#thickness9").val();

    if (checkArry.checked != true) {
        flag = false;
    }
    if (thickness == null) {
        flag = false;
    }
    if (flag) {
        //打开面板前清空面板信息
        clearsmallartsfordealdivdata();
        opensmallartsfordealdiv(500, 500);
        $("#tipsforwhere").val("aluminumplate" + "art");
    }
}

function showelectplatediv() {
    var flag = true;
    var checkArry = document.getElementById("electplate");
    var thickness = $("#thickness8").val();

    if (checkArry.checked != true) {
        flag = false;
    }
    if (thickness == null) {
        flag = false;
    }
    if (flag) {
        //打开面板前清空面板信息
        clearsmallartsfordealdivdata();
        opensmallartsfordealdiv(500, 500);
        $("#tipsforwhere").val("electplate" + "art");
    }
}

function showgalvanizedsheetdiv() {
    var flag = true;
    var checkArry = document.getElementById("galvanizedsheet");
    var thickness = $("#thickness7").val();

    if (checkArry.checked != true) {
        flag = false;
    }
    if (thickness == null) {
        flag = false;
    }
    if (flag) {
        //打开面板前清空面板信息
        clearsmallartsfordealdivdata();
        opensmallartsfordealdiv(500, 500);
        $("#tipsforwhere").val("galvanizedsheet" + "art");
    }
}

function showcopperplatediv() {
    var flag = true;
    var checkArry = document.getElementById("copperplate");
    var materialattribute = $("#materialattr15").val();
    var thickness = $("#thickness6").val();

    if (checkArry.checked != true) {
        flag = false;
    }
    if (materialattribute == null) {
        flag = false;
    }
    if (thickness == null) {
        flag = false;
    }
    if (flag) {
        //打开面板前清空面板信息
        clearsmallartsfordealdivdata();
        opensmallartsfordealdiv(500, 500);
        $("#tipsforwhere").val("copperplate" + "art");
    }
}

function showironplatediv() {
    var flag = true;
    var checkArry = document.getElementById("ironplate");
    var materialattribute = $("#materialattr5").val();
    var thickness = $("#thickness5").val();

    if (checkArry.checked != true) {
        flag = false;
    }
    if (materialattribute == null) {
        flag = false;
    }
    if (thickness == null) {
        flag = false;
    }
    if (flag) {
        //打开面板前清空面板信息
        clearsmallartsfordealdivdata();
        opensmallartsfordealdiv(500, 500);
        $("#tipsforwhere").val("ironplate" + "art");
    }
}

function showaluminumalloyplatediv() {
    var flag = true;
    var checkArry = document.getElementById("aluminumalloyplate");
    var materialattribute = $("#marialattr3").val();
    var thickness = $("#thickness3").val();

    if (checkArry.checked != true) {
        flag = false;
    }
    if (materialattribute == null) {
        flag = false;
    }
    if (thickness == null) {
        flag = false;
    }
    if (flag) {
        //打开面板前清空面板信息
        clearsmallartsfordealdivdata();
        opensmallartsfordealdiv(500, 500);
        $("#tipsforwhere").val("aluminumalloyplate" + "art");
    }
}


function showstainlessdiv() {
    var flag2 = true;
    var checkArry2 = document.getElementById("stainless2");
    var surattribute2 = $("#coverattri2").val();
    var materialattribute2 = $("#materialattri2").val();
    var thickness2 = $("#thickness2").val();

    if (checkArry2.checked != true) {
        flag2 = false;
    }
    if (surattribute2 == null) {

        flag2 = false;
    }
    if (materialattribute2 == null) {
        flag2 = false;
    }
    if (thickness2 == null) {
        flag2 = false;
    }
    if (flag2) {
        //打开面板前清空面板信息
        clearsmallartsfordealdivdata();
        opensmallartsfordealdiv(500, 500);
        $("#tipsforwhere").val("stainless2" + "art");
    }
}

function showlastdiv() {
    var flag = true;
    var checkArry = document.getElementById("stainlesteel");
    var surattribute = $("#surattribute").val();
    var materialattribute = $("#materialattribute").val();
    var thickness = $("#thickness").val();

    if (checkArry.checked != true) {
        flag = false;
    }
    if (surattribute == null) {

        flag = false;
    }
    if (materialattribute == null) {
        flag = false;
    }
    if (thickness == null) {
        flag = false;
    }
    if (flag) {
        //打开面板前清空面板信息
        clearsmallartsfordealdivdata();
        opensmallartsfordealdiv(500, 500);
        $("#tipsforwhere").val("stainlesteel" + "art");
    }

}

function clearsmallartsfordealdivdata() {
    var tipmessage = "颜色值等信息补充";
    if ($('#needrefersample').is(":checked")) {
        $("#needrefersample").prop("checked", false);
    }
    if ($('#uvprint').is(":checked")) {
        $("#uvprint").prop("checked", false);
    }
    if ($('#sandblasting').is(":checked")) {
        $("#sandblasting").prop("checked", false);
    }
    if ($('#dorust').is(":checked")) {
        $("#dorust").prop("checked", false);
    }
    if ($('#silkprint').is(":checked")) {
        $("#silkprint").prop("checked", false);
    }

    if ($('#polishing').is(":checked")) {
        $("#polishing").prop("checked", false);
    }
    if ($('#wiredrawing').is(":checked")) {
        $("#wiredrawing").prop("checked", false);
    }
    $("#wiredrawlines").val(0);
    $("#coverprotemethodplumes").val(0);
    $("#uvprintplusmessage").val(tipmessage);
    $("#silkprintplusmes").val(tipmessage);
    if ($('#watertranprint').is(":checked")) {
        $("#watertranprint").prop("checked", false);
    }
    $("#watertranprintplusmes").val(tipmessage);
    $("#bakingpaintplusmes").val(tipmessage);
    $("#hottransprintplusmes").val(tipmessage);
    $("#dustplusmessage").val(tipmessage);
    if ($('#bakingpaint').is(":checked")) {
        $("#bakingpaint").prop("checked", false);
    }
    if ($('#hottransprint').is(":checked")) {
        $("#hottransprint").prop("checked", false);
    }
    if ($('#dusting').is(":checked")) {
        $("#dusting").prop("checked", false);
    }

    if ($('#patch').is(":checked")) {
        $("#patch").prop("checked", false);
    }
    if ($('#elecplate').is(":checked")) {
        $("#elecplate").prop("checked", false);
    }
    if ($('#corrosion').is(":checked")) {
        $("#corrosion").prop("checked", false);
    }
    if ($('#waterplating').is(":checked")) {
        $("#waterplating").prop("checked", false);
    }
    $("#patchplusmes").val(tipmessage);
    $("#elecplateplusmes").val(tipmessage);
    $("#corrosionplusmes").val(tipmessage);
    $("#waterplatingplusmes").val(tipmessage);
    $("#superglueplusmes").val(tipmessage);
    $("#anodicoxidplusmes").val(tipmessage);
    $("#resinplusmes").val(tipmessage);
    $("#paintfillplusmes").val(tipmessage);

    if ($('#superglue').is(":checked")) {
        $("#superglue").prop("checked", false);
    }
    if ($('#anodicoxid').is(":checked")) {
        $("#anodicoxid").prop("checked", false);
    }
    if ($('#resin').is(":checked")) {
        $("#resin").prop("checked", false);
    }
    if ($('#paintfill').is(":checked")) {
        $("#paintfill").prop("checked", false);
    }
    if ($('#coverprotemethod').is(":checked")) {
        $("#coverprotemethod").prop("checked", false);
    }

    $("#otherarts").val("其它工艺");

}

function opensmallartsfordealdiv(w, h) {
    var poptwo = document.getElementById("smallshowdivforartdeal");
    document.getElementById("coverbehidepagetwo").style.display = "block";
    document.getElementById("smallshowdivforartdeal").style.display = "block";
    poptwo.style.top = "150px";
    poptwo.style.left = "500px";
    poptwo.style.width = w + "px";
    poptwo.style.height = h + "px";
    poptwo.style.overflowY = "visible";
    poptwo.style.overflowX = "visible";
    poptwo.style.visibility = "visible";
}

function shutdownpackingdiv() {
    var popfive = document.getElementById("safttransandpacking");
    document.getElementById("coverbehidepage").style.display = "none";
    document.getElementById("safttransandpacking").style.display = "none";
    popfive.style.visibility = "hidden";
}

function opensafttransandpackingdiv(w, h) {
    var poptwo = document.getElementById("safttransandpacking");
    document.getElementById("coverbehidepage").style.display = "block";
    document.getElementById("safttransandpacking").style.display = "block";
    poptwo.style.top = "60px";
    poptwo.style.left = "250px";
    poptwo.style.width = w + "px";
    poptwo.style.height = h + "px";
    poptwo.style.overflowY = "visible";
    poptwo.style.overflowX = "visible";
    poptwo.style.visibility = "visible";

}

function shudownelectmaterialneedsdiv() {
    var popfive = document.getElementById("showelectmaterialneeds");
    document.getElementById("coverbehidepage").style.display = "none";
    document.getElementById("showelectmaterialneeds").style.display = "none";
    popfive.style.visibility = "hidden";
}

function shutdownbigdiv() {
    var popfive = document.getElementById("showmainmaterialandarts");
    document.getElementById("coverbehidepage").style.display = "none";
    document.getElementById("showmainmaterialandarts").style.display = "none";
    popfive.style.visibility = "hidden";
}

function showelectmaterialneeds(w, h) {
    var poptwo = document.getElementById("showelectmaterialneeds");
    document.getElementById("coverbehidepage").style.display = "block";
    document.getElementById("showelectmaterialneeds").style.display = "block";
    poptwo.style.top = "250px";
    poptwo.style.left = "250px";
    poptwo.style.width = w + "px";
    poptwo.style.height = h + "px";
    poptwo.style.overflowY = "visible";
    poptwo.style.overflowX = "visible";
    poptwo.style.visibility = "visible";
}

function showmainmaterialandarts(w, h) {
    var popUp = document.getElementById("showmainmaterialandarts");
    document.getElementById("coverbehidepage").style.display = "block";
    document.getElementById("showmainmaterialandarts").style.display = "block";
    popUp.style.top = "10px";
    popUp.style.left = "260px";
    popUp.style.width = w + "px";
    popUp.style.height = h + "px";
    popUp.style.overflowY = "visible";
    popUp.style.overflowX = "visible";
//if (baseText == null) baseText = popUp.innerHTML;
//popUp.innerHTML = baseText + "<div id=\"statusbar\"><button onclick=\"hidePopup();\">Close window</button></div>";
//var sbar = document.getElementById("statusbar");
//sbar.style.marginTop = (parseInt(h)-40) + "px";
    popUp.style.visibility = "visible";
}