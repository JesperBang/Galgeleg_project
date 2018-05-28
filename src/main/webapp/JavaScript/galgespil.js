$(document).ready(function() {
    $( document ).ajaxSend(function( event, jqxhr, settings ) {
	    jqxhr.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("user"))
	});

});
//var path = 'http://ubuntu4.saluton.dk:20002/mavenproject1/rest/game';
//var path = 'http://localhost:8084/mavenproject1/rest/game';
var path = 'http://ubuntu4.saluton.dk:20002/s144211_testbuild/rest/game';
                
var string;
var lives = 6;
var answers = [];
var usedLetters = [];
var letter;
var correctLetters;
var timeSpent;
var word;

function start(){
  
    nulstil(); 
    document.getElementById("livesLeft").innerHTML = lives;
}


function getOrdet() {
    
    document.getElementById("infotext").textContent = "Gæt ordet for at vinde!";
    nulstil();
    usedLetters.length = 0;
    document.getElementById("usedLetters").innerHTML = usedLetters;
   
    $.ajax({
         url: path+'/getordet',
         method: 'GET',
         crossDomain: 'true',
         dataType: 'text',
         success: function(response){
             word = response;
             console.log(word);
                
            string = answers.join(" ");
            document.getElementById("answer").innerHTML = string;
            getSynligtOrd();
            
             
         },
         error: function(response) {
             alert("Error, timed out");
         }
     });
     return false;

}

function gætBogstav() {
    
    var bogstav = {
        "bogstav": document.getElementById("letter").value
        
    };
    
        if(word.includes(document.getElementById("letter").value)) {
                console.log("Guess correct");
            }else {
                console.log("Guess incorrect");
                lives = lives - 1;
            }
            
            console.log(lives);
            document.getElementById("livesLeft").innerHTML = lives;
    
    $.ajax({
        url: path+"/gaetbogstav",
        crossDomain: 'true',
        contentType: "application/json",
        method: 'POST',
        data: JSON.stringify(bogstav),
        dataType: "text",
        success: function(bogstav){
            $.post("http://ubuntu4.saluton.dk:20002/s144211_testbuild/rest/game/gaetbogstav", bogstav);
            console.log(bogstav); 
            
            getSynligtOrd();
            erSpilletTabt();
            erSpilletVundet();
            getBrugteBogstaver();
            logStatus();

            
        },
        error: function(error) {
            
        }
        
        
    });
    return false;
    
    updateImage();
}

function getBrugteBogstaver() {
    
    $.ajax({
        url: path+"/getbrugtebogstaver",
        method: 'GET',
        dataType: 'json',
        success: function(brugtebogstaver){
            
                usedLetters = brugtebogstaver;
                document.getElementById("usedLetters").innerHTML = usedLetters;
                console.log(usedLetters);
            
        },
        error: function(error){
            
        }
    });
    
}

function nulstil() {
    
    $.ajax({
        url: path+"/nulstil",
        method: 'GET',
        success: function(response){
        },
        error: function(error){
            
        }
        
    });
    
}

function getSynligtOrd() {
    
    $.ajax({
        url: path+"/getsynligtord",
        method: 'GET',
        dataType: "text",
        success: function(synligtOrd){
            visibleWord = synligtOrd;
            document.getElementById("answer").innerHTML = visibleWord;
            
            
        },
        error: function(error){
            
        }
        
    });
    return false;
    
}

function erSpilletVundet(){
    
    $.ajax({
        url: path+"/erspilletvundet",
        method: 'GET',
        dataType: "",
        success: function(status){
            if (status === true) {
                console.log('Tillykke, du har vundet!');
                document.getElementById("infotext").textContent = "Tillykke! Du vandt spillet! Hent et nyt ord for at spille igen!";
            }
        },
        error: function(error){
            
        }
    });
    return false;
    
    
}

function erSpilletTabt(){
    
    $.ajax({
        url: path+"/erspillettabt",
        method: 'GET',
        dataType: "",
        success: function(status){
            if (status === true) {
                console.log('Desværre, du har tabt!');
                document.getElementById("infotext").textContent = "Desværre! Du tabte spillet! Hent et nyt ord for at spille igen!";
                
            }
        },
        error: function(error){
            
        }
    });
    return false;
    
    
}

function logStatus() {
  
    $.ajax({
        url: path+"/logstatus",
        method: 'GET',
        success: function(response){
            console.log(response);
            
            
            
        },
        error: function(error){
            
        }
    });
    
}


function updateImage() {
    
    switch(lives) {
        
        case 1:
            lives = document.getElementById("image").src="images/forkert6-web.png";
            break;
        case 2:
            lives = document.getElementById("image").src="images/forkert5-web.png";
            break;
        case 3:
            lives = document.getElementById("image").src="images/forkert4-web.png";
            break;
        case 4:
            lives = document.getElementById("image").src="images/forkert3-web.png";
            break;
        case 5:
            lives = document.getElementById("image").src="images/forkert2-web.png";
            break;
        case 6:
            lives = document.getElementById("image").src="images/forkert1-web.png";
            break;
        
    }
    
    
    
    
}

