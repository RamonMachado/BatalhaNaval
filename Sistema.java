public class Sistema {
	
	TelaMenu window;
	TelaJogo jogo;
	TelaEscolher escolher;
	
	//Variaveis para uso do Sistema, algumas classes possuem uma instância da classe sistema
	//e conseguem utilizar esses valores
	public int janelaAltura = 590;
	public int janelaLargura = 980;
	public int tamanhoBotao = 40;
	public int tamanhoTabela = 10;
	public String tituloJogo = "Batalha Naval";
	public int botaoJogadorX = 40;
	public int botaoJogadorY = 40;
	public int posicaoJogadorX = 0;
	public int posicaoJogadorY = 0;
	public int posicaoIAX = 500;
	public int posicaoIAY = 0;
	
	public static void main(String[] args) {
		Sistema sistema = new Sistema();
		sistema.inicializarMenu();
	}
	
	//Método esconde as janelas que estão visíveis.
	
	//Usando essa ideia, teremos apenas uma tela de cada carregada por vez na memória principal.
	//Assim que uma nova for criada sendo referenciada por uma variavel, a outra janela do mesmo tipo
	//será liberada pelo garbage collector
	
	//Mas ainda assim, não é a maneira mais eficiente de ser criar janelas.
	//Seria melhor criar um JFrame e apenas ir trocando o JPanel daquele JFrame.
	public void esconderJanelas(){
		if(window != null)
			window.getFrame().setVisible(false);
		if(jogo != null)
			jogo.getFrame().setVisible(false);
		if(escolher != null)
			escolher.getFrame().setVisible(false);
	}
	
	public void inicializarMenu(){
		this.esconderJanelas();
		window = new TelaMenu(this);
	}
	
	public void inicializarJogo(double tabelaJogador[][], double tabelaIA[][]){
		this.esconderJanelas();
		jogo = new TelaJogo(this, tabelaJogador, tabelaIA);
	}
	
	public void inicializarEscolher(boolean aleatorio){
		this.esconderJanelas();
		escolher = new TelaEscolher(this, aleatorio);
	}
	
}
