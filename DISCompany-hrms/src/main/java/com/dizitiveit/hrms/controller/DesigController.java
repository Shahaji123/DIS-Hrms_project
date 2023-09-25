package com.dizitiveit.hrms.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dizitiveit.hrms.dao.DesignationDAO;
import com.dizitiveit.hrms.model.Designation;
import com.dizitiveit.hrms.pojo.Responses;
@RestController
@RequestMapping("/designation")
public class DesigController {
	@Autowired
	private DesignationDAO desigDao;
	
	@PostMapping("/addDesig")
	public ResponseEntity<?> addDesig(@RequestBody Designation designation)
	{
	   if(desigDao.existsByDesigName(designation.getDesigName()))
	   {
		   return ResponseEntity.badRequest().body(Responses.builder().message("Designation Already Exists").build());
	   }
	   else
	   {
		desigDao.save(designation);
		//return new ResponseEntity<Designation>(designation,HttpStatus.OK);
		//return ResponseEntity.ok("Saved Successfully");
		return ResponseEntity.ok(Responses.builder().message("Designation Details are Saved Successfully.").build());
	   }
		
	}//method
	

	@PatchMapping("/updateDesig/{desigId}")
	public ResponseEntity<?> updateDesig(@RequestBody Designation designation , @PathVariable("desigId") int desigId)
	{
		Designation desigUpdate= desigDao.findBydesigId(desigId);
		if(desigUpdate!=null)
		{
		desigUpdate.setDesigName(designation.getDesigName());
		desigUpdate.setGrade(designation.getGrade());
		desigUpdate.setLevel(designation.getLevel());
		desigDao.save(desigUpdate);
		//return ResponseEntity.ok(desigUpdate);	
		//return ResponseEntity.ok("Updated Successfully.");
		return ResponseEntity.ok(Responses.builder().message("Designation Details Updated Successfully.").build());
		}
		//else {
			 return ResponseEntity.badRequest().body(Responses.builder().message("Can not found Designation ID").build());
		//}
		
	}//method
	
	 @GetMapping("/getDesigDetails")
	 public ResponseEntity<?> Designation() {
	 List<Designation> designation=desigDao.findAll();
	 if(designation!=null) {
		 HashMap<String,List<Designation>> map=new HashMap<String, List<Designation>>();
		   map.put("Designation", designation);
		   return ResponseEntity.ok(map);
	 
	 } 
	 else { 
		 return ResponseEntity.ok(Responses.builder().message("Data Not Found").build()); 
	  } 
	 }//method
	 
	 @GetMapping("/getDesigNames")
	  public ResponseEntity<?> getDesigNames()
	  {
		  List<String> dept= desigDao.findBydesigName();
		  HashMap<String,List<String>> map=new HashMap<String,List<String>>();
	      map.put("desigNames", dept);
	      return ResponseEntity.ok(map);
		  
	  }//method
	 

	 @GetMapping("/getDesigName/{desigName}") 
	 public ResponseEntity<?> getDesigId(@PathVariable("desigName") String desigName)
      { 
	   Designation desig=desigDao.findByDesigName(desigName);
	   if(desig!=null) 
	 {
	   HashMap<String,Designation> map=new HashMap<String, Designation>();
	   map.put("Designation", desig);
	   return ResponseEntity.ok(map);	 
	 }
	
	   else 
		 { 
		   return ResponseEntity.badRequest().body(Responses.builder().message("Can not found Designation Name").build());
		  }
   }
	
	  
	 @DeleteMapping("/deleteDesig/{desigId}")
		public ResponseEntity<?> deleteDesig(@PathVariable("desigId") int desigId)
		{
	
			if(desigDao.existsByDesigId(desigId))
			{
			desigDao.deleteById(desigId);
			
			return ResponseEntity.ok(Responses.builder().message("Designation Details Deleted Successfully.").build());
			}
			else {
			 return ResponseEntity.badRequest().body(Responses.builder().message(" Not found desigId " + desigId).build());
			}
			
		}
	 
}
