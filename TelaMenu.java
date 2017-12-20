import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class TelaMenu {

	Sistema sistema;
	private JFrame frame;
	JButton botaoMenuAleatorio, botaoMenuEscolher;
	
	public TelaMenu(Sistema s) {
		this.sistema = s;
		inicializar();
	}

	//Método inicializa conteúdo do frame, sendo chamado pelo construtor da classe
	private void inicializar() {
		frame = new JFrame(sistema.tituloJogo + " - Menu Principal");
		frame.setSize(sistema.janelaLargura, sistema.janelaAltura);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
		frame.setResizable(false);
		this.criarMenu();
		
	}
	
	public void criarMenu(){
		
		JLabel textoTitulo = new JLabel("Batalha Naval");
		textoTitulo.setBounds(230, 200, 700, 100);
		textoTitulo.setFont(textoTitulo.getFont().deriveFont(70f));
		frame.getContentPane().add(textoTitulo);
		
		JLabel textoTitulo2 = new JLabel("(Mas tem um avião no meio também)");
		textoTitulo2.setBounds(360, 250, 700, 100);
		//textoTitulo2.setFont(textoTitulo.getFont().deriveFont(70f));
		frame.getContentPane().add(textoTitulo2);
		
		JLabel textoCreditos = new JLabel("UFRRJ-IM - Ciência da Computação | Ramon Machado e Rafael Lemos");
		textoCreditos.setBounds(10, 500, 700, 100);
		//textoTitulo2.setFont(textoTitulo.getFont().deriveFont(70f));
		frame.getContentPane().add(textoCreditos);
		
		botaoMenuAleatorio = new JButton("Jogo Aleatório");
		botaoMenuAleatorio.setBounds(390, 450, 200, 25);
		botaoMenuAleatorio.addActionListener(new ActionListener(){
			   public void actionPerformed(ActionEvent e){
				   sistema.inicializarEscolher(true);
				   frame.setVisible(false);
			   }
			});
		frame.getContentPane().add(botaoMenuAleatorio);
		
		botaoMenuEscolher = new JButton("Definir Jogo");
		botaoMenuEscolher.setBounds(390, 485, 200, 25);
		botaoMenuEscolher.addActionListener(new ActionListener(){
			   public void actionPerformed(ActionEvent e){
				   sistema.inicializarEscolher(false);
				   frame.setVisible(false);
			   }
			});
		botaoMenuEscolher.setVisible(true);
		frame.getContentPane().add(botaoMenuEscolher);	
	}
	
	public JFrame getFrame(){
		return this.frame;
	}
	
	public void setFrame(JFrame f){
		this.frame = f;
	}
	
}
