package SwissLeagueProject;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class SwissLeagueFrame extends JFrame implements FrameSize{

	TopPanel topPanel = new TopPanel(this);
	RegisterPanel registerPanel = new RegisterPanel(this);
	GamePanel gamePanel = new GamePanel(this);
	GamePanelOdd gamePanelOdd = new GamePanelOdd(this);
	FinishPanel finishPanel = new FinishPanel(this);

	//frameSize Value
	public int height = 275;
	public int width = 400;

	public SwissLeagueFrame() {
		this.setTitle("SwissLeague Program");
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocation(300, 200);
		this.setSize(width, height);

		this.add(topPanel, BorderLayout.NORTH);
		this.add(registerPanel, BorderLayout.CENTER);
		this.add(topPanel.buttonPanel, BorderLayout.SOUTH);

		this.setVisible(true);
	}

	//Change Frame Size
	public void changeHeight(int i) {
		height += i;
		this.setSize(400, height);
	}

	//registerPanel Delete, add gamePanel & Button Panel
	public void changePanel(int i, int j) {
		height = i;
		width = 450;
		this.setSize(width, height);

		this.remove(registerPanel);

		if(j == 0) {
			this.add(gamePanel,BorderLayout.CENTER);
		}
		else if(j == 1) {
			this.add(gamePanelOdd,BorderLayout.CENTER);
		}

	}

	//finish
	public void result() {
		this.remove(gamePanel);
		this.add(finishPanel, BorderLayout.CENTER);
	}
	public void resultOdd() {
		this.remove(gamePanelOdd);
		this.add(finishPanel, BorderLayout.CENTER);
	}

}
