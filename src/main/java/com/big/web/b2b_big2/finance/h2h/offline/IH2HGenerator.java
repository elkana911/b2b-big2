package com.big.web.b2b_big2.finance.h2h.offline;

import java.io.File;
import java.io.IOException;

public interface IH2HGenerator {
	void exportToFile(File file) throws IOException;
	
}
