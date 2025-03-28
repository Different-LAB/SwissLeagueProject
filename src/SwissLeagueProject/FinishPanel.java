package SwissLeagueProject;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class FinishPanel extends JPanel implements ActionListener{
	public SwissLeagueFrame swissLeagueFrame;
	//List
	String[] itemList = {"이름", "번호", "승점", "승리 횟수", "버치", "버그"};

	//Label
	JLabel[] itemLabel = new JLabel[itemList.length];

	//Panel
	JPanel resultWindow = new JPanel();
	JPanel buttonPanel = new JPanel();

	//Button
	JButton exitProgram = new JButton("프로그램 종료");
	JButton returnButton = new JButton("처음으로");

	public FinishPanel(SwissLeagueFrame swissLeagueFrame) {
		this.swissLeagueFrame = swissLeagueFrame;

		//finishPanel
		this.setLayout(new BorderLayout());
		this.add(resultWindow, BorderLayout.CENTER);
		this.add(buttonPanel, BorderLayout.SOUTH);

		//resultWindowPanel
		resultWindow.setLayout(new GridLayout(0, 6, 10, 10));
		resultWindow.setBorder(BorderFactory.createTitledBorder("최종 결과"));

		//buttonPanel
		buttonPanel.add(exitProgram);
		buttonPanel.add(returnButton);
		exitProgram.addActionListener(this);
		returnButton.addActionListener(this);
	}

	public void printResult(String[] people, double[][] total) {

		for(int i = 0; i < itemList.length; i++) {
			itemLabel[i] = new JLabel(itemList[i]);
			resultWindow.add(itemLabel[i]);
		}

		//결과 입력
		for(int i = 0; i < people.length; i++) {
			resultWindow.add(new JLabel(people[(int)Math.round(total[i][0])]));
			for(int j = 0; j < total[i].length; j++) {
				resultWindow.add(new JLabel(total[i][j] + ""));
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//프로그램 종료 버튼
		if(e.getSource() == exitProgram) {
			swissLeagueFrame.dispose();
		}

		//프로그램 재시작 버튼
		if(e.getSource() == returnButton) {
			SwissLeagueFrame swissLeagueFrame = new SwissLeagueFrame();
			this.swissLeagueFrame.dispose();
		}
	}

}
