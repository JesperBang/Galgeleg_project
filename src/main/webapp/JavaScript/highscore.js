/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

 $(document).ready(function() {
	$( document ).ajaxSend(function( event, jqxhr, settings ) {
	    jqxhr.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("user"))
	});
 //var path = 'http://ubuntu4.saluton.dk:20002/Galgeleg/rest';
 var path = 'http://localhost:8084/mavenproject1/rest';
 //var path = 'http://ubuntu4.saluton.dk:20002/s144211_testbuild/rest';
 
 populateTable();
 
// Load table info into table
function populateTable() {
 document.getElementById("hsLoad").style.visibility = "visible";
     document.getElementById("hsError").style.visibility = "hidden";
    $.ajax({
    url: path+"/score",
    method: "GET",
    success: function(data){
        allUsers = data;
	console.log(data);
	//Parse JSON from ajax request to html table
	//ini vars for table row
	var tr;
				
	//clearing old table rows and table heads
	$("#scoretable tr").remove();
	$("#scoretable th").remove();
		
	//Append table row with descriptions
	$('<tr>').append(
            $('<th>').text("Placement"),
            $('<th>').text("User ID"),
            $('<th>').text("Number of tries"),
            $('<th>').text("Time used"),
            $('<th>').text("Score")
	).appendTo("#scoretable");
				
	//Loop through users and append them to the table in html
	$.each(allUsers, function(i, item) {
            $('<tr>').append(
                $('<td>').text(i+1),
                $('<td>').text(item.student_Id),
		$('<td>').text(item.number_of_tries),
		$('<td>').text(item.time_used),
		$('<td>').text(item.score.toFixed(3))			
            ).appendTo('#scoretable');
	});
				
	},
			
	//error function
	error: function(error){
            localStorage.clear();
            document.getElementById("hsError").style.visibility = "visible";
            alert("Error, timed out");
	}
    });
  document.getElementById("hsLoad").style.visibility = "hidden";
  
    return false;		
}

document.getElementById("testprint").addEventListener("click", function prinths() {

    $.ajax({
        url: path+"/score",
        method: "GET",
        success: function(resp){
            
            Print(resp)
        },		
        //error function
        error: function(error){
            alert("Error, timed out");
        }
    });
    return false;		
}
    );
function Print(resp){
		var currentdate = new Date(); 
		var datetime =  currentdate.getDate() + "-"
		                + (currentdate.getMonth()+1)  + "-" 
		                + currentdate.getYear() ;
		
		var HSP = "Udskrevet "+datetime;
		HSP += "<br><br>";
		HSP += "<table style=\"width:100%\"> <tr><td>Placement</td><td>User ID</td><td>Number of tries</td><td>Time used</td><td>Score</td></tr>"
		$.each(resp, function(i, item){
			HSP += "<tr><td>"+(i+1)+"</td><td>"+item.student_Id+"</td><td>"+item.number_of_tries+"</td><td>"+item.time_used+"</td><td>"+item.score.toFixed(3)+"</td></tr>";
                       
		});
		HSP += "</table>";
	    var mywindow = window.open('', 'PRINT', 'height=800,width=1000');

	    mywindow.document.write('<html><head><title>' + "Highscores"  + '</title>');
	    mywindow.document.write('</head><body>');
	    mywindow.document.write('<center><h1>' + "Galgeleg Highscore List"  + '</h1></center>');
	    mywindow.document.write(HSP);
	    mywindow.document.write('</body></html>');

	    mywindow.document.close(); // necessary for IE >= 10
	    mywindow.focus(); // necessary for IE >= 10*/

	    mywindow.print();
	    mywindow.close();

	    return true;
	}
});