Explicação Detalhada do Código
O programa é estruturado em uma classe chamada CalculadoraIMCSwing que herda de JFrame, a janela principal da aplicação:
Variáveis de Instância:
campoNome, campoAno, campoAltura, campoPeso: São objetos JTextField onde o usuário insere dados de texto.
areaSaida: Um JTextArea usado para exibir o resultado do cálculo.

Construtor (CalculadoraIMCSwing()):
Configura o layout e a aparência da janela.
Utiliza BorderLayout como gerenciador de layout principal, dividindo a janela em regiões (Norte, Centro, Sul).
Cria painéis (JPanel) com GridLayout para organizar os campos de entrada e botões de forma organizada.
Adiciona os componentes visuais (rótulos, campos de texto, botões e área de texto rolável) à janela.
Define o comportamento padrão ao fechar a janela (JFrame.EXIT_ON_CLOSE) e a torna visível.

Tratamento de Eventos (ActionListeners):
btnCalcular.addActionListener(e -> calcularIMC());: Associa o botão "Calcular" ao método calcularIMC(), que é executado quando o botão é clicado.
O botão "Limpar" tem um listener que redefine todos os campos de texto e a área de saída para vazio.

Método calcularIMC():
Esta é a lógica central da aplicação.
Bloco try-catch: Garante que, se o usuário inserir dados inválidos (por exemplo, texto em campos numéricos), o programa não trave e exiba uma mensagem de erro amigável na área de saída.
Leitura e Conversão: Obtém os valores dos campos de texto e converte-os para os tipos de dados apropriados (int, double).

Cálculo da Idade e IMC: Calcula a idade subtraindo o ano de nascimento do ano atual e calcula o IMC com a fórmula peso / (altura * altura).
Classificação do IMC: Usa uma série de estruturas if-else if para determinar a faixa de peso do usuário (Abaixo do Peso, Normal, Obesidade, etc.) com base no valor do IMC
.
Exibição do Resultado: Formata e exibe todos os dados do usuário e o resultado do cálculo na areaSaida.

Método Principal (main):
O ponto de entrada do programa.
SwingUtilities.invokeLater(...) é a maneira recomendada de iniciar aplicações Swing, garantindo que todos os componentes de GUI sejam criados e atualizados na Event Dispatch Thread (EDT), o que é crucial para a estabilidade da interface gráfica em Java.
