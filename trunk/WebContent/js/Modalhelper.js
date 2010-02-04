var ModalHelper =  {
		
	validateModal:function(formId,modalId){
		var val = $(formId+":errorCount").value;
	    if (val == 'false' ){
			Richfaces.hideModalPanel(modalId);
	    }
	},
	show:function(){
		var w = 500;
		var h = 95;
		var left = (screen.width/2)-(w/2);
		var top = (screen.height/2)-(h/2);		
		//alert(left + "  "+ top);
		 document.getElementById('wait').style.left= '40%';
	     document.getElementById('wait').style.top= '40%';
		 document.getElementById('wait').style.visibility = "visible";
	},
	hide:function(){
		 document.getElementById('wait').style.visibility = "hidden";
	}		
  
  };

function wordCounter(count){
	var y = $("memberStoryForm:idBody").value;

	var r = 0;
	var a = y.replace(/\s/g,' ');
	a = a.split(' ');
	var len = a.length;
	var z = 0;
	var cont = '';
	for (z=0; z<len; z++) {
		if (a[z].length > 0) r++;		
		if(cont == ''){
			cont = a[z];
		}else{
			cont = cont + ' '+ a[z];
		}
		if( r >= count){
			$("memberStoryForm:idBody").value = cont;
			$("memberStoryForm:idCharCounter").value = count-r;
			return false;
		}

	}
	
	$("memberStoryForm:idCharCounter").value = count-r;

}	