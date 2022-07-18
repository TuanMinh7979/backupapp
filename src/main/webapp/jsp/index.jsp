<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:layout>

<jsp:body>
	<nav id="nav-bar"
		class="navbar navbar-expand-lg navbar-dark bg-primary fixed-top">
		<div class="container-fluid">
			<a class="navbar-brand">simple app</a>
			<div class="collapse navbar-collapse" id="navbarSupportedContent">
				<ul class="navbar-nav me-auto mb-2 mb-lg-0">
					<li class="nav-item"><a class="nav-link active"
						aria-current="page" href="#driver">For Driver</a></li>
					<li class="nav-item"><a class="nav-link active"
						aria-current="page" href="#customer">For Customer</a></li>
				</ul>
			</div>
		</div>
	</nav>
	<div id="customer" class="container bg-info position-relative">
		<div class="row g-2 position-absolute w-100" style="top:20px">
			<div class="col-8">
				<div class="p-3 border bg-light" style="height: 350px; border-radius:20px">
					<figure class="text-center" style="margin-top: 110px;">
						<blockquote class="blockquote">
							<p>Let us serve you better, please</p>
						</blockquote>
					</figure>
					<div class="hstack gap-2 d-md-flex justify-content-md-center">
						<a href="/login" class="btn btn-outline-primary">Sign in</a>
						<a href="/register" class="btn btn-primary">Sign up</a>
					</div>
				</div>
			</div>
			<div class="col-4">
				<div class="p-3 border bg-light" style="height: 350px; border-radius:20px">
					<figure class="text-center" style="margin-top: 110px;">
						<blockquote class="blockquote">
							<p>Or simple, just</p>
						</blockquote>
					</figure>
					<div class="hstack gap-2 d-md-flex justify-content-md-center">
						<a href="/map-customer" class="btn btn-primary">Using with no account</a>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="driver" class="container bg-primary position-relative">
		<div class="row g-2 position-absolute top-20 w-100" style="top:20px">
			<div class="col-8">
				<div class="p-3 border bg-light" style="height: 350px; border-radius:20px">
					<figure class="text-center " style="margin-top: 110px;"> 
						<blockquote class="blockquote">
							<p>If you are a driver, please</p>
						</blockquote>
					</figure>
					<div class="hstack gap-2 d-md-flex justify-content-md-center">
						<a href="/login" class="btn btn-outline-primary">Sign in</a>
					</div>
				</div>
			</div>
			<div class="col-4">
				<div class="p-3 border bg-light" style="height: 350px; border-radius:20px">
					<figure class="text-center" style="margin-top: 110px;">
						<blockquote class="blockquote">
							<p>Or want to be a driver</p>
						</blockquote>
					</figure>
					<div class="hstack gap-2 d-md-flex justify-content-md-center">
						<a href="/register" class="btn btn-primary">Let's sign up</a>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script src="${pageContext.request.contextPath}/resources/js/main.js"></script>
</jsp:body>
</t:layout>