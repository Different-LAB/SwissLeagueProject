package SwissLeagueProject;

import java.awt.LayoutManager;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class RegisterPanel extends JPanel implements ListSelectionListener{

	public SwissLeagueFrame swissLeagueFrame;

	//Label
	public JLabel[] registerName;

	//List
	public JList list;

	//ListModel
	public DefaultListModel model;
	public JScrollPane scrolled;

	public RegisterPanel(LayoutManager title) {
		super(title);
	}

	public RegisterPanel(SwissLeagueFrame swissLeagueFrame) {
		this.swissLeagueFrame = swissLeagueFrame;

		this.setBorder(BorderFactory.createTitledBorder("참가자"));

		//list
		model = new DefaultListModel();
		list = new JList(model);
		list.addListSelectionListener(this);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		//list FixdeSize
		list.setFixedCellHeight(20);
		list.setFixedCellWidth(300);

		scrolled = new JScrollPane(list);
		scrolled.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

		this.add(scrolled, "Center");
	}

	//list 항목 추가 함수
	public void addItem(String text) {
		model.addElement(text);
		scrolled.getVerticalScrollBar().setValue(scrolled.getVerticalScrollBar().getMaximum());
	}

	//list 항목 삭제 함수
	public void removeItem(int index) {
		if(index < 0) {
			if(model.size() == 0) {
				return;
			}
		}
		model.remove(index);
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		//List 선택시
		if(!e.getValueIsAdjusting()) {
			System.out.println("selected :" + list.getSelectedValue());
		}
	}
}
