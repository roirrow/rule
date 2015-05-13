package com.ql.util.express.instruction.detail;

import java.util.List;

import com.ql.util.express.OperateData;
import com.ql.util.express.RunEnvironment;
import com.ql.util.express.instruction.opdata.OperateDataAttr;
import com.ql.util.express.instruction.opdata.OperateDataVirClass;

public class InstructionNewVirClass extends Instruction {
	private static final long serialVersionUID = -4174411242319009814L;
	String className;
	int opDataNumber;

	public InstructionNewVirClass(String name, int aOpDataNumber) {
		this.className = name;
		this.opDataNumber = aOpDataNumber;
	}

	public void execute(RunEnvironment environment, List<String> errorList)
			throws Exception {
		OperateData[] parameters = environment.popArray(
				environment.getContext(), this.opDataNumber);
		if (environment.isTrace() && log.isDebugEnabled()) {
			String str = "new VClass(";
			for (int i = 0; i < parameters.length; i++) {
				if (i > 0) {
					str = str + ",";
				}
				if (parameters[i] instanceof OperateDataAttr) {
					str = str + parameters[i] + ":"
							+ parameters[i].getObject(environment.getContext());
				} else {
					str = str + parameters[i];
				}
			}
			str = str + ")";
			log.debug(str);
		}
		OperateDataVirClass result = new OperateDataVirClass(className);
		environment.push(result);
		environment.programPointAddOne();
		result.initialInstance(environment.getContext(), parameters, errorList,
				environment.isTrace(), this.log);
	}

	public String toString() {
		return "new VClass[" + this.className + "] OPNUMBER["
				+ this.opDataNumber + "]";
	}

}