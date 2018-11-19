<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   
	<header id="header-head">
		<div id="header-top">
			<div id="header-inner-top">
			<div id="header-logo">
			<h1>
				<i class="fa fa-search" style="color:#fff"></i>
				<a href="../main/main-home.jsp">오늘 뭐 볼까?</a>
			</h1>
			</div>
			<nav id="header-menu" style="width: 70%">
				<ul>
					<li>
						<!-- <A HREF="#">검색</A> -->
						<form action="../main/search.jsp" method="get" style="display: unset;" class="example">
							<div id="header-srch">
								<input type="text" name="query" placeholder="웹툰 검색">
								<button type="submit"><i class="fa fa-search"></i></button>
							</div>
						</form>
					</li>
					<li>
					<li>
						<div id="header-profile">
							<a href="#">
								<span>닉네임</span>
								<i class="fa fa-caret-down"></i>
							</a>
							<div class="dropdown-content">
						      <a href="../member/myProfile.jsp">회원정보1</a>
						      <a href="#">회원정보2</a>
						      <a href="#">로그아웃/로그인</a>
						    </div>
						</div>
					</li>
				</ul>
			</nav>
			</div>
				<!-- 프로필 이미지  -->
		</div>
	<!-- 헤더 확장, 홈에만 사용되는 부분이므로 지우시면 됩니다 (끝) -->
	</header>


