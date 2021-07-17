package com.spirit.DMRE;


import javax.ws.rs.Path;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.LinkedHashMap;
import java.util.Map;

public class MedicalGuideLineService_implementedInJava{

	public String MedicalGuideLineExecutionJava(String jsonrequest){
		System.out.println("[MedicalGuideLineService] JSON-Request: " + jsonrequest);
		//checkABCDE-Algorithm
		//define defaults
		boolean checkABCDEAlgorithmResult = false;
		boolean checkAirwayResult = false;
		boolean checkBreathingResult = false;
		boolean checkCirculationResult = false;
		boolean checkDisabilityResult = false;
		boolean checkExposureResult = false;
		
		boolean airwayIsFree = true;
		boolean breathes = true;
		boolean cyanosis = false;
		String chestExpansion = "normal";
		String respiratoryRhythm = "normal";
		boolean chestExpansionSymmetric = true;
		boolean pulseCentral = true;
		boolean pulsePeripheral = true;
		boolean pupilsIsocore = true;
		boolean strokeSigns = false;
		boolean hasBleeding = false;
		boolean hasBrokenBone = false;
		boolean hasAllergies = false;
		boolean hasPain = false;
		int respiratoryRate = 0;
		int oxygenSaturation = 0;
		int glucoseLevel = 0;
		
		Map<String, Object> inputMap = null;
		try {
			inputMap = new ObjectMapper().readValue(jsonrequest, LinkedHashMap.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		for(Map.Entry<String, Object> entry : inputMap.entrySet()) {
			System.out.println(entry.getKey() + " : " + entry.getValue().toString());
		}
		if (airwayIsFree == Boolean.parseBoolean((String) inputMap.get("airwayFree"))) {
			System.out.println("airwayIsFree");
			System.out.println(Integer.parseInt((String) inputMap.get("respiratoryRate")));
			//--> proceeding to next case
			if ( Integer.parseInt((String) inputMap.get("respiratoryRate")) < 15 && 
				 Integer.parseInt((String) inputMap.get("respiratoryRate")) > 8 && 
				 Integer.parseInt((String) inputMap.get("oxygenSaturation")) > 95 && 
				 breathes == Boolean.parseBoolean((String) inputMap.get("breathes")) && 
				 cyanosis == Boolean.parseBoolean((String) inputMap.get("cyanosis")) &&
				 chestExpansion.equalsIgnoreCase((String) inputMap.get("chestExpansion")) &&
				 respiratoryRhythm.equalsIgnoreCase((String) inputMap.get("respiratoryRythm")) && 
				 chestExpansionSymmetric == Boolean.parseBoolean((String) inputMap.get("chestExpansionSymmetric"))
				 ){
					 System.out.println("breathingCheck ok");
					//--> proceeding to next case
					 if(Integer.parseInt((String) inputMap.get("pulseRate")) <130 &&
					 	Integer.parseInt((String) inputMap.get("pulseRate")) >50 &&
					 	Integer.parseInt((String) inputMap.get("systolicBloodPressure")) < 160 &&
					 	Integer.parseInt((String) inputMap.get("systolicBloodPressure")) > 50 &&
					 	pulseCentral == Boolean.parseBoolean((String) inputMap.get("pulseCentral")) && 
					 	pulsePeripheral == Boolean.parseBoolean((String) inputMap.get("pulsePeripheral"))
						) {
						 System.out.println("CirculationCheck ok");
						//--> proceeding to next case
						 if(Integer.parseInt((String) inputMap.get("glucoseLevel")) >50 &&
							Integer.parseInt((String) inputMap.get("glucoseLevel")) <500 &&
							Integer.parseInt((String) inputMap.get("gcsnumber")) <=15 &&
							Integer.parseInt((String) inputMap.get("gcsnumber")) >12 &&
							pupilsIsocore == Boolean.parseBoolean((String) inputMap.get("pupilsIsocore")) && 
							strokeSigns == Boolean.parseBoolean((String) inputMap.get("strokeSigns"))
							) {
							 System.out.println("DisabilityCheck ok");
							//--> proceeding to next case
							 if(hasBleeding == Boolean.parseBoolean((String) inputMap.get("hasBleeding")) &&
								hasBrokenBone == Boolean.parseBoolean((String) inputMap.get("hasBrokenBone")) &&
								hasAllergies == Boolean.parseBoolean((String) inputMap.get("hasAllergies")) &&
								hasPain == Boolean.parseBoolean((String) inputMap.get("hasPain"))
								) {
								 System.out.println("ExposureCheck ok");
							 }else {
								 System.out.println("ExposureCheck NOK");
								 if(hasBleeding != Boolean.parseBoolean((String) inputMap.get("hasBleeding")))
									 return "hasBleeding -> " + inputMap.get("hasBleeding").toString();
								 if(hasBrokenBone != Boolean.parseBoolean((String) inputMap.get("hasBrokenBone")))
									 return "hasBrokenBone -> " + inputMap.get("hasBrokenBone").toString();
								 if(hasAllergies != Boolean.parseBoolean((String) inputMap.get("hasAllergies")))
									 return "hasAllergies -> " + inputMap.get("hasAllergies").toString();
								 if(hasPain == Boolean.parseBoolean((String) inputMap.get("hasPain")))
									 return "hasPain -> " + inputMap.get("hasPain").toString();
							 }
						 }else {
							 System.out.println("DisabilityCheck NOK");
							 if(Integer.parseInt((String) inputMap.get("glucoseLevel")) <50)
								 return "glucoseLevel -> " + inputMap.get("glucoseLevel").toString();
							 if(Integer.parseInt((String) inputMap.get("glucoseLevel")) >500)
								 return "glucoseLevel -> " + inputMap.get("glucoseLevel").toString();
							 if(Integer.parseInt((String) inputMap.get("gcsnumber")) >15)
								 return "gcsnumber -> " + inputMap.get("gcsnumber").toString();
							 if(Integer.parseInt((String) inputMap.get("gcsnumber")) <12)
								 return "gcsnumber -> " + inputMap.get("gcsnumber").toString();
							 if(pupilsIsocore != Boolean.parseBoolean((String) inputMap.get("pupilsIsocore")))
								 return "pupilsIsocore -> " + inputMap.get("pupilsIsocore").toString();
							 if(strokeSigns != Boolean.parseBoolean((String) inputMap.get("strokeSigns")))
								 return "hasstrokeSigns -> " + inputMap.get("strokeSigns").toString();
						 }
					 }else {
						 System.out.println("CirculationCheck NOK");
						 if(Integer.parseInt((String) inputMap.get("pulseRate")) >130)
							return "pulseRate -> " + inputMap.get("pulseRate").toString();
					 	 if(Integer.parseInt((String) inputMap.get("pulseRate")) <50)
					 		return "pulseRate -> " + inputMap.get("pulseRate").toString();
					 	 if(Integer.parseInt((String) inputMap.get("systolicBloodPressure")) > 160)
					 		return "systolicBloodPressure -> " + inputMap.get("systolicBloodPressure").toString();
					 	 if(Integer.parseInt((String) inputMap.get("systolicBloodPressure")) < 50)
					 		return "systolicBloodPressure -> " + inputMap.get("systolicBloodPressure").toString();
					 	 if(pulseCentral != Boolean.parseBoolean((String) inputMap.get("pulseCentral")))
					 		return "haspulseCentral -> " + inputMap.get("pulseCentral");
					 	 if(pulsePeripheral != Boolean.parseBoolean((String) inputMap.get("pulsePeripheral")))
					 		return "hasPulsePeripheral -> " + inputMap.get("pulsePeripheral");
					 }
				 }else {
					 System.out.println("breathingCheck NOK");
					 if(Integer.parseInt((String) inputMap.get("respiratoryRate")) > 15)
						 return "respiratoryRate -> " + inputMap.get("respiratoryRate");
				     if(Integer.parseInt((String) inputMap.get("respiratoryRate")) < 8)
				    	 return "respiratoryRate -> " + inputMap.get("respiratoryRate");
				     if(Integer.parseInt((String) inputMap.get("oxygenSaturation")) < 95)
				    	 return "oxygenSaturation -> " + inputMap.get("oxygenSaturation");
				     if(breathes != Boolean.parseBoolean((String) inputMap.get("breathes")))
				    	 return "breathes -> " + inputMap.get("breathes");
				     if(cyanosis != Boolean.parseBoolean((String) inputMap.get("cyanosis")))
				    	 return "cyanosis -> " + inputMap.get("cyanosis");
				     if(!chestExpansionSymmetric == Boolean.parseBoolean((String) inputMap.get("chestExpansionSymmetric")))
				    	 return "chestExpansion -> " + inputMap.get("chestExpansion");
				     if(!respiratoryRhythm.equalsIgnoreCase((String) inputMap.get("respiratoryRythm")))
				    	 return "respiratoryRythm -> " + inputMap.get("respiratoryRythm");
				     if(!chestExpansionSymmetric == Boolean.parseBoolean((String) inputMap.get("chestExpansionSymmetric")))
				    	 return "chestExpansionSymmetric -> " + inputMap.get("chestExpansionSymmetric");
				 
				 }
		}else {
			System.out.println("Airway is Not free");
			if(airwayIsFree != Boolean.parseBoolean((String) inputMap.get("airwayFree")))
				return "airwayFree -> " + inputMap.get("airwayFree");
		}
		return null;

	}
	public static void main(String [] args) {
		System.out.println("Calling the IMplementation of the rules in java....");
		String jsonRequest ="{\"airwayFree\":\"true\",\"breathes\":\"true\",\"cyanosis\":\"false\",\"respiratoryRate\":\"10\",\"chestExpansion\":\"normal\",\"respiratoryRythm\":\"normal\",\"chestExpansionSymmetric\":\"true\",\"oxygenSaturation\":\"99\",\"pulseRate\":\"60\",\"systolicBloodPressure\":\"120\",\"pulseCentral\":\"true\",\"pulsePeripheral\":\"true\",\"glucoseLevel\":\"100\",\"gcsnumber\":\"15\",\"pupilsIsocore\":\"true\",\"strokeSigns\":\"false\",\"hasBleeding\":\"false\",\"hasBrokenBone\":\"false\",\"hasAllergies\":\"false\",\"hasPain\":\"false\"}";
		MedicalGuideLineService_implementedInJava mg = new MedicalGuideLineService_implementedInJava();
		String result = mg.MedicalGuideLineExecutionJava(jsonRequest);
		if(result != null)
			System.out.println(result);
	}
	

	
}
