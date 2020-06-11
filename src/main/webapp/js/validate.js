function valider(){
	
	if(checkDate()) {  
		return true;
	}
	else {
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
			alert("Introduced date can't be after discontinued date");
			return false;
		}
	}
	return true;
}