/*******************************************************************************
 * Copyright (C) 2016-2023 Dennis Cosgrove
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 ******************************************************************************/
package hof.filter.viz;

import edu.wustl.cse231s.viz.LookAndFeels;
import hof.filter.group.FilterUtils;
import hof.filter.viz.io.MedianRentInfoResource;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

/**
 * @author <a href="https://www.cse.wustl.edu/~dennis.cosgrove/">Dennis Cosgrove</a>
 */
public class MedianRentInfoApp extends JFrame {
	private static final long serialVersionUID = 1L;

	private static class InfoTableModel extends AbstractTableModel {
		private static final long serialVersionUID = 1L;

		private List<MedianRentInfo> infos;

		public InfoTableModel(List<MedianRentInfo> infos) {
			this.infos = infos;
		}

		@Override
		public String getColumnName(int columnIndex) {
			switch (columnIndex) {
			case 0:
				return "name";
			case 1:
				return "br1 rent";
			case 2:
				return "pop 2017";
			default:
				throw new Error(Integer.toString(columnIndex));
			}
		}

		@Override
		public java.lang.Class<?> getColumnClass(int columnIndex) {
			switch (columnIndex) {
			case 0:
				return String.class;
			case 1:
			case 2:
				return Integer.class;
			default:
				throw new Error(Integer.toString(columnIndex));
			}
		}

		@Override
		public int getColumnCount() {
			return 3;
		}

		@Override
		public int getRowCount() {
			return infos.size();
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			switch (columnIndex) {
			case 0:
				return infos.get(rowIndex).getAreaName();
			case 1:
				return infos.get(rowIndex).getBedroom1();
			case 2:
				return infos.get(rowIndex).getPopulation2017();
			default:
				throw new Error(Integer.toString(columnIndex));
			}
		}

		public void updateTableData(List<MedianRentInfo> infos) {
			this.infos = infos;
			fireTableDataChanged();
		}
	}

	private final List<MedianRentInfo> originalInfos;
	private final InfoTableModel tableModel;

	private final JLabel filterCodeLabel;
	private final JTextField areaNameTextField;
	private final JLabel rentLabel;
	private final JLabel popLabel;
	private final JSlider minRentSlider;
	private final JSlider maxRentSlider;
	private final JSlider minPopSlider;
	private final JSlider maxPopSlider;

	public MedianRentInfoApp(List<MedianRentInfo> originalInfos) {
		setTitle("Median Rent Filter");
		this.originalInfos = originalInfos;

		filterCodeLabel = new JLabel();
		filterCodeLabel.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 18));

		tableModel = new InfoTableModel(originalInfos);
		JTable table = new JTable(tableModel);
		TableColumn column0 = table.getColumnModel().getColumn(0);
		column0.setPreferredWidth(column0.getPreferredWidth() * 8);
		table.setPreferredScrollableViewportSize(table.getPreferredSize());
		table.setFillsViewportHeight(true);
		JScrollPane scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		areaNameTextField = new JTextField();
		areaNameTextField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				updateUserInterface();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				updateUserInterface();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				updateUserInterface();
			}
		});
		final int MAX_RENT = 3_000;
		minRentSlider = createSlider(JSlider.HORIZONTAL, 0, MAX_RENT, 0, 100, "%d");
		maxRentSlider = createSlider(JSlider.HORIZONTAL, 0, MAX_RENT, MAX_RENT, 100, "%d");
		final int MAX_POP = 10_000_000;
		minPopSlider = createSlider(JSlider.HORIZONTAL, 0, MAX_POP, 0, 200_000, "%d");
		maxPopSlider = createSlider(JSlider.HORIZONTAL, 0, MAX_POP, MAX_POP, 200_000, "%d");

		JLabel nameLabel = new JLabel("name: ");
		JLabel minLabel = new JLabel("min: ");
		JLabel maxLabel = new JLabel("max: ");
		JLabel codeLabel = new JLabel("code: ");
		nameLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		minLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		maxLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		codeLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		codeLabel.setVerticalAlignment(SwingConstants.TOP);

		rentLabel = new JLabel();
		rentLabel.setHorizontalAlignment(SwingConstants.CENTER);
		popLabel = new JLabel();
		popLabel.setHorizontalAlignment(SwingConstants.CENTER);

		JPanel controlPane = new JPanel();
		controlPane.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
		controlPane.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		gbc.insets = new Insets(0, 0, 32, 16);
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 0.0;
		gbc.gridx = 0;
		controlPane.add(nameLabel, gbc);
		gbc.weightx = 1.0;
		gbc.gridx = 1;
		gbc.gridwidth = 2;
		controlPane.add(areaNameTextField, gbc);
		gbc.gridwidth = 1;

		gbc.insets = new Insets(0, 0, 0, 16);
		gbc.gridy = 1;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 0.0;
		gbc.gridx = 0;
		controlPane.add(new JLabel(), gbc);
		gbc.weightx = 1.0;
		gbc.gridx = 1;
		controlPane.add(rentLabel, gbc);
		gbc.gridx = 2;
		controlPane.add(popLabel, gbc);

		gbc.gridy = 2;
		gbc.weightx = 0.0;
		gbc.gridx = 0;
		controlPane.add(minLabel, gbc);
		gbc.weightx = 1.0;
		gbc.gridx = 1;
		controlPane.add(minRentSlider, gbc);
		gbc.gridx = 2;
		controlPane.add(minPopSlider, gbc);

		gbc.gridy = 3;
		gbc.weightx = 0.0;
		gbc.gridx = 0;
		controlPane.add(maxLabel, gbc);
		gbc.weightx = 1.0;
		gbc.gridx = 1;
		controlPane.add(maxRentSlider, gbc);
		gbc.gridx = 2;
		controlPane.add(maxPopSlider, gbc);

		gbc.insets = new Insets(48, 0, 0, 16);
		gbc.gridy = 4;
		gbc.weightx = 0.0;
		gbc.gridx = 0;
		controlPane.add(codeLabel, gbc);
		gbc.gridx = 1;
		gbc.gridwidth = 2;

		controlPane.add(filterCodeLabel, gbc);

		getContentPane().add(controlPane, BorderLayout.PAGE_START);
		getContentPane().add(scrollPane, BorderLayout.CENTER);

		updateUserInterface();
	}

	private void updateUserInterface() {
		String areaNameText = areaNameTextField.getText();

		int minRent = minRentSlider.getValue();
		int maxRent = maxRentSlider.getValue();
		rentLabel.setText(String.format("%d <= rent <= %d", minRent, maxRent));

		int minPop = minPopSlider.getValue();
		int maxPop = maxPopSlider.getValue();
		String minPopText = NumberFormat.getNumberInstance(Locale.US).format(minPop);
		String maxPopText = NumberFormat.getNumberInstance(Locale.US).format(maxPop);
		popLabel.setText(String.format("%s <= population <= %s", minPopText, maxPopText));

		String indent = "&nbsp;&nbsp;&nbsp;&nbsp;";

		String sb = "<html>" +
				"results = filter(rentInfos, <strong>(rentInfo) -> {" +
				"<br/>" +
				indent + "if areaName.contains(\"%s\") {" + "<br/>" +
				indent + indent + "if %d &lt;= rent &lt;= %d {" + "<br/>" +
				indent + indent + indent + "return %d &lt;= pop &lt;= %d" + "<br/>" +
				indent + indent + "}" + "<br/>" +
				indent + "}" + "<br/>" +
				indent + "return false" + "<br/>" +
				"}</strong>);" +
				"</html>";
		String text = String.format(sb, areaNameText, minRent, maxRent, minPop, maxPop);
		filterCodeLabel.setText(text);

		List<MedianRentInfo> filteredInfos = FilterUtils.filter((info) -> {
			if (areaNameText.trim().length() == 0
					|| info.getAreaName().toLowerCase().contains(areaNameText.toLowerCase())) {
				int rent = info.getBedroom1();
				int pop = info.getPopulation2017();
				if (minRent <= rent) {
					if (rent <= maxRent) {
						if (minPop <= pop) {
							return pop <= maxPop;
						}
					}
				}
			}
			return false;
		}, originalInfos);
		tableModel.updateTableData(filteredInfos);

	}

	private JSlider createSlider(int orientation, int min, int max, int value, int minorTickSpacing, String format) {
		JSlider slider = new JSlider(orientation, min, max, value);
		slider.setMinorTickSpacing(minorTickSpacing);
		slider.setPaintTicks(true);
		slider.addChangeListener((e) -> updateUserInterface());

		//		Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
		//		labelTable.put(min, new JLabel(String.format(format, min)));
		//		labelTable.put(max, new JLabel(String.format(format, max)));
		//		slider.setLabelTable(labelTable);
		//		slider.setPaintLabels(true);
		return slider;
	}

	private static void createAndShowGUI(List<MedianRentInfo> infos) {
		LookAndFeels.attemptNimbusLookAndFeel();
		JFrame frame = new MedianRentInfoApp(infos);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) throws IOException {
		List<MedianRentInfo> infos = MedianRentInfoResource.AREA_2019.getMedianRentInfos();
		SwingUtilities.invokeLater(() -> MedianRentInfoApp.createAndShowGUI(infos));
	}
}
