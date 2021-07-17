package com.spirit.DMRE;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Observation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Table;
import com.spirit.DMRE.Utils.ObservationResource;
import com.spirit.DMRE.Utils.SampleResourceParseTest2;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;

import javax.ws.rs.core.Application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;

import com.spirit.DMRE.Provarules.ExecuteProvaRules;
import com.spirit.DMRE.Provarules.ProvaTest2;
import com.spirit.DMRE.Utils.*;
  
@Path("/MedicalGuideLineService")
public class MedicalGuideLineService{
	private static Logger logger = Logger.getLogger("com.spirit.DMRE.MedicalGuideLineService");
	private static List<Object> returnList = new ArrayList<Object>();
	private static int returnListCount = 0;
	@Context
    private UriInfo context;
	@Context
	private Request request;
	/**
	 * The DistributedMedicalServiceEngine is the core server, which allows to start different services, as needed in the DMRE Distributed environment
	 * @throws Exception 
	 */
	@GET
	@Path("{name}")
	public String getMsg(@PathParam("name") String name, @Context HttpServletResponse servletResponse) {
		StringBuilder sb = null;
		String output = null;
		System.out.println("SysOut: " + this.getClass().getName() + ": " + name);
		
		if(name.isEmpty()) {
			System.out.println("request is empty");
			
		}else {
			System.out.println("request is not empty");
			System.out.println("Request value: " + name);
		}
				
		return "MedicalGuideLineService GET-Done";

	}
	@POST
	@Path("{name}")
	public Response MedicalGuideLineExecution(String jsonrequest,@PathParam("ExecuteMedicalGuideline") String medicalGuideLineParameter){
		logger.info("Hitting the medical GuidelineService: the Post-part");
		System.out.println("MedicalGuideLineService: " + medicalGuideLineParameter);
		System.out.println("[MedicalGuideLineService] now in POST for a SPARQL-Query");
		System.out.println("[MedicalGuideLineService] JSON-Request: " + jsonrequest);
//		ProvaTest2 test = new ProvaTest2();
//		test.runNow();
				
		try {
			Map<String,Object> inputMap = new ObjectMapper().readValue(jsonrequest, LinkedHashMap.class);
			for(Map.Entry<String, Object> entry : inputMap.entrySet()) {
				System.out.println(entry.getKey() + " : " + entry.getValue().toString());
			}
			ExecuteProvaRules  executor = new ExecuteProvaRules(inputMap);
			//ProvaTest2  test = new ProvaTest2();
			executor.runNow();
			
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(returnListCount>1) {
			return Response.ok("ResponseFromMedicalGuidelineExecution - more than one results\n" + returnList, new MediaType("text","plain")).build();
		}else {
			return Response.ok("ResponseFromMedicalGuidelineExecution\n" + returnList, new MediaType("text","plain")).build();
		}

	}
	public static void Response(int errorCounterSend, List<Object> returnListFromProva) {
		System.out.println("MedicalGuideLineService - Response");
		System.out.println("errorCounterSend: " + errorCounterSend);
		System.out.println("returnList: " + returnListFromProva);
		returnList = returnListFromProva;
		returnListCount = errorCounterSend;
		
	}
	

	
}
