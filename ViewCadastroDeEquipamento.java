package br.com.senai.view;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JComboBox;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;

import br.com.senai.core.domain.Equipamento;
import br.com.senai.core.domain.Status;
import br.com.senai.core.service.EquipamentoService;

import javax.swing.JButton;

public class ViewCadastroDeEquipamento extends JFrame  {
	
	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JTextField textDescricaoCurta;
	private JTextField textGarantia;
	private JTextField textEspecificacoes;
	private JComboBox<String> cbStatus;
	
	private EquipamentoService service;
	private Equipamento equipamento;
	
	public ViewCadastroDeEquipamento() {
		setResizable(false);
		setTitle("Gerenciar Equipamentos - Cadastro");
		setName("frmCadastro");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 457, 291);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		this.service = new EquipamentoService();
		
		textDescricaoCurta = new JTextField();
		textDescricaoCurta.setBounds(26, 65, 214, 26);
		getContentPane().add(textDescricaoCurta);
		textDescricaoCurta.setColumns(10);
		
		JLabel lblDescricaoCurta = new JLabel("Descri\u00E7\u00E3o Curta*");
		lblDescricaoCurta.setBounds(26, 48, 131, 15);
		getContentPane().add(lblDescricaoCurta);
		
		textGarantia = new JTextField();
		textGarantia.setBounds(252, 65, 66, 26);
		getContentPane().add(textGarantia);
		textGarantia.setColumns(10);
		
		JLabel lblGarantia = new JLabel("Garantia*");
		lblGarantia.setBounds(252, 48, 70, 15);
		getContentPane().add(lblGarantia);
		
		cbStatus = new JComboBox<>();
		cbStatus.addItem("Selecione...");
		cbStatus.addItem("Recebido");
		cbStatus.addItem("Entregue");
		cbStatus.setBounds(330, 64, 84, 26);
		cbStatus.setSelectedIndex(1);
		cbStatus.setEnabled(true);
		getContentPane().add(cbStatus);
		
		JLabel lblStatus = new JLabel("Status");
		lblStatus.setBounds(330, 48, 70, 15);
		getContentPane().add(lblStatus);
		
		JLabel lblEspecificacoes = new JLabel("Especifica\u00E7\u00F5es*");
		lblEspecificacoes.setBounds(26, 102, 121, 15);
		getContentPane().add(lblEspecificacoes);
		
		textEspecificacoes = new JTextField();
		textEspecificacoes.setBounds(26, 120, 385, 86);
		getContentPane().add(textEspecificacoes);
		textEspecificacoes.setColumns(10);
		
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String descricaoCurta = textDescricaoCurta.getText();
					int garantia = Integer.parseInt(textGarantia.getText()); 
					String especificacoes = textEspecificacoes.getText();
			if (equipamento == null) {
				equipamento = new Equipamento(descricaoCurta, especificacoes, garantia);
			} else {
				Status status = null;
				int opcaoSelecionada = cbStatus.getSelectedIndex();
				if (opcaoSelecionada == 1) {
					status = Status.R;
				} else if (opcaoSelecionada == 2) {
					status = Status.E;
				}
				
				equipamento.setDescricaoCurta(descricaoCurta);
				equipamento.setGarantia(garantia);
				equipamento.setEspecificacoes(especificacoes);
				equipamento.setStatus(status);
			}  
				service.salvar(equipamento);
				cbStatus.setEnabled(false);
				JOptionPane.showMessageDialog(contentPane, "Equipamento salvo com sucesso!");
			} catch (NumberFormatException nfe) {
				JOptionPane.showMessageDialog(contentPane, "O campo de garantia só aceita valores inteiros");
			}catch (Exception ex) {
				JOptionPane.showMessageDialog(contentPane, ex.getMessage());
			}
					
			}
		});
		btnSalvar.setBounds(300, 217, 117, 25);
		getContentPane().add(btnSalvar);
		
		JButton btnConsultar = new JButton("Consultar");
		btnConsultar.setBounds(300, 11, 117, 25);
		btnConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ViewConsultaDeEquipamento view = new ViewConsultaDeEquipamento();
				view.setVisible(true);
				dispose();
			}
		});
		
		getContentPane().add(btnConsultar);
	}
	
	public void setEquipamento(Equipamento equipamento) {
		this.equipamento = equipamento;
		this.textDescricaoCurta.setText(equipamento.getDescricaoCurta());
		this.textGarantia.setText(String.valueOf(equipamento.getGarantia()));
		this.textEspecificacoes.setText(equipamento.getEspecificacoes());
		if (equipamento.getStatus() == Status.R) {
			this.cbStatus.setSelectedIndex(1);
			this.cbStatus.setEnabled(true);
		}else if (equipamento.getStatus() == Status.E) {
			this.cbStatus.setSelectedIndex(2);
			this.cbStatus.setEnabled(false);
		}		
	}
}
