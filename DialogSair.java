import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class DialogSair extends JDialog implements ActionListener{

	private JPanel myPanel = null;
	private TelaJogo tela;
    private JButton botaoReiniciar = null;
    private JButton botaoMenu = null;
    private boolean answer = false;
    public boolean getAnswer() { return answer; }

    public DialogSair(JFrame frame, TelaJogo tela, boolean modal, String mensagem) {
        super(frame, modal);
        myPanel = new JPanel();
        myPanel.setBounds(0, 0, 400, 50);
        this.setSize(400, 100);
        this.tela = tela;
        getContentPane().add(myPanel);
        criarItens(mensagem);
        //pack();
        setLocationRelativeTo(frame);
        setVisible(true);
    }
    
    public void criarItens(String mensagem){
    	JLabel text = new JLabel(mensagem);
    	text.setHorizontalAlignment(SwingConstants.CENTER);
    	myPanel.add(text);
    	botaoReiniciar = new JButton("Reiniciar");
    	botaoReiniciar.setBounds(0, 150, 200, 50);
        botaoReiniciar.addActionListener(this);
        myPanel.add(botaoReiniciar); 
        botaoMenu = new JButton("Menu Principal");
        botaoMenu.addActionListener(this);
        myPanel.add(botaoMenu);  
    }

    public void actionPerformed(ActionEvent e) {
        if(botaoReiniciar == e.getSource()) {
           tela.getSistema().inicializarJogo(tela.getTabelaJogador(), tela.getTabelaIA());
            setVisible(false);
        }
        else if(botaoMenu == e.getSource()) {
            tela.getSistema().inicializarMenu();
            setVisible(false);
        }
    }
}
