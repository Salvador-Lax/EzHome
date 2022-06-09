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

import beans.PerfilBean;
import miBibliotecaGeneral.ConversionArchivo;
import miBibliotecaGeneral.Encriptado;
import miModelo.AgenteDAO;
import miModelo.AgenteVO;
import miModelo.ClienteDAO;
import miModelo.ClienteVO;

/**
 * Servlet implementation class ModificarPerfil
 */
@WebServlet("/ModificarPerfil")
public class ModificarPerfil extends HttpServlet {
	private static final long serialVersionUID = 1L;

	HttpSession sesion;
	ClienteVO cvo;
	AgenteVO avo;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PerfilBean pb;
		sesion = request.getSession();

		cvo = (ClienteVO) sesion.getAttribute("cliente");
		avo = (AgenteVO) sesion.getAttribute("agente");

		// Comprobamos si el usuario esta logueado y comprobamos que sus datos esten
		// actualizados
		if (avo != null) {
			pb = new PerfilBean(avo.getDni(), avo.getNombre(), avo.getApellido(), avo.getDireccion(), avo.getEmail(),
					avo.getTelefono());

			sesion.setAttribute("infoPerfil", pb);

			request.getRequestDispatcher("WEB-INF/modificarPerfil.jsp").forward(request, response);
		} else {
			if (cvo != null && cvo.getCodigoVerificar().equals("0")) {
				pb = new PerfilBean(cvo.getDni(), cvo.getNombre(), cvo.getApellido(), cvo.getDireccion(),
						cvo.getEmail(), cvo.getTelefono());

				sesion.setAttribute("infoPerfil", pb);

				request.getRequestDispatcher("WEB-INF/modificarPerfil.jsp").forward(request, response);

			} else {
				response.sendRedirect("Login");
			}

		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ClienteVO cvoMod;
		ClienteDAO cdao;
		AgenteVO avoMod;
		AgenteDAO adao;
		Encriptado e;
		ConversionArchivo ca;

		String dni;
		String nombre;
		String apellido;
		String direccion;
		String email;
		String telefono;
		String passwd;
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

		cvo = (ClienteVO) sesion.getAttribute("cliente");

		dni = "";
		nombre = "";
		apellido = "";
		direccion = "";
		email = "";
		telefono = "";
		passwd = "";
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
						passwd = objetoArchivo.getString("UTF-8");
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

		// Comprobamos el tipo de usuario y los datos insertados en el formulario
		if (avo != null) {
			if (dni.equals("")) {
				dni = avo.getDni();
			}

			if (nombre.equals("")) {
				nombre = avo.getNombre();
			}

			if (apellido.equals("")) {
				apellido = avo.getApellido();
			}

			if (direccion.equals("")) {
				direccion = avo.getDireccion();
			}

			if (email.equals("")) {
				email = avo.getEmail();
			}

			if (telefono.equals("")) {
				telefono = avo.getTelefono();
			}

			if (passwd.equals("")) {
				passwd = avo.getPasswd();
			} else {
				e = new Encriptado();
				passwd = e.generar(passwd);
			}

			if (imagen == null) {
				imagen = avo.getImagen();
			}

			avoMod = new AgenteVO(dni, nombre, apellido, direccion, email, telefono, passwd, imagen);

			adao = new AgenteDAO();

			// Comprobamos que la modificacion haya funcionado
			if (adao.modificarAgente(avoMod, avo.getId())) {
				response.sendRedirect("Perfil");
			} else {
				sesion.setAttribute("errorModificarPerfil", "Error al modificar el usuario");
				response.sendRedirect("ModificarPerfil");
			}
		}

		if (cvo != null) {
			if (dni.equals("")) {
				dni = cvo.getDni();
			}

			if (nombre.equals("")) {
				nombre = cvo.getNombre();
			}

			if (apellido.equals("")) {
				apellido = cvo.getApellido();
			}

			if (direccion.equals("")) {
				direccion = cvo.getDireccion();
			}

			if (email.equals("")) {
				email = cvo.getEmail();
			}

			if (telefono.equals("")) {
				telefono = cvo.getTelefono();
			}

			if (passwd.equals("")) {
				passwd = cvo.getPasswd();
			} else {
				e = new Encriptado();
				passwd = e.generar(passwd);
			}

			if (imagen == null) {
				imagen = cvo.getImagen();
			}

			cvoMod = new ClienteVO(dni, nombre, apellido, direccion, email, telefono, passwd, imagen);

			cdao = new ClienteDAO();

			// Comprobamos que la modificacion haya funcionado
			if (cdao.modificarCliente(cvoMod, cvo.getId())) {
				response.sendRedirect("Perfil");
			} else {
				sesion.setAttribute("errorModificarPerfil", "Error al modificar el usuario");
				response.sendRedirect("ModificarPerfil");
			}
		}

	}

}
