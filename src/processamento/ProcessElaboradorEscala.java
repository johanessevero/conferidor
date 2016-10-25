package processamento;

import java.util.ArrayList;

import auxiliar.AuxiliarConstantes;
import entidade.EntidadeEscala;
import entidade.EntidadeLegenda;
import entidade.EntidadeServidor;

public class ProcessElaboradorEscala {

	private ArrayList<EntidadeLegenda> listaLegendas;
	
	private EntidadeServidor servidoresProximoMes[];
	
	private ArrayList<EntidadeLegenda> getLegendasServidores(EntidadeServidor servidores[]) {
		
		ArrayList<EntidadeLegenda> listaLegendas = new ArrayList<EntidadeLegenda>();
		
		/*LISTA AS LEGENDAS USADAS PELO SETOR*/
		for (int i = 0; i < servidores.length; ++i) {
			
			for (int j = 0; j < servidores[i].getEscala().getEscalaHoraContratual().length; ++j) {
			
				EntidadeLegenda legenda = AuxiliarConstantes.getLegenda(servidores[i].getEscala().getEscalaHoraContratual()[j]); 
				if (!legenda.getLegenda().equals(AuxiliarConstantes.NENHUMA_LEGENDA) && !legenda.isAfastamento())
					listaLegendas.add(legenda);
				
			}
		}
		
		listaLegendas.trimToSize();
		
		for (int i = 0; i < listaLegendas.size(); ++i) {
			
				for (int j = i+1; j < listaLegendas.size(); ++j) {
		
					if (listaLegendas.get(i).getLegenda().equals(listaLegendas.get(j).getLegenda())) {
						listaLegendas.remove(j);
					}
				
				}
		}
		
		listaLegendas.trimToSize();
		
		return listaLegendas;
		
	}
	
	public String[] elaborarEscala(EntidadeServidor servidor) {
		
		int mes = servidor.getEscala().getMes();
		int ano = servidor.getEscala().getAno();
		int ultimoDiaMesAnterior = AuxiliarConstantes.getUltimoDiaMes(mes, ano);
		int ultimoDiaProximoMes = AuxiliarConstantes.getUltimoDiaMes((mes==12 ? 1 : mes+1), ano);
		
		String escalaElaborada[] = new String[ultimoDiaProximoMes];
		for (int i = 0; i < escalaElaborada.length; ++i)
			escalaElaborada[i] = "";
		
		/*
		 * SEMANAS COMPLETAS MES ANTERIOR
		 */
		String diasSemanaMesAnterior[] = new String[ultimoDiaMesAnterior];
		for (int i = 0; i < ultimoDiaMesAnterior; ++i) {
			diasSemanaMesAnterior[i] = AuxiliarConstantes.getDiaSemanaData(i+1, mes, ano);
		}
		
		int ultimoDiaSemanaCompletaMesAnterior = 0;
		aqui:for (int i = ultimoDiaMesAnterior-1; i >= 0; --i) {
			if (diasSemanaMesAnterior[i].equals(AuxiliarConstantes.DOMINGO)) {
				ultimoDiaSemanaCompletaMesAnterior = i;
				break aqui;
			}
		}
		
		int primeiroDiaSemanaCompletaMesAnterior = 0;
		aqui:for (int i = 0; i < ultimoDiaMesAnterior; ++i) {
			if (diasSemanaMesAnterior[i].equals(AuxiliarConstantes.DOMINGO)) {
				primeiroDiaSemanaCompletaMesAnterior = i;
				break aqui;
			}
		}
		
		ArrayList<String> stringsSemanas = new ArrayList<String>();
		String stringSemana = "";
		ArrayList<Integer> intervalosMesAnterior = new ArrayList<Integer>();
		int numSemanasCompletasMesAnterior = 0;
		for (int k = primeiroDiaSemanaCompletaMesAnterior; k < ultimoDiaSemanaCompletaMesAnterior; ++k) {
			
			stringSemana+=servidor.getEscala().getEscalaHoraContratual()[k];
			if (diasSemanaMesAnterior[k].equals(AuxiliarConstantes.DOMINGO)) {
				intervalosMesAnterior.add(k);
			}
			
			if (diasSemanaMesAnterior[k].equals(AuxiliarConstantes.SABADO)) {
				intervalosMesAnterior.add(k);
				numSemanasCompletasMesAnterior+=1;
				stringsSemanas.add(stringSemana);
			
				stringSemana = "";
			}
		}
		
		System.out.println();
		
		stringsSemanas.trimToSize();
		intervalosMesAnterior.trimToSize();
		
		/*
		 * SEMANAS COMPLETAS PROXIMO MES
		 */
		//teste
		System.out.println();
		System.out.println();
		System.out.println("proximo mes: " + ((mes==12 ? 1 : mes+1)));
		//teste
		
		String diasSemanaProximoMes[] = new String[ultimoDiaProximoMes];
		for (int i = 0; i < ultimoDiaProximoMes; ++i) {
			diasSemanaProximoMes[i] = AuxiliarConstantes.getDiaSemanaData(i+1, (mes==12 ? 1 : mes+1), ano);
		}
		
		int ultimoDiaSemanaCompletaProximoMes = 0;
		aqui:for (int i = ultimoDiaProximoMes-1; i >= 0; --i) {
			if (diasSemanaProximoMes[i].equals(AuxiliarConstantes.DOMINGO)) {
				ultimoDiaSemanaCompletaProximoMes = i;
				break aqui;
			}
		}
		
		int primeiroDiaSemanaCompletaProximoMes = 0;
		aqui:for (int i = 0; i < ultimoDiaProximoMes; ++i) {
			if (diasSemanaProximoMes[i].equals(AuxiliarConstantes.DOMINGO)) {
				primeiroDiaSemanaCompletaProximoMes = i;
				break aqui;
			}
		}
		
		ArrayList<Integer> intervalosProximoMes = new ArrayList<Integer>();
		int numSemanasCompletasProximoMes = 0;
		for (int k = primeiroDiaSemanaCompletaProximoMes; k < ultimoDiaSemanaCompletaProximoMes; ++k) {
			
			if (diasSemanaProximoMes[k].equals(AuxiliarConstantes.DOMINGO)) {
				intervalosProximoMes.add(k);
			}
			
			if (diasSemanaProximoMes[k].equals(AuxiliarConstantes.SABADO)) {
				intervalosProximoMes.add(k);
				numSemanasCompletasProximoMes+=1;
			}
		}
		
		intervalosProximoMes.trimToSize();
		
		if (numSemanasCompletasMesAnterior >= numSemanasCompletasProximoMes) {
			
			 int contSemanasProximoMes = 0;
			 int contDiasProximoMes = 0;
			 for (int i = 0; i < intervalosProximoMes.size(); i+=2) {
				 
				 contDiasProximoMes = intervalosProximoMes.get(contSemanasProximoMes);
				 for (int j = intervalosMesAnterior.get(i); j <= intervalosMesAnterior.get(i+1); ++j) {
					 escalaElaborada[contDiasProximoMes] = servidor.getEscala().getEscalaHoraContratual()[j];
					 contDiasProximoMes+=1;
				 }
				 contSemanasProximoMes += 2;
			 }
			
		}
		else
		if (numSemanasCompletasMesAnterior < numSemanasCompletasProximoMes) {
			
			 int contSemanasCompletas = 0;
			 int indiceSemanasProximoMes = 0;
			 int contDiasProximoMes = 0;
			 for (int i = 0; i < intervalosMesAnterior.size(); i+=2) {
				 
				 contDiasProximoMes = intervalosProximoMes.get(indiceSemanasProximoMes);
				 for (int j = intervalosMesAnterior.get(i); j <= intervalosMesAnterior.get(i+1); ++j) {
					 escalaElaborada[contDiasProximoMes] = servidor.getEscala().getEscalaHoraContratual()[j];
					 contDiasProximoMes+=1;
				 }
				 indiceSemanasProximoMes += 2;
				 contSemanasCompletas += 1;
			 }
			 
			 int indiceStringMaiorNumero = -1;
			 int cont = -1;
			 int contMenor = 0;
			 for (int i = 0; i < stringsSemanas.size(); ++i) {
				
				 for (int j = 0; j < stringsSemanas.size(); ++j) {
					 if (stringsSemanas.get(i).equals(stringsSemanas.get(j))) {
						 contMenor+=1;
					 }
				 }
				 
				 if (contMenor > cont) {
					 cont = contMenor;
					 indiceStringMaiorNumero+=1;
				 }
			 }
			 
			 int inicioIntervaloSemanaEscolhida = (indiceStringMaiorNumero*2);
			 int fimIntervaloSemanaEscolhida = inicioIntervaloSemanaEscolhida+1;
			 
			 
			 indiceSemanasProximoMes = (contSemanasCompletas*2);
			 for (int i = numSemanasCompletasMesAnterior; i < numSemanasCompletasProximoMes; i+=1) {
				 
				 contDiasProximoMes = intervalosProximoMes.get(indiceSemanasProximoMes);
				 for (int j = intervalosMesAnterior.get(inicioIntervaloSemanaEscolhida); j <= intervalosMesAnterior.get(fimIntervaloSemanaEscolhida); ++j) {
					 escalaElaborada[contDiasProximoMes] = servidor.getEscala().getEscalaHoraContratual()[j];
					 contDiasProximoMes+=1;
		
				 }
				 indiceSemanasProximoMes += 2;
			 }
		}
		
		/*** SEMANAS INCMPLETAS ****/
		ArrayList<String> diasSemanaIncompletaProximoMesInicio = new ArrayList<String>();
		ArrayList<Integer> intervaloSemanaIncompletaInicio = new ArrayList<Integer>();
		if (!diasSemanaProximoMes[0].equals(AuxiliarConstantes.DOMINGO))
			intervaloSemanaIncompletaInicio.add(0);
		aqui:for (int i = 0; i < diasSemanaProximoMes.length; ++i) {
			
			if (!diasSemanaProximoMes[i].equals(AuxiliarConstantes.DOMINGO)) {
				
				diasSemanaIncompletaProximoMesInicio.add(diasSemanaProximoMes[i]);
				
			}
			else {
				intervaloSemanaIncompletaInicio.add(i-1);
				break aqui;
			}
		}
		
		ArrayList<String> diasSemanaIncompletaProximoMesFim = new ArrayList<String>();
		ArrayList<Integer> intervaloSemanaIncompletaFim = new ArrayList<Integer>();
		aqui:for (int i = diasSemanaProximoMes.length-1; i >= 0; i--) {
			
			if (!diasSemanaProximoMes[i].equals(AuxiliarConstantes.SABADO)) {
				
				diasSemanaIncompletaProximoMesFim.add(diasSemanaProximoMes[i]);
				

			}
			else {
				if (i < ultimoDiaProximoMes-1)
					intervaloSemanaIncompletaFim.add(i+1);
				break aqui;
			}
		}
		if (!diasSemanaProximoMes[ultimoDiaProximoMes-1].equals(AuxiliarConstantes.SABADO))
			intervaloSemanaIncompletaFim.add(ultimoDiaProximoMes-1);
		
		ArrayList<String> diasSemanaIncompletaProximoMesFimAux = new ArrayList<String>();
		for (int i = diasSemanaIncompletaProximoMesFim.size()-1; i >= 0; --i) {
			
			diasSemanaIncompletaProximoMesFimAux.add(diasSemanaIncompletaProximoMesFim.get(i));
		}
		
		diasSemanaIncompletaProximoMesFim = null;
		diasSemanaIncompletaProximoMesFim = new ArrayList<String>();
		for (int i = 0; i < diasSemanaIncompletaProximoMesFimAux.size(); ++i) {
			
			diasSemanaIncompletaProximoMesFim.add(diasSemanaIncompletaProximoMesFimAux.get(i));
	
		}
		
		diasSemanaIncompletaProximoMesFim.trimToSize();
		diasSemanaIncompletaProximoMesInicio.trimToSize();
		intervaloSemanaIncompletaInicio.trimToSize();
		intervaloSemanaIncompletaFim.trimToSize();
		
		//teste
		System.out.println();
		System.out.println("Intervalo inicio");
		for (int i = 0; i < intervaloSemanaIncompletaInicio.size(); ++i) {
			
			System.out.print(intervaloSemanaIncompletaInicio.get(i) + " ");
			
		}
		System.out.println();
		System.out.println("Dias semanas incompletas inicio: ");
		for (int i = 0; i < diasSemanaIncompletaProximoMesInicio.size(); ++i) {
			
			System.out.print(diasSemanaIncompletaProximoMesInicio.get(i) + " ");
			
		}
		System.out.println();
		System.out.println("Intervalo fim");
		for (int i = 0; i < intervaloSemanaIncompletaFim.size(); ++i) {
			
			System.out.print(intervaloSemanaIncompletaFim.get(i) + " ");
			
		}	
		System.out.println();
		System.out.println("Dias semanas incompletas fim");
		for (int i = 0; i < diasSemanaIncompletaProximoMesFim.size(); ++i) {
			
			System.out.print(diasSemanaIncompletaProximoMesFim.get(i) + " ");
			
		}
		System.out.println();
		//teste
		
		ArrayList<String> stringsSemanasIncompletasInicio = new ArrayList<String>();
		int contAux = 0;
		int semanaInicio = 0;
		if (!(diasSemanaIncompletaProximoMesInicio.isEmpty())) {
				
			for (int i = 0; i < diasSemanaMesAnterior.length; ++i) {
		
				if (diasSemanaMesAnterior[i].equals(diasSemanaIncompletaProximoMesInicio.get(0))) {
					
					String stringIncompleta = "";
					contAux = i;
					for (int j = 0; j < diasSemanaIncompletaProximoMesInicio.size(); ++j) {
						
						if (contAux < ultimoDiaMesAnterior)
							stringIncompleta += servidor.getEscala().getEscalaHoraContratual()[contAux];
						contAux+=1;
						
					}
					
					if (stringIncompleta.isEmpty())
						stringsSemanasIncompletasInicio.add("-");
					else {
						stringsSemanasIncompletasInicio.add(stringIncompleta);
					}
					i = contAux;
					stringIncompleta = "";
					
				}
				
			}
		}
		
		ArrayList<String> stringsSemanasIncompletasFim = new ArrayList<String>();
		int semanaFim = 0;
		contAux = 0;
		if (!(diasSemanaIncompletaProximoMesFim.isEmpty())) {
			
			
			for (int i = 0; i < diasSemanaMesAnterior.length; ++i) {
				
				if (diasSemanaMesAnterior[i].equals(diasSemanaIncompletaProximoMesFim.get(0))) {
					
					String stringIncompleta = "";
					contAux = i;
					for (int j = 0; j < diasSemanaIncompletaProximoMesFim.size(); ++j) {
						
						if (contAux < ultimoDiaMesAnterior)
							stringIncompleta += servidor.getEscala().getEscalaHoraContratual()[contAux];
						contAux+=1;
						
						
					}
					
					if (stringIncompleta.isEmpty())
						stringsSemanasIncompletasFim.add("-");
					else {
						stringsSemanasIncompletasFim.add(stringIncompleta);
					}
					i = contAux;
					stringIncompleta = "";
					
				}
				
			}
		}
		
		stringsSemanasIncompletasFim.trimToSize();
		stringsSemanasIncompletasInicio.trimToSize();
		
		//teste
		System.out.println();
		System.out.println("Strings inicio");
		for (int i = 0; i < stringsSemanasIncompletasInicio.size(); ++i) {
			System.out.println(stringsSemanasIncompletasInicio.get(i)+" ");
		}
		System.out.println();
		System.out.println("Strings fim");
		for (int i = 0; i < stringsSemanasIncompletasFim.size(); ++i) {
			System.out.println(stringsSemanasIncompletasFim.get(i)+" ");
		}
		System.out.println();
	
		
		//teste
		
		
		int semanaInicioAux = -1;
		int contMenorInicio = semanaInicioAux;
		
		for (int i = 0; i < stringsSemanasIncompletasInicio.size(); ++i) {
			for (int j = 0; j < stringsSemanasIncompletasInicio.size(); ++j) {
				
				if(!stringsSemanasIncompletasInicio.get(j).equals("-")) {
					if (stringsSemanasIncompletasInicio.get(i).equals(stringsSemanasIncompletasInicio.get(j))) {
					
						contMenorInicio += 1;
					
					}
				}
			}
			
			if (semanaInicioAux < contMenorInicio) {
				semanaInicio = i;
			}
			semanaInicioAux = contMenorInicio;
			contMenorInicio = 0;
		}
		
		int semanaFimAux = -1;
		int contMenorFim = semanaFimAux;
		for (int i = 0; i < stringsSemanasIncompletasFim.size(); ++i) {
			for (int j = 0; j < stringsSemanasIncompletasFim.size(); ++j) {
				
				if (!stringsSemanasIncompletasFim.get(j).equals("-")) {
					if (stringsSemanasIncompletasFim.get(i).equals(stringsSemanasIncompletasFim.get(j))) {
					
						contMenorFim += 1;
					
					}
				}
			}
			
			if (semanaFimAux < contMenorFim) {
				semanaFim = i;
			}
			semanaFimAux = contMenorFim;
			contMenorFim = 0;
		}
		
		//teste
		System.out.println();
		System.out.println("semana inicio");
		System.out.println(semanaInicio);
		System.out.println();
		System.out.println("semana fim");
		System.out.println(semanaFim);
		//teste
		
		
		ArrayList<String> legendasSemanasIncompletasInicio = new ArrayList<String>();
		int contSemanas = 0;
		contAux = 0;
		
		int maximo = 0;
		int minimo = 0;
		
		if (servidor.getCargaHoraria() == AuxiliarConstantes.CARGA_20) {
			maximo = AuxiliarConstantes.MAXIMO_CARGA_SEMANAL_20;
			minimo = AuxiliarConstantes.MINIMO_CARGA_SEMANAL_20;
			
		}
		else
		if (servidor.getCargaHoraria() == AuxiliarConstantes.CARGA_24) {
			maximo = AuxiliarConstantes.MAXIMO_CARGA_SEMANAL_24;
			minimo = AuxiliarConstantes.MINIMO_CARGA_SEMANAL_24;
		}
		else
		if (servidor.getCargaHoraria() == AuxiliarConstantes.CARGA_30) {
			maximo = AuxiliarConstantes.MAXIMO_CARGA_SEMANAL_30;
			minimo = AuxiliarConstantes.MINIMO_CARGA_SEMANAL_30;
		}
		else
		if (servidor.getCargaHoraria() == AuxiliarConstantes.CARGA_40) {
			maximo = AuxiliarConstantes.MAXIMO_CARGA_SEMANAL_40;
			minimo = AuxiliarConstantes.MINIMO_CARGA_SEMANAL_40;
		}
		else {
			minimo = 0;
			maximo = AuxiliarConstantes.MAXIMO_CARGA_SEMANAL_40;
		}
		
		float valor = servidor.getEscala().getHorasUltimaSemana();
		if (!diasSemanaIncompletaProximoMesInicio.isEmpty()) {
			
			for (int i = 0; i < diasSemanaMesAnterior.length; ++i) {
				contAux = i;
				
				if (diasSemanaMesAnterior[i].equals(diasSemanaIncompletaProximoMesInicio.get(0))) {
					
					for (int j = 0; j < diasSemanaIncompletaProximoMesInicio.size(); ++j) {
						
						if (contSemanas==semanaInicio) {
							valor += AuxiliarConstantes.getLegenda(servidor.getEscala().getEscalaHoraContratual()[contAux]).getValorTotal();
							if (valor <= maximo)
								if (contAux < ultimoDiaMesAnterior) {
										legendasSemanasIncompletasInicio.add(servidor.getEscala().getEscalaHoraContratual()[contAux]);
								}
						}
						contAux += 1;
						
						
					}
					contSemanas+=1;
					i = contAux;
				}
				
			}
			
			/*valor = servidor.getEscala().getHorasUltimaSemana();
			for (int i = 0; i < legendasSemanasIncompletasInicio.size(); ++i) {
				valor += AuxiliarConstantes.getLegenda(legendasSemanasIncompletasInicio.get(i)).getValorTotal();
			}
			if (valor < minimo) {
				
				for (int i = 0; i < legendasSemanasIncompletasInicio.size(); ++i) {
					int cont = 0;
					if (legendasSemanasIncompletasInicio.get(i).equals("")) {
						valor += AuxiliarConstantes.getLegenda(legendasSemanasIncompletasInicio.get(cont)).getValorTotal();
						if (valor <= minimo) {
							legendasSemanasIncompletasInicio.add(i, legendasSemanasIncompletasInicio.get(cont));
						}
						
						while (!validaHorasConsecutivas(legendasSemanasIncompletasInicio)) {
							cont+=1;
							if (valor <= minimo)
								legendasSemanasIncompletasInicio.add(i, legendasSemanasIncompletasInicio.get(cont));
							
						}
						if (valor >= minimo)
							break;
						
					}
				}
			}*/
		}
		
		//teste
		System.out.println();
		System.out.println("valor: " + valor);
		System.out.println();
		//teste
		
		ArrayList<String> legendasSemanasIncompletasFim = new ArrayList<String>();
		contSemanas = 0;
		if (!diasSemanaIncompletaProximoMesFim.isEmpty()) {
			for (int i = 0; i < diasSemanaMesAnterior.length; ++i) {
				contAux = i;
				if (diasSemanaMesAnterior[i].equals(diasSemanaIncompletaProximoMesFim.get(0))) {
					
					for (int j = 0; j < diasSemanaIncompletaProximoMesFim.size(); ++j) {
						
						if (contSemanas==semanaFim) {
							if (contAux < ultimoDiaMesAnterior)
								legendasSemanasIncompletasFim.add(servidor.getEscala().getEscalaHoraContratual()[contAux]);
						}
						contAux += 1;
						
						
					}
					contSemanas +=1;
					i = contAux;
				}
			}
		}
		
		legendasSemanasIncompletasInicio.trimToSize();
		legendasSemanasIncompletasFim.trimToSize();
		
		//teste
		System.out.println();
		System.out.println("legendas inicio");
		for (int i = 0; i < legendasSemanasIncompletasInicio.size(); ++i) {
			System.out.print(legendasSemanasIncompletasInicio.get(i) + " ");
		}
		System.out.println();
		System.out.println("legendas fim");
		for (int i = 0; i < legendasSemanasIncompletasFim.size(); ++i) {
			System.out.print(legendasSemanasIncompletasFim.get(i) + " ");

		}
		System.out.println();
		//teste
		
		if (!diasSemanaIncompletaProximoMesInicio.isEmpty()) {
			int contDiasInicio = 0;
			for (int i = intervaloSemanaIncompletaInicio.get(0); i <= intervaloSemanaIncompletaInicio.get(1); ++i) {
				
				if (contDiasInicio < legendasSemanasIncompletasInicio.size()) {
					escalaElaborada[i] = legendasSemanasIncompletasInicio.get(contDiasInicio);
				}
				contDiasInicio+=1;
			}
		}

		if (!diasSemanaIncompletaProximoMesFim.isEmpty()) {
			int contDiasFim = 0;
			for (int i = intervaloSemanaIncompletaFim.get(0); i <= intervaloSemanaIncompletaFim.get(1); ++i) {
				
				if (contDiasFim < legendasSemanasIncompletasFim.size()) {
					escalaElaborada[i] = legendasSemanasIncompletasFim.get(contDiasFim);
					System.out.println(servidor.getNome() + " * "  + legendasSemanasIncompletasFim.get(contDiasFim));
				}
				contDiasFim+=1;
			}
		}
		
		System.out.println();
		for (int i = 0; i < escalaElaborada.length; ++i) {
			System.out.print((i+1) + " " + diasSemanaProximoMes[i] + " "  + escalaElaborada[i] +  " ");
		}
		
		return escalaElaborada;
		
	}
	
	public EntidadeServidor[] elaborarEscalaServidoresProximoMes(EntidadeServidor servidores[], String area) {
		
		ProcessCalculo calculo;
		ProcessConferidor conferidor;
		calculo = new ProcessCalculo();
		conferidor = new ProcessConferidor();
		
		servidoresProximoMes = new EntidadeServidor[servidores.length];
		
		for (int i = 0; i < servidoresProximoMes.length; ++i) {
			
			EntidadeServidor servidor = new EntidadeServidor();
			servidor.setCargaHoraria(servidores[i].getCargaHoraria());
			servidor.setCargaHorariaSetor(servidores[i].getCargaHorariaSetor());
			servidor.setCargo(servidores[i].getCargo());
			servidor.setMatricula(servidores[i].getMatricula());
			servidor.setNome(servidores[i].getNome());
			servidor.setSetor(servidores[i].getSetor());
			
			EntidadeEscala escala = new EntidadeEscala();
			escala.setAno(servidores[i].getEscala().getAno());
			escala.setMes((servidores[i].getEscala().getMes() == 12 ? 1 : servidores[i].getEscala().getMes()+1));
			String escalaHoraExtra[] = new String[AuxiliarConstantes.getUltimoDiaMes((servidores[i].getEscala().getMes() == 12 ? 1 : servidores[i].getEscala().getMes()+1), servidores[i].getEscala().getAno())];
			for (int j = 0; j < escalaHoraExtra.length; ++j) {
			
				escalaHoraExtra[j] = "";
			}
			
			calculo.calcular(servidores[i]);
			servidores[i].getEscala().setHorasUltimaSemana(calculo.getHorasUltimaSemana());
			
			escala.setEscalaHoraContratual(elaborarEscala(servidores[i]));
			escala.setEscalaHoraExtra(escalaHoraExtra);
			escala.setObservacao("");
			servidor.setEscala(escala);
			servidor.getEscala().setHorasUltimaSemanaMesAnterior(calculo.getHorasUltimaSemana());
			servidoresProximoMes[i] = servidor;				
			
		}
		
		ArrayList<EntidadeLegenda> listaLegendas = getLegendasServidores(servidoresProximoMes);
		//TESTE
		System.out.println();
		System.out.println("Legendas usadas no setor");
		for (int i = 0; i < listaLegendas.size(); ++i) {
	
			System.out.println(listaLegendas.get(i).getLegenda());
		}
		//TESTE
		
		
		/*
		 * TROCA OS AFASTAMENTOS POR LEGENDAS
		 */
		boolean encontrou; 
		for (int i = 0; i < servidoresProximoMes.length; ++i) {
			
			for (int j = 0; j < servidoresProximoMes[i].getEscala().getEscalaHoraContratual().length; ++j) {
				
				EntidadeLegenda legenda = AuxiliarConstantes.getLegenda(servidoresProximoMes[i].getEscala().getEscalaHoraContratual()[j]); 
				encontrou = false;
				if (legenda.isAfastamento() && legenda.getValorTotal() > 0) {
					
					for (int k = 0; k < listaLegendas.size(); ++k) {
						
						if (
							(listaLegendas.get(k).getArea().indexOf(AuxiliarConstantes.ADMINISTRATIVO) 
							+ 
							listaLegendas.get(k).getArea().indexOf(AuxiliarConstantes.ENFERMARIA)
							+ 
							listaLegendas.get(k).getArea().indexOf(AuxiliarConstantes.AMBULATORIOS)
							+ 
							listaLegendas.get(k).getArea().indexOf(AuxiliarConstantes.CENTRO_CIRURGICO)
							+ 
							listaLegendas.get(k).getArea().indexOf(AuxiliarConstantes.PRONTO_SOCORRO)
							+ 
							listaLegendas.get(k).getArea().indexOf(AuxiliarConstantes.SERVICOS)
							) == -5
							&& 
							(listaLegendas.get(k).getArea().indexOf(area) != -1)
							&&
							listaLegendas.get(k).getValorTotal() == legenda.getValorTotal()
							&&
							listaLegendas.get(k).getValorManha() == legenda.getValorManha()
							&&
							listaLegendas.get(k).getValorTarde() == legenda.getValorTarde()
							&&
							listaLegendas.get(k).getValorNoite() == legenda.getValorNoite()
							&&
							listaLegendas.get(k).getPeriodo() == legenda.getPeriodo()
							) {
								
								
									encontrou = true;
								//if (listaLegendas.get(k).isComecaSete() && legenda.isComecaSete())
									servidoresProximoMes[i].getEscala().getEscalaHoraContratual()[j] = listaLegendas.get(k).getLegenda();
								
								//else 
								//if (!listaLegendas.get(k).isComecaSete() && !legenda.isComecaSete())
									servidoresProximoMes[i].getEscala().getEscalaHoraContratual()[j] = listaLegendas.get(k).getLegenda();
							}	
						}
					
						if (encontrou == false) {
							for (int k = 0; k < AuxiliarConstantes.LISTA_LEGENDAS.size(); ++k) {
								
								if (
									(AuxiliarConstantes.LISTA_LEGENDAS.get(k).getArea().indexOf(AuxiliarConstantes.ADMINISTRATIVO) 
									+ 
									AuxiliarConstantes.LISTA_LEGENDAS.get(k).getArea().indexOf(AuxiliarConstantes.ENFERMARIA)
									+ 
									AuxiliarConstantes.LISTA_LEGENDAS.get(k).getArea().indexOf(AuxiliarConstantes.AMBULATORIOS)
									+ 
									AuxiliarConstantes.LISTA_LEGENDAS.get(k).getArea().indexOf(AuxiliarConstantes.CENTRO_CIRURGICO)
									+ 
									AuxiliarConstantes.LISTA_LEGENDAS.get(k).getArea().indexOf(AuxiliarConstantes.PRONTO_SOCORRO)
									+ 
									AuxiliarConstantes.LISTA_LEGENDAS.get(k).getArea().indexOf(AuxiliarConstantes.SERVICOS)
									) == -5
									&& 
									(AuxiliarConstantes.LISTA_LEGENDAS.get(k).getArea().indexOf(area) != -1)
									&&
									AuxiliarConstantes.LISTA_LEGENDAS.get(k).getValorTotal() == legenda.getValorTotal()
									&&
									AuxiliarConstantes.LISTA_LEGENDAS.get(k).getValorManha() == legenda.getValorManha()
									&&
									AuxiliarConstantes.LISTA_LEGENDAS.get(k).getValorTarde() == legenda.getValorTarde()
									&&
									AuxiliarConstantes.LISTA_LEGENDAS.get(k).getValorNoite() == legenda.getValorNoite()
									&&
									AuxiliarConstantes.LISTA_LEGENDAS.get(k).getPeriodo() == legenda.getPeriodo()
									) {
										
										
											encontrou = true;
										//if (AuxiliarConstantes.LISTA_LEGENDAS.get(k).isComecaSete() && legenda.isComecaSete())
											servidoresProximoMes[i].getEscala().getEscalaHoraContratual()[j] = AuxiliarConstantes.LISTA_LEGENDAS.get(k).getLegenda();
										
										//else 
										//if (!AuxiliarConstantes.LISTA_LEGENDAS.get(k).isComecaSete() && !legenda.isComecaSete())
											servidoresProximoMes[i].getEscala().getEscalaHoraContratual()[j] = AuxiliarConstantes.LISTA_LEGENDAS.get(k).getLegenda();
									}	
								}
						}
					}
					else
					if (legenda.isAfastamento() && legenda.getValorTotal() == 0) {
						servidoresProximoMes[i].getEscala().getEscalaHoraContratual()[j] = "";
					}
					
				}
			}
		
		for (int i = 0; i < servidoresProximoMes.length; ++i) {
			conferidor.conferirEscala(servidoresProximoMes[i], false);
		}
		
		return servidoresProximoMes;
		
	}
	
	private boolean validaHorasConsecutivas(ArrayList<String> escalaHoraContratual) {
		
		EntidadeLegenda legendasEscalaHoraContratual[] = new EntidadeLegenda[escalaHoraContratual.size()];
		boolean escalaOK = true;
		
		for (int i = 0; i < escalaHoraContratual.size(); ++i) {
			
			legendasEscalaHoraContratual[i] = AuxiliarConstantes.getLegenda(escalaHoraContratual.get(i));
		}
		
		for (int i = 0; i < escalaHoraContratual.size(); ++i) {
			
			if ((i < escalaHoraContratual.size() - 1) & ((legendasEscalaHoraContratual[i] != null))) {
				
				
				
				//6 horas consecutivas anteriores
				
				if (legendasEscalaHoraContratual[i].getValorTarde() >= 1 & legendasEscalaHoraContratual[i].getValorNoite() == 12) {
			
					escalaOK = false;
				}
				
				//dias diferentes 6 horas de intervalo

				if (legendasEscalaHoraContratual[i].getValorNoite() == 12 & (legendasEscalaHoraContratual[i+1].getValorManha() >= 1 /*&& legendasEscalaHoraContratual[i+1].isComecaSete()*/)) {
	
					escalaOK = false;
				}
				
				//mesmo dia 6 e 12 consecutivas
				if ((legendasEscalaHoraContratual[i].getValorManha() == 6) & 
					(legendasEscalaHoraContratual[i].getDescricao().indexOf("13:00 as 18:00") != -1 | legendasEscalaHoraContratual[i].getDescricao().indexOf("13:00 as 17:00") != -1)
					) {
					escalaOK = false;
				}

			}
			
		}

		return escalaOK;	
	}
	
}
