<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
    <title>댓글 </title>
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>

</head>
<body>
	<h2>Comment Test </h2>
	comment: <input type="text" name="comment"><br>
	<button id="sendBtn" type="button">댓글등록</button>
	<button id="modBtn" type="button">수정 </button>
    <div id="commentList"></div>
    
    <div id="replayForm" style="display:none">
    	<input type="text" name="replayComment">
    	<button id="wrtRepBtn" type="button">등록</button>
    </div>
    
   
    
    <script>
        
		let bno = 1296;

		// <댓글리스트 가져오기 > 
		let showList = function(bno){
			
			$.ajax({
                type:'GET',       // 요청 메서드
                url: '/ch4/comments?bno='+bno,  // 요청 URI
               
                success : function(result){
                    
                    
                    $("#commentList").html(toHtml(result));
                },
                error   : function(){ alert("error") } // 에러가 발생했을 때, 호출될 함수
            }); // $.ajax()
		};

		// <<함수작동>> 
		$(document).ready(function(){
			
			showList(bno); // 강제로 댓글리스트 보이게 하기 하드코딩 후 삭제 
			
			
			
			 //댓글수정 	
				 $("#commentList").on("click",".modBtn", function(){
		        	  
		        	  // 댓글번호, 게시글 번호, 댓글 내용 를 가져온다 
		        	  let cno = $(this).parent().attr("data-cno");
		        	  let comment = $("span.comment", $(this).parent()).text();
		        	  
		        	  //1. comment 내용을 input에 넣어주기 (수정하기 위해 )
		        	  $("input[name=comment]").val(comment);
		        	  //2. cno 전달하기 
		        	  $("#modBtn").attr("data-cno",cno);
		        	 

				 });  

			//수정한 댓글 등록 
			 $("#modBtn").click(function(){
				 
				 let cno = $(this).attr("data-cno");
				 let comment = $("input[name=comment]").val();
				 
				
				 
				 //수정 댓글 미입력시 댓글 등록되지 않도록 막는다 
				 if(comment.trim()==''){
					 alert("댓글이 입력되지 않았습니다");
					 $("input[name=comment]").focus()
					 return;
				 }
				 
				 
				 
	                $.ajax({
	                    type:'PATCH',       // 요청 메서드
	                    url: '/ch4/comments/'+cno,  // 요청 URI
	                    headers : { "content-type": "application/json"}, // 요청 헤더
	                    data : JSON.stringify({ cno:cno, comment:comment}),  // 서버로 전송할 데이터. stringify()로 직렬화 필요.
	                    success : function(result){
	                        
	                        alert(result);       // result는 서버가 전송한 데이터
	                        showList(bno);
	                    },
	                    error   : function(){ alert("error") } // 에러가 발생했을 때, 호출될 함수
	                }); // $.ajax()  
         });
			
			
			// 댓글 등록 
			 $("#sendBtn").click(function(){
				 
				 let comment = $("input[name=comment]").val();
				 
	             
				 //댓글 미입력시 댓글 등록되지 않도록 막는다 
				 if(comment.trim()==''){
					 alert("댓글이 입력되지 않았습니다");
					 $("input[name=comment]").focus()
					 return;
				 }
				 
				 
				 
	                $.ajax({
	                    type:'POST',       // 요청 메서드
	                    url: '/ch4/comments?bno='+bno,  // 요청 URI
	                    headers : { "content-type": "application/json"}, // 요청 헤더
	                    data : JSON.stringify({bno:bno, comment:comment}),  // 서버로 전송할 데이터. stringify()로 직렬화 필요.
	                    success : function(result){
	                        
	                        alert(result);       // result는 서버가 전송한 데이터
	                        showList(bno);
	                    },
	                    error   : function(){ alert("error") } // 에러가 발생했을 때, 호출될 함수
	                }); // $.ajax()  
          });
          
			
	
			
			
			
			
		// 댓글 삭제 	
          $("#commentList").on("click",".delBtn", function(){
        	  
        	  // 댓글번호, 게시글 번호를 가져온다 
        	  let cno = $(this).parent().attr("data-cno");
        	  let bno = $(this).parent().attr("data-bno");
        	  
        	  $.ajax({
                  type:'DELETE',      
                  url:'/ch4/comments/'+cno+'?bno='+bno,// 요청 URI
                 
                  success : function(result){
                      alert(result)
                      showList(bno);
                  },
                  error   : function(){ alert("error") } // 에러가 발생했을 때, 호출될 함수
              }); // $.ajax()   	
       	   });  
		
        //대댓글 등록시 폼 보이게 하기   	
			 $("#commentList").on("click",".replayBtn", function(){
	        	  
	        	  // replayForm을 옮기고 
	        	  $("#replayForm").appendTo($(this).parent());
	        	  //2. 답글을 입력할 폼을 보여주고 
	        	  $("#replayForm").css("display","block");

			 });  
		
		// 대댓글 등록 
			
		$("#wrtRepBtn").click(function(){
				 
			let comment = $("input[name=replayComment]").val();
			let pcno = $("#replayForm").parent().attr("data-pcno");
				 
	             
				 //댓글 미입력시 댓글 등록되지 않도록 막는다 
				 if(comment.trim()==''){
					 alert("댓글이 입력되지 않았습니다");
					 $("input[name=replayComment]").focus()
					 return;
				 }			 
				 
	                $.ajax({
	                    type:'POST',       // 요청 메서드
	                    url: '/ch4/comments?bno='+bno,  // 요청 URI
	                    headers : { "content-type": "application/json"}, // 요청 헤더
	                    data : JSON.stringify({ pcno:pcno , bno:bno, comment:comment}),  // 서버로 전송할 데이터. stringify()로 직렬화 필요.
	                    success : function(result){
	                        
	                        alert(result);       // result는 서버가 전송한 데이터
	                        showList(bno);
	                    },
	                    error   : function(){ alert("error") } // 에러가 발생했을 때, 호출될 함수
	                }); // $.ajax()  
	                
	                $("#replayForm").css("display","none")
	                $("input[name=replayComment]").val('')
	                $("#replayForm").appendTo("body");
	                
          });
		
		});
		

		
		

		let toHtml = function(comments){
			
			let tmp ="<ul>";
			
			
			comments.forEach(function(comment){
				
				tmp += '<li data-cno='+ comment.cno 
				tmp += ' data-pcno='+comment.pcno 
				tmp += ' data-bno='+comment.bno+ '>'
				if(comment.cno!=comment.pcno)
				tmp += 'ㄴ'
				tmp += ' commenter =<span class="commenter">' + comment.commenter + '</span>'
				tmp += ' comment = <span class="comment">' + comment.comment + '</span>'
				tmp += ' up_date='+comment.up_date
				tmp += ' <button class="delBtn">삭제</button>'
				tmp += ' <button class="modBtn">수정</button>'
				tmp += ' <button class="replayBtn">대댓글</button>'
				tmp += '</li>'

			})
			
			return tmp +"</ul>";

		}
		
		
    </script>
</body>
</html>