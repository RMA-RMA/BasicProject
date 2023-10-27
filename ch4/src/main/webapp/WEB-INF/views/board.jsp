<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ page session="true"%>
<c:set var="loginId" value="${sessionScope.id}"/>
<c:set var="loginOutLink" value="${loginId==null ? '/login/login' : '/login/logout'}"/>
<c:set var="loginOut" value="${loginId==null ? 'Login' : 'ID='+=loginId}"/>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <title>fastcampus</title>
    <link rel="stylesheet" href="<c:url value='/css/menu.css'/>">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
	<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<style>
    * {
      box-sizing: border-box;
      margin: 0;
      padding: 0;
      font-family: "Noto Sans KR", sans-serif;
    }

    .container {
      width : 50%;
      margin : auto;
    }

    .writing-header {
      position: relative;
      margin: 20px 0 0 0;
      padding-bottom: 10px;
      border-bottom: 1px solid #323232;
    }

    input {
      width: 100%;
      height: 35px;
      margin: 5px 0px 10px 0px;
      border: 1px solid #e9e8e8;
      padding: 8px;
      background: #f8f8f8;
      outline-color: #e6e6e6;
    }

    textarea {
      width: 100%;
      background: #f8f8f8;
      margin: 5px 0px 10px 0px;
      border: 1px solid #e9e8e8;
      resize: none;
      padding: 8px;
      outline-color: #e6e6e6;
    }

    .frm {
      width:100%;
    }
    .btn {
      background-color: rgb(236, 236, 236); /* Blue background */
      border: none; /* Remove borders */
      color: black; /* White text */
      padding: 6px 12px; /* Some padding */
      font-size: 16px; /* Set a font size */
      cursor: pointer; /* Mouse pointer on hover */
      border-radius: 5px;
    }

    .btn:hover {
      text-decoration: underline;
    }
  </style>


</head>
<body>

<div id="menu"> 
    <ul>
        <li id="logo">fastcampus</li>
        <li><a href="<c:url value='/'/>">Home</a></li>
        <li><a href="<c:url value='/board/list'/>">Board</a></li>
        <li><a href="<c:url value='${loginOutLink}'/>">${loginOut}</a></li>
        <li><a href="<c:url value='/register/add'/>">Sign in</a></li>
        <li><a href=""><i class="fa fa-search"></i></a></li>
    </ul>
</div>
<script>
    let msg = "${msg}";
    if(msg=="WRT_ERR") alert("게시물 등록에 실패하였습니다. 다시 시도해 주세요.");
    if(msg=="MOD_ERR") alert("게시물 수정에 실패하였습니다. 다시 시도해 주세요.");
</script>


<div class="container">
    <h2 class="writing-header">게시판 ${mode=="new" ? "글쓰기" : "읽기"}</h2>
    <form id="form" class="frm" action="" method="post">
        <input type="hidden" name="bno" value="${boardDto.bno}">

        <input name="title" type="text" value="<c:out value='${boardDto.title}'/>" 
        placeholder="제목을 입력해 주세요." ${mode=="new" ? "" : "readonly='readonly'"}><br>
        <textarea name="content" rows="20" placeholder=" 내용을 입력해 주세요." ${mode=="new" ? "" : "readonly='readonly'"}>
        <c:out value='${boardDto.content}'/></textarea><br>


        <c:if test="${mode eq 'new'}">
            <button type="button" id="writeBtn" class="btn btn-write"><i class="fa fa-pencil"></i> 등록</button>
        </c:if>
        <c:if test="${mode ne 'new'}">
            <button type="button" id="writeNewBtn" class="btn btn-write"><i class="fa fa-pencil"></i> 글쓰기</button>
        </c:if>
        <c:if test="${boardDto.writer eq loginId}">
            <button type="button" id="modifyBtn" class="btn btn-modify"><i class="fa fa-edit"></i> 수정</button>
            <button type="button" id="removeBtn" class="btn btn-remove"><i class="fa fa-trash"></i> 삭제</button>
        </c:if>
        <button type="button" id="listBtn" class="btn btn-list"><i class="fa fa-bars"></i> 목록</button>
    </form>
</div>

    
 

	<script>
	$(document).ready(function(){
		
		// 글쓰기할때  제목 , 내용에 나오는 미리보기 
	    let formCheck = function() {
	      let form = document.getElementById("form");
	      if(form.title.value=="") {
	        alert("제목을 입력해 주세요.");
	        form.title.focus();
	        return false;
	      }

	      if(form.content.value=="") {
	        alert("내용을 입력해 주세요.");
	        form.content.focus();
	        return false;
	      }
	      return true;
	    }
		
		
	//글쓰기 버튼 
	    $("#writeNewBtn").on("click", function(){
	      location.href="<c:url value='/board/write'/>";
	    });

	    $("#writeBtn").on("click", function(){
	      let form = $("#form");
	      form.attr("action", "<c:url value='/board/write'/>");
	      form.attr("method", "post");

	      if(formCheck())
	        form.submit();
	    });

	    // 게시물 수정버튼을 누르면 읽기 상태로 전환 
	    $("#modifyBtn").on("click", function(){
	      let form = $("#form");
	      let isReadonly = $("input[name=title]").attr('readonly');

	      // 1. 읽기 상태이면, 수정 상태로 변경
	      if(isReadonly=='readonly') {
	        $(".writing-header").html("게시판 수정");
	        $("input[name=title]").attr('readonly', false);
	        $("textarea").attr('readonly', false);
	        $("#modifyBtn").html("<i class='fa fa-pencil'></i> 등록");
	        return;
	      }

	      // 2. 수정 상태이면, 수정된 내용을 서버로 전송
	       form.attr("action", "<c:url value='/board/modify${searchCondition.queryString}'/>");
	      form.attr("method", "post");
	      if(formCheck())
	        form.submit();
	    });

	    
	    // 게시물 삭제 팝업 
	    $("#removeBtn").on("click", function(){
	      if(!confirm(" 게시물을 삭제하시겠습니까?")) return;

	      let form = $("#form");
	      form.attr("action", "<c:url value='/board/remove${searchCondition.queryString}'/>");
	      form.attr("method", "post");
	      form.submit();
	    });

	    // 게시물 목록 연동 
	    $("#listBtn").on("click", function(){
	    	 location.href="<c:url value='/board/list${searchCondition.queryString}'/>";
	    });
	  });
	
	//하드코딩 
	let bno = 1296;
	let id = 'asdf';
	
	// 댓글 등록시 나오는 날짜 
	let addZero = function(value=1){
        return value > 9 ? value : "0"+value;
    }

    let dateToString = function(ms=0) {
        let date = new Date(ms);

        let yyyy = date.getFullYear();
        let mm = addZero(date.getMonth() + 1);
        let dd = addZero(date.getDate());

        let HH = addZero(date.getHours());
        let MM = addZero(date.getMinutes());
        let ss = addZero(date.getSeconds());          

        return yyyy+"."+mm+"."+dd+ " " + HH + ":" + MM + ":" + ss;            
    }
	
	
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
			 $("#commentList").on("click","#btn-write-modify", function(){
	        	  
	        	  // 댓글번호, 게시글 번호, 댓글 내용 를 가져온다 
	        	  let cno = $(this).parent().attr("data-cno");
	        	  let comment = $("span.comment", $(this).parent()).text();
	        	  
	        	  //1. comment 내용을 input에 넣어주기 (수정하기 위해 )
	        	  $("input[name=comment]").val(comment);
	        	  //2. cno 전달하기 
	        	  $("#modBtn").attr("data-cno",cno);
	        	  //모달 창 닫기 
	        	  $(".close").trigger("click");

			 });  

		//수정한 댓글 등록 
		 $("#a.btn-modify").click(function(){
			 
			 //let cno = $(this).attr("data-cno");
			 //let comment = $("input[name=comment]").val();
			 
			let target = e.target;
            let cno = target.getAttribute("data-cno");
            let bno = target.getAttribute("data-bno");
            let pcno = target.getAttribute("data-pcno");
            let li = $("li[data-cno="+cno+"]");
            let commenter = $(".commenter", li).first().text();
            let comment = $(".comment-content", li).first().text();

            $("#modalWin .commenter").text(commenter);
            $("#modalWin textarea").text(comment);
            $("#btn-write-modify").attr("data-cno", cno);
            $("#btn-write-modify").attr("data-pcno", pcno);
            $("#btn-write-modify").attr("data-bno", bno);

            // 팝업창을 열고 내용을 보여준다.
            $("#modalWin").css("display","block");
			 
			 
			 
			 
			 
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
		 $("#a.btn-write").click(function(){
			 
			 //let comment = $("input[name=comment]").val();
			 
			 let target = e.target;
             let cno = target.getAttribute("data-cno")
             let bno = target.getAttribute("data-bno")

             let repForm = $("#reply-writebox");
             repForm.appendTo($("li[data-cno="+cno+"]"));
             repForm.css("display", "block");
             repForm.attr("data-pcno", pcno);
             repForm.attr("data-bno",  bno);
             
             
             
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
      
		
		
		// 댓글 등록 취소시 디스플레이 안보이게 바꾼다 
            $("#btn-cancel-reply").click(function(e){
                $("#reply-writebox").css("display", "none");
            });
		
		
		
		
		
	// 댓글 삭제 	
      $("#commentList").on("click",".a.btn-delete", function(){
    	  
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
	
      $(".close").click(function(){
          $("#modalWin").css("display","none");
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
			 
			 let comment = $("input[name=replyComment]").val(); 
			 let pcno = $("#replayForm").parent().attr("data-pcno"); //답글의 부모 
			 
			 //댓글 미입력시 댓글 등록되지 않도록 막는다 
			 if(comment.trim()==''){
				 alert("댓글이 입력되지 않았습니다");
				 $("input[name=replyComment]").focus()
				 return;
			 }
			 
			 
			 
                $.ajax({
                    type:'POST',       // 요청 메서드
                    url: '/ch4/comments?bno='+bno,  // 요청 URI
                    headers : { "content-type": "application/json"}, // 요청 헤더
                    data : JSON.stringify({pcno:pcno, bno:bno, comment:comment}),  // 서버로 전송할 데이터. stringify()로 직렬화 필요.
                    success : function(result){
                        
                        alert(result);       // result는 서버가 전송한 데이터
                        showList(bno);
                    },
                    error   : function(){ alert("error") } // 에러가 발생했을 때, 호출될 함수
                }); // $.ajax()  

             // replayForm을 다시등록전으로 옮겨야함  
            	 $("#replayForm").css("display","none"); // 안보이도록 숨기기 
            	 $("input[name=replyComment]").val(''); // 값 없애기 
	        	 $("#replayForm").appendTo("body"); // 원래있던 위치 
	        	

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