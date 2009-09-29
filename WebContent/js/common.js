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

Common.openModalPanel = function(panelId,errMag) {
	if ( errMag ) return;
	if( panelId ) {
	//Richfaces.showModalPanel(panelId,{width:100,height:100,top:50});
    Richfaces.showModalPanel(panelId,{width:605, top:25});
  }
 }

Common.closeModalPanel = function(errMag,panelId) {
  if(!Common.ajaxReqContainsErrs(errMag,panelId)) {
    Richfaces.hideModalPanel(panelId);
  }
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