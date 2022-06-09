package servlets;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import miBibliotecaGeneral.CadenaAleatoria;
import miBibliotecaGeneral.ConversionArchivo;
import miBibliotecaGeneral.Correo;
import miBibliotecaGeneral.Encriptado;
import miModelo.AgenteVO;
import miModelo.ClienteDAO;
import miModelo.ClienteVO;

/**
 * Servlet implementation class CrearUsuario
 */
@WebServlet("/Registro")
public class Registro extends HttpServlet {
	private static final long serialVersionUID = 1L;

	HttpSession sesion;
	ClienteVO cvo;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AgenteVO avo;

		sesion = request.getSession();

		cvo = (ClienteVO) sesion.getAttribute("cliente");
		avo = (AgenteVO) sesion.getAttribute("agente");

		if (cvo != null || avo != null) {
			response.sendRedirect("Home");
		} else {
			request.getRequestDispatcher("WEB-INF/registro.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ClienteDAO cdao;
		Encriptado e;
		Correo confirmacion;
		CadenaAleatoria codigo;
		ConversionArchivo ca;

		String dni;
		String nombre;
		String apellido;
		String direccion;
		String email;
		String telefono;
		String passwd;
		String codigoVerificar;
		byte[] imagen;

		File archivo;
		int tamanioMaximoArchivo;
		int tamanioMaximoMemoria;
		String ruta;
		String rutaArchivo;
		String nombreArchivo;
		ServletFileUpload sfu;
		String tipoContenido;
		FileItem objetoArchivo;
		List<FileItem> listaArchivo;
		DiskFileItemFactory dfif;

		sesion = request.getSession();

		dni = "";
		nombre = "";
		apellido = "";
		direccion = "";
		email = "";
		telefono = "";
		passwd = "";
		codigoVerificar = "";
		imagen = null;

		response.getWriter();

		ruta = this.getServletContext().getRealPath("/WEB-INF/data/");

		rutaArchivo = ruta;

		tipoContenido = request.getContentType();

		// Recorremos el formulario y guardamos la informacion obtenida en las variables
		if (tipoContenido.indexOf("multipart/form-data") >= 0) {
			dfif = new DiskFileItemFactory();
			tamanioMaximoMemoria = 5000 * 1024;
			dfif.setSizeThreshold(tamanioMaximoMemoria);
			dfif.setRepository(new File("."));

			sfu = new ServletFileUpload(dfif);

			tamanioMaximoArchivo = 5000 * 1024;
			sfu.setSizeMax(tamanioMaximoArchivo);

			try {
				listaArchivo = sfu.parseRequest(request);
				Iterator<FileItem> i = listaArchivo.iterator();

				while (i.hasNext()) {
					objetoArchivo = i.next();

					if (objetoArchivo.getFieldName().equals("dni")) {
						dni = objetoArchivo.getString("UTF-8");
					}
					if (objetoArchivo.getFieldName().equals("nombre")) {
						nombre = objetoArchivo.getString("UTF-8");
					}
					if (objetoArchivo.getFieldName().equals("apellido")) {
						apellido = objetoArchivo.getString("UTF-8");
					}
					if (objetoArchivo.getFieldName().equals("direccion")) {
						direccion = objetoArchivo.getString("UTF-8");
					}
					if (objetoArchivo.getFieldName().equals("email")) {
						email = objetoArchivo.getString("UTF-8");
					}
					if (objetoArchivo.getFieldName().equals("telefono")) {
						telefono = objetoArchivo.getString("UTF-8");
					}
					if (objetoArchivo.getFieldName().equals("passwd")) {
						e = new Encriptado();
						passwd = e.generar(objetoArchivo.getString("UTF-8"));
					}

					if (!objetoArchivo.isFormField()) {
						nombreArchivo = objetoArchivo.getName();

						if (nombreArchivo.lastIndexOf("/") >= 0) {
							archivo = new File(rutaArchivo + nombreArchivo.substring(nombreArchivo.lastIndexOf("/")));
						} else {
							archivo = new File(
									rutaArchivo + "/" + nombreArchivo.substring(nombreArchivo.lastIndexOf("/") + 1));
						}

						objetoArchivo.write(archivo);

						ca = new ConversionArchivo();
						imagen = ca.convertirArchivoABytes(archivo);

						archivo.delete();
					}
				}
			} catch (Exception exc) {
				exc.printStackTrace();
			}
		}

		cdao = new ClienteDAO();

		if (cdao.consultaGenerica("email = '" + email + "'") == null) {
			// Generamos un codigo aleatorio con nuestra biblioteca CadenaAleatoria
			codigo = new CadenaAleatoria();
			codigoVerificar = codigo.generar();

			// Construimos un objeto ClienteVO
			cvo = new ClienteVO(dni, nombre, apellido, direccion, email, telefono, passwd, codigoVerificar, imagen);

			if (cdao.crearCliente(cvo)) {
				confirmacion = new Correo();

				// Mandamos un correo con el codigo de verificacion
				confirmacion.enviar(email, "Su código de verificación en EzHome",
						"Usted se ha registrado en la pagina EzHome,\n" + "su código de verificación es: "
								+ codigoVerificar + ".\n"
								+ "Si usted no se ha registrado, ignore este e-mail y compruebe\n"
								+ "la seguridad de su correo.");
				response.sendRedirect("Login");
			} else {
				sesion.setAttribute("errorRegistro", "Error al crear el usuario");
				response.sendRedirect("Registro");
			}
		} else {
			sesion.setAttribute("errorRegistro", "Este correo ya esta registrado");
			response.sendRedirect("Registro");
		}

	}

}
