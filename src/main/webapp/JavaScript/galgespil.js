

//Ord skal hentes gennem REST
var word = "computer";

var string;
var count = 0;
var answers = [];
var lives = 5;
var usedLetters = [];
var triesUsed = 0;
var letter;
var correctLetters;

var timeSpent;

function start(){
  
    for (var i = 0; i < word.length; i++) {
        answers[i] = "_";
    }
    
    correctLetters = 0;
    
    string = answers.join(" ");
    document.getElementById("answer").innerHTML = string;
   
}

function guessLetter(){    
    
    //Get input/guess from the player
    letter = document.getElementById("letter").value;
    
    //Check for valid input
    if (letter.length > 0) {
        triesUsed += 1;
       
        //Loop through the word to see whether the word contains the guessed letter
        for (var i = 0; i < word.length; i++){
            
            //If the word contains the guessed letter -> add the letter to the visible word
            if (word[i] === letter){
                answers[i] = letter;
            }       
        }
        
        //If the word doesn't contain the letter guessed remove one life
        var j = (word.indexOf(letter));
            
        if (j === -1) {
            lives -= 1;
            count += 1;
        }


        //Add the letter 
        usedLetters.push(letter);
     
        document.getElementById("usedLetters").innerHTML = "Brugte bogstaver: " + usedLetters;
        document.getElementById("counter").innerHTML = "Antal forkerte: " + count;
        document.getElementById("livesLeft").innerHTML = "Du har " + lives + " liv tilbage.";
        document.getElementById("answer").innerHTML = answers.join(" ");
        
    }
    if (lives === 0){
        document.getElementById("status").innerHTML = "Desværre! Du tabte spillet!";
        document.getElementById("usedLetters").innerHTML = " ";
        document.getElementById("counter").innerHTML = " ";
        document.getElementById("answer").innerHTML = " ";
        
        //Reset
        //nulstil();
        

    }
}


function getOrdet() {
    
    $.ajax({
         url: path+"/getordet",
         method: 'GET',
         success: function(data){
             word = data;
             console.log(data);
             
             
         },
         error: function(error) {
             alert("Error, timed out");
         }
     });
    

}


function gameOverWon(){
    
    
}

function gameOverLoss(){
    
    
}

