import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainPanel {

	public static void main(String[] args) {
		new MainPanel();
	}

	private JFrame frame;

	public MainPanel() {
		frame = new JFrame("Soduku Solver");
		frame.setVisible(true);
		frame.setSize(300, 325);
		frame.setMinimumSize(new Dimension(300, 325));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Container container = frame.getContentPane();
		container.setLayout(new BorderLayout());

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(9, 9, 2, 2));
		NumberBox.fillPanelWithBoxes(panel);
		container.add(panel, BorderLayout.CENTER);

		JButton button = new JButton("Solve");
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				solve();
			}
		});
		container.add(button, BorderLayout.SOUTH);
		// SpadesBox.setUpPuzzle1();
		frame.revalidate();
	}

	private void solve() {
		System.out.println("Solving");
		if (NumberBox.isSolved()) {
			printSolvedState();
		}
		NumberBox box = NumberBox.boxWithLeastOptions();
		if (box == null) {
			System.out.println("Box == null");
			return;
		}
		ArrayList<Integer> options = box.getOptions();
		for (Integer i : options) {
			System.out.println("Looking at " + i);
			box.setValue(i);
			solve();
		}
		box.setValue(0);
	}

	private void printSolvedState() {
		JFrame frame = new JFrame();
		frame.setVisible(true);
		frame.getContentPane().setLayout(new GridLayout(9, 9));
		frame.setSize(300, 300);
		NumberBox[][] boxes = NumberBox.getBoxes();
		for (NumberBox[] list : boxes) {
			for (NumberBox box : list) {
				frame.getContentPane().add(new JLabel(box.getValue() + ""));
			}
		}
	}
}
