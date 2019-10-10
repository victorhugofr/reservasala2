package br.ufrn.imd.aulajsf.controllers;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Inject;
import javax.inject.Named;

import br.ufrn.imd.aulajsf.dominio.Usuario;
import br.ufrn.imd.aulajsf.repositorios.UsuarioRepositorio;

@Named("usuarioMBean")
@SessionScoped
public class UsuarioMBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioRepositorio usuarioRepositorio;
	private Usuario usuario;
	private Usuario usuarioLogado;
	private DataModel<Usuario> usuariosModel;

	public DataModel<Usuario> getUsuariosModel() {
		return usuariosModel;
	}

	public void setUsuariosModel(DataModel<Usuario> usuariosModel) {
		this.usuariosModel = usuariosModel;
	}

	public String logar() {
		Usuario usuarioBd = usuarioRepositorio.getUsuario(usuario.getLogin());
		if (usuarioBd != null && usuarioBd.getSenha().equals(usuario.getSenha())) {
			usuarioLogado = usuarioBd;
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuarioLogado", usuarioLogado);
			return "/pages/index.jsf?faces-redirect=true";
		} else if(usuarioBd != null && !usuarioBd.getSenha().equals(usuario.getSenha())){
			FacesMessage msg = new FacesMessage("Senha incorreta","");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return null;
		}else {
			FacesMessage msg = new FacesMessage("Usuario não encontrado","");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return null;
		}
	}

	public String deslogar() {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().invalidateSession();

		if (usuarioLogado != null) {
			usuarioLogado = null;
			usuario = null;
		}
		try {
			context.getExternalContext().redirect("/reservasala/login/login.jsf?faces-redirect=true");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "/reservasala/login/login.jsf?faces-redirect=true";

	}

	public String cadastrarLogado() {
		Date dataCadastro = new Date();
		usuario.setDataCadastro(dataCadastro);
		usuarioRepositorio.salvar(usuario);
//		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuarioLogado", usuario);
		return "/pages/index.jsf";
	}
	
	public String listarUsuarios() {
		usuariosModel = new ListDataModel<Usuario>(usuarioRepositorio.listarUsuarios());

		return "/pages/usuarios/list.jsf?faces-redirect=true";
	}
	
	public String removerUsuario() {
		Usuario usuarioRemovido = usuariosModel.getRowData();
		usuarioRepositorio.remover(usuarioRemovido);
		//return listarMateriais();
		usuariosModel = new ListDataModel<Usuario>(usuarioRepositorio.listarUsuarios());
		return "/pages/material/list.jsf";
	}

	public String cadastrar() {
		Date dataCadastro = new Date();
		usuario.setDataCadastro(dataCadastro);
		usuarioRepositorio.salvar(usuario);
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuarioLogado", usuario);
		return "/pages/index.jsf";
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public UsuarioMBean() {
		usuario = new Usuario();
	}
}
