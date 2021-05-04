package Controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Bean.O_ATUALIZAVELOZ;
import Bean.STRTITACORDVELOZ;
import DAO.DSconexao;

public class TituloController {

	public static O_ATUALIZAVELOZ enviosFiebTitulos(String filial) throws SQLException {
		O_ATUALIZAVELOZ atualiza = new O_ATUALIZAVELOZ();

		atualiza.setE1_TITULOS(consulta_So_Titulos(filial));

		return atualiza;
	}
	
	
	
	/*
	 * Apesar de estar usando a mesma estrutura dos titulos do acordos só e enviado os titulos
	 * com o status 9 ou 13
	 */
	
	
	public static List<STRTITACORDVELOZ> consulta_So_Titulos(String filial) throws SQLException {
		DSconexao data = new DSconexao();

		String query = "SELECT ";
		query += " CPFTIT,ACOTIT,NUMTIT,BCOTIT, AMOCLI,'S',PROCLI,ADMCLI  FROM EJCBTIT";
		query += " left join ejcbCLI on CONCLI = CONTIT AND CRTCLI = CRTTIT AND CRECLI = CRETIT";
		query += " WHERE CRETIT = 53 AND DTSTIT Between '01/07/2021' and '01/07/2021' AND STATIT <> '' ";
		query += " AND BCOTIT in( '9','12','13') AND SUBSTRING(ADMCLI from 1 for 4) = '" + filial+"'";
		query += " ORDER BY CONTIT,NUMTIT,DTVTIT,CRTTIT,CRETIT";
		PreparedStatement ps = data.getConnection().prepareStatement(query);

		ResultSet result = ps.executeQuery();
		ArrayList<STRTITACORDVELOZ> titulos = new ArrayList<STRTITACORDVELOZ>();
		while (result.next()) {
			STRTITACORDVELOZ titulo = new STRTITACORDVELOZ();
			titulo.setE1_FILIAL(result.getString("ADMCLI"));
			titulo.setA1_CGC(result.getString("CPFTIT"));			
			titulo.setE1_FSACORD("");
			String[] numParc = result.getString("NUMTIT").split("-");
			String numero = numParc[0];
			String parcela = "";
			if (numParc.length > 1) {
				parcela = numParc[1];
			}
			titulo.setE1_NUM(numero);
			titulo.setE1_PARCELA(parcela);
			titulo.setE1_PREFIXO(result.getString("AMOCLI"));
			titulo.setE1_STATUS(result.getString("BCOTIT")); 
			titulo.setE1_TIPO(result.getString("PROCLI"));

			titulos.add(titulo);
		}

		ps.close();
		data.getConnection().close();
		return titulos;
	}
	
}
