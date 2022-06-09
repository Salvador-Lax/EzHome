<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
    	<link rel="icon" href="./img/EZ HOME Logo.png">
		<title>Registro</title>
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous"/>
    	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.2/font/bootstrap-icons.css">
    	<link href="./styles/style.css" rel="stylesheet" type="text/css">
	</head>
	<body>
		<header class="sticky-top">
			<nav class="navbar">
				<div class="container-fluid">
				<a class="navbar-brand" href="Registro">
					<img
					src="./img/EZ HOME Logo.png"
					alt="logo"
					width="70"
					height="70"
					class="d-inline-block"
					/>
					Registro
				</a>
				<button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarToggleExternalContent" aria-controls="navbarToggleExternalContent" aria-expanded="false" aria-label="Toggle navigation">
					<i class="bi bi-list"></i>
				</button>
				</div>
			</nav>
			<div class="collapse" id="navbarToggleExternalContent">
				<div class="shadow-3 p-4 d-flex justify-content-center flex-column">
					<a href="Home" class="btn btn-link btn-block m-0" style="text-decoration: none; font-size: x-large;">Home</a>
					<hr>
					<a href="Catalogo" class="btn btn-link btn-block m-0" style="text-decoration: none; font-size: x-large;">Catalogo</a>
					<hr>
					<a href="Contacto" class="btn btn-link btn-block m-0" style="text-decoration: none; font-size: x-large;">Contacto</a>
					<hr>
					<a href="Login" class="btn btn-link btn-block m-0" style="text-decoration: none; font-size: x-large;">Login</a>				</div>
			</div>
		</header>

		<main id="registro">
			<% 
				if (session.getAttribute("errorRegistro") != null) {
					%><script>alert("<%=session.getAttribute("errorRegistro")%>");</script><%
					session.setAttribute("errorRegistro", null);	
				}; 
			%>
			<div class="d-flex justify-content-center">				
				<form method="post" enctype="multipart/form-data">
					<h2 class="text-center pb-3"><u>Registro de Usuario</u></h2>
					<div class="form-floating">
						<input type="text" name="dni" class="form-control" placeholder="dni" required maxlength="9" pattern="(^\d{8}([A-Z])$)">
						<label for="dni" >Documentación (DNI/NIF) <span style="color: green;">*</span></label>	
					</div>

					<div class="form-floating">
						<input type="text" name="nombre" class="form-control" placeholder="nombre" maxlength="30" required>
						<label for="nombre">Nombre <span style="color: green;">*</span></label>
					</div>

					<div class="form-floating">					
						<input type="text" name="apellido" class="form-control" placeholder="apellido" maxlength="30" required>
						<label for="apellido">Apellidos <span style="color: green;">*</span></label>									
					</div>

					<div class="form-floating">
						<input type="text" name="direccion" class="form-control" placeholder="direccion" maxlength="50" required>
						<label for="direccion">Dirección <span style="color: green;">*</span></label>

					</div>

					<div class="form-floating">
						<input type="email" name="email" class="form-control" placeholder="email" maxlength="50" required>
						<label for="email">E-Mail <span style="color: green;">*</span></label>
					</div>
					
					<div class="form-floating">
						<input type="tel" name="telefono" class="form-control" placeholder="telefono" pattern="[0-9]*$" maxlength="9" required>
						<label for="telefono">Numero de contacto <span style="color: green;">*</span></label>
					</div>

					<div class="form-floating">
						<input type="password" name="passwd" class="form-control" placeholder="passwd" required>
						<label for="passwd">Contraseña <span style="color: green;">*</span></label>
					</div>

					<div>
						<label for="imagen" class="form-label" style="color: #e3c800;">Imagen de perfil</label>
						<br>
						<input type="file" class="form-control" name="imagen" accept="image/*">
					</div>

					<div class="d-flex justify-content-center"> 
						<input type="submit" value="Registrarse" class="btn" style="color: #e3c800;">
					</div>

					<a href="Login">¿Ya tienes una cuenta? Inicia sesión</a>
				</form>
			</div>
		</main>

		<footer class="d-flex flex-wrap justify-content-between align-items-center py-3 px-5">
			<div class="col-md-4 d-flex align-items-center">
				<a href="Home" class="mb-3 me-2 mb-md-0 text-muted text-decoration-none lh-1">
				<img
					src="./img/EZ HOME Logo.png"
					alt="logo"
					width="40"
					height="40"
					class="d-inline-block"
				/>
				</a>
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