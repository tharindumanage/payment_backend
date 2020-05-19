package com;

import java.util.ArrayList;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.google.gson.*;
import org.json.simple.*;

import model.*;

import org.apache.tomcat.util.json.JSONParser;
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

@Path("/pay")
public class payment {

	paymentModel pay =new paymentModel();
	
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String view(String pays)
	{
		return pay.getPayments();
	}
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String add(String pays)
	{
		
		//Convert the input string to a JSON object
		JsonObject payObject = new JsonParser().parse(pays).getAsJsonObject();
		
		pay.addPayments(payObject.get("name").getAsString(),payObject.get("hopital").getAsString(),payObject.get("type").getAsString(),Double.parseDouble(payObject.get("price").getAsString()));
		
		JSONObject json = new JSONObject();
		json.put("success", Integer.toString(pay.getSuccess()));
		
		return json.toString();
			
		
	}
	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String edit(String pays)
	{
		
		//Convert the input string to a JSON object
		JsonObject payObject = new JsonParser().parse(pays).getAsJsonObject();
		
		pay.editPayments(payObject.get("name").getAsString(),payObject.get("hospital").getAsString(),payObject.get("type").getAsString(),Double.parseDouble(payObject.get("price").getAsString()),Integer.parseInt(payObject.get("id").getAsString()));
		
		JSONObject json = new JSONObject();
		json.put("success", Integer.toString(pay.getSuccess()));
		
		return json.toString();
			
		
	}
	
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String delete(String pays)
	{
		
		//Convert the input string to a JSON object
		JsonObject payObject = new JsonParser().parse(pays).getAsJsonObject();
		
		pay.deletePayment(Integer.parseInt(payObject.get("id").getAsString()));
		
		JSONObject json = new JSONObject();
		json.put("success", Integer.toString(pay.getSuccess()));
		
		return json.toString();
			
		
	}
	
	@POST
	@Path("/get")
	@Produces(MediaType.TEXT_HTML)
	public String viewOne(String pays)
	{
		JsonObject payObject = new JsonParser().parse(pays).getAsJsonObject();
		
		return pay.getPaymentById(Integer.parseInt(payObject.get("id").getAsString())).toString();
	}
	
}
