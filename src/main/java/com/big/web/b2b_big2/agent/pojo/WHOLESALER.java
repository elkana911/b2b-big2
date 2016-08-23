package com.big.web.b2b_big2.agent.pojo;


public enum WHOLESALER {
	BERJAYA_INOVASI_GLOBAL(16), MANDIRA_TRAVEL(113);
	
	private long agentId;
	
	private WHOLESALER(long agentId) {
		this.agentId = agentId;
	}
	
	public long getAgentId() {
		return agentId;
	}

	public void setAgentId(long agentId) {
		this.agentId = agentId;
	}

	public static WHOLESALER getWholeSaler(long agentId) {
		for (WHOLESALER d : WHOLESALER.values()) {
			if (agentId == d.getAgentId())
				return d;
		}
		
		return null;
	}
}
