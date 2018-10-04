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

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class HtmlToJson {
	
	String htmlFileName;
	String ref;
	String date;
	String currency;
	double amount;
	
	// request user input
	@RequestMapping("/")
	public ModelAndView index () {
		ModelAndView mv = new ModelAndView("index");
		mv.addObject("tag", "made it here");
		return mv;
	}
	
	// parse input and display JSON object
	@RequestMapping("/parse")
	public ModelAndView htmlParse(@RequestParam("htmlfile") String htmlFile, @RequestParam("pretty") Boolean pretty) {
		
		htmlFileName = htmlFile;
		JSONObject invoice = new JSONObject();
		
		try {
			
			File input = new File(htmlFile + ".html");
			Document doc = Jsoup.parse(input, "UTF-8");
		    
			Elements refEl = doc.getElementsContainingOwnText("Invoice #");
			String refText = refEl.text();
			String[] rtSplit = refText.split(" ");
		    ref = rtSplit[6];
			invoice.put("ref", ref);
		    
		    Elements dateEl = doc.getElementsContainingOwnText("Date");
		    String dateText = dateEl.text();
		    dateText = dateText.substring(6);
		   
		    if (dateText.contains("/")) {
		    	
		    	try {	
				    DateFormat dfSlash = new SimpleDateFormat("mm/dd/yyy", Locale.ENGLISH);
				    DateFormat output = new SimpleDateFormat("yyyy-mm-dd");
				    Date result = dfSlash.parse(dateText);
				    date = output.format(result);
				    invoice.put("date", date);
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
				    date = output.format(result);
				    invoice.put("date", date);
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
		    
		    amount = Double.parseDouble(lastAmount[0]);
		    
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
		
		// create invoice object
		Invoice invoiceObj = new Invoice(ref, date, currency, amount);
		
		ObjectMapper mapper = new ObjectMapper();

		try {
			// Convert object to JSON string and save into a file directly
			
			if (pretty == true) {
				
			// ordered, pretty printed:
			mapper.writerWithDefaultPrettyPrinter().writeValue(new File(htmlFileName + ".json"), invoiceObj);
			} else {
				
			// unordered, not pretty printed:
			mapper.writeValue(new File(htmlFileName + ".json"), invoiceObj);
			}	
			
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return new ModelAndView("result", "tag", htmlFileName + ".json has been written to the project's root directory.");
	}
	
}

