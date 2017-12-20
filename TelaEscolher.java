import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class TelaEscolher {
	
	private Sistema sistema;
	private JFrame frame;
	private JButton botoes[][], botaoNavio, botaoAviao, botaoPorta, botaoSubmarino, botaoJogar, botaoLerArquivo;
	private JLabel letras[], numeros[];
	private int posBotaoX = 20;
	private int posBotaoY = 40;
	private String tipoNavio = "submarino";
	private double mapa[][];
	private double tabelaJogador[][];
	private double tabelaIA[][];
	JTextArea entradaCaminho;
	
	public TelaEscolher(Sistema s, boolean ale){
		this.sistema = s;
		frame = new JFrame(sistema.tituloJogo + " - Montando Tabuleiro");
		mapa = new double[sistema.tamanhoTabela][sistema.tamanhoTabela];
		botoes = new JButton[sistema.tamanhoTabela][sistema.tamanhoTabela];
		criarGerarPosicoes(ale);
	}
	
	//Método que define os sorteios ou a tela de escolher
	public void criarGerarPosicoes(boolean aleatorio){
		//Caso seja aleatório, vai sortear as posições e chama a tela de jogo
		if(aleatorio){
			criarBotoes();
			tabelaJogador = sortearPosicoes();
			tabelaIA = sortearPosicoes();
			sistema.inicializarJogo(tabelaJogador, tabelaIA);
		}
		//Caso NÃO for aleatório, irá exibir a tela onde o jogador poderá posicionar os navios ou ler de um arquivo
		else{
			frame.getContentPane().removeAll();
			frame.revalidate();
			frame.repaint();
			frame.setSize(sistema.janelaLargura, sistema.janelaAltura);
			frame.setLocationRelativeTo(null);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.getContentPane().setLayout(null);
			frame.setVisible(true);
			frame.setResizable(false);
			criarBotoes();
			criarLetrasNumeros();
		}
		
	}

//Método cria os botões onde o jogador poderá clicar para escolher a posição
public void criarBotoes(){
	for(int i = 0; i < sistema.tamanhoTabela; i++){
		for(int j = 0; j < sistema.tamanhoTabela; j++){
			ImageIcon icon = new ImageIcon(Tabuleiro.class.getResource("sprites/cinza.png"));
			botoes[i][j] = new JButton(icon);
			icon = new ImageIcon("src/sprites/mar.png");
			botoes[i][j].setDisabledIcon(icon);
			int x = i, y = j;
			botoes[i][j].addActionListener(new ActionListener(){
					//Ao clicar no botão, o botão irá verificar qual o tipo de navio está sendo posicionado
				   public void actionPerformed(ActionEvent e){
					  if(tipoNavio.equals("submarino")){
						  if(x <= 8){
							  if(checkSubmarino(mapa, x, y)){
								  setSubmarino(mapa, x, y);
								  botaoSubmarino.setEnabled(false);
								  enableJogar();
							  }
						  }
					  }
					  if(tipoNavio.equals("porta")){
						  if(x <= 6){
							  if(checkPorta(mapa, x, y)){
								  setPorta(mapa, x, y);
								  botaoPorta.setEnabled(false);
								  enableJogar();
							  }	 
						  }
					  }
					  if(tipoNavio.equals("navio")){
						  if(x <= 7){
							  if(checkNavio(mapa, x, y)){
								  setNavio(mapa, x, y);
								  botaoNavio.setEnabled(false);
								  enableJogar();
							  }
								 
						  }
					  }
					  if(tipoNavio.equals("aviao")){
						  if(x <= 8){
							  if(checkAviao(mapa, x, y)){
								  setAviao(mapa, x, y);
								  botaoAviao.setEnabled(false);
								  enableJogar();
							  }
								 
						  }
					  }
				   }
				});
			
			botoes[i][j].setBounds(posBotaoX+(sistema.tamanhoBotao*(i+1)), posBotaoY+(sistema.tamanhoBotao*(j+1)), sistema.tamanhoBotao, sistema.tamanhoBotao);
			frame.getContentPane().add(botoes[i][j]);
		}
	}
	
	JSeparator separator = new JSeparator();
	separator.setOrientation(SwingConstants.VERTICAL);
	separator.setBounds(12*sistema.tamanhoBotao + 20, 10, 2, 545);
	frame.getContentPane().add(separator);
	
	JSeparator separator2 = new JSeparator();
	separator2.setOrientation(SwingConstants.HORIZONTAL);
	separator2.setBounds(12*sistema.tamanhoBotao + 30, 100, 450, 2);
	frame.getContentPane().add(separator2);
	
	JSeparator separator3 = new JSeparator();
	separator3.setOrientation(SwingConstants.HORIZONTAL);
	separator3.setBounds(12*sistema.tamanhoBotao + 30, 210, 450, 2);
	frame.getContentPane().add(separator3);
	
	JSeparator separator4 = new JSeparator();
	separator4.setOrientation(SwingConstants.HORIZONTAL);
	separator4.setBounds(12*sistema.tamanhoBotao + 30, 320, 450, 2);
	frame.getContentPane().add(separator4);
	
	JLabel textoEscolher = new JLabel("Escolha a posição de cada embarcação:");
	textoEscolher.setBounds(530, 75, 450, 25);
	frame.getContentPane().add(textoEscolher);
	
	JLabel textoArquivo = new JLabel("Ou então, digite o caminho absoluto do arquivo a ser lido:");
	textoArquivo.setBounds(530, 220, 450, 25);
	frame.getContentPane().add(textoArquivo);
	
	entradaCaminho = new JTextArea();
	entradaCaminho.setEditable(true);
	entradaCaminho.setBounds(530, 245, 420, 25);
	entradaCaminho.setBorder(BorderFactory.createLineBorder(Color.black));
	frame.getContentPane().add(entradaCaminho);
	
	//Código abaixo cria os botões para escolher o navio
	botaoSubmarino = new JButton("Posicionar Submarino");
	botaoSubmarino.setBounds(530, 120, 200, 25);
	botaoSubmarino.addActionListener(new ActionListener(){
		   public void actionPerformed(ActionEvent e){
			   tipoNavio = "submarino";
		   }
		});
	frame.getContentPane().add(botaoSubmarino);
	
	botaoAviao = new JButton("Posicionar Caça");
	botaoAviao.setBounds(750, 120, 200, 25);
	botaoAviao.addActionListener(new ActionListener(){
		   public void actionPerformed(ActionEvent e){
			   tipoNavio = "aviao";
		   }
		});
	frame.getContentPane().add(botaoAviao);
	
	botaoNavio = new JButton("Posicionar Navio");
	botaoNavio.setBounds(530, 165, 200, 25);
	botaoNavio.addActionListener(new ActionListener(){
		   public void actionPerformed(ActionEvent e){
			   tipoNavio = "navio";
		   }
		});
	frame.getContentPane().add(botaoNavio);
	
	botaoPorta = new JButton("Posicionar Porta-Aviões");
	botaoPorta.setBounds(750, 165, 200, 25);
	botaoPorta.addActionListener(new ActionListener(){
		   public void actionPerformed(ActionEvent e){
			   tipoNavio = "porta";
		   }
		});
	frame.getContentPane().add(botaoPorta);
	
	botaoJogar = new JButton("Jogar");
	botaoJogar.setBounds(530, 350, 420, 75);
	botaoJogar.setEnabled(false);
	botaoJogar.addActionListener(new ActionListener(){
		   public void actionPerformed(ActionEvent e){
			   double tabelaJogador[][] = mapa;
			   double tabelaIA[][] = sortearPosicoes();
			   sistema.inicializarJogo(tabelaJogador, tabelaIA);
		   }
		});
	frame.getContentPane().add(botaoJogar);
	
	botaoLerArquivo = new JButton("Carregar Arquivo");
	botaoLerArquivo.setBounds(530, 280, 200, 25);
	botaoLerArquivo.addActionListener(new ActionListener(){
		   public void actionPerformed(ActionEvent e){
			   try {
				carregarArquivo();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		   }
		});
	frame.getContentPane().add(botaoLerArquivo);
}

//Este método carrega as posições dos navios de acordo com o conteúdo do arquivo "entrada.txt"
public void carregarArquivo() throws FileNotFoundException{
	
	mapa = new double[sistema.tamanhoTabela][sistema.tamanhoTabela];
	File arquivo = new File(entradaCaminho.getText().toString());
	Scanner scan = new Scanner(arquivo);
	int naviosCarregados = 0;
	boolean aviao = false;
	
	while(naviosCarregados < 4){
		int tipo = scan.nextInt();
		String s = scan.next();
		char c[] = s.toCharArray();
		
		if(c[0] - 'A' < 10 && c[1] - '1' < 10){
			if(tipo == 2){
				if(!aviao){
					setAviao(mapa, c[1] - '1', c[0] - 'A');
					aviao = true;
					naviosCarregados++;
				}
				else{
					setSubmarino(mapa, c[1] - '1', c[0] - 'A');
					naviosCarregados++;
				}
			}
			if(tipo == 3){
				setNavio(mapa, c[1] - '1', c[0] - 'A');
				naviosCarregados++;
			}
			if(tipo == 4){
				setPorta(mapa,c[1] - '1', c[0] - 'A');
				naviosCarregados++;
			}
		}
	}
	
	scan.close();
	double tabelaJogador[][] = mapa;
	double tabelaIA[][] = sortearPosicoes();
	sistema.inicializarJogo(tabelaJogador, tabelaIA);
}

//Caso todos os navios estejam já posicionados, o botão Jogar fica disponível
public void enableJogar(){
	if(!botaoNavio.isEnabled() && !botaoAviao.isEnabled() && !botaoSubmarino.isEnabled() && !botaoPorta.isEnabled())
		botaoJogar.setEnabled(true);
}
	
//Método que cria os JLabel de letras e números
public void criarLetrasNumeros(){
		
		int i = 1;
		char a = 'A';
		
		numeros = new JLabel[sistema.tamanhoTabela];
		letras = new JLabel[sistema.tamanhoTabela];
		
		for(i = 1; i <= sistema.tamanhoTabela; i++){
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
			numeros[i-1].setBounds(posBotaoX+(i*sistema.tamanhoBotao), posBotaoY, sistema.tamanhoBotao, sistema.tamanhoBotao);
			letras[i-1].setBounds(posBotaoX, posBotaoY+(i*sistema.tamanhoBotao), sistema.tamanhoBotao, sistema.tamanhoBotao);
			frame.getContentPane().add(numeros[i-1]);
			frame.getContentPane().add(letras[i-1]);
		}
	}
	
	//Método verifica se o porta-navios pode ser posicionado ali
	public boolean checkPorta(double mapa[][], int x, int y){
		if(mapa[x][y] == 0 && mapa[x+1][y] == 0 && mapa[x+2][y] == 0 && mapa[x+3][y] == 0)
			return true;
		else
			return false;
	}
	
	//Método determina a posição do porta-navios no mapa
	public void setPorta(double mapa[][], int x, int y){
			mapa[x][y] = Navios.PORTA1.valor;
			mapa[x+1][y] = Navios.PORTA2.valor;
			mapa[x+2][y] = Navios.PORTA3.valor;
			mapa[x+3][y] = Navios.PORTA4.valor;
			ImageIcon icon;
			icon = new ImageIcon(Tabuleiro.class.getResource("sprites/" + mapa[x][y] + ".png"));
			botoes[x][y].setDisabledIcon(icon);
			botoes[x][y].setEnabled(false);
			icon = new ImageIcon(Tabuleiro.class.getResource("sprites/" + mapa[x+1][y] + ".png"));
			botoes[x+1][y].setDisabledIcon(icon);
			botoes[x+1][y].setEnabled(false);
			icon = new ImageIcon(Tabuleiro.class.getResource("sprites/" + mapa[x+2][y] + ".png"));
			botoes[x+2][y].setDisabledIcon(icon);
			botoes[x+2][y].setEnabled(false);
			icon = new ImageIcon(Tabuleiro.class.getResource("sprites/" + mapa[x+3][y] + ".png"));
			botoes[x+3][y].setDisabledIcon(icon);
			botoes[x+3][y].setEnabled(false);
	}
	
	//Método verifica se o submarino pode ser posicionado ali
	public boolean checkSubmarino(double mapa[][], int x, int y){
		if(mapa[x][y] == 0 && mapa[x+1][y]== 0)
			return true;
		else
			return false;
	}
	
	//Método determina a posição do submarino no mapa
	public void setSubmarino(double mapa[][], int x, int y){
		mapa[x][y] = Navios.SUBMARINO1.valor;
		mapa[x+1][y] = Navios.SUBMARINO2.valor;
		ImageIcon icon;
		icon = new ImageIcon(Tabuleiro.class.getResource("sprites/" + mapa[x][y] + ".png"));
		botoes[x][y].setDisabledIcon(icon);
		botoes[x][y].setEnabled(false);
		icon = new ImageIcon(Tabuleiro.class.getResource("sprites/" + mapa[x+1][y] + ".png"));
		botoes[x+1][y].setDisabledIcon(icon);
		botoes[x+1][y].setEnabled(false);
	}
	
	//Método verifica se o aviao pode ser posicionado ali
	public boolean checkAviao(double mapa[][], int x, int y){
		if(mapa[x][y] == 0 && mapa[x+1][y]== 0)
			return true;
		else
			return false;
	}
	
	//Método determina a posição do aviao no mapa
	public void setAviao(double mapa[][], int x, int y){
		mapa[x][y] = Navios.AVIAO1.valor;
		mapa[x+1][y] = Navios.AVIAO2.valor;
		ImageIcon icon;
		icon = new ImageIcon(Tabuleiro.class.getResource("sprites/" + mapa[x][y] + ".png"));
		botoes[x][y].setDisabledIcon(icon);
		botoes[x][y].setEnabled(false);
		icon = new ImageIcon(Tabuleiro.class.getResource("sprites/" + mapa[x+1][y] + ".png"));
		botoes[x+1][y].setDisabledIcon(icon);
		botoes[x+1][y].setEnabled(false);
	}
	
	//Método verifica se o navio pode ser posicionado ali
	public boolean checkNavio(double mapa[][], int x, int y){
		if(mapa[x][y] == 0 && mapa[x+1][y] == 0 && mapa[x+2][y] == 0)
			return true;
		else
			return false;
	}
	
	//Método determina a posição do navio no mapa
	public void setNavio(double mapa[][], int x, int y){
			mapa[x][y] = Navios.NAVIO1.valor;
			mapa[x+1][y] = Navios.NAVIO2.valor;
			mapa[x+2][y] = Navios.NAVIO3.valor;
			ImageIcon icon;
			icon = new ImageIcon(Tabuleiro.class.getResource("sprites/" + mapa[x][y] + ".png"));
			botoes[x][y].setDisabledIcon(icon);
			botoes[x][y].setEnabled(false);
			icon = new ImageIcon(Tabuleiro.class.getResource("sprites/" + mapa[x+1][y] + ".png"));
			botoes[x+1][y].setDisabledIcon(icon);
			botoes[x+1][y].setEnabled(false);
			icon = new ImageIcon(Tabuleiro.class.getResource("sprites/" + mapa[x+2][y] + ".png"));
			botoes[x+2][y].setDisabledIcon(icon);
			botoes[x+2][y].setEnabled(false);
	}

	//Método sorteia as posições dos navios aleatoriamente e retorna um mapa
	public double[][] sortearPosicoes(){
		double matriz[][] = new double[sistema.tamanhoTabela][sistema.tamanhoTabela];
		Random sorteador = new Random();
		int posX;
		int posY;
		
		//Sorteando as posicoes para o Porta-Avião
		do{
			posX = sorteador.nextInt(sistema.tamanhoTabela-4) + 1;
			posY = sorteador.nextInt(sistema.tamanhoTabela-1) + 1;
			
		}while(!checkPorta(matriz, posX, posY));
		setPorta(matriz, posX, posY);
		

		//Sorteando as posicoes para o Submarino
		do{
			posX = sorteador.nextInt(sistema.tamanhoTabela-2) + 1;
			posY = sorteador.nextInt(sistema.tamanhoTabela-1) + 1;
		}while(!checkSubmarino(matriz, posX, posY));
		setSubmarino(matriz, posX, posY);

		//Sorteando as posicoes para o Caça
		do{
			posX = sorteador.nextInt(sistema.tamanhoTabela-2) + 1;
			posY = sorteador.nextInt(sistema.tamanhoTabela-1) + 1;
		}while(!checkAviao(matriz, posX, posY));
		setAviao(matriz, posX, posY);
		
		//Sorteando as posicoes para o Navio de Escolta
		do{
			posX = sorteador.nextInt(sistema.tamanhoTabela-3) + 1;
			posY = sorteador.nextInt(sistema.tamanhoTabela-1) + 1;
		}while(!checkNavio(matriz, posX, posY));
		setNavio(matriz, posX, posY);
		
		return matriz;
	}
	
	public JFrame getFrame(){
		return this.frame;
	}
	
	public void setFrame(JFrame f){
		this.frame = f;
	}
}
