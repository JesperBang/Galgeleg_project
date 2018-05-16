 $(document).ready(function() {
	$( document ).ajaxSend(function( event, jqxhr, settings ) {
	    jqxhr.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("user"))
	});
 var path = 'http://ubuntu4.saluton.dk:20002/Galgeleg/rest';
 //var path = 'http://localhost:8080/mavenproject1/rest';
  
    // On login load useradmin page
    $("#login_form").submit(function() {
	event.preventDefault();
	
        var person={
            "username":document.getElementById("usernameField").value,
            "password":document.getElementById("pswField").value
        };
        
	$.ajax({	
            url: path+"/login",
            contentType: "application/json",
            method: 'POST',
            data: JSON.stringify(person),
            dataType: "text",
            success: function(resp) {
                console.log(resp);
                
		if (resp == null) {
                    alert("Wrong Credentials!");
		} else {
                    console.log(resp);	
                    localStorage.setItem("user", resp); //session Storage						
                    location.href = 'index.html';
                    //var name = $.parseJSON(window.atob(resp.split(".")[1]));
                    //console.log(name);
                    //console.log(name.UserDTO.firstname);		
                    //document.getElementById("logoutmenu").innerHTML = "Logout - "+name.UserDTO.firstname;
		}
            },
            error: function(resp) {
                //Error handling...
		console.log(resp);
            }	
	});
        return false;
    });

function forgotPass(){
    var student={
        "student":document.getElementById("emailpsw").value
    };

  
    $.ajax({
        url: path+'/email',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(student),
        success: function(response) {
            var send = response.Valid;
            // Login true
            if (send === true){
            alert('Email sent to student email');
            location.href = 'loginPage.html'
            }
            // Login false
            else {
                     alert('Wrong student ID');
                     return false;
            }
        },
        error: function(){
            
            alert("Error");
        }
    });

    return false;
}

function scores() {
 
    $.ajax({
        url: path+'/service/score',
        type: 'GET',
        dataType: 'json',
        data: JSON.stringify(scores),
        Success: function(scores){
            console.log(scores);
            
         },
        Error: function(error){
            alert('Something went wrong!');
            
         }

     });    
}
 });