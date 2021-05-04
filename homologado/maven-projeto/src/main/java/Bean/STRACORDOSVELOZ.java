package Bean;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class STRACORDOSVELOZ {

	private String Z06_ACORDO;
	private String Z06_DATA;
	private String Z06_TPPGTO;
	private Float  Z06_VACORD;
	private Float  Z06_VALOR;
	private String Z06_VENCTO;
	private String Z06_FILIAL;
	private String FILIAL;

	public String getZ06_ACORDO() {
		return Z06_ACORDO;
	}
	public void setZ06_ACORDO(String z06_ACORDO) {
		Z06_ACORDO = z06_ACORDO;
	}
	public String getZ06_DATA() {
		return Z06_DATA;
	}
	public void setZ06_DATA(String z06_DATA) {
		Z06_DATA = z06_DATA;
	}
	public String getZ06_TPPGTO() {
		return Z06_TPPGTO;
	}
	public void setZ06_TPPGTO(String z06_TPPGTO) {
		Z06_TPPGTO = z06_TPPGTO;
	}
	public Float getZ06_VACORD() {
		return Z06_VACORD;
	}
	public void setZ06_VACORD(Float z06_VACORD) {
		Z06_VACORD = z06_VACORD;
	}
	public Float getZ06_VALOR() {
		return Z06_VALOR;
	}
	public void setZ06_VALOR(Float z06_VALOR) {
		Z06_VALOR = z06_VALOR;
	}
	public String getZ06_VENCTO() {
		return Z06_VENCTO;
	}
	public void setZ06_VENCTO(String z06_VENCTO) {
		Z06_VENCTO = z06_VENCTO;
	}
	public String getFILIAL() {
		return FILIAL;
	}
	public void setFILIAL(String fILIAL) {
		FILIAL = fILIAL;
	}
	public String getZ06_FILIAL() {
		return Z06_FILIAL;
	}
	public void setZ06_FILIAL(String z06_FILIAL) {
		Z06_FILIAL = z06_FILIAL;
	}
	
}



