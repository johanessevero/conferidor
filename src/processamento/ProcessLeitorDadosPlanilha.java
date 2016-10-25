package processamento;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import auxiliar.AuxiliarConstantes;
import auxiliar.AuxiliarGeral;
import auxiliar.RNException;
import entidade.EntidadeEscala;
import entidade.EntidadeServidor;

/**
 * 
 * @author Johanes Severo dos Santos
 * Classe que de uma planilha com a escala dos servidores no formato adequado.
 */
public class ProcessLeitorDadosPlanilha {
	
	private static int numServidores;
	
	public EntidadeServidor[] getDadosPlanilhaEscala(String planilha, int mes, int ano) throws RNException {
		
		EntidadeServidor servidores[] = null;
		//PLANILHA TRAK
		try {
			Workbook workbook = Workbook.getWorkbook(new File(planilha));
			Sheet sheet = workbook.getSheet(0);
			
			int numCelulas= 0;
			numServidores = 0;
			int minDia = 5;
			int maxDia = AuxiliarConstantes.getUltimoDiaMes(mes, ano)-1 + minDia;
			int contServidores = -1;
			
			String dado = "";
			
			while (!dado.toUpperCase().equals("TOTAL")) {
				
				dado = sheet.getCell("B" + (numCelulas + 1)).getContents();
				numCelulas+=1;
				
			}
			
			numCelulas -= 1;
			numServidores = (numCelulas)/3;
			
			servidores = new EntidadeServidor[numServidores - 2];
			
			for (int i = 8; i < numCelulas; i+=3) {
				
				contServidores += 1;
				servidores[contServidores] = new EntidadeServidor();
				EntidadeEscala escala = new EntidadeEscala();
				servidores[contServidores].setSetor(sheet.getCell("B4").getContents());
				
				servidores[contServidores].setCargo(sheet.getCell("B" + (i+1)).getContents());
				String escalaHoraExtra[] = new String[AuxiliarConstantes.getUltimoDiaMes(mes, ano)];
				String escalaHoraContratual[] = new String[AuxiliarConstantes.getUltimoDiaMes(mes, ano)];
				
				String nome = sheet.getCell("B" + i).getContents();
				String matricula = sheet.getCell("C" + i).getContents();
				String cargaHoraria = sheet.getCell("E" + i).getContents();
				String passagemMesAnterior = sheet.getCell("D" + i).getContents();
				String observacao = sheet.getCell("E" + (i + 2)).getContents();
				
				for(int k = minDia; k <= maxDia; ++k) {
					
					escalaHoraExtra[k-minDia] = AuxiliarGeral.substituiEspacos((sheet.getCell(k, i).getContents()).trim()).toUpperCase();
					if (escalaHoraExtra[k-minDia] == null)
						escalaHoraExtra[k-minDia] = "";
					escalaHoraContratual[k-minDia] = AuxiliarGeral.substituiEspacos((sheet.getCell(k, i-1).getContents()).trim()).toUpperCase();
					if (escalaHoraContratual[k-minDia] == null)
						escalaHoraContratual[k-minDia] = "";
					
					
				}
				
				servidores[contServidores].setMatricula((matricula == null ? "" : matricula));
				servidores[contServidores].setNome((nome == null ? "" : nome));
				if (cargaHoraria != null & !cargaHoraria.equals("")) {
					servidores[contServidores].setCargaHoraria(Integer.parseInt(cargaHoraria));
					servidores[contServidores].setCargaHorariaSetor(Integer.parseInt(cargaHoraria));
				}
				else {
					servidores[contServidores].setCargaHoraria(0);
					servidores[contServidores].setCargaHorariaSetor(0);
				}
				
				escala.setAno(AuxiliarConstantes.getAnoAtual());
				
				
				if (passagemMesAnterior != null & !passagemMesAnterior.equals(""))
					escala.setHorasUltimaSemanaMesAnterior(Float.parseFloat(passagemMesAnterior));
				else
					escala.setHorasUltimaSemanaMesAnterior((float) 0);
				
				escala.setObservacao((observacao == null ? "" : observacao));
				escala.setMes(mes);
				escala.setEscalaLancada(false);
				escala.setEscalaHoraContratual(escalaHoraContratual);
				escala.setEscalaHoraExtra(escalaHoraExtra);
				servidores[contServidores].setEscala(escala);
		
			}
			        
			workbook.close();
		}
		catch(IOException ex) {
			throw new RNException("Nao e possivel obter os dados da planilha!"); 
		}
		catch(BiffException ex) {
			throw new RNException("Nao possivel obter os dados da planilha!"); 
		}
		return servidores;
		
	}
	
	public EntidadeServidor[] getDadosPlanilhaEscalaXML(String planilha, int mes, int ano) throws RNException {
		
	
		String strBuscaNome = "<Cell ss:Index=\"2\" ss:StyleID=\"s122\"><Data ss:Type=\"String\">";
		String strBuscaMatricula = "<Cell ss:StyleID=\"s123\"><Data ss:Type=\"String\">";
		String strBuscaCargo = "<Cell ss:Index=\"2\" ss:StyleID=\"s142\"><Data ss:Type=\"String\">";
		String strBuscaUltimaSemanaMesAnterior = "<Cell ss:MergeDown=\"1\" ss:StyleID=\"m38130632\"><Data ss:Type=\"Number\">";
		String strBuscaObservacao = "<Cell ss:MergeAcross=\"31\" ss:StyleID=\"s168\"><Data ss:Type=\"String\">";
		String strBuscaCargaHoraria = "<Cell ss:StyleID=\"s130\"><Data ss:Type=\"String\">";
		String strBuscaDiaContratualSemana = "<Cell ss:StyleID=\"s132\"><Data ss:Type=\"String\">";
		String strBuscaDiaContratuaFinalDeSemana = "<Cell ss:StyleID=\"s131\"><Data ss:Type=\"String\">";
		String strBuscaDiaExtra = "<Cell ss:StyleID=\"s146\"><Data ss:Type=\"String\">";
		String strBuscaSetor = "<Cell ss:Index=\"2\" ss:StyleID=\"s21\"><Data ss:Type=\"String\">";
		
		char menor = '<';
		char maior = '>';
		
		int cont;
		EntidadeServidor servidores[] = null;
		 //for (int z = 1; z <= 16; ++z) {
			
		try {
			String arquivo = "";
			String linha = ""; 
			FileReader reader = new FileReader(planilha);  
			BufferedReader leitor = new BufferedReader(reader);  
			
			char charsArquivo[] = new char[1000000];
			leitor.read(charsArquivo);
			arquivo = new String(charsArquivo).trim().toString();
			
			/*while ((linha = leitor.readLine()) != null) {   
				
				arquivo += linha;
				
			}*/
			
			reader.close();
			
			
					//busca setor
					int posBuscaSetor = arquivo.indexOf(strBuscaSetor);
					String setor = "";
					cont = -1;
					
					while (posBuscaSetor != -1) {
						
						setor = "";
						cont += 1;
						while (arquivo.charAt(posBuscaSetor) != maior) {
							posBuscaSetor += 1;
						}
						
						posBuscaSetor+= 2;
						
						while (arquivo.charAt(posBuscaSetor) != maior) {
							posBuscaSetor += 1;
						}
						
						posBuscaSetor+= 1;
						
						while (arquivo.charAt(posBuscaSetor) != menor) {
							setor += arquivo.charAt(posBuscaSetor);
							posBuscaSetor += 1;
						}
					
					
						posBuscaSetor = arquivo.indexOf(strBuscaSetor, posBuscaSetor);
					}
					
					int posBuscaNome = arquivo.indexOf(strBuscaNome);
					int contNomes = 0;
					
					while (posBuscaNome != -1) {
						contNomes += 1;
						while (arquivo.charAt(posBuscaNome) != maior) {
							posBuscaNome += 1;
						}
						
						posBuscaNome+= 2;
						
						while (arquivo.charAt(posBuscaNome) != maior) {
							posBuscaNome += 1;
						}
						
						posBuscaNome+= 1;
						
						while (arquivo.charAt(posBuscaNome) != menor) {
							posBuscaNome += 1;
						}
					
						posBuscaNome = arquivo.indexOf(strBuscaNome, posBuscaNome);
					}
				//}
					
				numServidores = contNomes;
				servidores = new EntidadeServidor[contNomes]; 
				
				for (int i = 0; i < servidores.length; ++i) {
					servidores[i] = new EntidadeServidor();
					servidores[i].setSetor(setor.trim());
					servidores[i].setEscala(new EntidadeEscala());
					servidores[i].getEscala().setMes(mes);
					servidores[i].getEscala().setAno(ano);
				}
				
				//busca nomes
				posBuscaNome = arquivo.indexOf(strBuscaNome);
				String nome;
				cont = -1;
				
				while (posBuscaNome != -1) {
					
					nome = "";
					cont += 1;
					while (arquivo.charAt(posBuscaNome) != maior) {
						posBuscaNome += 1;
					}
					
					posBuscaNome+= 2;
					
					while (arquivo.charAt(posBuscaNome) != maior) {
						posBuscaNome += 1;
					}
					
					posBuscaNome+= 1;
					
					while (arquivo.charAt(posBuscaNome) != menor) {
						nome += arquivo.charAt(posBuscaNome);
						posBuscaNome += 1;
					}
				
					servidores[cont].setNome(nome.trim());
					posBuscaNome = arquivo.indexOf(strBuscaNome, posBuscaNome);
				}
				
				//busca cargos
				int posBuscaCargo = arquivo.indexOf(strBuscaCargo);
				String cargo;
				cont = -1;
						
				while (posBuscaCargo != -1) {
							
					cargo = "";
					cont += 1;
					while (arquivo.charAt(posBuscaCargo) != maior) {
						posBuscaCargo += 1;
					}
							
					posBuscaCargo+= 2;
							
					while (arquivo.charAt(posBuscaCargo) != maior) {
						posBuscaCargo += 1;
					}
							
					posBuscaCargo+= 1;
							
					while (arquivo.charAt(posBuscaCargo) != menor) {
						cargo += arquivo.charAt(posBuscaCargo);
						posBuscaCargo += 1;
					}
						
					servidores[cont].setCargo(cargo.trim());
					posBuscaCargo = arquivo.indexOf(strBuscaCargo, posBuscaCargo);
				}
				
				//busca matriculas
				int posBuscaMatricula = arquivo.indexOf(strBuscaMatricula);
				String matricula;
				cont = -1;
				
				while (posBuscaMatricula != -1) {
					
					matricula = "";
					cont += 1;
					while (arquivo.charAt(posBuscaMatricula) != maior) {
						posBuscaMatricula += 1;
					}
					
					posBuscaMatricula+= 2;
					
					while (arquivo.charAt(posBuscaMatricula) != maior) {
						posBuscaMatricula += 1;
					}
					
					posBuscaMatricula+= 1;
					
					while (arquivo.charAt(posBuscaMatricula) != menor) {
						matricula += arquivo.charAt(posBuscaMatricula);
						posBuscaMatricula += 1;
					}
				
					servidores[cont].setMatricula(matricula.trim());
					posBuscaMatricula = arquivo.indexOf(strBuscaMatricula, posBuscaMatricula);
				}
				
				//busca ultimaSemanaMesAnterior
				int posBuscaUltimaSemanaMesAnterior = arquivo.indexOf(strBuscaUltimaSemanaMesAnterior);
				String ultimaSemanaMesAnterior;
				cont = -1;
				
				while (posBuscaUltimaSemanaMesAnterior != -1) {
					
					ultimaSemanaMesAnterior = "";
					cont += 1;
					while (arquivo.charAt(posBuscaUltimaSemanaMesAnterior) != maior) {
						posBuscaUltimaSemanaMesAnterior += 1;
					}
					
					posBuscaUltimaSemanaMesAnterior+= 2;
					
					while (arquivo.charAt(posBuscaUltimaSemanaMesAnterior) != maior) {
						posBuscaUltimaSemanaMesAnterior += 1;
					}
					
					posBuscaUltimaSemanaMesAnterior+= 1;
					
					while (arquivo.charAt(posBuscaUltimaSemanaMesAnterior) != menor) {
						ultimaSemanaMesAnterior += arquivo.charAt(posBuscaUltimaSemanaMesAnterior);
						posBuscaUltimaSemanaMesAnterior += 1;
					}
					
					servidores[cont].getEscala().setHorasUltimaSemanaMesAnterior(Float.parseFloat(ultimaSemanaMesAnterior.trim()));
					posBuscaUltimaSemanaMesAnterior = arquivo.indexOf(strBuscaUltimaSemanaMesAnterior, posBuscaUltimaSemanaMesAnterior);
				}
				
				//busca cargaHoraria
				int posBuscaCargaHoraria = arquivo.indexOf(strBuscaCargaHoraria);
				String cargaHoraria;
				cont = -1;
				
				while (posBuscaCargaHoraria != -1) {
					
					cargaHoraria = "";
					cont += 1;
					while (arquivo.charAt(posBuscaCargaHoraria) != maior) {
						posBuscaCargaHoraria += 1;
					}
					
					posBuscaCargaHoraria+= 2;
					
					while (arquivo.charAt(posBuscaCargaHoraria) != maior) {
						posBuscaCargaHoraria += 1;
					}
					
					posBuscaCargaHoraria+= 1;
					
					while (arquivo.charAt(posBuscaCargaHoraria) != menor) {
						cargaHoraria += arquivo.charAt(posBuscaCargaHoraria);
						posBuscaCargaHoraria += 1;
					}
				
					servidores[cont].setCargaHoraria(Integer.parseInt(cargaHoraria.trim()));
					servidores[cont].setCargaHorariaSetor(Integer.parseInt(cargaHoraria.trim()));
					posBuscaCargaHoraria = arquivo.indexOf(strBuscaCargaHoraria, posBuscaCargaHoraria);
				}
				
				//busca observacao
				int posBuscaObservacao = arquivo.indexOf(strBuscaObservacao);
				String observacao;
				cont = -1;
				
				while (posBuscaObservacao != -1) {
					
					observacao = "";
					cont += 1;
					while (arquivo.charAt(posBuscaObservacao) != maior) {
						posBuscaObservacao += 1;
					}
					
					posBuscaObservacao+= 2;
					
					while (arquivo.charAt(posBuscaObservacao) != maior) {
						posBuscaObservacao += 1;
					}
					
					posBuscaObservacao+= 1;
					
					while (arquivo.charAt(posBuscaObservacao) != menor) {
						observacao += arquivo.charAt(posBuscaObservacao);
						posBuscaObservacao += 1;
					}
				
					servidores[cont].getEscala().setObservacao(observacao);
					posBuscaObservacao = arquivo.indexOf(strBuscaObservacao, posBuscaObservacao);
				}
				
				//busca diaExtra
				int posBuscaDiaExtra = arquivo.indexOf(strBuscaDiaExtra);
				String diaExtra;
				for (int i = 0; i < servidores.length; ++i) {
					
					cont = 0;
					
					while (cont <= 30 & posBuscaDiaExtra != -1) {
						
						diaExtra = "";
						while (arquivo.charAt(posBuscaDiaExtra) != maior) {
							posBuscaDiaExtra += 1;
						}
						
						posBuscaDiaExtra+= 2;
						
						while (arquivo.charAt(posBuscaDiaExtra) != maior) {
							posBuscaDiaExtra += 1;
						}
						
						posBuscaDiaExtra+= 1;
						
						while (arquivo.charAt(posBuscaDiaExtra) != menor) {
							diaExtra += arquivo.charAt(posBuscaDiaExtra);
							posBuscaDiaExtra += 1;
						}
						
						//System.out.print(diaExtra.trim() + " ");
						servidores[i].getEscala().getEscalaHoraExtra()[cont] = diaExtra.trim();
						posBuscaDiaExtra = arquivo.indexOf(strBuscaDiaExtra, posBuscaDiaExtra);
						
						cont += 1;
					}
				}
		
				char diasSemanas[] = new char[31];
				String diaContratualSemana;
				String diaContratuaFinalDeSemana;
				int posBuscaDiaContratualSemana = arquivo.indexOf(strBuscaDiaContratualSemana);
				int posBuscaDiaContratuaFinalDeSemana = arquivo.indexOf(strBuscaDiaContratuaFinalDeSemana);
				int contS;
				int contF;
				
				
				for (int i = 0; i < servidores.length; ++i) {
				
					contS = 0;
					contF = 0;
					
					int numS = 0;
					int numF = 0;
					for (int k = 0; k < diasSemanas.length; ++k) {
						
						diasSemanas[k] = ((AuxiliarConstantes.getDiaSemanaData(k+1, mes, ano).equals(AuxiliarConstantes.SABADO) || AuxiliarConstantes.getDiaSemanaData(k+1, mes, ano).equals(AuxiliarConstantes.DOMINGO)) ? 'f' : 's');
						if (diasSemanas[k] == 'f') {
							numF += 1;
						}
						else {
							numS += 1;
						}
					}
					
					
					String ecds[] = new String[numS];
					String ecfs[] = new String[numF];
					
					while (contS < numS) {
						
						diaContratualSemana = "";
						while (arquivo.charAt(posBuscaDiaContratualSemana) != maior) {
							posBuscaDiaContratualSemana += 1;
						}
						
						posBuscaDiaContratualSemana+= 2;
						
						while (arquivo.charAt(posBuscaDiaContratualSemana) != maior) {
							posBuscaDiaContratualSemana += 1;
						}
						
						posBuscaDiaContratualSemana+= 1;
						
						while (arquivo.charAt(posBuscaDiaContratualSemana) != menor) {
							diaContratualSemana += arquivo.charAt(posBuscaDiaContratualSemana);
							posBuscaDiaContratualSemana += 1;
						}
						
						//System.out.print(diaContratualSemana.trim() + " - S - ");
						if (!diaContratualSemana.trim().isEmpty())
							//System.out.print(contS + " " + diaContratualSemana.trim() + " ");
		
						ecds[contS] = diaContratualSemana.trim();
						//servidores[i].getEscala().getEscalaHoraContratual()[cont] = diaContratualSemana.trim();
						posBuscaDiaContratualSemana = arquivo.indexOf(strBuscaDiaContratualSemana, posBuscaDiaContratualSemana);
						contS += 1;
					}	
					
					System.out.println();
					
					while (contF < numF) {
						
						diaContratuaFinalDeSemana = "";
						while (arquivo.charAt(posBuscaDiaContratuaFinalDeSemana) != maior) {
							posBuscaDiaContratuaFinalDeSemana += 1;
						}
						
						posBuscaDiaContratuaFinalDeSemana+= 2;
						
						while (arquivo.charAt(posBuscaDiaContratuaFinalDeSemana) != maior) {
							posBuscaDiaContratuaFinalDeSemana += 1;
						}
						
						posBuscaDiaContratuaFinalDeSemana+= 1;
						
						while (arquivo.charAt(posBuscaDiaContratuaFinalDeSemana) != menor) {
							diaContratuaFinalDeSemana += arquivo.charAt(posBuscaDiaContratuaFinalDeSemana);
							posBuscaDiaContratuaFinalDeSemana += 1;
						}
						
						if (!diaContratuaFinalDeSemana.trim().isEmpty())
							//System.out.print(contF + " " + diaContratuaFinalDeSemana.trim() + " ");
						ecfs[contF] = diaContratuaFinalDeSemana.trim();
						posBuscaDiaContratuaFinalDeSemana = arquivo.indexOf(strBuscaDiaContratuaFinalDeSemana, posBuscaDiaContratuaFinalDeSemana);
						
						contF += 1;
					}
					
					System.out.println();
					
					numF = 0;
					numS = 0;
					for (int k = 0; k < diasSemanas.length; ++k) {
						
						if (diasSemanas[k] == 'f') {
							servidores[i].getEscala().getEscalaHoraContratual()[k] = (ecfs[numF] == null ? "" : ecfs[numF]);
						
							numF += 1;
						}
						else {
							servidores[i].getEscala().getEscalaHoraContratual()[k] = (ecds[numS] == null ? "" : ecds[numS]);
							
							numS += 1;
							
						}
						System.out.print(k + 1 + " " + AuxiliarConstantes.getDiaSemanaData(k+1, mes, ano) + " : " + servidores[i].getEscala().getEscalaHoraContratual()[k]  + " ");
					}
					
					System.out.println();
					
				}
			}
			catch (IOException ex) { 
				
				throw new RNException("Nao e possivel obter os dados do arquivo XML!"); 
			}
			
			return servidores;
		
		}
	
		public HashMap<String, Float>[] getValoresHoraExtra() throws RNException {
			
			HashMap<String, Float> listas[] = new HashMap[2];
			try {
				Workbook workbook = Workbook.getWorkbook(new File(AuxiliarConstantes.VALORES_HE));
				Sheet sheet = workbook.getSheet(0);
				
				listas[0] = new HashMap<String, Float>();
				listas[1] = new HashMap<String, Float>();
				
				String matricula = "";
				Float valorNoturno = new Float(0);
				Float valorDiurno = new Float(0);
				int contNome = 2;
				
				while(!matricula.equalsIgnoreCase("FIM")) {
					
					matricula = sheet.getCell("C" + contNome).getContents().trim();
						
					if (!matricula.equalsIgnoreCase("FIM")) {
						valorDiurno = Float.parseFloat(sheet.getCell("I" + contNome).getContents().replace(',', '.').trim());
						valorNoturno = Float.parseFloat(sheet.getCell("J" + contNome).getContents().replace(',', '.').trim());
						
						System.out.println(contNome + " " + matricula + " " + valorDiurno + " " + valorNoturno);
							
						listas[0].put(matricula, valorDiurno);
						listas[1].put(matricula, valorNoturno);
							
						contNome +=1;
					}
				
				}
			}
			catch(IOException ex) {
				throw new RNException("Nao e possivel obter os dados da planilha de valores!"); 

			}
			catch(BiffException ex) {
				throw new RNException("Nao e possivel obter os dados da planilha de valores!"); 

			}
			
			return listas;
		}
	
	public static void main(String args[]) throws Exception {

		ProcessLeitorDadosPlanilha pldp = new ProcessLeitorDadosPlanilha();
		EntidadeServidor servidores[] = pldp.getDadosPlanilhaEscalaXML("nlr_junho_2012.xml", 6, 2012);
		
		//pldp.getValoresHoraExtra();
		
	}

}
