
var incrementSameCase=1;
var incrementSubCase=1;

var prev=0;
var pos1=2;
var pos2=3;
function onClickGenerateExcel() {
	var fileName = $('input[type=file]').val().replace(/.*(\/|\\)/, '')
	$(document).ready(function() {
		$.ajax({
			type: "GET",
			url: '/printItNow?fileName=' + fileName,
			contentType: "application/json; charset=utf-8",
			success: function(result) {
				window.location.href = "/printItNow?fileName=" + fileName;
			},
			error: function(result) {
			}
		})
	});
}




$(document).ready(function() {
	$("#editorCode").mouseenter(
		function() {
			$("#editorCode")
				.contextmenu(function() {
					$.contextMenu({
						selector: '#editorCode',
						callback: function(key, options, e) {
							var m = "clicked: " + key;
							console.log(m);
							var editor = ace.edit("editorCode");
							var xexe = editor.getSelectedText();
							var selectionRange = editor.getSelectionRange();
							var startline = selectionRange.start.row + 1;
							$("#start").val(startline);
							var endline = selectionRange.end.row;
							$("#end").val(endline);
							highlightCode();
							if (key == 'query') {
								$('#query').val(xexe);
								var examps = 0;
								$('.ace_gutter-cell ').each(function(index, value) {
									if ($(this).text() == startline) {
										examps = startline;
										$(this).css('background-color', 'blue');
									} else if (examps <= endline && examps != 0) {
										$(this).css('background-color', 'blue');
										examps++;
									}
								});

							} else if (key == 'message') {
								$('#message').val(xexe);
								var examps = 0;
								$('.ace_gutter-cell ').each(function(index, value) {
									if ($(this).text() == startline) {
										examps = startline;
										$(this).css('background', 'red');
									} else if (examps <= endline && examps != 0) {
										$(this).css('background', 'red');
										examps++;
									}
								});

							}
							else if (key == 'description') {
								$("#exampleModal").modal('show');
							}
							//idhr ab this se get karo aur value paste kar do text box mein
							// ace_line
							// div.ace_line
						},
						items: {
							"query": {
								name: "Query"
							},
							"message": {
								name: "Message"
							},
							"description": {
								name: "Short Description"
							}
						}
					});
				});
		});

});
$('#shortButton').click(function() {
	var x = $('#shortDes').val();
	$("#shortdesc").val(x);
	
});
function readFile(input) {
	let file = input.files[0];

	let reader = new FileReader();

	reader.readAsText(file);
	var x = document.getElementById('editorCode');
	reader.onload = function() {
		var editor = ace.edit("editorCode");
		editor.setTheme("ace/theme/monokai");
		editor.getSession().setMode("ace/mode/java");
		var z = 0;
		var customPosition = {
			row: 0,
			column: 0
		};
		editor.session.insert(customPosition, reader.result);
		highlightCode();
		$("#req_id").val("P."+incrementSameCase);
	};

	reader.onerror = function() {

	};

}
function onIncreasingValue(){
var temp=$("#req_id").val();
if(temp=="P"){
	
}else{
var prevs=temp.substring(temp.length-1,temp.length);//"P."+incrementSameCase++
prevs++;
temp=temp.substring(0,temp.length-1)+prevs;
$("#req_id").val(temp);
}
}
function onIncrementingSameCase(){
	
}
function onIncrementingSubCase(){
var s=$("#req_id").val();
prev=s;
$("#req_id").val(s+"."+incrementSubCase);
} 
function ondecrement(){
	var s=$("#req_id").val();
	if(s=="P"){
		
	}else{
	var toReplace=s.substring(0,s.length-2);
	console.log(toReplace);
	
$("#req_id").val(toReplace);
	}
}
function closeModal(){
	$(document).ready(function(){
	$("#shortButton").click(function(){
	$("#close").click();
	}
	);
	});
}
function onClickSaveDB(x) {
	var start_id = document.getElementById('start').value;
	var end_id = document.getElementById('end').value;
	var description = document.getElementById('description').value;
	var shortdesc=document.getElementById('shortDes').value;
	var text = '{ "start_id" :' + start_id + ',' + ' "end_id":'
		+ end_id + ',' + ' "description":' + description + '' + '}';
	var query = document.getElementById('query').value;
	var requirement=document.getElementById('req_id').value;
	console.log(query);
	console.log("Its Query");
	var message = document.getElementById('message').value;
	highlightCode(start_id, end_id);
	/* var data={"start_id :"+start_id+
	", end_id:"+end_id+		
	};
	 */
	var formData = {
		firstname: start_id,
		lastname: end_id
	}
	var search = "HELLO";
	var obj = "";
	var filename = $('input[type=file]').val().replace(/.*(\/|\\)/, '')
	var filesTosa = { "filePath": filename };
	var msg = { "message": message, "start_line": start_id, "end_line": end_id, "query": query,"short_description":shortdesc, "description": description,"requirementid":requirement };
	var newObj = {
		file: filesTosa,
		msgFile: msg
	};
	$(document).ready(function() {
		$.ajax({
			type: "POST",
			url: '/sendJson',
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify(newObj),
			success: function(result) {
				$("#start").val("");
				$("#end").val("");
				$("#description").val("");
				$( "shortDes").val("");
				$("#query").val("");
				$("#message").val("");
			alert("Data Successfully Saved");
			},
			error: function(result) {
			}
		})
	});
}

function highlightCode() {
	var filename = $('input[type=file]').val().replace(/.*(\/|\\)/, '')
	$(document).ready(function() {
		$.ajax({
			type: "GET",
			url: '/getLinesFromDb?FileName=' + filename,
			contentType: "application/json; charset=utf-8",
			success: function(result) {
				for (var i = 0; i < result.length; i++) {
					var startlines =parseInt(result[i].start_line);
					var endlines = parseInt(result[i].end_line);
					var exampss = 0;
					
						$('.ace_gutter-cell ').each(function(index, value) {
							if ($(this).text() == startlines) {
								exampss = startlines;
								$(this).css('background', 'red');
							} else if ($(this).text() <= endlines && exampss!=0) {
								$(this).css('background', 'red');
								exampss++;
							}
							else if(exampss>=endlines){
								return false;
							}
								});
					
				}

			},
			error: function(result) {
				alert("OOPS");
			}
		});
	});
}
