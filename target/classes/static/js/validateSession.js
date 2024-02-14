/**
 * 
 */
//alert("called");
 var intervalId = window.setInterval(function(){
  // call your function here
      	$.ajax({
						type: "POST",
						 enctype: 'multipart/form-data',
						 processData: false,
						 contentType: false,
			             url :'validateSession',
			        }).done(function(data) {
			        
			         var isactive =data["msg"];
			         //alert(isactive);
			         if(!isactive){
						 alert("Session expired redirecting to login page");
						 window.location.replace('login');
					 }
			        });
}, 1000000);