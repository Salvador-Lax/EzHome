package beans;

import java.io.Serializable;
import java.sql.Timestamp;

public class CitasBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String tipo;
	String subtipo;
	String provincia;
	String poblacion;
	String direccion;
	Timestamp fechayhora;
	String nombre;
	String telefono;
	String email;
	long idCliente;
	long idAgente;
	String codigoInmueble;

	public CitasBean() {
	}

	public CitasBean(String tipo, String subtipo, String provincia, String poblacion, String direccion,
			Timestamp fechayhora, String nombre, String telefono, String email, long idCliente, long idAgente,
			String codigoInmueble) {
		this.tipo = tipo;
		this.subtipo = subtipo;
		this.provincia = provincia;
		this.poblacion = poblacion;
		this.direccion = direccion;
		this.fechayhora = fechayhora;
		this.nombre = nombre;
		this.telefono = telefono;
		this.email = email;
		this.idCliente = idCliente;
		this.idAgente = idAgente;
		this.codigoInmueble = codigoInmueble;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getSubtipo() {
		return subtipo;
	}

	public void setSubtipo(String subtipo) {
		this.subtipo = subtipo;
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

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Timestamp getFechayhora() {
		return fechayhora;
	}

	public void setFechayhora(Timestamp fechayhora) {
		this.fechayhora = fechayhora;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(long idCliente) {
		this.idCliente = idCliente;
	}

	public long getIdAgente() {
		return idAgente;
	}

	public void setIdAgente(long idAgente) {
		this.idAgente = idAgente;
	}

	public String getCodigoInmueble() {
		return codigoInmueble;
	}

	public void setCodigoInmueble(String codigoInmueble) {
		this.codigoInmueble = codigoInmueble;
	}

}
