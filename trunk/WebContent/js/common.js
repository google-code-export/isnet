var Common = new Object();
var isIE = (navigator.userAgent.indexOf("MSIE") >= 0);
var childWindows = [];
var styleClasses = {"left":"left_class","right":"right_class","ico":"ico_class","txt":"txt_class","dicon":"ico_class"};

var cssRules = new Array();
if (isIE) {
  cssRules = document.styleSheets[0].rules;
} else {
  cssRules = document.styleSheets[0].cssRules;
}
var commonSearchText = "";
var orginalText = ""
var isCheck = true;
var searchBoxObj = "";

Common.setRadioValue = function ( id, value ) {
	var obj = document.getElementById("idMainForm:"+id);
	if ( obj && value ) {
		obj.value = value; 
	}
}
Common.resetSearchValue = function(noRecordMsg) {
  if(!searchBoxObj) return;
  var searchBoxVal = searchBoxObj.value;
  var searchIdObj = document.getElementById(searchBoxObj.id+"RecId");
  if(searchBoxObj && searchIdObj) {
    var searchBoxVal = searchBoxObj.value;
    if(searchBoxVal) {
      var searchVals = searchBoxVal.split("-");
      if(searchVals) {
        var searchVLen = searchVals.length;
        searchIdObj.value = searchVals[searchVLen-1];
        var searchtxt = "";
        for(var i = 0; i<=searchVLen-2; i++) {
          searchtxt = searchtxt + searchVals[i];
        }
        searchBoxObj.value = searchtxt;
        orginalText = searchtxt;
        isCheck = true;
      }
    }
  }
}

Common.checkSearchValue = function(searchEle) {
  if(searchEle) {
    var searchText = searchEle.value;
    if(searchText && searchText != orginalText) {
      tempField = searchEle;
      isCheck = false;
      setTimeout("tempField.focus();",0);
      return false;
    } else if(searchText == "") {
      var searchIdObj = document.getElementById(searchEle.id+"RecId");
      if(searchIdObj) {
        searchIdObj.value = "";
      }
    }
  }
  return true;
}
Common.getSearchValue = function(searchEle) {	
	searchBoxObj = searchEle;
	if(searchEle && isCheck) {
		orginalText = searchEle.value;
	}
}

Common.clearSearchBox = function(idSearchBox,moduleCode) {
  if(idSearchBox) {
    var srcEl = document.getElementById("idMainForm:"+idSearchBox);
    if(srcEl) {
      var val = srcEl.value.split("-");
      srcEl.value = "";

      if(val && val[1]) {
        var vals = val[1].split(":");
        if(vals && vals[0]) {
          document.title = moduleCode + " : " + vals[0];
        } else {
          document.title = moduleCode;
        }
      }
    }
  }
}

Common.openModalPanel = function(panelId,errMag) {
	if ( errMag ) return;
	if( panelId ) {
	//Richfaces.showModalPanel(panelId,{width:100,height:100,top:50});
    Richfaces.showModalPanel(panelId,{width:605});
  }
 }

Common.closeModalPanel = function(errMag,panelId) {
  if(!Common.ajaxReqContainsErrs(errMag,panelId)) {
    Richfaces.hideModalPanel(panelId);
  }
}

Common.loadTinyMceValue = function(panelId,richTextIds) {
	tinyMCE.triggerSave(true);
}

Common.applyTinyMCE =  function(elementIds) {
	  tinyMCE.init({
	    // General options
	    mode : "exact",
	    theme : "advanced",
	    strict_loading_mode: true,
	    elements : elementIds,
	    // Theme options
	    plugins : "table,advhr,advimage,advlink,insertdatetime,media,searchreplace",
	    theme_advanced_buttons1 : "bold,italic,underline,strikethrough,separator,justifyleft,justifycenter,justifyright, justifyfull,separator,removeformat,formatselect,fontselect,fontsizeselect",
	    theme_advanced_buttons2 : "search,replace,separator,bullist,numlist,separator,outdent,indent,separator,undo,redo,separator,link,unlink,anchor,help,separator,insertdate,inserttime,separator,forecolor,backcolor,charmap",

	    // orig
	    /*plugins : "safari,pagebreak,style,layer,table,save,advhr,advimage,advlink,emotions,iespell,insertdatetime,preview,media,searchreplace,print,contextmenu,paste,directionality,fullscreen,noneditable,visualchars,nonbreaking,xhtmlxtras,template,inlinepopups",
	    theme_advanced_buttons1 : "save,newdocument,|,bold,italic,underline,strikethrough,|,justifyleft,justifycenter,justifyright,justifyfull,|,styleselect,formatselect,fontselect,fontsizeselect",
	    theme_advanced_buttons2 : "cut,copy,paste,pastetext,pasteword,|,search,replace,|,bullist,numlist,|,outdent,indent,blockquote,|,undo,redo,|,link,unlink,anchor,image,cleanup,help,code,|,insertdate,inserttime,preview,|,forecolor,backcolor",
	    theme_advanced_buttons3 : "tablecontrols,|,hr,removeformat,visualaid,|,sub,sup,|,charmap,emotions,iespell,media,advhr,|,print,|,ltr,rtl,|,fullscreen",
	    theme_advanced_buttons4 : "insertlayer,moveforward,movebackward,absolute,|,styleprops,|,cite,abbr,acronym,del,ins,attribs,|,visualchars,nonbreaking,template,pagebreak",
	    */
	    theme_advanced_toolbar_location : "top",
	    theme_advanced_toolbar_align : "center",
	    theme_advanced_statusbar_location : "bottom",
	    theme_advanced_resizing : true,
	    width: "100%",
	    forced_root_block : '',
	    force_br_newlines : false,
	    force_p_newlines : false,
	    // Example word content CSS (should be your site CSS) this one removes paragraph margins
	    content_css : "../../resources/common/css/tinyword.css",
	    //content_css : "css/tinyword.css",
	    // Drop lists for link/image/media/template dialogs
	    template_external_list_url : "lists/template_list.js",
	    external_link_list_url : "lists/link_list.js",
	    external_image_list_url : "lists/image_list.js",
	    media_external_list_url : "lists/media_list.js"
	  });
	}

Common.ajaxReqContainsErrs = function(errMag,panelId) {
  var maxSeverityId = panelId + "HidErrForm:" + panelId + "MaxSeverity";
  var maxSeverityObj = document.getElementById(maxSeverityId);
  if(maxSeverityObj) {
    var maxSeverity =  maxSeverityObj.value;
    if(maxSeverity || errMag) {
      return true;
    }
  }
  return false;
}