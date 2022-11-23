package br.com.senai.view;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import br.com.senai.core.domain.Equipamento;
import br.com.senai.core.service.EquipamentoService;
import br.com.senai.view.componentes.table.EquipamentoTableModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JScrollBar;
import javax.swing.JTable;
import javax.swing.JScrollPane;

public class ViewConsultaDeEquipamento extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private EquipamentoService service;
	private JPanel contentPane;
	private JTextField textFiltro;
	private JTable tableResultados;
	
	public ViewConsultaDeEquipamento() {
		setResizable(false);
		setTitle("Gerenciar Contatos - Consulta");
		setName("frmConsulta");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 491, 290);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		this.service = new EquipamentoService();
		
		textFiltro = new JTextField();
		textFiltro.setBounds(23, 46, 215, 26);
		getContentPane().add(textFiltro);
		textFiltro.setColumns(10);
		
		JLabel lblFiltro = new JLabel("Filtro");
		lblFiltro.setBounds(23, 30, 70, 15);
		lblFiltro.setHorizontalAlignment(SwingConstants.LEFT);
		getContentPane().add(lblFiltro);
		
		JButton btnListar = new JButton("Listar");
		btnListar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					List<Equipamento> equipamentos = service.listarPor(textFiltro.getText());
					EquipamentoTableModel model = new EquipamentoTableModel(equipamentos);
					tableResultados.setModel(model);
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(contentPane, ex.getMessage());
				}
			}
			
		});
		btnListar.setBounds(248, 47, 117, 25);
		getContentPane().add(btnListar);
		
		EquipamentoTableModel model = new EquipamentoTableModel(new ArrayList<Equipamento>());
		tableResultados = new JTable(model);
		JScrollPane spResultados = new JScrollPane(tableResultados);
		spResultados.setBounds(23, 102, 446, 107);
		getContentPane().add(spResultados);
		
		
		JLabel lblResultados = new JLabel("Resultados");
		lblResultados.setBounds(23, 83, 64, 15);
		lblResultados.setHorizontalAlignment(SwingConstants.TRAILING);
		getContentPane().add(lblResultados);
		
		JButton btnRemover = new JButton("Remover");
		btnRemover.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int linhaSelecionada = tableResultados.getSelectedRow();
				if (linhaSelecionada >= 0) {
					int opcao = JOptionPane.showConfirmDialog(contentPane,"Deseja remover"
							+ " o item selecionado?" );
				if (opcao == 0) {
					EquipamentoTableModel model = (EquipamentoTableModel)tableResultados.getModel();
					Equipamento equipamentoSelecionado = model.getPor(linhaSelecionada);
				try {
					service.remover(equipamentoSelecionado.getId());
					model.removerPor(linhaSelecionada);
					tableResultados.updateUI();
					JOptionPane.showMessageDialog(contentPane, "Equipamento removido com sucesso!");
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(contentPane, ex.getMessage());
				}
				} 
				} else {
					JOptionPane.showMessageDialog(contentPane, "Selecione uma linha para remoção.");
				}
			}
		});
		btnRemover.setBounds(347, 213, 117, 25);
		getContentPane().add(btnRemover);
		
		JButton btnEditar = new JButton("Editar");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			 int linhaSelecionada = tableResultados.getSelectedRow();
			 if (linhaSelecionada >= 0) {
				EquipamentoTableModel model = (EquipamentoTableModel)tableResultados.getModel();
				Equipamento equipamentoSelecionado = model.getPor(linhaSelecionada);
				ViewCadastroDeEquipamento view = new ViewCadastroDeEquipamento();
				view.setEquipamento(equipamentoSelecionado);
				view.setVisible(true);
				dispose();
			} else {
				JOptionPane.showMessageDialog(contentPane, "Selecione uma linha para editar.");
			}
			}
		});
		btnEditar.setBounds(228, 213, 117, 25);
		getContentPane().add(btnEditar);
		
		JButton btnAdicionar = new JButton("Adicionar");
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			ViewCadastroDeEquipamento view = new ViewCadastroDeEquipamento();
			view.setVisible(true);
			dispose();
				
			}
		});
		btnAdicionar.setBounds(347, 11, 117, 25);
		getContentPane().add(btnAdicionar);
	
	}
} 
