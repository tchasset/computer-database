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

	if(introduced != "" && introduced<"1970-01-01") {
		alert("Introduced date can't be before 01-01-1970");
		return false
	}
	if(discontinued != "" && discontinued<"1970-01-01") {
		alert("Discontinued date can't be before 01-01-1970");
		return false
	}
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