 var path = 'http://localhost:8080/mavenproject1/rest';
  
function login(){
  var person={
        "username":document.getElementById("usernameField").value,
        "password":document.getElementById("pswField").value
    };

  
    $.ajax({
        url: path+'/login',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(person),
        success: function(response) {
            var login = response.Valid;
            // Login true
            if (login === true){
            alert('ok');
            location.href = 'index.html';
            }
            // Login false
            else {
                     alert('close');
            }
        },
        error: function(response){
            
            alert(response.Valid);
        }
    });

    return false;
}

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
//    
//function changePass(){
//    //insert path for objects
//    var changePass={
//    "student":studentID,
//    "oldPass":
//    "newPass": }
//;
//    
//    $.ajax({
//        url: path+'/changePass',
//        type = 'POST',
//        contentType: 'application/json',
//        data: JSON.stringify(changePass),
//        Success: function(response){
//        var send = response.Valid;
//        if (send === true){
//            alert('Passwrod change success');
//            location.href = 'loginPage.html'
//            }
//            // Login false
//            else {
//                     alert('Wrong student ID or Password');
//                     return false;
//            }
//    },
//    Error: function(){
//        alert("Error");
//    }
//}    
//            );
//    
//}
//
//function sendGameInfo(){
//    var Info={
//   "player":,
//   "word":,
//   "attempt":,
//   "time":,
//   "date":
//    };
//   $.ajax({
//        url: path+'/sendInfo',
//        type = 'POST',
//        contentType: 'application/json',
//        data: JSON.stringify(Info),
//        Success: function(response){
//        var send = response.Valid;
//        if (send === true){
//            alert('Score saved');
//            location.href = 'loginPage.html'
//            }
//            // connection error
//            else {
//                     alert('Database connection Error, try again!');
//                     return false;
//            }
//    },
//    Error: function(){
//        alert("Server connection Error, try again!");
//    }
//}    
//            );
//    
//    }
//
//function sendAttempt(){
//    var Guess=(
//            "letter":);
//   $.ajax({
//        url: path+'/sendInfo',
//        type = 'POST',
//        contentType: 'application/json',
//        data: JSON.stringify(Guess),
//        Success: function(response){
//        var send = response.Valid;
//        if (send === true){
//            alert(correct);
//            location.href = 'loginPage.html'
//            }
//            // connection error
//            else {
//                     alert('Wrong Guess!');
//                     return false;
//            }
//    },
//    Error: function(){
//        alert("Server connection Error, try again!");
//    }
//}    
//            );    
//    
//}

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