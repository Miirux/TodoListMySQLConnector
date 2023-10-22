import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.EmptyBorder;

public class MainFrame extends JFrame {

	JFrame frame;
	private JTextField textField;
	JPanel panel;
	JButton addButton,subButton,mulButton,divButton;
	JButton decButton, equButton, delButton, clrButton;
	private JButton Button_0,Button_1,Button_2,Button_3,
					Button_4,Button_5,Button_6,Button_7,
					Button_8,Button_9;
	
	Font myFont = new Font("Ink Free",Font.BOLD,30);
	
	double num1=0,num2=0,result=0;
	char operator;
	private JButton decButton_1;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setSize(420,550);
		setTitle("Calculator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel = new JPanel();
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(panel);
		panel.setLayout(null);
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setBounds(44, 24, 333, 54);
		textField.setFont(myFont);
		panel.add(textField);
		textField.setColumns(10);
		
		delButton = new JButton("Delete");
		delButton.setVerticalAlignment(SwingConstants.BOTTOM);
		delButton.setFont(myFont);
		delButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String str = textField.getText();
				StringBuilder sb = new StringBuilder(str);
				sb.deleteCharAt(str.length()-1);
				textField.setText(sb.toString());
			}
		});
		delButton.setBounds(44, 444, 160, 59);
		panel.add(delButton);
		
		clrButton = new JButton("Clear");
		clrButton.setVerticalAlignment(SwingConstants.BOTTOM);
		clrButton.setFont(myFont);
		clrButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.setText("");
			}
		});
		clrButton.setBounds(214, 444, 163, 59);
		panel.add(clrButton);
		
		Button_1 = new JButton("1");
		Button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.setText(textField.getText().concat("1"));
			}
		});
		Button_1.setBounds(44, 99, 75, 75);
		Button_1.setFont(myFont);
		panel.add(Button_1);
		
		Button_2 = new JButton("2");
		Button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.setText(textField.getText().concat("2"));
			}
		});
		Button_2.setBounds(129, 99, 75, 75);
		Button_2.setFont(myFont);
		panel.add(Button_2);
		
		Button_3 = new JButton("3");
		Button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.setText(textField.getText().concat("3"));
			}
		});
		Button_3.setBounds(215, 99, 75, 75);
		Button_3.setFont(myFont);
		panel.add(Button_3);
		
		addButton = new JButton("+");
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				num1 = Double.parseDouble(textField.getText());
				operator = '+';
				textField.setText("");
			}
		});
		addButton.setBounds(300, 99, 75, 75);
		addButton.setFont(myFont);
		panel.add(addButton);
		
		Button_4 = new JButton("4");
		Button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.setText(textField.getText().concat("4"));
			}
		});
		Button_4.setBounds(44, 184, 75, 75);
		Button_4.setFont(myFont);
		panel.add(Button_4);
		
		Button_5 = new JButton("5");
		Button_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.setText(textField.getText().concat("5"));
			}
		});
		Button_5.setBounds(129, 184, 75, 75);
		Button_5.setFont(myFont);
		panel.add(Button_5);
		
		Button_6 = new JButton("6");
		Button_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.setText(textField.getText().concat("6"));
			}
		});
		Button_6.setBounds(215, 184, 75, 75);
		Button_6.setFont(myFont);
		panel.add(Button_6);
		
		subButton = new JButton("-");
		subButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(textField.getText().isEmpty()) {
					textField.setText("-");
				}else {
					num1 = Double.parseDouble(textField.getText());
					operator = '-';
					textField.setText("");
				}
			}
		});
		subButton.setBounds(300, 184, 75, 75);
		subButton.setFont(myFont);
		panel.add(subButton);
		
		Button_7 = new JButton("7");
		Button_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.setText(textField.getText().concat("7"));
			}
		});
		Button_7.setBounds(44, 269, 75, 75);
		Button_7.setFont(myFont);
		panel.add(Button_7);
		
		Button_8 = new JButton("8");
		Button_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.setText(textField.getText().concat("8"));
			}
		});
		Button_8.setBounds(129, 269, 75, 75);
		Button_8.setFont(myFont);
		panel.add(Button_8);
		
		Button_9 = new JButton("9");
		Button_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.setText(textField.getText().concat("9"));
			}
		});
		Button_9.setBounds(215, 269, 75, 75);
		Button_9.setFont(myFont);
		panel.add(Button_9);
		
		mulButton = new JButton("*");
		mulButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				num1 = Double.parseDouble(textField.getText());
				operator = '*';
				textField.setText("");
			}
		});
		mulButton.setBounds(300, 269, 75, 75);
		mulButton.setFont(myFont);
		panel.add(mulButton);
		
		decButton = new JButton(".");
		decButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.setText(textField.getText().concat("."));
			}
		});
		decButton.setBounds(44, 354, 75, 75);
		decButton.setFont(myFont);
		panel.add(decButton);
		
		Button_0 = new JButton("0");
		Button_0.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.setText(textField.getText().concat("0"));
			}
		});
		Button_0.setBounds(129, 354, 75, 75);
		Button_0.setFont(myFont);
		panel.add(Button_0);
		
		equButton = new JButton("=");
		equButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				num2 = Double.parseDouble(textField.getText());
				switch(operator) {
					case '+':
						result = num1 + num2;
						break;
					case '-':
						result = num1 - num2;
						break;
					case '*':
						result = num1 * num2;
						break;
					case '/':
						if(num2 == 0) {
							JOptionPane.showMessageDialog(panel, "You can't divide by 0!",
						               "Error!", JOptionPane.ERROR_MESSAGE);
						}else {
						result = num1 / num2;
						}
						break;	
				}
				textField.setText(String.valueOf(result));
				num1 = result;
			}
		});
		equButton.setBounds(215, 354, 75, 75);
		equButton.setFont(myFont);
		panel.add(equButton);
		
		divButton = new JButton("/");
		divButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				num1 = Double.parseDouble(textField.getText());
				operator = '/';
				textField.setText("");
			}
		});
		divButton.setBounds(300, 354, 75, 75);
		divButton.setFont(myFont);
		panel.add(divButton);
	}
}
