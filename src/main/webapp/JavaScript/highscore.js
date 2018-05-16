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
 var path = 'http://localhost:8080/mavenproject1/rest';
 
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
            $('<th>').text("number_of_tries"),
            $('<th>').text("time_used"),
            $('<th>').text("score")
	).appendTo("#scoretable");
				
	//Loop through users and append them to the table in html
	$.each(allUsers, function(i, item) {
            $('<tr>').append(
                $('<td>').text(i+1),
                $('<td>').text(item.student_Id),
		$('<td>').text(item.number_of_tries),
		$('<td>').text(item.time_used),
		$('<td>').text(item.score)			
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
});