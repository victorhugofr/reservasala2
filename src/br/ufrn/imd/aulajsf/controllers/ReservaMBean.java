package br.ufrn.imd.aulajsf.controllers;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Inject;
import javax.inject.Named;

import br.ufrn.imd.aulajsf.dominio.Reserva;
import br.ufrn.imd.aulajsf.dominio.Sala;
import br.ufrn.imd.aulajsf.repositorios.ReservaRepositorio;

@Named("reservaMBean")
@SessionScoped
public class ReservaMBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private ReservaRepositorio reservaRepositorio;
	private Reserva reserva;
	private DataModel<Reserva> reservasModel;
	private Sala sala;

	public String fazerReserva() {
		List<Reserva> reservasBd = reservaRepositorio.listarReservas();
		for(int i=0;i<reservasBd.size();i++) {
			if(reservasBd.get(i).getDataReserva().equals(reserva.getDataReserva())
					&& reservasBd.get(i).getSala().equals(sala)) {
				FacesMessage msg = new FacesMessage("Reserva ja cadastrada","");
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				FacesContext.getCurrentInstance().addMessage(null, msg);
				return null;
			}
		}
		
		reserva.setSala(sala);
		cadastrarReserva();
		
		return "/pages/index.jsf?faces-redirect=true";
		
	}


	public String listarReservas() {
		reservasModel = new ListDataModel<Reserva>(reservaRepositorio.listarReservas());

		return "/pages/reserva/list.jsf?faces-redirect=true";
	}
	
	public String removerReserva() {
		Reserva usuarioRemovido = reservasModel.getRowData();
		reservaRepositorio.remover(usuarioRemovido);
		//return listarMateriais();
		reservasModel = new ListDataModel<Reserva>(reservaRepositorio.listarReservas());
		return "/pages/reserva/list.jsf";
	}

	public String cadastrarReserva() {
		reservaRepositorio.salvar(reserva);
		return "/pages/index.jsf";
	}

	public ReservaRepositorio getReservaRepositorio() {
		return reservaRepositorio;
	}

	public void setReservaRepositorio(ReservaRepositorio reservaRepositorio) {
		this.reservaRepositorio = reservaRepositorio;
	}

	public Reserva getReserva() {
		return reserva;
	}

	public void setReserva(Reserva reserva) {
		this.reserva = reserva;
	}

	public DataModel<Reserva> getReservasModel() {
		return reservasModel;
	}
	
	public ReservaMBean() {
		reserva=new Reserva();
		sala=new Sala();
	}

	public Sala getSala() {
		return sala;
	}

	public void setSala(Sala sala) {
		this.sala = sala;
	}

}
