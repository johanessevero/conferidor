package processamento;

import java.util.ArrayList;

import auxiliar.AuxiliarConstantes;
import entidade.EntidadeLegenda;
import entidade.EntidadeServidor;

/**
 * 
 * @author Johanes Severo dos Santos Classe que modela o calculo dos valores de
 *         informacoes necess�rias retirados de uma escala.
 */
public class ProcessCalculo {

	private boolean extraNoturno;

	private String diasSemana[];

	private float horasEscalaContratual[];
	private float horasEscalaExtra[];

	private int ultimoDiaMes;

	private int totalRefeicoes;
	private int totalHorasExtras;
	private int ultimaSemana;

	private float horasUltimaSemana;

	private EntidadeLegenda legendasContratual;
	private EntidadeLegenda legendasExtra;

	public void calcular(EntidadeServidor servidor) {

		Float horasSemana1 = new Float(0);
		Float horasSemana2 = new Float(0);
		Float horasSemana3 = new Float(0);
		Float horasSemana4 = new Float(0);
		Float horasSemana5 = new Float(0);
		Float horasSemana6 = new Float(0);
		Float bancoHoras = new Float(0);
		int totalHorasExtras = 0;

		ultimoDiaMes = AuxiliarConstantes.getUltimoDiaMes(servidor.getEscala()
				.getMes(), servidor.getEscala().getAno());
		horasEscalaContratual = new float[ultimoDiaMes];
		horasEscalaExtra = new float[ultimoDiaMes];
		diasSemana = new String[ultimoDiaMes];

		for (int i = 0; i < ultimoDiaMes; ++i) {

			horasEscalaContratual[i] = AuxiliarConstantes.getValorLegenda(
					servidor.getEscala().getEscalaHoraContratual()[i],
					servidor.getCargaHorariaSetor());
			horasEscalaExtra[i] = AuxiliarConstantes.getValorLegenda(servidor
					.getEscala().getEscalaHoraExtra()[i], servidor
					.getCargaHorariaSetor());
			diasSemana[i] = AuxiliarConstantes.getDiaSemanaData(i + 1, servidor
					.getEscala().getMes(), servidor.getEscala().getAno());

			legendasContratual = AuxiliarConstantes.getLegenda(servidor
					.getEscala().getEscalaHoraContratual()[i]);
			legendasExtra = AuxiliarConstantes.getLegenda(servidor.getEscala()
					.getEscalaHoraExtra()[i]);

			if (legendasContratual != null) {
				if ((diasSemana[i].equals(AuxiliarConstantes.SABADO) || diasSemana[i]
						.equals(AuxiliarConstantes.DOMINGO))) {
					if (legendasContratual.getPeriodo() == AuxiliarConstantes.AFASTAMENTO_) {
						horasEscalaContratual[i] = 0;
					}

					if (legendasExtra.getPeriodo() == AuxiliarConstantes.AFASTAMENTO_) {
						horasEscalaExtra[i] = 0;
					}

				}
			}
		}

		// total de horas semanais
		int contSabado = 0;
		int posSabado = 0;

		if (AuxiliarConstantes.getDiaSemanaData(1,
				servidor.getEscala().getMes(), servidor.getEscala().getAno())
				.equals(AuxiliarConstantes.DOMINGO)) {
			servidor.getEscala().setHorasUltimaSemanaMesAnterior(new Float(0));
		} else {
			horasSemana1 += servidor.getEscala()
					.getHorasUltimaSemanaMesAnterior();
		}

		if (diasSemana[0].equals(AuxiliarConstantes.SABADO)) {
			horasSemana1 += horasEscalaContratual[0];
		}

		if (!diasSemana[0].equals(AuxiliarConstantes.SABADO)) {
			for (posSabado = 0; posSabado < ultimoDiaMes; ++posSabado) {

				horasSemana1 += horasEscalaContratual[posSabado];

				if (diasSemana[posSabado].equals(AuxiliarConstantes.SABADO)) {
					contSabado += 1;
					break;
				}
			}
		}

		for (posSabado = posSabado + 1; posSabado < ultimoDiaMes; ++posSabado) {

			horasSemana2 += horasEscalaContratual[posSabado];

			if (diasSemana[posSabado].equals(AuxiliarConstantes.SABADO)) {
				contSabado += 1;
				break;
			}

		}

		for (posSabado = posSabado + 1; posSabado < ultimoDiaMes; ++posSabado) {

			horasSemana3 += horasEscalaContratual[posSabado];

			if (diasSemana[posSabado].equals(AuxiliarConstantes.SABADO)) {
				contSabado += 1;
				break;
			}

		}

		for (posSabado = posSabado + 1; posSabado < ultimoDiaMes; ++posSabado) {

			horasSemana4 += horasEscalaContratual[posSabado];

			if (diasSemana[posSabado].equals(AuxiliarConstantes.SABADO)) {
				contSabado += 1;
				break;
			}

		}

		for (posSabado = posSabado + 1; posSabado < ultimoDiaMes; ++posSabado) {

			horasSemana5 += horasEscalaContratual[posSabado];

			if (diasSemana[posSabado].equals(AuxiliarConstantes.SABADO)) {
				contSabado += 1;
				break;
			}

		}
		posSabado += 1;

		if (!(posSabado >= ultimoDiaMes - 1)) {
			for (int i = posSabado; i < ultimoDiaMes; ++i) {

				horasSemana6 += horasEscalaContratual[i];

				if (diasSemana[i].equals(AuxiliarConstantes.SABADO)) {

					break;
				}

			}
			contSabado += 1;
		}

		// compensacao

		float totalHorasSemanasCompletas = 0;
		int posUltimoSabado = 0;
		float horasUltimaSemana = 0;

		if (!diasSemana[ultimoDiaMes - 1].equals(AuxiliarConstantes.SABADO)) {
			for (int i = ultimoDiaMes - 1; i >= 0; --i) {

				if (diasSemana[i].equals(AuxiliarConstantes.SABADO)) {
					posUltimoSabado = i;
					break;
				}

				horasUltimaSemana += horasEscalaContratual[i];
			}
		} else {
			posUltimoSabado = ultimoDiaMes - 1;
		}

		if (diasSemana[ultimoDiaMes - 1].equals(AuxiliarConstantes.DOMINGO)) {

			horasUltimaSemana = horasEscalaContratual[ultimoDiaMes - 1];

		}

		for (int i = posUltimoSabado; i >= 0; --i) {

			totalHorasSemanasCompletas += horasEscalaContratual[i];

		}

		totalHorasSemanasCompletas += servidor.getEscala()
				.getHorasUltimaSemanaMesAnterior();

		float totalHorasIdeal = contSabado * servidor.getCargaHorariaSetor();
		bancoHoras = totalHorasSemanasCompletas - totalHorasIdeal;

		setHorasUltimaSemana(horasUltimaSemana);

		// passagem de horas
		if (horasSemana4 == horasUltimaSemana) {
			ultimaSemana = 4;
		} else if (horasSemana5 == horasUltimaSemana) {
			ultimaSemana = 5;
		} else if (horasSemana6 == horasUltimaSemana) {
			ultimaSemana = 6;
		}

		// total de horas extras
		extraNoturno = true;
		for (int l = 0; l < ultimoDiaMes; ++l) {

			EntidadeLegenda legenda = AuxiliarConstantes.getLegenda(servidor
					.getEscala().getEscalaHoraExtra()[l]);

			if (!legenda.isAfastamento()) {
				if ((legenda.getValorManha() != 0.0 | legenda.getValorTarde() != 0.0)
						& (horasEscalaExtra[l] != 0.0)) {
					extraNoturno = false;
					break;
				}
			}

		}

		int totalHorasExtrasNoturno = 0;
		int totalHorasExtrasDiurno = 0;
		for (int l = 0; l < ultimoDiaMes; ++l) {

			EntidadeLegenda legenda = AuxiliarConstantes.getLegenda(servidor
					.getEscala().getEscalaHoraExtra()[l]);

			if (!legenda.isAfastamento()) {
				if ((legenda.getValorManha() != 0 | legenda.getValorTarde() != 0)
						& legenda.getValorNoite() < 12.0)
					totalHorasExtras += horasEscalaExtra[l];

				if (legenda.getValorNoite() == 0) {
					totalHorasExtrasDiurno += horasEscalaExtra[l];
				} else {
					totalHorasExtrasDiurno += (legenda.getValorManha() + legenda
							.getValorTarde());
				}

				if ((legenda.getValorManha() == 0 & legenda.getValorTarde() == 0)
						& legenda.getValorNoite() < 12.0)
					totalHorasExtrasNoturno += horasEscalaExtra[l];

				if (legenda.getValorNoite() == 12.0) {
					totalHorasExtras += horasEscalaExtra[l] + 1;
					totalHorasExtrasNoturno += legenda.getValorNoite() - 4;
					totalHorasExtrasDiurno += 5;
				}
			}

		}

		// quantidade de refeicoes

		int quantidadeRefeicoesDiurno = 0;
		int quantidadeRefeicoesNoturno = 0;
		for (int l = 0; l < ultimoDiaMes; ++l) {

			EntidadeLegenda legendaContratual = AuxiliarConstantes
					.getLegenda(servidor.getEscala().getEscalaHoraContratual()[l]);
			EntidadeLegenda legendaExtra = AuxiliarConstantes
					.getLegenda(servidor.getEscala().getEscalaHoraExtra()[l]);

			if (!legendaContratual.isAfastamento()) {
				if (legendaContratual.getValorManha()
						+ legendaContratual.getValorTarde() == 12) {
					quantidadeRefeicoesDiurno += 1;
				}

				if (legendaContratual.getPeriodo() == AuxiliarConstantes.NOITE
						&& legendaContratual.getValorTotal() == 12) {
					quantidadeRefeicoesNoturno += 1;
				}

			}

			if (!legendaExtra.isAfastamento()) {

				if (legendaExtra.getValorManha() + legendaExtra.getValorTarde() == 12) {
					quantidadeRefeicoesDiurno += 1;
				}

				if (legendaExtra.getPeriodo() == AuxiliarConstantes.NOITE
						&& legendaExtra.getValorTotal() == 12) {
					quantidadeRefeicoesNoturno += 1;
				}
			}

		}

		servidor.getEscala().setBancoHorasDestaEscala(bancoHoras);
		servidor.getEscala().setQuantidadeRefeicoesDiurno(
				quantidadeRefeicoesDiurno);
		servidor.getEscala().setQuantidadeRefeicoesNoturno(
				quantidadeRefeicoesNoturno);
		servidor.getEscala().setTotalHorasExtras(totalHorasExtras);
		servidor.getEscala().setHorasSemana1(horasSemana1);
		servidor.getEscala().setHorasSemana2(horasSemana2);
		servidor.getEscala().setHorasSemana3(horasSemana3);
		servidor.getEscala().setHorasSemana4(horasSemana4);
		servidor.getEscala().setHorasSemana5(horasSemana5);
		servidor.getEscala().setHorasSemana6(horasSemana6);
		servidor.getEscala().setHorasUltimaSemana(horasUltimaSemana);
		servidor.getEscala().setTotalHorasExtrasDiurno(totalHorasExtrasDiurno);
		servidor.getEscala()
				.setTotalHorasExtrasNoturno(totalHorasExtrasNoturno);
		servidor.getEscala().setContemHoraExtra(totalHorasExtras > 0);

	}

	public boolean isExtraNoturno() {
		return extraNoturno;
	}

	public void setExtraNoturno(boolean extraNoturno) {
		this.extraNoturno = extraNoturno;
	}

	public int getTotalRefeicoes(EntidadeServidor servidores[]) {

		if (servidores != null) {
			for (int i = 0; i < servidores.length; ++i) {

				totalRefeicoes += servidores[i].getEscala()
						.getQuantidadeRefeicoesDiurno();

			}
		}

		return totalRefeicoes;
	}

	public int getTotalHorasExtras(EntidadeServidor servidores[]) {

		if (servidores != null) {
			for (int i = 0; i < servidores.length; ++i) {

				totalHorasExtras += servidores[i].getEscala()
						.getTotalHorasExtras();

			}
		}

		return totalHorasExtras;
	}

	public int[][] getQuantitativo(EntidadeServidor servidores[], int mes,
			int ano) {

		int qmtn[][] = new int[3][AuxiliarConstantes.getUltimoDiaMes(mes, ano)];
		qmtn[0] = new int[AuxiliarConstantes.getUltimoDiaMes(mes, ano)];
		qmtn[1] = new int[AuxiliarConstantes.getUltimoDiaMes(mes, ano)];
		qmtn[2] = new int[AuxiliarConstantes.getUltimoDiaMes(mes, ano)];

		for (int i = 0; i < AuxiliarConstantes.getUltimoDiaMes(mes, ano); ++i) {
			for (int j = 0; j < servidores.length; ++j) {

				if (AuxiliarConstantes.getLegenda(
						servidores[j].getEscala().getEscalaHoraContratual()[i])
						.getValorManha() > 0
						| AuxiliarConstantes
								.getLegenda(
										servidores[j].getEscala()
												.getEscalaHoraExtra()[i])
								.getValorManha() > 0) {
					qmtn[0][i] += 1;
				}

			}
		}

		for (int i = 0; i < AuxiliarConstantes.getUltimoDiaMes(mes, ano); ++i) {
			for (int j = 0; j < servidores.length; ++j) {

				if (AuxiliarConstantes.getLegenda(
						servidores[j].getEscala().getEscalaHoraContratual()[i])
						.getValorTarde() > 0
						| AuxiliarConstantes
								.getLegenda(
										servidores[j].getEscala()
												.getEscalaHoraExtra()[i])
								.getValorTarde() > 0) {
					qmtn[1][i] += 1;
				}

			}
		}

		for (int i = 0; i < AuxiliarConstantes.getUltimoDiaMes(mes, ano); ++i) {
			for (int j = 0; j < servidores.length; ++j) {

				if (AuxiliarConstantes.getLegenda(
						servidores[j].getEscala().getEscalaHoraContratual()[i])
						.getValorNoite() > 0
						| AuxiliarConstantes
								.getLegenda(
										servidores[j].getEscala()
												.getEscalaHoraExtra()[i])
								.getValorNoite() > 0) {
					qmtn[2][i] += 1;
				}

			}
		}

		return qmtn;
	}

	public int getTotalHorasExtras(ArrayList<EntidadeServidor> servidores) {

		if (servidores != null) {
			for (int i = 0; i < servidores.size(); ++i) {

				totalHorasExtras += servidores.get(i).getEscala()
						.getTotalHorasExtras();

			}
		}

		return totalHorasExtras;
	}

	public int getUltimaSemana() {
		return ultimaSemana;
	}

	public void setUltimaSemana(int ultimaSemana) {
		this.ultimaSemana = ultimaSemana;
	}

	public float getHorasUltimaSemana() {
		return horasUltimaSemana;
	}

	public void setHorasUltimaSemana(float horasUltimaSemana) {
		this.horasUltimaSemana = horasUltimaSemana;
	}

	public int getUltimoDiaMes() {
		return ultimoDiaMes;
	}

	public void setUltimoDiaMes(int ultimoDiaMes) {
		this.ultimoDiaMes = ultimoDiaMes;
	}
}
