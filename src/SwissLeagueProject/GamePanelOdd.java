package SwissLeagueProject;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class GamePanelOdd extends JPanel implements ActionListener, ItemListener {
	public SwissLeagueFrame swissLeagueFrame;

	//Panel
	public JPanel rankingPanel = new JPanel();
	public JPanel matchPanel = new JPanel();
	public JPanel buttonPanel = new JPanel();

	//변수
	public int round_now = 0;
	public int total_Round;
	public int[] first; // 인덱스 번째 대진에서 왼쪽 선수번호
	public int[] second; // 인덱스 번째 대진에서 오른쪽 선수번호
	public int people_num;
	public int buch_index = 3, berg_index = 4;
	public int round = 1;

	//List
	double[][] total;
	double[][] rank;
	String[] people;
	int[][] record_fight;
	int[] record_rd;

	//JLabel
	public JLabel[] matchList;
	public JLabel[] rankingList;

	//Button
	JButton nextButton = new JButton("라운드 종료");
	JButton finishButton = new JButton("경기 종료");

	//random
	Random rd = new Random();

	//승패 결정하는 라디오 버튼
	public JRadioButton[] left_win;
	public JRadioButton[] drow;
	public JRadioButton[] right_win;
	ButtonGroup[] bg;



	public GamePanelOdd(SwissLeagueFrame swissLeagueFrame) {
		this.swissLeagueFrame = swissLeagueFrame;

		//GamePanel
		this.setLayout(new BorderLayout());
		this.add(rankingPanel, BorderLayout.WEST);
		this.add(matchPanel, BorderLayout.CENTER);
		this.add(buttonPanel, BorderLayout.SOUTH);

		//rankingPanel
		rankingPanel.setLayout(new GridLayout(0, 2, 5, 5));
		rankingPanel.setBorder(BorderFactory.createTitledBorder("랭킹"));

		//matchPanel
		matchPanel.setLayout(new GridLayout(0, 3, 5, 5));
		matchPanel.setBorder(BorderFactory.createTitledBorder("대진표"));

		//ButtonPanel
		buttonPanel.add(nextButton);
		buttonPanel.add(finishButton);
		nextButton.addActionListener(this);
		finishButton.addActionListener(this);
		nextButton.setVisible(false);
		finishButton.setVisible(false);
	}

	public void RegisterList(ArrayList list, int value) {
		//SwissLeague Info
		total_Round = value;
		people_num = list.size();

		//List reset
		people = new String[people_num];
		total = new double[people_num][value + 5]; // 선수번호, 라운드별 점수, 승점, 승리횟수, 버치, 버그
		rank = new double[people_num][5]; // 선수번호, 승점, 승리횟수, 버치, 버그
		record_rd = new int[people_num];
		record_fight = new int[people_num][total_Round];
		matchList = new JLabel[people_num];
		rankingList = new JLabel[people_num];

		//대진 인원 엄 나중에 설명
		first = new int[people_num];
		second = new int[people_num];

		//승패 결정하는 라디오 버튼
		left_win = new JRadioButton[people_num];
		drow = new JRadioButton[people_num];
		right_win = new JRadioButton[people_num];
		bg = new ButtonGroup[people_num];

		if(value == 1) {
			finishButton.setVisible(true);
		}
		else {
			nextButton.setVisible(true);
		}

		//함수 값 초기화
		for(int i = 0; i < list.size(); i++) {
			//대국 전 value 값 -1로 초기화
			record_rd[i] = -1;
			//최종 대국 표에 번호 추가
			total[i][0] = i;
			//참가자 이름 추가
			people[i] = (String)list.get(i);
		}

		//Game Start
		start_game(/*people, record_rd, rank, record_fight, total, round_now*/);
	}

	//첫 대진표 짜는 함수
	public void start_game(/*String[] people, int[] record_rd, double[][] rank, int[][] record_fight, double[][] total, int round_now*/) {
		int random;
		Random rd = new Random();
		int check = 0;
		for(int i = 0; i < record_rd.length; i++) {
			record_rd[i] = -1;
		}

		for(int i = 0; i < record_fight.length; i++) {
			for(int j = 0; j < total_Round; j++) {
				record_fight[i][j] = -1;
			}
		}
		//첫 순위표
		while(true) {// 대결 순서를 정하기 위해 무한반복
			check = 0;
			random = rd.nextInt(people.length);

			// 인원수 만큼 랜덤 숫자를 다 뽑았는지 확인
			for(int i = 0; i < people.length; i++) {
				if(record_rd[i] != -1) {
					check++;
				}
			}

			// 인원수 만큼 랜덤 숫자를 다 배치했으면 순서를 출력하고 반복문 탈출
			if(check == people.length) {
				for(int i = 0; i < people.length; i++) {
					rankingList[i] = new JLabel(people[record_rd[i]]);
					rankingPanel.add(new JLabel((i + 1) + ". "));
					rankingPanel.add(rankingList[i]);

					System.out.println( (i + 1) + ". " + people[record_rd[i]]);
				}
				break;
			}

			for(int j = 0; j < people.length; j++) {
				// 이미 뽑은 숫자라면 다시 뽑기위해 현재 반복문 탈출
				if(record_rd[j] == random) {
					break;
				}
				// j번째 선수가 아직 안정해 졌다면 선수 번호인 random값을 할당
				else if(record_rd[j] == -1) {
					record_rd[j] = random;
					break;
				}
			}
		}

		//첫 대진표
		check = 0; //라디오 버튼 출력을 위한 변수 밖으로 빼지마!!
		for(int i = 0; i < people_num; i++) {
			// 대진인원 왼쪽에 작고 first vs second[check] 로 표시
			if(i % 2 == 0) {
				first[check] = record_rd[i];
				matchList[i] = new JLabel(people[first[check]]);
				rank[first[check]][0] = first[check];
				matchPanel.add(matchList[i]);
				//맨 마지막 순번이 아니라면 vs출력
				if(i != people_num - 1) {
					matchPanel.add(new JLabel("vs"));
				}
				//맨 마지막 순번이라면 vs대신 부전승을 출력하여 표시
				else {
					matchPanel.add(new JLabel("부전승"));
				}
			}
			// i가 1일때 오른쪽 인원의 값을 받아서 표시함
			// 여기서 승패를 가리고 순위를 나눠야 하는 rank배열에 승점,승리횟수,버치값,버그값 입력
			else {
				second[check] = record_rd[i];
				matchList[i] = new JLabel(people[second[check]]);
				matchPanel.add(matchList[i]);

				//승패 결과 라디오 버튼
				left_win[check] = new JRadioButton(people[first[check]] + " 승리");
				drow[check] = new JRadioButton("무승부");
				right_win[check] = new JRadioButton(people[second[check]] + " 승리");
				bg[check] = new ButtonGroup();
				bg[check].add(left_win[check]);
				bg[check].add(drow[check]);
				bg[check].add(right_win[check]);
				matchPanel.add(left_win[check]);
				matchPanel.add(drow[check]);
				matchPanel.add(right_win[check]);
				left_win[check].addItemListener(this);
				drow[check].addItemListener(this);
				right_win[check].addItemListener(this);

				rank[second[check]][0] = second[check];

				check++;
			}
		}


	}

	//Buch
	public void buch(double[][] total, int[][] record_fight, int round_now, int first, int second, double[][] rank) {
		//          모든 기록이 들어가있는 배열, 대진 기록           ,  현재 라운드     ,  왼쪽 선수번호, 오른쪽 선수 번호,  정렬 돌이기 위한 배열
		double sum = 0;
		for(int i = 0; i < round_now + 1; i++) {
			//first는 왼쪽 선수의 선수번호
			// total[왼쪽 선수번호 인덱스에서 0 라운드에 만난 선수 번호][총 라운드 + 1]
			if(record_fight[first][i] != -1) {
				sum += total[record_fight[first][i]][total_Round + 1];
			}

		}
		for(int i = 0; i < people_num; i++) {
			// rank에서 왼쪽 선수번호를 가진 인덱스 i 찾기
			if(rank[i][0] == first) {
				rank[i][buch_index] = sum; // 왼쪽 선수의 버치값
			}
		}

		sum = 0;
		if(second != -1) {
			for(int i = 0; i < round_now + 1; i++) {
				// second는 오른쪽 선수의 선수번호
				// total[오른쪽 선수번호 인덱스에서 0 라운드에 만난 선수 번호][총 라운드 + 1]

				if(record_fight[second][i] != -1) {
					sum += total[record_fight[second][i]][total_Round + 1];
				}
			}
			for(int i = 0; i < people_num; i++) {
				// rank에서 오른쪽 선수번호를 가진 인덱스 i 찾기
				if(rank[i][0] == second) {
					rank[i][buch_index] = sum; // 오른쪽 선수의 버치값
				}
			}
		}

	}

	//berg
	public void berg(double[][] total, int[][] record_fight, int round_now, int first, int second, double[][] rank) {
		//      모든 기록이 들어가있는 배열, 대진 기록           ,  현재 라운드     ,  왼쪽 선수번호, 오른쪽 선수 번호,  정렬 돌이기 위한 배열
		double sum = 0;
		for(int i = 0; i < round_now + 1; i++) {
			if(record_fight[first][i] != -1) {// 기본 초기화 값은 -1 (대결을 안한 사람은 -1로 표시)

				if(total[record_fight[first][i]][i + 1] == 0.5) { // 상대선수와 무승부했다면
					for(int j = 0; j < round_now + 1; j++) { // 현재 라운드까지 상대 선수의 점수를 가져옴
						sum += (total[record_fight[first][i]][j + 1]) * 0.5;
					}
				}

				else if(total[record_fight[first][i]][i + 1] == 0) {// 상대 선수를 이겼다면 상대선수 점수를 가져옴
					for(int j = 0; j < round_now + 1; j++) { // 현재 라운드까지 상대 선수의 점수를 가져옴
						sum += (total[record_fight[first][i]][j + 1]);
					}
				}
				// 상대선수에게 졌다면 점수 포함 안시킴
			}

		}

		for(int i = 0; i < people_num; i++) {
			if(rank[i][0] == first) {
				rank[i][berg_index] = sum; // 왼쪽 선수의 버그값
			}
		}

		sum = 0;
		if(second != -1) {
			for(int i = 0; i < round_now + 1; i++) {
				if(record_fight[second][i] != -1) {// 기본 초기화값은 -1 상대방과 싸운적이 있는지 탐색
					if(total[record_fight[second][i]][i + 1] == 0.5) { // 상대선수와 무승부했다면
						for(int j = 0; j < round_now + 1; j++) { // 현재 라운드까지 상대 선수의 점수를 가져옴
							sum += (total[record_fight[second][i]][j + 1]) * 0.5;
						}
					}

					else if(total[record_fight[second][i]][i + 1] == 0) {// 상대 선수를 이겼다면 상대선수 점수를 가져옴
						for(int j = 0; j < round_now + 1; j++) { // 현재 라운드까지 상대 선수의 점수를 가져옴
							sum += (total[record_fight[second][i]][j + 1]);
						}
					}
				}
			}
			for(int i = 0; i < people_num; i++) {
				if(rank[i][0] == second) {
					rank[i][berg_index] = sum; // 왼쪽 선수의 버그값
				}
			}
		}
	}

	//ranking
	public void standing(String[] people, double[][] rank) {
		// TODO Auto-generated method stub

		//값이 올바르게 들어가 있는지 확인하기 위한 코드
		System.out.println("이름  번호  승점  횟수  버치  버그");
		for(int i = 0; i < people_num; i++) {
			System.out.print(people[(int)Math.round(rank[i][0])] + " ");
			for(int j = 0; j < 5; j++) {
				System.out.print(rank[i][j] + "  ");
			}
			System.out.println();
		}

		//본 코드
		for(int i = 0; i < people.length; i++) {
			System.out.println(people[(int)Math.round(rank[i][0])]);
			rankingList[i].setText(people[(int)Math.round(rank[i][0])]);
		}
	}

	//sorting
	public void rank_sort(double[][] rank) { // 오름차순 정렬코드
		Arrays.sort(rank, new Comparator<double[]>() {
			public int compare(double[] o1, double[] o2) {
				if(o1[1] == o2[1]) {
					if(o1[2] == o2[2]) {
						if(o1[3] == o2[3]) {
							return Double.compare(o2[4], o1[4]);
						}
						else {
							return Double.compare(o2[3], o1[3]);
						}
					}
					else {
						return Double.compare(o2[2], o1[2]);
					}
				}
				else {
					return Double.compare(o2[1], o1[1]);
				}
			}
		});
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == nextButton) {
			rank_sort(rank);
			standing(people, rank);
			League_Table();
			round ++;
			System.out.println(round);
			swissLeagueFrame.topPanel.setText(round, 0);
			//swissLeagueFrame.topPanel.round;
			if(round == total_Round) {
				nextButton.setVisible(false);
				finishButton.setVisible(true);
			}
		}
		if(e.getSource() == finishButton) {
			rank_sort(rank);
			standing(people, rank);
			swissLeagueFrame.topPanel.setText(round, 1);
			swissLeagueFrame.resultOdd();
			swissLeagueFrame.finishPanel.printResult(people, rank);
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		int check = 0;
		//임시저장 배열문
		double[] win_count = new double[2];	// 승리횟수
		double[] win_point = new double[2]; // 승점

		for(int i = 0; i < people_num / 2 + 1; i++) {
			for(int j = 0; j < 2; j++) {
				win_count[j] = 0;
				win_point[j] = 0;
			}
			if(i == people_num / 2) {
				for(int j = 0; j < people_num; j++) {
					//first[check]가 왼쪽에 있는 선수의 번호이다
					//total에서 왼쪽 선수의 번호가 있는 배열을 찾고 해당 배열에 라운드 점수 할당
					if(total[j][0] == first[check]) {
						total[j][round_now + 1] = 1;
						// 라운드 점수를 할당후에 현재 라운드까지의 점수를 더해 임시 저장배열에 승점을 저장
						for(int k = 1; k < round_now + 2; k++) {
							win_point[0] += total[j][k];
							// 승점 저장 도중에 승리한 횟수 만큼 승리 횟수를 임시 저장배열에 저장
							if(total[j][k] == 1) {
								win_count[0] += 1;
							}
						}
					}
				}
				//승점을 total에 입력 buch, berg에서 사용되기 때문에 승점먼저 입력
				total[first[check]][total_Round + 1] = win_point[0];

				for(int j = 0; j < people_num; j++) {
					//왼쪽 선수의 승점 및 승리횟수 입력부분
					if(rank[j][0] == first[check]) {
						rank[j][1] = win_point[0];
						rank[j][2] = win_count[0];
					}
				}
				// 버치 버그값 구하는 함수 실행
				buch(total, record_fight, round_now, first[check], -1, rank);
				System.out.println("마지막 순번 버치 이후 배열상황");
				for(int j = 0; j < people_num; j++) {
					System.out.println(j + ". " + rank[j][0]);
				}
				berg(total, record_fight, round_now, first[check], -1, rank);
				System.out.println("마지막 순번 버그 이후 배열상황");
				for(int j = 0; j < people_num; j++) {
					System.out.println(j + ". " + rank[j][0]);
				}
				for(int j = 2; j < 5; j++) {
					for(int k = 0; k < people_num; k++) {
						if(rank[k][0] == first[check]) {
							total[first[check]][total_Round + j - 1] = rank[k][j];
						}
					}
				}

			}
			else {
				if(left_win[i].isSelected()) {
					//랭크 순위에 따라 대진한 사람 현재 라운드에 점수입력
					//라디오 버튼을 계속 바꿔 눌러도 점수가 쌓이지 않게 하기 위해 만든 임시저장 배열문
					for(int j = 0; j < people_num; j++) {
						//first[check]가 왼쪽에 있는 선수의 번호이다
						//total에서 왼쪽 선수의 번호가 있는 배열을 찾고 해당 배열에 라운드 점수 할당
						if(total[j][0] == first[check]) {
							total[j][round_now + 1] = 1;
							// 라운드 점수를 할당후에 현재 라운드까지의 점수를 더해 임시 저장배열에 승점을 저장
							for(int k = 1; k < round_now + 2; k++) {
								win_point[0] += total[j][k];
								// 승점 저장 도중에 승리한 횟수 만큼 승리 횟수를 임시 저장배열에 저장
								if(total[j][k] == 1) {
									win_count[0] += 1;
								}
							}
						}
						if(total[j][0] == second[check]) {
							total[j][round_now + 1] = 0;
							for(int k = 1; k < round_now + 2; k++) {
								win_point[1] += total[j][k];
								if(total[j][k] == 1) {
									win_count[1] += 1;
								}
							}
						}
					}


					//승점 및 승리횟수 입력
					// first[check]가 해당 선수의 번호를 나타내는 것이기 때문에
					// rank배열에서 해당 선수의 번호가 들어가있는 인덱스는 찾아내어 점수를 입력한다
					for(int j = 0; j < people_num; j++) {
						//왼쪽 선수의 승점 및 승리횟수 입력부분
						if(rank[j][0] == first[check]) {
							rank[j][1] = win_point[0];
							rank[j][2] = win_count[0];
						}
						// 오른쪽 선수의 승점 및 승리횟수 입력부분
						if(rank[j][0] == second[check]) {
							rank[j][1] = win_point[1];
							rank[j][2] = win_count[1];
						}
					}

				}
				else if(drow[i].isSelected()) {
					for(int j = 0; j < people_num; j++) {
						//first[check]가 왼쪽에 있는 선수의 번호이다
						//total에서 왼쪽 선수의 번호가 있는 배열을 찾고 해당 배열에 라운드 점수 할당
						if(total[j][0] == first[check]) {
							total[j][round_now + 1] = 0.5;
							// 라운드 점수를 할당후에 현재 라운드까지의 점수를 더해 임시 저장배열에 승점을 저장
							for(int k = 1; k < round_now + 2; k++) {
								win_point[0] += total[j][k];
								// 승점 저장 도중에 승리한 횟수 만큼 승리 횟수를 임시 저장배열에 저장
								if(total[j][k] == 1) {
									win_count[0] += 1;
								}
							}
						}
						if(total[j][0] == second[check]) {
							total[j][round_now + 1] = 0.5;
							for(int k = 1; k < round_now + 2; k++) {
								win_point[1] += total[j][k];
								if(total[j][k] == 1) {
									win_count[1] += 1;
								}
							}
						}
					}


					//승점 및 승리횟수 입력
					// first[check]가 해당 선수의 번호를 나타내는 것이기 때문에
					// rank배열에서 해당 선수의 번호가 들어가있는 인덱스는 찾아내어 점수를 입력한다
					for(int j = 0; j < people_num; j++) {
						//왼쪽 선수의 승점 및 승리횟수 입력부분
						if(rank[j][0] == first[check]) {
							rank[j][1] = win_point[0];
							rank[j][2] = win_count[0];
						}
						// 오른쪽 선수의 승점 및 승리횟수 입력부분
						if(rank[j][0] == second[check]) {
							rank[j][1] = win_point[1];
							rank[j][2] = win_count[1];
						}
					}
				}

				else if(right_win[i].isSelected()) {
					for(int j = 0; j < people_num; j++) {
						//first[check]가 왼쪽에 있는 선수의 번호이다
						//total에서 왼쪽 선수의 번호가 있는 배열을 찾고 해당 배열에 라운드 점수 할당
						if(total[j][0] == first[check]) {
							total[j][round_now + 1] = 0;
							// 라운드 점수를 할당후에 현재 라운드까지의 점수를 더해 임시 저장배열에 승점을 저장
							for(int k = 1; k < round_now + 2; k++) {
								win_point[0] += total[j][k];
								// 승점 저장 도중에 승리한 횟수 만큼 승리 횟수를 임시 저장배열에 저장
								if(total[j][k] == 1) {
									win_count[0] += 1;
								}
							}
						}
						if(total[j][0] == second[check]) {
							total[j][round_now + 1] = 1;
							for(int k = 1; k < round_now + 2; k++) {
								win_point[1] += total[j][k];
								if(total[j][k] == 1) {
									win_count[1] += 1;
								}
							}
						}
					}


					//승점 및 승리횟수 입력
					// first[check]가 해당 선수의 번호를 나타내는 것이기 때문에
					// rank배열에서 해당 선수의 번호가 들어가있는 인덱스는 찾아내어 점수를 입력한다
					for(int j = 0; j < people_num; j++) {
						//왼쪽 선수의 승점 및 승리횟수 입력부분
						if(rank[j][0] == first[check]) {
							rank[j][1] = win_point[0];
							rank[j][2] = win_count[0];
						}
						// 오른쪽 선수의 승점 및 승리횟수 입력부분
						if(rank[j][0] == second[check]) {
							rank[j][1] = win_point[1];
							rank[j][2] = win_count[1];
						}
					}

				}
				// 왼쪽 선수와 오른쪽 선수가 대진 했다는 기록
				record_fight[first[check]][round_now] = second[check];
				record_fight[second[check]][round_now] = first[check];

				//승점을 total에 입력 buch, berg에서 사용되기 때문에 승점먼저 입력
				total[first[check]][total_Round + 1] = win_point[0];
				total[second[check]][total_Round + 1] = win_point[1];

				// 버치 버그값 구하는 함수 실행
				buch(total, record_fight, round_now, first[check], second[check], rank);
				System.out.println("버치 이후 배열상황");
				for(int j = 0; j < people_num; j++) {
					System.out.println(j + ". " + rank[j][0]);
				}
				berg(total, record_fight, round_now, first[check], second[check], rank);
				System.out.println("버그 이후 배열상황");
				for(int j = 0; j < people_num; j++) {
					System.out.println(j + ". " + rank[j][0]);
				}

				for(int j = 2; j < 5; j++) {
					for(int k = 0; k < people_num; k++) {
						if(rank[k][0] == first[check]) {
							total[first[check]][total_Round + j - 1] = rank[k][j];
						}
						if(rank[k][0] == second[check]) {
							total[second[check]][total_Round + j - 1] = rank[k][j];
						}
					}
				}

				check++;
			}
		}

		System.out.println(round_now + "라운드");
		System.out.println("이름  번호  승점  횟수  버치  버그");
		for(int i = 0; i < people_num; i++) {
			System.out.print(people[(int)Math.round(rank[i][0])] + " ");
			for(int j = 0; j < 5; j++) {
				System.out.print(rank[i][j] + "  ");
			}
			System.out.println();
		}
	}

	// 2번째 라운드 부터 사용할 대진표 코드
	public void League_Table() {
		round_now++;
		int pass = round_now-1;
		int check = 0;
		int matchList_num = 0;


		// 오른쪽 선수까지 확인 안해도 되므로 전체 인원수 - 1
		for(int i = 0; i < people_num; i++) {
			System.out.println("League_Table에서 랭킹 확인");
			for(int j = 0; j < people_num; j++) {
				System.out.println(j + ". " + rank[j][0]);
			}
			first[check] = (int)Math.round(rank[i][0]);
			// 이미 앞 순위의 선수와 매칭이 이뤄진 선수는 매칭에서 제외
			if(record_fight[first[check]][round_now] == -1 && i != people_num - 1) {
				//  왼쪽 선수 출력
				matchList[matchList_num].setText(people[first[check]]);
				matchList_num++;
				System.out.print(people[first[check]] + " vs ");

				// i번째 선수의 뒤에서부터 확인하기 위해 check 변수 사용
				// i 번째 = 왼쪽 선수, i + j번째 오른쪽 선수

				for(int j = 1; (i + j) < people.length; j++) {
					pass = round_now - 1;
					second[check] = (int)Math.round(rank[i + j][0]);
					// 왼쪽 선수와 매칭이 되었던 선수인지 확인
					for(int k = 0; k < round_now; k++) {
						if(record_fight[second[check]][k] != first[check] && record_fight[second[check]][round_now] == -1) {
							System.out.println(record_fight[second[check]][k]);
							pass++;
							break;
						}
					}
					// 왼쪽 선수와 매칭된적이 없다면 해당 선수를 오른쪽 선수로 지정
					if(pass == round_now) {
						matchList[matchList_num].setText(people[second[check]]);
						matchList_num++;
						left_win[check].setText(people[first[check]] + " 승리");
						drow[check].setText("무승부");
						right_win[check].setText(people[second[check]] + " 승리");

						record_fight[first[check]][round_now] = second[check];
						record_fight[second[check]][round_now] = first[check];

						check++;
						break;

					}

				}
			}
		}
		matchList[people.length-1].setText(people[first[check]]);
	}

}
