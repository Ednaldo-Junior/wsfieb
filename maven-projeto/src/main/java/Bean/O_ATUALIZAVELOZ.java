package Bean;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class O_ATUALIZAVELOZ {
	
	private String A1_CGC = "";
	private List<STRACORDOSVELOZ> E1_ACORDOS = new ArrayList<STRACORDOSVELOZ>();
	private String E1_DESCST = "";
	private String E1_NUM = "";
	private String E1_PARCELA = "";
	private String E1_PREFIXO = "";
	private String E1_STATUS = "";
	private String E1_TIPO = "";
	public String getA1_CGC() {
		return A1_CGC;
	}
	public void setA1_CGC(String a1_CGC) {
		A1_CGC = a1_CGC;
	}
	public List<STRACORDOSVELOZ> getE1_ACORDOS() {
		return E1_ACORDOS;
	}
	public void setE1_ACORDOS(List<STRACORDOSVELOZ> e1_ACORDOS) {
		E1_ACORDOS = e1_ACORDOS;
	}
	public String getE1_DESCST() {
		return E1_DESCST;
	}
	public void setE1_DESCST(String e1_DESCST) {
		E1_DESCST = e1_DESCST;
	}
	public String getE1_NUM() {
		return E1_NUM;
	}
	public void setE1_NUM(String e1_NUM) {
		E1_NUM = e1_NUM;
	}
	public String getE1_PARCELA() {
		return E1_PARCELA;
	}
	public void setE1_PARCELA(String e1_PARCELA) {
		E1_PARCELA = e1_PARCELA;
	}
	public String getE1_PREFIXO() {
		return E1_PREFIXO;
	}
	public void setE1_PREFIXO(String e1_PREFIXO) {
		E1_PREFIXO = e1_PREFIXO;
	}
	public String getE1_STATUS() {
		return E1_STATUS;
	}
	public void setE1_STATUS(String e1_STATUS) {
		E1_STATUS = e1_STATUS;
	}
	public String getE1_TIPO() {
		return E1_TIPO;
	}
	public void setE1_TIPO(String e1_TIPO) {
		E1_TIPO = e1_TIPO;
	}
}

