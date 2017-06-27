package panels;

import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JLabel;
import net.miginfocom.swing.MigLayout;

import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import entities.Tree;
import entities.Wind;

import javax.swing.JCheckBox;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.JButton;

public class SettingsPane extends JPanel {

	private DecimalFormat decimal = new DecimalFormat("#.##");
	private DecimalFormat integer = new DecimalFormat("#");
	
	private JTextField windSpeed;
	private JTextField windFreq;
	private JTextField windStrength;
	private JTextField treeWidth;
	private JTextField treeLength;
	private JTextField branchOccurrence;
	private JTextField leafOccurrence;
	private JTextField treeDensity;
	private JTextField treeCurve;
	
	public SettingsPane(Wind wind, Tree tree, TreePane pane) {
		setLayout(new MigLayout("", "[][][]", "[31px][][][][][][][][][][][][][][][][][][]30[]"));
		
		JSlider windSlider = new JSlider();
		JSlider windFreqSlider = new JSlider(1, 100, (int)wind.getInterval());
		JSlider windStrengthSlider = new JSlider(-1000, 1000, (int)wind.getWindMod());
		JSlider widthSlider = new JSlider(1, 30, tree.getMaxWidth());
		JSlider lengthSlider = new JSlider(40, 80, tree.getMaxLength());
		JSlider branchSlider = new JSlider(0, 100, (int)(tree.getBranchOccurrence() * 100));
		JSlider leafSlider = new JSlider(0, 100, (int)(tree.getLeafOccurrence() * 100));
		JSlider densitySlider = new JSlider();
		densitySlider.setEnabled(false);
		JSlider curveSlider = new JSlider(10, 60, tree.getTreeCurve());
		
		windSpeed = new JTextField(Double.toString(wind.getSpeed()));
		windSpeed.setEnabled(false);
		
		windFreq = new JTextField(Double.toString(wind.getInterval()));
		windFreq.setText(integer.format(Double.parseDouble(windFreq.getText())));
		windFreq.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				windFreq.setText(integer.format(Double.parseDouble(windFreq.getText())));
				wind.setInterval(Integer.parseInt(windFreq.getText()));
				windFreqSlider.setValue((int)wind.getInterval());
			}
		});
		windStrength = new JTextField(Double.toString(wind.getWindMod()));
		windStrength.setText(integer.format(Double.parseDouble(windStrength.getText())));
		windStrength.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				windStrength.setText(integer.format(Double.parseDouble(windStrength.getText())));
				wind.setWindMod(Integer.parseInt(windStrength.getText()));
				windStrengthSlider.setValue((int)wind.getWindMod());
			}
		});
		treeWidth = new JTextField(Double.toString(tree.getMaxWidth()));
		treeWidth.setText(integer.format(Double.parseDouble(treeWidth.getText())));
		treeWidth.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				treeWidth.setText(integer.format(Double.parseDouble(treeWidth.getText())));
				tree.setMaxWidth(Integer.parseInt(treeWidth.getText()));
				widthSlider.setValue(tree.getMaxWidth());
				pane.reGrow();
			}
		});
		treeLength = new JTextField(Double.toString(tree.getMaxLength()));
		treeLength.setText(integer.format(Double.parseDouble(treeLength.getText())));
		treeLength.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				treeLength.setText(integer.format(Double.parseDouble(treeLength.getText())));
				tree.setMaxLength(Integer.parseInt(treeLength.getText()));
				lengthSlider.setValue(tree.getMaxLength());
				pane.reGrow();
			}
		});
		branchOccurrence = new JTextField(Double.toString(tree.getBranchOccurrence()));
		branchOccurrence.setText(decimal.format(Double.parseDouble(branchOccurrence.getText())));
		branchOccurrence.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				branchOccurrence.setText(decimal.format(Double.parseDouble(branchOccurrence.getText())));
				tree.setBranchOccurrence(Double.parseDouble(branchOccurrence.getText()));
				branchSlider.setValue((int)(tree.getBranchOccurrence() * 100));
				pane.reGrow();
			}
		});
		leafOccurrence = new JTextField(Double.toString(tree.getLeafOccurrence()));
		leafOccurrence.setText(decimal.format(Double.parseDouble(leafOccurrence.getText())));
		leafOccurrence.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				leafOccurrence.setText(decimal.format(Double.parseDouble(leafOccurrence.getText())));
				tree.setLeafOccurrence(Double.parseDouble(leafOccurrence.getText()));
				leafSlider.setValue((int)(tree.getLeafOccurrence() * 100));
				pane.reGrow();
			}
		});
		treeDensity = new JTextField(Double.toString(tree.getTreeDensity()));
		treeDensity.setText(decimal.format(Double.parseDouble(treeDensity.getText())));
		treeDensity.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				treeDensity.setText(decimal.format(Double.parseDouble(treeDensity.getText())));
				tree.setTreeDensity(Double.parseDouble(treeDensity.getText()));
				densitySlider.setValue((int)tree.getTreeDensity() * 100);
				pane.reGrow();
			}
		});
		treeDensity.setEnabled(false);
		treeCurve = new JTextField(Double.toString(tree.getTreeCurve()));
		treeCurve.setText(integer.format(Double.parseDouble(treeCurve.getText())));
		treeCurve.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				treeCurve.setText(integer.format(Double.parseDouble(treeCurve.getText())));
				tree.setTreeCurve(Integer.parseInt(treeCurve.getText()));
				curveSlider.setValue(tree.getTreeCurve());
				pane.reGrow();
			}
		});
		
		JTextField[] numbers = new JTextField[]{windSpeed, windFreq, windStrength, treeWidth, treeLength, branchOccurrence, leafOccurrence, treeDensity, treeCurve};
		for(JTextField jtf : numbers) {
			jtf.setColumns(10);
		}

		JLabel lblWindSpeedText = new JLabel("Wind Speed");
		JLabel lblWindFreq = new JLabel("Wind change freq");
		JLabel lblWindStrength = new JLabel("Wind strength");
		JLabel lblTreeWidth = new JLabel("Tree width");
		JLabel lblTreeLength = new JLabel("Tree length");
		JLabel lblBranchOccurrence = new JLabel("Branch occurrence");
		JLabel lblLeafOccurrence = new JLabel("Leaf occurrence");
		JLabel lblTreeDensity = new JLabel("Tree density");
		JLabel lblTreeCurve = new JLabel("Tree curve");
		JLabel[] labels = new JLabel[]{lblWindSpeedText, lblWindFreq, lblWindStrength, lblTreeWidth, lblTreeLength, lblBranchOccurrence, lblLeafOccurrence, lblTreeDensity, lblTreeCurve};
		
		windSlider.setEnabled(false);
		windFreqSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				windFreq.setText(Integer.toString(windFreqSlider.getValue()));
				for(ActionListener al : windFreq.getActionListeners()) al.actionPerformed(null);
			}
		});
		windStrengthSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				windStrength.setText(Integer.toString(windStrengthSlider.getValue()));
				for(ActionListener al : windStrength.getActionListeners()) al.actionPerformed(null);
			}
		});
		widthSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				treeWidth.setText(Integer.toString(widthSlider.getValue()));
				for(ActionListener al : treeWidth.getActionListeners()) al.actionPerformed(null);
			}
		});
		lengthSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				treeLength.setText(Integer.toString(lengthSlider.getValue()));
				for(ActionListener al : treeLength.getActionListeners()) al.actionPerformed(null);
			}
		});
		branchSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				branchOccurrence.setText(Double.toString(((double)branchSlider.getValue() / 100)));
				for(ActionListener al : branchOccurrence.getActionListeners()) al.actionPerformed(null);
			}
		});
		leafSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				leafOccurrence.setText(Double.toString((double)leafSlider.getValue() / 100));
				for(ActionListener al : leafOccurrence.getActionListeners()) al.actionPerformed(null);
			}
		});
		densitySlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				treeDensity.setText(Integer.toString(densitySlider.getValue() / 100));
				for(ActionListener al : treeDensity.getActionListeners()) al.actionPerformed(null);
			}
		});
		curveSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				treeCurve.setText(Integer.toString(curveSlider.getValue()));
				for(ActionListener al : treeCurve.getActionListeners()) al.actionPerformed(null);
			}
		});
		JSlider[] sliders = new JSlider[]{windSlider, windFreqSlider, windStrengthSlider, widthSlider, lengthSlider, branchSlider, leafSlider, densitySlider, curveSlider};

		int row = 1;
		String str = "";
		for(int i=0; i<9; i++) {
			str = "cell " + 0 + " " + row;
			add(numbers[i], str);
			str = "cell " + 1 + " " + row + ", alignx right";
			add(labels[i], str);
			row++;
			str = "cell " + 0 + " " + row + " 2 1";
			add(sliders[i], str);
			row++;
		}
		
		JCheckBox windPos = new JCheckBox("");
//		windPos.setEnabled(false);
		windPos.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(windPos.isSelected()) {
					wind.setPosOnly(true);
				} else {
					wind.setPosOnly(false);
				}
			}
		});
		add(windPos, "cell 0 0");
		JLabel lblWindPositive = new JLabel("Wind = positive only");
		add(lblWindPositive, "cell 1 0");
		
		JButton btnRerollSeed = new JButton("Reroll seed");
		btnRerollSeed.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				pane.reGrow();
			}
			
		});
		add(btnRerollSeed, "cell 1 21");
		
	}
	
	public void setWindSpeed(double speed) {
		windSpeed.setText(decimal.format(speed));
	}

}
