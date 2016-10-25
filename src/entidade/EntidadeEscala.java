package entidade;

import java.io.Serializable;

import auxiliar.AuxiliarConstantes;

/**
 * @author John
 * Classe que representa as informações de escala.
 */
public class EntidadeEscala implements Serializable {
	
	private Integer id;
	
	private Integer mes;
	private Integer ano;
	private Float horasSemana1;
	private Float horasSemana2;
	private Float horasSemana3;
	private Float horasSemana4;
	private Float horasSemana5;
	private Float horasSemana6;
	private Float horasUltimaSemanaMesAnterior;
	private Float horasUltimaSemana;
	private Integer totalHorasExtras;
	private Integer totalHorasExtrasDiurno;
	private Integer totalHorasExtrasNoturno;
	private Integer quantidadeRefeicoesDiurno;
	private Integer quantidadeRefeicoesNoturno;
	private Float bancoHorasDestaEscala;
	
	private String d1c, d2c, d3c, d4c, d5c, d6c, d7c, d8c, d9c, d10c, d11c, d12c, d13c, 
					d14c, d15c, d16c, d17c, d18c, d19c, d20c, d21c, d22c, d23c, d24c, d25c, d26c, 
					d27c, d28c, d29c, d30c, d31c;
	
	private String de1, de2, de3, de4, de5, de6, de7, de8, de9, de10, de11, 
					de12, de13, de14, de15, de16, de17, de18, de19, de20, de21, de22, 
					de23, de24, de25, de26, de27, de28, de29, de30, de31;
	
	private boolean escalaAlterada;
	private boolean escalaLancada;
	private boolean contemHoraExtra;
	
	private String escalaHoraExtra[] = new String[AuxiliarConstantes.TAMANHO_MES];
	private String escalaHoraContratual[] = new String[AuxiliarConstantes.TAMANHO_MES];
	private String observacao;
	
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public Integer getMes() {
		return mes;
	}
	public void setMes(Integer mes) {
		this.mes = mes;
	}
	public Integer getAno() {
		return ano;
	}
	public void setAno(Integer ano) {
		this.ano = ano;
	}
	public Float getHorasSemana1() {
		return horasSemana1;
	}
	public void setHorasSemana1(Float horasSemana1) {
		this.horasSemana1 = horasSemana1;
	}
	public Float getHorasSemana2() {
		return horasSemana2;
	}
	public void setHorasSemana2(Float horasSemana2) {
		this.horasSemana2 = horasSemana2;
	}
	public Float getHorasSemana3() {
		return horasSemana3;
	}
	public void setHorasSemana3(Float horasSemana3) {
		this.horasSemana3 = horasSemana3;
	}
	public Float getHorasSemana4() {
		return horasSemana4;
	}
	public void setHorasSemana4(Float horasSemana4) {
		this.horasSemana4 = horasSemana4;
	}
	public Float getHorasSemana5() {
		return horasSemana5;
	}
	public void setHorasSemana5(Float horasSemana5) {
		this.horasSemana5 = horasSemana5;
	}
	public Float getHorasSemana6() {
		return horasSemana6;
	}
	public void setHorasSemana6(Float horasSemana6) {
		this.horasSemana6 = horasSemana6;
	}
	public Float getHorasUltimaSemanaMesAnterior() {
		return horasUltimaSemanaMesAnterior;
	}
	public void setHorasUltimaSemanaMesAnterior(Float horasUltimaSemanaMesAnterior) {
		this.horasUltimaSemanaMesAnterior = horasUltimaSemanaMesAnterior;
	}
	public Integer getTotalHorasExtras() {
		return totalHorasExtras;
	}
	public void setTotalHorasExtras(Integer totalHorasExtras) {
		this.totalHorasExtras = totalHorasExtras;
	}
	public Integer getQuantidadeRefeicoesDiurno() {
		return quantidadeRefeicoesDiurno;
	}
	public void setQuantidadeRefeicoesDiurno(Integer quantidadeRefeicoesDiurno) {
		this.quantidadeRefeicoesDiurno = quantidadeRefeicoesDiurno;
	}
	public Integer getQuantidadeRefeicoesNoturno() {
		return quantidadeRefeicoesNoturno;
	}
	public void setQuantidadeRefeicoesNoturno(Integer quantidadeRefeicoesNoturno) {
		this.quantidadeRefeicoesNoturno = quantidadeRefeicoesNoturno;
	}
	
	public String getD1c() {
		return d1c;
	}
	public void setD1c(String d1c) {
		this.d1c = d1c;
	}
	public String getD2c() {
		return d2c;
	}
	public void setD2c(String d2c) {
		this.d2c = d2c;
	}
	public String getD3c() {
		return d3c;
	}
	public void setD3c(String d3c) {
		this.d3c = d3c;
	}
	public String getD4c() {
		return d4c;
	}
	public void setD4c(String d4c) {
		this.d4c = d4c;
	}
	public String getD5c() {
		return d5c;
	}
	public void setD5c(String d5c) {
		this.d5c = d5c;
	}
	public String getD6c() {
		return d6c;
	}
	public void setD6c(String d6c) {
		this.d6c = d6c;
	}
	public String getD7c() {
		return d7c;
	}
	public void setD7c(String d7c) {
		this.d7c = d7c;
	}
	public String getD8c() {
		return d8c;
	}
	public void setD8c(String d8c) {
		this.d8c = d8c;
	}
	public String getD9c() {
		return d9c;
	}
	public void setD9c(String d9c) {
		this.d9c = d9c;
	}
	public String getD10c() {
		return d10c;
	}
	public void setD10c(String d10c) {
		this.d10c = d10c;
	}
	public String getD11c() {
		return d11c;
	}
	public void setD11c(String d11c) {
		this.d11c = d11c;
	}
	public String getD12c() {
		return d12c;
	}
	public void setD12c(String d12c) {
		this.d12c = d12c;
	}
	public String getD13c() {
		return d13c;
	}
	public void setD13c(String d13c) {
		this.d13c = d13c;
	}
	public String getD14c() {
		return d14c;
	}
	public void setD14c(String d14c) {
		this.d14c = d14c;
	}
	public String getD15c() {
		return d15c;
	}
	public void setD15c(String d15c) {
		this.d15c = d15c;
	}
	public String getD16c() {
		return d16c;
	}
	public void setD16c(String d16c) {
		this.d16c = d16c;
	}
	public String getD17c() {
		return d17c;
	}
	public void setD17c(String d17c) {
		this.d17c = d17c;
	}
	public String getD18c() {
		return d18c;
	}
	public void setD18c(String d18c) {
		this.d18c = d18c;
	}
	public String getD19c() {
		return d19c;
	}
	public void setD19c(String d19c) {
		this.d19c = d19c;
	}
	public String getD20c() {
		return d20c;
	}
	public void setD20c(String d20c) {
		this.d20c = d20c;
	}
	public String getD21c() {
		return d21c;
	}
	public void setD21c(String d21c) {
		this.d21c = d21c;
	}
	public String getD22c() {
		return d22c;
	}
	public void setD22c(String d22c) {
		this.d22c = d22c;
	}
	public String getD23c() {
		return d23c;
	}
	public void setD23c(String d23c) {
		this.d23c = d23c;
	}
	public String getD24c() {
		return d24c;
	}
	public void setD24c(String d24c) {
		this.d24c = d24c;
	}
	public String getD25c() {
		return d25c;
	}
	public void setD25c(String d25c) {
		this.d25c = d25c;
	}
	public String getD26c() {
		return d26c;
	}
	public void setD26c(String d26c) {
		this.d26c = d26c;
	}
	public String getD27c() {
		return d27c;
	}
	public void setD27c(String d27c) {
		this.d27c = d27c;
	}
	public String getD28c() {
		return d28c;
	}
	public void setD28c(String d28c) {
		this.d28c = d28c;
	}
	public String getD29c() {
		return d29c;
	}
	public void setD29c(String d29c) {
		this.d29c = d29c;
	}
	public String getD30c() {
		return d30c;
	}
	public void setD30c(String d30c) {
		this.d30c = d30c;
	}
	public String getD31c() {
		return d31c;
	}
	public void setD31c(String d31c) {
		this.d31c = d31c;
	}
	public String getDe1() {
		return de1;
	}
	public void setDe1(String de1) {
		this.de1 = de1;
	}
	public String getDe2() {
		return de2;
	}
	public void setDe2(String de2) {
		this.de2 = de2;
	}
	public String getDe3() {
		return de3;
	}
	public void setDe3(String de3) {
		this.de3 = de3;
	}
	public String getDe4() {
		return de4;
	}
	public void setDe4(String de4) {
		this.de4 = de4;
	}
	public String getDe5() {
		return de5;
	}
	public void setDe5(String de5) {
		this.de5 = de5;
	}
	public String getDe6() {
		return de6;
	}
	public void setDe6(String de6) {
		this.de6 = de6;
	}
	public String getDe7() {
		return de7;
	}
	public void setDe7(String de7) {
		this.de7 = de7;
	}
	public String getDe8() {
		return de8;
	}
	public void setDe8(String de8) {
		this.de8 = de8;
	}
	public String getDe9() {
		return de9;
	}
	public void setDe9(String de9) {
		this.de9 = de9;
	}
	public String getDe10() {
		return de10;
	}
	public void setDe10(String de10) {
		this.de10 = de10;
	}
	public String getDe11() {
		return de11;
	}
	public void setDe11(String de11) {
		this.de11 = de11;
	}
	public String getDe12() {
		return de12;
	}
	public void setDe12(String de12) {
		this.de12 = de12;
	}
	public String getDe13() {
		return de13;
	}
	public void setDe13(String de13) {
		this.de13 = de13;
	}
	public String getDe14() {
		return de14;
	}
	public void setDe14(String de14) {
		this.de14 = de14;
	}
	public String getDe15() {
		return de15;
	}
	public void setDe15(String de15) {
		this.de15 = de15;
	}
	public String getDe16() {
		return de16;
	}
	public void setDe16(String de16) {
		this.de16 = de16;
	}
	public String getDe17() {
		return de17;
	}
	public void setDe17(String de17) {
		this.de17 = de17;
	}
	public String getDe18() {
		return de18;
	}
	public void setDe18(String de18) {
		this.de18 = de18;
	}
	public String getDe19() {
		return de19;
	}
	public void setDe19(String de19) {
		this.de19 = de19;
	}
	public String getDe20() {
		return de20;
	}
	public void setDe20(String de20) {
		this.de20 = de20;
	}
	public String getDe21() {
		return de21;
	}
	public void setDe21(String de21) {
		this.de21 = de21;
	}
	public String getDe22() {
		return de22;
	}
	public void setDe22(String de22) {
		this.de22 = de22;
	}
	public String getDe23() {
		return de23;
	}
	public void setDe23(String de23) {
		this.de23 = de23;
	}
	public String getDe24() {
		return de24;
	}
	public void setDe24(String de24) {
		this.de24 = de24;
	}
	public String getDe25() {
		return de25;
	}
	public void setDe25(String de25) {
		this.de25 = de25;
	}
	public String getDe26() {
		return de26;
	}
	public void setDe26(String de26) {
		this.de26 = de26;
	}
	public String getDe27() {
		return de27;
	}
	public void setDe27(String de27) {
		this.de27 = de27;
	}
	public String getDe28() {
		return de28;
	}
	public void setDe28(String de28) {
		this.de28 = de28;
	}
	public String getDe29() {
		return de29;
	}
	public void setDe29(String de29) {
		this.de29 = de29;
	}
	public String getDe30() {
		return de30;
	}
	public void setDe30(String de30) {
		this.de30 = de30;
	}
	public String getDe31() {
		return de31;
	}
	public void setDe31(String de31) {
		this.de31 = de31;
	}
	public boolean isContemHoraExtra() {
		return contemHoraExtra;
	}
	public void setContemHoraExtra(boolean contemHoraExtra) {
		this.contemHoraExtra = contemHoraExtra;
	}
	public String[] getEscalaHoraContratual() {
		return escalaHoraContratual;
	}
	public void setEscalaHoraContratual(String[] escalaHoraContratual) {
		this.escalaHoraContratual = escalaHoraContratual;
	}
	
	public boolean isEscalaLancada() {
		return escalaLancada;
	}
	
	public void setEscalaLancada(boolean escalaLancada) {
		this.escalaLancada = escalaLancada;
	}
	
	public String[] getEscalaHoraExtra() {
		return escalaHoraExtra;
	}
	
	public void setEscalaHoraExtra(String[] escalaHoraExtra) {
		this.escalaHoraExtra = escalaHoraExtra;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getId() {
		return id;
	}

	public void setEscalaAlterada(boolean escalaAlterada) {
		this.escalaAlterada = escalaAlterada;
	}
	public boolean isEscalaAlterada() {
		return escalaAlterada;
	}
	public void setBancoDeHorasEscalaAtual(Float bancoDeHorasEscalaAtual) {
		this.bancoHorasDestaEscala = bancoDeHorasEscalaAtual;
	}
	public Float getBancoDeHorasEscalaAtual() {
		return bancoHorasDestaEscala;
	}
	
	public Integer getTotalHorasExtrasDiurno() {
		return totalHorasExtrasDiurno;
	}
	public void setTotalHorasExtrasDiurno(Integer totalHorasExtrasDiurno) {
		this.totalHorasExtrasDiurno = totalHorasExtrasDiurno;
	}
	public Integer getTotalHorasExtrasNoturno() {
		return totalHorasExtrasNoturno;
	}
	public void setTotalHorasExtrasNoturno(Integer totalHorasExtrasNoturno) {
		this.totalHorasExtrasNoturno = totalHorasExtrasNoturno;
	}
	public Float getHorasUltimaSemana() {
		return horasUltimaSemana;
	}
	public void setHorasUltimaSemana(Float passagemProximoMes) {
		this.horasUltimaSemana = passagemProximoMes;
	}
	public Float getBancoHorasDestaEscala() {
		return bancoHorasDestaEscala;
	}
	public void setBancoHorasDestaEscala(Float bancoHorasDestaEscala) {
		this.bancoHorasDestaEscala = bancoHorasDestaEscala;
	}
}
