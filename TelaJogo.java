import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class TelaJogo {
	
	private Sistema sistema;
	private JFrame frame;
	JButton botaoJogoSair, botaoJogoReiniciar, botaoJogoDica, botaoComum, botaoEstrela, botaoRajada;
	JLabel textoLetra, textoNumero, textoDicas;
	JTextArea entradaX, entradaY;
	private double tabelaJogador[][];
	private double tabelaIA[][];
	private Tabuleiro tabJogador, tabIA;
	private boolean terminou = false;
	private int dica = 3;
	
	public TelaJogo(Sistema s, double tabelaJogador[][], double tabelaIA[][]){
		this.sistema = s;
		this.tabelaJogador = tabelaJogador;
		this.tabelaIA = tabelaIA;
		
		this.inicializar();
	}
	
	private void inicializar() {
		this.criarJogo();
	}
	
	public void criarJogo(){
		
		//Cria a tela
		frame = new JFrame(sistema.tituloJogo + " - Jogo");
		frame.setSize(sistema.janelaLargura, sistema.janelaAltura);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
		frame.setResizable(false);
		
		//Cria os tabuleiros da tela
		tabJogador = new Tabuleiro(this, true, sistema.tamanhoTabela, tabelaJogador, sistema.posicaoJogadorX, sistema.posicaoJogadorY, sistema.tamanhoBotao);
		tabIA = new Tabuleiro(this, false, sistema.tamanhoTabela, tabelaIA, sistema.posicaoIAX, sistema.posicaoIAY, sistema.tamanhoBotao);
		
		//Adiciona os botões dos tabuleiros na tela
		for(int i = 0; i < sistema.tamanhoTabela; i++){
			for(int j = 0; j < sistema.tamanhoTabela; j++){
				frame.getContentPane().add(tabJogador.getBotoes()[i][j]);
				frame.getContentPane().add(tabIA.getBotoes()[i][j]);
			}
		}
		
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(308, 460, 2, 100);
		frame.getContentPane().add(separator);
		
		JSeparator separator2 = new JSeparator();
		separator2.setOrientation(SwingConstants.HORIZONTAL);
		separator2.setBounds(40, 450, 900, 2);
		frame.getContentPane().add(separator2);
		
		JSeparator separator3 = new JSeparator();
		separator3.setOrientation(SwingConstants.VERTICAL);
		separator3.setBounds(672, 460, 2, 100);
		frame.getContentPane().add(separator3);
		
		textoLetra = new JLabel("Letra:");
		textoLetra.setBounds(355, 495, 70, 25);
		frame.getContentPane().add(textoLetra);
		
		textoNumero = new JLabel("Número:");
		textoNumero.setBounds(355, 460, 70, 25);
		frame.getContentPane().add(textoNumero);
		
		entradaX = new JTextArea();
		entradaX.setEditable(true);
		entradaX.setBounds(425, 460, 200, 25);
		entradaX.setBorder(BorderFactory.createLineBorder(Color.black));
		frame.getContentPane().add(entradaX);
		
		entradaY = new JTextArea();
		entradaY.setEditable(true);
		entradaY.setBounds(425, 495, 200, 25);
		entradaY.setBorder(BorderFactory.createLineBorder(Color.black));
		frame.getContentPane().add(entradaY);
		
		//Adiciona as letras e numeros na tela
		for(int i = 0; i < sistema.tamanhoTabela; i++){
			frame.getContentPane().add(tabJogador.getNumeros()[i]);
			frame.getContentPane().add(tabJogador.getLetras()[i]);
			frame.getContentPane().add(tabIA.getNumeros()[i]);
			frame.getContentPane().add(tabIA.getLetras()[i]);
		}
		
		//Criando o botão Dica
		botaoJogoDica = new JButton("Dica (3 restantes)");
		botaoJogoDica.setBounds(355, 530, 270, 25);
		JFrame fra = this.frame;
		botaoJogoDica.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
						if(dica > 0){
							int x = Integer.parseInt(entradaX.getText().toString()) - 1;
							int y = 0;
							
							if(entradaY.getText().toString().equals("a") || entradaY.getText().toString().equals("A")){
								y = 0;
							}
							if(entradaY.getText().toString().equals("b") || entradaY.getText().toString().equals("B")){
								y = 1;
							}
							if(entradaY.getText().toString().equals("c") || entradaY.getText().toString().equals("C")){
								y = 2;
							}
							if(entradaY.getText().toString().equals("d") || entradaY.getText().toString().equals("D")){
								y = 3;
							}
							if(entradaY.getText().toString().equals("e") || entradaY.getText().toString().equals("E")){
								y = 4;
							}
							if(entradaY.getText().toString().equals("f") || entradaY.getText().toString().equals("F")){
								y = 5;
							}
							if(entradaY.getText().toString().equals("g") || entradaY.getText().toString().equals("G")){
								y = 6;
							}
							if(entradaY.getText().toString().equals("h") || entradaY.getText().toString().equals("H")){
								y = 7;
							}
							if(entradaY.getText().toString().equals("i") || entradaY.getText().toString().equals("I")){
								y = 8;
							}
							if(entradaY.getText().toString().equals("j") || entradaY.getText().toString().equals("J")){
								y = 9;
							}
							
							
							dica--;
							
							if(tabelaIA[x][y] == 0){
								JOptionPane.showMessageDialog(fra,"Ali só tem água mesmo...");
							}
							else{
								JOptionPane.showMessageDialog(fra,"Tem um navio ali!");
							}
							
							botaoJogoDica.setText("Dica (" + dica + " restantes)");
							
							if(dica == 0){
								botaoJogoDica.setEnabled(false);
							}
						}
						 
				}
			});
		frame.getContentPane().add(botaoJogoDica);
		
		//Criando o botão Sair
		botaoJogoReiniciar = new JButton("Reiniciar");
		botaoJogoReiniciar.setBounds(716, 460, 200, 25);
		botaoJogoReiniciar.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){  
					sistema.inicializarJogo(getTabelaJogador(), getTabelaIA());
		            frame.setVisible(false);
				}
			});
		frame.getContentPane().add(botaoJogoReiniciar);
		
		//Criando o botão Sair
		botaoJogoSair = new JButton("Sair");
		botaoJogoSair.setBounds(716, 495, 200, 25);
		JFrame esse = this.frame;
		TelaJogo essa = this;
		botaoJogoSair.addActionListener(new ActionListener(){
			   public void actionPerformed(ActionEvent e){  
				  DialogSair fim = new DialogSair(esse, essa, true, "O que deseja fazer?");
			   }
			});
		frame.getContentPane().add(botaoJogoSair);
		
		//Criando o botão disparo comum
		botaoComum = new JButton("Disparo Comum");
		botaoComum.setBounds(69, 460, 200, 25);
		botaoComum.addActionListener(new ActionListener(){
			   public void actionPerformed(ActionEvent e){
				  tabJogador.setDisparo(Disparos.COMUM);
			   }
			});
		frame.getContentPane().add(botaoComum);
		
		//Criando o botão disparo rajada
		botaoRajada = new JButton("Disparo Rajada");
		botaoRajada.setBounds(69, 495, 200, 25);
		botaoRajada.addActionListener(new ActionListener(){
			   public void actionPerformed(ActionEvent e){
				   tabJogador.setDisparo(Disparos.RAJADA);
			   }
			});
		frame.getContentPane().add(botaoRajada);
		
		//Criando o botão disparo estrela
		botaoEstrela = new JButton("Disparo Estrela");
		botaoEstrela.setBounds(69, 530, 200, 25);
		botaoEstrela.addActionListener(new ActionListener(){
			   public void actionPerformed(ActionEvent e){
				   tabJogador.setDisparo(Disparos.ESTRELA);
			   }
			});
		frame.getContentPane().add(botaoEstrela);
	}
	
	//Método que recebe a intenção de um tiro e de acordo com o tipo, chama o método específico
	public void realizarTiro(Tabuleiro tabDamaged, Tabuleiro tabAtack, int x, int y){
			if(tabAtack.getDisparo() == Disparos.COMUM){
				disparoComum(tabDamaged, x, y);
			}
			if(tabAtack.getDisparo() == Disparos.RAJADA){
				disparoRajada(tabDamaged, x, y);
			}
			if(tabAtack.getDisparo() == Disparos.ESTRELA){
				disparoEstrela(tabDamaged, x, y);
			}
				
	}
	
	//Método que realiza um disparo comum
	public void disparoComum(Tabuleiro tab, int x, int y){
		tab.setAcerto(x, y);
		if(tab.checkDerrota())
			fimDeJogo(tab, "fim");
	}
	
	//Método que realiza um disparo rajada
	public void disparoRajada(Tabuleiro tab, int x, int y){
		tab.setAcerto(x, y);
		tab.setAcerto(x+1, y);
		if(tab.checkDerrota())
			fimDeJogo(tab, "fim");
	}
	
	//Método que realiza um disparo estrela
	public void disparoEstrela(Tabuleiro tab, int x, int y){
		tab.setAcerto(x, y);
		tab.setAcerto(x+1, y);
		tab.setAcerto(x-1, y);
		tab.setAcerto(x, y+1);
		tab.setAcerto(x, y-1);
		if(tab.checkDerrota())
			fimDeJogo(tab, "fim");
	}
	
	//Método chamado quando for terminar o jogo, e determina quem foi o vencedor
	public void fimDeJogo(Tabuleiro tab, String s){
		this.terminou = true;
		DialogSair fim;
		if(tab.getJogador()){
			if(tab.checkPorta()){
				fim = new DialogSair(this.frame, this, true, "O computador venceu! O jogador não podia mais atirar!");
			}	
			else{
				fim = new DialogSair(this.frame, this, true, "O computador venceu! O jogador ficou sem navios!");
			}
		}
		else{
			if(tab.checkPorta()){
				fim = new DialogSair(this.frame, this, true, "O jogador venceu! O computador não podia mais atirar!");
				}
			else{
				fim = new DialogSair(this.frame, this, true, "O jogador venceu! O computador ficou sem navios!");
				}
		}
	}
	
	//Método realiza a jogada da IA
	public void jogadaIA(){
		this.setDisparoIA();
		if(!terminou){
			Random sort = new Random();
			int x;
			int y;
			
			do{
				x = sort.nextInt(sistema.tamanhoTabela - 1) + 1;
				y = sort.nextInt(sistema.tamanhoTabela - 1) + 1;
			}while(tabJogador.getMapa()[x][y] == 1);
			
			this.realizarTiro(tabJogador, tabIA, x, y);
		}
	}
	
	//Método verifica quais disparos a IA ainda possui, e escolhe o tipo com maior raio de acerto
	public void setDisparoIA(){
		if(tabIA.checkAviao())
			tabIA.setDisparo(Disparos.ESTRELA);
		else if(tabIA.checkNavio())
			tabIA.setDisparo(Disparos.RAJADA);
		else if(tabIA.checkSubmarino())
			tabIA.setDisparo(Disparos.COMUM);
		else
			tabIA.setDisparo(null);
	}
	
	//Get and Set a partir daqui
	public double[][] getTabelaJogador() {
		return tabelaJogador;
	}
	public void setTabelaJogador(double[][] tabelaJogador) {
		this.tabelaJogador = tabelaJogador;
	}
	public double[][] getTabelaIA() {
		return tabelaIA;
	}
	public void setTabelaIA(double[][] tabelaIA) {
		this.tabelaIA = tabelaIA;
	}
	
	public JFrame getFrame(){
		return this.frame;
	}
	
	public void setFrame(JFrame f){
		this.frame = f;
	}

	public Tabuleiro getTabJogador() {
		return tabJogador;
	}

	public void setTabJogador(Tabuleiro tabJogador) {
		this.tabJogador = tabJogador;
	}

	public Tabuleiro getTabIA() {
		return tabIA;
	}

	public void setTabIA(Tabuleiro tabIA) {
		this.tabIA = tabIA;
	}

	public boolean getTerminou() {
		return terminou;
	}

	public void setTerminou(boolean terminou) {
		this.terminou = terminou;
	}
	
	public Sistema getSistema(){
		return this.sistema;
	}
	
	
}
