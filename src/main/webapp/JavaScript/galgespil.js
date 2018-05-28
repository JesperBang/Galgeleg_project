$(document).ready(function() {
    $( document ).ajaxSend(function( event, jqxhr, settings ) {
	    jqxhr.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("user"))
	});

});
//var path = 'http://ubuntu4.saluton.dk:20002/mavenproject1/rest/game';
var path = 'http://localhost:8084/mavenproject1/rest';
//var path = 'http://ubuntu4.saluton.dk:20002/s144211_testbuild/rest/game';
                
var string;
var lives = 6;
var answers = [];
var usedLetters = [];
var letter;
var correctLetters;
var timeSpent;
var word;
var wrongs = 0;
var tStart, tSnd,tDelta;


function start(){ 
 
    document.getElementById("livesLeft").innerHTML = lives;
    getOrdet();

}

function getOrdet() {
    nulstil();
    updateImage();
  
    document.getElementById("infotext").textContent = "Gæt ordet for at vinde!";
    usedLetters.length = 0;
    document.getElementById("usedLetters").innerHTML = usedLetters;
   
    $.ajax({
         url: path+'/game/getordet',
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
           
    
    $.ajax({
        url: path+"/game/gaetbogstav",
        contentType: "application/json",
        method: 'POST',
        data: JSON.stringify(bogstav),
        dataType: "text",
        success: function(resp){
            if(resp == null){
                
            }else{

                getSynligtOrd();
                erSpilletTabt();
                erSpilletVundet();
                getBrugteBogstaver();
                logStatus();
            }
        },
        error: function(error) {
            
        }
        
        
    });
    
    return false;
}

function getBrugteBogstaver() {
    
    $.ajax({
        url: path+"/game/getbrugtebogstaver",
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
        url: path+"/game/nulstil",
        method: 'GET',
        dataType: 'text',
        success: function(response){
            if(response == null){
                alert("null")
            }else{
                document.getElementById("image").src="images/forkert1-web.png";
                document.getElementById("gætord").disabled=false;
                lives = 6;
                updateImage();
        }
        },
        error: function(error){
            console.log(error);
        }
        
    });
    
}

function getSynligtOrd() {
    
    $.ajax({
        url: path+"/game/getsynligtord",
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
        url: path+"/game/erspilletvundet",
        method: 'GET',
        dataType: "",
        success: function(status){
            if (status === true) {
                console.log('Tillykke, du har vundet!');
                document.getElementById("gætord").disabled = true;
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
        url: path+"/game/erspillettabt",
        method: 'GET',
        dataType: "",
        success: function(status){
            if (status === true) {
                document.getElementById("gætord").disabled = true;
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
        url: path+"/game/logstatus",
        method: 'GET',
        success: function(response){
            if(response==null){}
            else{
            console.log(response);
              var WrongLetters = response;
              var tmp;
              tmp = WrongLetters.split(":");
               var tmp1 = tmp[2].split("B");
               var numwrongs = tmp1[0].split(" ");
               wrongs = numwrongs[1];
               wrongs.replace("\n", "");
               wrongs.replace(" ", "");
               wrongs.replace("\t", "");
               wrongs = parseInt(wrongs.trim());
               console.log(wrongs);
               
            lives = 6 - wrongs;
             document.getElementById("livesLeft").innerHTML = lives;
            updateImage();
        }
    },
        error: function(error){
            
        }
    });
    
}

function postscore() {
    
    var score = {
        "jwt": localStorage.getItem("user"),
        "username": $.parseJSON(window.atob(localStorage.getItem("user").split(".")[1])).UserDTO.student_Id,
        "score": "",
        "numtries":wrongs,
        "time":""
    };
           
    
    $.ajax({
        url: path+"/postscore",
        contentType: "application/json",
        method: 'POST',
        data: JSON.stringify(score),
        dataType: "application/json",
        success: function(resp){
            
        },
        error: function(error) {
        }   
    });
    
    return false;
}

function updateImage() {
    
    switch(lives) {

        case 0:
            document.getElementById("image").src="images/forkert6-web.png";
            break;
        case 1:
            document.getElementById("image").src="images/forkert5-web.png";
            break;
        case 2:
            document.getElementById("image").src="images/forkert4-web.png";
            break;
        case 3:
            document.getElementById("image").src="images/forkert3-web.png";
            break;
        case 4:
            document.getElementById("image").src="images/forkert2-web.png";
            break;
        case 5:
            document.getElementById("image").src="images/forkert1-web.png";
            break;
        case 6:
            document.getElementById("image").src="images/forkert0-web.png";
        break;
        
    }
    
        function getScore(){
        //Grabbing time and converting to seconds.
        tEnd = System.currentTimeMillis();
        tDelta = tEnd- tStart;
        elapsedSeconds = tDelta / 1000.0;

        //Random "algorithm" i came up with
        //to calculate score relative to word length,
        //time spend and amount of misses.
        temp1 = elapsedSeconds*(numofguess+1);
        temp2 = temp1/(wordunderscore.length()+1);
        temp3 = (100/temp2)*100;
    }
    
    
}

