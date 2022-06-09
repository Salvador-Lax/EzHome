<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
	<head>
		<meta charset="UTF-8">
	    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    	<link rel="icon" href="./img/EZ HOME Logo.png">
		<title>Login</title>
	    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous"/>
    	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.2/font/bootstrap-icons.css">
    	<link href="./styles/style.css" rel="stylesheet" type="text/css">
	</head>
	<body>
	    <header class="sticky-top">
			<nav class="navbar">
				<div class="container-fluid">
				<a class="navbar-brand" href="PrimerInicio">
					<img
					src="./img/EZ HOME Logo.png"
					alt="logo"
					width="70"
					height="70"
					class="d-inline-block"
					/>
					Login
				</a>
				
				</div>
			</nav>
		</header>
		<main class="primerInicio">
			<div class="d-flex justify-content-center">				
				<%
					if (session.getAttribute("errorPrimerInicio") != null) {
						%><p><%=session.getAttribute("errorPrimerInicio")%></p><% 
					};
					session.setAttribute("errorPrimerInicio", null);
				%>
				<form method="post">
					<h1 class="my-3">Verifique su Usuario</h1>
					<div class="form-floating">
						<input type="text" name="codigo" class="form-control" placeholder="codigo" required>
						<label for="codigo">Indique su código aquí <span style="color: green;">*</span></label>
					</div>
					<input type="submit" value="Enviar" class="btn mt-3 text-center">
				</form>
			</div>
		</main>

    	<footer class="d-flex flex-wrap justify-content-between align-items-center py-3 px-5">
			<div class="col-md-4 d-flex align-items-center">
				<img
					src="./img/EZ HOME Logo.png"
					alt="logo"
					width="40"
					height="40"
					class="d-inline-block"
				/>
				<span>&copy; 2022 EzHome, Inc</span>
			</div>

			<ul class="nav col-md-4 justify-content-end list-unstyled d-flex">
				<li class="ms-3">
				<a class="text-muted" href="#">
					<i class="bi bi-twitter" ></i>
				</a>
				</li>
				<li class="ms-3">
				<a class="text-muted" href="#">
					<i class="bi bi-instagram"></i>
				</a>
				</li>
				<li class="ms-3">
				<a class="text-muted" href="#">
					<i class="bi bi-facebook"></i>
				</a>
				</li>
			</ul>
		</footer>

		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous"></script>
	</body>
</html>