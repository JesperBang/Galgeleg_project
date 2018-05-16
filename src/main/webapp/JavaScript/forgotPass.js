//var path = 'http://ubuntu4.saluton.dk:20002/Galgeleg/rest';
 var path = 'http://localhost:8080/mavenproject1/rest';
  
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