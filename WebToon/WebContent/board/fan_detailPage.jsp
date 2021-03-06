<%@page import="java.util.List"%>
<%@page import="net.wtf.comm.db.CommentsBean"%>
<%@page import="net.board.db.FanBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>오늘 뭐 볼까?</title>
<link rel="stylesheet" href="./css/board.css">
<link rel="stylesheet" href="./main/css/header.css">
<link rel="stylesheet" href="./main/css/border-header.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="./main/css/footer-main.css">
<script src="./js/jquery-3.3.1.js"></script>
<script type="text/javascript"
	src="//code.jquery.com/jquery-1.11.0.min.js"></script>


<%
	String mem_num = (String) session.getAttribute("mem_num");

	int fa_num = (Integer) request.getAttribute("fa_num");
	String pageNum = (String) request.getAttribute("pageNum");

	boolean check = (boolean) request.getAttribute("check");
	FanBean fb = (FanBean) request.getAttribute("fb");

	int nextNum = (Integer) request.getAttribute("nextNum");
	int preNum = (Integer) request.getAttribute("preNum");
	FanBean fb2 = (FanBean) request.getAttribute("fb2");
	FanBean fb3 = (FanBean) request.getAttribute("fb3");
%>



<script type="text/javascript">
	function modifyCommentToggle(articleNo) {
		var p_id = "comment" + articleNo;
		var p = document.getElementById(p_id);

		var form_id = "modifyCommentForm" + articleNo;
		var form = document.getElementById(form_id);

		var p_display;
		var form_display;

		if (p.style.display) {
			p_display = '';
			form_display = 'none';
		} else {
			p_display = 'none';
			form_display = '';
		}
		p.style.display = p_display;
		form.style.display = form_display;
	}
	
	function del(fa_num){
		if(confirm("해당 글을 삭제하시겠습니까?")==true){
			location.href="./fanDelete.fo?fa_num=<%=fa_num%>&pageNum=<%=pageNum%>";
		}
	};
 		
	
	<%-- $(document).ready(function(){
		$.ajax('fanModify.fo',{
			data:{
				fan_category1 : <%=fb.getFa_category1()%>
			},
			success : function(data){
				$('')
			}
		});
	}); --%>
	
</script>
</head>

<body>

	<!-- wrap 영역 시작 -->
	<div id="wrap">
		<!-- header 영역 시작 -->
		<jsp:include page="../main/header.jsp"></jsp:include>
		<!-- header 영역 끝-->

		<div class="detail">
			<div class="fi">
				<!-- 이전 글 없을 경우 제어 -->
				<%
					if (preNum != 0) {	
				%>
					<input type="button" class="bt" value="이전 글"
					onclick="location.href='./fanboardContent.fo?fa_num=<%=preNum%>&pageNum=<%=pageNum%>'" />
				<%
					} else {
				%><input type="button" class="bt-if"
					onclick="location.href='./fanboardList.fo'" value="목록" />
				<%
					}
				%>
				<!-- 다음 글 없을 경우 제어 -->
				<%
					if (nextNum != 0) {
				%>
				<input type="button" class="bt" value="다음 글"
					onclick="location.href='./fanboardContent.fo?fa_num=<%=nextNum%>&pageNum=<%=pageNum%>'" />
				<br>
				<%
					} else {
				%><input type="button" class="bt-if"
					onclick="location.href='./fanboardList.fo'" value="목록" />
				<%
					}
				%>
				<br>
			</div>
			<%
				if (nextNum != 0 && preNum != 0) {
			%>
			<input type="button" class="bt-pri"
				onclick="location.href='./fanboardList.fo'" value="목록" />
			<%
				}
			%>
			<div class="clear"></div>

			<article>
				<div class="detail_content">
					<table class="main">
						<tr>
							<th
								style="text-align: left; vertical-align: center center; font-size: 30px; display: inline;">&nbsp;&nbsp;</th>
							<th style="text-align: left; font-size: 30px;">[<%=fb.getFa_category1()%>]
								[<%=fb.getFa_category2()%>] <%=fb.getFa_subject()%></th>
						</tr>
						<hr>
					</table>
					<div id="content">
						<hr>
						<div id="date-writer-hit">
							<span><%=fb.getFa_date()%> | </span> <span>닉네임: <%=fb.getFa_mem_nik()%>
								|
							</span> <span>조회수: <%=fb.getFa_readcount()%></span>
						</div>
						<div id="article-content">
						<%if (fb.getFa_img()!=null) {%>
							 <img src="./upload/<%=fb.getFa_img()%>" class="content_img" style="max-width: 100%;">
						<%} %>
							 <br><br>
							<%=fb.getFa_content()%><br> <br>
						</div>
					</div>

					<!-- LikeBtn 시작 -->
					<i class="fa fa-heart-o like" id="likeIcon"
						style="margin: 10px 0 0 15px; font-size: 32px; cursor: pointer; color: red;">
					</i> <span class="likeBtnSp">좋아요 <%=fb.getFa_sumlike()%></span>
					<!-- LikeBtn 끝 -->
				</div>
				<script>
					 
					 $('.like').each(function(){
							var check = <%=check%>
							if(check==true){
								$(this).removeClass('fa-heart-o');
								$(this).addClass('fa-heart');
							}
						});
					 
					 $("i.like").click(function(){
							if(<%=mem_num%>==null){
								alert('로그인이 필요합니다');
							}else{
								$.ajax('fanboardLikeAction.fo',{
									context:this,
									data: {
										fa_num : <%=fa_num%>
									},success: function(data){
										var op = data.split(",");
										if(op[0]=='true'){
											$(this).removeClass('fa-heart');
											$(this).addClass('fa-heart-o');
											$(this).next().html('좋아요 '+op[1]);
										}else{
											$(this).removeClass('fa-heart-o');
											$(this).addClass('fa-heart');
											$(this).next().html('좋아요 '+op[1]);
										}
									}
								});
							}
						});
					</script>

				<!-- 파일 다운 및 삭제  -->
				<div id="file-list" style="text-align: right;">
					<div class="attach-file">
						<a href="#" title="filename" class="download">TEST.png</a> <a
							href="#" title="filekey">삭제</a>
					</div>
				</div>

				<!-- 파일 다운 및 삭제 끝 -->

				<!-- 수정삭제 다음글 이전글 -->
				<div class="view-menu" style="margin-bottom: 47px;">
					<div class="fi">
						<br>
						<%
							if (mem_num != null) {
								if (mem_num.equals(fb.getFa_mem_num()) || mem_num.equals("18121220303328")) {
						%>
						<input type="button" class="bt" value="수정"
							onclick="location.href='./fanModify.fo?fa_num=<%=fa_num%>&pageNum=<%=pageNum%>';">
						<input type="button" class="bt" value="삭제" onclick="del(<%=fa_num%>)"> 
						<%
								} 
							}
						%>
					</div>



				</div>

				<!-- 수정 삭제 다음글 이전글 버튼끝 -->

			</article>
			<!--  댓글 쓰기 -->
			<div class="clear"></div>
			<%if(mem_num!=null){ %>
				<form id="addCommentForm" style="margin: 10px 0;" action="ComsWriteAction.fo" method="post" >
    				<div id="addComment">
					<input type="hidden" name="fa_num" value="<%=fa_num%>">
       				 <textarea id="dtl_tex" rows="4" cols="100" placeholder="댓글을 입력하세요." name="wtf_content"></textarea>
   					 </div>
   			
      				  <input type="submit"  class="bt_c_write" value="댓글 남기기" />
   				
				</form>
				<div class="clear"></div>
			<%} %>
<!--  댓글 반복 시작 -->
<%		
		List<CommentsBean> CommentList = (List<CommentsBean>)request.getAttribute("CommentsList");

		for( int i=0; i< CommentList.size(); i++) {
		CommentsBean cb = CommentList.get(i);
%>
	 
		 <div class="comments">
   	 <span class="writer"> <%=cb.getWtf_mem_nik() %>&nbsp;&nbsp;</span>
   	 <span class="date">  <%=cb.getWtf_date() %> </span>
   	<%  
	if(mem_num!=null){
   if(mem_num.equals(cb.getWtf_mem_num())){%> 
    <!-- mem_num과 맞을때 수정버튼 뜨게 -->
   	 	<span class="modify-del">
       	 	<a class="modi<%=cb.getWtf_num()%>" id="modi2">수정  </a>
       	 	<!-- 수정버튼을 누르면 수정하기/삭제하기 토글 -->
       	 	<div class="fr<%=cb.getWtf_num()%>" style="display: none;" >
       	 		 <form id="modifyComment" class="comment-form" action="./ComsModifyAction.fo?wtf_num=<%=cb.getWtf_num()%>" method="post">
       	 		 <input type="hidden" name="fa_num" value="<%=fa_num%>">
     	   		 <textarea class="comment-textarea" name="new_content" rows="7" cols="50"><%=cb.getWtf_content()%></textarea><br>
      			 <input type="submit" class="modify_butt" value="수정하기 | "> <%-- | <a class="dell<%=cb.getFbcom_bdnum()%>">취소</a> --%>
      			 <%-- <input type="button" value="수정하기" onclick="location.href='./CommModifyAction.bo?fbcom_bdnum=<%=cb.getFbcom_bdnum()%>&fb_num=<%=fb_num%>'"> --%>
      			 
      			 <input type="button" class="del_butt" onclick="location.href='./ComsDelete.fo?wtf_num=<%=cb.getWtf_num()%>&fa_num=<%=fa_num%>'" value="삭제하기">
   		 		</form>
   		 	</div>
    	</span>
    	<%} %>
    	<%} %>
    <p id="comment"><%=cb.getWtf_content() %> </p> <br><br>
    	<!-- 수정하기 토글 -->
   		<script>
   			$(document).ready(function() {
   				$(".modi<%=cb.getWtf_num()%>").click(function(){
   					/* alert("ddd"); */
   					$(this).next().toggle();
   						
   				});
   				$(".dell<%=cb.getWtf_num()%>").click(function(){
   					$(".fr").toggle();		
   				
   				});
   			});
   			
   		</script>
  		<!-- 수정버튼을 누르면 수정하기/삭제하기 토글 -->

</div> <%} %>
<!--  댓글 반복 끝 -->
			<br> <br>
			<div id="next-prev">

				<%
					if (nextNum == 0) {
				%>
				<p>다음 글이 존재하지 않습니다.</p>
				<%
					} else {
				%>
				<p>
					다음 글 : <a
						href="./fanboardContent.fo?fa_num=<%=nextNum%>&pageNum=<%=pageNum%>"><%=fb2.getFa_subject()%></a>
				</p>
				<%
					}
				%>

				<%
					if (preNum == 0) {
				%>
				<p>이전 글이 존재하지 않습니다.</p>
				<%
					} else {
				%>
				<p>
					이전 글 : <a href="./fanboardContent.fo?fa_num=<%=preNum%>&pageNum=<%=pageNum%>"><%=fb3.getFa_subject()%></a>
				</p>
				<%
					}
		
				%>
			</div>

		</div>

	</div>
</div>
</body>



<div class="clear"></div>
<!-- 본문 영역 끝 -->
<!-- footer 영역 시작-->
<jsp:include page="../main/footer.jsp"></jsp:include>
<!-- footer 영역 끝  -->
</body>
</html>