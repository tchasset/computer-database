var error;

function valider(){
	error="";
	if(checkName() && checkDate()) {  
		return true;
	}
	else {
		$("#main .ici").replaceWith( "<div class='ici alert alert-danger'>"+error+"</div>" );
		return false;
	}
}

function checkName() {
	var name = document.form.computerName.value;
	if(name!="") {
		return true;
	}
	else {
		error += "<b>Name can't be empty.</b><br/>";
		return false;
	}
}

function checkDate() {
	
	var introduced   = document.form.introduced.value,
		discontinued = document.form.discontinued.value;

	if(introduced != "" && (introduced<"1970-01-01" || introduced>"2038-01-18")) {
		error += "<b>Introduced date should be between 01-01-1970 and 18-01-2038.</b><br/>";
	}
	if(discontinued != "" && (discontinued<"1970-01-01" || discontinued>"2038-01-18")) {
		error += "<b>Discontinued date should be between 01-01-1970 and 18-01-2038.</b><br/>";
	}
	if(introduced != "" && discontinued != "") {
		if (introduced > discontinued) {
			error += "<b>Introduced date can't be after discontinued date.</b><br/>";
		}
	}
	if(error!="") {
		return false;
	}
	else {
		return true;
	}
}