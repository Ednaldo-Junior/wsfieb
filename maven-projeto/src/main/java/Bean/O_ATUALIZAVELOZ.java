package Bean;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class O_ATUALIZAVELOZ {

	private List<STRACORDOSVELOZ> E1_ACORDOS = new ArrayList<STRACORDOSVELOZ>();
	private List<STRTITACORDVELOZ> E1_TITULOS = new ArrayList<STRTITACORDVELOZ>();

	public List<STRACORDOSVELOZ> getE1_ACORDOS() {
		return E1_ACORDOS;
	}

	public void setE1_ACORDOS(List<STRACORDOSVELOZ> e1_ACORDOS) {
		E1_ACORDOS = e1_ACORDOS;
	}

	public List<STRTITACORDVELOZ> getE1_TITULOS() {
		return E1_TITULOS;
	}

	public void setE1_TITULOS(List<STRTITACORDVELOZ> e1_TITULOS) {
		E1_TITULOS = e1_TITULOS;
	}

}
