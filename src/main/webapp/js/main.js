$(document).ready(function() {
	
	var socket;
	var suscription;
	var view = null;
	
	 jQuery('.tabs .tab-links a').on('click', function(e)  {
        var currentAttrValue = jQuery(this).attr('href');
 
        // Show/Hide Tabs
        jQuery('.tabs ' + currentAttrValue).show().siblings().hide();
 
        // Change/remove current tab to active
        jQuery(this).parent('li').addClass('active').siblings().removeClass('active');
 
        e.preventDefault();
        
        $('#selectAppender').find('option').remove();
        
        if (currentAttrValue = '#tab2'){
        	 $.ajax({
    	        url: "api/logs/appenders"
    	    }).then(function(data) {
    	    	$.each(data, function (index) {
    	    	    $('#selectAppender').append($('<option>', { 
    	    	        value: index,
    	    	        text : data[index].fileName
    	    	    }));
    	    	});
    	    	
    	    });
        }
    });
	 
	 function loadList() {
		  $.ajax({
		        url: "api/logs/list"
		    }).then(function(data) {

		    	var allItems = '';
		    	$.each(data, function(index) {
		    	     if ("DEBUG" == data[index].level) {
		    	    	 allItems += '<li data-rowid="' + data[index].name + '"><div class="logName">'+ data[index].name  +'</div><a href="#"><input type="button" value="DEBUG" style="background-color:green"/><input type="button" value="INFO" /><input type="button" value="ERROR" /><input type="button"value="FATAL" /><input type="button" value="WARN" /></a></li>'
		    	     } else if ("INFO" == data[index].level) {
		    	    	 allItems += '<li data-rowid="' + data[index].name + '"><div class="logName">'+ data[index].name  +'</div><a href="#"><input type="button" value="DEBUG" /><input type="button" value="INFO" style="background-color:green"/><input type="button" value="ERROR" /><input type="button" value="FATAL" /><input type="button" value="WARN" /></a></li>'
		    	     } else if ("ERROR" == data[index].level) {
		    	    	 allItems += '<li data-rowid="' + data[index].name + '"><div class="logName">'+ data[index].name  +'</div><a href="#"><input type="button" value="DEBUG" /><input type="button" value="INFO" /><input type="button" value="ERROR" style="background-color:green"/><input type="button" value="FATAL" /><input type="button" value="WARN" /></a></li>'
		    	     } else if ("FATAL" == data[index].level) {
		    	    	 allItems += '<li data-rowid="' + data[index].name + '"><div class="logName">'+ data[index].name  +'</div><a href="#"><input type="button" value="DEBUG" /><input type="button" value="INFO" /><input type="button" value="ERROR" /><input type="button" value="FATAL" style="background-color:green"/><input type="button" value="WARN" /></a></li>'
		    	     } else if ("WARN" == data[index].level) {
		    	    	 allItems += '<li data-rowid="' + data[index].name + '"><div class="logName">'+ data[index].name  +'</div><a href="#"><input type="button" value="DEBUG" /><input type="button" value="INFO" /><input type="button" value="ERROR" /><input type="button" value="FATAL" /><input type="button" value="WARN" style="background-color:green"/></a></li>'
		    	     }else{
		    	    	 allItems += '<li data-rowid="' + data[index].name + '"><div class="logName">'+ data[index].name  +'</div><a href="#"><input type="button" value="DEBUG" /><input type="button" value="INFO" /><input type="button" value="ERROR" /><input type="button" value="FATAL" /><input type="button" value="WARN" /></a></li>'
		    	     }
		    	});
		    	$("#thelist").empty().append(allItems);
		    });
	 }
	 
	 loadList();
    
    $("#thelist").on("click", "li input", function(e){
        var name = $(this).parents("li").data("rowid");
        var level = $(this).val();
        
        var logFormObject = {};
        logFormObject['name'] = name;
        logFormObject['level'] = level;
        
        $.ajax({
			type: 'PUT',
			url: 'api/logs/change',
			data: JSON.stringify(logFormObject),
			dataType: 'json',
			contentType: "application/json; charset=utf-8", 
			success: function(responseData) {
				loadList();
			},
			error: function (responseData) {
				alert('Ha fallat el canvi de logs');
			}
		});
    });
    
    
    $( "#connectar" ).click(function() {
    	
    	var logFormObject = $("#selectAppender option:selected").val();
    	
   	 	$.ajax({
			type: 'PUT',
			url: 'api/logs/startwatch',
			data: logFormObject,
			contentType: "application/json; charset=utf-8", 
			success: function(responseData) {
				var url =  window.location.href;
				var last = url.lastIndexOf("/");
				var defurl = url.substring(0, last + 1);
				
				 socket = new SockJS(defurl + 'api/watch');
		         stompClient = Stomp.over(socket);
		         stompClient.connect({}, function(frame) {
		        	 $( "#logContent" ).empty();
		        	 $("#desconnectar").prop("disabled",false);
		        	 $("#connectar").prop("disabled",true);
		        	 $('#selectAppender').prop("disabled",true);
		        	 view = logFormObject;
		        	 $('#connectionStatusIcon').removeClass();
		        	 $('#connectionStatusIcon').addClass('fa fa-lg fa-check-circle');
		        	 subscription = stompClient.subscribe(responseData, function(content){
		            		 addLogMessage(content.body);
		             });
		         });
			},
			error: function (responseData) {
			}
		});
	});
    
  $("#desconnectar").click(function() {
    	
	 	if (view != null){
	 		
	 		subscription.unsubscribe();
	 		
	 		$.ajax({
				type: 'PUT',
				url: 'api/logs/stopwatch',
				data: view,
				contentType: "application/json; charset=utf-8", 
				success: function(responseData) {
					socket.close();
		        	 $('#connectionStatusIcon').removeClass();
		        	 $('#connectionStatusIcon').addClass('fa fa-lg fa-exclamation-triangle');
		        	 view = null;
		        	 $("#desconnectar").prop("disabled",true);
		        	 $("#connectar").prop("disabled",false);
		        	 $('#selectAppender').prop("disabled",false);
				},
				error: function (responseData) {
				}
			});
	 	}
	});
    
    $( "#downloadLog" ).click(function(e) {
    	
    	var id = $("#selectAppender option:selected").val();
    	
    	var aTag = document.createElement('a');
    	aTag.setAttribute('href',"api/logs/downloadLog/"+id);
    	
    	window.location.href = aTag.href;
         
	});
    
    $( "#clearButton" ).click(function(e) {
    	
    	$( "#logContent" ).empty();
         
	});
    
    $('#connectionStatusIcon').addClass('fa fa-lg fa-exclamation-triangle');
    $("#desconnectar").prop("disabled",true);
    
    
    var tid = setInterval(keepalive, 1800000);
    
    function keepalive() {
    	if (view != null){
	 		$.ajax({
				type: 'PUT',
				url: 'api/logs/keepwatch',
				data: view,
				contentType: "application/json; charset=utf-8", 
				success: function(responseData) {
				},
				error: function (responseData) {
				}
			});
	 	}
    }
    
});
