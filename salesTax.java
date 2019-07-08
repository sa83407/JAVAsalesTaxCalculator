import java.io.IOException;
import java.lang.IllegalStateException;
import java.nio.file.Paths;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.*;
import java.util.ArrayList;
import java.util.Collections;
import java.text.DecimalFormat;
import java.nio.file.Path;


public class salesTax 
{ 

    public static void main(String args[]) 
    {
    	//get filename from user
    	System.out.println("This program calculates the basic sales tax for any existing text file.\n");
	System.out.println("Enter file or directory name (or q to quit):");
	Scanner scanner = new Scanner(System.in);
	String filename = scanner.nextLine();
	System.out.println();
	
	//continue executing until user quits
	do{
		//check if filename exists or if file is in proper format
	    	try (Scanner input = new Scanner(Paths.get(filename)))
	    	{	
	    		double totalTax = 0.00;
			double totalWTax = 0.00;
			int importCount = 0;
			ArrayList<Double> priceTest = new ArrayList<Double>();
			ArrayList<Integer> itemNum = new ArrayList<Integer>();
			ArrayList<String> keyWords = new ArrayList<String>();

	    		while (input.hasNext()) // while there's more data to read in file
	    		{	//separate file contents
	    			String line = input.nextLine();
	    			String[] arrOfStr = line.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");
	    			int amount = Integer.parseInt(arrOfStr[0]);
				String desc = arrOfStr[1];
				String comPrice = arrOfStr[2] + arrOfStr[3] + arrOfStr[4];
				double price = Double.parseDouble(comPrice);
				desc = desc.replaceAll(" at ", "");
				DecimalFormat df = new DecimalFormat("#.00");    		
	    			df.format(price);
	    			
				//add contents to arraylists
				priceTest.add(price);
				itemNum.add(amount);
				keyWords.add(desc);
			}
			//find highest price of imported item
	    		double maxImportPriceVal = Collections.max(priceTest);
	    		//get sizeOfArray for for loop execution
	    		int sizeOfArray = priceTest.size() - 1;
	    		
	    		//print receipt
	    		for(int i = 0; i <= sizeOfArray; i++)
	    		{
	    			int amount = itemNum.get(i);
	    			String desc = keyWords.get(i);
	    			double price = priceTest.get(i);
	    			
	    			if(desc.contains("imported") && importCount == 0 && price == maxImportPriceVal)
				{
					double tax = Math.ceil((0.15 * price * amount) * 20);
					double round = (tax / 20) + (price * amount);
					System.out.format(amount+desc+ ":"+" $%.2f%n", round);
					totalTax += (tax/20);
					totalWTax += round;
					++importCount;	
				}
				else if(desc.contains("imported") && importCount == 0)
				{
					double tax = Math.ceil((0.05 * price * amount) * 20);
					double round = (tax / 20) + (price * amount);
					System.out.format(amount+desc+ ":"+" $%.2f%n", round);
					totalTax += (tax/20);
					totalWTax += round;
				}
				else if(desc.contains("imported") && importCount > 0)
				{
					double tax = Math.ceil((0.05 * price * amount) * 20);
					double round = (tax / 20) + (price * amount);
					System.out.format(amount+desc+ ":"+" $%.2f%n", round);
					totalTax += (tax/20);
					totalWTax += round;
					++importCount;
				}
	    			else if(desc.contains("chocolate") || desc.contains("pills") || desc.contains("book"))
	    			{
					double total = (price * amount);
					System.out.format(amount+desc+ ":"+" $%.2f%n", total);
					totalWTax += total;			
				}
				else
				{
					double tax = Math.ceil((0.1 * price * amount) * 20);
					double round = (tax / 20) + (price * amount);
					System.out.format(amount+desc+ ":"+" $%.2f%n", round);
					totalTax += (tax/20);
					totalWTax += round;
				}
			}
			System.out.format("Sales Taxes: %.2f%n" , totalTax);
			System.out.format("Total: %.2f%n", totalWTax); 
			}
		//if theres a filename error or filename doesn't exist
		catch (NoSuchElementException | IllegalStateException | IOException e)
			{
				System.err.println("Error processing file. Terminating.");
				System.exit(1);
			}
	//get filename from user	
	System.out.println("\nEnter file or directory name (or q to quit):");		
	filename = scanner.nextLine();
	System.out.println();
    	}while(!filename.equals("q"));
    }
}
		
    
 
