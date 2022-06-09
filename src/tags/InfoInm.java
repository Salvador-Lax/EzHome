package tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class InfoInm extends SimpleTagSupport {
	String codigo;
	String descripcion;
	Double precio;
	Double metroCuadrado;
	int numHabitacion;
	String provincia;
	String poblacion;
	int codigoPostal;
	String direccion;
	String coordenada;
	String tNombre;
	String stNombre;

	@Override
	public void doTag() throws JspException, IOException {
		JspWriter out = getJspContext().getOut();

		out.print("<h2 class='mb-5 text-start text-break'>Precio: " + getPrecio() + " €</br><hr>" + gettNombre() + ", "
				+ getStNombre() + ".</br><hr>" + "Direccion: " + getDireccion() + ", en " + getPoblacion() + " (CP:"
				+ getCodigoPostal() + "), " + getProvincia() + ".</br><hr>" + "Habitaciones: " + getNumHabitacion()
				+ ".</br><hr>Metros²: " + getMetroCuadrado() + ".</br><hr>Descripcion:<br>" + getDescripcion()
				+ "</h2>");
		out.print("<hr><div class='mapa'><h2><u>Coordenadas</u></h2>");
		out.print(
				"<iframe width=\"100%\" height=\"600\" frameborder=\"0\" scrolling=\"no\" marginheight=\"0\" marginwidth=\"0\" src=\"https://maps.google.com/maps?width=100%25&amp;height=600&amp;hl=es&amp;q="
						+ getCoordenada()
						+ "+(ezhome)&amp;t=&amp;z=14&amp;ie=UTF8&amp;iwloc=B&amp;output=embed\"></iframe><hr>");
		out.print("</div>");
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public Double getMetroCuadrado() {
		return metroCuadrado;
	}

	public void setMetroCuadrado(Double metroCuadrado) {
		this.metroCuadrado = metroCuadrado;
	}

	public int getNumHabitacion() {
		return numHabitacion;
	}

	public void setNumHabitacion(int numHabitacion) {
		this.numHabitacion = numHabitacion;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getPoblacion() {
		return poblacion;
	}

	public void setPoblacion(String poblacion) {
		this.poblacion = poblacion;
	}

	public int getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(int codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getCoordenada() {
		return coordenada;
	}

	public void setCoordenada(String coordenada) {
		this.coordenada = coordenada;
	}

	public String gettNombre() {
		return tNombre;
	}

	public void settNombre(String tNombre) {
		this.tNombre = tNombre;
	}

	public String getStNombre() {
		return stNombre;
	}

	public void setStNombre(String stNombre) {
		this.stNombre = stNombre;
	}

}
