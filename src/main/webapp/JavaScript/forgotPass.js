//var path = 'http://ubuntu4.saluton.dk:20002/Galgeleg/rest';
var path = 'http://ubuntu4.saluton.dk:20002/s144211_testbuild/rest';
//var path = 'http://localhost:8084/mavenproject1/rest';
  
function forgotPass(){
    document.getElementById("forgotBtn").disabled = true;
        document.getElementById("forgotBtn").style.opacity = 0.5;
        document.getElementById("forgotBtn").style.cursor = "progress";
    var student={
        "student":document.getElementById("emailpsw").value
    };

  
    $.ajax({
        url: path+'/email',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(student),
        success: function(response) {

            alert('Email sent to student email');
            location.href = 'loginPage.html'
            document.getElementById("forgotBtn").disabled = false;
            document.getElementById("forgotBtn").style.opacity = 01;
            document.getElementById("forgotBtn").style.cursor = "pointer";

        },
        error: function(response){
             document.getElementById("forgotBtn").disabled = false;
             document.getElementById("forgotBtn").style.opacity = 01;
             document.getElementById("forgotBtn").style.cursor = "pointer";
            alert(response.status + "! Failed to send email.");
        }
    });

    return false;

}