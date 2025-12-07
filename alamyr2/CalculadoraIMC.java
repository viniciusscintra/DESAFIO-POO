import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.Period;
import javax.swing.*; 
public class CalculadoraIMC extends JFrame {

    private JTextField txtNome, txtAltura, txtPeso;
    private JComboBox<String> cbDia, cbMes, cbAno;
    private JTextArea txtResultado;
    private JProgressBar barraIMC;
    private Timer animacaoBarra;

    public CalculadoraIMC() {

        setTitle("PowerFitnnes - Sistema Avançado de Avaliação Física");
        setSize(520, 620);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Painel com degradê
        JPanel painel = new JPanel() {
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(
                        0, 0, new Color(0, 80, 0),
                        0, getHeight(), new Color(0, 160, 0)
                );
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        painel.setLayout(null);
        add(painel);

        // Título
        JLabel lblTitulo = new JLabel("💻 PowerFitnnes - Sistema Inteligente de IMC");
        lblTitulo.setBounds(30, 15, 480, 30);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setForeground(Color.WHITE);
        painel.add(lblTitulo);

        // Nome
        painel.add(label("Nome:", 30, 70));
        txtNome = campo(150, 70, 320);
        painel.add(txtNome);

        // Data
        painel.add(label("Nascimento:", 30, 110));
        cbDia = combo(150, 110, 60, 31);
        cbMes = combo(220, 110, 60, 12);

        cbAno = new JComboBox<>();
        cbAno.setBounds(290, 110, 100, 27);
        int anoAtual = LocalDate.now().getYear();
        for (int i = anoAtual; i >= 1900; i--) {
            cbAno.addItem(String.valueOf(i));
        }

        painel.add(cbDia);
        painel.add(cbMes);
        painel.add(cbAno);

        // Altura
        painel.add(label("Altura:", 30, 150));
        txtAltura = campo(150, 150, 260);
        painel.add(txtAltura);

        JLabel lblCm = new JLabel("cm");
        lblCm.setBounds(420, 150, 40, 25);
        lblCm.setForeground(Color.WHITE);
        painel.add(lblCm);

        // Peso
        painel.add(label("Peso:", 30, 190));
        txtPeso = campo(150, 190, 320);
        painel.add(txtPeso);

        // Botões tecnológicos
        JButton btnCalcular = botao("⚡ Calcular", 110, 240);
        JButton btnLimpar = botao("🧹 Limpar", 270, 240);
        painel.add(btnCalcular);
        painel.add(btnLimpar);

        // Barra de progresso animada
        barraIMC = new JProgressBar(0, 50);
        barraIMC.setBounds(50, 300, 420, 28);
        barraIMC.setStringPainted(true);
        barraIMC.setFont(new Font("Segoe UI", Font.BOLD, 14));
        painel.add(barraIMC);

        // Área de resultado
        txtResultado = new JTextArea();
        txtResultado.setBounds(50, 340, 420, 210);
        txtResultado.setFont(new Font("Segoe UI", Font.BOLD, 14));
        txtResultado.setEditable(false);
        txtResultado.setLineWrap(true);
        txtResultado.setWrapStyleWord(true);
        txtResultado.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        txtResultado.setBackground(Color.WHITE);
        txtResultado.setForeground(Color.BLACK);
        txtResultado.setOpaque(true);
        painel.add(txtResultado);

        // Eventos
        btnCalcular.addActionListener(e -> calcularComAnimacao());
        btnLimpar.addActionListener(e -> limpar());
    }

    // Métodos visuais
    private JLabel label(String txt, int x, int y) {
        JLabel lbl = new JLabel(txt);
        lbl.setBounds(x, y, 150, 25);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lbl.setForeground(Color.WHITE);
        return lbl;
    }

    private JTextField campo(int x, int y, int largura) {
        JTextField txt = new JTextField();
        txt.setBounds(x, y, largura, 27);
        txt.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        return txt;
    }

    private JComboBox<String> combo(int x, int y, int largura, int limite) {
        JComboBox<String> cb = new JComboBox<>();
        cb.setBounds(x, y, largura, 27);
        for (int i = 1; i <= limite; i++) {
            cb.addItem(String.format("%02d", i));
        }
        return cb;
    }

    private JButton botao(String texto, int x, int y) {
        JButton btn = new JButton(texto);
        btn.setBounds(x, y, 140, 38);
        btn.setBackground(Color.WHITE);
        btn.setForeground(new Color(0, 120, 0));
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

        // Efeito hover
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(new Color(230, 255, 230));
            }
            public void mouseExited(MouseEvent e) {
                btn.setBackground(Color.WHITE);
            }
        });

        return btn;
    }

    // Animação principal
    private void calcularComAnimacao() {
        try {
            double alturaCm = Double.parseDouble(txtAltura.getText());
            double peso = Double.parseDouble(txtPeso.getText());

            double alturaM = alturaCm / 100.0;
            double imcFinal = peso / (alturaM * alturaM);

            if (animacaoBarra != null && animacaoBarra.isRunning()) {
                animacaoBarra.stop();
            }

            barraIMC.setValue(0);
            txtResultado.setText("🔄 Processando dados...\nCalculando IMC...");

            animacaoBarra = new Timer(20, null);
            animacaoBarra.addActionListener(new ActionListener() {
                int valor = 0;
                public void actionPerformed(ActionEvent e) {
                    valor++;
                    barraIMC.setValue(valor);
                    barraIMC.setString("Processando...");

                    if (valor >= (int) imcFinal) {
                        animacaoBarra.stop();
                        mostrarResultado(imcFinal);
                    }
                }
            });
            animacaoBarra.start();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Preencha os campos corretamente.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarResultado(double imc) {
        try {
            int dia = Integer.parseInt(cbDia.getSelectedItem().toString());
            int mes = Integer.parseInt(cbMes.getSelectedItem().toString());
            int ano = Integer.parseInt(cbAno.getSelectedItem().toString());

            String nome = txtNome.getText();

            LocalDate dataNascimento = LocalDate.of(ano, mes, dia);
            int idade = Period.between(dataNascimento, LocalDate.now()).getYears();

            String classificacao;
            String mensagem;

            if (imc < 18.5) {
                classificacao = "Abaixo do peso";
                mensagem = "⚠️ Atenção: procure um nutricionista.";
            } else if (imc < 25) {
                classificacao = "Peso normal";
                mensagem = "✅ Excelente desempenho físico!";
            } else if (imc < 30) {
                classificacao = "Sobrepeso";
                mensagem = "💪 Treino recomendado!";
            } else {
                classificacao = "Obesidade";
                mensagem = "🚨 Recomenda-se acompanhamento profissional.";
            }

            barraIMC.setString(String.format("IMC Final: %.2f", imc));

            txtResultado.setText(
                    "✅ Avaliação concluída!\n\n" +
                    "Nome: " + nome + "\n" +
                    "Idade: " + idade + " anos\n" +
                    String.format("IMC: %.2f\n", imc) +
                    "Classificação: " + classificacao + "\n\n" +
                    mensagem
            );

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao mostrar resultado.");
        }
    }

    private void limpar() {
        txtNome.setText("");
        txtAltura.setText("");
        txtPeso.setText("");
        txtResultado.setText("");
        barraIMC.setValue(0);

        if (animacaoBarra != null) {
            animacaoBarra.stop();
        }
    }

    public static void main(String[] args) {
        new CalculadoraIMC().setVisible(true);
    }
}

