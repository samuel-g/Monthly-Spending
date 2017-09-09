import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Map.Entry;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class SpendingSwing{
	
	private File selectedfile;
	private Map<String, Map<String, Double>> data;
	
	private String s = "";
	String s2 = "";
	String s3 = "";
	String s4 = "";

		
	public static void main(String[] args) {
		
		SpendingSwing object = new SpendingSwing();
		object.createAndShowGUI(object);
	}

	public void createAndShowGUI(SpendingSwing object) {
		
		
		JPanel windowContent = new JPanel();
		BorderLayout fl = new BorderLayout();
		windowContent.setLayout(fl);
		JFrame frame = new JFrame("Monthly Spending by Category");		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JButton uploadButton = new JButton();
		JTextArea text = new JTextArea();
		uploadButton.setText("Upload File");
		windowContent.add(uploadButton, BorderLayout.PAGE_START);
		windowContent.add(text, BorderLayout.CENTER);
		JScrollPane pane = new JScrollPane(windowContent);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setSize(screenSize.width/2, screenSize.height*75/100);
		frame.setVisible(true);
		uploadButton.setFont(new Font("Arial", Font.BOLD, 20));
		uploadButton.setToolTipText("CSV files exported to excel from Mint.com only. Please remove any commas(,) from your file");
		frame.getContentPane().add(pane, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		uploadButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try{
					selectedfile = object.selectFile();
					data = object.readFile(selectedfile);
					Entry<String, Double> element = null;
					
					//Loop thru Month, <Category, Price>
					for(Entry<String, Map<String, Double>> entry: data.entrySet()) 
					{
						s2 = "";
						s3 = "";
						
						//Contains Map with Category, Price
						Map<String, Double> entrySet = entry.getValue();
						Iterator<Map.Entry<String, Double>> i = entrySet.entrySet().iterator();
						while(i.hasNext()){
						    element = i.next();
						    s2 = s2 + element.getKey() + " = $" + Math.round(element.getValue()*100.00)/100.00 + "\r\n";
						    s3 = s3 + entry.getKey()+ element.getKey() + element.getValue();
						}
						s = s + entry.getKey() + "\r\n" + s2 + "\r\n";
					}
					
					text.setText(s);
					text.setFont(new Font("Arial", Font.PLAIN, 20));
				}catch(Exception exc){
					System.out.println("None selected");
					exc.printStackTrace();
				}
			}
		});
	
	}
	
	protected Map<String, Map<String, Double>> readFile(File selectedfile2) {
		String line = "";
	    String[] columns;
	    String[] columns2;
	    Set<String> categories = new TreeSet<>();
	    Map<String, Double> janData = new TreeMap<>();
	    Map<String, Double> febData = new TreeMap<>();
	    Map<String, Double> marData = new TreeMap<>();
	    Map<String, Double> aprData = new TreeMap<>();
	    Map<String, Double> mayData = new TreeMap<>();
	    Map<String, Double> junData = new TreeMap<>();
	    Map<String, Double> julData = new TreeMap<>();
	    Map<String, Double> augData = new TreeMap<>();
	    Map<String, Double> sepData = new TreeMap<>();
	    Map<String, Double> novData = new TreeMap<>();
	    Map<String, Double> octData = new TreeMap<>();
	    Map<String, Double> decData = new TreeMap<>();
	    Map<String, Map<String, Double>> doubleMap = new TreeMap<>();
	    Double janValue = 0.00;Double febValue = 0.00;Double marValue = 0.00;Double aprValue = 0.00;Double mayValue = 0.00;Double junValue = 0.00;
	    Double julValue = 0.00;Double augValue = 0.00;Double sepValue = 0.00;Double octValue = 0.00;Double novValue = 0.00;Double decValue = 0.00;
 
	    String mapKey = "";
	    Double mapValue = 0.00;
	
	    Calendar cal = Calendar.getInstance();
	    DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
	    String month = "";
			
			List<String> categoriesList = new ArrayList<>();
			//read the file
	        try (BufferedReader br = new BufferedReader(new FileReader(selectedfile2))){
	        	br.readLine();
	            while ((line = br.readLine()) != null) {
	            	columns = line.split(",");//read every line and put each word in an array
	            	categories.add(columns[1]); //categorize the data based on column 1 and store them in a set
	           
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        } 
	       
	        categoriesList.addAll(categories);
	       
	      
	        for(int x=0; x<categoriesList.size(); x++){
	        	//Reinitialize value to 0, so they don't keep adding on top of each other
	        	janValue = 0.00; febValue = 0.00; marValue = 0.00; aprValue = 0.00; mayValue = 0.00; junValue = 0.00;
	       	    julValue = 0.00; augValue = 0.00; sepValue = 0.00; octValue = 0.00; novValue = 0.00; decValue = 0.00;
	       	    mapValue = 0.0;
	     
	       	    //read the file ***SECOND TIME*** 
	       	    try (BufferedReader br = new BufferedReader(new FileReader(selectedfile2))){
	       	    	//each every line, compare column 1 with every category, if category matches column1, then add
	       	    	br.readLine();
	       	    	while ((line = br.readLine()) != null) {
	          		//read every line and put each word in an array
	          			
	          		columns2 = line.split(",");
	          		cal.setTime(df.parse(columns2[0]));
	            	month = cal.getDisplayName(Calendar.MONTH, Calendar.LONG_FORMAT, Locale.US);
	          		
	           		if(columns2[1].matches(categoriesList.get(x)))
	           			//&& categoriesList.get(x).equals("Amazon")
	           		{          				
	           			//System.out.println();
	           			
	           			switch(month){
	           				case "January":
	           					mapKey = categoriesList.get(x);
	           					if(columns2[4].equals("debit"))
	    	           			{
	    	           				janValue = janValue + Double.parseDouble(columns2[3]);
	    	           			} else 
	    	           			{
	    	           				//subtract billpays, refunds, etc. 
	    	           				janValue = janValue - Double.parseDouble(columns2[3]);
	    	           			}
	           					janData.put(mapKey, janValue);
	           					doubleMap.put(month, janData);
	           					break;
	           				case "February":
	           					if(columns2[4].equals("debit"))
	    	           			{
	    	           				febValue = febValue + Double.parseDouble(columns2[3]);
	    	           			} else 
	    	           			{
	    	           				febValue = febValue - Double.parseDouble(columns2[3]);
	    	           			}
	           					febData.put(categoriesList.get(x), febValue);
	           					doubleMap.put(month, febData);
	           					break;
	           				case "March":
	           					if(columns2[4].equals("debit"))
	    	           			{
	    	           				marValue = marValue + Double.parseDouble(columns2[3]);
	    	           			} else 
	    	           			{
	    	           				marValue = marValue - Double.parseDouble(columns2[3]);
	    	           			}
	           					marData.put(categoriesList.get(x), marValue);
	           					doubleMap.put(month, marData);
	           					break;
	           				case "April":
	           					if(columns2[4].equals("debit"))
	    	           			{
	    	           				aprValue = aprValue + Double.parseDouble(columns2[3]);
	    	           			} else 
	    	           			{
	    	           				aprValue = aprValue - Double.parseDouble(columns2[3]);
	    	           			}
	           					aprData.put(categoriesList.get(x), aprValue);
	           					doubleMap.put(month, aprData);
	           					break;
	           				case "May":
	           					if(columns2[4].equals("debit"))
	    	           			{
	    	           				mayValue = mayValue + Double.parseDouble(columns2[3]);
	    	           			} else 
	    	           			{
	    	           				mayValue = mayValue - Double.parseDouble(columns2[3]);
	    	           			}
	           					mayData.put(categoriesList.get(x), mayValue);
	           					doubleMap.put(month, mayData);
	           					break;
	           				case "June":           				
	           					if(columns2[4].equals("debit"))
	    	           			{
	           						junValue = junValue + Double.parseDouble(columns2[3]);
	    	           			} else 
	    	           			{
	    	           				junValue = junValue - Double.parseDouble(columns2[3]);
	    	           			}
	           					junData.put(categoriesList.get(x), junValue);
	           					doubleMap.put(month, junData);
	           					break;
	           				case "July":
	           					if(columns2[4].equals("debit"))
	    	           			{
	    	           				julValue = julValue + Double.parseDouble(columns2[3]);
	    	           			} else 
	    	           			{
	    	           				julValue = julValue - Double.parseDouble(columns2[3]);
	    	           			}
	           					julData.put(categoriesList.get(x), julValue);
	           					doubleMap.put(month, julData);
	           					break;
	           				case "August":
	           					if(columns2[4].equals("debit"))
	    	           			{
	    	           				augValue = augValue + Double.parseDouble(columns2[3]);
	    	           			} else 
	    	           			{
	    	           				augValue = augValue - Double.parseDouble(columns2[3]);
	    	           			}
	           					augData.put(categoriesList.get(x), augValue);
	           					doubleMap.put(month, augData);
	           					break;
	           				case "September":
	           					if(columns2[4].equals("debit"))
	    	           			{
	    	           				sepValue = sepValue + Double.parseDouble(columns2[3]);
	    	           			} else 
	    	           			{
	    	           				sepValue = sepValue - Double.parseDouble(columns2[3]);
	    	           			}
	           					sepData.put(categoriesList.get(x), sepValue);
	           					doubleMap.put(month, sepData);
	           					break;
	           				case "October":
	           					if(columns2[4].equals("debit"))
	    	           			{
	    	           				octValue = octValue + Double.parseDouble(columns2[3]);
	    	           			} else 
	    	           			{
	    	           				octValue = octValue - Double.parseDouble(columns2[3]);
	    	           			}
	           					octData.put(categoriesList.get(x), octValue);
	           					doubleMap.put(month, octData);
	           					break;
	           				case "November":
	           					if(columns2[4].equals("debit"))
	    	           			{
	    	           				novValue = novValue + Double.parseDouble(columns2[3]);
	    	           			} else 
	    	           			{
	    	           				novValue = novValue - Double.parseDouble(columns2[3]);
	    	           			}
	           					novData.put(categoriesList.get(x), novValue);
	           					doubleMap.put(month, novData);
	           					break;
	           				case "December":
	           					if(columns2[4].equals("debit"))
	    	           			{
	    	           				decValue = decValue + Double.parseDouble(columns2[3]);
	    	           			} else 
	    	           			{
	    	           				decValue = decValue - Double.parseDouble(columns2[3]);
	    	           			}
	           					decData.put(categoriesList.get(x), decValue);
	           					doubleMap.put(month, decData);
	           					break;
	           				
	           				
	           			}
	           			
	           			if(columns2[4].equals("debit"))
	           			{
	           				mapValue = mapValue + Double.parseDouble(columns2[3]);
	           			} else 
	           			{
	           				//subtract billpays, refunds, etc. 
	           				mapValue = mapValue - Double.parseDouble(columns2[3]);
	           			}
	           			   	
	          		}
	          
	           }
	       }
	        catch (IOException | ParseException e) {
	           e.printStackTrace();
	       } 
		}
	    return doubleMap;
	   
	}

	public File selectFile() {
		JFileChooser fileChooser = new JFileChooser();
		if(fileChooser.showOpenDialog(null)==JFileChooser.APPROVE_OPTION)
		{
			//get the file
			File file = fileChooser.getSelectedFile();
			return file;
		}
		else{
			System.out.println("None");
			return null;
		}
	}

}
