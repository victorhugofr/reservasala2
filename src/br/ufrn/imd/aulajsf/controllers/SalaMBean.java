package br.ufrn.imd.aulajsf.controllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Inject;
import javax.inject.Named;

import br.ufrn.imd.aulajsf.dominio.Sala;
import br.ufrn.imd.aulajsf.repositorios.SalaRepositorio;

@Named("salaMBean")
@SessionScoped
public class SalaMBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Sala sala;
	private List<Sala> salasList;
	private DataModel<Sala> salasModel;

	public String listarSala() {
		salasModel = new ListDataModel<Sala>(SalaRepositorio.listarSalas());

		return "/pages/sala/list.jsf?faces-redirect=true";
	}
	
	public String removerSala() {
		Sala usuarioRemovido = salasModel.getRowData();
		SalaRepositorio.remover(usuarioRemovido);
		//return listarMateriais();
		salasModel = new ListDataModel<Sala>(SalaRepositorio.listarSalas());
		return "/pages/sala/list.jsf";
	}

	public String cadastrarSala() {
		SalaRepositorio.salvar(sala);
		return "/pages/sala/list.jsf";
	}


	public Sala getSala() {
		return sala;
	}

	public void setSala(Sala sala) {
		this.sala = sala;
	}

	public DataModel<Sala> getSalasModel() {
		return salasModel;
	}

	public void setSalasModel(DataModel<Sala> salasModel) {
		this.salasModel = salasModel;
	}
	
	public SalaMBean() {
		sala=new Sala();
	}

	public List<Sala> getSalasList() {
		salasList = new ArrayList<Sala>(SalaRepositorio.listarSalas());
		return salasList;
	}

	public void setSalasList(List<Sala> salasList) {
		this.salasList = salasList;
	}

}
