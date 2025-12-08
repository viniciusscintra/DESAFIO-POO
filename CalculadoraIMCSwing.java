import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.Year;

public class CalculadoraIMCSwing extends JFrame {
    
    // Campos de entrada e área de saída
    private JTextField campoNome, campoAno, campoAltura, campoPeso;
    private JTextArea areaSaida;

    public CalculadoraIMCSwing() {
        super("Calculadora de IMC - Academia"); 
        // Título da janela

        setLayout(new BorderLayout()); 
        // Define layout principal da janela

        // ===== PAINEL DOS CAMPOS DE ENTRADA =====
        JPanel painelCampos = new JPanel(new GridLayout(4, 2, 5, 5));
        // GridLayout com 4 linhas, 2 colunas e espaçamento entre componentes

        // Campo Nome
        painelCampos.add(new JLabel("Nome do usuário:"));
        campoNome = new JTextField();
        painelCampos.add(campoNome);

        // Campo Ano de nascimento
        painelCampos.add(new JLabel("Ano de nascimento:"));
        campoAno = new JTextField();
        painelCampos.add(campoAno);

        // Campo Altura
        painelCampos.add(new JLabel("Altura (m):"));
        campoAltura = new JTextField();
        painelCampos.add(campoAltura);

        // Campo Peso
        painelCampos.add(new JLabel("Peso (kg):"));
        campoPeso = new JTextField();
        painelCampos.add(campoPeso);

        add(painelCampos, BorderLayout.NORTH); 
        // Coloca os campos na parte superior da janela

        // ===== ÁREA DE SAÍDA DO RESULTADO =====
        areaSaida = new JTextArea(10, 30);
        areaSaida.setEditable(false); 
        // Usuário não pode alterar o resultado diretamente
        add(new JScrollPane(areaSaida), BorderLayout.CENTER);
        // Adiciona barra de rolagem caso o texto seja grande

        // ===== PAINEL DOS BOTÕES =====
        JPanel painelBotoes = new JPanel();
        JButton btnCalcular = new JButton("Calcular"); // Botão de cálculo
        JButton btnLimpar = new JButton("Limpar");     // Botão para limpar campos

        painelBotoes.add(btnCalcular);
        painelBotoes.add(btnLimpar);

        add(painelBotoes, BorderLayout.SOUTH); 
        // Coloca os botões na parte inferior

        // Evento do botão Calcular
        btnCalcular.addActionListener(e -> calcularIMC());

        // Evento do botão Limpar
        btnLimpar.addActionListener(e -> {
            campoNome.setText("");
            campoAno.setText("");
            campoAltura.setText("");
            campoPeso.setText("");
            areaSaida.setText("");
        });

        // Configurações da janela principal
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack(); // Ajusta o tamanho da janela automaticamente
        setLocationRelativeTo(null); // Centraliza na tela
        setVisible(true); // Exibe a janela
    }

    // ===== MÉTODO QUE CALCULA O IMC =====
    private void calcularIMC() {
        try {
            // Obtém os valores digitados
            String nome = campoNome.getText();
            int anoNasc = Integer.parseInt(campoAno.getText());
            double altura = Double.parseDouble(campoAltura.getText());
            double peso = Double.parseDouble(campoPeso.getText());

            // Calcula idade baseado no ano atual
            int idade = Year.now().getValue() - anoNasc;

            // Fórmula do IMC
            double imc = peso / (altura * altura);

            // Classificação do IMC
            String classificacao;
            if (imc < 18.5) classificacao = "Abaixo do Peso";
            else if (imc < 24.9) classificacao = "Peso Normal";
            else if (imc < 29.9) classificacao = "Sobrepeso";
            else if (imc < 34.9) classificacao = "Obesidade I";
            else if (imc < 39.9) classificacao = "Obesidade II";
            else classificacao = "Obesidade III";

            // Exibe resultado na área de saída
            areaSaida.setText(
                "Nome: " + nome + "\n" +
                "Idade: " + idade + " anos\n" +
                String.format("IMC: %.2f", imc) + "\n" +
                "Classificação: " + classificacao
            );

        } catch (Exception ex) {
            // Caso ocorra algum erro de conversão ou campo vazio
            areaSaida.setText("Erro: Verifique os dados informados.");
        }
    }

    // ===== MÉTODO PRINCIPAL =====
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CalculadoraIMCSwing());
        // Garante que a interface seja criada na Thread correta
    }
}
