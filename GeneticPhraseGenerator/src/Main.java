import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

public class Main {
	
	static String target;
	static double mutationRate = 0.01;
	static int population = 10000;
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("Java Display");
		JTextField jInputBox = new JTextField("Enter the target phrase");
        JTextArea jOutputBox = new JTextArea();
        JButton button = new JButton("Run");
        
        frame.setLayout(new BorderLayout());
        frame.add(button,BorderLayout.SOUTH);
        frame.add(jInputBox,BorderLayout.NORTH);
        frame.add(jOutputBox,BorderLayout.CENTER);
        
        button.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                new SwingWorker<Void, Object>(){
                    @Override
                    protected Void doInBackground() throws Exception {
                        runProgram(jInputBox,jOutputBox);
                        return null;
                    }}.execute();
            }});
        
        frame.setSize(750, 750);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
	}
	
	public static void runProgram(JTextField input, JTextArea statScreen){
		target = input.getText();
		
		Population species = new Population(target,population,mutationRate);
		while(!species.isEvolved()){
			species.calcFitness();
			statScreen.setText("Generation: "+species.getGeneration()+"\t\t"+species.getBest()+"\n"+species.getAllPhrases());		
			species.naturalSelection();
			species.generate();
		}
	}

}
