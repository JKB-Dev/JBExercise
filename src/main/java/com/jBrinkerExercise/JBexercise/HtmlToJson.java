package com.jBrinkerExercise.JBexercise;

import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;



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
	
	// TODO: order json properties: ref, date, currency, amount
	// also, export .json file
	
	// request user input
	@RequestMapping("/")
	public ModelAndView index () {
		ModelAndView mv = new ModelAndView("index");
		mv.addObject("tag", "made it here");
		return mv;
	}
	
	// parse input and display JSON object
	@RequestMapping("/parse")
	public ModelAndView htmlParse(@RequestParam("htmlfile") String htmlFile) {
		
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
				    DateFormat dfSlash = new SimpleDateFormat("mm/dd/yyy", Locale.ENGLISH);
				    DateFormat output = new SimpleDateFormat("yyyy-mm-dd");
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
		    String[] splitMoney = moneyText.split("\\$");
		    String fullAmount = splitMoney[1];
		    String[] lastAmount = fullAmount.split(" ");
		    
		    String currency = "";
		    double amount = Double.parseDouble(lastAmount[0]);
		    
		    if (lastAmount.length > 1) {
		    	currency = lastAmount[1];
		    } else {
		    	currency = "USD";
		    }
		    
		    invoice.put("currency", currency);
		    invoice.put("amount", amount);
		    
		    }
		    catch (IOException f)
		    {
		    	f.printStackTrace();
		    }  
		
		// not working -- need to create POJO?
		
//		try (FileWriter file = new FileWriter("test.json")) {
//
//            file.write(invoice.toJSONString());
//            file.flush();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
	
		System.out.println(invoice);	// for testing
		
		return new ModelAndView("result", "tag", invoice);
	}
	
}


