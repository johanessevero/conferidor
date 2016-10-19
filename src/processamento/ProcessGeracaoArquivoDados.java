package processamento;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

import auxiliar.AuxiliarConstantes;
import auxiliar.AuxiliarGeral;
import auxiliar.RelatorioException;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import entidade.EntidadeLegenda;
import entidade.EntidadeMensagem;
import entidade.EntidadeServidor;

/**
 * 
 * @author Johanes Severo dos Santos
 * Classe que gera arquivos com dados retirados e deduzidos da escala.
 */
public class ProcessGeracaoArquivoDados {
	
	public void geraProcessoHorasExtras(EntidadeServidor servidores[], String arquivo, int mes, int ano, HashMap<String, Float> listasValoresHE[]) {}
	
	public void geraEscaladosNoDia(EntidadeServidor servidores[], String local, int dia) throws RelatorioException {
		
		String nomeArq = AuxiliarGeral.substituiUnderline(local.trim()+ servidores[0].getEscala().getMes() + "_" + servidores[0].getEscala().getAno() +"_RELATORIO_ESCALADOS_NO_DIA_"+dia+".pdf") ;     
		Document relPDF = null;
		OutputStream fos = null;
		
		try {
			//cria o documento tamanho A4, margens de 2,54cm
			relPDF = new Document(PageSize.A4, 72, 72, 72, 72);
	
	        //cria a stream de saida
	        fos = new FileOutputStream(nomeArq);
	        
	        //associa a stream de saida ao 
	        PdfWriter pdfWriter = PdfWriter.getInstance(relPDF, fos);
	        
	        //abre o documento
	        relPDF.open();
		
	        Paragraph titulo = new Paragraph("DISTRIBUICAO DE ESCALA DIARIA " + servidores[0].getSetor().trim() + " DIA: " + dia + "/" + AuxiliarConstantes.MESES[servidores[0].getEscala().getMes() - 1] + "/" + servidores[0].getEscala().getAno() + ": " + AuxiliarConstantes.getDiaSemanaData(dia, servidores[0].getEscala().getMes(), servidores[0].getEscala().getAno()).toUpperCase(), new Font(Font.TIMES_ROMAN, 10, Font.BOLD));
	    	titulo.setSpacingAfter(17);
			relPDF.add(titulo); 	
		    
			PdfPTable table = new PdfPTable(3);
	        table.setWidthPercentage(100.0f);
	        table.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
	        table.setSpacingAfter(5);
	        
	        PdfPCell manhaCell = new PdfPCell(new Paragraph("MANHA", new Font(Font.TIMES_ROMAN, 8, Font.BOLD)));
	    	table.addCell(manhaCell);
	    	PdfPCell tardeCell = new PdfPCell(new Paragraph("TARDE", new Font(Font.TIMES_ROMAN, 8, Font.BOLD)));
	    	table.addCell(tardeCell);
	    	PdfPCell noiteCell = new PdfPCell(new Paragraph("NOITE", new Font(Font.TIMES_ROMAN, 8, Font.BOLD)));
	    	table.addCell(noiteCell);
			
	    	PdfPTable tableManha = new PdfPTable(1);
	        table.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
	        PdfPTable tableTarde = new PdfPTable(1);
	        table.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
	        PdfPTable tableNoite = new PdfPTable(1);
	        table.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
	    	
		    for (int i = 0; i < servidores.length; ++i) {
			    
		    	EntidadeLegenda legendaExtra = AuxiliarConstantes.getLegenda(servidores[i].getEscala().getEscalaHoraExtra()[dia-1]);
		    	EntidadeLegenda legendaContratual = AuxiliarConstantes.getLegenda(servidores[i].getEscala().getEscalaHoraContratual()[dia-1]);
		    		
		    	if (!(legendaExtra.isAfastamento() || legendaContratual.isAfastamento())) {
			    	if (legendaExtra.getValorManha() > 0 || legendaContratual.getValorManha() > 0) {
			    		PdfPCell nomeCellD = new PdfPCell(new Paragraph(servidores[i].getNome() +" "+legendaExtra.getInicioManha()+" "+legendaExtra.getFimManha() + legendaContratual.getInicioManha()+" "+legendaContratual.getFimManha(), new Font(Font.TIMES_ROMAN, 8, Font.BOLD)));
			    		tableManha.addCell(nomeCellD);
			    	}
		
			    	if (legendaExtra.getValorTarde() > 0 || legendaContratual.getValorTarde() > 0) {
			    		PdfPCell nomeCellD = new PdfPCell(new Paragraph(servidores[i].getNome() + " " +legendaExtra.getInicioTarde()+" "+legendaExtra.getFimTarde() + legendaContratual.getInicioTarde()+" "+legendaContratual.getFimTarde(), new Font(Font.TIMES_ROMAN, 8, Font.BOLD)));
			    		tableTarde.addCell(nomeCellD);
			    	}
			
			    	if (legendaExtra.getValorNoite() > 0 || legendaContratual.getValorNoite() > 0) {
			    		PdfPCell nomeCellD = new PdfPCell(new Paragraph(servidores[i].getNome()+" "+legendaExtra.getInicioNoite()+" "+legendaExtra.getFimNoite() + legendaContratual.getInicioNoite()+" "+legendaContratual.getFimNoite(), new Font(Font.TIMES_ROMAN, 8, Font.BOLD)));
			    		tableNoite.addCell(nomeCellD);
			    	}	
		    	}
		    }
		    
		    table.addCell(tableManha);
	    	table.addCell(tableTarde);
	    	table.addCell(tableNoite);
		    relPDF.add(table);
				
			if (relPDF != null) {
					//fechamento do documento
				relPDF.close();
			}
			if (fos != null) {
					//fechamento da stream de sa�da
				fos.close();
			}
			
	       
			Runtime.getRuntime().exec("rundll32 SHELL32.DLL,ShellExec_RunDLL "+nomeArq);
		}
		catch(DocumentException ex) {
			
			throw new RelatorioException("Erro ao gerar o relatorio de escalados no dia!");
			
		}
		catch(IOException ex) {
			
			throw new RelatorioException("Erro ao gerar o relatorio de escalados no dia!");
		}
	}
	
	
	
	public void geraRelatorioQuantidadeRefeicoes(EntidadeServidor servidores[], String local) throws RelatorioException {
			
			String nomeArq = AuxiliarGeral.substituiUnderline(local.trim()+ servidores[0].getEscala().getMes() + "_" + servidores[0].getEscala().getAno() +"_RELATORIO_TESOURARIA.pdf") ;     
			Document relPDF = null;
			OutputStream fos = null;
			
			try {
				//cria o documento tamanho A4, margens de 2,54cm
		        relPDF = new Document(PageSize.A4, 72, 72, 72, 72);
		
		        //cria a stream de saida
		        fos = new FileOutputStream(nomeArq);
		        
		        //associa a stream de saida ao 
		        PdfWriter pdfWriter = PdfWriter.getInstance(relPDF, fos);
		        
		        //abre o documento
		        relPDF.open();
			
		    	Paragraph titulo = new Paragraph("QUANTIDADE DE REFEICOES " + servidores[0].getSetor().trim() + " " + AuxiliarConstantes.MESES[servidores[0].getEscala().getMes() - 1] + " " + servidores[0].getEscala().getAno() , new Font(Font.TIMES_ROMAN, 15, Font.BOLD));
		    	titulo.setSpacingAfter(17);
				relPDF.add(titulo); 	
			    
				PdfPTable table = new PdfPTable(5);
		        table.setWidthPercentage(100.0f);
		        table.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
		        table.setSpacingAfter(5);
		        
		        PdfPCell nomeCell = new PdfPCell(new Paragraph("NOME", new Font(Font.TIMES_ROMAN, 8, Font.BOLD)));
		    	table.addCell(nomeCell);
		    	PdfPCell matriculaCell = new PdfPCell(new Paragraph("MATRICULA", new Font(Font.TIMES_ROMAN, 8, Font.BOLD)));
		    	table.addCell(matriculaCell);
		    	PdfPCell almocoCell = new PdfPCell(new Paragraph("REFEICOES DIURNAS", new Font(Font.TIMES_ROMAN, 8, Font.BOLD)));
		    	table.addCell(almocoCell);
		    	PdfPCell jantaCell = new PdfPCell(new Paragraph("REFEICOES NOTURNAS", new Font(Font.TIMES_ROMAN, 8, Font.BOLD)));
		    	table.addCell(jantaCell);
		    	PdfPCell totalCell = new PdfPCell(new Paragraph("TOTAL", new Font(Font.TIMES_ROMAN, 8, Font.BOLD)));
		    	table.addCell(totalCell);
				
			 
			    for (int i = 0; i < servidores.length; ++i) {
			   
			    	if (servidores[i].getEscala().getQuantidadeRefeicoesDiurno()  + servidores[i].getEscala().getQuantidadeRefeicoesNoturno() > 0) {
				    	Font font = new Font(Font.COURIER, 12, Font.BOLD);
				        PdfPCell nomeCellD = new PdfPCell(new Paragraph(servidores[i].getNome(), new Font(Font.TIMES_ROMAN, 8, Font.BOLD)));
				    	table.addCell(nomeCellD);
				    	PdfPCell matriculaCellD = new PdfPCell(new Paragraph(servidores[i].getMatricula(), new Font(Font.TIMES_ROMAN, 8, Font.BOLD)));
				    	table.addCell(matriculaCellD);
				    	PdfPCell almocoCellD = new PdfPCell(new Paragraph("" + servidores[i].getEscala().getQuantidadeRefeicoesDiurno(), new Font(Font.TIMES_ROMAN, 8, Font.BOLD)));
				    	table.addCell(almocoCellD);
				    	PdfPCell jantaCellD = new PdfPCell(new Paragraph("" + servidores[i].getEscala().getQuantidadeRefeicoesNoturno(), new Font(Font.TIMES_ROMAN, 8, Font.BOLD)));
				    	table.addCell(jantaCellD);
				    	PdfPCell totalCellD = new PdfPCell(new Paragraph("" + (servidores[i].getEscala().getQuantidadeRefeicoesDiurno()  + servidores[i].getEscala().getQuantidadeRefeicoesNoturno()), new Font(Font.TIMES_ROMAN, 8, Font.BOLD)));
				    	table.addCell(totalCellD);
			    	}
			
			    
			    }
			    
			    relPDF.add(table);
					
				if (relPDF != null) {
						//fechamento do documento
					relPDF.close();
				}
				if (fos != null) {
						//fechamento da stream de sa�da
					fos.close();
				}
					
		       
				Runtime.getRuntime().exec("rundll32 SHELL32.DLL,ShellExec_RunDLL "+nomeArq);
			}
			catch(DocumentException ex) {
				
				throw new RelatorioException("Erro ao gerar o relatorio de quantidade de refeicoes!");
				
			}
			catch(IOException ex) {
				
				throw new RelatorioException("Erro ao gerar o relatorio de quantidade de refeicoes!");
			}
			
		}
	
	public void geraProcessoHorasExtras(EntidadeServidor servidores[], int mes, int ano, HashMap<String, Float> listasValoresHE[]) throws RelatorioException {
		
		String nomeArq = AuxiliarGeral.substituiUnderline(servidores[0].getSetor().trim()+ servidores[0].getEscala().getMes() + "_" + servidores[0].getEscala().getAno() +"_PROCESSO_EXTRA.pdf") ;     
		Document relPDF = null;
		OutputStream fos = null;
		
		try {
			//cria o documento tamanho A4, margens de 2,54cm
	        relPDF = new Document(PageSize.A4.rotate(), 72, 72, 72, 72);
	        //cria a stream de saida
	        fos = new FileOutputStream(nomeArq);
	        
	        //associa a stream de saida ao 
	        PdfWriter pdfWriter = PdfWriter.getInstance(relPDF, fos);
	        
	        //abre o documento
	        relPDF.open();
		
	        Paragraph titulo1 = new Paragraph("COMISSAO PERMANENTE DE AVALIACAO CONTROLE E FISCALIZACAO DAS HORAS EXTRAS DA SES-DF", new Font(Font.TIMES_ROMAN, 5, Font.BOLD));
	    	titulo1.setSpacingAfter(17);
			relPDF.add(titulo1); 
			
			Paragraph titulo2 = new Paragraph("FORMULARIO 10 MODIFICADO", new Font(Font.TIMES_ROMAN, 7, Font.BOLD));
			titulo2.setAlignment(Paragraph.ALIGN_CENTER);
			titulo2.setSpacingAfter(17);
			relPDF.add(titulo2); 
			
			Paragraph titulo3 = new Paragraph("RELACIONAR OS PROFISSIONAIS QUE DEVEM RECEBER HORAS EXTRAS", new Font(Font.TIMES_ROMAN, 7, Font.BOLD));
			titulo3.setAlignment(Paragraph.ALIGN_CENTER);
			titulo3.setSpacingAfter(17);
			relPDF.add(titulo3); 
	        
	    	Paragraph titulo4 = new Paragraph("COORDENACAO GERAL DE SAUDE DE TAGUATINGA SETOR: "+ servidores[0].getSetor().trim() + " " + AuxiliarConstantes.MESES[mes-1] + "/" + ano, new Font(Font.TIMES_ROMAN, 7, Font.BOLD));
	    	titulo4.setAlignment(Paragraph.ALIGN_CENTER);
	    	titulo4.setSpacingAfter(17);
			relPDF.add(titulo4); 	
		    
			PdfPTable table = new PdfPTable(10);
	        table.setWidthPercentage(100.0f);
	        table.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
	        table.setSpacingAfter(5);
	        
	        PdfPCell numCell = new PdfPCell(new Paragraph("N.", new Font(Font.TIMES_ROMAN, 7, Font.BOLD)));
	    	table.addCell(numCell);
	    	PdfPCell matriculaCell = new PdfPCell(new Paragraph("MATRICULA", new Font(Font.TIMES_ROMAN, 7, Font.BOLD)));
	    	table.addCell(matriculaCell);
	    	PdfPCell nomeCell = new PdfPCell(new Paragraph("NOME", new Font(Font.TIMES_ROMAN, 7, Font.BOLD)));
	    	table.addCell(nomeCell);
	    	PdfPCell cargoCell = new PdfPCell(new Paragraph("CARGO", new Font(Font.TIMES_ROMAN, 7, Font.BOLD)));
	    	table.addCell(cargoCell);
	    	PdfPCell valorDiurnaCell = new PdfPCell(new Paragraph("VALOR DA HORA DIURNA R$", new Font(Font.TIMES_ROMAN, 7, Font.BOLD)));
	    	table.addCell(valorDiurnaCell);
	    	PdfPCell valorNoturnaCell = new PdfPCell(new Paragraph("VALOR DA HORA NOTURNA R$", new Font(Font.TIMES_ROMAN, 7, Font.BOLD)));
	    	table.addCell(valorNoturnaCell);
	    	PdfPCell horaDiurnaCell = new PdfPCell(new Paragraph("HORAS EXTRAS DIURNAS SOLICITADAS", new Font(Font.TIMES_ROMAN, 7, Font.BOLD)));
	    	table.addCell(horaDiurnaCell);
	    	PdfPCell horaNoturnaCell = new PdfPCell(new Paragraph("HORAS EXTRAS NOTURNAS SOLICITADAS", new Font(Font.TIMES_ROMAN, 7, Font.BOLD)));
	    	table.addCell(horaNoturnaCell);
	    	PdfPCell totalHECell = new PdfPCell(new Paragraph("TOTAL DE HE", new Font(Font.TIMES_ROMAN, 7, Font.BOLD)));
	    	table.addCell(totalHECell);
	    	PdfPCell valorPagoCell = new PdfPCell(new Paragraph("VALOR A SER PAGO R$", new Font(Font.TIMES_ROMAN, 8, Font.BOLD)));
	    	table.addCell(valorPagoCell);
		 
	    	 int cont = 0;
	    		
			    int totalHorasExtras = 0;
			    int totalHorasExtrasNoturno = 0;
			    int totalHorasExtrasDiurno = 0;
			    float totalValorDiurno = 0;
			    float totalValorNoturno = 0;
			    float valorAPagar = 0;
			    
			    
		 	    for (int i = 0; i < servidores.length; i++) {
		 	    	
			    	if (servidores[i].getEscala().isContemHoraExtra()) {
			    		cont += 1;
			    		
			    		float valorDaHoraDiurna = (listasValoresHE[0].get(servidores[i].getMatricula()) == null ? 0 : listasValoresHE[0].get(servidores[i].getMatricula()));
			    		float valorDaHoraNoturna = (listasValoresHE[1].get(servidores[i].getMatricula()) == null ? 0 : listasValoresHE[1].get(servidores[i].getMatricula()));
			    		
			    		table.addCell(new PdfPCell(new Paragraph(cont + "", new Font(Font.TIMES_ROMAN, 7, Font.BOLD))));
			    		table.addCell(new PdfPCell(new Paragraph(servidores[i].getMatricula(), new Font(Font.TIMES_ROMAN, 7, Font.BOLD))));
			    		table.addCell(new PdfPCell(new Paragraph(servidores[i].getNome(), new Font(Font.TIMES_ROMAN, 7, Font.BOLD))));
			    		table.addCell(new PdfPCell(new Paragraph(servidores[i].getCargo(), new Font(Font.TIMES_ROMAN, 7, Font.BOLD))));
			    		table.addCell(new PdfPCell(new Paragraph((listasValoresHE[0].get(servidores[i].getMatricula()) == null ? 0 : (valorDaHoraDiurna + "").replace('.', ','))+"", new Font(Font.TIMES_ROMAN, 7, Font.BOLD))));
			    		table.addCell(new PdfPCell(new Paragraph((listasValoresHE[1].get(servidores[i].getMatricula()) == null ? 0 : (valorDaHoraNoturna + "").replace('.', ','))+"", new Font(Font.TIMES_ROMAN, 7, Font.BOLD))));
			    		table.addCell(new PdfPCell(new Paragraph(servidores[i].getEscala().getTotalHorasExtrasDiurno()+"", new Font(Font.TIMES_ROMAN, 7, Font.BOLD))));
			    		table.addCell(new PdfPCell(new Paragraph(servidores[i].getEscala().getTotalHorasExtrasNoturno()+"", new Font(Font.TIMES_ROMAN, 7, Font.BOLD))));
			    		totalHorasExtras += servidores[i].getEscala().getTotalHorasExtras();
			    		totalHorasExtrasNoturno += servidores[i].getEscala().getTotalHorasExtrasNoturno();
			    		totalHorasExtrasDiurno += servidores[i].getEscala().getTotalHorasExtrasDiurno();
			    		totalValorNoturno += valorDaHoraDiurna;
			    		totalValorDiurno += valorDaHoraNoturna; 
			    		
			    		table.addCell(new PdfPCell(new Paragraph(servidores[i].getEscala().getTotalHorasExtras()+"", new Font(Font.TIMES_ROMAN, 7, Font.BOLD))));
			    		
			    		valorAPagar += AuxiliarGeral.arredonda2Casas((servidores[i].getEscala().getTotalHorasExtrasDiurno()*valorDaHoraDiurna) + (servidores[i].getEscala().getTotalHorasExtrasNoturno()*valorDaHoraNoturna)).floatValue();
			    		
			    		if (listasValoresHE[0].get(servidores[i].getMatricula()) != null & listasValoresHE[1].get(servidores[i].getMatricula()) != null) {
			    			table.addCell(new PdfPCell(new Paragraph("R$"+(AuxiliarGeral.arredonda2Casas((servidores[i].getEscala().getTotalHorasExtrasDiurno()*valorDaHoraDiurna) + (servidores[i].getEscala().getTotalHorasExtrasNoturno()*valorDaHoraNoturna)) + "").replace('.', ','), new Font(Font.TIMES_ROMAN, 7, Font.BOLD))));
			    		}
			    		else {
			    			table.addCell(new PdfPCell(new Paragraph(0+"", new Font(Font.TIMES_ROMAN, 8, Font.BOLD))));
			    		}
			    	}
			    }
		 	    
		 	   table.addCell(new PdfPCell(new Paragraph("", new Font(Font.TIMES_ROMAN, 7, Font.BOLD))));
		 	   table.addCell(new PdfPCell(new Paragraph("TOTAL", new Font(Font.TIMES_ROMAN, 7, Font.BOLD))));
		 	   table.addCell(new PdfPCell(new Paragraph("", new Font(Font.TIMES_ROMAN, 7, Font.BOLD))));
		 	   table.addCell(new PdfPCell(new Paragraph("", new Font(Font.TIMES_ROMAN, 7, Font.BOLD))));
		 	   table.addCell(new PdfPCell(new Paragraph(/*(AuxiliarGeral.arredonda2Casas(totalValorDiurno) + "").replace('.', ',')*/"", new Font(Font.TIMES_ROMAN, 7, Font.BOLD))));
		 	   table.addCell(new PdfPCell(new Paragraph(/*(AuxiliarGeral.arredonda2Casas(totalValorNoturno) + "").replace('.', ',')*/"", new Font(Font.TIMES_ROMAN, 7, Font.BOLD))));
		 	   table.addCell(new PdfPCell(new Paragraph(totalHorasExtrasDiurno+"", new Font(Font.TIMES_ROMAN, 7, Font.BOLD))));
		 	   table.addCell(new PdfPCell(new Paragraph(totalHorasExtrasNoturno+"", new Font(Font.TIMES_ROMAN, 7, Font.BOLD))));
		 	   table.addCell(new PdfPCell(new Paragraph(totalHorasExtras+"", new Font(Font.TIMES_ROMAN, 8, Font.BOLD))));
		 	   table.addCell(new PdfPCell(new Paragraph((AuxiliarGeral.arredonda2Casas(valorAPagar) + "").replace('.', ','), new Font(Font.TIMES_ROMAN, 7, Font.BOLD))));
		    
		    relPDF.add(table);
			
		    geraQuantitativoHorasExtras(relPDF, servidores, mes, ano);
		    
			if (relPDF != null) {
					//fechamento do documento
				relPDF.close();
			}
			if (fos != null) {
					//fechamento da stream de sa�da
				fos.close();
			}
				
	       
			Runtime.getRuntime().exec("rundll32 SHELL32.DLL,ShellExec_RunDLL "+nomeArq);
		}
		catch(DocumentException ex) {
			
			throw new RelatorioException("Erro ao gerar o relatorio de quantidade de refeicoes!");
			
		}
		catch(IOException ex) {
			
			throw new RelatorioException("Erro ao gerar o relatorio de quantidade de refeicoes!");
		}
		
	}
	
	private void geraQuantitativoHorasExtras(Document relPDF, EntidadeServidor servidores[], int mes, int ano) throws DocumentException {
		
		relPDF.newPage();
		
		Paragraph titulo1 = new Paragraph("COMISSAO PERMANENTE DE AVALIACAO CONTROLE E FISCALIZACAO DAS HORAS EXTRAS DA SES-DF", new Font(Font.TIMES_ROMAN, 5, Font.BOLD));
    	titulo1.setSpacingAfter(17);
		relPDF.add(titulo1); 
		
		Paragraph titulo3 = new Paragraph("RELACIONAR OS PROFISSIONAIS QUE DEVEM RECEBER HORAS EXTRAS", new Font(Font.TIMES_ROMAN, 7, Font.BOLD));
		titulo3.setAlignment(Paragraph.ALIGN_CENTER);
		titulo3.setSpacingAfter(17);
		relPDF.add(titulo3); 
        
    	Paragraph titulo4 = new Paragraph("COORDENACAO GERAL DE SAUDE DE TAGUATINGA SETOR: "+ servidores[0].getSetor().trim() + " " + AuxiliarConstantes.MESES[mes-1] + "/" + ano, new Font(Font.TIMES_ROMAN, 7, Font.BOLD));
    	titulo4.setAlignment(Paragraph.ALIGN_CENTER);
    	titulo4.setSpacingAfter(17);
		relPDF.add(titulo4); 	
	    
		PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100.0f);
        table.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
        table.setSpacingAfter(5);
        
        PdfPCell numCell = new PdfPCell(new Paragraph("N.", new Font(Font.TIMES_ROMAN, 7, Font.BOLD)));
        table.addCell(numCell);
    	PdfPCell matriculaCell = new PdfPCell(new Paragraph("MATRICULA", new Font(Font.TIMES_ROMAN, 7, Font.BOLD)));
    	table.addCell(matriculaCell);
    	PdfPCell nomeCell = new PdfPCell(new Paragraph("NOME", new Font(Font.TIMES_ROMAN, 7, Font.BOLD)));
    	table.addCell(nomeCell);
    	PdfPCell horaDiurnaCell = new PdfPCell(new Paragraph("HORAS EXTRAS DIURNAS SOLICITADAS", new Font(Font.TIMES_ROMAN, 7, Font.BOLD)));
    	table.addCell(horaDiurnaCell);
    	PdfPCell horaNoturnaCell = new PdfPCell(new Paragraph("HORAS EXTRAS NOTURNAS SOLICITADAS", new Font(Font.TIMES_ROMAN, 7, Font.BOLD)));
    	table.addCell(horaNoturnaCell);
    	PdfPCell totalHECell = new PdfPCell(new Paragraph("TOTAL DE HE", new Font(Font.TIMES_ROMAN, 7, Font.BOLD)));
    	table.addCell(totalHECell);
	 
    	 int cont = 0;
    		
		    int totalHorasExtras = 0;
		    int totalHorasExtrasNoturno = 0;
		    int totalHorasExtrasDiurno = 0;
		    
		    
	 	    for (int i = 0; i < servidores.length; i++) {
	 	    	
		    	if (servidores[i].getEscala().isContemHoraExtra()) {
		    		cont += 1;
		    		
		    		table.addCell(new PdfPCell(new Paragraph(cont + "", new Font(Font.TIMES_ROMAN, 7, Font.BOLD))));
		    		table.addCell(new PdfPCell(new Paragraph(servidores[i].getMatricula(), new Font(Font.TIMES_ROMAN, 7, Font.BOLD))));
		    		table.addCell(new PdfPCell(new Paragraph(servidores[i].getNome(), new Font(Font.TIMES_ROMAN, 7, Font.BOLD))));
		    		table.addCell(new PdfPCell(new Paragraph(servidores[i].getEscala().getTotalHorasExtrasDiurno()+"", new Font(Font.TIMES_ROMAN, 7, Font.BOLD))));
		    		table.addCell(new PdfPCell(new Paragraph(servidores[i].getEscala().getTotalHorasExtrasNoturno()+"", new Font(Font.TIMES_ROMAN, 7, Font.BOLD))));
		    		totalHorasExtras += servidores[i].getEscala().getTotalHorasExtras();
		    		totalHorasExtrasNoturno += servidores[i].getEscala().getTotalHorasExtrasNoturno();
		    		totalHorasExtrasDiurno += servidores[i].getEscala().getTotalHorasExtrasDiurno();
		    		
		    		table.addCell(new PdfPCell(new Paragraph(servidores[i].getEscala().getTotalHorasExtras()+"", new Font(Font.TIMES_ROMAN, 7, Font.BOLD))));
		    		
		    	}
		    }
	 	    
	 	   table.addCell(new PdfPCell(new Paragraph("", new Font(Font.TIMES_ROMAN, 7, Font.BOLD))));
	 	   table.addCell(new PdfPCell(new Paragraph("TOTAL", new Font(Font.TIMES_ROMAN, 7, Font.BOLD))));
	 	   table.addCell(new PdfPCell(new Paragraph("", new Font(Font.TIMES_ROMAN, 7, Font.BOLD))));
	 	   table.addCell(new PdfPCell(new Paragraph(totalHorasExtrasDiurno+"", new Font(Font.TIMES_ROMAN, 7, Font.BOLD))));
	 	   table.addCell(new PdfPCell(new Paragraph(totalHorasExtrasNoturno+"", new Font(Font.TIMES_ROMAN, 7, Font.BOLD))));
	 	   table.addCell(new PdfPCell(new Paragraph(totalHorasExtras+"", new Font(Font.TIMES_ROMAN, 8, Font.BOLD))));
	    
	    relPDF.add(table);
		
	}

	public String geraRelatorioErros(EntidadeServidor servidores[], ProcessConferidor conferidor, String local) throws RelatorioException {
		
		String nomeArq = AuxiliarGeral.substituiUnderline(local.trim()+"_RELATORIO_DE_ERROS.pdf") ;
		boolean contemErro = false;
		boolean verifica = true;
		File fileArq = new File(nomeArq);   
		Document relPDF = null;
		OutputStream fos = null;
		
		try {
		if (servidores.length > 0) {
		try {
            //cria o documento tamanho A4, margens de 2,54cm
            relPDF = new Document(PageSize.A4, 72, 72, 72, 72);

            //cria a stream de sa�da
            fos = new FileOutputStream(nomeArq);
            
            //associa a stream de sa�da ao 
            PdfWriter pdfWriter = PdfWriter.getInstance(relPDF, fos);
            
            //abre o documento
            relPDF.open();
		
	    	Paragraph titulo = new Paragraph(servidores[0].getSetor().trim() + " " + AuxiliarConstantes.MESES[servidores[0].getEscala().getMes() - 1] + " " + servidores[0].getEscala().getAno() , new Font(Font.TIMES_ROMAN, 15, Font.BOLD));
	    	titulo.setSpacingAfter(17);
			relPDF.add(titulo); 	
		    	
			    for (int i = 0; i < servidores.length; ++i) {

			    	EntidadeMensagem entidadeMensagem = conferidor.getRelatorioErro(servidores[i].getMatricula());
			    	
			    	
			    	if (verifica) {
			    		if (entidadeMensagem.isOk() == false) {
			    			contemErro = true;
			    			verifica = false;
			    		}
			    	}
			    	
			    	if (!entidadeMensagem.isOk()) {
			    		
			    		Font f = new Font(Font.COURIER, 14, Font.BOLD);
				    	Paragraph nomeMatricula = new Paragraph(servidores[i].getNome() + " " + servidores[i].getMatricula(), f);
				    	nomeMatricula.setSpacingAfter(15);
						relPDF.add(nomeMatricula);
			    		int posChr = 0;
			    		while (posChr <  entidadeMensagem.getMensagem().length()) {
			    			String parteMensagem = "";
			    			aqui: for (int j = posChr; j < entidadeMensagem.getMensagem().length(); ++j) {
			    			
			    				if (entidadeMensagem.getMensagem().charAt(j) != '$') 
			    					parteMensagem += entidadeMensagem.getMensagem().charAt(j);
			    				
			    				if (entidadeMensagem.getMensagem().charAt(j) == '$') {
			    				
			    					Paragraph paragrafo = new Paragraph(parteMensagem);
			    					paragrafo.setSpacingAfter(10);
			    					relPDF.add(paragrafo);
			    					posChr = j+1;
			    					break aqui;
			    				}
			    				
			    			}
			    		}
			    		
			    	}
			    	//fos.println();
	
			    }
		    }
		
		finally {
			
			if (relPDF != null) {
				//fechamento do documento
				relPDF.close();
			}
			if (fos != null) {
				//fechamento da stream de saida
				fos.close();
			}
			
        }
		
	
		    File novoFile = new File(AuxiliarGeral.substituiEspacos(nomeArq + (contemErro ? "_" + servidores[0].getEscala().getMes() + "_" + servidores[0].getEscala().getAno() + "_" + "_CONTEM_ERRO.pdf"  : "_" + servidores[0].getEscala().getMes() + "_" + servidores[0].getEscala().getAno() + "_" + "_ESTA_OK.pdf")));
		    fileArq.renameTo(novoFile);
		    nomeArq = novoFile.getName();
		    Runtime.getRuntime().exec("rundll32 SHELL32.DLL,ShellExec_RunDLL "+novoFile.getName());    
		}
		}
		catch(DocumentException ex) {
			
			throw new RelatorioException("Erro ao gerar o relatorio de erros!");
			
		}
		catch(IOException ex) {
			
			throw new RelatorioException("Erro ao gerar o relatorio de erros!");
		}
		
		return nomeArq;
	}
	
	public void geraQuantitativoPorPeriodo(EntidadeServidor servidores[], String local, int mes, int ano) throws RelatorioException {
		
		try {
		if (servidores.length > 0) {
			
			String nomeArq = AuxiliarGeral.substituiUnderline(servidores[0].getSetor().trim()) + "_" + servidores[0].getEscala().getMes() + "_" + servidores[0].getEscala().getAno() +"_RASCUNHO_DE_ESCALA.pdf";
			Document relPDF = null;
			OutputStream fos = null;
		
		try {		
	            relPDF = new Document(PageSize.A4.rotate(), 10, 10, 10, 10);
	            fos = new FileOutputStream(nomeArq);
	            PdfWriter.getInstance(relPDF, fos);
	            relPDF.open();
	           
	            
	            PdfPCell branco = new PdfPCell(new Paragraph(""));
	            branco.setBorderColor(BaseColor.WHITE);
	            
	            PdfPTable table = new PdfPTable(1);
	            table.setWidthPercentage(100.0f);
	            table.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
	            table.setSpacingAfter(1);
	            
	            ProcessCalculo processCalculo = new ProcessCalculo();
	            int qmtn[][] = processCalculo.getQuantitativo(servidores, mes, ano);
	            
	            PdfPTable tableQuantitativo = new PdfPTable(AuxiliarConstantes.getUltimoDiaMes(mes, ano) + 1);
                tableQuantitativo.setWidthPercentage(100.0f);
                
                PdfPCell qTitulo = new PdfPCell(new Paragraph("QUANTITATIVO MANHA, TARDE E NOITE", new Font(Font.TIMES_ROMAN, 11, Font.BOLD)));
	            qTitulo.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	            qTitulo.setColspan(AuxiliarConstantes.getUltimoDiaMes(mes, ano) + 1);
	            tableQuantitativo.addCell(qTitulo);
	        
	            tableQuantitativo.addCell("");
                for (int i = 0; i < AuxiliarConstantes.getUltimoDiaMes(mes, ano); ++i) {
                
                	PdfPCell cellDiaMes = new PdfPCell(new Paragraph("" + (i+1), new Font(Font.TIMES_ROMAN, 8, Font.BOLD)));
                	tableQuantitativo.addCell(cellDiaMes);
                }
                
            
                tableQuantitativo.addCell("");
                for (int i = 0; i < AuxiliarConstantes.getUltimoDiaMes(mes, ano); ++i) {
                	PdfPCell cellDiaMes = new PdfPCell(new Paragraph(AuxiliarConstantes.getDiaSemanaData(i + 1, mes, ano), new Font(Font.TIMES_ROMAN, 8, Font.BOLD)));
                	
                	if (AuxiliarConstantes.getDiaSemanaData(i + 1, mes, ano).equals(AuxiliarConstantes.SABADO) | AuxiliarConstantes.getDiaSemanaData(i + 1, mes, ano).equals(AuxiliarConstantes.DOMINGO)) {
                		cellDiaMes.setBackgroundColor(BaseColor.GRAY);
                	}
                	
                	tableQuantitativo.addCell(cellDiaMes);
                }
                
            	PdfPCell cellManha = new PdfPCell(new Paragraph("M", new Font(Font.TIMES_ROMAN, 8, Font.BOLD)));
            	tableQuantitativo.addCell(cellManha);
                for (int i = 0; i < AuxiliarConstantes.getUltimoDiaMes(mes, ano); ++i) {
                	PdfPCell cellDiaMes = new PdfPCell(new Paragraph(qmtn[0][i] + "", new Font(Font.TIMES_ROMAN, 8, Font.BOLD)));
                	tableQuantitativo.addCell(cellDiaMes);
                }
                PdfPCell cellTarde = new PdfPCell(new Paragraph("T", new Font(Font.TIMES_ROMAN, 8, Font.BOLD)));
            	tableQuantitativo.addCell(cellTarde);
                for (int i = 0; i < AuxiliarConstantes.getUltimoDiaMes(mes, ano); ++i) {
                	PdfPCell cellDiaMes = new PdfPCell(new Paragraph(qmtn[1][i] + "", new Font(Font.TIMES_ROMAN, 8, Font.BOLD)));
                	tableQuantitativo.addCell(cellDiaMes);
                }
                PdfPCell cellNoite = new PdfPCell(new Paragraph("N", new Font(Font.TIMES_ROMAN, 8, Font.BOLD)));
            	tableQuantitativo.addCell(cellNoite);
                for (int i = 0; i < AuxiliarConstantes.getUltimoDiaMes(mes, ano); ++i) {
                	PdfPCell cellDiaMes = new PdfPCell(new Paragraph(qmtn[2][i] + "", new Font(Font.TIMES_ROMAN, 8, Font.BOLD)));
                	tableQuantitativo.addCell(cellDiaMes);
                }
	            
                tableQuantitativo.setSpacingAfter(2);
                relPDF.add(tableQuantitativo);
			}
			finally {
				if (relPDF != null) {
					relPDF.close();
				}
				if (fos != null) {		
					fos.close();
				}
	        }
			
			Runtime.getRuntime().exec("rundll32 SHELL32.DLL,ShellExec_RunDLL "+nomeArq);  
		}
		
		}
		catch(DocumentException ex) {
			
			throw new RelatorioException("Erro ao gerar o rascunho de escala!");
			
		}
		catch(IOException ex) {
			
			throw new RelatorioException("Erro ao gerar o rascunho de escala!");
		}
		
	}
	
	public void geraRascunho(EntidadeServidor servidores[], String local, int mes, int ano) throws RelatorioException {
		 
		if (servidores.length > 0) {
				
			String nomeArq = AuxiliarGeral.substituiUnderline(servidores[0].getSetor().trim()) + "_" + servidores[0].getEscala().getMes() + "_" + servidores[0].getEscala().getAno() +"_RASCUNHO_DE_ESCALA.pdf";
			Document relPDF = null;
			OutputStream fos = null;
            ProcessCalculo processCalculo = new ProcessCalculo();

            try {
			try {
				
	            relPDF = new Document(PageSize.A4.rotate(), 10, 10, 10, 10);
	            fos = new FileOutputStream(nomeArq);
	            PdfWriter.getInstance(relPDF, fos);
	            relPDF.open();
	           
	            
	            PdfPCell branco = new PdfPCell(new Paragraph(""));
	            branco.setBorderColor(BaseColor.WHITE);
	            
	            PdfPTable table = new PdfPTable(1);
	            table.setWidthPercentage(100.0f);
	            table.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
	            table.setSpacingAfter(1);
                
	            int totalHorasExtras = processCalculo.getTotalHorasExtras(servidores);
	        
	            PdfPCell header = new PdfPCell(new Paragraph(servidores[0].getSetor().trim() + " RASCUNHO DE ESCALA " + AuxiliarConstantes.MESES[servidores[0].getEscala().getMes() - 1] + " " + servidores[0].getEscala().getAno() + " | TOTAL EXTRAS: " + totalHorasExtras, new Font(Font.TIMES_ROMAN, 12, Font.BOLD)));
	            header.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	            table.addCell(header);
                
	            relPDF.add(table);
	           
	            
	            for (int k = 0; k < servidores.length; ++k) {
	            
	            	if (k % 8 == 0 & k > 0) {
		 	            relPDF.newPage();
		 	        }
	            	
	            	PdfPTable tableNome = new PdfPTable(1);
	                tableNome.setWidthPercentage(100.0f);
	         
	            	PdfPCell cellNome = new PdfPCell(new Paragraph((!servidores[k].getEscala().isEscalaLancada() ? "CONTEM ERRO " : "") + servidores[k].getNome().trim() + "| " + servidores[k].getMatricula() + " | THE: " + servidores[k].getEscala().getTotalHorasExtras() + " | RD: " + servidores[k].getEscala().getQuantidadeRefeicoesDiurno() + " | RN: " + servidores[k].getEscala().getQuantidadeRefeicoesNoturno() + " | THED: " + servidores[k].getEscala().getTotalHorasExtrasDiurno() + " | THEN: " + servidores[k].getEscala().getTotalHorasExtrasNoturno() + " | S1: " + servidores[k].getEscala().getHorasSemana1() + " | S2: " + servidores[k].getEscala().getHorasSemana2() + " | S3: " + servidores[k].getEscala().getHorasSemana3() + " | S4: " + servidores[k].getEscala().getHorasSemana4() + " | S5: " + servidores[k].getEscala().getHorasSemana5() + " | S6: " + servidores[k].getEscala().getHorasSemana6() + " | BANCO DE HORAS: " + servidores[k].getEscala().getBancoHorasDestaEscala(), new Font(Font.TIMES_ROMAN, 8, Font.BOLD)));
	            	if (!servidores[k].getEscala().isEscalaLancada()) {
	            		cellNome.setBackgroundColor(BaseColor.RED);
	            	}
	            		
	            	tableNome.addCell(cellNome);
	            	
	            	relPDF.add(tableNome);
	            	
	            	PdfPTable tableEscala = new PdfPTable(AuxiliarConstantes.getUltimoDiaMes(servidores[k].getEscala().getMes(), servidores[k].getEscala().getAno()));
	                tableEscala.setWidthPercentage(100.0f);
	                for (int i = 0; i < AuxiliarConstantes.getUltimoDiaMes(servidores[k].getEscala().getMes(), servidores[k].getEscala().getAno()); ++i) {
	                	PdfPCell cellDiaMes = new PdfPCell(new Paragraph("" + (i+1), new Font(Font.TIMES_ROMAN, 8, Font.BOLD)));
	                	tableEscala.addCell(cellDiaMes);
	                }
	                
	                for (int i = 0; i < AuxiliarConstantes.getUltimoDiaMes(servidores[k].getEscala().getMes(), servidores[k].getEscala().getAno()); ++i) {
	                	PdfPCell cellDiaSemana = new PdfPCell(new Paragraph(AuxiliarConstantes.getDiaSemanaData((i+1), servidores[k].getEscala().getMes(), servidores[k].getEscala().getAno()), new Font(Font.TIMES_ROMAN, 8, Font.BOLD)));
	                	
	                	if (AuxiliarConstantes.getDiaSemanaData(i + 1, mes, ano).equals(AuxiliarConstantes.SABADO) | AuxiliarConstantes.getDiaSemanaData(i + 1, mes, ano).equals(AuxiliarConstantes.DOMINGO)) {
	                		cellDiaSemana.setBackgroundColor(BaseColor.GRAY);
	                	}
	                	
	                	tableEscala.addCell(cellDiaSemana);
	                	
	                }
	                
	                for (int i = 0; i < AuxiliarConstantes.getUltimoDiaMes(servidores[k].getEscala().getMes(), servidores[k].getEscala().getAno()); ++i) {
	                	PdfPCell cellEscalaC = new PdfPCell(new Paragraph(servidores[k].getEscala().getEscalaHoraContratual()[i], new Font(Font.TIMES_ROMAN, 8, Font.BOLD)));
	                	
	                	if (AuxiliarConstantes.getLegenda(servidores[k].getEscala().getEscalaHoraContratual()[i]).isAfastamento()) {
	                		cellEscalaC.setBackgroundColor(BaseColor.YELLOW);
	                	}
	                	
	                	tableEscala.addCell(cellEscalaC);
	                	
	                }
	                
	                if (servidores[k].getEscala().getTotalHorasExtras() > 0) {
		                for (int i = 0; i < AuxiliarConstantes.getUltimoDiaMes(servidores[k].getEscala().getMes(), servidores[k].getEscala().getAno()); ++i) {

		                	PdfPCell cellEscalaE = new PdfPCell(new Paragraph(servidores[k].getEscala().getEscalaHoraExtra()[i], new Font(Font.TIMES_ROMAN, 8, Font.BOLD)));
		                	tableEscala.addCell(cellEscalaE);
		                	
		                }
	                }
	                
	            	PdfPCell cellObservacao = new PdfPCell(new Paragraph(servidores[k].getEscala().getObservacao(), new Font(Font.TIMES_ROMAN, 8, Font.BOLD)));
	 	            cellObservacao.setColspan(AuxiliarConstantes.getUltimoDiaMes(servidores[k].getEscala().getMes(), servidores[k].getEscala().getAno()));
	 	            tableEscala.addCell(cellObservacao);
	 	            tableEscala.setSpacingAfter(1);

	 	            tableEscala.setSpacingAfter(4);
	 	            relPDF.add(tableEscala);
	 	            
	 	            
	            }

			 }
			finally {
				if (relPDF != null) {
					relPDF.close();
				}
				if (fos != null) {		
					fos.close();
				}
	        }
			
			Runtime.getRuntime().exec("rundll32 SHELL32.DLL,ShellExec_RunDLL "+nomeArq);  
        	}
    		catch(DocumentException ex) {
    			
    			throw new RelatorioException("Erro ao gerar o rascunho de escala!");
    			
    		}
    		catch(IOException ex) {
    			
    			throw new RelatorioException("Erro ao gerar o rascunho de escala!");
    		}
            }
            
	}
}
