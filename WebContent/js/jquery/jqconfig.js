/*#FF6A06
jQuery.extend(jQuery.blockUI.defaults.elementMessageCSS, { width:'150px', padding:'0px', textAlign:'left', fontWeight: 'bold', color:'white', background:'red'});
// styles for the displayBox
jQuery.extend(jQuery.blockUI.defaults.displayBoxCSS, { width: '400px', height: '400px', top:'10%', left:'10%' });
---- remove up code -------------------
Configuration for jquery drag-ndrop and blockUI
*/
jQuery.noConflict();
jQuery.extend(jQuery.blockUI.defaults.overlayCSS, { opacity: '0', cursor:'normal'});
jQuery.blockUI.defaults.elementMessage = " Loading...";
jQuery.blockUI.defaults.fadeTime = 0;
jQuery.blockUI.defaults.fadeOut = 0;
jQuery.extend(jQuery.blockUI.defaults.elementMessageCSS, { height:'20px',width:'300px', padding:'1px', textAlign:'left',fontWeight: 'normal', color:'white',
                                                           fontFamily: "Arial,Helvetica,sans-serif",background:'red'});
// styles for the displayBox
jQuery.extend(jQuery.blockUI.defaults.displayBoxCSS, { width: '400px', height: '400px', top:'10%', left:'10%' });

function forceBlockUI(msg, divClass){

	// show the msg if it sent as param
	if (typeof msg != 'undefined' ){
		if ( msg.length > 0){
			jQuery.blockUI.defaults.elementMessage = msg;
		}
	}
	var blockDivClass = "div.mainBlock";
	if (typeof divClass != 'undefined' ){
		if ( divClass.length > 0){
			blockDivClass = divClass;
		}
	}
	jQuery(blockDivClass).block();
	return true;
}
function forceUnblockUI(divClass){
	var blockDivClass = "div.mainBlock";
	if (typeof divClass != 'undefined' ){
		if ( divClass.length > 0){
			blockDivClass = divClass;
		}
	}

	jQuery(blockDivClass).unblock();
	return true;
}

function forceSBlockUI(msg, divClass) {
  // show the msg if it sent as param
  jQuery.extend(jQuery.blockUI.defaults.elementMessageCSS,
                  { height:'18px',width:'100px', padding:'1px', textAlign:'left',fontWeight: 'normal', color:'white',
                    fontFamily: "Arial,Helvetica,sans-serif",background:'red'});
  if (typeof msg != 'undefined' ){
    if ( msg.length > 0){
      jQuery.blockUI.defaults.elementMessage = msg;
    }
  }
  var blockDivClass = "div.mainSecBlock";
  if (typeof divClass != 'undefined' ){
    if ( divClass.length > 0){
      blockDivClass = divClass;
    }
  }
  jQuery(blockDivClass).block();
  return true;
}

function forceSUnblockUI(divClass){
  var blockDivClass = "div.mainSecBlock";
  if (typeof divClass != 'undefined' ){
    if ( divClass.length > 0){
      blockDivClass = divClass;
    }
  }

  jQuery(blockDivClass).unblock();
  return true;
}
