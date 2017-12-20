
public enum Navios {
	
	PORTA1(1.1), PORTA2(1.2), PORTA3(1.3), PORTA4(1.4), 
	SUBMARINO1(2.1), SUBMARINO2(2.2),
	NAVIO1(3.1), NAVIO2(3.2), NAVIO3(3.3),
	AVIAO1(4.1), AVIAO2(4.2);
	
	double valor;
	
	Navios(double v){
		this.valor = v;
	}
}
