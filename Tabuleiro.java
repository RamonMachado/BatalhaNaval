import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Tabuleiro {
	
	TelaJogo telaJogo;
	private JButton botoes[][];
	private JLabel letras[];
	private JLabel numeros[];
	private double tabela[][];
	private double mapa[][];
	private boolean jogador;
	private int posicaoX, posicaoY;
	private int tamanho, tamanhoBotao;
	private int navio = 3;
	private int submarino = 2;
	private int porta = 4;
	private int aviao = 2;
	private Disparos disparo;
	
	public Tabuleiro(TelaJogo tela, boolean jogador, int tamanho, double tabela[][], int pX, int pY, int tamanhoBotao){
		this.telaJogo = tela;
		this.jogador = jogador;
		this.tabela = tabela;
		this.posicaoX = pX;
		this.posicaoY = pY;
		this.tamanho = tamanho;
		this.tamanhoBotao = tamanhoBotao;
		botoes = new JButton[tamanho][tamanho];
		mapa = new double[tamanho][tamanho];
		letras = new JLabel[tamanho];
		numeros = new JLabel[tamanho];
		this.criarBotoes();
		this.criarLetrasNumeros();
		this.disparo = Disparos.COMUM;
	}
	
	//Método criar as JLabel de letras e números do tabuleiro
	public void criarLetrasNumeros(){
		
		int i = 1;
		char a = 'A';
		
		for(i = 1; i <= tamanho; i++){
			if(i == 1){
				numeros[i-1] = new JLabel("1");
				letras[i-1] = new JLabel("A");
			}
			else{
				a++;
				numeros[i-1] = new JLabel(Integer.toString(i));
				letras[i-1] = new JLabel(Character.toString(a));
			}
			numeros[i-1].setFont(new Font("Dialog", Font.BOLD, 20));
			letras[i-1].setFont(new Font("Dialog", Font.BOLD, 20));
			numeros[i-1].setHorizontalAlignment(SwingConstants.CENTER);
			letras[i-1].setHorizontalAlignment(SwingConstants.CENTER);
			numeros[i-1].setBounds(posicaoX+(i*tamanhoBotao), posicaoY, tamanhoBotao, tamanhoBotao);
			letras[i-1].setBounds(posicaoX, posicaoY+(i*tamanhoBotao), tamanhoBotao, tamanhoBotao);
		}
	}
	
	//Método cria a matriz de botões do tabuleiro
	public void criarBotoes(){
		int i, j;
		for(i = 0; i < tamanho; i++){
			for(j = 0; j < tamanho; j++){
				
				//Caso o botão seja água
				if(tabela[i][j] == 0){
					//Caso o botão seja da tabela de um jogador
					if(jogador){
						ImageIcon icon = new ImageIcon(Tabuleiro.class.getResource("sprites/mar.png"));
						botoes[i][j] = new JButton(icon);
						icon = new ImageIcon(Tabuleiro.class.getResource("sprites/marx.png"));
						botoes[i][j].setDisabledIcon(icon);
						botoes[i][j].addActionListener(new ActionListener(){
							   public void actionPerformed(ActionEvent e){
								  //Nada ainda
							   }
							});
						}
					//Caso o botão seja da tabela da IA
					else{
						ImageIcon icon = new ImageIcon(Tabuleiro.class.getResource("sprites/cinza.png"));
						botoes[i][j] = new JButton(icon);
						icon = new ImageIcon(Tabuleiro.class.getResource("sprites/mar.png"));
						botoes[i][j].setDisabledIcon(icon);
						int x = i, y = j;
						Tabuleiro tab = this;
						Tabuleiro tabAtack = telaJogo.getTabJogador();
						botoes[i][j].addActionListener(new ActionListener(){
							   public void actionPerformed(ActionEvent e){
								   telaJogo.realizarTiro(tab, tabAtack, x, y);
								   if(!telaJogo.getTerminou())
									   telaJogo.jogadaIA();
								   disableButtons();
							   }
							});
					}
				}
				//Caso o botão seja um navio
				else{
					//Caso o botão seja da tabela de um jogador
					if(jogador){
						ImageIcon icon = new ImageIcon(Tabuleiro.class.getResource("sprites/" + tabela[i][j] + ".png"));
						botoes[i][j] = new JButton(icon);
						icon = new ImageIcon(Tabuleiro.class.getResource("sprites/" + tabela[i][j] + "x.png"));
						botoes[i][j].setDisabledIcon(icon);
						int x = i, y = j;
						botoes[i][j].addActionListener(new ActionListener(){
							   public void actionPerformed(ActionEvent e){
								   
							   }
							});
					}
					//Caso o botão seja da tabela da IA
					else{
						ImageIcon icon = new ImageIcon(Tabuleiro.class.getResource("sprites/cinza.png"));
						botoes[i][j] = new JButton(icon);
						icon = new ImageIcon(Tabuleiro.class.getResource("sprites/" + tabela[i][j] + ".png"));	
						botoes[i][j].setDisabledIcon(icon);
						int x = i, y = j;
						Tabuleiro tab = this;
						Tabuleiro tabAtack = telaJogo.getTabJogador();
						botoes[i][j].addActionListener(new ActionListener(){
							   public void actionPerformed(ActionEvent e){
								   telaJogo.realizarTiro(tab, tabAtack, x, y);
								   if(!telaJogo.getTerminou())
									  telaJogo.jogadaIA();
								   disableButtons();
							   }
							});
					}
				}
				botoes[i][j].setBounds(posicaoX+(tamanhoBotao*(i+1)), posicaoY+(tamanhoBotao*(j+1)), tamanhoBotao, tamanhoBotao);
				
				
				
			}
		}
	}
	
	//Verifica se ainda há avião no tabuleiro
	public boolean checkAviao(){
		if(aviao == 0)
			return false;
		else
			return true;
	}
	
	//Verifica se ainda há navio no tabuleiro
	public boolean checkNavio(){
		if(navio == 0)
			return false;
		else
			return true;
	}

	//Verifica se ainda há submarino no tabuleiro
	public boolean checkSubmarino(){
		if(submarino == 0)
			return false;
		else
			return true;
	}

	//Verifica se ainda há porta-avião no tabuleiro
	public boolean checkPorta(){
		if(porta == 0)
			return false;
		else
			return true;
	}
	
	//Método verifica se o tipo de tiro ainda está disponível, e desabilita o botão caso esteja
	public void disableButtons(){
		if(!telaJogo.getTabJogador().checkAviao()){
			telaJogo.botaoEstrela.setEnabled(false);
			this.setDisparoChange();
		}
		if(!telaJogo.getTabJogador().checkNavio()){
			telaJogo.botaoRajada.setEnabled(false);
			this.setDisparoChange();
		}
		if(!telaJogo.getTabJogador().checkSubmarino()){
			telaJogo.botaoComum.setEnabled(false);
			this.setDisparoChange();
		}
	}
	
	//Método que procura o tipo de disparo com maior alcance disponível
	public void setDisparoChange(){
		if(telaJogo.getTabJogador().checkAviao())
			telaJogo.getTabJogador().setDisparo(Disparos.ESTRELA);
		else if(telaJogo.getTabJogador().checkNavio())
			telaJogo.getTabJogador().setDisparo(Disparos.RAJADA);
		else if(telaJogo.getTabJogador().checkSubmarino())
			telaJogo.getTabJogador().setDisparo(Disparos.COMUM);
		else
			telaJogo.getTabJogador().setDisparo(null);
	}
	
	//Método retorna verdade caso o jogador tenha sido derrotado
	public boolean checkDerrota(){
		if(!checkAviao() && !checkNavio() && !checkSubmarino()){
			return true;
		}
		else
			return false;
	}
	
	//Método que recebe a posicao do acerto e verifica se acetou algum navio
	public void setAcerto(int x, int y){
		if(x >= 0 && x < tamanho && y >=0 && y < tamanho){
			if(mapa[x][y] == 0){
				mapa[x][y] = 1;
				if(tabela[x][y] > 1 && tabela[x][y] < 2){
					this.porta--;
				}
				if(tabela[x][y] > 2 && tabela[x][y] < 3){
					this.submarino--;
				}
				if(tabela[x][y] > 3 && tabela[x][y] < 4){
					this.navio--;
				}
				if(tabela[x][y] > 4 && tabela[x][y] < 5){
					this.aviao--;
				}
				this.botoes[x][y].setEnabled(false);
			}
		}
	}

	//Get and Set a partir daqui
	public JButton[][] getBotoes() {
		return botoes;
	}
	public void setBotoes(JButton[][] botoes) {
		this.botoes = botoes;
	}
	public double[][] getTabela() {
		return tabela;
	}
	public void setTabela(double[][] tabela) {
		this.tabela = tabela;
	}
	public double[][] getMapa() {
		return mapa;
	}
	public void setMapa(double[][] mapa) {
		this.mapa = mapa;
	}

	public boolean isJogador() {
		return jogador;
	}

	public void setJogador(boolean jogador) {
		this.jogador = jogador;
	}
	
	public boolean getJogador() {
		return jogador;
	}

	public int getNavio() {
		return navio;
	}

	public void setNavio(int navio) {
		this.navio = navio;
	}

	public int getSubmarino() {
		return submarino;
	}

	public void setSubmarino(int submarino) {
		this.submarino = submarino;
	}

	public int getPorta() {
		return porta;
	}

	public void setPorta(int porta) {
		this.porta = porta;
	}

	public int getAviao() {
		return aviao;
	}

	public void setAviao(int aviao) {
		this.aviao = aviao;
	}

	public Disparos getDisparo() {
		return disparo;
	}

	public void setDisparo(Disparos disparo) {
		this.disparo = disparo;
	}

	public JLabel[] getLetras() {
		return letras;
	}

	public void setLetras(JLabel[] letras) {
		this.letras = letras;
	}

	public JLabel[] getNumeros() {
		return numeros;
	}

	public void setNumeros(JLabel[] numeros) {
		this.numeros = numeros;
	}
	
}
