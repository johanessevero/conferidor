package processamento;

import java.io.File;
import java.util.HashMap;

import auxiliar.AuxiliarConstantes;
import auxiliar.RNException;
import entidade.EntidadeLegenda;
import entidade.EntidadeMensagem;
import entidade.EntidadeServidor;

/**
 * 
 * @author Johanes Severo dos Santos
 * Classe que confere os valores obtidos de uma escala e verifica se est� dentro dos limites estabelecidos.
 */
public class ProcessConferidor {

	private int numErros;
	
	private HashMap<String, EntidadeMensagem> relatoriosErro;
	
	private String textoMensagem = "";
	
	private boolean escalaOK;
	
	public ProcessConferidor() {
		
		this.numErros = 0;
		this.relatoriosErro = new HashMap<String, EntidadeMensagem>();
		
		this.escalaOK = true;
		
	}
	
	public EntidadeMensagem conferirEscala(EntidadeServidor servidores[], boolean contemExtra) {
	
		
		ProcessCalculo calculoHoras = new ProcessCalculo();
		EntidadeMensagem mensagem = null;
		
			for (int i = 0; i < servidores.length; ++i) {
				
				//textoMensagem += "$Ano: " + servidores[i].getEscala().getAno() + " Mes: " + servidores[i].getEscala().getMes();
		
				//textoMensagem += "$Matricula: " + servidores[i].getMatricula();
				//textoMensagem += "$Cargo: " + servidores[i].getCargo(); 
				//textoMensagem += "$Carga horaria: " + servidores[i].getCargaHoraria() + "$";
				//textoMensagem += "$Setor: " + servidores[i].getSetor();
				//textoMensagem += "$Carga horaria no setor: " + servidores[i].getCargaHorariaSetor() + "$";
				//textoMensagem += "$Observacao: " + servidores[i].getEscala().getObservacao() + "$";
				calculoHoras.calcular(servidores[i]);
				/*(textoMensagem += "$$Escala contratual: ";
				for (int k = 0; k < servidores[i].getEscala().getEscalaHoraContratual().length; ++k) {
					textoMensagem += "Dia " + (k + 1) + ": " + servidores[i].getEscala().getEscalaHoraContratual()[k] + " ";
					if (k % 10 == 0)
						textoMensagem += "$";
				}
				textoMensagem += "$$Escala extra: ";
				for (int k = 0; k < servidores[i].getEscala().getEscalaHoraContratual().length; ++k) {
					textoMensagem += "Dia " + (k + 1) + ": " + servidores[i].getEscala().getEscalaHoraExtra()[k] + " ";
					if (k % 10 == 0)
						textoMensagem += "$";
				}*/
				
				/*textoMensagem += "Compensacao: " + servidores[i].getEscala().getCompensacao() + "$";
				textoMensagem += "Passagem de horas do mes anterior: " + servidores[i].getEscala().getPassagemMesAnterior() + "$";
				textoMensagem += "Passagem de horas para o proximo mes: " + servidores[i].getEscala().getPassagemProximoMes() + "$";*/
				//textoMensagem += "Semana 1: " + servidores[i].getEscala().getHorasSemana1() + " ";
				/*textoMensagem += "Semana 2: " + servidores[i].getEscala().getHorasSemana2() + " ";
				textoMensagem += "Semana 3: " + servidores[i].getEscala().getHorasSemana3() + " ";
				textoMensagem += "Semana 4: " + servidores[i].getEscala().getHorasSemana4() + " ";
				textoMensagem += "Semana 5: " + servidores[i].getEscala().getHorasSemana5() + " ";
				textoMensagem += "Semana 6: " + servidores[i].getEscala().getHorasSemana6() + "$";
				textoMensagem += "Total de horas extras: " + servidores[i].getEscala().getTotalHorasExtras() + "$";
				textoMensagem += "Quantidade de refeicoes: " + servidores[i].getEscala().getQuantidadeRefeicoesDiurno() + "$";*/
				//textoMensagem += "<h3>"+ servidores[i].getNome() + " " + servidores[i].getMatricula() + "</h3>$";
				textoMensagem += validaErroDigitacao(servidores[i].getEscala().getEscalaHoraContratual(), servidores[i].getEscala().getEscalaHoraExtra());
				
				//textoMensagem += validaCompensacao(servidores[i].getEscala().getBancoHorasDestaEscala());
			
				textoMensagem += validaAfastamentoHorasExtras(servidores[i].getEscala().getEscalaHoraContratual(), servidores[i].getEscala().getEscalaHoraExtra());
				
				if (contemExtra) {
					
					textoMensagem += validaHorasExtras(servidores[i].getEscala().getTotalHorasExtras(), calculoHoras.isExtraNoturno());
				}
				else {
					textoMensagem+= validaHorasExtras(servidores[i].getEscala().getEscalaHoraExtra());
				}
				
				//textoMensagem += validaAfastamentoHorasExtras(servidores[i].getEscala().getEscalaHoraExtra());
				textoMensagem += validaHorasSemanais(servidores[i].getEscala().getHorasSemana1(), servidores[i].getEscala().getHorasSemana2(), servidores[i].getEscala().getHorasSemana3(), servidores[i].getEscala().getHorasSemana4(), servidores[i].getEscala().getHorasSemana5(), servidores[i].getEscala().getHorasSemana6(), servidores[i].getEscala().getHorasUltimaSemanaMesAnterior(), servidores[i].getCargaHoraria(), calculoHoras.getUltimaSemana(), servidores[i].getEscala().getMes(), servidores[i].getEscala().getAno());
				textoMensagem += validaHorasConsecutivas(servidores[i].getEscala().getEscalaHoraContratual(), servidores[i].getEscala().getEscalaHoraExtra());
				textoMensagem += validaChoqueHorario(servidores[i].getEscala().getEscalaHoraContratual(), servidores[i].getEscala().getEscalaHoraExtra());;
				textoMensagem += validaObservacao(servidores[i].getEscala().getEscalaHoraContratual(), servidores[i].getEscala().getObservacao());
				textoMensagem += "$A Ultima semana faltam " + calculaFaltaUltimaSemana(servidores[i].getEscala().getHorasUltimaSemana(), servidores[i].getCargaHoraria()) + " horas: " + AuxiliarConstantes.getDiaSemanaData(calculoHoras.getUltimoDiaMes(), servidores[i].getEscala().getMes(), servidores[i].getEscala().getAno()).toUpperCase() + ".$";
	
				if (escalaOK) {
					servidores[i].getEscala().setEscalaLancada(escalaOK);
				}
				
				mensagem = new EntidadeMensagem(textoMensagem, escalaOK);
				relatoriosErro.put(servidores[i].getMatricula(), mensagem);
				escalaOK = true;
				textoMensagem = "";
			}
			
			mensagem = new EntidadeMensagem(textoMensagem, escalaOK);
		
	
		
		return mensagem;
	}
	

	public EntidadeMensagem conferirEscala(EntidadeServidor servidor, boolean contemExtra) {
	
		ProcessCalculo calculoHoras = new ProcessCalculo();
		
		textoMensagem = "";
			
		calculoHoras.calcular(servidor);
			
		textoMensagem += validaErroDigitacao(servidor.getEscala().getEscalaHoraContratual(), servidor.getEscala().getEscalaHoraExtra());
	
		//textoMensagem = validaCompensacao(servidor.getEscala().getCompensacao());
		
		textoMensagem += servidor.getNome() + "$";
		
		textoMensagem += validaAfastamentoHorasExtras(servidor.getEscala().getEscalaHoraExtra());

		textoMensagem += validaAfastamentoHorasExtras(servidor.getEscala().getEscalaHoraContratual(), servidor.getEscala().getEscalaHoraExtra());
	
		if (contemExtra) {
			textoMensagem += validaHorasExtras(servidor.getEscala().getTotalHorasExtras(), calculoHoras.isExtraNoturno());
		}
		else {
			textoMensagem += validaHorasExtras(servidor.getEscala().getEscalaHoraExtra());

		}
			
		textoMensagem += validaHorasSemanais(servidor.getEscala().getHorasSemana1(), servidor.getEscala().getHorasSemana2(), servidor.getEscala().getHorasSemana3(), servidor.getEscala().getHorasSemana4(), servidor.getEscala().getHorasSemana5(), servidor.getEscala().getHorasSemana6(), servidor.getEscala().getHorasUltimaSemanaMesAnterior(), servidor.getCargaHoraria(), calculoHoras.getUltimaSemana(), servidor.getEscala().getMes(), servidor.getEscala().getAno());
		textoMensagem += validaHorasConsecutivas(servidor.getEscala().getEscalaHoraContratual(), servidor.getEscala().getEscalaHoraExtra());
		textoMensagem += validaChoqueHorario(servidor.getEscala().getEscalaHoraContratual(), servidor.getEscala().getEscalaHoraExtra());
		textoMensagem += validaErroDigitacao(servidor.getEscala().getEscalaHoraContratual(), servidor.getEscala().getEscalaHoraExtra());
		textoMensagem += validaObservacao(servidor.getEscala().getEscalaHoraContratual(), servidor.getEscala().getObservacao());
		textoMensagem += "$$$$A Ultima semana faltam " + calculaFaltaUltimaSemana(servidor.getEscala().getHorasUltimaSemana(), servidor.getCargaHoraria()) + " horas e o mes termina numa " + AuxiliarConstantes.getDiaSemanaData(calculoHoras.getUltimoDiaMes(), servidor.getEscala().getMes(), servidor.getEscala().getAno()).toUpperCase() + ".$";
		if (escalaOK) {
			servidor.getEscala().setEscalaLancada(escalaOK);
		}
			
		EntidadeMensagem mensagem = new EntidadeMensagem(textoMensagem, escalaOK);
		relatoriosErro.put(servidor.getMatricula(), mensagem);
		escalaOK = true;
		textoMensagem = "";
		
		return mensagem;
		
	}
	
	private String validaObservacao(String escalaContratual[], String observacao) {
		
		String mensagem = "";
		
		aqui: for (int i = 0; i < escalaContratual.length; ++i) {
		
			EntidadeLegenda legenda = AuxiliarConstantes.getLegenda(escalaContratual[i]);
			if(legenda != null) {
				if (
					legenda.isAfastamento()) {
				
						if (observacao.isEmpty() | observacao == null) {
							//mensagem += "$dia: " + (i + 1) + " " + legenda.getArea() + "; ";
							mensagem += "$E preciso anotar estes dias no campo observacao referente a " + legenda.getArea() + " com inicio no dia " + (i+1) +".$";
							escalaOK = false;
							this.numErros += 1;
							break aqui;
						}
						
					
				}
			}
		}
		
		if (mensagem.isEmpty())
			mensagem += "";
			
		return mensagem;
		
		
	}
	
	private String validaHorasSemanais(float horasSemana1, float horasSemana2, float horasSemana3, float horasSemana4, float horasSemana5, float horasSemana6, float passagemHorasProximoMes, int cargaHoraria, int ultimaSemana, int mes, int ano) {
		
		String mensagem = "";
		int maximo = 0;
		int minimo = 0;
		
		if (cargaHoraria == AuxiliarConstantes.CARGA_20) {
			maximo = AuxiliarConstantes.MAXIMO_CARGA_SEMANAL_20;
			minimo = AuxiliarConstantes.MINIMO_CARGA_SEMANAL_20;
			
		}
		else
		if (cargaHoraria == AuxiliarConstantes.CARGA_24) {
			maximo = AuxiliarConstantes.MAXIMO_CARGA_SEMANAL_24;
			minimo = AuxiliarConstantes.MINIMO_CARGA_SEMANAL_24;
		}
		else
		if (cargaHoraria == AuxiliarConstantes.CARGA_30) {
			maximo = AuxiliarConstantes.MAXIMO_CARGA_SEMANAL_30;
			minimo = AuxiliarConstantes.MINIMO_CARGA_SEMANAL_30;
		}
		else
		if (cargaHoraria == AuxiliarConstantes.CARGA_40) {
			maximo = AuxiliarConstantes.MAXIMO_CARGA_SEMANAL_40;
			minimo = AuxiliarConstantes.MINIMO_CARGA_SEMANAL_40;
		}
		else {
			minimo = 0;
			maximo = AuxiliarConstantes.MAXIMO_CARGA_SEMANAL_40;
		}
		
		//if (AuxiliarConstantes.getDiaSemanaData(1, mes, ano).equals(AuxiliarConstantes.DOMINGO)) {
			if (horasSemana1 > maximo) {
				mensagem += "$A semana 1 de " + horasSemana1 + " ultrapassa o limite de " + maximo + " horas semanais na carga horaria e preciso diminuir horas nesta semana.$";
				escalaOK = false;
				this.numErros += 1;
			}
		 
			if (horasSemana1 < minimo) {
				mensagem += "$A semana 1 de " + horasSemana1 + " esta menor do que o minimo de " + minimo + " horas semanais na carga horaria  preciso aumentar horas nesta semana.$";
				escalaOK = false;
				this.numErros += 1;
			}
		//}
		
		if (horasSemana2 > maximo) {
			mensagem += "$A semana 2 de " + horasSemana2 + " ultrapassa o limite de " + maximo + " horas semanais na carga horaria e preciso diminuir horas nesta semana.$";
			escalaOK = false;
			this.numErros += 1;
		}
	
		if (horasSemana2 < minimo) {
			mensagem += "$A semana 2 de " + horasSemana2 + " esta menor do que o minimo de " + minimo + " horas semanais na carga horaria e preciso aumentar horas nesta semana.$";
			escalaOK = false;
			this.numErros += 1;
		}
		
		if (horasSemana3 > maximo) {
			mensagem += "$A semana 3 de " + horasSemana3 + " ultrapassa o limite de " + maximo + " horas semanais na carga horaria e preciso diminuir horas nesta semana.$";
			escalaOK = false;
			this.numErros  += 1;
		}
		else 
		if (horasSemana3 < minimo) {
			mensagem += "$A semana 3 de " + horasSemana3 + " esta menor do que o minimo de " + minimo + " horas semanais na carga horaria e preciso aumentar horas nesta semana.$";
			escalaOK = false;
			this.numErros += 1;
		}
		
		if (horasSemana4 > maximo) {
			mensagem += "$A semana 4 de " + horasSemana4 + "  ultrapassa o limite de " + maximo + " horas semanais na carga horaria e preciso diminuir horas nesta semana.$";
			escalaOK = false;
			this.numErros += 1;
		}
	 
		if (horasSemana4 < minimo & ultimaSemana > 4) {
			mensagem += "$A semana 4 de " + horasSemana4 + " esta menor do que o minimo de " + minimo + " horas semanais na carga horaria e preciso aumentar horas nesta semana.$";
			escalaOK = false;
			this.numErros += 1;
		}
		
		if (horasSemana5 > maximo) {
			mensagem += "$A semana 5 de " + horasSemana5 + "  ultrapassa o limite de " + maximo + " horas semanais na carga horaria e preciso diminuir horas nesta semana.$";
			escalaOK = false;
			this.numErros += 1;
		}
		
		if (horasSemana5 < minimo & ultimaSemana > 5) {
			mensagem += "$A semana 5 de " + horasSemana5 + " esta menor do que o minimo de " + minimo + " horas semanais na carga horaria e preciso aumentar horas nesta semana.$";
			escalaOK = false;
			this.numErros += 1;
		}
		
		if (horasSemana6 > maximo) {
			mensagem += "$A semana 6 de " + horasSemana6 + "  ultrapassa o limite de " + maximo + " horas semanais na carga horaria e preciso diminuir horas nesta semana.$";
			escalaOK = false;
			this.numErros += 1;
		}
		
		/*if (passagemHorasProximoMes > maximo) {
			mensagem += "$A passagem de horas para o proximo mes de " + passagemHorasProximoMes + "  ultrapassa o limite de " + maximo + " horas semanais na carga horaria e preciso diminuir horas na ultima semana.$";
			escalaOK = false;
			this.numErros += 1;
		}*/
		
		if (mensagem.isEmpty())
			mensagem += "";
		
		return mensagem;
	}
	

	private String validaHorasExtras(int totalHorasExtras, boolean extrasNoturno) {
		
		String mensagem = "";
	
		if (extrasNoturno) {
			if (totalHorasExtras > AuxiliarConstantes.MAXIMO_HORAS_EXTRAS_NOTURNO) {
				mensagem += "$Ultrapassa o limite de " + AuxiliarConstantes.MAXIMO_HORAS_EXTRAS_NOTURNO + " e preciso diminuir a quantidade de horas extras.$";
				this.numErros += 1;
				escalaOK = false;
			}
		}
		else {
			if (totalHorasExtras > AuxiliarConstantes.MAXIMO_HORAS_EXTRAS) {
				mensagem += "$Ultrapassa o limite de " + AuxiliarConstantes.MAXIMO_HORAS_EXTRAS + " e preciso diminuir a quantidade de horas extras.$";
				this.numErros += 1;
				escalaOK = false;
			}
		}
		
		if (mensagem.isEmpty())
			mensagem += "";
		
		return mensagem;
	}
	
	private String validaAfastamentoHorasExtras(String escalaHoraExtra[]) {
		
		String mensagem = "";
		
		for (int i = 0; i < escalaHoraExtra.length; ++i) {
			
			if (AuxiliarConstantes.getLegenda(escalaHoraExtra[i]).isAfastamento()) {
				
				mensagem+=" " + i + ", ";
			}
		}
		
		if (!mensagem.isEmpty()) {
			mensagem ="$Existe legenda de referente a afastamento nos dias"+ mensagem +" isso n�o � permitido.$";
			escalaOK = false;
		}
		
		return mensagem;
		
	}
	
	private String validaHorasExtras(String escalaHoraExtra[]) {
		
		String mensagem = "";
		
		for (int i = 0; i < escalaHoraExtra.length; ++i) {
			
			if (!escalaHoraExtra[i].equals("")) {
				
				mensagem +="$Voce nao marcou que a escala possui hora extra, mas, existe uma legenda digitada no dia extra " + (i + 1) + ".$";
				escalaOK = false;
			}
		}
		
		if (mensagem.isEmpty())
			mensagem += "";
		
		return mensagem;
		
	}
	
	private String validaCompensacao(float compensacao) {
		
		String mensagem = "";
		
		
		
		if (compensacao > AuxiliarConstantes.MAXIMA_COMPENSACAO) {
			
			mensagem += "$Estacompensando mais do que o limite de " + AuxiliarConstantes.MAXIMA_COMPENSACAO + ". e preciso diminuir horas.$";
			this.numErros += 1;
			escalaOK = false;
		}
		else
		if (compensacao < AuxiliarConstantes.MINIMA_COMPENSACAO) {
			
			mensagem += "$Esta compensando menos do que o limite de " + AuxiliarConstantes.MINIMA_COMPENSACAO + ". e preciso aumentar horas.$";
			this.numErros += 1;
			escalaOK = false;
		}
		
		if (mensagem.isEmpty())
			mensagem += "";
		
		return mensagem;
	}
	
	public String validaHorasConsecutivas(String escalaHoraContratual[], String escalaHoraExtra[]) {
		
		EntidadeLegenda legendasEscalaHoraContratual[] = new EntidadeLegenda[escalaHoraContratual.length];
		EntidadeLegenda legendasEscalaHoraExtra[] = new EntidadeLegenda[escalaHoraExtra.length];
		String mensagem = "";
		
		for (int i = 0; i < escalaHoraContratual.length; ++i) {
			
			legendasEscalaHoraContratual[i] = AuxiliarConstantes.getLegenda(escalaHoraContratual[i]);
			legendasEscalaHoraExtra[i] = AuxiliarConstantes.getLegenda(escalaHoraExtra[i]);
		}
		
		
		for (int i = 0; i < escalaHoraContratual.length; ++i) {
			
			if ((i < escalaHoraContratual.length - 1) & ((legendasEscalaHoraContratual[i] != null)) & (legendasEscalaHoraExtra[i] != null)) {
				
				//mesmo dia
				/*if ((legendasEscalaHoraContratual[i].getValorManha() == 6 & legendasEscalaHoraContratual[i].getValorTarde() == 6) & (legendasEscalaHoraExtra[i].getValorNoite() >= 1)) {
					mensagem += "$Superior a 12 horas consecutivas com o dia " + (i + 1) + " contratual de " + escalaHoraContratual[i] + " e o dia " + (i + 1) + " extra de " + escalaHoraExtra[i] + ".$";
					this.numErros += 1;
					escalaOK = false;
				}
				
				if ((legendasEscalaHoraExtra[i].getValorManha() == 6 & legendasEscalaHoraExtra[i].getValorTarde() == 6) & (legendasEscalaHoraContratual[i].getValorNoite() >= 1)) {
					mensagem += "$Superior a 12 horas consecutivas com o dia " + (i + 1) + " extra de " + escalaHoraExtra[i] + " e o dia " + (i + 1) + " contratual de " + escalaHoraContratual[i] + ".$";
					this.numErros += 1;
					escalaOK = false;
				}
				
				if ((legendasEscalaHoraExtra[i].getValorTarde() == 6 & legendasEscalaHoraExtra[i].getValorNoite() >= 6) & (legendasEscalaHoraContratual[i].getValorManha() >= 1)) {
					mensagem += "$Superior a 12 horas consecutivas com o dia " + (i + 1) + " extra de " + escalaHoraExtra[i] + " e o dia " + (i + 1) + " contratual de " + escalaHoraContratual[i] + ".$";
					this.numErros += 1;
					escalaOK = false;
				}
				
				
				if ((legendasEscalaHoraContratual[i].getValorTarde() == 6 & legendasEscalaHoraContratual[i].getValorNoite() >= 6) & (legendasEscalaHoraExtra[i].getValorManha() >= 1)) {
					mensagem += "$Superior a 12 horas consecutivas com o dia " + (i + 1) + " contratual de " + escalaHoraContratual[i] + " e o dia " + (i + 1) + " extra de " + escalaHoraExtra[i] + ".$";
					this.numErros += 1;
					escalaOK = false;
				}
				
				if (legendasEscalaHoraContratual[i].getValorManha() == 6 & legendasEscalaHoraContratual[i].getValorTarde() == 6 & legendasEscalaHoraContratual[i].getValorNoite() >= 1) {
					mensagem += "$Superior a 12 horas consecutivas com o dia " + (i + 1) + " contratual de " + escalaHoraContratual[i] + ".$";
					this.numErros += 1;
					escalaOK = false;
				}
				
				if (legendasEscalaHoraExtra[i].getValorManha() == 6 & legendasEscalaHoraExtra[i].getValorTarde() == 6 & legendasEscalaHoraExtra[i].getValorNoite() >= 1) {
					mensagem += "$Superior a 12 horas consecutivas com o dia " + (i + 1) + " extra de " + escalaHoraExtra[i] + ".$";
					this.numErros += 1;
					escalaOK = false;
				}
				
				if (legendasEscalaHoraContratual[i].getValorTarde() == 6 & legendasEscalaHoraExtra[i].getValorNoite() == 12) {
					mensagem += "$Superior a 12 horas consecutivas com o dia " + (i + 1) + " contratual de " + escalaHoraContratual[i] + " e o dia " + (i + 1) + " extra de " + escalaHoraExtra[i] + ".$";
					this.numErros += 1;
					escalaOK = false;
				}
				
				if (legendasEscalaHoraExtra[i].getValorTarde() == 6 & legendasEscalaHoraContratual[i].getValorNoite() == 12) {
					mensagem += "$Superior a 12 horas consecutivas com o dia " + (i + 1) + " extra de " + escalaHoraExtra[i] + " e o dia " + (i + 1) + " contratual de " + escalaHoraContratual[i] + ".$";
					this.numErros += 1;
					escalaOK = false;
				}
				
				if (legendasEscalaHoraContratual[i].getValorTarde() == 6 & legendasEscalaHoraContratual[i].getValorNoite() == 12) {
					mensagem += "$Superior a 12 horas consecutivas com o dia " + (i + 1) + " contratual de " + escalaHoraContratual[i] + ".$";
					this.numErros += 1;
					escalaOK = false;
				}
				
				if (legendasEscalaHoraExtra[i].getValorTarde() == 6 & legendasEscalaHoraExtra[i].getValorNoite() == 12) {
					mensagem += "$Superior a 12 horas consecutivas com o dia " + (i + 1) + " extra de " + escalaHoraExtra[i] + ".$";
					this.numErros += 1;
					escalaOK = false;
				}*/
				
				//6 horas consecutivas anteriores
				if (legendasEscalaHoraExtra[i].getValorTarde() >= 1 & legendasEscalaHoraExtra[i].getValorNoite() == 12) {
					mensagem += "$Dia " + (i + 1) + " extra de " + escalaHoraExtra[i] + ".E preciso seis horas de intervalo antes ou depois de um plantao de 12 horas.$";
					this.numErros += 1;
					escalaOK = false;
				}
				
				if (legendasEscalaHoraContratual[i].getValorTarde() >= 1 & legendasEscalaHoraContratual[i].getValorNoite() == 12) {
					mensagem += "$Dia " + (i + 1) + " contratual de " + escalaHoraContratual[i] + ". E preciso seis horas de intervalo antes ou depois de um plantao de 12 horas.$";
					this.numErros += 1;
					escalaOK = false;
				}
				
				/*if (legendasEscalaHoraContratual[i].getValorTarde() >= 1 & legendasEscalaHoraExtra[i].getValorNoite() == 12) {
					mensagem += "$Dia " + (i + 1) + " contratual de " + escalaHoraContratual[i] + " e extra de " +  escalaHoraExtra[i] + ". E preciso seis horas de intervalo antes ou depois de um plantao de 12 horas.$";
					this.numErros += 1;
					escalaOK = false;
				}
				
				if (legendasEscalaHoraExtra[i].getValorTarde() >= 1 & legendasEscalaHoraContratual[i].getValorNoite() == 12) {
					mensagem += "$Dia " + (i + 1) + " contratual de " + escalaHoraContratual[i] + " e extra de " +  escalaHoraExtra[i] + ". E preciso seis horas de intervalo antes ou depois de um plantao de 12 horas.$";
					this.numErros += 1;
					escalaOK = false;
				}*/
				
				//dias diferentes superior a 12 consecutivas
				/*if (legendasEscalaHoraContratual[i].getValorNoite() == 12 & (legendasEscalaHoraExtra[i+1].getValorManha() >= 1 && legendasEscalaHoraExtra[i+1].isComecaSete())) {
					mensagem += "$Superior a 12 horas consecutivas com o dia " + (i + 1) + " contratual de " + escalaHoraExtra[i] + " e o dia " + (i + 2) + " extra de " + escalaHoraExtra[i+1] + ".$";
					this.numErros += 1;
					escalaOK = false;
				}
				
				if (legendasEscalaHoraExtra[i].getValorNoite() == 12 & (legendasEscalaHoraContratual[i+1].getValorManha() >= 1 && legendasEscalaHoraContratual[i+1].isComecaSete())) {
					mensagem += "$Superior a 12 horas consecutivas com o dia " + (i + 1) + " extra de " + escalaHoraExtra[i] + " e o dia " + (i + 2) + " contratual de " + escalaHoraContratual[i+1] + ".$";
					this.numErros += 1;
					escalaOK = false;
				}
				
				if (legendasEscalaHoraExtra[i].getValorNoite() == 12 & (legendasEscalaHoraExtra[i+1].getValorManha() >= 1 && legendasEscalaHoraExtra[i+1].isComecaSete())) {
					mensagem += "$Superior a 12 horas consecutivas com o dia " + (i + 1) + " extra de " + escalaHoraExtra[i] + " e o dia " + (i + 2) + " extra de " + escalaHoraExtra[i+1] + ".$";
					this.numErros += 1;
					escalaOK = false;
				}
				
				if (legendasEscalaHoraContratual[i].getValorNoite() == 12 & (legendasEscalaHoraContratual[i+1].getValorManha() >= 1 && legendasEscalaHoraContratual[i+1].isComecaSete())) {
					mensagem += "$Superior a 12 horas consecutivas com o dia " + (i + 1) + " contratual de " + escalaHoraContratual[i] + " e o dia " + (i + 2) + " contratual de " + escalaHoraContratual[i+1] + ".$";
					this.numErros += 1;
					escalaOK = false;
				}*/
				
				//dias diferentes 6 horas de intervalo
				if (legendasEscalaHoraContratual[i].getValorNoite() == 12 & (legendasEscalaHoraExtra[i+1].getValorManha() >= 1)) {
					mensagem += "$Dia " + (i + 1) + " contratual de " + escalaHoraContratual[i] + " e o dia " + (i + 2) + " extra de " + escalaHoraExtra[i+1] + ". E preciso seis horas de intervalo depois de um plantao de 12 horas$";
					this.numErros += 1;
					escalaOK = false;
				}
				
				if (legendasEscalaHoraExtra[i].getValorNoite() == 12 & (legendasEscalaHoraContratual[i+1].getValorManha() >= 1 /*&& legendasEscalaHoraContratual[i+1].isComecaSete()*/)) {
					mensagem += "$Dia " + (i + 1) + " extra de " + escalaHoraExtra[i] + " e o dia " + (i + 2) + " contratual de " + escalaHoraContratual[i+1] + ". E preciso seis horas de intervalo antes ou depois de um plantao de 12 horas.$";
					this.numErros += 1;
					escalaOK = false;
				}
				
				if (legendasEscalaHoraExtra[i].getValorNoite() == 12 & (legendasEscalaHoraExtra[i+1].getValorManha() >= 1 /*&& legendasEscalaHoraExtra[i+1].isComecaSete()*/)) {
					mensagem += "$Dia " + (i + 1) + " extra de " + escalaHoraExtra[i] + " e o dia " + (i + 2) + " extra de " + escalaHoraExtra[i+1] + ". E preciso seis horas de intervalo antes ou depois de um plantao de 12 horas.$";
					this.numErros += 1;
					escalaOK = false;
				}
				
				if (legendasEscalaHoraContratual[i].getValorNoite() == 12 & (legendasEscalaHoraContratual[i+1].getValorManha() >= 1 /*&& legendasEscalaHoraContratual[i+1].isComecaSete()*/)) {
					mensagem += "$Dia " + (i + 1) + " contratual de " + escalaHoraContratual[i] + " e o dia " + (i + 2) + " contratual de " + escalaHoraContratual[i+1] + ". E preciso seis horas de intervalo antes ou depois de um plantao de 12 horas.$";
					this.numErros += 1;
					escalaOK = false;
				}
				
				//mesmo dia 6 e 12 consecutivas
				if ((legendasEscalaHoraContratual[i].getValorManha() == 6) & 
					(legendasEscalaHoraContratual[i].getDescricao().indexOf("13:00 as 18:00") != -1 | legendasEscalaHoraContratual[i].getDescricao().indexOf("13:00 as 17:00") != -1)
					) {
					mensagem += "$Dia " + (i + 1) + " contratual de " + escalaHoraContratual[i] + ". Somente e permitido fazer 12 ou 6 horas consecutivas.";
					this.numErros += 1;
					escalaOK = false;
				}
				
				if (legendasEscalaHoraContratual[i].getValorManha() == 6 
					& ((legendasEscalaHoraExtra[i].getDescricao().indexOf("13:00 as 18:00") != -1) | (legendasEscalaHoraExtra[i].getDescricao().indexOf("13:00 as 17:00") != -1))) {
					mensagem += "$Dia " + (i + 1) + " contratual de " + escalaHoraContratual[i] + " " + escalaHoraExtra[i] + ". Somente e permitido fazer 12 ou 6 horas consecutivas.";
					this.numErros += 1;
					escalaOK = false;
				}
				
				if (legendasEscalaHoraExtra[i].getValorManha() == 6 
					& (legendasEscalaHoraContratual[i].getDescricao().indexOf("13:00 as 18:00") != -1 | legendasEscalaHoraContratual[i].getDescricao().indexOf("13:00 as 17:00") != -1)) {
					mensagem += "$Dia " + (i + 1) + " contratual de " + escalaHoraExtra + " " + escalaHoraContratual[i] + ". Somente e permitido fazer 12 ou 6 horas consecutivas.";
					this.numErros += 1;
					escalaOK = false;
				}
				
				if (legendasEscalaHoraExtra[i].getValorManha() == 6 & (legendasEscalaHoraExtra[i].getDescricao().indexOf("13:00 as 18:00") != -1 | legendasEscalaHoraExtra[i].getDescricao().indexOf("13:00 as 17:00") != -1)) {
					mensagem += "$Dia " + (i + 1) + " contratual de " + escalaHoraExtra + " " + escalaHoraContratual[i] + ". Somente e permitido fazer 12 ou 6 horas consecutivas.";
					this.numErros += 1;
					escalaOK = false;
				}
				
			}
			
		}
		
		if (mensagem.isEmpty())
			mensagem = ""; 
		
		return mensagem;
		
	}
	
	
	private String validaErroDigitacao(String escalaHoraContratual[], String escalaHoraExtra[]) {
		
		String mensagem = "";
		
		for (int i = 0; i < escalaHoraContratual.length; ++i) {
			/*
			if ((AuxiliarConstantes.getLegenda(escalaHoraContratual[i]).getLegenda()).equals(AuxiliarConstantes.NENHUMA_LEGENDA)) {
				mensagem += "$Verifique o dia " + (i + 1) + " contratual com " + (escalaHoraContratual[i] == "" ? "\"BRANCO\"" : escalaHoraContratual[i]) + " pois esta legenda digitada n�o computa.$";
				this.numErros += 1;
				escalaOK = false;
			}*/
			
			if (escalaHoraExtra[i] == null) {
				escalaHoraExtra[i] = "";
			}
			if (escalaHoraContratual[i] == null) {
				escalaHoraContratual[i] = "";
			}
			
			if ((AuxiliarConstantes.getLegenda(escalaHoraExtra[i]).getLegenda()).equals(AuxiliarConstantes.NENHUMA_LEGENDA) &&  !escalaHoraExtra[i].equals(AuxiliarConstantes.NENHUMA_LEGENDA)) {
				mensagem += "$Verifique o dia " + (i + 1) + " extra com " + (escalaHoraExtra[i]) + " pois esta legenda digitada n�o computa.$";
				this.numErros += 1;
				escalaOK = false;
			}
			
			if ((AuxiliarConstantes.getLegenda(escalaHoraContratual[i]).getLegenda()).equals(AuxiliarConstantes.NENHUMA_LEGENDA) &&  !escalaHoraContratual[i].equals(AuxiliarConstantes.NENHUMA_LEGENDA)) {
				mensagem += "$Verifique o dia " + (i + 1) + " contratual com " + (escalaHoraContratual[i]) + " pois esta legenda digitada n�o computa.$";
				this.numErros += 1;
				escalaOK = false;
			}
			
		}
		
		if (mensagem.isEmpty())
			mensagem = ""; 
		
		return mensagem;
		
	}
	
	public String validaChoqueHorario(String escalaHoraContratual[], String escalaHoraExtra[]) {
		
		String mensagem = "";
		
		for (int i = 0; i < escalaHoraContratual.length; ++i) {
			
			if (
				(AuxiliarConstantes.getLegenda(escalaHoraContratual[i]).getValorManha() != 0 
				& AuxiliarConstantes.getLegenda(escalaHoraExtra[i]).getValorManha() != 0) 
				|(AuxiliarConstantes.getLegenda(escalaHoraContratual[i]).getValorTarde() != 0 
				& AuxiliarConstantes.getLegenda(escalaHoraExtra[i]).getValorTarde() != 0)
				|(AuxiliarConstantes.getLegenda(escalaHoraContratual[i]).getValorNoite() != 0 
				& AuxiliarConstantes.getLegenda(escalaHoraExtra[i]).getValorNoite() != 0)
				) {
				
					mensagem += "$Houve choque de horario entre o dia " + (i + 1) + " de " + escalaHoraContratual[i] + " contratual com o dia " + (i + 1) + " de " + escalaHoraExtra[i] + " extra;$";
					this.numErros += 1;
					escalaOK = false;
				
			}
		}
		
		if (mensagem.isEmpty())
			mensagem = ""; 
		
		return mensagem;
		
	}
	
	public String validaAfastamentoHorasExtras(String escalaHoraContratual[], String escalaHoraExtra[]) {
		
		String mensagem = "";
		
		for (int i = 0; i < escalaHoraContratual.length; ++i) {
			
			EntidadeLegenda legendaC = AuxiliarConstantes.getLegenda(escalaHoraContratual[i]);
			EntidadeLegenda legendaE = AuxiliarConstantes.getLegenda(escalaHoraExtra[i]);
			
				if ((legendaC.isAfastamento()) & (legendaE.getValorTotal() > 0)) {
					
						mensagem += "$O servidor nao pode estar no dia contratual " + (i + 1) + " de " + escalaHoraContratual[i] + " e no dia extra " + (i + 1) + " de " + escalaHoraExtra[i] + ".$";
						this.numErros += 1;
						escalaOK = false;
					
				}
			
		}
		
		if (mensagem.isEmpty())
			mensagem = ""; 
		
		return mensagem;
		
	}
	
	public void conferirTudo(ProcessLeitorDadosPlanilha leitor, String arquivo, int opcao, int mes, int ano) throws RNException {
		
		boolean ok = false;
		File diretorio = new File(arquivo);
		int numArqs = 0;
		try {
			if (diretorio.isDirectory()) {
				String arquivos[] = diretorio.list();
				for (int i = 0; i < arquivos.length; ++i) {
					if ((arquivos[i].indexOf(".xls") != -1) || (arquivos[i].indexOf(".xml") != -1)){
						numArqs += 1;
							EntidadeServidor servidores[] = null;
							if (opcao == 0 & (arquivos[i].indexOf(".xml") != -1)) {
								servidores = leitor.getDadosPlanilhaEscalaXML(arquivo + File.separator + arquivos[i], mes, ano);
							}
							else
							if (opcao == 1 & (arquivos[i].indexOf(".xls") != -1)){
								servidores = leitor.getDadosPlanilhaEscala(arquivo + File.separator + arquivos[i], mes, ano);
							}
						
						conferirEscala(servidores, true);
						
						}
					}
					if (numArqs > 0)
						ok = true;
					else
						throw new RNException("Nao ha nenhum arquivo nesta pasta para conferir!");
				}
				else {
					throw new  RNException("Escolha um diretorio valido!");
	
				}
		}
		catch(RNException ex) { 
			throw new  RNException(ex.getMessage());
		}
	}
	
	public int getNumErros() {
		return numErros;
	}

	public void setNumErros(int escalaOK) {
		this.numErros = escalaOK;
	}
	
	public EntidadeMensagem getRelatorioErro(String matricula) {
		
		EntidadeMensagem mensagem = relatoriosErro.get(matricula);
		
		if (mensagem == null)
			mensagem = new EntidadeMensagem("Nao existe este nome nesta escala.", false);
		
		return mensagem;
		
	}
	
	public float calculaFaltaUltimaSemana(float horasUltimaSemana, float cargaHoraria) {
		
		
		float falta = 0;
		if ((horasUltimaSemana != 0) & (horasUltimaSemana < cargaHoraria)) {
			falta = cargaHoraria - horasUltimaSemana;
		}
		
		return falta;
		
	}

}
