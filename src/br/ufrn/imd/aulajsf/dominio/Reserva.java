package br.ufrn.imd.aulajsf.dominio;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Reserva {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Temporal(TemporalType.DATE)
	private Date dataReserva;
	
	@ManyToOne
	private Sala sala;
	
	@ManyToOne
	private Usuario usuarioCadastrante;

	public Reserva() {
		
	}
	
	public void setDataReserva(String dataReserva) throws ParseException {
		SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd"); 
		Date data = formato.parse(dataReserva);
		this.dataReserva = data;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getDataReserva() {
		return dataReserva;
	}
	public void setDataReserva(Date dataReserva) {
		this.dataReserva = dataReserva;
	}
	public Sala getSala() {
		return sala;
	}
	public void setSala(Sala sala) {
		this.sala = sala;
	}
	public Usuario getUsuarioCadastrante() {
		return usuarioCadastrante;
	}
	public void setUsuarioCadastrante(Usuario usuarioCadastrante) {
		this.usuarioCadastrante = usuarioCadastrante;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataReserva == null) ? 0 : dataReserva.hashCode());
		result = prime * result + id;
		result = prime * result + ((sala == null) ? 0 : sala.hashCode());
		result = prime * result + ((usuarioCadastrante == null) ? 0 : usuarioCadastrante.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reserva other = (Reserva) obj;
		if (dataReserva == null) {
			if (other.dataReserva != null)
				return false;
		} else if (!dataReserva.equals(other.dataReserva))
			return false;
		if (id != other.id)
			return false;
		if (sala == null) {
			if (other.sala != null)
				return false;
		} else if (!sala.equals(other.sala))
			return false;
		if (usuarioCadastrante == null) {
			if (other.usuarioCadastrante != null)
				return false;
		} else if (!usuarioCadastrante.equals(other.usuarioCadastrante))
			return false;
		return true;
	}
	
}
