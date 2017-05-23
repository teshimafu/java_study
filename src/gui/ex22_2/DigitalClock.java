package gui.ex22_2;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class DigitalClock extends JFrame {

	public static void main(String[] args) {
		DigitalClock clock = new DigitalClock();
		clock.setVisible(true);
	}

	TimerPanel tp;
	MenuDialog md;
	Thread dialogThread;

	public DigitalClock() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 200, 100);
		//setResizable(false);
		tp = new TimerPanel();
		setLayout(new BorderLayout());
		add(tp);
		md = new MenuDialog();
		JMenuBar menubar = new JMenuBar();
		JMenu menu = new JMenu("Menu");
		JMenuItem menuItem = new JMenuItem("Property");
		menu.add(menuItem);
		menubar.add(menu);
		setJMenuBar(menubar);
		menuItem.addActionListener(new MenuAction());

		dialogThread = new Thread(new DialogRunnable());
		dialogThread.start();
	}

	private class DialogRunnable implements Runnable {
		@Override
		public void run() {
			while (true) {
				tp.setData(md.getNewFont(), md.getFontColor(), md.getBackgroundColor());
				try {
					DigitalClock.this.setSize(
							(int) FontPixel.getFontPixelSize(md.getNewFont().getSize() - 1).getWidth() * 10,
							(int) FontPixel.getFontPixelSize(md.getNewFont().getSize() - 1).getHeight() + 50);
				} catch (NullPointerException e) {
					// 一回目はnull
				}
			}
		}

	}

	/**
	 * メニューのアクションがあるのであればこのクラスに追加する
	 *
	 * @author p000526831
	 *
	 */
	class MenuAction implements ActionListener {
		// メニューのイベント処理
		public void actionPerformed(ActionEvent e) {

			if (e.getActionCommand() == "Property") {
				md.setVisible(true);
			}
		}
	}
}
