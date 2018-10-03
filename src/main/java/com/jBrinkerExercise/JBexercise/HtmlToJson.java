package com.jBrinkerExercise.JBexercise;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HtmlToJson {
	
	// request user input
	@RequestMapping("/")
	public ModelAndView index () {
		ModelAndView mv = new ModelAndView("index");
		mv.addObject("tag", "made it here");
		return mv;
		//htmlParse(fileName);
	}
	
	// parse input and display JSON object
	@RequestMapping("/parse")
	public ModelAndView htmlParse(@RequestParam("htmlfile") String htmlFile) {
		
		//Scanner scan = new Scanner(System.in);
		//System.out.println("Please enter the name (excluding extension) of an invoice html file located in this project's root directory (e.g.: invoice1):");
		//String htmlFile = scan.nextLine();
		JSONObject invoice = new JSONObject();
		
		try {
			
			File input = new File(htmlFile + ".html");
			Document doc = Jsoup.parse(input, "UTF-8");
		    
			Elements ref = doc.getElementsContainingOwnText("Invoice #");
			String refText = ref.text();
			String[] rtSplit = refText.split(" ");
		    String invNum = rtSplit[6];
			invoice.put("ref", invNum);
		    
		    Elements date = doc.getElementsContainingOwnText("Date");
		    String dateText = date.text();
		    dateText = dateText.substring(6);
		   
		    if (dateText.contains("/")) {
		    	
		    	try {
				    DateFormat dfSlash = new SimpleDateFormat("dd/mm/yyy", Locale.ENGLISH);
				    DateFormat output = new SimpleDateFormat("yyyy-MM-dd");
				    Date result = dfSlash.parse(dateText);
				    String shortResult = output.format(result);
				    invoice.put("date", shortResult);
			    }
			    catch (ParseException e)
			    {
			    	e.printStackTrace();
			    }  
		   
		    } else {
		    	try {
		    		
				    DateFormat df = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
				    Date result =  df.parse(dateText);  
				    DateFormat output = new SimpleDateFormat("yyyy-MM-dd");
				    String shortResult = output.format(result);
				    invoice.put("date", shortResult);
			    }
			    catch (ParseException f)
			    {
			    	f.printStackTrace();
			    }  
		    }
		    
		    Elements gTotal = doc.getElementsContainingOwnText("Grand Total");
		    Element money = gTotal.get(0);
		    String moneyText = money.text();
		    String[] splitMoney = moneyText.split(" ");
		    
		    if (splitMoney.length > 3) {
		    	String currency = splitMoney[3];
		    	invoice.put("currency", currency);
		    	String amount = splitMoney[2];
		    	invoice.put("amount", amount);
		    } else {
		    	String amount = splitMoney[2];
		    	invoice.put("amount", amount);
		    }
		    
		    }
		    catch (IOException f)
		    {
		    	f.printStackTrace();
		    }  
	
		//System.out.println(invoice);
		//scan.close();
		//result(invoice);
		
		return new ModelAndView("result", "tag", invoice);
	}
	
}


