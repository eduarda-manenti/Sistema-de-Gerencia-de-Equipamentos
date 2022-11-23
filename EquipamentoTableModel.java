package br.com.senai.view.componentes.table;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.com.senai.core.domain.Equipamento;

public class EquipamentoTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private final int QTDE_COLUNAS = 2;
	private List<Equipamento> equipamentos;

	public EquipamentoTableModel(List<Equipamento> equipamentos) {
		this.equipamentos = equipamentos;
	}

	@Override
	public int getColumnCount() {
		return QTDE_COLUNAS;
	}

	public String getColumnName(int column) {
		if (column == 0) {
			return "ID";
		} else if (column == 1) {
			return "Descrição Curta";
		}
		throw new IllegalArgumentException("Indíce inválido");
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (columnIndex == 0) {
			return equipamentos.get(rowIndex).getId();
		} else if (columnIndex == 1) {
			return equipamentos.get(rowIndex).getDescricaoCurta();
		}
		throw new IllegalArgumentException("Índice inválido!");
	}

	@Override
	public int getRowCount() {
		return equipamentos.size();

	}

	public Equipamento getPor(int rowIndex) {
		return equipamentos.get(rowIndex);
	}

	public Equipamento removerPor(int rowIndex) {
		return equipamentos.remove(rowIndex);
	}

}
