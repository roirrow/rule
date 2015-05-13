package com.ql.util.express.instruction.op;

import com.ql.util.express.Operator;
import com.ql.util.express.OperatorOfNumber;

public   class OperatorAddReduce extends Operator {
	public OperatorAddReduce(String name) {
		this.name = name;
	}
	public OperatorAddReduce(String aAliasName, String aName, String aErrorInfo) {
		this.name = aName;
		this.aliasName = aAliasName;
		this.errorInfo = aErrorInfo;
	}
	public Object executeInner(Object[] list) throws Exception {
		return executeInner(list[0], list[1]);
	}

	public Object executeInner(Object op1,
			Object op2) throws Exception {
		Object obj = null;
		if (this.getName().equals("+")) {
			obj = OperatorOfNumber.add(op1, op2,this.isPrecise);
		} else if (this.getName().equals("-")) {
			obj = OperatorOfNumber.subtract(op1, op2,this.isPrecise);
		}
		return obj;
	}
}