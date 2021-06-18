package org.muplsql.transform.array;

import java.util.ArrayList;
import java.util.List;

import org.muplsql.mcase.Case;
import org.muplsql.transform.MITransformer;
import org.muplsql.transform.MutaOperatorType;

public class ScalarVariableforArrayReplacer  extends MITransformer {
	
	
	public ScalarVariableforArrayReplacer() {
		this.mtype = MutaOperatorType.Mot85.name();
		this.mopId = 85;
		oCcase = new Case();
	}

	@Override
	public List<String> mutateAsList(String value){
		if (true)
			throw new RuntimeException("This mutation operator works with code analysis");
	
		return null;
	}

	public List<String> mutateAsList(String value,  String changeList[]) {
		List<String> result = new ArrayList<>();

		if (value.equalsIgnoreCase(";") && 
				oCcase.whereStarted == 0  && 
				oCcase.ifStarted  == 0 && 
				oCcase.assignStarted == 1) {

			for (int i = 0; i < changeList.length; i++) {

					if (oCcase.preString.contains(changeList[i])) {
						result.add("'0';");
					}
				
			}

		}
		
		if (oCcase.whereStarted  == 0 && oCcase.ifStarted  == 0){
			oCcase.reset();
		}

		if (value.equalsIgnoreCase("where"))
			oCcase.whereStarted++;

		if (value.equalsIgnoreCase("if") )
			oCcase.ifStarted++;
		
		if (value.equalsIgnoreCase(":=") )
			oCcase.assignStarted++;
		
		if (value.equalsIgnoreCase(";") )
			oCcase.reset();
		
		return result;
	}
	
	@Override
	public  boolean hasPreCondition(){
		return false;
	}
	
	@Override
	public boolean checkPreCondition(String value, String valueNext) {
		return (oCcase.whereStarted == 0); // not in where clauses

	}

	
	public String getMType(){
		return "DB";
	}
}
