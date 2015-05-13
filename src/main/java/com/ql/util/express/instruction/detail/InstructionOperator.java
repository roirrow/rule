package com.ql.util.express.instruction.detail;

import java.util.List;

import org.apache.commons.logging.Log;

import com.ql.util.express.OperateData;
import com.ql.util.express.RunEnvironment;
import com.ql.util.express.instruction.op.OperatorBase;
import com.ql.util.express.instruction.opdata.OperateDataAttr;

public class InstructionOperator extends Instruction{
	private static final long serialVersionUID = -1217916524030161947L;
	OperatorBase operator;
	int opDataNumber;
	public InstructionOperator(OperatorBase aOperator,int aOpDataNumber){
	  this.operator = aOperator;
	  this.opDataNumber =aOpDataNumber;
	}
	public OperatorBase getOperator(){
		return this.operator;
	}
	public void execute(RunEnvironment environment,List<String> errorList) throws Exception{
		execute(this.operator,this.opDataNumber, environment, errorList, this.log);
	}
	public static void execute(OperatorBase aOperator,int aOpNum,RunEnvironment environment,List<String> errorList,Log log) throws Exception{		
		OperateData[] parameters = environment.popArray(environment.getContext(),aOpNum);		
		if(environment.isTrace() && log.isDebugEnabled()){
			String str = aOperator.toString() + "(";
			for(int i=0;i<parameters.length;i++){
				if(i > 0){
					str = str + ",";
				}
				if(parameters[i] instanceof OperateDataAttr){
					str = str + parameters[i] + ":" + parameters[i].getObject(environment.getContext());
				}else{
				   str = str + parameters[i];
				}
			}
			str = str + ")";
			log.debug(str);
		}
		
		OperateData result = aOperator.execute(environment.getContext(),parameters, errorList);
		environment.push(result);
		environment.programPointAddOne();
	}
	public String toString(){
		String result = "OP : " + this.operator.toString() +  " OPNUMBER[" + this.opDataNumber +"]";
		return result;
	}
}	