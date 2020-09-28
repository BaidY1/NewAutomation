
function onClickGenerateExcel(){
	var fileName= $('input[type=file]').val().replace(/.*(\/|\\)/, '')
			$(document).ready(function(){
				$.ajax({
					type: "GET",
					url: '/printItNow?fileName='+fileName,
					contentType: "application/json; charset=utf-8",
					success :function(result) {
			window.location.href="/printItNow?fileName="+fileName;
					alert("YES");
					},
					error:function(result){
						console.log("CHAL PARAAA NOT");
					}
				})
			});
					}





	$(document).ready(function() {
	$("#editorCode").mouseenter(
		function() {$("#editorCode")
		.contextmenu(function() {
	$.contextMenu({
	selector : '#editorCode',
	callback : function(key,options,e) {
		var m = "clicked: "+ key;
		console.log(m);
		var editor = ace.edit("editorCode");
		var xexe = editor.getSelectedText();
		var selectionRange = editor.getSelectionRange();
		var startline=selectionRange.start.row+1;
		$("#start").val(startline);
		var endline=selectionRange.end.row;
		$("#end").val(endline++);
		console.log(startline);
		console.log(endline);
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
			else if(key=='description'){
				$("#exampleModal").modal('show');
			}
		//idhr ab this se get karo aur value paste kar do text box mein
		// ace_line
		// div.ace_line
	},
		items : {
	"query" : {
		name : "Query"
	},	
	"message" : {
		name : "Message"
		},
		"description" : {
			name : "Short Description"
			}
									}
	});
		});
	});

						});
		$('#shortButton').click(function(){
			var x=$('#shortDes').val();
			$("#shortdesc").val(x);
			alert(x);
		});
		function readFile(input) {
			let file = input.files[0];

			let reader = new FileReader();

			reader.readAsText(file);
			var x = document.getElementById('editorCode');
			reader.onload = function() {
				console.log(reader.result);
				var editor = ace.edit("editorCode");
				editor.setTheme("ace/theme/monokai");
				editor.getSession().setMode("ace/mode/java");
				var z = 0;
				var customPosition = {
					row : 0,
					column : 0
				};
				editor.session.insert(customPosition, reader.result);
				$("#req_id").val("U.1.P.1");
			};

			reader.onerror = function() {
				console.log(reader.error);

			};

		}
		function onClickSaveDB(x) {
			var start_id = document.getElementById('start').value;
			var end_id = document.getElementById('end').value;
			var description = document.getElementById('description').value;
			var text = '{ "start_id" :' + start_id + ',' + ' "end_id":'
					+ end_id + ',' + ' "description":' + description + '' + '}';
var query=document.getElementById('query').textContent
.replace(/[\n\r]+|[\s]{2,}/g, ' ').trim();
console.log(query);
var message=document.getElementById('message').value;
			highlightCode(start_id, end_id);
			/* var data={"start_id :"+start_id+
			", end_id:"+end_id+		
			};
			 */
			var formData = {
				firstname : start_id,
				lastname : end_id
			}
			var search = "HELLO";
			var obj = "";
			var filename = $('input[type=file]').val().replace(/.*(\/|\\)/, '')
			var filesTosa={"filePath":filename};
			var msg={"message":message,"start_line":start_id,"end_line":end_id,"query":query,"description":description};
			var newObj={
					file:filesTosa,
					msgFile:msg
			};
			$(document).ready(function(){
				$.ajax({
					type: "POST",
					url: '/sendJson',
					contentType: "application/json; charset=utf-8",
					data: JSON.stringify(newObj),
					success :function(result) {
						console.log("CHAL PARAAA");
						alert("Data Successfully Saved");
					},
					error:function(result){
						console.log("CHAL PARAAA NOT");
					}
				})
			});
					}
		function highlightCode(z, x) {
			$(document).ready(function() {
				//	$('.ace_gutter-cell ').css('background-color','red');
				//	 $('.ace_gutter-cell ').each(function(index){ $('.ace_gutter-cell ').attr('id',$('.ace_gutter-cell ').text())});	

				var examp = 0;
				$('.ace_gutter-cell ').each(function(index, value) {
					if ($(this).text() == z) {
						examp = z;
						$(this).css('background-color', 'green');
					} else if (examp < x && examp != 0) {
						$(this).css('background-color', 'green');
						examp++;
					}/* 
														else if($(this).text()==x){
															$(this).css('background-color','green');		
														} */
				});
			});
		}
