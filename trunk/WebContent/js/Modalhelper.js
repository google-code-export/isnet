var ModalHelper =  {
		
	validateModal:function(formId,modalId){
		var val = $(formId+":errorCount").value;
	    if (val == 'false' ){
			Richfaces.hideModalPanel(modalId);
	    }
	}
  
  };