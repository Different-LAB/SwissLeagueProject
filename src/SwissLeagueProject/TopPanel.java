package SwissLeagueProject;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

public class TopPanel extends JPanel implements ActionListener, KeyListener{

	public SwissLeagueFrame swissLeagueFrame;

	//변수
	int currectRound = 1;
	int total_Round = 0;
	int userNumber = 0;
	int count = 0;

	//ArrayList
	ArrayList<String> registerArray = new ArrayList();

	//Label
	JLabel round = new JLabel(currectRound + " Round");
	JLabel roundLabel = new JLabel("Round");

	//spinner
	SpinnerModel value = new SpinnerNumberModel(1, 1, 100, 1);
	JSpinner registerRound = new JSpinner(value);

	//Button
	JButton register = new JButton("등록");
	JButton startGame = new JButton("시작");
	JButton cancelRegister = new JButton("등록 취소");

	//Button Panel
	JPanel buttonPanel = new JPanel();

	//textField
	JTextField registerTextField = new JTextField("", 7);

	public TopPanel(SwissLeagueFrame swissLeagueFrame) {
		this.swissLeagueFrame = swissLeagueFrame;

		this.add(roundLabel);
		this.add(registerRound);
		this.add(registerTextField);
		this.add(register);
		this.add(startGame);
		this.add(round);

		//topPanel.buttonPanel
		buttonPanel.add(startGame);
		buttonPanel.add(cancelRegister);

		//setVistble(false)
		round.setVisible(false);
		buttonPanel.setVisible(false);

		//add ActionLister
		registerTextField.addKeyListener(this);
		register.addActionListener(this);
		startGame.addActionListener(this);
		cancelRegister.addActionListener(this);

	}

	//registerArray 배열에 이름 추가
	public void RegisterName() {
		String success = "T";

		if(registerTextField.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "이름을 입력해주세요");
		}
		else {
			if(registerArray.size() == 0) {
				JOptionPane.showMessageDialog(null, registerTextField.getText() + "님이 등록되었습니다.");
				registerArray.add(registerTextField.getText());
				swissLeagueFrame.registerPanel.addItem(registerTextField.getText());
				registerTextField.setText("");
				swissLeagueFrame.changeHeight(40);
				buttonPanel.setVisible(true);
			}
			else {
				for(int i = 0; i < registerArray.size(); i++) {
					if(registerArray.get(i).equals(registerTextField.getText())){
						success = "F";
						break;
					}
				}
				if(success == "T") {
					JOptionPane.showMessageDialog(null, registerTextField.getText() + "님이 등록되었습니다.");
					swissLeagueFrame.registerPanel.addItem(registerTextField.getText());
					registerArray.add(registerTextField.getText());
					registerTextField.setText("");
				}
				else {
					JOptionPane.showMessageDialog(null, "이미 등록된 이름입니다.");
				}
			}
		}
	}

	//registerArray에 배열 값 삭제
	public void Delete(int i) {
		boolean returned = registerArray.remove(registerArray.get(i));
	}

	//Label text 변경
	public void setText(int round, int i) {
		if(i == 0) {
			this.round.setText(round + " Round");
		}
		else {
			this.round.setText("경기 종료");
		}
	}

	//경기 시작
	public void StartGame() {
		total_Round = (int)registerRound.getValue();
		if(total_Round >= registerArray.size()) {
			JOptionPane.showMessageDialog(null, "인원 수가 부족합니다.");
			JOptionPane.showMessageDialog(null, "부족한 인원 수 : " + (total_Round + 1 - registerArray.size()));
		}
		else {
			JOptionPane.showMessageDialog(null, "총 라운드 : " + total_Round + " 참가자 인원 수 : " + registerArray.size());

			int hight = registerArray.size() * 30 + 150;

			// 첫 순위표, 대진표 뽑는 함수
			if(registerArray.size() % 2 == 0) {
				swissLeagueFrame.changePanel(hight, 0);
				swissLeagueFrame.gamePanel.RegisterList(registerArray, total_Round);
			}
			else {
				swissLeagueFrame.changePanel(hight, 1);
				swissLeagueFrame.gamePanelOdd.RegisterList(registerArray, total_Round);
			}

			round.setVisible(true);

			roundLabel.setVisible(false);
			registerRound.setVisible(false);
			registerTextField.setVisible(false);
			register.setVisible(false);
			buttonPanel.setVisible(false);
		}
	}

	//등록 취소 함수
	public void CancelRegister() {
		int selected = swissLeagueFrame.registerPanel.list.getSelectedIndex();
		int value = JOptionPane.showConfirmDialog(null, registerArray.get(selected) + "님의 등록을 취소하시겠습니까?", "등록 취소", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null);
		if(value == 0) {
			swissLeagueFrame.registerPanel.removeItem(selected);
			Delete(selected);
			if(registerArray.size() == 0) {
				swissLeagueFrame.changeHeight(-40);
				buttonPanel.setVisible(false);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//register Button
		if(e.getSource() == register) {
			RegisterName();
		}

		//startGame Button
		if(e.getSource() == startGame) {
			StartGame();
		}

		//cancelRegister Button
		if(e.getSource() == cancelRegister) {
			CancelRegister();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {}
	@Override
	public void keyPressed(KeyEvent e) {}
	@Override
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();

		//enter키 입력시 실행
		if(keyCode==KeyEvent.VK_ENTER) {
			RegisterName();
		}

		//F5키 입력시 실행
		if(keyCode==KeyEvent.VK_F5) {
			System.out.println("F5");
			StartGame();
		}
	}
}
