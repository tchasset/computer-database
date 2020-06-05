$(function() {
	var options = $("#companyId option"); 
    options.detach().sort(function(a, b) { 
        var at = $(a).text(); 
        var bt = $(b).text(); 
        return (at > bt) ? 1 : ((at < bt) ? -1 : 0); 
    }); 
    options.appendTo("#companyId"); 
});


function valider(){
	
	if(checkName() && checkDate()) {  
		return true;
	}
	else {
		return false;
	}
}

function checkName() {
	if(document.form.computerName.value != "") {  
		return true;
	}
	else {
		alert("Computer name can't be empty");
		return false;
	}
}

function checkDate() {
	
	var introduced   = document.form.introduced.value,
		discontinued = document.form.discontinued.value;
	
	if(introduced != "" && discontinued != "") {
		if (introduced < discontinued) {
			return true;
		}
		else {
			alert("au revoir");
			return false;
		}
	}
	return true;
}