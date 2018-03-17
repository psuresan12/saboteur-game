package game.viewer.board;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import game.controller.GameManager;

@SuppressWarnings("serial")
public class AppResumeDialog extends JPanel {

	private JLabel resumeText;
	private JButton resumeButton, newGameButton;

	public AppResumeDialog(AppFrame appFrame) {

		resumeText = new JLabel("Would you like to resume the saved game ?");
		add(resumeText);

		resumeButton = new JButton("Resume Game");
		add(resumeButton);

		newGameButton = new JButton("New Game");
		add(newGameButton);

		// Add ActionListenre for ResumeButton.
		resumeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				appFrame.isSettingsUpdated = false;
				appFrame.makeBiggerScreen();
				GameManager.resumeGame();
			}
		});

		// Action Listener for New-Game Button
		newGameButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				appFrame.isSettingsUpdated = true;
				GameManager.deleteStoredFile();
				appFrame.populateMenu();

			}
		});

	}
}
